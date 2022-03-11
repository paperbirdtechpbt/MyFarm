package com.pbt.myfarm.Fragement.CollectNewData

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity.Companion.positionnn
import com.pbt.myfarm.Fragement.CollectNewData.Adapter.AdapterAddmultiple
import com.pbt.myfarm.Fragement.CollectNewData.Model.ListMultipleCollcetdata
import com.pbt.myfarm.Fragement.PackCollect.CollectActivityList
import com.pbt.myfarm.Fragement.PackCollect.CollectPackFragement.Companion.collectDataId
import com.pbt.myfarm.Fragement.PackCollect.SensorsList
import com.pbt.myfarm.HttpResponse.EditCollectData
import com.pbt.myfarm.HttpResponse.Responseeditcollectdata
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.fragment_collect_new_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CollectNewData : Fragment(), Callback<ResponseCollectAcitivityResultList> {
    private var param1: String? = null
    private var param2: String? = null
    var viewmodel: CollectNewDataViewModel? = null
    val TAG = "CollectNewData"
    var collectId: Int = 0
    var collectNAME: String = ""
    var sensorId: Int = 0
    var sensorNAME: String = ""
    var resultid: Int = 0
    var resultNAME: String = ""
    var spinnerUnitId: Int = 0
    var spinnerUnitNAme: String = ""
    val collectactivitylist = ArrayList<CollectAcitivityResultList>()
    val collectactivitylistName = ArrayList<String>()
    val collectactivitylistid = ArrayList<String>()
    var result: Spinner? = null
    var unit: Spinner? = null
    var valueSpinner: Spinner? = null
    var value: EditText? = null
    var valuedate: EditText? = null
    val myCalendar: Calendar = Calendar.getInstance()
    var edValue: String = ""
    var dtvalue: String = ""
    var spinnervalue: String = ""
    var duration: String = ""
    var dataa: EditCollectData? = null
    var myduration: EditText? = null
    var getResultId: String = ""
    var getCollectId: String = ""
    var getUnitId: String = ""
    var getValueId: String = ""
    var getSensorId: String = ""
    var adapterAddmultiple: AdapterAddmultiple? = null
    var listmultipleData = ArrayList<ListMultipleCollcetdata>()
    val handler: Handler = Handler(Looper.getMainLooper())
    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }


    companion object {
        var objectmultipleData: ListMultipleCollcetdata? = null
        var sensorlist = ArrayList<SensorsList>()
        var collectActivityList = ArrayList<CollectActivityList>()
        var sensorid = ArrayList<String>()
        var sensorname = ArrayList<String>()
        var collectid = ArrayList<String>()
        var collectname = ArrayList<String>()
        val name = String

        fun newInstance(name: String): CollectNewData {
            val fragment = CollectNewData()

            val bundle = Bundle().apply {
                putString("ARG_NAME", name)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        progressbar_collectnewdata.visibility = View.GONE
        if (listmultipleData != null) {
            progressbar_collectnewdata.visibility = View.GONE
            adapterAddmultiple = AdapterAddmultiple(requireContext(), listmultipleData)
            recyclerview_addmultipledata.adapter = adapterAddmultiple

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_collect_new_data, container, false)


        val name = arguments?.getString("ARG_NAME")
        AppUtils.logDebug(TAG, "-=-=-=--=-=-=" + name)

        result = view.findViewById(R.id.field_spinner_result)
        value = view.findViewById(R.id.ed_value)
        valuedate = view.findViewById(R.id.dt_value)
        valueSpinner = view.findViewById(R.id.spinner_value)
        unit = view.findViewById(R.id.spinner_units)
        myduration = view.findViewById(R.id.ed_duration)

        val collectActivity: Spinner = view.findViewById(R.id.field_Collect_activity)
        val sensonser: Spinner = view.findViewById(R.id.field_spinner_sensor)

        val button: Button = view.findViewById(R.id.btn_collectNewData)
        val BtnUpdate: Button = view.findViewById(R.id.btn_UpdateNewData)
        val BtnAddmore: Button = view.findViewById(R.id.btn_addmoreData)

        initViewModel()
        if (collectid != null) {
            handler.postDelayed({
                setSpinner(collectActivity, sensonser, false)

            }, 1000)

        } else {
            handler.postDelayed({
                setSpinner(collectActivity, sensonser, false)

            }, 1200)
        }



        if (positionnn == 2) {
            positionnn = 0
            button.visibility = View.GONE
            BtnAddmore.visibility = View.GONE
            BtnUpdate.visibility = View.VISIBLE
            val service = ApiClient.client.create(ApiInterFace::class.java)
            val apiInterFace = service.editcollectdata(
                collectDataId
            )
            AppUtils.logDebug(TAG, "collectDataId-" + collectDataId)

            apiInterFace.enqueue(object : Callback<Responseeditcollectdata> {
                override fun onResponse(
                    call: Call<Responseeditcollectdata>,
                    response: Response<Responseeditcollectdata>
                ) {
                    AppUtils.logDebug(TAG, Gson().toJson(response.body()))
                    if (response.body()?.error == false) {
                        val baserespomse: Responseeditcollectdata = Gson().fromJson(
                            Gson().toJson(response.body()),
                            Responseeditcollectdata::class.java
                        )
                        dataa = Gson().fromJson(
                            Gson().toJson(baserespomse.data),
                            EditCollectData::class.java
                        )
                        if (dataa != null) {
                            setSpinner(collectActivity, sensonser, true)
                        }
                        myduration?.setText(dataa?.duration.toString())

                    }
                }

                override fun onFailure(call: Call<Responseeditcollectdata>, t: Throwable) {
                    try {
                        AppUtils.logError(TAG, t.message.toString())
                    } catch (e: Exception) {
                        AppUtils.logError(TAG, e.localizedMessage)

                    }
                }

            })
        }
        getActivity()?.setTitle("Update Pack")

        BtnUpdate.setOnClickListener {
            var unit = ""
            if (ed_value != null) {
                getValueId = ed_value.text.toString()
            } else if (dt_value != null) {
                getValueId = ed_value.text.toString()
            } else {
                getValueId = spinner_value.getSelectedItem().toString()
            }
            if (getUnitId == null) {
                getUnitId = dataa?.unit_id.toString()
            }
            if (getCollectId == null) {
                getCollectId == dataa?.collect_activity_id.toString()
            }
            if (getSensorId == null) {
                getSensorId == dataa?.sensor_id.toString()
            }
            if (getResultId == null) {
                getResultId == dataa?.result_id.toString()
            }

            val service = ApiClient.client.create(ApiInterFace::class.java)
            val apiInterFace = service.updateCollectData(
                MySharedPreference.getUser(requireContext())?.id.toString(),
                packList?.id!!,
                getResultId,
                getValueId,
                dataa?.unit_id.toString(),
                dataa?.sensor_id.toString(),
                myduration!!.text.toString(),
                collectDataId
            )

            apiInterFace.enqueue(object : Callback<ResponseCollectAcitivityResultList> {
                override fun onResponse(
                    call: Call<ResponseCollectAcitivityResultList>,
                    response: Response<ResponseCollectAcitivityResultList>
                ) {
                    if (response.body()?.error == false) {
                        Toast.makeText(
                            requireContext(),
                            "${response.body()?.msg}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(activity, UpdatePackActivity::class.java)
                        intent.putExtra("fragment", "1")
                        startActivity(intent)
                        activity?.finish()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "${response.body()?.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ResponseCollectAcitivityResultList>,
                    t: Throwable
                ) {
                    try {
                        AppUtils.logError(TAG, t.message.toString())
                    } catch (e: Exception) {
                        AppUtils.logError(TAG, e.localizedMessage)

                    }
                }

            })
        }
        button.setOnClickListener {
                            progressbar_collectnewdata.visibility = View.VISIBLE

//            if (!listmultipleData.isNullOrEmpty()) {
if (listmultipleData.size>=1){
                for (i in 0 until listmultipleData.size) {
                    objectmultipleData = listmultipleData.get(i)
                    callMyApi("Value", objectmultipleData!!)
                }

                listmultipleData.clear()
                objectmultipleData = null

            } else {
                if (ed_value != null) {
                    edValue = ed_value.text.toString()
                } else if (dt_value != null) {
                    dtvalue = ed_value.text.toString()
                } else {
                    spinnervalue = spinner_value.getSelectedItem().toString()
                }
                duration = myduration?.text.toString()

                callStoreCollectDataAPi(
                    edValue,
                    dtvalue, spinnervalue, duration, sensorId, collectId, resultid, spinnerUnitId
                )
            }


        }
        BtnAddmore.setOnClickListener {

            var valuee: String = ""
            if (ed_value != null) {
                valuee = ed_value.text.toString()
            } else if (dt_value != null) {
                valuee = dt_value.text.toString()
            } else {
                valuee = spinner_value.getSelectedItem().toString()
            }
            duration = myduration?.text.toString()

            recyclerview_addmultipledata?.layoutManager = LinearLayoutManager(requireContext())

            listmultipleData.add(
                ListMultipleCollcetdata(
                    collectNAME, collectId.toString(),
                    resultNAME, resultid.toString(),
                    spinnerUnitNAme, spinnerUnitId.toString(),
                    sensorNAME, sensorId.toString(), valuee,
                    valuee, duration
                )
            )
            Toast.makeText(requireContext(), "Added $collectNAME", Toast.LENGTH_SHORT).show()

            adapterAddmultiple = AdapterAddmultiple(requireContext(), listmultipleData)
            recyclerview_addmultipledata.adapter = adapterAddmultiple

            myduration?.setText("")
            collectActivity.setSelection(0)
            result?.setSelection(0)
            unit?.setSelection(0)
            sensonser.setSelection(0)
            if (edValue != null) {
                value?.setText("")

            } else if (dtvalue != null) {
                valuedate?.setText("")

            } else {
                valueSpinner?.setSelection(0)

            }
        }







        return view
    }

    private fun
             callStoreCollectDataAPi(
        edValue: String,
        dtvalue: String,
        spinnervalue: String,
        duration: String,
        sensorId: Int,
        collectId: Int,
        resultid: Int,
        spinnerUnitId: Int
    ) {
        AppUtils.logDebug(TAG, "soinnerunitiD--" + spinnerUnitId)
        if (edValue != null) {
            callMyApi(edValue, objectmultipleData!!)


        } else if (dtvalue != null) {
            callMyApi(dtvalue, objectmultipleData!!)
        } else {
            callMyApi(spinnervalue, objectmultipleData!!)

        }

    }

    private fun callMyApi(edValue: String, objectmultipleDataa: ListMultipleCollcetdata) {


        if (edValue != null) {

            if (collectId == null) {
                collectId = collectActivityList.get(0).id.toInt()
            }
            var apiInterFace: Call<ResponseCollectAcitivityResultList>? = null


            val service = ApiClient.client.create(ApiInterFace::class.java)
            if (objectmultipleDataa != null) {
                apiInterFace = service.storecollectdata(
                    packList?.id.toString(),
                    objectmultipleDataa.resultID,
                    objectmultipleDataa.activityID,
                    objectmultipleDataa.valueID,
                    objectmultipleDataa.valueID,
                    objectmultipleDataa.unitID,
                    objectmultipleDataa.sensorID,
                    objectmultipleDataa.duration,
                    MySharedPreference.getUser(requireContext())?.id.toString(),
                )
            } else {
                apiInterFace = service.storecollectdata(
                    packList?.id.toString(), resultid.toString(), collectId.toString(),

                    edValue, edValue, spinnerUnitId.toString(), sensorId.toString(), duration,
                    MySharedPreference.getUser(requireContext())?.id.toString(),
                )
            }


            apiInterFace.enqueue(object : Callback<ResponseCollectAcitivityResultList> {
                override fun onResponse(
                    call: Call<ResponseCollectAcitivityResultList>,
                    response: Response<ResponseCollectAcitivityResultList>
                ) {
                    if (response.body()?.error == false) {

                        progressbar_collectnewdata.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "${response.body()?.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(activity, UpdatePackActivity::class.java)
                        intent.putExtra("fragment", "1")
                        activity?.finish()

                    }
                }

                override fun onFailure(
                    call: Call<ResponseCollectAcitivityResultList>,
                    t: Throwable
                ) {
                    progressbar_collectnewdata?.visibility = View.GONE
                    try {
                        AppUtils.logError(TAG, t.message.toString())
                    } catch (e: Exception) {
                        AppUtils.logError(TAG, e.localizedMessage)

                    }
                }

            })

        }

    }

    private fun setSpinner(collectActivity: Spinner, sensonser: Spinner, boolean: Boolean) {
        if (boolean) {

            if (dataa?.sensor_id != null) {
                for (i in 0 until sensorlist.size) {

                    if (dataa?.sensor_id.toString() == sensorlist.get(i).id) {
                        AppUtils.logDebug(
                            TAG,
                            "sensor loop" + dataa?.sensor_id.toString() + "+" + sensorid.get(i)
                        )

                        sensonser.setSelection(i)
                    }
                }
            }


            if (dataa?.collect_activity_id != null) {
                myduration?.setText(dataa?.duration.toString())
                AppUtils.logDebug(TAG, "sensor list id" + sensorid.toString())



                for (i in 0 until collectid.size) {

                    if (dataa?.collect_activity_id.toString() == collectid.get(i)) {


                        collectActivity.setSelection(i)
                        callApi(collectid.get(i))
                    }
                }

            }
        } else {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, collectname
            )

            collectActivity.adapter = adapter

            val adapter2 = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, sensorname
            )
            sensonser.adapter = adapter2
        }


        collectActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int,
                id: Long
            ) {

                for (i in 0 until collectActivityList.size) {
                    if (i == position) {
                        collectId = collectActivityList.get(i).id.toInt()
                        collectNAME = collectActivityList.get(i).name
                        callApi(collectId.toString())
                        getCollectId = collectId.toString()
                    }

                }


            }

        }
        sensonser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {

                for (i in 0 until sensorlist.size) {
                    if (i == position) {
                        sensorId = sensorlist.get(i).id.toInt()
                        sensorNAME = sensorlist.get(i).name
                        getSensorId = sensorId.toString()


                    }
                }
            }

        }
        callApi("17")


    }

    private fun callApi(collectactivity_id: String) {

        ApiClient.client.create(ApiInterFace::class.java).collectAcitivityResultList(
            MySharedPreference.getUser(requireContext())?.id.toString(),
            collectactivity_id
        ).enqueue(this)

    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this).get(CollectNewDataViewModel::class.java)
        viewmodel?.progressbar = progressbar_collectnewdata
        viewmodel?.onCollectDataFieldList(requireContext())

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
                collectactivitylistid.add(it.id)
            }

            if (collectactivitylistName != null) {
                setSpinnerResult(collectactivitylistName)

            }


        }

    }

    override fun onFailure(call: Call<ResponseCollectAcitivityResultList>, t: Throwable) {
        Toast.makeText(requireContext(), "Failed To Update", Toast.LENGTH_SHORT).show()
        AppUtils.logDebug(TAG, "Failur report-->" + t.message)

    }

    private fun setSpinnerResult(collectactivitylistName: ArrayList<String>) {

        try {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, collectactivitylistName
            )
            result?.adapter = adapter
            progressbar_collectnewdata.visibility = View.GONE
        } catch (e: Exception) {
            AppUtils.logDebug(TAG, e.localizedMessage.toString())
            progressbar_collectnewdata.visibility = View.GONE

        }



        if (dataa?.result_id != null) {
            for (i in 0 until collectactivitylistName.size) {
                if (dataa?.result_id.toString() == collectactivitylistid.get(i)) {

                    result?.setSelection(i)
                }
            }
        }


