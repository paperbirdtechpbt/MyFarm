package com.pbt.myfarm.Activity.TaskFunctions.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.BaseTaskFunction
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList


class ViewModelTaskFunctionality(val activity: Application) : AndroidViewModel(activity), retrofit2.Callback<HttpResponse> {

    val TAG = "ViewModelTaskFunctionality"
    var context: Context? = null
    var listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()

    init {
        listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
    }


    fun onTaskFunctionList(context: Context, updateTaskId: String, userID: String, updateTaskconfigId: String) {

        Log.d("Apicall","Param updateTsk : $updateTaskId  UserID $userID ")

        if (AppUtils().isInternet(context)){
                    ApiClient.client.create(ApiInterFace::class.java)
            .taskFunctionList(
               userID,
                updateTaskId
            ).enqueue(this)
        }
        else{
            val db=DbHelper(context,null)
            val list=     db.getTaskFunctionList(updateTaskconfigId)
            AppUtils.logDebug(TAG,"ontaskfunction list --"+list.toString())
            val listtask=ArrayList<ListTaskFunctions>()
            listtask.add(ListTaskFunctions("0","Select"))

            list.forEach{
                AppUtils.logDebug(TAG,"ontaskfunction list --"+Gson().toJson(it).toString())

                listtask.add(ListTaskFunctions(it.id.toString(),it.id.toString(),it.name,it.privilege))
            }
            listTaskFuntions.value = listtask
        }
    }

    override fun onResponse(
        call: Call<HttpResponse>,
        response: Response<HttpResponse>
    ) {
        if (response.body()?.error == false) {
            if(response.body() != null){
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
    }
}