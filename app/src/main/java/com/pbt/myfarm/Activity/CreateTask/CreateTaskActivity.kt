package com.pbt.myfarm.Activity.CreateTask


import android.content.Intent
import android.content.res.ColorStateList
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
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListNameOffline
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.TaskFunctions.TaskFunctionActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.selectedComunityGroupTask
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.Activity.task_object.ViewTaskObjectActivity
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArray
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArrayId
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_UPDATE_LIST
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityCreateTaskBinding
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.itemcongiffeildlist.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class CreateTaskActivity : AppCompatActivity(), retrofit2.Callback<testresponse> {
    val myCalendar: Calendar = Calendar.getInstance()
    var viewmodel: CreatetaskViewModel? = null
    var binding: ActivityCreateTaskBinding? = null
    private val TAG = "CreateTaskActivity"
    var viewtask: TasklistDataModel? = null
    var adapter: CreateTaskAdapter? = null
    val successObject = JSONArray()
    var toolbar: Toolbar? = null

    //    var updateTaskList: TasklistDataModel? = null
    var updateTaskList: Task? = null


    val fieldModel = ArrayList<FieldModel>()
    var configtype: TaskConfig? = null

    companion object {
//        var ExpAmtArray = ArrayList<String>()
//        var ExpName = ArrayList<String>()
//        var ExpNameKey = ArrayList<String>()
//        var ExpAmtArrayKey = ArrayList<String>()
//        var selectedCommunityGroup = ""

    }


    override fun onCreate(savedInstanceState: Bundle?) {
//        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_task)


//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
//        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
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

        } else {

            configtype = intent.getParcelableExtra(CONST_VIEWMODELCLASS_LIST)

        }
        if (updateTaskList == null) {
            val buttonTask: Button = findViewById(R.id.btn_taskfuntion)
            buttonTask.visibility = View.GONE
            btn_taskobject.visibility = View.GONE
        }

//        if (checkInternetConnection()) {
//            initViewModel()
//        }
        initViewModel()

        btn_taskfuntion.setOnClickListener {
            val intent = Intent(this, TaskFunctionActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID, updateTaskList)
            startActivity(intent)
        }
        btn_taskobject.setOnClickListener {
            val intent = Intent(this, ViewTaskObjectActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID, updateTaskList)
            startActivity(intent)
        }
    }

    private fun initViewModel() {

        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CreatetaskViewModel::class.java)

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
            viewmodel?.onConfigFieldList(this, true, updateTaskList)
        }
        else {
            viewmodel?.onConfigFieldListFalse(this, configtype)
        }

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

            adapter = CreateTaskAdapter(this, config, updateTaskBoolen) { list, name ->
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

        val communitGroup: Spinner = findViewById(R.id.field_communitygroup)
        setCommunityGroup(communitGroup)

        viewmodel?.setEditableOrNot(field_prefix, field_communitygroup,this)


        communitGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                selectedCommunityGroup = groupArrayId!!.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btn_create_task.setOnClickListener {

            adapter?.callBack()

            val userId = MySharedPreference.getUser(this)?.id
            val prefix = field_desciption.text.toString()
            selectedCommunityGroup

            if (!updateTaskBoolen) {
                if (AppUtils().isInternet(this)) {
                    if (configtype?.name_prefix.isNullOrEmpty()) {
                        ApiClient.client.create(ApiInterFace::class.java)
                            .storeTask(
                                configtype?.id.toString(),
                                prefix,
                                selectedCommunityGroup,
                                userId.toString(),
                                successObject.toString(),
                                ""
                            ).enqueue(this)
                    } else {
                        ApiClient.client.create(ApiInterFace::class.java)
                            .storeTask(
                                configtype?.id.toString(),
                                prefix,
                                selectedCommunityGroup,
                                userId.toString(),
                                successObject.toString(),
                                configtype?.name_prefix.toString()
                            ).enqueue(this)
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

                    ApiClient.client.create(ApiInterFace::class.java)
                        .updateTask(
                            updateTaskList?.task_config_id.toString(),
                            prefix,
                            selectedCommunityGroup,
                            userId.toString(),
                            successObject.toString(),
                            updateTaskList?.taskConfigNamePrefix.toString() + updateTaskList?.padzero.toString(),
                            updateTaskList?.id.toString()
                        ).enqueue(this)
                } else {
                    viewmodel?.desciption = field_desciption.text.toString()
                    var success =
                        viewmodel?.createTaskOffline(this, configtype, true, updateTaskList)
                    if (success == true) {
                        finish()

                    }

                }

            }

            layout_ProgressBar.visibility = View.VISIBLE
            createtaskProgressbar.visibility = View.VISIBLE

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

            }
        }, 1500)

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        try {
            if (response.body()?.error == false) {
                Toast.makeText(this, "${response.body()?.msg}", Toast.LENGTH_SHORT).show()

                ViewTaskActivity().finish()
                val intent = Intent(this, ViewTaskActivity::class.java)
                startActivity(intent)
                finish()
                btn_create_task.visibility = View.VISIBLE
                layout_ProgressBar.visibility = View.GONE
                createtaskProgressbar.visibility = View.GONE

            } else {
                Toast.makeText(this, "${response.body()?.msg}", Toast.LENGTH_SHORT).show()

                btn_create_task.visibility = View.VISIBLE
                layout_ProgressBar.visibility = View.GONE
                createtaskProgressbar.visibility = View.GONE
            }

        } catch (e: Exception) {
            AppUtils.logDebug(TAG, "failure" + e.message)

        }

    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {

        AppUtils.logDebug(TAG, "failure" + t.message)


        btn_create_task.visibility = View.VISIBLE
        layout_ProgressBar.visibility = View.GONE
        createtaskProgressbar.visibility = View.GONE
    }


}


