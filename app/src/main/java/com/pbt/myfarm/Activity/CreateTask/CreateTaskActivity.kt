package com.pbt.myfarm.Activity.CreateTask


import android.content.Intent
import android.net.ConnectivityManager
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
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArray
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArrayId
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
    var toolbar:Toolbar?=null
//    var updateTaskList: TasklistDataModel? = null
    var updateTaskList: Task? = null


    val fieldModel = ArrayList<FieldModel>()
    var configtype:TaskConfig?=null

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
        ExpAmtArray= ArrayList()
        ExpName=ArrayList()
        ExpNameKey=ArrayList()
        ExpAmtArrayKey=ArrayList()


        ExpAmtArray.clear()
        ExpName.clear()
        ExpNameKey.clear()
        ExpAmtArrayKey.clear()

        if (updateTaskBoolen){
            updateTaskList = intent.getParcelableExtra(CONST_TASK_UPDATE_LIST)
            AppUtils.logDebug(TAG," true updateTaskList"+updateTaskList.toString())

        }
        else{
            AppUtils.logDebug(TAG,"false updateTaskList"+updateTaskList.toString())

            configtype = intent.getParcelableExtra(CONST_VIEWMODELCLASS_LIST)

        }
        if (updateTaskList==null){
            val buttonTask:Button=findViewById(R.id.btn_taskfuntion)
            buttonTask.visibility=View.GONE
        }

