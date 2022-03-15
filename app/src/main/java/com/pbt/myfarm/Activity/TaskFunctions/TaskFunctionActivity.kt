package com.pbt.myfarm.Activity.TaskFunctions

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.TaskFunctions.ViewModel.ViewModelTaskFunctionality
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ResponseTaskExecution
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.FilePath
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_task_function.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.DecimalFormat


class TaskFunctionActivity : AppCompatActivity(), retrofit2.Callback<ResponseTaskFunctionaliyt> {

    var updateTaskID = ""
    var viewmodel: ViewModelTaskFunctionality? = null
    var spinner: Spinner? = null
    var selectedFunctionId = 0
    var selectedFunctionFieldId = 0
    var body: MultipartBody.Part? = null
    var requestbody: RequestBody? = null
    var edAttachMedia: EditText? = null
    val TAG: String = "TaskFunctionActivity"
    private val CAMERA_REQUEST = 1888
    private val GELARY_REQUEST = 1088
    private val MY_CAMERA_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_function)

        spinner = findViewById(R.id.taskfunction_field)

        edAttachMedia = findViewById(R.id.taskfunction_media)

        if (intent.extras != null) {
            updateTaskID = intent.getStringExtra(CONST_TASKFUNCTION_TASKID)!!
            AppUtils.logDebug(TAG, "updateTaskId" + updateTaskID.toString())

            initViewModel(updateTaskID)
            checkAndRequestPermissions()
        }
        recycler_viewMedia?.layoutManager = LinearLayoutManager(this)
        recycler_viewMedia?.visibility = View.GONE

        btn_choosefile.setOnClickListener {

            if (checkAndRequestPermissions()) {
                val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
//        val options = arrayOf<CharSequence>("Take Photo")
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Choose your profile picture")
                builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                    if (options[item] == "Take Photo") {
//                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                        setResult(CAMERA_REQUEST, cameraIntent)
//                        resultLauncher.launch(cameraIntent)
                    } else if (options[item] == "Choose from Gallery") {

                        val pickPhoto = Intent()
                        pickPhoto.setType("*/*")
                        pickPhoto.setAction(Intent.ACTION_GET_CONTENT)
                        setResult(GELARY_REQUEST, pickPhoto)
                        gallaryLauncher.launch(pickPhoto)

                    } else if (options[item] == "Cancel") {
                        dialog.dismiss()
                    }
                })
                builder.show()
            }
        }
        btn_execute.setOnClickListener {

            var file: File? = null

            AppUtils.logDebug(
                TAG,
                "===>" + updateTaskID + "=" + selectedFunctionId.toString() + "=" + selectedFunctionFieldId.toString()
            )
            val service = ApiClient.client.create(ApiInterFace::class.java)
            val apiInterFace = service.taskExecuteFunction(
                updateTaskID,
                selectedFunctionId.toString(), MySharedPreference.getUser(this)?.id.toString(),
                selectedFunctionFieldId.toString()
            )


            apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
                override fun onResponse(
                    call: Call<ResponseTaskExecution>,
                    response: Response<ResponseTaskExecution>
                ) {
                    AppUtils.logDebug(TAG, response.body().toString())
                    if (response.body()?.error == false) {
//                        Toast.makeText(this@TaskFunctionActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
//                        Toast.makeText(this@TaskFunctionActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseTaskExecution>, t: Throwable) {
                    try {
                        AppUtils.logError(TAG, t.message.toString())
                    } catch (e: Exception) {
                        AppUtils.logError(TAG, t.message.toString())

                    }
                    Toast.makeText(this@TaskFunctionActivity, "failed", Toast.LENGTH_SHORT).show()
                }

            })

