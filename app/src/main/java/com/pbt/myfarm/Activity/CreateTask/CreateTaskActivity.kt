package com.pbt.myfarm.Activity.CreateTask


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.TaskFunctions.TaskFunctionActivity
import com.pbt.myfarm.Activity.ViewMediaActivity.ViewMediaActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.selectedComunityGroupTask
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.Activity.task_object.ViewTaskObjectActivity
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArray
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArrayId
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.HttpResponse.testresponse

import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_ALLUSERS
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_UPDATE_LIST
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityCreateTaskBinding
import kotlinx.android.synthetic.main.activity_create_task.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class CreateTaskActivity : AppCompatActivity() {

    var viewmodel: CreatetaskViewModel? = null
    var binding: ActivityCreateTaskBinding? = null
    private val TAG = "CreateTaskActivity"
    var viewtask: TasklistDataModel? = null
    var adapter: CreateTaskAdapter? = null
    val successObject = JSONArray()
    var updateTaskList: Task? = null
    val fieldModel = ArrayList<FieldModel>()
    var allUsersList = ArrayList<AllUserList>()
    var configtype: TaskConfig? = null


    companion object {
        var taskcompainionobject: Task? = null
        var checkFieldStatus: HttpResponse? = null
        var taskObject: Task? = null
    }

    override fun onResume() {
        super.onResume()

        initViewModel()
        setButtonListner()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_task)


    }

    private fun setButtonListner() {
        btn_taskfuntion.setOnClickListener {
            val intent = Intent(this, TaskFunctionActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID, updateTaskList)
            intent.putExtra(CONST_TASKFUNCTION_ALLUSERS, allUsersList)
            AppUtils.logError(TAG,"intentlist"+allUsersList.toString())
            startActivity(intent)
        }
        btn_taskobject.setOnClickListener {
            val intent = Intent(this, ViewTaskObjectActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID, updateTaskList)
            startActivity(intent)
        }
        btn_viewmedia.setOnClickListener {
            val intent = Intent(this, ViewMediaActivity::class.java)
            taskObject = updateTaskList
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        ExpAmtArray = ArrayList()
        ExpName = ArrayList()
        ExpNameKey = ArrayList()
        ExpAmtArrayKey = ArrayList()


        ExpAmtArray.clear()
        ExpName.clear()
        ExpNameKey.clear()
        ExpAmtArrayKey.clear()


        if (updateTaskBoolen) {

            updateTaskList = intent.getParcelableExtra(CONST_TASK_UPDATE_LIST)
            taskcompainionobject = updateTaskList

        } else {

            configtype = intent.getParcelableExtra(CONST_VIEWMODELCLASS_LIST)

        }
        if (updateTaskList == null) {
            val buttonTask: Button = findViewById(R.id.btn_taskfuntion)
            buttonTask.visibility = View.GONE
            btn_taskobject.visibility = View.GONE
            btn_viewmedia.visibility = View.GONE
        }


        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CreatetaskViewModel::class.java)

        viewmodel?.layoutProgressbar=layout_ProgressBar

        val communitGroup: Spinner = findViewById(R.id.field_communitygroup)
        setCommunityGroup(communitGroup)



        communitGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                if (!groupArrayId.isNullOrEmpty()) {
                    selectedCommunityGroup = groupArrayId!!.get(position)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        if (updateTaskList != null) {
            btn_create_task.setText("Update Task")
        }

        if (configtype == null) {

            field_prefix.setText(updateTaskList?.taskConfigName)
            field_desciption.setText(updateTaskList?.description)

        } else {
            field_prefix.setText(configtype?.name)
            field_desciption.setText(configtype?.description)
        }
        binding?.viewModel = viewmodel
        viewmodel?.progressbar = createtaskProgressbar

        AppUtils.logDebug(TAG, "updateTaskBoolen==" + updateTaskBoolen)

        if (updateTaskBoolen) {
            viewmodel?.onCheckStatus(this, updateTaskList?.id.toString())

            viewmodel?.onConfigFieldList(this, true, updateTaskList)
        } else {
            viewmodel?.onConfigFieldListFalse(this, configtype)
        }
        viewmodel?.checkstatusObject?.observe(this, androidx.lifecycle.Observer {
            if (it.error == false) {

                checkFieldStatus = it
                viewmodel?.btnSubmit = btn_create_task
                viewmodel?.setEditableOrNot(
                    field_prefix,
                    field_communitygroup,
                    this,
                    field_desciption
                )
            }

        })

        viewmodel?.configlist?.observe(this, androidx.lifecycle.Observer { list ->

            if (!list.isNullOrEmpty()) {
                createtaskProgressbar.visibility = View.GONE
            } else {
                createtaskProgressbar.visibility = View.GONE
                layout_ProgressBar.visibility = View.GONE

            }
            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<ConfigFieldList>()::class.java)
            recycler_taskconfiglist?.layoutManager = LinearLayoutManager(this)

            AppUtils.logDebug(TAG, "list size for taskcreate==${list}")

            adapter = CreateTaskAdapter(
                this, config, updateTaskBoolen,
            ) { list, name ->
                while (list.contains("0")) {
                    list.remove("0")
                }
                while (name.contains("0")) {
                    name.remove("0")
                }


                for (i in 0 until name.size) {

                    val jsonObject = JSONObject()
                    jsonObject.put(ExpAmtArrayKey.get(i), list.get(i))
                    jsonObject.put(ExpNameKey.get(i), name.get(i))

                    successObject.put(jsonObject)

                    if (!AppUtils().isInternet(this)) {
                        val db = DbHelper(this, null)

                        val lastValueOfPacknew = db.getLastofTask()
                        val idd = lastValueOfPacknew.toInt() + 1

//                    addPackValue(list.get(i),name.get(i),idd.toString())
                        if (updateTaskBoolen) {
                            db.addTaskValues(
                                name.get(i), list.get(i), updateTaskList?.id.toString(),
                                true, field_desciption.text.toString()
                            )
                        } else {
                            db.addTaskValues(
                                name.get(i),
                                list.get(i),
                                idd.toString(),
                                false,
                                field_desciption.text.toString()
                            )

                        }
                    }


                    fieldModel.add(FieldModel(name.get(i), list.get(i)))
                }

            }

            recycler_taskconfiglist.adapter = adapter
        })

        viewmodel?.allUserList?.observe(this){ list ->
            if (!list.isNullOrEmpty())
            allUsersList.addAll(list)
            AppUtils.logError(TAG,"alluSerslist"+allUsersList.toString())

        }


        btn_create_task.setOnClickListener {
            layout_ProgressBar.visibility = View.VISIBLE
            createtaskProgressbar.visibility = View.VISIBLE

            adapter?.callBack()

            val userId = MySharedPreference.getUser(this)?.id
            val prefix = field_desciption.text.toString()
            selectedCommunityGroup

            if (!updateTaskBoolen) {
                if (AppUtils().isInternet(this)) {
                    if (configtype?.name_prefix.isNullOrEmpty()) {
                        viewmodel?.callApiStoreTask(this,configtype?.id.toString(),
                        prefix, selectedCommunityGroup,userId.toString(),
                        successObject.toString(),"",true)
//
                    } else {
                        viewmodel?.callApiStoreTask(this,configtype?.id.toString(),
                            prefix, selectedCommunityGroup,userId.toString(),
                            successObject.toString(), configtype?.name_prefix.toString(),false)

//
                    }
                } else {
                    //Store Task Offline In Database
                    viewmodel?.desciption = field_desciption.text.toString()
                    val success =
                        viewmodel?.createTaskOffline(this, configtype, false, updateTaskList)
                    if (success == true) {
                        finish()

                    }

                }

                btn_create_task.visibility = View.GONE

            } else {
                if (AppUtils().isInternet(this)) {
                    btn_create_task.visibility = View.GONE

                    viewmodel?.callApiUpdatetask(this,updateTaskList?.task_config_id.toString(),
                    prefix, selectedCommunityGroup,userId.toString(),
                    successObject.toString(),updateTaskList?.taskConfigNamePrefix.toString() + updateTaskList?.padzero.toString(),
                        updateTaskList?.id.toString() )

                } else {
                    viewmodel?.desciption = field_desciption.text.toString()
                    val success =
                        viewmodel?.createTaskOffline(this, configtype, true, updateTaskList)
                    if (success == true) {
                        finish()

                    }

                }

            }


        }
    }


    private fun setCommunityGroup(communitGroup: Spinner) {
        Handler(Looper.getMainLooper()).postDelayed({
            val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, groupArray!!)
            dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            communitGroup.setAdapter(dd)
            AppUtils.logDebug(TAG, "list of comunitygroup for createtask=${groupArray}")


            if (selectedComunityGroupTask != 0) {
                for (i in groupArrayId!!.indices) {
                    val item = groupArrayId!!.get(i).toDouble().toInt()
                    if (selectedComunityGroupTask == item) {
                        communitGroup.setSelection(i)

                    }

                }

            } else {
                communitGroup.setSelection(0)

            }
        }, 1500)

    }


}