//        Handler(Looper.getMainLooper()).postDelayed({
        setListner()
//        }, 1000)


    }

    private fun setListner() {
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
                        resultid = collectactivitylist.get(i).id.toInt()
                        resultNAME = collectactivitylist.get(i).result_name
                        callResultValueApi(resultid.toString())
                        getResultId = collectactivitylistid.get(i)

                    }
                }

            }

        }
    }

    private fun callResultValueApi(resultid: String) {
//        val service = ApiClient.getClient().create(ApiInterFace::class.java)
        val service = ApiClient.getClient()!!.create(ApiInterFace::class.java)
        val apiInterFace = service.collectAcitivityResultValue(
            MySharedPreference.getUser(requireContext())?.id.toString(),
            resultid
        )

        apiInterFace.enqueue(object : Callback<ResponsecollectAcitivityResultValue> {
            override fun onResponse(
                call: Call<ResponsecollectAcitivityResultValue>,
                response: Response<ResponsecollectAcitivityResultValue>
            ) {
                if (response.body()?.error == false) {
                    val basrespose: ResponsecollectAcitivityResultValue = Gson().fromJson(
                        Gson().toJson(response.body()),
                        ResponsecollectAcitivityResultValue::class.java
                    )
                    val data: Data = basrespose.data
                    val unitList = ArrayList<UnitList>()
                    val unitListname = ArrayList<String>()
                    val valueListname = ArrayList<String>()
                    val valueListid = ArrayList<String>()

                    basrespose.unit_list.forEach {
                        unitList.add(it)
                        unitListname.add(it.name)
                    }
                    data.list_array.forEach {
                        valueListname.add(it.name)
                        valueListid.add(it.id)

                    }
                    if (!data.list_array.isNullOrEmpty()) {
                        setUnitSpinner(valueListid, valueListname, data)

                    }

                    setValueSpinner(unitList, unitListname, data)
                }
            }

            override fun onFailure(call: Call<ResponsecollectAcitivityResultValue>, t: Throwable) {
                try {
                    AppUtils.logError(TAG, t.message.toString())
                } catch (e: Exception) {
                    AppUtils.logError(TAG, e.localizedMessage)

                }
            }

        })
    }

    private fun setUnitSpinner(
        valueListid: ArrayList<String>, valueListname: ArrayList<String>, data: Data
    ) {


        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            valueListname
        )
        spinner_value?.adapter = adapter

    }

    private fun setValueSpinner(
        unitList: ArrayList<UnitList>, unitListname: ArrayList<String>, data: Data
    ) {
        try {
            if (data.type == "date") {

                spinner_value?.visibility = View.GONE
                ed_value?.visibility = View.GONE
                dt_value.visibility = View.VISIBLE
                dt_value.setText(dataa?.value)
                dt_value.setOnClickListener {
                    var settime = ""

                    val c = Calendar.getInstance()
                    val mHour = c[Calendar.HOUR_OF_DAY]
                    val mMinute = c[Calendar.MINUTE]

                    val timePickerDialog = TimePickerDialog(
                        context,
                        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            settime = "$hourOfDay:$minute"
                            AppUtils.logDebug(TAG, "settime" + settime)
//                    txtTime.setText("$hourOfDay:$minute")
                            updateLabel(dt_value, settime)

                        }, mHour, mMinute, false
                    )

                    timePickerDialog.show()

                    val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->

                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, month)
                        myCalendar.set(Calendar.DAY_OF_MONTH, day)
                        updateLabel(dt_value, settime)
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
                ed_value?.setText(dataa?.value)
            } else if (data.type == "list" || data.type == "List" || data.type == "table" || data.type == "Table") {


                spinner_value?.visibility = View.VISIBLE
                ed_value?.visibility = View.GONE
                dt_value.visibility = View.GONE
            }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, unitListname
            )
            spinner_units?.adapter = adapter
        } catch (e: Exception) {
            AppUtils.logDebug(TAG, e.localizedMessage.toString())
        }







        spinner_units?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in 0 until unitListname.size) {
                    if (i == position) {
                        spinnerUnitId = unitList.get(i).id.toInt()
                        spinnerUnitNAme = unitList.get(i).name
//                        callResultValueApi(resultid.toString())
                        getSensorId = spinnerUnitId.toString()

                    }
                }

            }

        }

    }


    private fun updateLabel(dtValue: EditText, time: String) {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        dtValue.setText(dateFormat.format(myCalendar.time) + "  T- " + time)
    }
}

