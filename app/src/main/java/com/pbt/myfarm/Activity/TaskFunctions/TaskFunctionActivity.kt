package com.pbt.myfarm.Activity.TaskFunctions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abedelazizshe.lightcompressorlibrary.CompressionListener
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor
import com.abedelazizshe.lightcompressorlibrary.VideoQuality
import com.abedelazizshe.lightcompressorlibrary.config.Configuration
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.TaskFunctions.ViewModel.ViewModelTaskFunctionality
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CAMERA_REQUEST
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_ALLUSERS
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_OBJECT
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_OBJECTI_ISUPDATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKLIST
import com.pbt.myfarm.Util.AppConstant.Companion.GALARY_REQUEST
import com.pbt.myfarm.Util.AppConstant.Companion.VIDEO_CAPTURE
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.FilePath
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_task_function.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class TaskFunctionActivity : AppCompatActivity(), ProgressRequestBody.UploadCallback,
    Callback<ResponseTaskFunctionaliyt>, PickiTCallbacks {

    private val tag: String = "TaskFunctionActivity"
    private var updateTaskID: Task? = null
    private var allUserList: ArrayList<AllUserList>? = null
    private var viewModel: ViewModelTaskFunctionality? = null
    private var spinner: Spinner? = null
    private var selectedFunctionId = 0
    private var selectedFunctionFieldId = 0
    private var edAttachMedia: EditText? = null
    private var manager: DownloadManager? = null
    private var recordedVideoPath: String = ""
    private var fileVideo: File? = null
    private var progressCircular: CircularProgressIndicator? = null
    private var progressCircularLabel: TextView? = null
    private var progressCircularTitle: TextView? = null
    private var taskFunction: Spinner? = null
    private var isTaskFunctionUpdate = false
    private var selectedFunctionName = ""
    private var selectedFunctionNo = ""
    private var db: DbHelper? = null
    private var checkStats: HttpResponse? = null
    private val listLocalPrivilegesName = ArrayList<String>()
    private var taskObject: TaskObject? = null
    private val uris = mutableListOf<Uri>()
    private var fileSizeInMB: Long? = null
    private var transferPackList = ArrayList<TaskObject>()
    private var transferPackListID = ArrayList<String>()
    private var transferPackListNAME = ArrayList<String>()
    private var transferPackListNAMEString = ""


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_function)

        db = DbHelper(this, null)
        db?.getAllRolePrivilegesName()?.let {
            listLocalPrivilegesName.addAll(it)
        }

        spinner = findViewById(R.id.taskfunction_field)
        progressCircular = findViewById(R.id.progress_circular)
        progressCircularLabel = findViewById(R.id.progress_circular_label)
        progressCircularTitle = findViewById(R.id.progress_circular_title)

        edAttachMedia = findViewById(R.id.taskfunction_media)
        taskFunction = findViewById(R.id.taskfunction)

        if (intent.extras != null) {
            updateTaskID = intent.getParcelableExtra(CONST_TASKFUNCTION_TASKID)
            allUserList = intent.getParcelableArrayListExtra(CONST_TASKFUNCTION_ALLUSERS)
            AppUtils.logError(tag,"alluserlist"+allUserList.toString())
            isTaskFunctionUpdate =
                intent.getBooleanExtra(CONST_TASKFUNCTION_OBJECTI_ISUPDATE, false)

            if (intent.getParcelableExtra<TaskObject>(CONST_TASKFUNCTION_OBJECT) != null) {
                taskObject =
                    taskObject ?: intent.getParcelableExtra(CONST_TASKFUNCTION_OBJECT)
                selectedFunctionName = taskObject?.function ?: ""
                selectedFunctionNo = taskObject?.no ?: ""
            }

            updateTaskID?.let {
                initViewModel(it.task_config_id.toString(), it.id.toString())
            }

        }

        recycler_viewMedia?.layoutManager = LinearLayoutManager(this)
        recycler_viewMedia?.visibility = View.GONE
