package com.pbt.myfarm.Activity.task_object

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.BaseHttpResponse
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.OffLineSyncModel
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewTaskObjectViewModel (val activity: Application) : AndroidViewModel(activity) , retrofit2.Callback<BaseHttpResponse>{


    var taskObjectList = MutableLiveData<List<TaskObject>>()
    var deleteHttpResponse = MutableLiveData<HttpResponse>()
    var deleteID = MutableLiveData<String>()


    init {
        taskObjectList = MutableLiveData<List<TaskObject>>()
    }

    fun getObjectsList(taskID :String) {
        ApiClient.client.create(ApiInterFace::class.java).getTaskObject(taskID).enqueue(this)
    }

    fun deleteTaskObject(taskID :String) {
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
    }

    override fun onResponse(call: Call<BaseHttpResponse>, response: Response<BaseHttpResponse>) {

        if(response.isSuccessful && response.body()?.data?.task_objects?.isNullOrEmpty() == false) {
            taskObjectList.postValue(response.body()?.data?.task_objects)
        }else{
            taskObjectList.postValue(emptyList())
        }
    }

    override fun onFailure(call: Call<BaseHttpResponse>, t: Throwable) {
        TODO("Not yet implemented")
    }


}
