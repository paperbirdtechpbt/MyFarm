package com.pbt.myfarm

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.checkFieldStatus
import com.pbt.myfarm.Activity.Event.ResponseScanCodeForTaskFunction
import com.pbt.myfarm.Activity.Event.ScannedPersonData
import com.pbt.myfarm.Activity.FieldLIst
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.TaskFunctions.*
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.task_object.ViewTaskObjectActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Service.ResponseTaskExecution
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_task_function.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreatetaskViewModel(var activity: Application) : AndroidViewModel(activity),
    Callback<TaskFieldResponse>,ProgressRequestBody.UploadCallback {
    companion object {
        const val TAG: String = "CreateTaskViewModel"
        var comConfigFieldList: ArrayList<ComunityGroup>? = null
        var groupArray: ArrayList<String>? = ArrayList()
        var groupArrayId: ArrayList<String>? = ArrayList()
        var field: ArrayList<Field>? = ArrayList()
    }



    val context: Context = activity

    var configlist = MutableLiveData<List<ConfigFieldList>>()
    var allUserList = MutableLiveData<List<AllUserList>>()
    var checkstatusObject = MutableLiveData<HttpResponse>()
    val navlist = MutableLiveData<List<ListNavigationTaskFunction>>()
    val updateTaskid = MutableLiveData<String>()
    val selectFunctionName = MutableLiveData<String>()
    val selectFunctionFiedlId = MutableLiveData<String>()
    val functionFieldList = MutableLiveData<List<ListFunctionFieldlist>>()
    val isTransferPack = MutableLiveData<Boolean>()
    var taskObjectList = MutableLiveData<List<TaskObject>>()
    private var transferPackListNAMEString = ""


    var namePrefix: ObservableField<String>? = null
    var confiType: ObservableField<String>? = null
    var desciption: String? = null
    var expectedStartDate: ObservableField<String>? = null
    var expectedEndDate: ObservableField<String>? = null
    var startDate: ObservableField<String>? = null
    var EndDate: ObservableField<String>? = null
    var progressbar: ProgressBar? = null
    var btnSubmit: Button? = null
    var layoutProgressbar: ConstraintLayout? = null
    var id =0
    var scannedName = ""
    var mprogressCircular: CircularProgressIndicator? = null
    var mprogressCircularLabel: TextView? = null


    init {
        namePrefix = ObservableField("")
        confiType = ObservableField("")
        expectedStartDate = ObservableField("")
        expectedEndDate = ObservableField("")
        startDate = ObservableField("")
        EndDate = ObservableField("")

        configlist = MutableLiveData<List<ConfigFieldList>>()
        checkstatusObject = MutableLiveData<HttpResponse>()

    }

    fun setEditableOrNot(
        edittext: EditText?,
        spinner: Spinner?,
        context: Context,
        description: EditText
    ) {

        if (ViewTaskActivity.updateTaskBoolen) {
            if (checkFieldStatus?.status == "completed" || checkFieldStatus?.status == null) {
                btnSubmit?.visibility = View.GONE
                edittext?.isEnabled = false
                edittext?.isFocusable = false
                edittext?.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.grey
                        )
                    )
                )
                spinner?.isEnabled = false
                spinner?.isFocusable = false
                spinner?.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.grey
                        )
                    )
                )
                description.isEnabled = false
                description.isFocusable = false
                description.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.grey
                        )
                    )
                )
            } else {
                btnSubmit?.visibility = View.VISIBLE

                spinner?.isEnabled = false
                spinner?.isFocusable = false
                spinner?.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.grey
                        )
                    )
                )
                edittext?.isEnabled = false
                edittext?.isFocusable = false
                edittext?.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.grey
                        )
                    )
                )
                description.isEnabled = true
                description.isFocusable = true
            }

        }
    }

    fun createTaskOffline(
        context: Context, configtype: TaskConfig?, isUpdate: Boolean, updateTaskId: Task?
    ): Boolean {
        var sucess = false
        val db = DbHelper(context, null)
        var packsnew: Task? = null
        val userid = MySharedPreference.getUser(context)?.id


        if (isUpdate) {


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

            sucess = db.tasksCreateOffline(packsnew, true)
        } else {

            val d = db.getLastValue_task_new(configtype?.id.toString())

            val lastValueOfPacknew = db.getLastofTask()
            val idd = lastValueOfPacknew.toInt() + 1
            AppUtils.logDebug(TAG, "iddd=" + idd.toString())

            if (d.isEmpty()) {

                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                packsnew = Task(
                    selectedCommunityGroup.toInt(), userid, currentDate, "", desciption,
                    null, idd, userid, null, "1", null, "1", configtype?.id,
                    null, configtype?.name, configtype?.description, configtype?.name_prefix,
                    null, null, null,
                )

                sucess = db.tasksCreateOffline(packsnew, false)
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


                sucess = db.tasksCreateOffline(packsnew, false)

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

    fun onCheckStatus(context: Context, taskid: String) {
        updateTaskid.postValue(taskid)
        val apiClient = ApiClient.client.create(ApiInterFace::class.java)
        val apiInterFace = apiClient.checkTaskStatus(taskid)
        apiInterFace.enqueue(object : Callback<HttpResponse> {
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                if (response.body()?.error == false) {
                    val baseHttpResponse: HttpResponse =
                        Gson().fromJson(Gson().toJson(response.body()), HttpResponse::class.java)
                    checkstatusObject.value = baseHttpResponse
                } else
                    Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT)
                        .show()

            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                println(t.localizedMessage.toString())
            }

        })

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
                val item = list.get(i)
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


                val userslist = Gson().fromJson(
                    Gson().toJson(response.body()?.users),
                    ArrayList<AllUserList>()::class.java
                )
                val list = ArrayList<AllUserList>()
                for (i in 0 until userslist!!.size) {
                    val row: Any = userslist.get(i)
                    val rowmap: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
                    val name = rowmap["name"].toString()
                    val id = rowmap["id"].toString()

                    list.add(AllUserList(id, name))
                }
                allUserList.value = list

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

    fun callApiStoreTask(
        context: Context, configtypeId: String, prefix: String,
        selectedCommunityGroup: String,
        userid: String, successobject: String, nameprefix: String, isPrefixNull: Boolean
    ) {
        var call: Call<testresponse>? = null
        if (isPrefixNull) {
            val apiClient = ApiClient.client.create(ApiInterFace::class.java)
            call = apiClient.storeTask(
                configtypeId,
                prefix,
                selectedCommunityGroup,
                userid,
                successobject,
                ""
            )
        } else {
            val apiClient = ApiClient.client.create(ApiInterFace::class.java)
            call = apiClient.storeTask(
                configtypeId,
                prefix,
                selectedCommunityGroup,
                userid,
                successobject,
                nameprefix
            )

        }
        call.enqueue(object : Callback<testresponse> {
            override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
                try {
                    if (response.body()?.error == false) {
                        Toast.makeText(context, "${response.body()?.msg}", Toast.LENGTH_SHORT)
                            .show()

                        (context as Activity).finish()
                        btnSubmit?.visibility = View.VISIBLE
                        layoutProgressbar?.visibility = View.GONE
                        progressbar?.visibility = View.GONE

                    } else {
                        Toast.makeText(context, "${response.body()?.msg}", Toast.LENGTH_SHORT)
                            .show()

                        btnSubmit?.visibility = View.VISIBLE
                        layoutProgressbar?.visibility = View.GONE
                        progressbar?.visibility = View.GONE
                    }

                } catch (e: Exception) {
                    AppUtils.logDebug(TAG, "failure" + e.message)

                }

            }

            override fun onFailure(call: Call<testresponse>, t: Throwable) {

                AppUtils.logDebug(TAG, "failure" + t.message)


                btnSubmit?.visibility = View.VISIBLE
                layoutProgressbar?.visibility = View.GONE
                progressbar?.visibility = View.GONE
            }
        })


    }

    fun callApiUpdatetask(
        context: Context,
        configtypeId: String,
        prefix: String,
        selectedCommunityGroup: String,
        userid: String,
        successObject: String,
        nameprefix: String,
        taskid: String
    ) {
        val apiClient = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiClient.updateTask(
            configtypeId,
            prefix,
            selectedCommunityGroup,
            userid,
            successObject,
            nameprefix,
            taskid
        )
        call.enqueue(object : Callback<testresponse> {
            override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
                try {
                    if (response.body()?.error == false) {
                        Toast.makeText(context, "${response.body()?.msg}", Toast.LENGTH_SHORT)
                            .show()

                        (context as Activity).finish()
                        btnSubmit?.visibility = View.VISIBLE
                        layoutProgressbar?.visibility = View.GONE
                        progressbar?.visibility = View.GONE

                    } else {
                        Toast.makeText(context, "${response.body()?.msg}", Toast.LENGTH_SHORT)
                            .show()

                        btnSubmit?.visibility = View.VISIBLE
                        layoutProgressbar?.visibility = View.GONE
                        progressbar?.visibility = View.GONE
                    }

                } catch (e: Exception) {
                    AppUtils.logDebug(TAG, "failure" + e.message)

                }

            }

            override fun onFailure(call: Call<testresponse>, t: Throwable) {

                AppUtils.logDebug(TAG, "failure" + t.message)


                btnSubmit?.visibility = View.VISIBLE
                layoutProgressbar?.visibility = View.GONE
                progressbar?.visibility = View.GONE
            }
        })


    }

    fun addItemToNavigationList(context: Context, privilegeListName: ArrayList<String>) {
        val list = ArrayList<ListNavigationTaskFunction>()
        if (privilegeListName.contains("CanAddPerson")) {
            list.add(
                ListNavigationTaskFunction(
                    "AddPerson",
                    R.drawable.ic_addperson
                )
            )
        }
        if (privilegeListName.contains("CanAddContainer")) {
            list.add(ListNavigationTaskFunction("AddContainer", R.drawable.ic_addcontainer))
        }

        if (privilegeListName.contains("CanAddPack")) {
            list.add(ListNavigationTaskFunction("AddPack", R.drawable.ic_addpack))
        }
        if (privilegeListName.contains("CanTransferPack")) {
            list.add(ListNavigationTaskFunction("TransferPack", R.drawable.ic_transferpack))
        }

        if (privilegeListName.contains("CanSign")) {
//            list.add(ListNavigationTaskFunction("InsertContainer",R.drawable.ic_baseline_person_add_alt_1_24))
        }

        if (privilegeListName.contains("CanAddTaskPackScan")) {
//            list.add(ListNavigationTaskFunction("InsertContainer",R.drawable.ic_baseline_person_add_alt_1_24))
        }
        if (privilegeListName.contains("CanAddTaskContainerScan")) {
//            list.add(ListNavigationTaskFunction("InsertContainer",R.drawable.ic_baseline_person_add_alt_1_24))
        }
        if (privilegeListName.contains("CanAddTaskPersonScan")) {
//            list.add(ListNavigationTaskFunction("PersonScan",R.drawable.personscan))
        }
        if (privilegeListName.contains("CanCancelTask")) {
            list.add(ListNavigationTaskFunction("CancelTask",R.drawable.ic_canceltask))
        }
        if (privilegeListName.contains("CanStartTask")) {
            list.add(ListNavigationTaskFunction("StartTask", R.drawable.ic_starttask))
        }
        if (privilegeListName.contains("CanEndTask")) {
            list.add(ListNavigationTaskFunction("EndTask", R.drawable.ic_stop))
        }
        if (privilegeListName.contains("CanAttachMedia")) {
            list.add(ListNavigationTaskFunction("Photo", R.drawable.ic_camera))
        }

        list.let {
            navlist.postValue(list)

        }
    }

    private fun showStartTaskDialog(context: Context, endtaskorStarttask: String) {
        AlertDialog.Builder(context)
            .setTitle("${endtaskorStarttask} Function")
            .setMessage("Do You want to ${endtaskorStarttask}") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    callTaskExcuteApi(context, endtaskorStarttask, "")
                })
            .setNegativeButton(android.R.string.no, null)
            .show()
    }


    fun checkTaskFunction(context: Context, it: ListNavigationTaskFunction) {
        if (it.name.equals("Photo")) {

        } else if (it.name.equals("StartTask")) {

            selectFunctionName.postValue("StartTask")
            showStartTaskDialog(context, "StartTask")

        } else if (it.name.equals("EndTask")) {
            selectFunctionName.postValue("EndTask")

            showStartTaskDialog(context, "EndTask")
        }
        else if (it.name.equals("CancelTask")) {
            selectFunctionName.postValue("CancelTask")

            showStartTaskDialog(context, "CancelTask")
        } else if (it.name.equals("AddPack")) {
            selectFunctionName.postValue("AddPack")

            showCustomAlert(context,  "175")
        } else if (it.name.equals("TransferPack")) {
            selectFunctionName.postValue("TransferPack")
//            showStartTaskDialog(context, "TransferPack")

            showCustomAlert(context, "219")


        } else if (it.name.equals("AddPerson")) {
            selectFunctionName.postValue("AddPerson")

            Toast.makeText(context, "AddPerson", Toast.LENGTH_SHORT).show()
            showCustomAlert(context,  "171")
        } else if (it.name.equals("AddContainer")) {
            selectFunctionName.postValue("AddContainer")

            Toast.makeText(context, "AddContainer", Toast.LENGTH_SHORT).show()
            showCustomAlert(context,  "176")
        }
       
    }

    private fun showCustomAlert(
        context: Context,
        selectionfunId: String
    ) {
        callApi(selectionfunId, context, updateTaskid.value.toString())


    }

    private fun callTaskExcuteApi(
        context: Context,
        checkFieldFunction: String,
        selectedFunctionFieldid: String
    ) {

        val mUserID = MySharedPreference.getUser(context)?.id.toString()
        val tag = "ViewModelTaskFunctionility"
        val service = ApiClient.client.create(ApiInterFace::class.java)


        val taskID = AppUtils.paramRequestTextBody(updateTaskid.value.toString())

        var functionID: RequestBody? = null


        if (checkFieldFunction.equals("StartTask") || checkFieldFunction.equals("START TASK")
            || checkFieldFunction.equals("START_TASK") || checkFieldFunction.equals("Start Task")
        ) {
            functionID = AppUtils.paramRequestTextBody("169")

        } else if (checkFieldFunction.equals("EndTask") || checkFieldFunction.equals("END TASK")
            || checkFieldFunction.equals("END_TASK") || checkFieldFunction.equals("End Task")
        ) {
            functionID = AppUtils.paramRequestTextBody("170")
        } else if (checkFieldFunction.equals("AddPerson") || checkFieldFunction.equals("ADD_PERSON")
            || checkFieldFunction.equals("ADD PERSON") || checkFieldFunction.equals("Add_Person")
        ) {
            functionID = AppUtils.paramRequestTextBody("171")

        } else if (checkFieldFunction.equals("ADD_PACK") || checkFieldFunction.equals("ADD PACK")
            || checkFieldFunction.equals("AddPack") || checkFieldFunction.equals("Add Pack")
        ) {
            functionID = AppUtils.paramRequestTextBody("175")

        } else if (checkFieldFunction.equals("ADD_CONTAINER") || checkFieldFunction.equals("ADD CONTAINER")
            || checkFieldFunction.equals("Add Container") || checkFieldFunction.equals("Add_Container")
        ) {
            functionID = AppUtils.paramRequestTextBody("176")

        } else if (checkFieldFunction.equals("TRANSFER_PACK") || checkFieldFunction.equals("TRANSFER PACK")
            || checkFieldFunction.equals("Transfer Pack") || checkFieldFunction.equals("Transfer_pack")
        ) {
            functionID = AppUtils.paramRequestTextBody("219")

        }
        else if (checkFieldFunction.equals("PersonScan")) {
            functionID = AppUtils.paramRequestTextBody("217")

        }
        else if (checkFieldFunction.equals("CancelTask")) {
            functionID = AppUtils.paramRequestTextBody("218")

        }
        val fieldID = AppUtils.paramRequestTextBody(selectedFunctionFieldid)

        val userID = AppUtils.paramRequestTextBody(mUserID)
        val mediaTypeID = AppUtils.paramRequestTextBody("")

        val apiInterFace =
            service.uploadFile(null, taskID, functionID!!, userID, fieldID!!, mediaTypeID)
        apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
            override fun onResponse(
                call: Call<ResponseTaskExecution>,
                response: Response<ResponseTaskExecution>
            ) {
                AppUtils.logDebug(tag, response.body().toString())
                val message = response.body()?.msg.toString()
                if (response.body()?.error == false) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                    (context as Activity).finish()
                } else {
                    Toast.makeText(context, " ${message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseTaskExecution>, t: Throwable) {
                try {
                    AppUtils.logError(tag, t.message.toString())
                } catch (e: Exception) {
                    AppUtils.logError(tag, t.message.toString())
                }
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    var myListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int,
                id: Long
            ) {
                when (parent?.id) {

                    R.id.taskfunction -> {
                        if (isTransferPack.value == true) {
                            selectFunctionFiedlId.postValue(
                                allUserList.value?.get(position)?.id?.toDouble().toString()
                            )

                        } else {
                            selectFunctionFiedlId.postValue(functionFieldList.value?.get(position)?.id!!)

                        }
                    }
                    R.id.sp_event_closed -> {
                    }
                    R.id.sp_event_responsible -> {
                    }
                    R.id.sp_event_type -> {
                    }
                    R.id.sp_event_team -> {
                    }
                    R.id.sp_event_status -> {
                    }
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }

    private fun callApi(selectedFunctionId: String, context: Context, updateTaskID: String) {


        if (AppUtils().isInternet(context)) {
            val apiclinet = ApiClient.client.create(ApiInterFace::class.java)

            val call = apiclinet.taskFunctionFieldList(
                MySharedPreference.getUser(context)?.id.toString(),
                updateTaskID, selectedFunctionId
            )
            call.enqueue(object : Callback<ResponseTaskFunctionaliyt> {
                override fun onResponse(
                    call: Call<ResponseTaskFunctionaliyt>,
                    response: Response<ResponseTaskFunctionaliyt>
                ) {

                    if (response.body()?.error == false) {

                        if (response.body()?.msg == "Inserted Successfully") {
//                            Toast.makeText(this, response.body()?.msg.toString(), Toast.LENGTH_SHORT)
//                                .show()
//                            val intent = Intent(this, ViewTaskActivity::class.java)
//                            startActivity(intent)
//                            finish()
                        } else {

                            val baseResponse: ResponseTaskFunctionaliyt = Gson().fromJson(
                                Gson().toJson(response.body()),
                                ResponseTaskFunctionaliyt::class.java
                            )

                            val attachmedia = ArrayList<ListMediaFile>()
                            val fieldListTaskFunctions = ArrayList<FieldListTaskFunctions>()
                            var functionName = ""
                            val function = ArrayList<ListFunctionFieldlist>()
                            val mediatype = ArrayList<Listmedia_types>()

                            if (!baseResponse.TaskFunction.isNullOrEmpty()) {
                                baseResponse.TaskFunction.forEach { tsk ->
                                    fieldListTaskFunctions.add(tsk)
                                    functionName = tsk.name
                                }
                            }
                            if (!baseResponse.Function.isNullOrEmpty()) {
                                baseResponse.Function.forEach {
                                    function.add(it)
                                }
                                functionFieldList.postValue(baseResponse.Function)


                            }
                            setAdapterField(function, context, functionName, updateTaskID)

                            if (!baseResponse.media_types.isNullOrEmpty()) {
                                baseResponse.media_types.forEach {
                                    mediatype.add(it)
                                }


                            }
                            if (functionName == "ATTACH_MEDIA") {

//                                setAdapter(function)
//                                setAdapterFieldMediaType(mediatype)

                            }
                        }

                    } else {
//                        AppUtils.logDebug(tag, "on Failure")
                    }
                }

                override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {
                    try {
//                        AppUtils.logDebug(tag, t.message.toString())
                    } catch (e: java.lang.Exception) {
//                        AppUtils.logDebug(tag, e.message.toString())

                    }
                }

            })


        }
    }

    private fun setAdapterField(
        function: ArrayList<ListFunctionFieldlist>,
        context: Context,
        functionName: String,
        updateTaskID: String
    ) {
        val functionid = ArrayList<String>()
        val functionname = ArrayList<String>()

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

        val inflater: LayoutInflater = (context as Activity).getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_task_function, null)
        dialogBuilder.setView(dialogView)


        val spinner = dialogView.findViewById<View>(R.id.taskfunction) as Spinner
        val pack_label_desciption = dialogView.findViewById<TextView>(R.id.pack_label_desciption)
        val btnExecute: Button = dialogView.findViewById(R.id.btnExecute)
        val btnChooseFile: Button = dialogView.findViewById(R.id.btnChoosefile)
        val taskfunction_media: EditText = dialogView.findViewById(R.id.taskfunction_media)
        val spinnerfield = dialogView.findViewById<View>(R.id.taskfunction_field) as Spinner
        val spinnermediatype = dialogView.findViewById<View>(R.id.field_mediatype) as Spinner
        val spinnerAllUser = dialogView.findViewById<Spinner>(R.id.taskfunction_alluser)
        val label_attachmedia = dialogView.findViewById<TextView>(R.id.label_attachmedia)

     
            if (functionName.equals("TRANSFER_PACK")) {
                label_attachmedia.visibility=View.GONE

                taskfunction_media.visibility = View.VISIBLE
                spinner.visibility=View.VISIBLE

                isTransferPack.postValue(true)
                getTaskObjectList(updateTaskID, taskfunction_media)

                val allUserss = ArrayList<String>()
                for (i in 0 until (allUserList.value)!!.size) {
                    allUserss.add(allUserList.value!!.get(i).name)
                }
                pack_label_desciption.setText("Target User")
                dialogView.label_TargetUser.visibility = View.GONE

                val dd = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    allUserss
                )
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = dd
//            functionFieldList.postValue(allUserList.value)


            }
            else if (functionName.equals("PersonScan")){
                label_attachmedia.visibility=View.VISIBLE
                label_attachmedia.setText("Add PersonScan")

                spinner.visibility=View.GONE
                isTransferPack.postValue(false)
                taskfunction_media.visibility=View.VISIBLE
                taskfunction_media.setText(scannedName.toString())
                selectFunctionFiedlId.postValue(id.toString())

            }
            else {
                label_attachmedia.visibility=View.GONE

                isTransferPack.postValue(false)

                for (i in 0 until function.size) {
                    functionid.add(function.get(i).id.toString())
                    functionname.add(function.get(i).name.toString())
                }
                val label_TargetUser = dialogView.findViewById<TextView>(R.id.label_TargetUser)

                pack_label_desciption.setText(functionName)



                label_TargetUser.visibility = View.GONE

                val dd =
                    ArrayAdapter(this.context, android.R.layout.simple_spinner_item, functionname)
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.setAdapter(dd)
            }
        


//        spinnerfield.visibility=View.VISIBLE
//        spinnermediatype.visibility=View.VISIBLE


        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()

        spinner.setOnItemSelectedListener(myListener)
        btnExecute.setOnClickListener {
            callTaskExcuteApi(context, functionName, selectFunctionFiedlId.value.toString())
        }
    }

    fun getTaskObjectList(taskId: String, taskfunction_media: EditText) {
        val apiInterFace = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiInterFace.getTaskObject(taskId)

        call.enqueue(object : Callback<BaseHttpResponse> {
            override fun onResponse(
                call: Call<BaseHttpResponse>,
                response: Response<BaseHttpResponse>
            ) {

                if (response.body()?.error == false) {
                    taskObjectList.postValue(emptyList())

                    val list = ArrayList<TaskObject>()

                    response.body()?.data?.task_objects?.forEach { it ->
                        if ((it.function == "PACK" || it.function == "CREATE_PACK") && it.status == null) {
                            list.add(it)
                        }


                    }
                    taskObjectList.postValue(list)

                    if (!list.isNullOrEmpty()) {
                        list.forEach { data ->
                            transferPackListNAMEString += " " + data.name + ","
                        }
                        val i = transferPackListNAMEString.lastIndex
                        transferPackListNAMEString.dropLast(i - 1)
                        taskfunction_media.setText(transferPackListNAMEString)


                    }
                } else {
                    taskObjectList.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<BaseHttpResponse>, t: Throwable) {
                println("Get VierwTAskoBject list response Failure " + t.localizedMessage.toString())

            }
        })


    }

    fun callApiScan(content: String, context: Context, scanfunctionId: String) {
        callApiForGetIDofPersonScanned(context, scanfunctionId, content)

    }

    private fun callApiForGetIDofPersonScanned(context: Context, selectedFunctionId: String, content: String) {
        val userid = MySharedPreference.getUser(context)?.id
        val apiInterFace = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiInterFace.scanCodeTaskFunction(
            content, selectedFunctionId.toString(),
            userid.toString()
        )
        call.enqueue(object : Callback<ResponseScanCodeForTaskFunction> {
            override fun onResponse(
                call: Call<ResponseScanCodeForTaskFunction>,
                response: Response<ResponseScanCodeForTaskFunction>
            ) {

                if (response.body()?.error==false) {
                    btnSubmit?.visibility=View.VISIBLE
                    val data= Gson().fromJson(Gson().toJson(response.body()!!.data),
                        ScannedPersonData::class.java )
//                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT)
//                        .show()
                    AppUtils.logDebug("##TAG",data.toString())
                    id= data?.id!!
                    scannedName= data.name.toString()
                    setAdapterField(ArrayList(),context,"PersonScan",updateTaskid.value.toString())
//                    progressVideo?.visibility= View.GONE

//                    edScannedData?.setText(data.name)
                } else {
                    btnSubmit?.visibility=View.GONE
//                    progressVideo?.visibility= View.GONE
                    Toast.makeText(context, response.body()?.msg.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<ResponseScanCodeForTaskFunction>, t: Throwable) {
                btnSubmit?.visibility=View.GONE

//                progressVideo?.visibility= View.GONE
                AppUtils.logError(TAG, t.localizedMessage)
            }

        })

    }
    fun getFileSize(fileVideo: File): Long {
        val sizeinBytes = fileVideo.length()
        val fileSizeInKB = sizeinBytes / 1024
        val fileSizeInMB = fileSizeInKB / 1024
        return fileSizeInMB

    }
    fun uploadFile(
        context: Context,
        mTaskID: String,
        mFunctionID: String,
        mUserID: String,
        mFieldID: String,
        fileVideo: File?,
        progressCircular: CircularProgressIndicator?,
//        progressbarTaskexecute: ProgressBar?,
        progressCircularLabel: TextView?,
        btnExecute: Button?,
        mMediaTypeID: String
    ) {
        this.mprogressCircular=progressCircular
        this.mprogressCircularLabel=progressCircularLabel
        val tag="ViewModelTaskFunctionility"
        val service = ApiClient.client.create(ApiInterFace::class.java)

        val body = if (fileVideo?.let { it1 -> AppUtils().checkImageFile(it1) } == true)
            fileVideo?.let { it1 -> ProgressRequestBody(it1, "image", this) }
        else
            fileVideo?.let { it1 -> ProgressRequestBody(it1, "file", this) }

        val dataVideo: MultipartBody.Part? =
            body?.let {
                MultipartBody.Part.createFormData(
                    "file",
                    fileVideo!!.name,
                    it
                )
            }
        val taskID = AppUtils.paramRequestTextBody(mTaskID)
        val functionID = AppUtils.paramRequestTextBody(mFunctionID)
        val userID = AppUtils.paramRequestTextBody(mUserID)
        val fieldID = AppUtils.paramRequestTextBody(mFieldID)
        val mediaTypeID = AppUtils.paramRequestTextBody(mMediaTypeID)

        val apiInterFace = service.uploadFile(dataVideo, taskID, functionID, userID, fieldID,mediaTypeID)
        apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
            override fun onResponse(
                call: Call<ResponseTaskExecution>,
                response: Response<ResponseTaskExecution>
            ) {
                AppUtils.logDebug(tag, response.body().toString())
                val message = response.body()?.msg.toString()
                if (response.body()?.error == false) {
//                    progressbarTaskexecute?.visibility = View.GONE


                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
//                    progressbarTaskexecute?.visibility = View.GONE
                    btnExecute?.visibility = View.VISIBLE
//                    if (istaskFunctionUpdate) {
//                        context.startActivity(
//                            Intent(
//                                context,
//                                ViewTaskObjectActivity::class.java
//                            )
//                        )
//                    }
                    showToast(context,message)

                    (context as Activity).finish()
                } else {
//                    progressbarTaskexecute?.visibility = View.GONE
                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
//                    progressbarTaskexecute?.visibility = View.GONE

                    btnExecute?.visibility = View.VISIBLE
                    showToast(context,message)


                }
            }

            override fun onFailure(call: Call<ResponseTaskExecution>, t: Throwable) {
//                progressbarTaskexecute?.visibility = View.GONE

                try {
                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
                    btnExecute?.visibility = View.VISIBLE
                    AppUtils.logError(tag, t.message.toString())
                } catch (e: Exception) {
                    progressCircular?.visibility = View.GONE
                    progressCircularLabel?.visibility = View.GONE
                    btnExecute?.visibility = View.VISIBLE
                    AppUtils.logError(tag, t.message.toString())

                }
                showToast(context,"Failed")


            }


        })

    }
    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    override fun onProgressUpdate(percentage: Int) {
        mprogressCircular?.setProgressCompat(percentage, true)
        mprogressCircularLabel?.text = "$percentage%"
    }

}