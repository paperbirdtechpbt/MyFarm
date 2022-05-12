package com.pbt.myfarm.Fragement.CollectNewData

import android.annotation.SuppressLint
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
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity.Companion.positionnn
import com.pbt.myfarm.CollectData
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Fragement.CollectNewData.Adapter.AdapterAddmultiple
import com.pbt.myfarm.Fragement.CollectNewData.Model.ListMultipleCollcetdata
import com.pbt.myfarm.Fragement.PackCollect.CollectActivityList
import com.pbt.myfarm.Fragement.PackCollect.CollectDataFragement
import com.pbt.myfarm.Fragement.PackCollect.CollectDataFragement.Companion.collectDataId
import com.pbt.myfarm.Fragement.PackCollect.CollectDataFragement.Companion.collectDataServerId
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


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateNewCollectDataFragment : Fragment(), Callback<ResponseCollectAcitivityResultList> {
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
    var db: DbHelper? = null
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

        fun newInstance(name: String): CreateNewCollectDataFragment {
            val fragment = com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment()

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

        db = DbHelper(requireContext(), null)
        val name = arguments?.getString("ARG_NAME")

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

        if (positionnn == 2) {
            positionnn = 0

            button.visibility = View.GONE
            BtnAddmore.visibility = View.GONE
            BtnUpdate.visibility = View.VISIBLE

            if (AppUtils().isInternet(requireContext())) {
                val service = ApiClient.client.create(ApiInterFace::class.java)
                val apiInterFace = service.editcollectdata(
                    collectDataId
                )

                apiInterFace.enqueue(object : Callback<Responseeditcollectdata> {
                    override fun onResponse(
                        call: Call<Responseeditcollectdata>,
                        response: Response<Responseeditcollectdata>
                    ) {
                        try {
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
                        } catch (e: Exception) {
                            AppUtils.logDebug(TAG, e.message.toString())
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
            } else {
                val list = db!!.getEditCollectData(collectDataServerId)


                dataa = EditCollectData(
                    collect_activity_id = list.collect_activity_id?.toInt(),
                    result_id = list.result_id?.toInt(),
                    sensor_id = list.sensor_id?.toInt(),
                    unit_id = list.unit_id?.toInt(),
                    value = list.new_value,
                    new_value = list.new_value,
                    duration = list.duration?.toInt(),
                    pack_id = list.pack_id?.toInt(),
                    id = list.serverid?.toInt()
                )

                if (dataa != null) {
                    setSpinner(collectActivity, sensonser, true)
                }
                myduration?.setText(dataa?.duration.toString())
            }


        } else {
            if (collectid != null) {
                handler.postDelayed({
                    setSpinner(collectActivity, sensonser, false)

                }, 1000)

            } else {
                handler.postDelayed({
                    setSpinner(collectActivity, sensonser, false)

                }, 1000)
            }
        }
        getActivity()?.setTitle("Update Pack")

        BtnUpdate.setOnClickListener {
            BtnUpdate.visibility = View.GONE

            if (ed_value.text.isNotEmpty()) {
                getValueId = ed_value.text.toString()
            } else if (dt_value.text.isNotEmpty()) {
                getValueId = dt_value.text.toString()
            } else {
                getValueId = spinner_value.getSelectedItem().toString()
            }
            if (getUnitId.isNullOrEmpty()) {
                getUnitId = dataa?.unit_id.toString()
            }
            if (getCollectId.isNullOrEmpty()) {
                getCollectId == dataa?.collect_activity_id.toString()
            }
            if (getSensorId.isNullOrEmpty()) {
                getSensorId == dataa?.sensor_id.toString()
            }
            if (getResultId.isNullOrEmpty()) {
                getResultId == dataa?.result_id.toString()
            }

            if (AppUtils().isInternet(requireContext())) {

                val service = ApiClient.client.create(ApiInterFace::class.java)

                AppUtils.logDebug(TAG,"CallMySingApi====="+"pacjid-"+packList?.id.toString()+
                        "Resultid=="+getResultId+"Collectid-"+ collectDataId+" " +
                        "edvalue="+getValueId+" spinnerunit"+ getUnitId+
                        " sensorid="+getSensorId+
                        " duration"+ myduration!!.text.toString()+"userid"
                        +MySharedPreference.getUser(requireContext())?.id.toString())
//
                val apiInterFace = service.updateCollectData(
                    MySharedPreference.getUser(requireContext())?.id.toString(),
                    packList?.id!!.toString(),
                    getResultId,
                    getValueId,
//                    dataa?.unit_id.toString(),
                    getUnitId,
//                    dataa?.sensor_id.toString(),
                    getSensorId,
                    myduration!!.text.toString(),
                    collectDataId
                )

                apiInterFace.enqueue(object : Callback<ResponseCollectAcitivityResultList> {
                    override fun onResponse(
                        call: Call<ResponseCollectAcitivityResultList>,
                        response: Response<ResponseCollectAcitivityResultList>
                    ) {
                        try {
                            if (response.body()?.error == false) {

                                Toast.makeText(
                                    requireContext(),
                                    "${response.body()?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                BtnUpdate.visibility = View.VISIBLE
                                val transaction: FragmentTransaction =
                                    activity!!.supportFragmentManager.beginTransaction()
                                val fragment = CollectDataFragement()

                                transaction.replace(R.id.container, fragment)

//                                val intent = Intent(activity, UpdatePackActivity::class.java)
//                                intent.putExtra("fragment", "1")
//                                startActivity(intent)
                                activity?.finish()

                            } else {
                                BtnUpdate.visibility = View.VISIBLE

                                Toast.makeText(
                                    requireContext(),
                                    "${response.body()?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                "${response.body()?.msg}",
                                Toast.LENGTH_SHORT
                            ).show()
                            AppUtils.logError(TAG, e.message.toString())
                        }

                    }

                    override fun onFailure(
                        call: Call<ResponseCollectAcitivityResultList>,
                        t: Throwable
                    ) {
                        try {
                            BtnUpdate.visibility = View.VISIBLE
                            AppUtils.logError(TAG, t.message.toString())
                        } catch (e: Exception) {
                            BtnUpdate.visibility = View.VISIBLE
                            AppUtils.logError(TAG, e.localizedMessage)

                        }
                    }

                })
            } else {
                if (ed_value != null) {
                    getValueId = ed_value.text.toString()
                } else if (dt_value != null) {
                    getValueId = ed_value.text.toString()
                } else {
                    getValueId = spinner_value.getSelectedItem().toString()
                }
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())


                duration = ed_duration?.text.toString()
                val collectdata = CollectData(
                    id = dataa?.id.toString(),
                    result_id = resultid.toString(),
                    collect_activity_id = collectId.toString(),
                    value = getValueId,
                    new_value = getValueId,
                    unit_id = getSensorId,
                    sensor_id = sensorId.toString(),
                    duration = duration,
                    serverid = dataa?.id.toString(),
                    pack_id = packList?.id.toString(),
                    updated_at = currentDate
                )
                AppUtils.logDebug(TAG, "btnUpdate CLick check datas" + collectdata.toString())

                val db = DbHelper(requireContext(), null)
                db.updateCollectDataOffline(collectdata, true)
            }
        }
        button.setOnClickListener {

            if (listmultipleData.size >= 1) {
                for (i in 0 until listmultipleData.size) {
                    objectmultipleData = listmultipleData.get(i)
                    callMyApi("Value", objectmultipleData!!)

                }

                listmultipleData.clear()
                objectmultipleData = null

            }
            else {

                if (collectActivity.selectedItemPosition == 0) {
                    Toast.makeText(
                        requireContext(),
                        "Please Select CollectActivity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else if (sensonser.selectedItemPosition == 0) {
                    Toast.makeText(requireContext(), "Please Select Sensor", Toast.LENGTH_SHORT).show()

                } else if (result?.selectedItemPosition == 0) {
                    Toast.makeText(
                        requireContext(),
                        "Please Select Result Activity",
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (valueSpinner?.selectedItemPosition == 0) {
                    Toast.makeText(requireContext(), "Please Select value", Toast.LENGTH_SHORT).show()

                }
                else if (unit?.selectedItemPosition == 0) {
                    Toast.makeText(requireContext(), "Please Select Unit", Toast.LENGTH_SHORT).show()

                }
                else if (valueSpinner?.selectedItemPosition == 0 && value?.text!!.isEmpty() && valuedate?.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Value is Required", Toast.LENGTH_SHORT).show()

                }
                else {
                    progressbar_collectnewdata.visibility = View.VISIBLE
                    button.visibility = View.GONE
                    btn_addmoreData.visibility = View.GONE

//            if (!listmultipleData.isNullOrEmpty()) {
                    if (ed_value.text.isNotEmpty()) {
                        edValue = ed_value.text.toString()
                    } else if (dt_value.text.isNotEmpty()) {
                        dtvalue = dt_value.text.toString()
                    } else {
                        spinnervalue = spinner_value.getSelectedItem().toString()
                    }
                    duration = ed_duration?.text.toString()




                    callStoreCollectDataAPi(
                        edValue,
                        dtvalue,
                        spinnervalue,
                        duration,
                        sensorId,
                        collectId,
                        resultid,
                        spinnerUnitId
                    )

                }
            }

        }
        BtnAddmore.setOnClickListener {
//            collectActivity

//            sensonser-
            if (collectActivity.selectedItemPosition == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please Select CollectActivity",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (sensonser.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please Select Sensor", Toast.LENGTH_SHORT).show()

            } else if (result?.selectedItemPosition == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please Select Result Activity",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (valueSpinner?.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please Select value", Toast.LENGTH_SHORT).show()

            } else if (unit?.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please Select Unit", Toast.LENGTH_SHORT).show()

            } else if (valueSpinner?.selectedItemPosition == 0 && value?.text!!.isEmpty() && valuedate?.text!!.isEmpty()) {
                Toast.makeText(requireContext(), "Value is Required", Toast.LENGTH_SHORT).show()

            } else {

                var valuee: String = ""
                 if (ed_value.text.isNotEmpty()) {
                    valuee = ed_value.text.toString()
                } else if (dt_value.text.isNotEmpty()) {
                    valuee = dt_value.text.toString()
                } else {
                    valuee = spinner_value.getSelectedItem().toString()
                }
                duration = ed_duration?.text.toString()
                recyclerview_addmultipledata?.layoutManager = LinearLayoutManager(requireContext())
                spinnerUnitNAme
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
                AppUtils.logDebug(TAG,"Multiple Data ==>"+listmultipleData.toString())

                adapterAddmultiple = AdapterAddmultiple(requireContext(), listmultipleData)
                recyclerview_addmultipledata.adapter = adapterAddmultiple

                myduration?.setText("")
                collectActivity.setSelection(0)
                result?.setSelection(0)
                unit?.setSelection(0)
                sensonser.setSelection(0)
                if (ed_value.text.isNotEmpty()) {
                    ed_value?.setText("")

                } else if (dt_value.text.isNotEmpty()) {
                    dt_value?.setText("")
                    dt_value.visibility=View.GONE
                    ed_value.visibility=View.VISIBLE

                } else {
                    valueSpinner?.setSelection(0)
                }
            }
        }
        return view
    }

    private fun callStoreCollectDataAPi(
        edValue: String,
        dtvalue: String,
        spinnervalue: String,
        duration: String,
        sensorId: Int,
        collectId: Int,
        resultid: Int,
        spinnerUnitId: Int
    ) {
        if (edValue.isNotEmpty()) {

            callMySingleApi(edValue,duration)


        } else if (dtvalue.isNotEmpty()) {
            callMySingleApi(dtvalue,duration)
        } else {
            callMySingleApi(spinnervalue,duration)

        }

    }

    private fun callMyApi(edValue: String, objectmultipleDataa: ListMultipleCollcetdata) {


        if (edValue != null) {

            if (collectId == null) {
                collectId = collectActivityList.get(0).id.toInt()
            }
            if (AppUtils().isInternet(requireContext())){
                var apiInterFace: Call<ResponseCollectAcitivityResultList>? = null


                val service = ApiClient.client.create(ApiInterFace::class.java)
                if (objectmultipleDataa != null) {
                    apiInterFace = service.storecollectdata(
                        packList?.id.toString(),
                        objectmultipleDataa.resultID!!,
                        objectmultipleDataa.activityID!!,
                        objectmultipleDataa.valueID!!,
                        objectmultipleDataa.valueID!!,
                        objectmultipleDataa.unitID!!,
                        objectmultipleDataa.sensorID!!,
                        objectmultipleDataa.duration!!,
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
                            btn_collectNewData.visibility = View.VISIBLE
                            btn_addmoreData.visibility = View.VISIBLE

                            progressbar_collectnewdata.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "${response.body()?.msg}",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(activity, UpdatePackActivity::class.java)
                            intent.putExtra("fragment", "1")
                            activity?.finish()

                        } else {
                            btn_collectNewData.visibility = View.VISIBLE
                            btn_addmoreData.visibility = View.VISIBLE
                            Toast.makeText(
                                requireContext(),
                                "Failed To add Collect Data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseCollectAcitivityResultList>,
                        t: Throwable
                    ) {
                        progressbar_collectnewdata?.visibility = View.GONE
                        try {
                            btn_collectNewData.visibility = View.VISIBLE
                            btn_addmoreData.visibility = View.VISIBLE
                            AppUtils.logError(TAG, t.message.toString())
                        } catch (e: Exception) {
                            btn_collectNewData.visibility = View.VISIBLE
                            btn_addmoreData.visibility = View.VISIBLE
                            AppUtils.logError(TAG, e.localizedMessage)

                        }
                    }

                })
            }
            else{

                val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
                val currentDate = sdf.format(Date())

                @SuppressLint("Range")
                val lastvalue = db?.getLastofCollectData()
                val serverid = lastvalue?.toInt()!! + 1

//
                val collectdata = CollectData(
                    id = packList?.id.toString(),
                    result_id = resultid.toString(),
                    collect_activity_id = collectId.toString(),
                    value = edValue,
                    new_value = edValue,
                    unit_id = getSensorId,
                    sensor_id = sensorId.toString(),
                    duration = duration,
                    date = currentDate,
                    serverid = serverid.toString(),
                    created_at=currentDate
                )


             val isSucces=   db!!.addNewCollectDataOffline(collectdata, false)
                if (isSucces){
                    val intent = Intent(activity, UpdatePackActivity::class.java)
                    intent.putExtra("fragment", "1")
                    activity?.finish()
                }

            }



        }

    }

    private fun callMySingleApi(edValue: String, duration: String) {


        if (edValue != null) {

            if (collectId == null) {
                collectId = collectActivityList.get(0).id.toInt()
            }

            if (AppUtils().isInternet(requireContext())) {

                var apiInterFace: Call<ResponseCollectAcitivityResultList>? = null
                val service = ApiClient.client.create(ApiInterFace::class.java)

AppUtils.logDebug(TAG,"CallMySingApi====="+"pacjid-"+packList?.id.toString()+
"Resultid=="+resultid.toString()+"Collectid-"+ collectId.toString()+" edvalue="+edValue+" spinnerunit"+getSensorId.toString()+
" sensorid="+sensorId.toString()+" duration"+ duration +"userid"+MySharedPreference.getUser(requireContext())?.id.toString())

                apiInterFace = service.storecollectdata(
                    packList?.id.toString(), resultid.toString(), collectId.toString(),
                    edValue, edValue, getSensorId.toString(), sensorId.toString(), duration,
                    MySharedPreference.getUser(requireContext())?.id.toString(),
                )

                apiInterFace.enqueue(object : Callback<ResponseCollectAcitivityResultList> {
                    override fun onResponse(
                        call: Call<ResponseCollectAcitivityResultList>,
                        response: Response<ResponseCollectAcitivityResultList>
                    ) {
                        try {
                            if (response.body()?.error == false) {
                                btn_collectNewData.visibility = View.VISIBLE
                                btn_addmoreData.visibility = View.VISIBLE

                                progressbar_collectnewdata.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    "${response.body()?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                UpdatePackActivity().finishAffinity()
                                val intent = Intent(activity, UpdatePackActivity::class.java)
                                intent.putExtra("fragment", "1")
                                activity?.finish()

                            } else {
                                Toast.makeText(requireContext(), "Failed To add", Toast.LENGTH_LONG)
                                    .show()
                            }
                        } catch (e: Exception) {
                            AppUtils.logError(TAG, e.localizedMessage)

                        }

                    }

                    override fun onFailure(
                        call: Call<ResponseCollectAcitivityResultList>,
                        t: Throwable
                    ) {
                        progressbar_collectnewdata?.visibility = View.GONE
                        try {
                            btn_collectNewData.visibility = View.VISIBLE
                            btn_addmoreData.visibility = View.VISIBLE
                            AppUtils.logError(TAG, t.message.toString())
                        } catch (e: Exception) {
                            btn_collectNewData.visibility = View.VISIBLE
                            btn_addmoreData.visibility = View.VISIBLE
                            AppUtils.logError(TAG, e.localizedMessage)

                        }
                    }

                })
            }
            else {

                val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
                val currentDate = sdf.format(Date())

                @SuppressLint("Range")
                val lastvalue = db?.getLastofCollectData()
                val serverid = lastvalue?.toInt()!! + 1


                val collectdata = CollectData(
                    id = packList?.id.toString(),
                    result_id = resultid.toString(),
                    collect_activity_id = collectId.toString(),
                    value = edValue,
                    new_value = edValue,
                    unit_id = getSensorId,
                    sensor_id = sensorId.toString(),
                    duration =duration,
                    date = currentDate,
                    serverid = serverid.toString(),
                    created_at=currentDate,
                )


             val isSucces=   db!!.addNewCollectDataOffline(collectdata, false)
                if (isSucces){
                    val intent = Intent(activity, UpdatePackActivity::class.java)
                    intent.putExtra("fragment", "1")
                    activity?.finish()
                }

            }


        }

    }

    private fun setSpinner(collectActivity: Spinner, sensonser: Spinner, boolean: Boolean) {
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
        if (boolean) {

            if (dataa?.sensor_id != null) {
                for (i in 0 until sensorlist.size) {

                    if (dataa?.sensor_id.toString() == sensorlist.get(i).id) {

                        sensonser.setSelection(i)
                    }
                }
            }


            if (dataa?.collect_activity_id != null) {
                myduration?.setText(dataa?.duration.toString())



                for (i in 0 until collectid.size) {

                    if (dataa?.collect_activity_id.toString() == collectid.get(i)) {


                        collectActivity.setSelection(i)
                    }
                }

            }

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
                        Handler(Looper.getMainLooper()).postDelayed({
                            callApi(collectId.toString())

                        }, 500)
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
//        callApi("17")


    }

    private fun callApi(collectactivity_id: String) {

//        ApiClient.client.create(ApiInterFace::class.java).collectAcitivityResultList(
//            MySharedPreference.getUser(requireContext())?.id.toString(),
//            collectactivity_id
//        ).enqueue(this)
        if (collectactivity_id != "0") {
            val resultList = db?.getCollectActivityResult(collectactivity_id)
            collectactivitylistName.clear()
            collectactivitylist.clear()
            collectactivitylistid.clear()
            collectactivitylist.add(
                CollectAcitivityResultList(
                    "0", "Select", null,
                    null, null
                )
            )
            collectactivitylistName.add("Select")
            collectactivitylistid.add("0")

            for (i in 0 until resultList!!.size) {
                val item = resultList.get(i)
                collectactivitylist.add(
                    CollectAcitivityResultList(
                        item.id.toString(),
                        item.result_name!!, item.type_id, item.unit_id, item.list_id
                    )
                )

                collectactivitylistName.add(item.result_name)
                collectactivitylistid.add(item.id.toString())
            }


        }

        if (collectactivitylistName != null) {
            setSpinnerResult(collectactivitylistName)
        }

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

            if (dataa?.result_id != null) {


                for (i in 0 until collectactivitylistid.size) {
                    if (dataa?.result_id.toString() == collectactivitylistid.get(i)) {

                        result?.setSelection(i)
                    }
                }
            }
            progressbar_collectnewdata.visibility = View.GONE
        } catch (e: Exception) {
            AppUtils.logDebug(TAG, e.localizedMessage.toString())

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
                        val item = collectactivitylist.get(i)
                        resultid = item.id!!.toInt()
                        resultNAME = item.result_name!!
                        callResultValueApi(
                            resultid.toString(), item.typeid.toString(),
                            item.unitId.toString(), item.listId
                        )

                        getResultId = collectactivitylistid.get(i)

                    }
                }

            }

        }
    }

    //    private fun callResultValueApi(resultid: String) {
    private fun callResultValueApi(
        resultid: String,
        value: String,
        unitid: String,
        listId: String?
    ) {
        val id = resultid.toInt() + 1


        var untiLst = db?.getUnitList(unitid)

        var unitList: ArrayList<UnitList>? = null
        unitList = ArrayList<UnitList>()

        var unitListname: ArrayList<String>? = null
        var unitListId: ArrayList<String>? = null

        unitList = ArrayList()
        unitListname = ArrayList()
        unitListId = ArrayList()



        unitList.add(UnitList("0", "Select"))
        unitListname.add("Select")
        unitListId.add("Select")



        for (i in 0 until untiLst!!.size) {
            val item = untiLst.get(i)
            unitList.add(UnitList(item.id.toString(), item.name!!))
            unitListname.add(item.name)
            unitListId.add(item.id.toString())
        }


//       setValueSpinner(unitList, unitListname, listId!!, unitListId)
        try {
            if (value == "date" || value == "Date" || value == "DATE") {

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

            } else if (value == "numeric" || value == "text" || value == "Numeric" || value == "Text") {
                ed_value?.visibility = View.VISIBLE
                dt_value?.visibility = View.GONE
                spinner_value?.visibility = View.GONE
                ed_value?.setText(dataa?.value)
            } else if (value == "list" || value == "List" || value == "table" || value == "Table") {

                val valueListname = ArrayList<String>()
                val valueListid = ArrayList<String>()


                val valuelist = db?.getListChoice(value)

                valueListname.add("Select")
                valueListid.add("0")

                for (i in 0 until valuelist!!.size) {
                    val item = valuelist.get(i)
                    valueListname.add(item.name!!)
                    valueListid.add(item.id.toString())
                }
                setUnitSpinner(valueListid, valueListname)

                spinner_value?.visibility = View.VISIBLE
                ed_value?.visibility = View.GONE
                dt_value.visibility = View.GONE
            }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, unitListname
            )
            unit?.adapter = adapter
            setunitListner(unitListname,unitList)
            if (dataa?.unit_id != null) {

                for (i in 0 until unitListname.size) {
                    if (dataa?.unit_id.toString() == unitListId.get(i)) {
                        unit?.setSelection(i)
                    }
                }
            }
        } catch (e: Exception) {
            AppUtils.logDebug(TAG, e.localizedMessage.toString())
        }


//        val service = ApiClient.getClient()!!.create(ApiInterFace::class.java)
//        val apiInterFace = service.collectAcitivityResultValue(
//            MySharedPreference.getUser(requireContext())?.id.toString(),
//            id.toString()
//        )
//
//        apiInterFace.enqueue(object : Callback<ResponsecollectAcitivityResultValue> {
//            override fun onResponse(
//                call: Call<ResponsecollectAcitivityResultValue>,
//                response: Response<ResponsecollectAcitivityResultValue>
//            ) {
//                if (response.body()?.error == false) {
//                    val basrespose: ResponsecollectAcitivityResultValue = Gson().fromJson(
//                        Gson().toJson(response.body()),
//                        ResponsecollectAcitivityResultValue::class.java
//                    )
//                    val data: Data = basrespose.data
//                    val unitList = ArrayList<UnitList>()
//                    val unitListname = ArrayList<String>()
//                    val unitListId = ArrayList<String>()
//                    val valueListname = ArrayList<String>()
//                    val valueListid = ArrayList<String>()
//
//
//
//                    unitList.add(UnitList("0", "Select"))
//                    unitListname.add("Select")
//                    unitListId.add("Select")
//
//                    valueListname.add("Select")
//                    valueListid.add("0")
//
//                    basrespose.unit_list.forEach {
//                        unitList.add(it)
//                        unitListname.add(it.name!!)
//                        unitListId.add(it.id.toString())
//                    }
//                    data.list_array.forEach {
//                        valueListname.add(it.name!!)
//                        valueListid.add(it.id!!)
//
//                    }
//                    if (!data.list_array.isNullOrEmpty()) {
//                        setUnitSpinner(valueListid, valueListname)
//
//                    }
//
//                    setValueSpinner(unitList, unitListname, data.toString(), unitListId)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponsecollectAcitivityResultValue>, t: Throwable) {
//                try {
//                    AppUtils.logError(TAG, t.message.toString())
//                } catch (e: Exception) {
//                    AppUtils.logError(TAG, e.localizedMessage)
//
//                }
//            }
//
//        })
    }

    private fun setunitListner(unitListname: ArrayList<String>, unitList: ArrayList<UnitList>) {
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
                    val item=unitListname.get(i)
                    if (i == position) {
                        spinnerUnitNAme = unitList.get(position).name.toString()

                        spinnerUnitId = unitList.get(i).id!!.toInt()

                        AppUtils.logDebug(TAG,"spinnerunitid++++"+spinnerUnitNAme.toString()
                        )

//                        callResultValueApi(resultid.toString())
                        getSensorId = spinnerUnitId.toString()

                    }
                }

            }

        }
    }

    private fun setUnitSpinner(
        valueListid: ArrayList<String>, valueListname: ArrayList<String>
    ) {


        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            valueListname
        )
        spinner_value?.adapter = adapter

    }

    private fun setValueSpinner(
        unitList: ArrayList<UnitList>,
        unitListname: ArrayList<String>,
        data: String,
        unitListId: ArrayList<String>
    ) {
        try {
            if (data == "date") {

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

            } else if (data == "numeric" || data == "text") {
                ed_value?.visibility = View.VISIBLE
                dt_value?.visibility = View.GONE
                spinner_value?.visibility = View.GONE
                ed_value?.setText(dataa?.value)
            } else if (data == "list" || data == "List" || data == "table" || data == "Table") {


                spinner_value?.visibility = View.VISIBLE
                ed_value?.visibility = View.GONE
                dt_value.visibility = View.GONE
            }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, unitListname
            )
            unit?.adapter = adapter
            if (dataa?.unit_id != null) {

                for (i in 0 until unitListname.size) {
                    if (dataa?.unit_id.toString() == unitListId.get(i)) {
                        unit?.setSelection(i)
                    }
                }
            }
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
                    val item=unitListname.get(i)
                    if (i == position) {
                        spinnerUnitNAme = unitList.get(position).name.toString()

                        spinnerUnitId = unitList.get(i).id!!.toInt()

                        AppUtils.logDebug(TAG,"spinnerunitid++++"+spinnerUnitNAme.toString()
                        )

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

