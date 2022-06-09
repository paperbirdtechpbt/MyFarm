package com.pbt.myfarm.Service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.Nullable
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Event.ResposneNotificationCount
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigViewModel
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.ConfigResponse
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.ModelClass.SendDataMasterList
import com.pbt.myfarm.OffLineSyncModel
import com.pbt.myfarm.TaskConfig
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFarmService() : Service(), retrofit2.Callback<testresponse> {

    val TAG = "MyFarmService"
    var userID: String? = null
    var roleID: String? = null
    var tasklist = ArrayList<com.pbt.myfarm.ModelClass.Task>()
    var packlist = ArrayList<com.pbt.myfarm.ModelClass.PacksNew>()
    var eventlist = ArrayList<com.pbt.myfarm.ModelClass.Event>()
    var collecrDataList = ArrayList<com.pbt.myfarm.ModelClass.CollectData>()
    var taskobjectt = ArrayList<com.pbt.myfarm.ModelClass.TaskObject>()

    val db = DbHelper(this, null)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(@Nullable intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, " Service run ${System.currentTimeMillis()}")

        userID = MySharedPreference.getUser(this)?.id.toString()
        roleID = MySharedPreference.getStringValue(this, AppConstant.CONST_PREF_ROLE_ID, "0")

//        GlobalScope.launch {
//
//            sendDataMastersApi(userID!!)
//            syncOfflineData(userID!!,roleID!!)
//            getEventTypeListAndOtherList(userID!!)
//        }
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                GlobalScope.launch {
                    val userID = MySharedPreference.getUser(this@MyFarmService)?.id.toString()

                    fetchNotificationApi(userID)
                }
                mainHandler.postDelayed(this, 7000)
            }
        })
        return START_STICKY
    }

    private fun fetchNotificationApi(userID: String) {
        val apiInterFace = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiInterFace.notificationCount(userID)
        call.enqueue(object : Callback<ResposneNotificationCount> {
            override fun onResponse(
                call: Call<ResposneNotificationCount>,
                response: Response<ResposneNotificationCount>
            ) {
                if (response.isSuccessful) {
                    val baserespose: ResposneNotificationCount = Gson().fromJson(
                        Gson().toJson(response.body()),
                        ResposneNotificationCount::class.java
                    )
                    val count = baserespose.count
                    MainActivity().setupBadge(count)


                    AppUtils.logError(
                        TAG,
                        "NotificationCount" + Gson().toJson(response.body()).toString()
                    )
                }


            }

            override fun onFailure(call: Call<ResposneNotificationCount>, t: Throwable) {
            }

        })
    }

    fun sendDataMastersApi(userID: String) {
        var senddata: SendDataMasterList? = null

        val collectData = db.getCollectDataToBeSend(userID)
        collecrDataList = collectData


        val evnet = db.getEventsTobeSend(userID)
        eventlist = evnet


        val packnew = db.getPacksToBeSend(userID)
        packlist = packnew


        val taskField = ArrayList<com.pbt.myfarm.ModelClass.TaskField>()

        var taskobject = ArrayList<com.pbt.myfarm.ModelClass.TaskObject>()
//        val taskObject = db.getTaskObject()
//        taskobjectt = taskObject


        val task = db.getTasksToBeSend(userID)
        tasklist = task

        AppUtils.logError(TAG, "Packs To Send==>" + Gson().toJson(task).toString())


        senddata = SendDataMasterList(collectData, evnet, packnew, taskField, taskobject, task)


        ApiClient.client.create(ApiInterFace::class.java).postJson(
            senddata
        ).enqueue(this)
    }


    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun syncOfflineData(userID: String, roleID: String) {

        val service = ApiClient.getClient()!!.create(ApiInterFace::class.java)
        val apiInterFace = service.offLineSync(userID, roleID)

        apiInterFace.enqueue(object : Callback<OffLineSyncModel> {

            override fun onFailure(call: Call<OffLineSyncModel>, t: Throwable) {

                AppUtils.logError(TAG, t.message.toString())

            }

            override fun onResponse(
                p0: Call<OffLineSyncModel>,
                p1: Response<OffLineSyncModel>
            ) {


                if (p1.isSuccessful) {
                    p1.body()?.let {
                        GlobalScope.launch {
                            if (it.Data.packs_new.isNotEmpty()) {
                                for (pack in it.Data.packs_new) {
                                    db.addNewPack(pack, "0")
                                }
                            }
                        }
                        GlobalScope.launch {
                            if (it.Data.pack_configs.isNotEmpty()) {
                                for (pack in it.Data.pack_configs) {
                                    db.pack_configscreate(pack)
                                }
                            }
                        }
                        GlobalScope.launch {
                            if (it.Data.pack_collect_activity.isNotEmpty()) {
                                for (pack in it.Data.pack_collect_activity) {
                                    db.pack_collect_activity_create(pack)
                                }
                            }
                        }

                        GlobalScope.launch {
                            if (it.Data.pack_config_fields.isNotEmpty()) {
                                for (pack in it.Data.pack_config_fields) {
                                    db.pack_config_fields_create(pack)
                                }
                            }
                        }
                        GlobalScope.launch {
                            if (it.Data.pack_fields.isNotEmpty()) {
                                for (pack in it.Data.pack_fields) {
                                    db.pack_fields_create(pack)
                                }
                            }
                        }

                        GlobalScope.launch {
                            if (it.Data.tasks.isNotEmpty()) {
                                for (pack in it.Data.tasks) {
                                    db.tasksCreate(pack)
                                }
                            }
                        }
                        GlobalScope.launch {

                            if (it.Data.task_fields.isNotEmpty()) {
                                for (pack in it.Data.task_fields) {
                                    db.task_fields_create(pack)
                                }
                            }
                        }

                        GlobalScope.launch {
                            if (it.Data.task_configs.isNotEmpty()) {
                                for (pack in it.Data.task_configs) {
                                    db.task_configs_create(pack)
                                }
                            } else {
                                calltaskConfigApi()
                            }
                        }

                        GlobalScope.launch {
                            if (it.Data.task_config_fields.isNotEmpty()) {
                                for (pack in it.Data.task_config_fields) {
                                    db.task_config_fields_create(pack)
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.task_config_functions.isNotEmpty()) {
                                    for (pack in it.Data.task_config_functions) {
                                        db.task_config_functions_create(pack)
                                    }
                                }
                            }
                            GlobalScope.launch {
                                if (it.Data.collect_data.isNotEmpty()) {
                                    for (pack in it.Data.collect_data) {
                                        db.collectDataCreate(pack)
                                    }
                                }
                                db.close()
                            }

                            GlobalScope.launch {
                                if (it.Data.collect_activities.isNotEmpty()) {
                                    for (pack in it.Data.collect_activities) {
                                        db.collectActivitiesCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.collect_activity_results.isNotEmpty()) {
                                    for (pack in it.Data.collect_activity_results) {
                                        db.collectActivitiesResultsCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.people.isNotEmpty()) {
                                    for (pack in it.Data.people) {
                                        db.peopleCreate(pack)
                                    }
                                }
                            }
                            GlobalScope.launch {
                                if (it.Data.container.isNotEmpty()) {
                                    for (pack in it.Data.container) {
                                        db.containerCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.events.isNotEmpty()) {
                                    for (pack in it.Data.events) {
                                        db.eventsCreate(pack, false)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.graph_charts.isNotEmpty()) {
                                    for (pack in it.Data.graph_charts) {
                                        db.graphChartsCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.list_choices.isNotEmpty()) {
                                    for (pack in it.Data.list_choices) {
                                        db.listChoicesCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.units.isNotEmpty()) {
                                    for (pack in it.Data.units) {
                                        db.unitsCreate(pack)
                                    }
                                }
                            }
                            GlobalScope.launch {
                                if (it.Data.teams.isNotEmpty()) {
                                    for (pack in it.Data.teams) {
                                        db.teamCreate(pack)
                                    }
                                }
                            }
                            GlobalScope.launch {
                                if (it.Data.task_media_files.isNotEmpty()) {
                                    for (pack in it.Data.task_media_files) {
                                        db.taskMediaFilescCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.privileges.isNotEmpty()) {
                                    for (pack in it.Data.privileges) {
                                        db.addPrivilege(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {

                                if (it.Data.role_privileges.isNotEmpty()) {
                                    for (privileges in it.Data.role_privileges) {
                                        db.insertRolPrivileges(privileges)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.task_objects.isNotEmpty()) {
                                    for (pack in it.Data.task_objects) {
                                        db.addTaskObjectCreate(pack, "0")
                                    }
                                }
                            }
                            //all are working fine above Crash here
                            GlobalScope.launch {
                                if (it.Data.collect_activity_result_unit.isNotEmpty()) {
                                    for (pack in it.Data.collect_activity_result_unit) {
                                        db.collectActivitiesResultsUnitCreate(pack)
                                    }
                                }
                            }
                            GlobalScope.launch {
                                if (it.Data.community_groups.isNotEmpty()) {
                                    for (pack in it.Data.community_groups) {
                                        db.communityGroupsCreate(pack)
                                    }
                                }
                            }
                            GlobalScope.launch {
                                if (it.Data.container_object.isNotEmpty()) {
                                    for (pack in it.Data.container_object) {
                                        db.containerObjectCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.fields.isNotEmpty()) {
                                    for (pack in it.Data.fields) {
                                        db.fieldsCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.graph_chart_objects.isNotEmpty()) {
                                    for (pack in it.Data.graph_chart_objects) {
                                        db.graphChartObjectsCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.lists.isNotEmpty()) {
                                    for (pack in it.Data.lists) {
                                        db.listsCreate(pack)
                                    }
                                }
                            }

                            GlobalScope.launch {
                                if (it.Data.sensors.isNotEmpty()) {
                                    for (pack in it.Data.sensors) {
                                        db.sensorCreate(pack)
                                    }
                                }
                                db.close()
                            }
                        }
                    }

                }
            }
        })
    }

    private fun calltaskConfigApi() {
        val service = ApiClient.getClient()?.create(ApiInterFace::class.java)
        val apiInterFace = service?.taskConfigList(MySharedPreference.getUser(this)?.id.toString())
        apiInterFace?.enqueue(object : Callback<ConfigResponse> {
            override fun onResponse(
                call: Call<ConfigResponse>,
                response: Response<ConfigResponse>
            ) {
                try {
                    if (response.body()?.error == false) {


                        val baseList: ConfigResponse = Gson().fromJson(
                            Gson().toJson(response.body()),
                            ConfigResponse::class.java
                        )
                        val upCommingTripList = ArrayList<TaskConfig>()
//                val upCommingTripList = ArrayList<ConfigTaskList>()
                        baseList.data.forEach { routes ->
                            db.task_configs_create(routes)

                            upCommingTripList.add(routes)

                        }

                    }
                } catch (e: Exception) {
                    AppUtils.logError(SelectConfigViewModel.TAG, e.message.toString())

                }

            }

            override fun onFailure(call: Call<ConfigResponse>, t: Throwable) {
                AppUtils.logError(SelectConfigViewModel.TAG, t.message.toString())

            }

        })

    }

    private fun getEventTypeListAndOtherList(userID: String) {
        val service = ApiClient.client.create(ApiInterFace::class.java)
        val apiInterFace = service.eventTeamList(userID)
        apiInterFace.enqueue(object : Callback<ResponseDashBoardEvent> {
            override fun onResponse(
                call: Call<ResponseDashBoardEvent>,
                response: Response<ResponseDashBoardEvent>
            ) {
                try {
                    if (response.isSuccessful) {
                        response.body().let {
                            if (it!!.error == false) {
//
                                for (pack in it.EventTyp) {
                                    db.addEventType(pack)
                                }
                                for (pack in it.EventSts) {
                                    db.addEventStatus(pack)
                                }
                            } else {
                                AppUtils.logDebug(TAG, "error true")

                            }
                        }
                    }


                } catch (e: java.lang.Exception) {
                    println(e.localizedMessage.toString())
                }
            }

            override fun onFailure(call: Call<ResponseDashBoardEvent>, t: Throwable) {

                println(t.localizedMessage.toString())

            }
        })

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        try {
            if (response.body()?.error == false) {
                if (!response.body()?.msg.isNullOrBlank()) {

                    if (!tasklist.isNullOrEmpty()) {
                        for (i in 0 until tasklist.size) {
                            val item = tasklist.get(i)
                            if (item.status != 3) {
                                val db = DbHelper(this, null)
                                db.changeTaskStatus(
                                    item.name.toString(),
                                    item.task_config_id.toString()
                                )
                            }
                        }
                    }
                    if (!packlist.isNullOrEmpty()) {
                        for (i in 0 until packlist.size) {
                            val item = packlist.get(i)
                            if (item.status != 3) {
                                val db = DbHelper(this, null)
                                db.changePackStatus(
                                    item.name.toString(),
                                    item.pack_config_id.toString(), item
                                )
                            }


                        }
                    }
                    if (!eventlist.isNullOrEmpty()) {
                        for (i in 0 until eventlist.size) {
                            val item = eventlist.get(i)
                            if (item.status != 3) {
                                val db = DbHelper(this, null)
                                db.changeEventStatus(item.id.toString())
                            }
                        }
                    }
                    if (!collecrDataList.isNullOrEmpty()) {
                        for (i in 0 until collecrDataList.size) {
                            val item = collecrDataList.get(i)
                            if (item.status != 3) {
                                val db = DbHelper(this, null)
                                db.changeCollectDataStatus(item.id.toString())
                            }
                        }
                    }
                }
            } else {
                AppUtils.logDebug(TAG, "Send Data Error " + response.body()?.msg.toString())
            }
        } catch (e: java.lang.Exception) {
            AppUtils.logDebug(TAG, e.message.toString())
        }
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logError(TAG, t.message.toString())

    }
}