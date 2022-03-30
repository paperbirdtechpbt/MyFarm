package com.pbt.myfarm


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference

import retrofit2.Call
import retrofit2.Response


class ViewTaskViewModel(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<testresponse> {
    companion object {
        const val TAG: String = "ViewTaskViewModel"
    }

//    var eventlist = MutableLiveData<List<TasklistDataModel>>()
    var eventlist = MutableLiveData<List<Task>>()

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null
    @SuppressLint("StaticFieldLeak")
    var progress:ProgressBar?=null

    init {

//        eventlist = MutableLiveData<List<TasklistDataModel>>()
        eventlist = MutableLiveData<List<Task>>()

    }

    fun onEventListRequest(context: Context) {

//        ApiClient.client.create(ApiInterFace::class.java)
//            .eventList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)


        val db=DbHelper(context,null)
      val list=  db.getAllTask()
        val upCommingTripList = ArrayList<Task>()
        if (list.isNotEmpty()){
            progress?.visibility= View.GONE

        }

        list.forEach { routes ->

            routes.padzero= routes.name?.padStart(4, '0').toString()
                       routes.type= "Type: "
            routes.labeldesciption= "Desciption: "

            upCommingTripList.add(routes)

        }


        eventlist.value = list

        AppUtils.logDebug(TAG,"get All Task list"+list.size.toString()
        )

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        try{
            if (response.body()?.error == false) {
                progress?.visibility= View.GONE
                eventlist.value = emptyList()
                val baseList : testresponse =  Gson().fromJson(Gson().toJson(response.body()),
                    testresponse::class.java)
//                val upCommingTripList = ArrayList<TasklistDataModel>()
                val upCommingTripList = ArrayList<Task>()
                baseList.data.forEach { routes ->

                    routes.padzero= routes.name!!.padStart(4, '0')
                    routes.type= "Type: "
                    routes.labeldesciption= "Desciption: "

//                    if (routes.name_prefix==null){
//                        routes.name_prefix="."
//                    }
                    upCommingTripList.add(routes)

                }
            eventlist.value = upCommingTripList
            }
        }
        catch (e:Exception){

        }


    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        try{
            AppUtils.logError(TAG, " onFailure : " + t.message)

        }
        catch (e:Exception){
            AppUtils.logError(TAG, " onFailure : " + e.message)

        }

    }


}
