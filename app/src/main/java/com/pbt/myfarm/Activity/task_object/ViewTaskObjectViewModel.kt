package com.pbt.myfarm.Activity.task_object


import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.BaseHttpResponse
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.BaseTaskFunction
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewTaskObjectViewModel (val activity: Application) : AndroidViewModel(activity) ,
    retrofit2.Callback<BaseHttpResponse>{


    var taskObjectList = MutableLiveData<List<TaskObject>>()
    var taskTaskFunctionList = MutableLiveData<List<ListTaskFunctions>>()
    var deleteHttpResponse = MutableLiveData<HttpResponse>()
    var deleteID = MutableLiveData<String>()
    var selectedTaskFUnctionID = MutableLiveData<String>("")
    var checkStatusobject=MutableLiveData<HttpResponse>()

    init {
        taskObjectList = MutableLiveData<List<TaskObject>>()
        checkStatusobject=MutableLiveData<HttpResponse>()
    }

    fun getObjectsList(taskID :String) {
        ApiClient.client.create(ApiInterFace::class.java).getTaskObject(taskID).enqueue(this)
    }
    fun checkStatus(taskID: String){

        val apiInterface = ApiClient.client.create(ApiInterFace::class.java)

        val call: Call<HttpResponse> = apiInterface.checkTaskStatus(taskID)
        call.enqueue(object :Callback<HttpResponse>{
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                if (response.isSuccessful && response.body()?.error==false){
                    val baseHttpResponse=Gson().fromJson<HttpResponse>(Gson().toJson(response.body()),HttpResponse::class.java)
                    checkStatusobject.postValue(baseHttpResponse)
                }
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                println(t.localizedMessage.toString())
            }

        })

    }

    fun getTaskFunctionList(taskID :String,userId: String) {


        val apiInterface = ApiClient.client.create(ApiInterFace::class.java)

        val call: Call<HttpResponse> = apiInterface.taskFunctionList(userId,taskID)

        call.enqueue(object : Callback<HttpResponse> {
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                if(response.isSuccessful && !response.body()!!.error){
                    val baseTaskFunction: BaseTaskFunction =
                        Gson().fromJson(Gson().toJson(response.body()!!.data), BaseTaskFunction::class.java)
                    if (!baseTaskFunction.listTask.isNullOrEmpty()) {
                        Log.d("##1234650"," getTaskFunctionList Response  ==> ")
                        taskTaskFunctionList.value= emptyList()
                        taskTaskFunctionList.postValue(baseTaskFunction.listTask)
                    }
                }
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
            }
        })
    }

    fun deleteTaskObject(taskID: String, context: Context) {
        showAlertDialog(context,taskID)
    }

    private fun showAlertDialog(context: Context, taskID: String) {
        AlertDialog.Builder(context)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    val apiInterface = ApiClient.client.create(ApiInterFace::class.java)

                    val call: Call<HttpResponse> = apiInterface.deleteTaskObject(taskID)

                    call.enqueue(object : Callback<HttpResponse> {
                        override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                            if(response.isSuccessful && !response.body()!!.error){
                                deleteID.postValue(taskID)
                                deleteHttpResponse.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                        }
                    })
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    override fun onResponse(call: Call<BaseHttpResponse>, response: Response<BaseHttpResponse>) {

        if(response.isSuccessful && response.body()?.data?.task_objects?.isNullOrEmpty() == false) {
            taskObjectList.postValue(emptyList())
            taskObjectList.postValue(response.body()?.data?.task_objects)
        }else{
            taskObjectList.postValue(emptyList())
        }
    }

    override fun onFailure(call: Call<BaseHttpResponse>, t: Throwable) {
        println("Get VierwTAskoBject list response Failure "+t.localizedMessage.toString())

    }




}
