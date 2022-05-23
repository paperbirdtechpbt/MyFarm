package com.pbt.myfarm.Activity.TaskFunctions

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.TaskFunctions.ViewModel.ViewModelTaskFunctionality
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.task_object.ViewTaskObjectActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ResponseTaskExecution
import com.pbt.myfarm.Task
import com.pbt.myfarm.TaskMediaFile
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_OBJECT
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_OBJECTI_ISUPDATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKLIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.AppUtils.Companion.paramRequestTextBody
import com.pbt.myfarm.Util.FilePath
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_task_function.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class TaskFunctionActivity : AppCompatActivity(), ProgressRequestBody.UploadCallback,
    retrofit2.Callback<ResponseTaskFunctionaliyt> {

    var updateTaskID: Task? = null
    var viewmodel: ViewModelTaskFunctionality? = null
    var spinner: Spinner? = null
    var selectedFunctionId = 0
    var selectedFunctionFieldId = 0
    var body: MultipartBody.Part? = null

    var edAttachMedia: EditText? = null
    val TAG: String = "TaskFunctionActivity"
    private val CAMERA_REQUEST = 1888
    private val GELARY_REQUEST = 1088
    var manager: DownloadManager? = null

    private val VIDEO_CAPTURE = 101
    var recordedVideoPath: String = ""

    var fileVideo: File? = null
    var progress_circular: CircularProgressIndicator? = null
    var progress_circularlabel: TextView? = null
    var taskfunction: Spinner? = null
    var isTaskuntionUpdate = false

    var selectedFunctionName = ""
    var selectedFunctionNo = ""

    var db: DbHelper? = null
    var checkStats: HttpResponse? = null

    val listLocalPrivilegesName = ArrayList<String>()

    var taskObject: TaskObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_function)

        db = DbHelper(this, null)
        db?.getAllRolePrivilegesName()?.let {
            listLocalPrivilegesName.addAll(it)
        }

        spinner = findViewById(R.id.taskfunction_field)
        progress_circular = findViewById(R.id.progress_circular)
        progress_circularlabel = findViewById(R.id.progress_circular_label)

        edAttachMedia = findViewById(R.id.taskfunction_media)
        taskfunction = findViewById(R.id.taskfunction)

        if (intent.extras != null) {
            updateTaskID = intent.getParcelableExtra<Task>(CONST_TASKFUNCTION_TASKID)
            isTaskuntionUpdate = intent.getBooleanExtra(CONST_TASKFUNCTION_OBJECTI_ISUPDATE, false)
            AppUtils.logDebug(TAG, "updateTaskId" + updateTaskID.toString())

            if (intent.getParcelableExtra<TaskObject>(CONST_TASKFUNCTION_OBJECT) != null) {
                taskObject =
                    taskObject ?: intent.getParcelableExtra<TaskObject>(CONST_TASKFUNCTION_OBJECT)
                selectedFunctionName = taskObject?.function ?: ""
                selectedFunctionNo = taskObject?.no ?: ""
            }

            updateTaskID?.let {
                initViewModel(it.task_config_id.toString(), it.id.toString())
            }

        }
        recycler_viewMedia?.layoutManager = LinearLayoutManager(this)
        recycler_viewMedia?.visibility = View.GONE
        label_filename?.visibility = View.GONE

        chechpermission()


        btn_choosefile.setOnClickListener {
            chechpermission()
//mypermission()
            val options = arrayOf<CharSequence>(
                "Take Photo",
                "Choose from Gallery",
                "Capture Video",
                "Cancel"
            )
//        val options = arrayOf<CharSequence>("Take Photo")
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Choose your profile picture")
            builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                if (options[item] == "Take Photo") {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    setResult(CAMERA_REQUEST, cameraIntent)
                    resultLauncher.launch(cameraIntent)
                } else if (options[item] == "Choose from Gallery") {

                    val pickPhoto = Intent()
                    pickPhoto.setType("*/*")
                    pickPhoto.setAction(Intent.ACTION_GET_CONTENT)
                    setResult(GELARY_REQUEST, pickPhoto)
                    gallaryLauncher.launch(pickPhoto)

                } else if (options[item] == "Capture Video") {

                    openCameraToCaptureVideo()

                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            })
            builder.show()


        }
        btn_execute.setOnClickListener {
            progressbar_taskexecute.visibility = View.VISIBLE

            val mTaskID = updateTaskID?.id.toString();
            val mFunctionID = selectedFunctionId.toString()
            val mUserID = MySharedPreference.getUser(this)?.id.toString()
            val mTimeZoneId =
                MySharedPreference.getUser(this)?.timezone?.toDouble()?.toInt().toString()
            val mFieldID = selectedFunctionFieldId.toString()


            if (selectedFunctionId == 172 || selectedFunctionId == 168) {
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
            } else if (selectedFunctionId == 174) {
                if (checkStats?.status == "completed" || checkStats?.status == null) {
                    if (checkStats?.status != "completed") {
                        Toast.makeText(
                            this@TaskFunctionActivity,
                            "Task Not Started",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@TaskFunctionActivity,
                            "Task is Ended",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            } else {
                if (selectedFunctionId == 173) {
                    progressbar_taskexecute.visibility = View.GONE

                    progress_circular?.visibility = View.VISIBLE
                    progress_circularlabel?.visibility = View.VISIBLE
                } else {
                    progressbar_taskexecute?.visibility = View.VISIBLE
                }

                btn_execute.visibility = View.GONE

                if (AppUtils().isInternet(this)) {

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
                    val taskID = paramRequestTextBody(mTaskID)
                    val functionID = paramRequestTextBody(mFunctionID)
                    val userID = paramRequestTextBody(mUserID)
                    val fieldID = paramRequestTextBody(mFieldID)

                    AppUtils.logDebug(
                        TAG,
                        "taskID $taskID, functionID $functionID userID $userID fieldID $fieldID"
                    )
                    val apiInterFace =
                        service.uploadFile(dataVideo, taskID, functionID, userID, fieldID)

                    apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
                        override fun onResponse(
                            call: Call<ResponseTaskExecution>,
                            response: Response<ResponseTaskExecution>
                        ) {
                            AppUtils.logDebug(TAG, response.body().toString())
                            val message = response.body()?.msg.toString()
                            if (response.body()?.error == false) {
                                progressbar_taskexecute.visibility = View.GONE

                                progress_circular?.visibility = View.GONE
                                progress_circularlabel?.visibility = View.GONE
                                progressbar_taskexecute?.visibility = View.GONE
                                btn_execute.visibility = View.VISIBLE
                                if (isTaskuntionUpdate) {
                                    startActivity(
                                        Intent(
                                            this@TaskFunctionActivity,
                                            ViewTaskObjectActivity::class.java
                                        )
                                    )
                                }
                                Toast.makeText(
                                    this@TaskFunctionActivity,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()


                                finish()


                            } else {

                                progressbar_taskexecute.visibility = View.GONE

                                progress_circular?.visibility = View.GONE
                                progress_circularlabel?.visibility = View.GONE
                                progressbar_taskexecute?.visibility = View.GONE

                                btn_execute.visibility = View.VISIBLE
                                Toast.makeText(
                                    this@TaskFunctionActivity,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }

                        //
                        override fun onFailure(call: Call<ResponseTaskExecution>, t: Throwable) {
                            progressbar_taskexecute.visibility = View.GONE

                            try {
                                progress_circular?.visibility = View.GONE
                                progress_circularlabel?.visibility = View.GONE
                                btn_execute.visibility = View.VISIBLE
                                AppUtils.logError(TAG, t.message.toString())
                            } catch (e: Exception) {
                                progress_circular?.visibility = View.GONE
                                progress_circularlabel?.visibility = View.GONE
                                btn_execute.visibility = View.VISIBLE
                                AppUtils.logError(TAG, t.message.toString())

                            }
                            Toast.makeText(this@TaskFunctionActivity, "failed", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                } else {
//                    val db = DbHelper(this, null)
//                    viewmodel?.apply {
//                        this.executeTask(mTaskID,mFunctionID,mUserID,mFieldID,db,mTimeZoneId)
//                    }
                    val db = DbHelper(this, null)
//                    db.checkIftaskStart(updateTaskID)
                    val taskobject = com.pbt.myfarm.TaskObject(
                        task_id = updateTaskID?.id, container = selectedFunctionFieldId.toString(),
                        function = selectedFunctionId.toString(),
                    )

                    val isSuucess = db.addTaskObjectOffline(taskobject, "1")
                    if (isSuucess) {
                        progressbar_taskexecute.visibility = View.GONE
                        Toast.makeText(this, "SuccessFull", Toast.LENGTH_SHORT).show()
                    } else {
                        progressbar_taskexecute.visibility = View.GONE

                        Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }

    }


    private fun initViewModel(taskConfigID: String, updateTaskID: String) {
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelTaskFunctionality::class.java)
        viewmodel?.context = this@TaskFunctionActivity
        viewmodel?.codeScannerView = qr_scanner

        btnScanCode.setOnClickListener{
//            qr_scanner.visibility=View.VISIBLE
openZingScanner()        }
        viewmodel?.oncheckStatuApi(this, updateTaskID)
        viewmodel?.checkstatus?.observe(this) {
            if (it != null) {
                checkStats = it
            }
        }

        viewmodel?.onTaskFunctionList(
            taskConfigID,
            this,
            updateTaskID,
            MySharedPreference.getUser(application)?.id.toString()
        )
        viewmodel?.listTaskFuntions?.observe(this, Observer { list ->

            AppUtils.logDebug(TAG, "list og list functions=" + Gson().toJson(list).toString())
//            AppUtils.logDebug(TAG, "list og list functions=" + Gson().toJson(privilegeListName).toString())
            val functionlist = ArrayList<ListTaskFunctions>()

            if (!list.isNullOrEmpty()) {

                AppUtils.logDebug(TAG, "-=-=-=-=-==-=-=-$privilegeListName")
                for (i in list.indices) {
                    val item = list.get(i).name1

                    if (item!!.contains("CREATE_PACK") || item!!.contains("ADD_PACK")) {
                        if (!privilegeListName.contains("Pack") && !privilegeListName.contains("InsertPack")) {
//asda
                        } else {
                            functionlist.add(list.get(i))

                        }
                    } else {
                        functionlist.add(list.get(i))
                    }
                }

                setSpinner(functionlist, taskfunction!!)

            }
        })

    }

    private fun openZingScanner() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setPrompt("My Farm Code Scanner")
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.initiateScan()
    }

    private fun setSpinner(list: List<ListTaskFunctions>, taskfunction: Spinner) {

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
        taskfunction.setAdapter(dd)

        var selectedID = ""
        if (selectedFunctionName == "PERSON") {
            selectedID = "171"
        } else if (selectedFunctionName == "PACK") {
            selectedID = "175"
        } else if (selectedFunctionName == "CONTAINER") {
            selectedID = "176"
        }

        if (!selectedFunctionName.isEmpty()) {
            for ((count, item) in listID.withIndex()) {
                if (item == selectedID) {
                    taskfunction.setSelection(count)
                }
            }
        }

        setListner(taskfunction, listID, listName)


    }

    private fun setListner(
        taskfunction: Spinner,
        listid: ArrayList<String>,
        listname: ArrayList<String>
    ) {
        taskfunction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                selectedFunctionId = listid[position].toDouble().toInt()

                if (selectedFunctionId == 0) {
                    label_filename.visibility = View.GONE
                    taskfunction_field.visibility = View.GONE
                    label_fieldname.visibility = View.GONE
                    recycler_viewMedia.visibility = View.GONE
                    label_filename.visibility = View.GONE
                    taskfunction_media.visibility = View.GONE
                    btn_choosefile.visibility = View.GONE
                    label_attachmedia.visibility = View.GONE
                } else {
                    val item = listid.get(position)
                    if (selectedFunctionId == 171 || selectedFunctionId == 176) {

                        taskfunction_media.visibility = View.GONE
                        btn_choosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        taskfunction_field.visibility = View.VISIBLE
                        label_fieldname.visibility = View.VISIBLE


                        if (selectedFunctionId == 171) {
                            label_fieldname.setText("Add Person")

                            callApi("171")
                        } else {
                            label_fieldname.setText("Add Container")
                            callApi("176")
                        }
                    } else if (selectedFunctionId == 173) {

                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        taskfunction_media.visibility = View.VISIBLE
                        btn_choosefile.visibility = View.VISIBLE
                        label_attachmedia.visibility = View.VISIBLE
                        recycler_viewMedia.visibility = View.VISIBLE
                        label_filename.visibility = View.GONE

                        label_attachmedia.setText("Attach Media")
                        callApi("173")

                    } else if (selectedFunctionId == 168 || selectedFunctionId == 169 || selectedFunctionId == 170) {
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btn_choosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                    } else if (item == "174") {
                        if (checkStats?.status == "completed" || checkStats?.status == null) {
                            if (checkStats?.status != "completed") {
                                Toast.makeText(
                                    this@TaskFunctionActivity,
                                    "Task Not Started",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {
                                Toast.makeText(
                                    this@TaskFunctionActivity,
                                    "Task is Ended",
                                    Toast.LENGTH_SHORT
                                ).show()

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

                        label_filename.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btn_choosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                    } else if (item == "175") {

                        taskfunction_media.visibility = View.GONE
                        btn_choosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        taskfunction_field.visibility = View.VISIBLE
                        label_fieldname.visibility = View.VISIBLE

                        callApi("175")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
//                callApi(selectedFunctionId.toString())
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
            AppUtils.logDebug(TAG, "callapi====" + selectedFunctionId)
            val db = DbHelper(this, null)

            if (selectedFunctionId == "171") {
                //add person
                val personlist = db.getPersonList()
                val personListId = ArrayList<String>()
                val personListName = ArrayList<String>()
                personListId.add("0")
                personListName.add("Select")
                personlist.forEach {
                    personListId.add(it.id.toString())
                    personListName.add(it.lname + it.fname)

                }


                val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, personListName)
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                taskfunction_field.setAdapter(dd)

                if (!selectedFunctionNo.isEmpty()) {
                    for ((count, item) in personListId.withIndex()) {
                        if (item == selectedFunctionNo) {
                            taskfunction_field.setSelection(count)
                        }
                    }
                }

            } else if (selectedFunctionId == "176") {
                val containerlist = db.getContainerList()
                val containerlistId = ArrayList<String>()
                val containerlistName = ArrayList<String>()
                containerlistId.add("0")
                containerlistName.add("Select")
                containerlist.forEach {
                    containerlistId.add(it.id.toString())
                    containerlistName.add(it.name!!)

                }

                val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, containerlistName)
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                taskfunction_field.setAdapter(dd)

                //add container

            } else if (selectedFunctionId == "173") {
                AppUtils.logDebug(TAG, "updataskid" + updateTaskID.toString())

                val imageList = db.getImageList(updateTaskID?.id.toString())
                AppUtils.logDebug(TAG, "imagelist" + imageList.toString())

                setImageList(imageList)
                //attach media

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
            val arrayAdapter: ArrayAdapter<*>
            val imagenameList = ArrayList<String>()
            val function = ArrayList<ListFunctionFieldlist>()

            for (i in 0 until imageList.size) {
                val item = imageList.get(i)
                imagenameList.add(item.name!!)
                function.add(
                    ListFunctionFieldlist(
                        item.id.toString(), item.name, item.link,
                        item.filePathLocal
                    )
                )
            }


            val adapter = AdapterTaskFunction(this, function) { name, link ->
                if (AppUtils().isInternet(this)) {
                    downloadFile(link, name)
                } else {
                    try {
                        val pdffile = File("file:///android_assets/$name")
                        val path = Uri.fromFile(pdffile)
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.setDataAndType(path, "Image/*")
                        startActivity(intent)
                    } catch (e: Exception) {
                        AppUtils.logDebug(
                            TAG,
                            "failed to load image or other file's" + e.message.toString()
                        )
                    }
                    Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
                }

            }
//            recycler_viewMedia.adapter = adapter
        }
    }

    var gallaryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            AppUtils.logDebug(TAG, "Camera Result= ${result.resultCode}")
//            if (result.resultCode == Activity.RESULT_OK) {
//                result.data?.let { it ->
//                    val contentURI = result.data!!.data
//
//                    recordedVideoPath = contentURI!!.path.toString()
//
//                    recordedVideoPath = getRealPathFromURI(contentURI)
////                    recordedVideoPath = getRealPathFromURITestt(this, contentURI)!!
//                    recordedVideoPath = getRealPathFromURITestt(this, contentURI)!!
//                    fileVideo = File(recordedVideoPath)
//                    AppUtils.logDebug(TAG, "VideoFiule Name" +recordedVideoPath)
//                    taskfunction_media.setText(fileVideo!!.name)
//                    Toast.makeText(this, "${fileVideo!!.name}", Toast.LENGTH_SHORT).show()
//
//                }
//            }
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->

                    val contentURI = result.data!!.data
                    val filepathfromUri = FilePath.getPath(this, contentURI!!)
                    fileVideo = File(filepathfromUri)
                    AppUtils.logError(TAG, "filepathfromUri" + filepathfromUri)
                    AppUtils.logError(TAG, "file" + fileVideo)
                    taskfunction_media.setText(fileVideo!!.name)

                    Toast.makeText(this, "${fileVideo!!.name}", Toast.LENGTH_LONG).show()

                }
            }
        }

    //--------do not clear below method------------//
    fun getRealPathFromURITestt(context: Context?, contentUri: Uri?): String? {
        val out: OutputStream
        val file = File(getFilename(context))
        try {
            if (file.createNewFile()) {
                val iStream = if (context != null) context.contentResolver.openInputStream(
                    contentUri!!
                )
                else contentResolver.openInputStream(contentUri!!)

                val inputData = getBytes(iStream)
                out = FileOutputStream(file)
                out.write(inputData)
                out.close()
                return file.absolutePath
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    //==----|||||||||||||||==============//

    @Throws(IOException::class)
    private fun getBytes(inputStream: InputStream?): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream!!.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    private fun getFilename(context: Context?): String? {
        val mediaStorageDir = File(context!!.getExternalFilesDir(""), "patient_data")
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }
        val mImageName = "IMG_" + System.currentTimeMillis().toString() + ".png"
        return mediaStorageDir.absolutePath.toString() + "/" + mImageName
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->
                    val imageBitmap = it.extras!!.get("data") as Bitmap
//                    filePart = buildImageBodyPart("file", imageBitmap)
                    val fileUri = getImageUri(applicationContext, imageBitmap)
                    val file = File(fileUri?.path)
                    recordedVideoPath = fileUri?.let { it1 -> getRealPathFromURI(it1) }.toString()
                    fileVideo = File(recordedVideoPath)
                    taskfunction_media.setText(file.name)
                }
            }
        }

    private var captureVideoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contentURI = result.data!!.data
                recordedVideoPath = contentURI!!.path.toString()
                recordedVideoPath = getRealPathFromURI(contentURI)
                fileVideo = File(recordedVideoPath)
                taskfunction_media.setText(fileVideo!!.name)

            }
        }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String?
        var cursor: Cursor? = null
        cursor = contentResolver.query(contentURI, null, null, null, null)
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

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "filename",
            null
        );
        return Uri.parse(path)
    }


    private fun openCameraToCaptureVideo() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            val captureVideo = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            setResult(VIDEO_CAPTURE, captureVideo)
            captureVideoResult.launch(captureVideo)
        }
    }


    override fun onResponse(
        call: Call<ResponseTaskFunctionaliyt>,
        response: Response<ResponseTaskFunctionaliyt>
    ) {
        AppUtils.logDebug(TAG, "REsposen " + Gson().toJson(response.body()))

        if (response.body()?.error == false) {
            AppUtils.logDebug(TAG, "REsposen " + Gson().toJson(response.body()))

            if (response.body()?.msg == "Inserted Successfully") {
                Toast.makeText(this, response.body()?.msg.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ViewTaskActivity::class.java)
                startActivity(intent)
                finish()
            } else {

                val basereponse: ResponseTaskFunctionaliyt = Gson().fromJson(
                    Gson().toJson(response.body()), ResponseTaskFunctionaliyt::class.java
                )


                val attachmedia = ArrayList<ListMediaFile>()
                val fieldListTaskFunctions = ArrayList<FieldListTaskFunctions>()
                var functionname = ""
                val function = ArrayList<ListFunctionFieldlist>()

                if (!basereponse.TaskFunction.isNullOrEmpty()) {

                    basereponse.TaskFunction.forEach { tsk ->
                        fieldListTaskFunctions.add(tsk)
                        functionname = tsk.name
                    }

                }
                if (!basereponse.Function.isNullOrEmpty()) {
                    basereponse.Function.forEach {
                        function.add(it)
                        AppUtils.logDebug(TAG, "in loop function" + it)

                    }
                    setAdapterField(function)
                }
                if (functionname == "ATTACH_MEDIA") {
                    setAdapter(function)
                }

            }

        } else {
            AppUtils.logDebug(TAG, "on Failure")

        }

    }

    override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {
        try {
            AppUtils.logDebug(TAG, t.message.toString())
        } catch (e: java.lang.Exception) {
            AppUtils.logDebug(TAG, e.message.toString())

        }
    }

    private fun setAdapterField(function: ArrayList<ListFunctionFieldlist>) {
        val functionname = ArrayList<String>()
        val functionid = ArrayList<String>()
        for (i in 0 until function.size) {
            functionname.add(function.get(i).name.toString())
            functionid.add(function.get(i).id.toString())
        }
        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, functionname)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskfunction_field.setAdapter(dd)

        if (!selectedFunctionNo.isEmpty()) {
            for ((count, item) in functionid.withIndex()) {
                if (item == selectedFunctionNo) {
                    taskfunction_field.setSelection(count)
                }
            }
        }

        setFieldListner(taskfunction_field, function)
    }

    private fun setFieldListner(
        taskfunctionField: Spinner?,
        function: ArrayList<ListFunctionFieldlist>
    ) {
        taskfunctionField?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {


                selectedFunctionFieldId = function.get(position).id!!.toInt()
                AppUtils.logDebug(
                    TAG,
                    "onitem Select lIstner" + selectedFunctionFieldId.toString()
                )


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun setAdapter(function: ArrayList<ListFunctionFieldlist>) {
        recycler_viewMedia.visibility = View.GONE
        label_filename.visibility = View.GONE
        AppUtils.logDebug(TAG, "In Set Adapter")
        val adapter = AdapterTaskFunction(this, function) { name, link ->
            if (AppUtils().isInternet(this)) {
                downloadFile(link, name)
            } else {
                Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
            }

        }
//        recycler_viewMedia.adapter = adapter
    }




    fun downloadFile(url: String, name: String) {
        manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)

        request.setTitle(name);
        request.setDescription("Downloading ")
        request.setAllowedOverRoaming(false)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
//    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        val reference: Long = manager!!.enqueue(request)

    }

    override fun onProgressUpdate(percentage: Int) {
        Log.d("onResponse", "$percentage")
        progress_circular?.setProgressCompat(percentage, true)
        progress_circular_label.setText(percentage.toString() + "%")
    }

    private fun chechpermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ), 100
            )

        }
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                Toast.makeText(this, "${intentResult.contents}", Toast.LENGTH_LONG).show()
//                messageText.setText(intentResult.contents)
//                messageFormat.setText(intentResult.formatName)
                Demo_adduser.setText(intentResult.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}



