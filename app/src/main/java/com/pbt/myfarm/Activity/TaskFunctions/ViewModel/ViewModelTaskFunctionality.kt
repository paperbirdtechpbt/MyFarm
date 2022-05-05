package com.pbt.myfarm.Activity.TaskFunctions.ViewModel

import android.app.Application
import android.content.Context

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.TaskFunctions.GetResponseTaskFunctionList
import com.pbt.myfarm.Activity.TaskFunctions.ListOfTaskFunctions
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.Activity.TaskFunctions.ResponseTaskFunctionaliyt
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ViewModelTaskFunctionality(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<GetResponseTaskFunctionList> {
    private val CAMERA_REQUEST = 1888
    private val GELARY_REQUEST = 1088
    private val MY_CAMERA_PERMISSION_CODE = 1001
    val TAG = "ViewModelTaskFunctionality"
    var context: Context? = null
    var listTaskFuntions = MutableLiveData<List<ListOfTaskFunctions>>()

    init {
        listTaskFuntions = MutableLiveData<List<ListOfTaskFunctions>>()
    }


    fun onTaskFunctionList(context: Context, updateTaskId: String) {
        if (AppUtils().isInternet(context)){
                    ApiClient.client.create(ApiInterFace::class.java)
            .taskFunctionList(
                MySharedPreference.getUser(context)?.id.toString(),
                updateTaskId
            ).enqueue(this)
        }
        else{
            val db=DbHelper(context,null)
            val list=     db.getTaskFunctionList(updateTaskId)
            AppUtils.logDebug(TAG,"ontaskfunction list --"+list.toString())
            val listtask=ArrayList<ListOfTaskFunctions>()
            listtask.add(ListOfTaskFunctions("0",name = "Select"))

            list.forEach{
                listtask.add(ListOfTaskFunctions(id = it.id.toString(),name = it.name))
            }
            listTaskFuntions.value = listtask
        }
    }

    override fun onResponse(
        call: Call<GetResponseTaskFunctionList>,
        response: Response<GetResponseTaskFunctionList>
    ) {
        try {
            if (response.body()?.error == false) {
                val baseresponse: GetResponseTaskFunctionList = Gson().fromJson(
                    Gson().toJson(response.body()),
                    GetResponseTaskFunctionList::class.java
                )
                val list = ArrayList<ListOfTaskFunctions>()
                if (baseresponse != null) {
                    baseresponse.data.forEach { item ->
                        list.add(item)
                    }
                    if (!list.isNullOrEmpty()) {
                        listTaskFuntions.value = list
                        AppUtils.logDebug(TAG, "resposenselist ==>" + Gson().toJson(list))

                    }

                }
            }
        }
        catch (e:Exception){
           AppUtils.logError(TAG,"$TAG "+e.message.toString())
        }

    }

    override fun onFailure(call: Call<GetResponseTaskFunctionList>, t: Throwable) {
        try {
            AppUtils.logError(TAG,"$TAG "+t.message.toString())

        }
        catch (e:Exception){
            AppUtils.logError(TAG,"$TAG "+e.message.toString())

        }
    }

}