package com.pbt.myfarm.Activity.TaskFunctions.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.BaseTaskFunction
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.TaskField
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.DATE_TIME_FORMATE
import com.pbt.myfarm.Util.AppUtils
import retrofit2.Call
import retrofit2.Response


class ViewModelTaskFunctionality(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<HttpResponse> {

    val TAG = "ViewModelTaskFunctionality"
    var context: Context? = null
    var listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()

    init {
        listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
    }


    fun onTaskFunctionList(
        taskConfigID: String,
        context: Context,
        updateTaskId: String,
        userID: String
    ) {

        Log.d("Apicall", "Param updateTsk : $updateTaskId  UserID $userID ")

        if (AppUtils().isInternet(context)) {
            ApiClient.client.create(ApiInterFace::class.java)
                .taskFunctionList(
                    userID,
                    updateTaskId
                ).enqueue(this)
        } else {
            val db = DbHelper(context, null)
            val taskFunctionList = ArrayList<ListTaskFunctions>()
            taskFunctionList.add(ListTaskFunctions("0", "Select"))
            db.getTaskFunctionList(taskConfigID).let {
                taskFunctionList.addAll(it)
            }
            listTaskFuntions.value = taskFunctionList
        }
    }

    override fun onResponse(
        call: Call<HttpResponse>,
        response: Response<HttpResponse>
    ) {
        if (response.body()?.error == false) {
            if (response.body() != null) {
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


    fun executeTask(
        mTaskID: String,
        mFunctionID: String,
        mUserID: String,
        mFieldID: String,
        db: DbHelper,
        mTimeZoneId: String
    ) {

        val appUtils = AppUtils()

        val choices = db.getListChoiceQuery(mTimeZoneId)
        var sign = "+"
        val timeZone = choices.name?.split("UTC")

        var hour = 0;
        var minut = 0;

        timeZone?.let {
            if (timeZone[1].contains("-")) {
                sign = "-"
            }

            val timeZone1 = timeZone[1].split(sign, timeZone[1])
            val timeZone2 = timeZone1[1].split(":", timeZone[1])


            timeZone2.let {
                hour = it[0].toInt() * 60
                minut = hour + it[1].toInt()
            }
        }

        val currentDateTime =
            appUtils.getCurrentDateTimeAccordingToUTC(AppConstant.DATE_TIME_FORMATE)
        val newTimeStamp = appUtils.getTimeStamp(currentDateTime!!)
        val newTimeStampConvert =
            if (sign == "+") newTimeStamp + minut.toLong() else newTimeStamp - minut.toLong()
        val userUtcDate = appUtils.timeStampCovertToDateTime(newTimeStampConvert)
        AppUtils.logDebug(TAG, " TimeStamp ==> ${userUtcDate}")

        val obj = JsonObject()

        if (mFunctionID == "169") {

            var taskField = db.getTaskField(mTaskID, "134")
            val taskExpStart = db.getTaskField(mTaskID, "129")


            val taskExpStartTime =
                taskExpStart?.value?.let { it1 -> appUtils.getTimeStamp(it1) } ?: 0
            val dateTime = appUtils.getCurrentDateTimeAccordingToUTC(DATE_TIME_FORMATE)
            val taskStartTime = dateTime?.let { it1 -> appUtils.getTimeStamp(it1) } ?: 0
            if (taskStartTime > taskExpStartTime) {
                db.updateTask(mTaskID, true)
            }
            if (taskField == null) {
                taskField = TaskField("134", 0, mTaskID.toInt(), userUtcDate)
                db.insertNewTaskField(taskField)
            }
            db.updateTaskStatus(mTaskID, "started")
            obj.addProperty("error", false)
            obj.addProperty("data", Gson().toJson(taskField))
            obj.addProperty("msg", "success")

            Log.d("executeTask", " 171 Success $obj")
        }
        else if (mFunctionID == "170") {

            var taskField = db.getTaskField(mTaskID, "135")
            val taskExpEnd = db.getTaskField(mTaskID, "131")
            val tastStarted = db.getTaskField(mTaskID, "134")


            if (tastStarted == null) {
                obj.addProperty("error", true)
                obj.addProperty("msg", "Task not started yet. Please start task first")
            } else {

                val taskExpStartTime =
                    taskExpEnd?.value?.let { it1 -> appUtils.getTimeStamp(it1) } ?: 0
                val dateTime = appUtils.getCurrentDateTimeAccordingToUTC(DATE_TIME_FORMATE)
                val taskStartTime = dateTime?.let { it1 -> appUtils.getTimeStamp(it1) } ?: 0
                if (taskStartTime > taskExpStartTime) {
                    db.updateTask(mTaskID, false)
                }

                if (taskField == null) {
                    taskField = TaskField("135", 0, mTaskID.toInt(), userUtcDate)
                    db.insertTaskField(taskField)
                }

                db.updateTaskStatus(mTaskID, "completed")
                obj.addProperty("error", false)
                obj.addProperty("data", Gson().toJson(taskField))
                obj.addProperty("msg", "success")

                Log.d("executeTask", " 170 Success $obj")
            }

        }
        else if (mFunctionID == "176") {

            if (mFieldID.isEmpty() || mFieldID == "0") {
                obj.addProperty("error", true)
                obj.addProperty("msg", "Select container")
            }

            val taskStartDate = db.getTaskField(mTaskID, "134")

            if (taskStartDate == null) {
                obj.addProperty("error", true)
                obj.addProperty("msg", "Task not started yet. Please start task first")
            } else {

                var taskObject = db.getTaskObject(mTaskID, mFieldID,"CONTAINER");
                val container = db.getContainer(mFieldID)
                if (taskObject == null) {
                    taskObject = TaskObject(
                        container?.`class` ?: "",
                        "",
                        "CONTAINER",
                        0,
                        mUserID.toInt(),
                        appUtils.systemDateTime(),
                        container?.name ?: "",
                        "ADDED",
                        "",
                        mTaskID.toInt(),
                        container?.type ?: "",
                        mFieldID,
                        "1"
                    )
                    db.insertNewTaskObject(taskObject)
                    obj.addProperty("data", Gson().toJson(taskObject))
                    obj.addProperty("error", false)
                    obj.addProperty("msg", "Inserted Successfully")
                }

            }

        }
        else if (mFunctionID == "171") {
            if (mFieldID.isEmpty() || mFieldID == "0") {
                obj.addProperty("error", true)
                obj.addProperty("msg", "Select Person")
            } else {
                val taskStartDate = db.getTaskField(mTaskID, "134")

                if (taskStartDate == null) {
                    obj.addProperty("error", true)
                    obj.addProperty("msg", "Task not started yet. Please start task first")
                } else {

                    var taskObject = db.getTaskObject(mTaskID, mFieldID,"PERSON");
                    var person = db.getPerson(mFieldID)

                    if(taskObject == null){
                        taskObject =  TaskObject(
                            person?.person_class ?:"","","PERSON",mUserID.toInt(),0,appUtils.systemDateTime(),person?.fname ?: "" +person?.lname ,mFieldID,"ADDED",mTaskID.toInt(),person?.person_type?:"","","1"
                        )

                        db.insertNewTaskObject(taskObject)

                        obj.addProperty("error", false)
                        obj.addProperty("data", Gson().toJson(taskObject))
                        obj.addProperty("msg", "Inserted Successfully")
                    }
                }
            }
        }
    }
}