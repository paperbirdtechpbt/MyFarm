package com.pbt.myfarm.Fragement.CollectNewData

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.pbt.myfarm.Fragement.PackCollect.CollectActivityList
import com.pbt.myfarm.Fragement.PackCollect.SensorsList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.fragment_collect_new_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CollectNewData : Fragment(),Callback<ResponseCollectAcitivityResultList> {
    private var param1: String? = null
    private var param2: String? = null
    var viewmodel: CollectNewDataViewModel? = null
    val TAG = "CollectNewData"
    var collectId: Int = 0
    var sensorId: Int = 0
    val collectactivitylist = ArrayList<CollectAcitivityResultList>()
    val collectactivitylistName = ArrayList<String>()
    var result: Spinner? = null
    var unit: Spinner? = null
    var valueSpinner: Spinner? = null
    var value: EditText? = null
    var valuedate: EditText? = null
    val myCalendar: Calendar = Calendar.getInstance()


    companion object {

        var sensorlist = ArrayList<SensorsList>()
        var collectActivityList = ArrayList<CollectActivityList>()
        var sensorid = ArrayList<String>()
        var sensorname = ArrayList<String>()
        var collectid = ArrayList<String>()
        var collectname = ArrayList<String>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_collect_new_data, container, false)

        initViewModel()
        result = view.findViewById(R.id.field_spinner_result)
        value = view.findViewById(R.id.ed_value)
        valuedate = view.findViewById(R.id.dt_value)
        valueSpinner = view.findViewById(R.id.spinner_value)
        unit = view.findViewById(R.id.spinner_units)

        val collectActivity: Spinner = view.findViewById(R.id.field_Collect_activity)
        val sensonser: Spinner = view.findViewById(R.id.field_spinner_sensor)

        Handler(Looper.getMainLooper()).postDelayed({
            setSpinner(collectActivity, sensonser)
                                                    }, 1000)

        return view
    }

    private fun setSpinner(collectActivity: Spinner, sensonser: Spinner) {

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, collectname)

        collectActivity.adapter = adapter

        val adapter2 = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, sensorname)
        sensonser.adapter = adapter2

        collectActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int,
                id: Long) {

                for (i in 0 until collectActivityList.size) {
                    if (i == position) {
                        collectId = collectActivityList.get(i).id.toInt()
                        AppUtils.logDebug(TAG, "sensorlistID---->" + collectId)
                        callApi(collectId.toString())

                    }

                }


            }

        }
        sensonser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                position: Int, id: Long) {

                for (i in 0 until sensorlist.size) {
                    if (i == position) {
                        sensorId = sensorlist.get(i).id.toInt()
                        AppUtils.logDebug(TAG, "sensorlistID---->" + id)


                    }
                }
            }

        }
    }

    private fun callApi(collectactivity_id: String) {

        ApiClient.client.create(ApiInterFace::class.java).collectAcitivityResultList(
            MySharedPreference.getUser(requireContext())?.id.toString(),
            collectactivity_id
        ).enqueue(this)
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this).get(CollectNewDataViewModel::class.java)
        viewmodel?.onCollectDataFieldList(requireContext())
        AppUtils.logDebug(TAG, "sensorlistID---->" + sensorlist.size.toString())

    }

    override fun onResponse(
        call: Call<ResponseCollectAcitivityResultList>,
        response: Response<ResponseCollectAcitivityResultList>
    ) {
        if (response.body()?.error == false) {
            val baseresposne: ResponseCollectAcitivityResultList = Gson().fromJson(
                Gson().toJson(response.body()),
                ResponseCollectAcitivityResultList::class.java
            )
            collectactivitylistName.clear()
            collectactivitylist.clear()
            baseresposne.data.forEach {
                collectactivitylist.add(it)
                collectactivitylistName.add(it.result_name)
            }
//            Handler(Looper.getMainLooper()).postDelayed({
                setSpinnerResult(collectactivitylistName)

//                                                        }, 1000)

        }
    }


    override fun onFailure(call: Call<ResponseCollectAcitivityResultList>, t: Throwable) {
        Toast.makeText(requireContext(), "Failed To Update", Toast.LENGTH_SHORT).show()
        AppUtils.logDebug(TAG, "Failur report-->" + t.message)

    }

    private fun setSpinnerResult(collectactivitylistName: ArrayList<String>) {

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, collectactivitylistName
        )
        result?.adapter = adapter
        result?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in 0 until collectactivitylist.size) {
                    if (i == position) {
                        val resultid = collectactivitylist.get(i).id
                        AppUtils.logDebug(TAG, "result---->" + resultid)
                        callResultValueApi(resultid)

                    }
                }
            }

        }
    }

    private fun callResultValueApi(resultid: String) {
        val service = ApiClient.client.create(ApiInterFace::class.java)
        val apiInterFace = service.collectAcitivityResultValue(
            MySharedPreference.getUser(requireContext())?.id.toString(),
            resultid  )

        apiInterFace.enqueue(object : Callback<ResponsecollectAcitivityResultValue> {
            override fun onResponse(
                call: Call<ResponsecollectAcitivityResultValue>,
                response: Response<ResponsecollectAcitivityResultValue>
            ) {
                if (response.body()?.error == false) {
                    AppUtils.logDebug(TAG, "callResultValueApi---" + Gson().toJson(response.body()))
                    val basrespose: ResponsecollectAcitivityResultValue = Gson().fromJson(
                        Gson().toJson(response.body()),
                        ResponsecollectAcitivityResultValue::class.java
                    )
                    val data: Data = basrespose.data
                    val unitList = ArrayList<UnitList>()
                    val unitListname = ArrayList<String>()
                    basrespose.unit_list.forEach {
                        unitList.add(it)
                        unitListname.add(it.name)
                    }
                    setValueSpinner(unitList, unitListname, data)
                }
            }

            override fun onFailure(call: Call<ResponsecollectAcitivityResultValue>, t: Throwable) {
                try {
                    AppUtils.logError(TAG,t.message.toString())
                }
                catch (e:Exception){
                    AppUtils.logError(TAG,e.localizedMessage)

                }
            }

        })
    }

    private fun setValueSpinner(
        unitList: ArrayList<UnitList>, unitListname: ArrayList<String>, data: Data) {
        AppUtils.logDebug(TAG, "setValueSpinner")
        if (data.type == "date") {
            spinner_value?.visibility = View.GONE
            ed_value?.visibility = View.GONE
            dt_value.visibility = View.VISIBLE
            dt_value.setOnClickListener {

                AppUtils.logDebug("TAG", "on CLick in date")
                val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->

                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, day)
                    updateLabel(dt_value)
                }
                DatePickerDialog(
                    requireContext(), date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        } else if (data.type == "numeric" || data.type == "text") {
            ed_value?.visibility = View.VISIBLE
            dt_value?.visibility = View.GONE
            spinner_value?.visibility = View.GONE
        }

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, unitListname)
        spinner_units?.adapter = adapter
    }

    private fun updateLabel(dtValue: EditText) {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        dtValue.setText(dateFormat.format(myCalendar.time))
    }
}

