package com.pbt.myfarm.Activity.SelectConfigType


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.HttpResponse.ConfigResponse
import com.pbt.myfarm.ConfigTaskList
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.TaskConfig
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference

import retrofit2.Call
import retrofit2.Response


class SelectConfigViewModel(var activity: Application):AndroidViewModel(activity),
    retrofit2.Callback<ConfigResponse> {
    companion object {
        const val TAG: String = "SelectConfigViewModel"
    }
//    var configlist = MutableLiveData<List<ConfigTaskList>>()
    var configlist = MutableLiveData<List<TaskConfig>>()
    var progressbar:ProgressBar?=null

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null

    init {

//        configlist = MutableLiveData<List<ConfigTaskList>>()
        configlist = MutableLiveData<List<TaskConfig>>()

    }

    fun onConfigTypeRequest(context: Context) {
        if (AppUtils().isInternet(context)){
                    ApiClient.client.create(ApiInterFace::class.java)
            .taskConfigList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
        }
        else{
            val db=DbHelper(context,null)
            val list=db.getTaskConfigList(MySharedPreference.getUser(context)?.id.toString())
            configlist.value = list
        }
    }

    override fun onResponse(call: Call<ConfigResponse>, response: Response<ConfigResponse>) {
        try{
            if (response.body()?.error == false) {

                configlist.value = emptyList()
                val baseList : ConfigResponse =  Gson().fromJson(
                    Gson().toJson(response.body()),
                    ConfigResponse::class.java)
                val upCommingTripList = ArrayList<TaskConfig>()
//                val upCommingTripList = ArrayList<ConfigTaskList>()
                baseList.data.forEach { routes ->

                    upCommingTripList.add(routes)

                }
                configlist.value = upCommingTripList
                progressbar?.visibility= View.GONE
            }
        }
        catch (e:Exception){
            AppUtils.logError(TAG,e.message.toString())

        }

    }

    override fun onFailure(call: Call<ConfigResponse>, t: Throwable) {
        try{
            AppUtils.logError(TAG,t.message.toString())

        }
        catch (e:Exception){
            AppUtils.logError(TAG,e.message.toString())

        }

    }

}
