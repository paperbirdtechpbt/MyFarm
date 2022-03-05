package com.pbt.myfarm.Activity.CreateTask


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.TaskFunctions.TaskFunctionActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArray
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArrayId
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
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


class CreateTaskActivity : AppCompatActivity(), retrofit2.Callback<testresponse> {
    val myCalendar: Calendar = Calendar.getInstance()
    var viewmodel: CreatetaskViewModel? = null
    var binding: ActivityCreateTaskBinding? = null
    private val TAG = "CreateTaskActivity"
    var viewtask: TasklistDataModel? = null
    var adapter: CreateTaskAdapter? = null
    val successObject = JSONArray()
var toolbar:Toolbar?=null
    var updateTaskList: TasklistDataModel? = null


    val fieldModel = ArrayList<FieldModel>()
    var configtype:ConfigTaskList?=null

    companion object {
        var ExpAmtArray = ArrayList<String>()
        var ExpName = ArrayList<String>()
        var ExpNameKey = ArrayList<String>()
        var ExpAmtArrayKey = ArrayList<String>()
        var selectedCommunityGroup = ""

    }


    override fun onCreate(savedInstanceState: Bundle?) {
//        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

 

//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
//        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        ExpAmtArray.clear()
        ExpName.clear()
        ExpNameKey.clear()
        ExpAmtArrayKey.clear()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_task)
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CreatetaskViewModel::class.java)
         configtype = intent.getParcelableExtra(CONST_VIEWMODELCLASS_LIST)
        updateTaskList = intent.getParcelableExtra(AppConstant.CONST_TASK_UPDATE_LIST)
        if (updateTaskList!=null){
            btn_create_task.setText("Update Task")

        }
        else{
            btn_taskFunction.visibility=View.GONE
        }
        btn_taskFunction.setOnClickListener{
            val intent=Intent(this,TaskFunctionActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID,updateTaskList?.id)
            startActivity(intent)
        }

        if (configtype== null) {

            field_prefix.setText(updateTaskList?.name_prefix)
            field_desciption.setText(updateTaskList?.description)
//            btn_create_task.visibility=View.GONE
//            btn_update_task.visibility=View.VISIBLE
        }
        else
        {


            field_prefix.setText(configtype?.name_prefix)
            field_desciption.setText(configtype?.description)
        }




        binding?.viewModel = viewmodel
        viewmodel?.progressbar = createtaskProgressbar
        viewmodel?.onConfigFieldList(this, updateTaskBoolen, updateTaskList?.id.toString())

        viewmodel?.configlist?.observe(this, androidx.lifecycle.Observer { list ->


            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<ConfigFieldList>()::class.java)
            recycler_taskconfiglist?.layoutManager = LinearLayoutManager(this)
            adapter = CreateTaskAdapter(this, config, true) { list, name ->
                while (list.contains("0")) {
                    list.remove("0")
                }
                while (name.contains("0")) {
                    name.remove("0")
                }
                while (ExpNameKey.contains("0")) {
                    ExpNameKey.remove("0")
                }
                while (ExpAmtArrayKey.contains("0")) {
                    ExpAmtArrayKey.remove("0")
                }





                for (i in 0 until name.size) {
                    val jsonObject = JSONObject()
                    jsonObject.put(ExpAmtArrayKey.get(i), name.get(i))
                    jsonObject.put(ExpNameKey.get(i), list.get(i))

                    successObject.put(jsonObject)
                    fieldModel.add(FieldModel(name.get(i), list.get(i)))

                }


            }
            recycler_taskconfiglist.adapter = adapter
        })


        val communitGroup: Spinner = findViewById(R.id.field_communitygroup)
        setCommunityGroup(communitGroup)
        communitGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                selectedCommunityGroup = groupArrayId!!.get(position)


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


//        viewmodel?.configlist?.observe(this, Observer { configlist ->
////            recycler_taskconfiglist?.layoutManager = LinearLayoutManager(this)
//
////            adapter = CreateTaskAdapter(this, configlist!!)
////            { position, taskname ,list->
////
////                val intent = Intent(this, CreateTaskActivity::class.java)
////                intent.putExtra(AppConstant.CONST_VIEWMODELCLASS_LIST,list)
////                startActivity(intent)
////
////            }
////            recycler_taskconfiglist.adapter = adapter
//
//        })
//        setSpinner()


//
//        btn_update_task.setOnClickListener{
//            viewmodel?.update()
//
//
//        }
        btn_create_task.setOnClickListener {
            adapter?.callBack()
            val listdata = ArrayList<String>()

            if (successObject != null) {
                for (i in 0 until successObject.length()) {
                    listdata.add(successObject.getString(i))
                }
            }

//            AppUtils.logDebug(TAG,"-->>"+successObject)
            val userId = MySharedPreference.getUser(this)?.id
            val prefix = field_desciption.text.toString()
            selectedCommunityGroup




            if(!updateTaskBoolen){
            ApiClient.client.create(ApiInterFace::class.java)
                .storeTask(
                    updateTaskList?.id.toString(), prefix, selectedCommunityGroup, userId.toString(),
                    successObject.toString(), updateTaskList?.name_prefix.toString()
                ).enqueue(this)}
            else{
                ApiClient.client.create(ApiInterFace::class.java)
                    .updateTask(
                      "10", prefix, selectedCommunityGroup, userId.toString(),
                        successObject.toString(), updateTaskList?.name_prefix.toString(),
                                updateTaskList?.id.toString() ).enqueue(this)
            }
//            CallApiForNewTask(fieldModel,userId,configtype?.id,selectedCommunityGroup.toInt()
//                ,prefix,configtype!!.name_prefix)


//            viewmodel?.login(it)
//            finish()
//            AppUtils.logDebug(TAG, ravi)
        }