//        label_filename?.visibility = View.GONE

        btnChoosefile.setOnClickListener {
            val options = arrayOf<CharSequence>(
                "Take Photo",
                "Choose from Gallery",
                "Capture Video",
                "Cancel"
            )
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Choose your profile picture")
            builder.setItems(options) { dialog, item ->
                if (options[item] == "Take Photo") {
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        setResult(CAMERA_REQUEST, cameraIntent)
                        resultLauncher.launch(cameraIntent)
                    } else
                        checkPermission()
                } else if (options[item] == "Choose from Gallery") {
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        val pickPhoto = Intent()
                        pickPhoto.type = "*/*"
                        pickPhoto.action = Intent.ACTION_GET_CONTENT
                        setResult(GALARY_REQUEST, pickPhoto)
                        galleryLauncher.launch(pickPhoto)
                    } else
                        checkPermission()
                } else if (options[item] == "Capture Video") {
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        openCameraToCaptureVideo()
                    } else
                        checkPermission()
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }

        btnExecute.setOnClickListener {
            progressbar_taskexecute.visibility = View.VISIBLE


            if (selectedFunctionId == 172 || selectedFunctionId == 168) {
                progressbar_taskexecute.visibility = View.GONE

                viewModel?.showToast(this, "Under Development")

            } else if (selectedFunctionId == 174) {
                if (checkStats?.status == "completed" || checkStats?.status == null) {
                    if (checkStats?.status != "completed") {

                        viewModel?.showToast(this, "Task Not Started")

                    } else {
                        viewModel?.showToast(this, "Task is Ended")


                    }
                }
            } else {
                if (selectedFunctionId == 173) {

                    if (fileSizeInMB!! < 40) {
                        progressbar_taskexecute.visibility = View.GONE

                        progress_circular?.visibility = View.VISIBLE
                        progress_circular_label?.visibility = View.VISIBLE

                        callApiTaskExecuteButton()

                        btnExecute.visibility = View.GONE


                    } else {
                        progressbar_taskexecute.visibility = View.GONE
                        viewModel?.showToast(this, "File Must be Less than 40MB")

                    }
                } else {
                    progressbar_taskexecute?.visibility = View.VISIBLE
                    callApiTaskExecuteButton()

                }

            }

            btnExecute.visibility = View.GONE

        }
    }


    private fun callApiTaskExecuteButton() {
        val mTaskID = updateTaskID?.id.toString()
        val mFunctionID = selectedFunctionId.toString()
        val mUserID = MySharedPreference.getUser(this)?.id.toString()
        val mFieldID = selectedFunctionFieldId.toString()

        if (AppUtils().isInternet(this)) {
            viewModel?.uploadFile(
                this,
                mTaskID,
                mFunctionID,
                mUserID,
                mFieldID,
                fileVideo,
                progressCircular,
                progressbar_taskexecute,
                progressCircularLabel,
                btnExecute,
                isTaskFunctionUpdate
            )

        } else {

            val db = DbHelper(this, null)
            val taskObject = TaskObject(
                task_id = updateTaskID?.id, container = selectedFunctionFieldId.toString(),
                function = selectedFunctionId.toString(),
            )
            val isSuccess = db.addTaskObjectOffline(taskObject, "1")
            if (isSuccess) {
                progressbar_taskexecute.visibility = View.GONE
                viewModel?.showToast(this@TaskFunctionActivity, "SuccessFull")

            } else {
                progressbar_taskexecute.visibility = View.GONE
                viewModel?.showToast(this@TaskFunctionActivity, "Database Error")


            }
        }
    }

    private fun initViewModel(taskConfigID: String, updateTaskID: String) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewModelTaskFunctionality::class.java]
        viewModel?.context = this@TaskFunctionActivity
        viewModel?.progressVideo = progressbar_taskexecute

        btnScanCode.setOnClickListener {
            openZingScanner()
        }
        viewModel?.oncheckStatuApi(this, updateTaskID)


        viewModel?.checkstatus?.observe(this) {
            if (it != null) {
                checkStats = it
            }

        }


        viewModel?.id?.observe(this) {
            progressbar_taskexecute.visibility = View.GONE

            if (it != 0) {
                progressbar_taskexecute.visibility = View.GONE
                selectedFunctionFieldId = it
            }
        }

        viewModel?.onTaskFunctionList(
            taskConfigID,
            this,
            updateTaskID,
            MySharedPreference.getUser(application)?.id.toString()
        )
        viewModel?.listTaskFuntions?.observe(this) { list ->

            val taskFunctionList = ArrayList<ListTaskFunctions>()

            if (!list.isNullOrEmpty()) {

                for (i in list.indices) {
                    val item = list[i].name1

                    if (item!!.contains("CREATE_PACK") || item.contains("ADD_PACK")) {
                        if (!privilegeListName.contains("Pack") && !privilegeListName.contains("InsertPack")) {

                        } else
                            taskFunctionList.add(list[i])
                    } else {
                        taskFunctionList.add(list[i])
                    }
                }
                setSpinner(taskFunctionList, taskFunction!!)

            }
        }

    }

    private fun openZingScanner() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setPrompt("My Farm Code Scanner")
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.initiateScan()
    }

    private fun setSpinner(list: List<ListTaskFunctions>, taskFunction: Spinner) {

        val listName = ArrayList<String>()
        val listID = ArrayList<String>()

        list.forEach {

            val privilegeName = it.privilegeName

            if (AppUtils().isInternet(this)) {
                if (privilegeListName.contains(privilegeName)) {
                    listName.add(it.name1!!)
                    listID.add(it.name!!)
                }
            } else {
                if (!privilegeName.isNullOrEmpty()) {
                    if (listLocalPrivilegesName.contains(privilegeName)) {
                        listName.add(it.name1!!)
                        listID.add(it.name!!)
                    }

                } else {
                    listName.add(it.name!!)
                    listID.add(it.id!!)
                }
            }
        }

        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, listName)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskFunction.adapter = dd

        var selectedID = ""
        when (selectedFunctionName) {
            "PERSON" -> {
                selectedID = "171"
            }
            "PACK" -> {
                selectedID = "175"
            }
            "CONTAINER" -> {
                selectedID = "176"
            }
        }

        if (selectedFunctionName.isNotEmpty()) {
            for ((count, item) in listID.withIndex()) {
                if (item == selectedID) {
                    taskFunction.setSelection(count)
                }
            }
        }
        setListener(taskFunction, listID)
    }

    private fun setListener(spinnerTaskFunction: Spinner, listOfID: ArrayList<String>) {
        spinnerTaskFunction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                selectedFunctionId = listOfID[position].toDouble().toInt()
                scanned_Data.setText("")

                if (selectedFunctionId == 0) {
                    hideButtonVisibility(false)

                    label_TargetUser.visibility = View.GONE
                    taskfunction_field.visibility = View.GONE
                    label_fieldname.visibility = View.GONE
                    recycler_viewMedia.visibility = View.GONE
                    taskfunction_alluser.visibility = View.GONE
                    taskfunction_media.visibility = View.GONE
                    btnChoosefile.visibility = View.GONE
                    label_attachmedia.visibility = View.GONE
                } else {
                    val item = listOfID[position]
                    if (selectedFunctionId == 171 || selectedFunctionId == 176) {
                        hideButtonVisibility(false)

                        scanned_Data.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btnChoosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                        taskfunction_alluser.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_TargetUser.visibility = View.GONE
                        taskfunction_field.visibility = View.VISIBLE
                        label_fieldname.visibility = View.VISIBLE

                        if (selectedFunctionId == 217) {
                            label_fieldname.setText(R.string.addPerson)

                            callApi("171")
                        }
                        if (selectedFunctionId == 171) {
                            label_fieldname.setText(R.string.addPerson)

                            callApi("171")
                        } else {
                            label_fieldname.setText(R.string.addContainer)
                            callApi("176")
                        }
                    } else if (selectedFunctionId == 173) {
                        hideButtonVisibility(false)

                        scanned_Data.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        label_TargetUser.visibility = View.GONE
                        taskfunction_media.visibility = View.VISIBLE
                        btnChoosefile.visibility = View.VISIBLE
                        label_attachmedia.visibility = View.VISIBLE
                        recycler_viewMedia.visibility = View.VISIBLE
                        taskfunction_alluser.visibility = View.GONE

                        label_attachmedia.text = getString(R.string.attach_media)
                        callApi("173")

                    } else if (selectedFunctionId == 168 || selectedFunctionId == 169 || selectedFunctionId == 170) {

                        hideButtonVisibility(false)
                        scanned_Data.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        label_TargetUser.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btnChoosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        taskfunction_alluser.visibility = View.GONE
                    } else if (item == "174") {
                        hideButtonVisibility(false)

                        if (checkStats?.status == "completed" || checkStats?.status == null) {
                            if (checkStats?.status != "completed") {
                                viewModel?.showToast(this@TaskFunctionActivity, "Task Not Started")


                            } else {
                                viewModel?.showToast(this@TaskFunctionActivity, "Task is Ended")
                            }
                        } else {
                            val intent =
                                Intent(
                                    this@TaskFunctionActivity,
                                    PackConfigListActivity::class.java
                                )
                            intent.putExtra(CONST_TASKFUNCTION_TASKLIST, updateTaskID)
                            startActivity(intent)
                            finish()

                        }
                        scanned_Data.visibility = View.GONE
                        label_TargetUser.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        taskfunction_alluser.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btnChoosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                    } else if (item == "175") {
                        hideButtonVisibility(false)

                        scanned_Data.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btnChoosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
//                        label_filename.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_TargetUser.visibility = View.GONE
                        taskfunction_field.visibility = View.VISIBLE
                        label_fieldname.visibility = View.VISIBLE
                        taskfunction_alluser.visibility = View.GONE

                        callApi("175")
                    } else if (item == "217" || item == "215" || item == "216") {
                        hideButtonVisibility(true)
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        scanned_Data.visibility = View.VISIBLE
                        taskfunction_alluser.visibility = View.GONE
                        viewModel?.edScannedData = scanned_Data
                        label_TargetUser.visibility = View.GONE

                        openZingScanner()
                    } else if (item == "218") {
                        hideButtonVisibility(false)

                        scanned_Data.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btnChoosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        taskfunction_alluser.visibility = View.GONE
                        label_TargetUser.visibility = View.GONE

                    } else if (item == "219") {
                        hideButtonVisibility(false)

                        scanned_Data.visibility = View.GONE
                        btnChoosefile.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        taskfunction_alluser.visibility = View.VISIBLE
                        label_TargetUser.visibility = View.VISIBLE
                        label_attachmedia.visibility = View.VISIBLE
                        taskfunction_media.visibility = View.VISIBLE

                        val userlist = ArrayList<String>()

                        transferPackListNAMEString=""

                        label_attachmedia.setText("Packs To Transfer")
                        label_TargetUser.setText("Target User")

                        allUserList?.add(AllUserList("0","Select"))
                        for (i in 0 until  allUserList?.size!!){
                            userlist.add(allUserList!!.get(i).name)
                        }


                        val dd = ArrayAdapter(
                            this@TaskFunctionActivity,
                            android.R.layout.simple_spinner_item,
                            userlist
                        )
                        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        taskfunction_alluser.adapter = dd

                        setTransferListner(taskfunction_alluser,allUserList)

                        viewModel?.getTaskObjectList(updateTaskID?.id.toString())

                        viewModel?.taskObjectList?.observe(this@TaskFunctionActivity) { it ->
                            if (!it.isNullOrEmpty()) {
                                transferPackList = it as ArrayList<TaskObject>
                                transferPackList.forEach { data ->
                                    transferPackListNAMEString += " " + data.name + ","
                                }
                                val i = transferPackListNAMEString.lastIndex
                                transferPackListNAMEString.dropLast(i - 1)
                                taskfunction_media.setText(transferPackListNAMEString)


                            }

                        }


                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun setTransferListner(taskfunctionMedia: Spinner, userlistid: ArrayList<AllUserList>?) {
        taskfunctionMedia.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                        selectedFunctionFieldId = userlistid!![position].id.toDouble().toInt()


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
    }

    private fun hideButtonVisibility(isHidden: Boolean) {
        if (isHidden) {
            btnExecute.visibility = View.GONE
        } else {
            btnExecute.visibility = View.VISIBLE

        }

    }

    private fun callApi(selectedFunctionId: String) {


        if (AppUtils().isInternet(this)) {
            ApiClient.client.create(ApiInterFace::class.java)
                .taskFunctionFieldList(
                    MySharedPreference.getUser(this)?.id.toString(),
                    updateTaskID?.id.toString(), selectedFunctionId
                ).enqueue(this)
        } else {

            val db = DbHelper(this, null)
            if (selectedFunctionId == "171") {
                //add person
                val personList = db.getPersonList()
                val personListId = ArrayList<String>()
                val personListName = ArrayList<String>()
                personListId.add("0")
                personListName.add("Select")
                personList.forEach {
                    personListId.add(it.id.toString())
                    personListName.add(it.lname + it.fname)

                }


                val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, personListName)
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                taskfunction_field.adapter = dd

                if (selectedFunctionNo.isNotEmpty()) {
                    for ((count, item) in personListId.withIndex()) {
                        if (item == selectedFunctionNo) {
                            taskfunction_field.setSelection(count)
                        }
                    }
                }

            } else if (selectedFunctionId == "176") {
                val containerList = db.getContainerList()
                val containerListID = ArrayList<String>()
                val containerListName = ArrayList<String>()
                containerListID.add("0")
                containerListName.add("Select")
                containerList.forEach {
                    containerListID.add(it.id.toString())
                    containerListName.add(it.name!!)
                }
                val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, containerListName)
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                taskfunction_field.adapter = dd
            } else if (selectedFunctionId == "173") {
                val imageList = db.getImageList(updateTaskID?.id.toString())
                setImageList(imageList)
            }
        }
    }

    private fun setImageList(imageList: ArrayList<TaskMediaFile>) {
        if (AppUtils().isInternet(this)) {
            ApiClient.client.create(ApiInterFace::class.java)
                .taskFunctionFieldList(
                    MySharedPreference.getUser(this)?.id.toString(),
                    updateTaskID?.id.toString(), "173"
                ).enqueue(this)

        } else {
            val imageNameList = ArrayList<String>()
            val function = ArrayList<ListFunctionFieldlist>()

            for (i in 0 until imageList.size) {
                val item = imageList[i]
                imageNameList.add(item.name!!)
                function.add(
                    ListFunctionFieldlist(
                        item.id.toString(), item.name, item.link,
                        item.filePathLocal
                    )
                )
            }

            AdapterTaskFunction(this, function) { name, link ->
                if (AppUtils().isInternet(this)) {
                    downloadFile(link, name)
                } else {
                    try {
                        val pdfFile = File("file:///android_assets/$name")
                        val path = Uri.fromFile(pdfFile)
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.setDataAndType(path, "Image/*")
                        startActivity(intent)
                    } catch (e: Exception) {
                        AppUtils.logDebug(
                            tag,
                            "failed to load image or other file's" + e.message.toString()
                        )
                    }
                    Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->
                    try {
//                        android  10  mate joi le
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            val contentURI = result.data!!.data

                            val filePathUri = FilePath.getPath(this, contentURI!!)
                            fileVideo = File(filePathUri)
                            fileSizeInMB = viewModel?.getFileSize(fileVideo!!)
                            fileVideo?.let {
                                taskfunction_media.setText(it.name)
                                val type = AppUtils().checkExtension(it.extension)
                                if (type == "video") {
                                    uris.clear()
                                    progressVisible(true)
                                    handleResult(result.data)
                                }
                            }
                        } else {
                            val pickiT = PickiT(this, this, this)

                            pickiT.getPath(result.data!!.data, Build.VERSION.SDK_INT)

                        }

                    } catch (e: Exception) {
                        AppUtils.logError(
                            tag, "GalleryLauncher error " + (e.localizedMessage?.toString()
                                ?: "")
                        )
                    }
                }
            }
        }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->
                    val imageBitmap = it.extras!!.get("data") as Bitmap
                    val fileUri = getImageUri(applicationContext, imageBitmap)
                    val file = fileUri?.path?.let { it1 -> File(it1) }
                    recordedVideoPath =
                        fileUri?.let { it1 -> getRealPathFromURI(it1) }.toString()
                    fileVideo = File(recordedVideoPath)
                    taskfunction_media.setText(file?.name)
                    fileSizeInMB = viewModel?.getFileSize(fileVideo!!)
                }
            }
        }

    private var captureVideoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                try {
                    val contentURI = result.data!!.data
                    val filePathUri = FilePath.getPath(this, contentURI!!)
                    fileVideo = File(filePathUri)
                    fileVideo?.let {
                        taskfunction_media.setText(it.name)
                        val type = AppUtils().checkExtension(it.extension)
                        if (type == "video") {
                            fileSizeInMB = viewModel?.getFileSize(fileVideo!!)
                            progressVisible(true)
                            handleResult(result.data)
                        }
                    }

                } catch (e: Exception) {
                    AppUtils.logError(
                        tag, "GalleryLauncher error " + (e.localizedMessage?.toString()
                            ?: "")
                    )
                }

            }
        }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String?
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result.toString()
    }

    private fun openCameraToCaptureVideo() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            val captureVideo = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            setResult(VIDEO_CAPTURE, captureVideo)
            captureVideoResult.launch(captureVideo)
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "filename",
            null
        )
        return Uri.parse(path)
    }

    private fun handleResult(data: Intent?) {
        val clipData: ClipData? = data?.clipData
        if (clipData != null) {
            for (i in 0 until clipData.itemCount) {
                val videoItem = clipData.getItemAt(i)
                uris.add(videoItem.uri)
            }
            processVideo()
        } else if (data != null && data.data != null) {
            val uri = data.data
            uris.add(uri!!)
            processVideo()
        }
    }

    override fun onResponse(
        call: Call<ResponseTaskFunctionaliyt>,
        response: Response<ResponseTaskFunctionaliyt>
    ) {

        if (response.body()?.error == false) {

            if (response.body()?.msg == "Inserted Successfully") {
                Toast.makeText(this, response.body()?.msg.toString(), Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, ViewTaskActivity::class.java)
                startActivity(intent)
                finish()
            } else {

                val baseResponse: ResponseTaskFunctionaliyt = Gson().fromJson(
                    Gson().toJson(response.body()),
                    ResponseTaskFunctionaliyt::class.java
                )

                val attachmedia = ArrayList<ListMediaFile>()
                val fieldListTaskFunctions = ArrayList<FieldListTaskFunctions>()
                var functionName = ""
                val function = ArrayList<ListFunctionFieldlist>()

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
                    setAdapterField(function)
                }
                if (functionName == "ATTACH_MEDIA") {
                    setAdapter(function)
                }
            }

        } else {
            AppUtils.logDebug(tag, "on Failure")
        }
    }

    override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {
        try {
            AppUtils.logDebug(tag, t.message.toString())
        } catch (e: java.lang.Exception) {
            AppUtils.logDebug(tag, e.message.toString())

        }
    }

    private fun setAdapterField(function: ArrayList<ListFunctionFieldlist>) {
        val functionName = ArrayList<String>()
        val functionId = ArrayList<String>()
        for (i in 0 until function.size) {
            functionName.add(function[i].name.toString())
            functionId.add(function[i].id.toString())
        }
        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, functionName)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskfunction_field.adapter = dd

        if (selectedFunctionNo.isNotEmpty()) {
            for ((count, item) in functionId.withIndex()) {
                if (item == selectedFunctionNo) {
                    taskfunction_field.setSelection(count)
                }
            }
        }

        setFieldListener(taskfunction_field, function)
    }

    private fun setFieldListener(
        spinnerTaskFunctionField: Spinner?,
        function: ArrayList<ListFunctionFieldlist>
    ) {
        spinnerTaskFunctionField?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    selectedFunctionFieldId = function[position].id!!.toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
    }

    private fun setAdapter(function: ArrayList<ListFunctionFieldlist>) {
        recycler_viewMedia.visibility = View.GONE
//        label_filename.visibility = View.GONE
        AppUtils.logDebug(tag, "In Set Adapter")
        AdapterTaskFunction(this, function) { name, link ->
            if (AppUtils().isInternet(this)) {
                downloadFile(link, name)
            } else {
                Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadFile(url: String, name: String) {
        manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)

        request.setTitle(name)
        request.setDescription("Downloading ")
        request.setAllowedOverRoaming(false)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
//    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        val reference: Long = manager!!.enqueue(request)

    }

    override fun onProgressUpdate(percentage: Int) {
        progressCircular?.setProgressCompat(percentage, true)
        progressCircularLabel?.text = "$percentage%"
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ), 100
            )

        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {
                hideButtonVisibility(true)

                progressbar_taskexecute.visibility = View.GONE

                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {

                callApiScan(intentResult.contents.toString())
                progressbar_taskexecute.visibility = View.VISIBLE
            }
        } else {
            progressbar_taskexecute.visibility = View.GONE

            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun callApiScan(content: String) {
        viewModel?.btnSubmit = btnExecute
        viewModel?.callApiForGetIDofPersonScanned(this, selectedFunctionId, content)
    }

    @SuppressLint("SetTextI18n")
    private fun processVideo() {

        GlobalScope.launch {
            VideoCompressor.start(
                context = applicationContext,
                uris,
                isStreamable = true,
                saveAt = Environment.DIRECTORY_MOVIES,
                listener = object : CompressionListener {
                    override fun onProgress(index: Int, percentage: Float) {
                        if (percentage <= 100 && percentage.toInt() % 5 == 0)
                            runOnUiThread {
                                progressCircular?.setProgressCompat(
                                    percentage.toInt(),
                                    true
                                )
                                progressCircularLabel?.text = "${percentage.toInt()}%"
                            }
                    }

                    override fun onStart(index: Int) {
                        runOnUiThread {
                            progressVisible(true)
                        }
                    }

                    override fun onSuccess(index: Int, size: Long, path: String?) {
                        runOnUiThread {
                            path?.let {
                                fileVideo = File(it)
                                progressVisible(false)
                            }
                        }
                    }

                    override fun onFailure(index: Int, failureMessage: String) {
                        runOnUiThread {
                            progressVisible(false)
                        }
                    }

                    override fun onCancelled(index: Int) {
                        runOnUiThread {
                            progressVisible(false)
                        }
                    }
                },
                configureWith = Configuration(
                    quality = VideoQuality.LOW,
                    isMinBitrateCheckEnabled = true,
                )
            )
        }
    }

    private fun progressVisible(isVisible: Boolean) {
        if (isVisible) {
            btnExecute.visibility = View.GONE
            progressCircularLabel?.visibility = View.VISIBLE
            progressCircular?.visibility = View.VISIBLE
            progressCircularTitle?.visibility = View.VISIBLE
        } else {
            btnExecute.visibility = View.VISIBLE
            progressCircularLabel?.visibility = View.GONE
            progressCircular?.visibility = View.GONE
            progressCircularTitle?.visibility = View.GONE
        }
    }

    override fun PickiTonUriReturned() {

    }

    override fun PickiTonStartListener() {
    }

    override fun PickiTonProgressUpdate(progress: Int) {
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        fileVideo = File(path)
        fileSizeInMB = viewModel?.getFileSize(fileVideo!!)

        taskfunction_media.setText(fileVideo!!.name)
    }

    override fun PickiTonMultipleCompleteListener(
        paths: java.util.ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
    }


}