//        if (checkInternetConnection()) {
//            initViewModel()
//        }
        initViewModel()

        btn_taskfuntion.setOnClickListener{
          val intent=  Intent(this,TaskFunctionActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID,updateTaskList)
            AppUtils.logDebug(TAG,"btntaskFUnction="+updateTaskList?.task_config_id)
            startActivity(intent)
        }
    }

    private fun initViewModel() {

            viewmodel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get(CreatetaskViewModel::class.java)

            if (updateTaskList!=null){
                btn_create_task.setText("Update Task")

            }

            if (configtype== null) {

                field_prefix.setText(updateTaskList?.taskConfigNamePrefix)
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
        if (updateTaskBoolen){

            viewmodel?.onConfigFieldList(this,
                updateTaskBoolen,updateTaskList)

            updateTaskBoolen=false

        }
        else{
            viewmodel?.onConfigFieldListFalse(this, configtype)
        }


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
//                    while (ExpNameKey.contains("0")) {
//                        ExpNameKey.remove("0")
//                    }
//                    while (ExpAmtArrayKey.contains("0")) {
//                        ExpAmtArrayKey.remove("0")
//                    }


                    for (i in 0 until name.size) {

                        val jsonObject = JSONObject()
                        jsonObject.put(ExpAmtArrayKey.get(i), list.get(i))
                        jsonObject.put(ExpNameKey.get(i), name.get(i))

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
//                val listdata = ArrayList<String>()
//
//
//                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//                val currentDate = sdf.format(Date())
//                val db=DbHelper(this,null)
//                val viewtasklist=ArrayList<Task>()
//
//                val d=db.getLastValue_task_new("12")
//
//                if (d.isEmpty()){
//                    viewtasklist.add(
//                        Task(selectedCommunityGroup.toInt(), MySharedPreference.getUser(this)?.id!!,
//                            currentDate,
//                            "",field_desciption.text.toString(),
//                            0, MySharedPreference.getUser(this)?.id!!,
//                             MySharedPreference.getUser(this)?.id!!,"",1,1,
//                            "0", configtype?.id?.toInt()!!,0)
//                    )
//                    val viewTask=viewtasklist.get(0)
//                    db.tasks_create(viewTask)
////                    for(i in 0 until ExpAmtArray.size){
////                        db.addtaskFieldValue("1", ExpName.get(i), ExpAmtArray.get(i))
////                    }
//
//                }
//                else{
//                    val newtaskname:Int=  d.toInt()+1
//
//                    viewtasklist.add(
//                        Task(selectedCommunityGroup.toInt(), MySharedPreference.getUser(this)?.id!!,
//                            currentDate,
//                            "",field_desciption.text.toString(),
//                            0, MySharedPreference.getUser(this)?.id!!,
//                            MySharedPreference.getUser(this)?.id!!,"",newtaskname,1,
//                            "0", configtype?.id?.toInt()!!,0))
//
//                   val mytask=viewtasklist.get(0)
//
//                    db.tasks_create(mytask)
//
//                    while (ExpName.contains("0")){
//                        ExpName.remove("0")
//                    }
//                    while (ExpAmtArray.contains("0")){
//                        ExpAmtArray.remove("0")
//                    }
////                    for(i in 0 until ExpAmtArray.size){
////                        db.addtaskFieldValue(newtaskname.toString(), ExpName.get(i), ExpAmtArray.get(i))
////                    }
//                }
//
//
//
//                if (successObject != null) {
//                    for (i in 0 until successObject.length()) {
//                        listdata.add(successObject.getString(i))
//                    }
//                }
//
//            AppUtils.logDebug(TAG,"-->>"+successObject)
                val userId = MySharedPreference.getUser(this)?.id
                val prefix = field_desciption.text.toString()
                selectedCommunityGroup


                layout_ProgressBar.visibility=View.VISIBLE

                if(!updateTaskBoolen){
                    if (configtype?.name_prefix.isNullOrEmpty()){
                        ApiClient.client.create(ApiInterFace::class.java)
                            .storeTask(
                                configtype?.id.toString(), prefix, selectedCommunityGroup, userId.toString(),
                                successObject.toString(), ""
                            ).enqueue(this)
                    }
                    else{
                        ApiClient.client.create(ApiInterFace::class.java)
                            .storeTask(
                                configtype?.id.toString(), prefix, selectedCommunityGroup, userId.toString(),
                                successObject.toString(), configtype?.name_prefix.toString()
                            ).enqueue(this)
                    }
                    btn_create_task.visibility=View.GONE
                    AppUtils.logDebug(TAG,  "upfateTaskboolean False"+  configtype?.id.toString()+"\n" +prefix+
                            "\n"+selectedCommunityGroup+"\n"+ userId.toString()+"\n"+

                            successObject.toString()+"\n"+ configtype?.name_prefix.toString())
                   }
                else{
                    btn_create_task.visibility=View.GONE
                    AppUtils.logDebug(TAG,"updateTaskboolean true"+    updateTaskList?.id.toString()+"\n" +prefix+
                            "\n"+selectedCommunityGroup+"\n"+ userId.toString()+"\n"+


                            successObject.toString()+"\n"+ updateTaskList?.taskConfigNamePrefix.toString()+"\n"+
                            updateTaskList?.id.toString())

                    ApiClient.client.create(ApiInterFace::class.java)
                        .updateTask(
                            updateTaskList?.task_config_id.toString(), prefix, selectedCommunityGroup, userId.toString(),
                            successObject.toString(), updateTaskList?.taskConfigNamePrefix.toString()+updateTaskList?.padzero.toString(),
                            updateTaskList?.id.toString() ).enqueue(this)
                }

//                -------|||do not uncomment  below code-------------------------|||||||
//
//            CallApiForNewTask(fieldModel,userId,configtype?.id,selectedCommunityGroup.toInt()
//                ,prefix,configtype!!.name_prefix)
//
//
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

        //---------||||| do not uncommnet above code ---------------|||||||||||||----------------||||||||||||||||||||||


    }

    private fun checkInternetConnection(): Boolean {
        val ConnectionManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            return true
        } else {
            return false
        }
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
        },1500)

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        try {
            if (response.body()?.error==false){
                Toast.makeText(this, "${response.body()?.msg}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ViewTaskActivity::class.java)
                startActivity(intent)
                finish()
                btn_create_task.visibility=View.VISIBLE
                layout_ProgressBar.visibility=View.GONE

            }
            else{
                Toast.makeText(this, "${response.body()?.msg}", Toast.LENGTH_SHORT).show()

                btn_create_task.visibility=View.VISIBLE
                layout_ProgressBar.visibility=View.GONE
            }

        }
        catch (e:Exception){
            AppUtils.logDebug(TAG, "failure" + e.message)

        }

    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        try {
            AppUtils.logDebug(TAG, "failure" + t.message)

        }
        catch (e:Exception){
            AppUtils.logDebug(TAG, "failure" + e.message)

        }

        btn_create_task.visibility=View.VISIBLE
        layout_ProgressBar.visibility=View.GONE
    }

}


