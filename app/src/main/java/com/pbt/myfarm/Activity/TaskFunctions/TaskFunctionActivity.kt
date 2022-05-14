package com.pbt.myfarm.Activity.TaskFunctions

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.TaskFunctions.ViewModel.ViewModelTaskFunctionality
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ResponseTaskExecution
import com.pbt.myfarm.Task
import com.pbt.myfarm.TaskMediaFile
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.AppUtils.Companion.paramRequestTextBody
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_task_function.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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

    //    var requestbody: RequestBody? = null
    var edAttachMedia: EditText? = null
    val TAG: String = "TaskFunctionActivity"
    private val CAMERA_REQUEST = 1888
    private val GELARY_REQUEST = 1088
    private val MY_CAMERA_PERMISSION_CODE = 1001
    var manager: DownloadManager? = null

    //    var filePart: MultipartBody.Part? = null
    private val VIDEO_CAPTURE = 101
    var recordedVideoPath: String = ""

    //    var recordedVideoName: String = ""
    var fileVideo: File? = null
    var progress_circular: CircularProgressIndicator? = null
    var progress_circularlabel: TextView? = null
    var taskfunction: Spinner? = null

    var db: DbHelper? = null

    val listLocalPrivilegesName = ArrayList<String>()

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
            AppUtils.logDebug(TAG, "updateTaskId" + updateTaskID.toString())

            updateTaskID?.let {
                initViewModel(it.task_config_id.toString(), it.id.toString())
            }

            checkAndRequestPermissions()
        }
        recycler_viewMedia?.layoutManager = LinearLayoutManager(this)
        recycler_viewMedia?.visibility = View.GONE
        label_filename?.visibility = View.GONE



        btn_choosefile.setOnClickListener {

            if (checkAndRequestPermissions()) {
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
        }
        btn_execute.setOnClickListener {

            val mTaskID = updateTaskID?.id.toString();
            val mFunctionID =  selectedFunctionId.toString()
            val mUserID =  MySharedPreference.getUser(this)?.id.toString()
            val mTimeZoneId =  MySharedPreference.getUser(this)?.timezone?.toDouble()?.toInt().toString()
            val mFieldID = selectedFunctionFieldId.toString()


            if (selectedFunctionId == 172 || selectedFunctionId == 168) {
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
            } else {
                if (selectedFunctionId == 173) {

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

                    val apiInterFace = service.uploadFile(dataVideo, taskID, functionID, userID, fieldID)

                    apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
                        override fun onResponse(
                            call: Call<ResponseTaskExecution>,
                            response: Response<ResponseTaskExecution>
                        ) {
                            AppUtils.logDebug(TAG, response.body().toString())
                            val message=response.body()?.msg.toString()
                            if (response.body()?.error == false) {
                                progress_circular?.visibility = View.GONE
                                progress_circularlabel?.visibility = View.GONE
                                progressbar_taskexecute?.visibility = View.GONE
                                btn_execute.visibility = View.VISIBLE
                                Toast.makeText(
                                    this@TaskFunctionActivity,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
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
        viewmodel?.onTaskFunctionList(
            taskConfigID,
            this,
            updateTaskID,
            MySharedPreference.getUser(application)?.id.toString()
        )
        viewmodel?.listTaskFuntions?.observe(this, Observer { list ->

            AppUtils.logDebug(TAG, "list og list functions=" + Gson().toJson(list).toString())
//            AppUtils.logDebug(TAG, "list og list functions=" + Gson().toJson(privilegeListName).toString())

            if (!list.isNullOrEmpty()) {

                setSpinner(list, taskfunction!!)
//                for (i in 0 until list.size) {
//                    if (list.get(i).id == "171" || list.get(i).id == "175" || list.get(i).id == "176") {
//                        taskfunction_field.visibility = View.VISIBLE
//                        label_fieldname.visibility = View.VISIBLE
//                    } else if (list.get(i).id == "173") {
//                        taskfunction_media.visibility = View.VISIBLE
//                        btn_choosefile.visibility = View.VISIBLE
//                        label_attachmedia.visibility = View.VISIBLE
//
//
//                    } else if (list.get(i).id == "174") {
//                        //pass intent to craete packactivty with task id
//                        //change in api according to this
//                    }
//                }
            }
        })
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
                        label_filename.visibility = View.VISIBLE
                        taskfunction_media.visibility = View.VISIBLE
                        btn_choosefile.visibility = View.VISIBLE
                        label_attachmedia.visibility = View.VISIBLE
                        recycler_viewMedia.visibility = View.VISIBLE
                        label_filename.visibility = View.VISIBLE

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
                    } else if (item == "174" || item == "175") {

                        val intent =
                            Intent(this@TaskFunctionActivity, PackConfigListActivity::class.java)
                        startActivity(intent)

                        label_filename.visibility = View.GONE
                        taskfunction_field.visibility = View.GONE
                        label_fieldname.visibility = View.GONE
                        recycler_viewMedia.visibility = View.GONE
                        label_filename.visibility = View.GONE
                        taskfunction_media.visibility = View.GONE
                        btn_choosefile.visibility = View.GONE
                        label_attachmedia.visibility = View.GONE
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
                if (checkInternetConncetion()) {
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
            recycler_viewMedia.adapter = adapter
        }


    }

    var gallaryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            AppUtils.logDebug(TAG, "Camera Result ${result.resultCode}")
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->
                    val contentURI = result.data!!.data
                    recordedVideoPath = contentURI!!.path.toString()
//                    recordedVideoPath = getRealPathFromURI(contentURI)
                    recordedVideoPath = getRealPathFromURITestt(this, contentURI)!!
                    fileVideo = File(recordedVideoPath)
                    taskfunction_media.setText(fileVideo!!.name)


                }
            }
        }

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

    private fun buildImageBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = leftImageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
    }

    private fun buildVideoBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = leftImageFile.asRequestBody("video/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(this.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
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

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    private fun openCameraToCaptureVideo() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            val captureVideo = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            setResult(VIDEO_CAPTURE, captureVideo)
            captureVideoResult.launch(captureVideo)
        }
    }

    fun playVideoInDevicePlayer(videoPath: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoPath))
        intent.setDataAndType(Uri.parse(videoPath), "video/mp4")
        startActivity(intent)
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
        for (i in 0 until function.size) {
            functionname.add(function.get(i).name.toString())
        }
        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, functionname)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskfunction_field.setAdapter(dd)

        setFieldListner(taskfunction_field, function)
    }

    private fun setFieldListner(
        taskfunctionField: Spinner?,
        function: ArrayList<ListFunctionFieldlist>
    ) {
        taskfunction_field?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                if (AppUtils().isInternet(this@TaskFunctionActivity)) {
                    if (privilegeListName.contains("CanOverideEditTask")) {

                        selectedFunctionFieldId = function.get(position).id!!.toInt()
                        AppUtils.logDebug(
                            TAG,
                            "onitem Select lIstner" + selectedFunctionFieldId.toString()
                        )
                    }
                } else {
                    if (listLocalPrivilegesName.contains("CanOverideEditTask")) {
                        selectedFunctionFieldId = function.get(position).id!!.toInt()
                        AppUtils.logDebug(
                            TAG,
                            "onitem Select lIstner" + selectedFunctionFieldId.toString()
                        )
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun setAdapter(function: ArrayList<ListFunctionFieldlist>) {
        recycler_viewMedia.visibility = View.VISIBLE
        label_filename.visibility = View.VISIBLE
        AppUtils.logDebug(TAG, "In Set Adapter")
        val adapter = AdapterTaskFunction(this, function) { name, link ->
            if (checkInternetConncetion()) {
                downloadFile(link, name)
            } else {
                Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
            }

        }
        recycler_viewMedia.adapter = adapter
    }

    private fun checkInternetConncetion(): Boolean {

        val ConnectionManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected == true

    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionSendMessage = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val locationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissionsNeeded: MutableList<String> = java.util.ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                1212
            )
            return false
        }
        return true
    }

    //
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_CAMERA_PERMISSION_CODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                        android.Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 100
                    )

                }
                return
            }
        }
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
}