//        if (ViewTaskActivity.mytasklist !=null){
//            AppUtils.logDebug(TAG,viewtask.toString())
//            btn_create_task.visibility=View.GONE
//            btn_update_task.visibility=View.VISIBLE
//            lablel_createnewtask.setText("Update A Task")
////            val viewtask = intent.getParcelableExtra<ViewTaskModelClass>(CONST_VIEWMODELCLASS_LIST)
//          setdata(ViewTaskActivity.mytasklist)
//
//
//        }
//
//        else {
//
//
//            val configTypeName: String? = intent.getStringExtra(CONST_CONFIGTYPE_NAME)
//            val num: Int = intent.getIntExtra(CONST_CONFIGTYPE_TYPE_ID, -1)
//
//            viewmodel?.confiType?.set(configTypeName)
//            setPrefix(num)
//        }
//        ed_expectedDate.setOnClickListener(this)
//        ed_endDate.setOnClickListener(this)
//        ed_startDate.setOnClickListener(this)
//        ed_expectedEndDate.setOnClickListener(this)
//    }

//    private fun setdata(viewtask: TasklistDataModel?) {
//
//        viewmodel?.namePrefix?.set(viewtask?.name)
//        viewmodel?.confiType?.set(viewtask?.task_config_name)
//        viewmodel?.desciption?.set(viewtask?.description)
//        viewmodel?.expectedStartDate?.set("Test")
//        viewmodel?.expectedEndDate?.set("Test")
//        viewmodel?.startDate?.set("Test")
//        viewmodel?.EndDate?.set("Test")
//
//    }
//
//    private fun updateLabel(view: View?) {
//        val myFormat = "yyyy-MM-dd"
//        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
//        when(view?.id){
//            R.id.ed_expectedDate -> {
//                ed_expectedDate.setText(dateFormat.format(myCalendar.time))
//            }
//            R.id.ed_endDate -> {
//                ed_endDate.setText(dateFormat.format(myCalendar.time))
//
//            }
//            R.id.ed_startDate -> {
//                ed_startDate.setText(dateFormat.format(myCalendar.time))
//            }
//            R.id.ed_expectedEndDate -> {
//                ed_expectedEndDate.setText(dateFormat.format(myCalendar.time))
//            }
//        }
//
//    }


//    private fun setPrefix(num: Int) {
//        when (num) {
//            0 ->   viewmodel?.namePrefix?.set("TRP")
//            1 ->viewmodel?.namePrefix?.set("VTST")
//            2 -> viewmodel?.namePrefix?.set("TST3")
//            3 -> viewmodel?.namePrefix?.set("FRMTN")
//            4 -> viewmodel?.namePrefix?.set("NET")
//            5 -> viewmodel?.namePrefix?.set("TSK")
//            6 -> viewmodel?.namePrefix?.set("DEFRM")
//        }
//    }

//    private fun setSpinner() {
//        ArrayAdapter.createFromResource(
//            this, R.array.array_communitygroup, android.R.layout.simple_spinner_item
//        ).also { adapter ->
//
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner_Grip.adapter = adapter
//        }
//
//    }
//
//    override fun onClick(view: View?) {
//        val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->
//            myCalendar.set(Calendar.YEAR, year)
//            myCalendar.set(Calendar.MONTH, month)
//            myCalendar.set(Calendar.DAY_OF_MONTH, day)
//            updateLabel(view)
//        }

//        DatePickerDialog(
//            this, date, myCalendar.get(Calendar.YEAR),
//            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
//        ).show()


    }

    private fun CallApiForNewTask(
        listdata: ArrayList<FieldModel>,
        userId: Int?,
        id: String?,
        selectedCommunityGroup: Int,
        prefix: String,
        name: String
    ) {

//        ApiClient.client.create(ApiInterFace::class.java)
//            .storeTask(id!!,prefix,selectedCommunityGroup,userId!!,
//                listdata,name).enqueue(this)

        ApiClient.client.create(ApiInterFace::class.java)
            .storeTask(
                "10", "refix", "1", "2",
                listdata.toString(), "TRP"
            ).enqueue(this)
//    ApiClient.client.create(ApiInterFace::class.java).storeTask().enqueue(this)
//

    }

    private fun setCommunityGroup(communitGroup: Spinner) {
        Handler(Looper.getMainLooper()).postDelayed({
            val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, groupArray!!)
            dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            communitGroup.setAdapter(dd)
        },2500)

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        Toast.makeText(this, "Task Updated Successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ViewTaskActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logDebug(TAG, "failure" + t.message)
        Toast.makeText(this, t.localizedMessage, Toast.LENGTH_SHORT).show()
    }

}