//            ApiClient.client.create(ApiInterFace::class.java).taskExecuteFunction(
//                updateTaskID,
//                selectedFunctionId.toString(), MySharedPreference.getUser(this)?.id.toString(),
//                selectedFunctionFieldId.toString(),
//                body!!
//            ).enqueue(this)
        }
    }

    private fun initViewModel(updateTaskID: String) {
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelTaskFunctionality::class.java)
        viewmodel?.context = this@TaskFunctionActivity
        viewmodel?.onTaskFunctionList(this, updateTaskID)
        viewmodel?.listTaskFuntions?.observe(this, Observer { list ->
            if (!list.isNullOrEmpty()) {
                val taskfunction: Spinner = findViewById(R.id.taskfunction)
                setSpinner(list, taskfunction)
                for (i in 0 until list.size) {
                    if (list.get(i).id == "171" || list.get(i).id == "175" || list.get(i).id == "176") {
                        taskfunction_field.visibility = View.VISIBLE
                        label_fieldname.visibility = View.VISIBLE
                    } else if (list.get(i).id == "173") {
                        taskfunction_media.visibility = View.VISIBLE
                        btn_choosefile.visibility = View.VISIBLE
                        label_attachmedia.visibility = View.VISIBLE
                    } else if (list.get(i).id == "174") {
                        //pass intent to craete packactivty with task id
                        //change in api according to this

                    }


                }
            }


        })

    }

    private fun setSpinner(list: List<ListTaskFunctions>, taskfunction: Spinner) {
        val listname = ArrayList<String>()
        val listid = ArrayList<String>()


        for (i in 0 until list.size) {
            listname.add(list.get(i).name)
            listid.add(list.get(i).id)

        }

        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, listname)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskfunction.setAdapter(dd)
        setListner(taskfunction, listid)
    }

    private fun setListner(taskfunction: Spinner, listid: ArrayList<String>) {
        taskfunction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {


                selectedFunctionId = listid.get(position).toInt()
                callApi(selectedFunctionId.toString())

                val item = listid.get(position)
                if (item == "171" || item == "176") {
                    taskfunction_media.visibility = View.GONE
                    btn_choosefile.visibility = View.GONE
                    label_attachmedia.visibility = View.GONE
                    label_filename.visibility = View.GONE
                    recycler_viewMedia.visibility = View.GONE
                    taskfunction_field.visibility = View.VISIBLE
                    label_fieldname.visibility = View.VISIBLE
                } else if (item == "173") {
                    taskfunction_field.visibility = View.GONE
                    label_fieldname.visibility = View.GONE
                    label_filename.visibility = View.VISIBLE
                    taskfunction_media.visibility = View.VISIBLE
                    btn_choosefile.visibility = View.VISIBLE
                    label_attachmedia.visibility = View.VISIBLE
                    recycler_viewMedia.visibility = View.VISIBLE
                } else if (item == "168" || item == "169" || item == "170") {
                    taskfunction_field.visibility = View.GONE
                    label_fieldname.visibility = View.GONE
                    label_filename.visibility = View.GONE
                    taskfunction_media.visibility = View.GONE
                    btn_choosefile.visibility = View.GONE
                    label_attachmedia.visibility = View.GONE
                    recycler_viewMedia.visibility = View.GONE
                } else if (item == "174" || item == "175") {
                    val intent =
                        Intent(this@TaskFunctionActivity, PackConfigListActivity::class.java)
                    startActivity(intent)

                    label_filename.visibility = View.GONE
                    taskfunction_field.visibility = View.GONE
                    label_fieldname.visibility = View.GONE
                    recycler_viewMedia.visibility = View.GONE
                    taskfunction_media.visibility = View.GONE
                    btn_choosefile.visibility = View.GONE
                    label_attachmedia.visibility = View.GONE

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }

    private fun callApi(selectedFunctionId: String) {

//        ApiClient.client.create(ApiInterFace::class.java)
//            .taskFunctionFieldList(
//                MySharedPreference.getUser(this)?.id.toString(),
//                updateTaskID, selectedFunctionId
//            ).enqueue(this)
        ApiClient.client.create(ApiInterFace::class.java)
            .taskFunctionFieldList(
                MySharedPreference.getUser(this)?.id.toString(),
                updateTaskID, selectedFunctionId
            ).enqueue(this)

    }

    var gallaryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            AppUtils.logDebug(TAG, "Camera Result ${result.resultCode}")
            if (result.resultCode == Activity.RESULT_OK) {
                AppUtils.logDebug(TAG, "Result Is ok")
                try {
                    if (result.data != null) {

                        val userid = MySharedPreference.getUser(this)
                        val selectedImageUri: Uri? = result.data?.data
                        val filePathFromUri = FilePath.getPath(this, selectedImageUri!!)
                        val file = File(filePathFromUri!!)
                        AppUtils.logError(TAG, "filepath" + file.toString())

                        requestbody =
                            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        body = MultipartBody.Part.createFormData("image", file.name, requestbody!!)


                        val absolutePath = file.absolutePath
                        val fileExtention: String = file.extension
                        val filename: String = file.name
                        taskfunction_media.setText(filename)
                        val fileinBytes = file.length()
                        val fileSizeInKB: Float = (fileinBytes / 1024).toFloat()
                        Toast.makeText(
                            this,
                            "PAth=======" + absolutePath.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        val fileSizeInMB = fileSizeInKB / 1024
                        val df = DecimalFormat("#.#")
                        val fileTotalsize = df.format(fileSizeInMB)


//                        val requestFile: RequestBody =
//                            RequestBody.create(MediaType.parse("multipart/form-data"), file)
//                        val body = MultipartBody.Part.createFormData(
//                            "uploaded_file",
//                            file.name,
//                            requestFile
//                        )
//        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);

                        AppUtils.logDebug(TAG, "UpdateTaskId" + updateTaskID)


                        if (fileSizeInMB < 2) {
//                            AppUtils.logDebug(TAG,"Gallery File size----- ${df.format(fileSizeInMB)}--  $fileinBytes")
//                            chatViewModel!!.imageUri?.set(absolutePath)
//                            val view= View(this)
//                            chatViewModel!!.sendImageToChat(view)
                        }  else {
                            Toast.makeText(
                                this,
                                "Please Select Less Than 2 Files",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG)
                        .show()

                    AppUtils.logError(TAG, "Catch Exception----" + "$e")
                }

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
//            val listcontainers=ArrayList<ListContainers>()
//            val listperson=ArrayList<ListPerson>()
                val function = ArrayList<ListFunctionFieldlist>()


//            Toast.makeText(this, "Upload SuccessFully", Toast.LENGTH_SHORT).show()
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
            functionname.add(function.get(i).name)
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
        taskfunctionField?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {


                selectedFunctionFieldId = function.get(position).id.toInt()
//                callApi(selectedFunctionId.toString())


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setAdapter(function: ArrayList<ListFunctionFieldlist>) {
        recycler_viewMedia.visibility = View.VISIBLE
        AppUtils.logDebug(TAG, "In Set Adapter")
        val adapter = AdapterTaskFunction(this, function)
        recycler_viewMedia.adapter = adapter


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

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        AppUtils.logDebug(TAG, " requestCode : " + requestCode)
        when (requestCode) {
            MY_CAMERA_PERMISSION_CODE -> {
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                }
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


}