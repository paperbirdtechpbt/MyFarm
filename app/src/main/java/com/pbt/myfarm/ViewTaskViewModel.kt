package com.pbt.myfarm


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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
import retrofit2.Callback
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

    fun showAlertDailog(
        taskname: String,
        position: Int,
        mytasklist: Task,
        context: Context
    ) {

            AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to Delete $taskname")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, which ->
                        if (AppUtils().isInternet(context)) {

                            callApiForDeleteTask(mytasklist?.id.toString(),context)

                        } else {
                            val db = DbHelper(context, null)

                            val issucess = db.deleteTaskNew(mytasklist.id!!.toString())
                            if (issucess) {
                                context.startActivity(Intent(context, ViewTaskActivity::class.java))
                                (context as Activity).finish()
                            }

                        }
                        Toast.makeText(context, "Deleted $taskname", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_delete)
                .show()


    }

    private fun callApiForDeleteTask(id: String, context: Context) {

        val apiclient=ApiClient.client.create(ApiInterFace::class.java)
        val call=apiclient.deleteTask(id)
        call.enqueue(object :Callback<testresponse>{
            override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
                try {
                    if (response.body()?.error == false) {
                        Toast.makeText(context, "Task Deleted SuccessFullly", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, ViewTaskActivity::class.java))
                        (context as Activity).finish()
                    } else {
                        Toast.makeText(context, response.body()?.msg.toString(), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    AppUtils.logError(ViewTaskActivity.TAG, e.localizedMessage)

                }

            }

            override fun onFailure(call: Call<testresponse>, t: Throwable) {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                    AppUtils.logError(ViewTaskActivity.TAG, t.localizedMessage)

            }
        })

    }


}
