package com.pbt.myfarm

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.FieldLIst
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.ComunityGroup
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.HttpResponse.TaskFieldResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CreatetaskViewModel(var activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<TaskFieldResponse> {
    companion object {
        const val TAG: String = "CreateTaskViewModel"
        var comConfigFieldList: ArrayList<ComunityGroup>? = null
        var groupArray: ArrayList<String>? = ArrayList()
        var groupArrayId: ArrayList<String>? = ArrayList()
        var field_list: ArrayList<String>? = ArrayList()
        var field: ArrayList<Field>? = ArrayList()

    }

    val context: Context = activity
    var configlist = MutableLiveData<List<ConfigFieldList>>()


    var namePrefix: ObservableField<String>? = null
    var confiType: ObservableField<String>? = null
    var desciption: String? = null
    var expectedStartDate: ObservableField<String>? = null
    var expectedEndDate: ObservableField<String>? = null
    var startDate: ObservableField<String>? = null
    var EndDate: ObservableField<String>? = null
    var progressbar: ProgressBar? = null

    init {
        namePrefix = ObservableField("")
        confiType = ObservableField("")
        expectedStartDate = ObservableField("")
        expectedEndDate = ObservableField("")
        startDate = ObservableField("")
        EndDate = ObservableField("")


        configlist = MutableLiveData<List<ConfigFieldList>>()


    }

    fun createTaskOffline(context: Context, configtype: TaskConfig?, isUpdate: Boolean, updateTaskId: Task?
    ): Boolean {
        var sucess = false
        val db = DbHelper(context, null)
        var packsnew: Task? = null
        val userid = MySharedPreference.getUser(context)?.id


        if (isUpdate){


            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())
            packsnew = Task(
                selectedCommunityGroup.toInt(), userid, currentDate, "", desciption,
                null, updateTaskId?.id, userid,
                currentDate, updateTaskId?.name, null, "2", updateTaskId?.task_config_id,

                null, updateTaskId?.taskConfigName, updateTaskId?.description,
                updateTaskId?.taskConfigNamePrefix,
                null, null, null,
            )

            sucess = db.tasksCreateOffline(packsnew,true)
        }
        else{

            val d = db.getLastValue_task_new(configtype?.id.toString())

            val lastValueOfPacknew = db.getLastofTask()
            val idd = lastValueOfPacknew.toInt() + 1
            AppUtils.logDebug(TAG,"iddd="+idd.toString())

            if (d.isEmpty()) {

                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                packsnew = Task(
                    selectedCommunityGroup.toInt(), userid, currentDate, "", desciption,
                    null, idd, userid, null, "1", null, "1", configtype?.id,
                    null, configtype?.name, configtype?.description, configtype?.name_prefix,
                    null, null, null,
                )

                sucess = db.tasksCreateOffline(packsnew,false)
            } else {
                val newPackname: Int = d.toInt() + 1
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())


                packsnew = Task(
                    selectedCommunityGroup.toInt(), userid, currentDate, null, desciption,
                    null, idd, userid, null, newPackname.toString(), null, "1", configtype?.id,
                    null, configtype?.name, configtype?.description, configtype?.name_prefix,
                    null, null, null,
                )


                sucess = db.tasksCreateOffline(packsnew,false)

            }
        }


        return sucess
    }

    fun onConfigFieldList(context: Context, updateTaskIdBoolean: Boolean, updateTaskList: Task?) {


        AppUtils.logError(TAG, "true configidd" + updateTaskList?.toString())

        if (AppUtils().isInternet(context)) {
            ApiClient.client.create(ApiInterFace::class.java)
                .configFieldList(
                    MySharedPreference.getUser(context)?.id.toString(),
                    updateTaskList?.task_config_id.toString(),
                    updateTaskList?.id.toString()
                ).enqueue(this)
        } else {
            setData(updateTaskList?.task_config_id.toString(), true, updateTaskList?.id.toString())

        }


//        val list = db.getTaskConfigFieldList(updateTaskList?.task_config_id.toString())
//        AppUtils.logDebug(TAG,"task Field List"+list.toString())
//        var configfieldList=ArrayList<ConfigFieldList>()
//       list.forEach{
//            configfieldList.add(
//                ConfigFieldList(
//                    it.editable!!, it.field_description!!, it.id.toString(),ArrayList(),
//            "","",""))
//        }

    }

    fun onConfigFieldListFalse(
        context: Context,
        configId: TaskConfig?,

        ) {
        AppUtils.logError(TAG, "false configidd" + configId?.toString())


        if (AppUtils().isInternet(context)) {

            ApiClient.client.create(ApiInterFace::class.java)
                .configFieldList(
                    MySharedPreference.getUser(context)?.id.toString(),
                    configId?.id.toString(), ""
                ).enqueue(this)
        } else {
            setData(configId?.id.toString(), false, "")

        }


    }

    private fun setData(id: String, isUpdate: Boolean, taskid: String) {
        val db = DbHelper(context, null)

        groupArray?.clear()
        groupArrayId?.clear()

        val communityyGrouplist = db.getCommunityGroupList()


        communityyGrouplist.forEach {
            groupArray?.add(it.name!!)
            groupArrayId?.add(it.id.toString())
        }

        val list = db.getTaskConfigFieldList(id, isUpdate, taskid)

        AppUtils.logDebug(TAG, "task Field List" + list.toString())

        val userId = MySharedPreference.getUser(context)?.id.toString()

        val taskConfigList = db.getTaskConfigList(userId)

        val packConfigField = ArrayList<ConfigFieldList>()
        if (list.size >= 1) {

//            for (i in 0 until taskConfigList.size) {
//                ExpAmtArray.add("0")
//                ExpName.add("0")
//                ExpNameKey.add("f_id")
//                ExpAmtArrayKey.add("f_value")
//
//            }
            for (i in list.indices) {
                val item=list.get(i)
                ExpAmtArray.add(" ")
                ExpName.add(item.field_name!!)
                ExpNameKey.add("f_id")
                ExpAmtArrayKey.add("f_value")

            }

            for (i in 0 until list.size) {
                val item = list.get(i)
                if (item.field_description == null) {
                    val name = db.getFieldNameFromListChoice(item.field_name.toString())
                    item.field_description = name
                }


                if (item.list != "N/A") {

                    if (item.list == "Field") {

                        val fieldList = db.getFieldList()
                        val packfieldList = ArrayList<FieldLIst>()

                        for (i in 0 until fieldList.size) {

                            val itm = fieldList.get(i)
                            packfieldList.add(
                                FieldLIst(itm.id, itm.name)
                            )

                        }
                        packConfigField.add(
                            ConfigFieldList(
                                null,
                                item.field_description.toString(),
                                item.field_name.toString(),
                                packfieldList,
                                item.field_description,
                                item.field_type.toString(),
                                null,
                            )
                        )
                    } else if (item.list == "Person") {

                        val packfieldList = ArrayList<FieldLIst>()

                        val fiellist = db.getPersonList()

                        for (i in 0 until fiellist.size) {

                            val itm = fiellist.get(i)
                            packfieldList.add(
                                FieldLIst(
                                    itm.id,
                                    itm.lname + itm.fname,

                                    )
                            )

                        }
                        packConfigField.add(
                            ConfigFieldList(
                                null,
                                item.field_description.toString(),
                                item.field_name.toString(),
                                packfieldList,
                                "",
                                item.field_type,
                                item.field_value,
                            )
                        )
                    } else if (item.list == "Team") {

                        val packfieldList = ArrayList<FieldLIst>()

                        val fiellist = db.getTeamList()

                        for (i in 0 until fiellist.size) {

                            val itm = fiellist.get(i)
                            packfieldList.add(
                                FieldLIst(itm.id, itm.name)
                            )

                        }
                        packConfigField.add(
                            ConfigFieldList(
                                null,
                                item.field_description.toString(),
                                item.field_name.toString(),
                                packfieldList,
                                item.field_description,
                                item.field_type.toString(),
                                item.field_value,
                            )
                        )

                    } else {

                        val listid = item.list

                        val fieldList = db.getListChoice(listid!!)
                        val packfieldList = ArrayList<FieldLIst>()

                        for (i in 0 until fieldList.size) {

                            val itm = fieldList.get(i)
                            packfieldList.add(
                                FieldLIst(itm.id, itm.name)
                            )

                        }

                        packConfigField.add(
                            ConfigFieldList(
                                null,
                                item.field_description.toString(),
                                item.field_name.toString(),
                                packfieldList,
                                item.field_description,
                                item.field_type.toString(),
                                item.field_value,
                            )
                        )
                    }

                } else {
                    packConfigField.add(
                        ConfigFieldList(
                            null,
                            item.field_description.toString(),
                            item.field_name.toString(),
                            ArrayList(),
                            item.field_description,
                            item.field_type.toString(),
                            item.field_value,
                        )
                    )
                }


            }
            AppUtils.logDebug(TAG, "config fieldList" + Gson().toJson(packConfigField).toString())
        }
        configlist.value = packConfigField
    }

    override fun onResponse(
        call: Call<TaskFieldResponse>,
        response: Response<TaskFieldResponse>
    ) {
        try {
            if (response.body()?.error == false) {
                progressbar?.visibility = View.GONE
                val configlistt = Gson().fromJson(
                    Gson().toJson(response.body()?.data),
                    ArrayList<ConfigFieldList>()::class.java
                )
                if (!configlistt.isNullOrEmpty()) {
                    for (i in 0 until configlistt.size) {
                        ExpAmtArray.add("0")
                        ExpName.add("0")
                        ExpNameKey.add("f_id")
                        ExpAmtArrayKey.add("f_value")

                    }
                    AppUtils.logDebug(
                        TAG,
                        "arrayid" + CreatePackActivity.arrayID + "\n" + CreatePackActivity.arrayName
                    )

                }
                comConfigFieldList = Gson().fromJson(
                    Gson().toJson(response.body()?.CommunityGroup),
                    ArrayList<ComunityGroup>()::class.java
                )
                configlist.value = configlistt
                groupArray?.clear()
                groupArrayId?.clear()


                for (i in 0 until comConfigFieldList!!.size) {
                    val row: Any = comConfigFieldList!!.get(i)
                    val rowmap: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
                    val name = rowmap["name"].toString()
                    val communitygroupid = rowmap["id"].toString()

                    groupArray?.add(name)
                    groupArrayId?.add(communitygroupid)
                }
            } else {
                println("error true")
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.toString())
        }
    }

    override fun onFailure(call: Call<TaskFieldResponse>, t: Throwable) {
        try {
            AppUtils.logError(TAG, "Failure -->" + t.message)
        } catch (e: Exception) {
            AppUtils.logError(TAG, "Failure -->" + e.message)
        }

    }


}