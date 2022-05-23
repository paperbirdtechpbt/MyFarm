package com.pbt.myfarm.Activity.TaskFunctions.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.budiyev.android.codescanner.*
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.BaseTaskFunction
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModelTaskFunctionality(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<HttpResponse> {

    val TAG = "ViewModelTaskFunctionality"
    var context: Context? = null
    var listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
    var checkstatus = MutableLiveData<HttpResponse>()
    var codeScannerView:CodeScannerView?=null
    var codeScanner:CodeScanner?=null


    init {
        listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
        checkstatus = MutableLiveData<HttpResponse>()
    }


    fun onTaskFunctionList(
        taskConfigID: String,
        context: Context,
        updateTaskId: String,
        userID: String
    ) {

        Log.d("Apicall", "Param updateTsk : $updateTaskId  UserID $userID ")

        if (AppUtils().isInternet(context)) {
            ApiClient.client.create(ApiInterFace::class.java)
                .taskFunctionList(
                    userID,
                    updateTaskId
                ).enqueue(this)
        } else {
            val db = DbHelper(context, null)
            val taskFunctionList = ArrayList<ListTaskFunctions>()
            taskFunctionList.add(ListTaskFunctions("0", "Select"))
            db.getTaskFunctionList(taskConfigID).let {
                taskFunctionList.addAll(it)
            }
            listTaskFuntions.value = taskFunctionList
        }
    }
    fun oncheckStatuApi(context: Context,taskid:String){
        val apiInterFace=ApiClient.client.create(ApiInterFace::class.java)
        val call=apiInterFace.checkTaskStatus(taskid)
        call.enqueue(object :Callback<HttpResponse>{
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                if (response.isSuccessful && response.body()?.error==false){
                    val basresponse=Gson().fromJson<HttpResponse>(Gson().toJson(response.body()),HttpResponse::class.java)
                    checkstatus.value=basresponse
                }
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
            }

        })
    }

    override fun onResponse(
        call: Call<HttpResponse>,
        response: Response<HttpResponse>
    ) {
        if (response.body()?.error == false) {
            if (response.body() != null) {
                val response: HttpResponse = Gson().fromJson(Gson().toJson(response.body()), HttpResponse::class.java)
                if (response != null) {
                    val baseTaskFunction: BaseTaskFunction =
                        Gson().fromJson(Gson().toJson(response.data), BaseTaskFunction::class.java)
                    if (!baseTaskFunction.listTask.isNullOrEmpty()) {
                        listTaskFuntions.value = baseTaskFunction.listTask
                        AppUtils.logDebug(
                            TAG,
                            "resposenselist ==>" + Gson().toJson(baseTaskFunction.listTask)
                        )
                    }
                }
            }
        }
    }

    override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
    }


    fun executeTask(mTaskID: String, mFunctionID: String, mUserID: String, mFieldID: String, db : DbHelper,mTimeZoneId :String) {
         val choices = db.getListChoiceQuery(mTimeZoneId)
        val slite  = choices.name
    }
    fun openScanner(context: Context){
codeScanner= CodeScanner(context, codeScannerView!!)
        codeScanner!!.camera=CodeScanner.CAMERA_BACK
        codeScanner!!.formats=CodeScanner.ALL_FORMATS
        codeScanner!!.autoFocusMode=AutoFocusMode.SAFE
        codeScanner!!.scanMode=ScanMode.SINGLE
        codeScanner!!.isAutoFocusEnabled=true
        codeScanner!!.isFlashEnabled=false
        codeScanner!!.startPreview()

        codeScanner!!.decodeCallback= DecodeCallback {
            (context as Activity).runOnUiThread(Runnable {
                Toast.makeText(context, "${it.text}", Toast.LENGTH_SHORT).show()
//          AppUtils.logDebug(TAG,"Sucesss+${it.text}")
                codeScanner!!.stopPreview()
                codeScannerView!!.visibility=View.GONE
            })

        }
        codeScanner!!.errorCallback= ErrorCallback {
            Toast.makeText(context, "ERRPR ${it.message}", Toast.LENGTH_SHORT).show()

        }
//        codeScannerView!!.setOnClickListener{
//        }

    }
    fun openZingScanner( context: Context){

    }

}