package com.pbt.myfarm.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.OffLineSyncModel
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFarmService() : Service() {

    val TAG = "MyFarmService"
    var userID:String? = null

    val db= DbHelper(this,null)


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG," Service run ${System.currentTimeMillis()}")

        userID =  MySharedPreference.getUser(this)?.id.toString()

        GlobalScope.launch(Dispatchers.IO){
            syncOfflineData(userID!!)
        }
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
    }
    private fun syncOfflineData(userID : String){

        val service = ApiClient.getClient()!!.create(ApiInterFace::class.java)
        val apiInterFace = service.offLineSync(userID)

        apiInterFace.enqueue(object : Callback<OffLineSyncModel> {

            override fun onFailure(call: Call<OffLineSyncModel>, t: Throwable) {
                try {
                    AppUtils.logError(TAG, t.message.toString())
                } catch (e: Exception) {
                    AppUtils.logError(TAG, e.localizedMessage)
                }
            }
            override fun onResponse(
                p0: Call<OffLineSyncModel>,
                p1: Response<OffLineSyncModel>
            ) {
                if(p1.isSuccessful){
                    p1.body()?.let {
                        if(it.Data.packs_new.isNotEmpty()){
                            for(pack in it.Data.packs_new) {

                                db.addNewPack(pack,"0")
                            }
                        }
                        if(it.Data.pack_configs.isNotEmpty()){
                            for(pack in it.Data.pack_configs) {

                                db.pack_configscreate(pack)
                            }
                        }
                        if(it.Data.pack_collect_activity.isNotEmpty()){
                            for(pack in it.Data.pack_collect_activity) {

                                db.pack_collect_activity_create(pack)
                            }
                        }
                        if(it.Data.pack_config_fields.isNotEmpty()){
                            for(pack in it.Data.pack_config_fields) {

                                db.pack_config_fields_create(pack)
                            }
                        }
                        if(it.Data.pack_fields.isNotEmpty()){
                            for(pack in it.Data.pack_fields) {
                                db.pack_fields_create(pack)
                            }
                        }
                        if(it.Data.tasks.isNotEmpty()){
                            for(pack in it.Data.tasks) {
                                db.tasksCreate(pack)

                            }
                        }
                        if(it.Data.task_fields.isNotEmpty()){
                            for(pack in it.Data.task_fields) {
                                db.task_fields_create(pack)
                            }
                        }
                        if(it.Data.task_configs.isNotEmpty()){
                            for(pack in it.Data.task_configs) {
                                db.task_configs_create(pack)
                            }
                        }
                        if(it.Data.task_config_fields.isNotEmpty()){
                            for(pack in it.Data.task_config_fields) {
                                db.task_config_fields_create(pack)
                            }
                        }
                        if(it.Data.task_config_functions.isNotEmpty()){
                            for(pack in it.Data.task_config_functions) {
                                db.task_config_functions_create(pack)
                            }
                        }
                        if(it.Data.collect_data.isNotEmpty()){
                            for(pack in it.Data.collect_data) {
                                db.collectDataCreate(pack)
                            }
                        }
                        if(it.Data.community_groups.isNotEmpty()){
                            for(pack in it.Data.community_groups) {
                                db.communityGroupsCreate(pack)
                            }
                        }
                        if(it.Data.collect_activities.isNotEmpty()){
                            for(pack in it.Data.collect_activities) {
                                db.collectActivitiesCreate(pack)
                            }
                        }
                        if(it.Data.collect_activity_results.isNotEmpty()){
                            for(pack in it.Data.collect_activity_results) {
                                db.collectActivitiesResultsCreate(pack)
                            }
                        }
                        if(it.Data.collect_activity_result_unit.isNotEmpty()){
                            for(pack in it.Data.collect_activity_result_unit) {
                                db.collectActivitiesResultsUnitCreate(pack)
                            }
                        }
                        if(it.Data.people.isNotEmpty()){
                            for(pack in it.Data.people) {
                                db.peopleCreate(pack)
                            }
                        }
                        if(it.Data.container.isNotEmpty()){
                            for(pack in it.Data.container) {
                                db.containerCreate(pack)
                            }
                        }
                        if(it.Data.container_object.isNotEmpty()){
                            for(pack in it.Data.container_object) {
                                db.containerObjectCreate(pack)
                            }
                        }
                        if(it.Data.events.isNotEmpty()){
                            for(pack in it.Data.events) {
                                db.eventsCreate(pack)
                            }
                        }
                        if(it.Data.fields.isNotEmpty()){
                            for(pack in it.Data.fields) {
                                db.fieldsCreate(pack)
                            }
                        }
                        if(it.Data.graph_charts.isNotEmpty()){
                            for(pack in it.Data.graph_charts) {
                                db.graphChartsCreate(pack)
                            }
                        }
                        if(it.Data.graph_chart_objects.isNotEmpty()){
                            for(pack in it.Data.graph_chart_objects) {
                                db.graphChartObjectsCreate(pack)
                            }
                        }
                        if(it.Data.lists.isNotEmpty()){
                            for(pack in it.Data.lists) {
                                db.listsCreate(pack)
                            }
                        }
                        if(it.Data.list_choices.isNotEmpty()){
                            for(pack in it.Data.list_choices) {
                                db.listChoicesCreate(pack)
                            }
                        }
                        if(it.Data.sensors.isNotEmpty()){
                            for(pack in it.Data.sensors) {
                                db.sensorCreate(pack)
                            }
                        }
                        if(it.Data.units.isNotEmpty()){
                            for(pack in it.Data.units) {
                                db.unitsCreate(pack)
                            }
                        }
                        if(it.Data.teams.isNotEmpty()){
                            for(pack in it.Data.teams) {
                                db.teamCreate(pack)
                            }
                        }
                        if(it.Data.task_media_files.isNotEmpty()){
                            for(pack in it.Data.task_media_files) {
                                db.taskMediaFilescCreate(pack)

                            }
                        }
                    }
                }

            }
        })
    }
}