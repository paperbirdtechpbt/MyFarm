package com.pbt.myfarm.Activity.TaskFunctions.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent

import android.util.Log
import android.view.View
import android.widget.*

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.hbisoft.pickit.PickiTCallbacks
import com.pbt.myfarm.Activity.Event.ResponseScanCodeForTaskFunction
import com.pbt.myfarm.Activity.Event.ScannedPersonData
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.Activity.TaskFunctions.ProgressRequestBody
import com.pbt.myfarm.Activity.task_object.ViewTaskObjectActivity
import com.pbt.myfarm.BaseHttpResponse
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.BaseTaskFunction
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ResponseTaskExecution
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_task_function.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ViewModelTaskFunctionality(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<HttpResponse>,  ProgressRequestBody.UploadCallback {

    val TAG = "ViewModelTaskFunctionality"
    var context: Context? = null
    var listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
    var checkstatus = MutableLiveData<HttpResponse>()
    val path: String? = null
    var edScannedData: EditText? = null
    var id = MutableLiveData<Int>(0)
    var progressVideo:ProgressBar?=null
   var btnSubmit: Button?=null
     var mprogressCircular: CircularProgressIndicator? = null
     var mprogressCircularLabel: TextView? = null
    var taskObjectList = MutableLiveData<List<TaskObject>>()



    init {
        listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
        checkstatus = MutableLiveData<HttpResponse>()
        taskObjectList = MutableLiveData<List<TaskObject>>()

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

    fun oncheckStatuApi(context: Context, taskid: String) {
        val apiInterFace = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiInterFace.checkTaskStatus(taskid)
        call.enqueue(object : Callback<HttpResponse> {
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                if (response.isSuccessful && response.body()?.error == false) {
                    val basresponse = Gson().fromJson<HttpResponse>(
                        Gson().toJson(response.body()),
                        HttpResponse::class.java
                    )
                    checkstatus.value = basresponse
                }
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                println(t.message.toString())
            }

        })
    }

    override fun onResponse(
        call: Call<HttpResponse>,
        response: Response<HttpResponse>
    ) {
        if (response.body()?.error == false) {
            if (response.body() != null) {
                val response: HttpResponse =
                    Gson().fromJson(Gson().toJson(response.body()), HttpResponse::class.java)
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
        println(t.message.toString())
    }


    fun executeTask(
        mTaskID: String,
        mFunctionID: String,
        mUserID: String,
        mFieldID: String,
        db: DbHelper,
        mTimeZoneId: String
    ) {
        val choices = db.getListChoiceQuery(mTimeZoneId)
        val slite = choices.name
    }

    fun getFileSize(fileVideo: File): Long {
        val sizeinBytes = fileVideo.length()
        val fileSizeInKB = sizeinBytes / 1024
        val fileSizeInMB = fileSizeInKB / 1024
        return fileSizeInMB

    }

    fun callApiForGetIDofPersonScanned(
        context: Context,
        selectedFunctionId: Int,
        responsedata: String
    ) {
        val userid = MySharedPreference.getUser(context)?.id
        val apiInterFace = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiInterFace.scanCodeTaskFunction(
            responsedata, selectedFunctionId.toString(),
            userid.toString()
        )
        call.enqueue(object : Callback<ResponseScanCodeForTaskFunction> {
            override fun onResponse(
                call: Call<ResponseScanCodeForTaskFunction>,
                response: Response<ResponseScanCodeForTaskFunction>
            ) {

                if (response.body()?.error==false) {
                    btnSubmit?.visibility=View.VISIBLE
                   val data= Gson().fromJson(Gson().toJson(response.body()!!.data),ScannedPersonData::class.java )
                    id.postValue(data?.id)
                    progressVideo?.visibility= View.GONE

                    edScannedData?.setText(data.name)
                } else {
                    btnSubmit?.visibility=View.GONE
                    progressVideo?.visibility= View.GONE
                    Toast.makeText(context, response.body()?.msg.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<ResponseScanCodeForTaskFunction>, t: Throwable) {
                btnSubmit?.visibility=View.GONE

                progressVideo?.visibility= View.GONE
                AppUtils.logError(TAG, t.localizedMessage)
            }

        })

    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    fun uploadFile(
        context: Context,
        mTaskID: String,
        mFunctionID: String,
        mUserID: String,
        mFieldID: String,
        fileVideo: File?,
        progressCircular: CircularProgressIndicator?,
        progressbarTaskexecute: ProgressBar?,
        progressCircularLabel: TextView?,
        btnExecute: Button?,
        istaskFunctionUpdate: Boolean
    ) {
        this.mprogressCircular=progressCircular
        this.mprogressCircularLabel=progressCircularLabel
        val tag="ViewModelTaskFunctionility"
        val service = ApiClient.client.create(ApiInterFace::class.java)

        val body = if (fileVideo?.let { it1 -> AppUtils().checkImageFile(it1) } == true)
            fileVideo?.let { it1 -> ProgressRequestBody(it1, "image", this) }
        else
            fileVideo?.let { it1 -> ProgressRequestBody(it1, "file", this) }

        val dataVideo: MultipartBody.Part? =
            body?.let {
                MultipartBody.Part.createFormData(
                    "file",
                    fileVideo!!.name,
                    it
                )
            }
        val taskID = AppUtils.paramRequestTextBody(mTaskID)
        val functionID = AppUtils.paramRequestTextBody(mFunctionID)
        val userID = AppUtils.paramRequestTextBody(mUserID)
        val fieldID = AppUtils.paramRequestTextBody(mFieldID)

        val apiInterFace = service.uploadFile(dataVideo, taskID, functionID, userID, fieldID)
        apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
            override fun onResponse(
                call: Call<ResponseTaskExecution>,
                response: Response<ResponseTaskExecution>
            ) {
                AppUtils.logDebug(tag, response.body().toString())
                val message = response.body()?.msg.toString()
                if (response.body()?.error == false) {
                    progressbarTaskexecute?.visibility = View.GONE


                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
                    progressbarTaskexecute?.visibility = View.GONE
                    btnExecute?.visibility = View.VISIBLE
                    if (istaskFunctionUpdate) {
                        context.startActivity(
                            Intent(
                                context,
                                ViewTaskObjectActivity::class.java
                            )
                        )
                    }
                    showToast(context,message)

                    (context as Activity).finish()
                } else {
                    progressbarTaskexecute?.visibility = View.GONE
                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
                    progressbarTaskexecute?.visibility = View.GONE

                    btnExecute?.visibility = View.VISIBLE
                    showToast(context,message)


                }
            }

            override fun onFailure(call: Call<ResponseTaskExecution>, t: Throwable) {
                progressbarTaskexecute?.visibility = View.GONE

                try {
                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
                    btnExecute?.visibility = View.VISIBLE
                    AppUtils.logError(tag, t.message.toString())
                } catch (e: Exception) {
                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
                    btnExecute?.visibility = View.VISIBLE
                    AppUtils.logError(tag, t.message.toString())

                }
                showToast(context,"Failed")


            }

        })

    }

    override fun onProgressUpdate(percentage: Int) {
        mprogressCircular?.setProgressCompat(percentage, true)
        mprogressCircularLabel?.text = "$percentage%"
    }

    fun getTaskObjectList( taskId: String) {
        val apiInterFace=ApiClient.client.create(ApiInterFace::class.java)
        val call=apiInterFace.getTaskObject(taskId)

        call.enqueue(object :Callback<BaseHttpResponse>{
            override fun onResponse(call: Call<BaseHttpResponse>, response: Response<BaseHttpResponse>) {

                if(response.body()?.error==false) {
                    taskObjectList.postValue(emptyList())

                    val list=ArrayList<TaskObject>()

response.body()?.data?.task_objects?.forEach { it ->
    if((it.function=="PACK" || it.function=="CREATE_PACK") && it.status==null){
        list.add(it)
    }


}
                    taskObjectList.postValue(list)
                }else{
                    taskObjectList.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<BaseHttpResponse>, t: Throwable) {
                println("Get VierwTAskoBject list response Failure "+t.localizedMessage.toString())

            }
        })


    }


}