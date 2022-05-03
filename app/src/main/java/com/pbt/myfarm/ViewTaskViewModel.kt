package com.pbt.myfarm


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
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
    var context: Context? = null
    @SuppressLint("StaticFieldLeak")
    var progress:ProgressBar?=null

    init {
//        eventlist = MutableLiveData<List<TasklistDataModel>>()
        eventlist = MutableLiveData<List<Task>>()
    }

    fun onEventListRequest(context: Context) {
        if (AppUtils().isInternet(context)){

        ApiClient.client.create(ApiInterFace::class.java)
            .eventList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
        }
        else{
            val db=DbHelper(context,null)
            val list=  db.getAllTask()
//            AppUtils.logDebug(ViewTaskActivity.TAG,"mytasklist===>"+ list.get(1).toString())

            val upCommingTripList = ArrayList<Task>()
            if (list.isNotEmpty()){
                progress?.visibility= View.GONE

            }

            list.forEach { routes ->


                if (routes.taskConfigName!=null){
                    routes.padzero= routes.taskConfigName+routes.name?.padStart(4, '0').toString()

                }else{
                    routes.padzero=  routes.name?.padStart(4, '0').toString()

                }

                routes.type= "Type: "
                routes.labeldesciption= "Desciption: "

                upCommingTripList.add(routes)

            }
            eventlist.value = list

        }

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
                val userid=MySharedPreference.getUser(context!!)?.id.toString()

             val   database = DbHelper(context!!, null)
                val   taskconfigs= database.getTaskConfigList(userid)
                AppUtils.logDebug(ViewTaskActivity.TAG,"mytasklist===>"+ taskconfigs.toString())

                baseList.data.forEach { routes ->

                    if (taskconfigs.isNullOrEmpty()){
                        if (routes.task_config_name!=null){
                            routes.padzero= routes.name_prefix+ routes.name!!.padStart(4, '0')

                        }
                        else{
                            routes.padzero= routes.name!!.padStart(4, '0')

                        }

                        routes.taskConfigName=routes.task_config_name
                        routes.taskConfigNamePrefix=routes.name_prefix
                    }
                    else{
                        for (i in 0 until  taskconfigs.size){
                            if (routes.task_config_id==taskconfigs.get(i).id){
                                val configname=taskconfigs.get(i).name_prefix
                                if (configname!=null){
                                    routes.padzero= configname+ routes.name!!.padStart(4, '0')

                                }
                                else{
                                    routes.padzero= routes.name!!.padStart(4, '0')

                                }

                                routes.taskConfigName=taskconfigs.get(i).name
                                routes.taskConfigNamePrefix=configname

                            }
                        }
                    }


//                    routes.padzero= routes.name!!.padStart(4, '0')
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
