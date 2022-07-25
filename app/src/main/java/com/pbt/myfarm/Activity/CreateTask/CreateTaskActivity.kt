package com.pbt.myfarm.Activity.CreateTask


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
//import com.abedelazizshe.lightcompressorlibrary.CompressionListener
//import com.abedelazizshe.lightcompressorlibrary.VideoCompressor
//import com.abedelazizshe.lightcompressorlibrary.VideoQuality
//import com.abedelazizshe.lightcompressorlibrary.config.Configuration
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.TaskFunctions.*
import com.pbt.myfarm.Activity.ViewMediaActivity.ViewMediaActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.selectedComunityGroupTask
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.Activity.task_object.ViewTaskObjectActivity
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArray
import com.pbt.myfarm.CreatetaskViewModel.Companion.groupArrayId
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_ALLUSERS
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_UPDATE_LIST
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.FilePath
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityCreateTaskBinding
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_task_function.*
import kotlinx.android.synthetic.main.activity_task_function.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class CreateTaskActivity : AppCompatActivity(), PickiTCallbacks, DialogInterface.OnCancelListener,
    Callback<ResponseTaskFunctionaliyt> {

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
    var list: List<ListNavigationTaskFunction>? = null
    var scanfunctionId = ""
    private var fileVideo: File? = null
    private var fileSizeInMB: Long? = null
    private var recordedVideoPath: String = ""
    private val uris = mutableListOf<Uri>()
    private var progressCircular: CircularProgressIndicator? = null
    private var progressCircularLabel: TextView? = null
    private var progressCircularTitle: TextView? = null
    var dialogBuilder: AlertDialog.Builder? = null
    var spinner: Spinner? = null
    var pack_label_desciption: TextView? = null
    var label_attachmedia: TextView? = null
    var taskfunction_media: EditText? = null
    var btnExecute: Button? = null
    var btnChooseFile: Button? = null
    var spinnerfield: Spinner? = null
    var spinnermediatype: Spinner? = null
    var spinnerAllUser: Spinner? = null
    var alertDialog: AlertDialog? = null

    private var selectedMediatype = 0


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
        setContentView(R.layout.activity_create_task)
        dialogBuilder = AlertDialog.Builder(this)


        createCustomDialog()

    }

    private fun createCustomDialog() {

        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_task_function, null)
        dialogBuilder?.setView(dialogView)

        spinner = dialogView.findViewById(R.id.taskfunction)
        pack_label_desciption = dialogView.findViewById(R.id.pack_label_desciption)
        btnExecute = dialogView.findViewById(R.id.btnExecute)
        btnChooseFile = dialogView.findViewById(R.id.btnChoosefile)
        taskfunction_media = dialogView.findViewById(R.id.taskfunction_media)
        spinnerfield = dialogView.findViewById(R.id.taskfunction_field)
        spinnermediatype = dialogView.findViewById(R.id.field_mediatype)
        spinnerAllUser = dialogView.findViewById(R.id.taskfunction_alluser)
        label_attachmedia = dialogView.findViewById(R.id.label_attachmedia)
        progressCircularLabel = dialogView.findViewById(R.id.progress_circular_label)
        progressCircular = dialogView.findViewById(R.id.progress_circular)
        progressCircularTitle = dialogView.findViewById(R.id.progress_circular_title)
        alertDialog = dialogBuilder!!.create()
        alertDialog?.setOnCancelListener(this)




    }

    private fun setButtonListner() {
        btn_taskfuntion.setOnClickListener {
            val intent = Intent(this, TaskFunctionActivity::class.java)
            intent.putExtra(CONST_TASKFUNCTION_TASKID, updateTaskList)
            intent.putExtra(CONST_TASKFUNCTION_ALLUSERS, allUsersList)
            AppUtils.logError(TAG, "intentlist" + allUsersList.toString())
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

        viewmodel?.layoutProgressbar = layout_ProgressBar
        list = ArrayList()
        viewmodel?.addItemToNavigationList(this, privilegeListName)

        viewmodel?.navlist?.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                val adapter = NavigtionListAdatapter(list) {
                    if (it.name.equals("PersonScan") || it.name.equals("AddPerson")) {

                        val options = arrayOf<CharSequence>(
                            "Add Person",
                            "Scan Person",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Choose Add Person or Scan Person")
                        builder.setItems(options) { dialog, item ->
                            if (options[item] == "Add Person") {
                                viewmodel?.selectFunctionName?.postValue("PersonScan")
                                viewmodel?.checkTaskFunction(this, it)


                            } else if (options[item] == "Scan Person") {
                                scanfunctionId = "217"
                                openZingScanner(this)

                                viewmodel?.selectFunctionName?.postValue("AddPerson")


                            } else if (options[item] == "Cancel") {
                                dialog.dismiss()
                            }
                        }
                        builder.show()
                    } else if (it.name.equals("AddContainer") || it.name.equals("InsertContainer")) {

                        val options = arrayOf<CharSequence>(
                            "Add Container",
                            "Scan Container",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Choose Add container or Scan Contaioner")
                        builder.setItems(options) { dialog, item ->
                            if (options[item] == "Add Container") {
                                viewmodel?.selectFunctionName?.postValue("AddContainer")
                                viewmodel?.checkTaskFunction(this, it)


                            } else if (options[item] == "Scan Container") {
                                scanfunctionId = "216"

                                openZingScanner(this)

                                viewmodel?.selectFunctionName?.postValue("InsertContainer")


                            } else if (options[item] == "Cancel") {
                                dialog.dismiss()
                            }
                        }
                        builder.show()
                    } else if (it.name.equals("AddPack") || it.name.equals("Add Pack")) {

                        val options = arrayOf<CharSequence>(
                            "Add Pack",
                            "Scan Pack",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Choose Add Pack or Scan Pack")
                        builder.setItems(options) { dialog, item ->
                            if (options[item] == "Add Container") {
                                viewmodel?.selectFunctionName?.postValue("AddPack")
                                viewmodel?.checkTaskFunction(this, it)


                            } else if (options[item] == "Scan Pack") {
                                scanfunctionId = "215"

                                openZingScanner(this)

                                viewmodel?.selectFunctionName?.postValue("ScanPack")


                            } else if (options[item] == "Cancel") {
                                dialog.dismiss()
                            }
                        }
                        builder.show()
                    } else if (it.name.equals("Photo")) {

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
                                    setResult(AppConstant.CAMERA_REQUEST, cameraIntent)
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
                                    setResult(AppConstant.GALARY_REQUEST, pickPhoto)
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
                    } else {
                        viewmodel?.checkTaskFunction(this, it)

                    }
                }
                recycler_taskfunctions.setLayoutManager(
                    LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL, false
                    )
                )

                recycler_taskfunctions.adapter = adapter
            }

        }

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

        viewmodel?.allUserList?.observe(this) { list ->
            if (!list.isNullOrEmpty())
                allUsersList.addAll(list)
            AppUtils.logError(TAG, "alluSerslist" + allUsersList.toString())

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
                        viewmodel?.callApiStoreTask(
                            this, configtype?.id.toString(),
                            prefix, selectedCommunityGroup, userId.toString(),
                            successObject.toString(), "", true
                        )
//
                    } else {
                        viewmodel?.callApiStoreTask(
                            this, configtype?.id.toString(),
                            prefix, selectedCommunityGroup, userId.toString(),
                            successObject.toString(), configtype?.name_prefix.toString(), false
                        )

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

                    viewmodel?.callApiUpdatetask(
                        this,
                        updateTaskList?.task_config_id.toString(),
                        prefix,
                        selectedCommunityGroup,
                        userId.toString(),
                        successObject.toString(),
                        updateTaskList?.taskConfigNamePrefix.toString() + updateTaskList?.padzero.toString(),
                        updateTaskList?.id.toString()
                    )

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

    private fun openZingScanner(context: Context) {
        val intentIntegrator = IntentIntegrator(context as Activity)
        intentIntegrator.setPrompt("My Farm Code Scanner")
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.initiateScan()
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

    private fun openCameraToCaptureVideo() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            val captureVideo = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            setResult(AppConstant.VIDEO_CAPTURE, captureVideo)
            captureVideoResult.launch(captureVideo)
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
                            fileSizeInMB = viewmodel?.getFileSize(fileVideo!!)
                            fileVideo?.let {
                                showCustomALertDialog(it.name)
                                val type = AppUtils().checkExtension(it.extension)
                                if (type == "video") {

                                    uris.clear()
//                                    progressVisible(true)
//                                    handleResult(result.data)
                                }
                            }
                        } else {
                            val pickiT = PickiT(this, this, this)

                            pickiT.getPath(result.data!!.data, Build.VERSION.SDK_INT)

                        }

                    } catch (e: Exception) {
                        AppUtils.logError(
                            TAG, "GalleryLauncher error " + (e.localizedMessage?.toString()
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
//                    taskfunction_media.setText(file?.name)
                    fileSizeInMB = viewmodel?.getFileSize(fileVideo!!)
                    showCustomALertDialog(file?.name)
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

                        showCustomALertDialog(it.name)

                        val type = AppUtils().checkExtension(it.extension)
                        if (type == "video") {
                            fileSizeInMB = viewmodel?.getFileSize(fileVideo!!)
//                            progressVisible(true)
//                            handleResu.lt(result.data)
                        }
                    }

                } catch (e: Exception) {
                    AppUtils.logError(
                        TAG, "GalleryLauncher error " + (e.localizedMessage?.toString()
                            ?: "")
                    )
                }

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
//            processVideo()
        } else if (data != null && data.data != null) {
            val uri = data.data
            uris.add(uri!!)
//            processVideo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {

//                progressbar_taskexecute.visibility = View.GONE

                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {

                viewmodel?.callApiScan(intentResult.contents.toString(), this, scanfunctionId)
//                progressbar_taskexecute.visibility = View.VISIBLE
            }
        } else {
//            progressbar_taskexecute.visibility = View.GONE

            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showCustomALertDialog(filename: String?) {
        ApiClient.client.create(ApiInterFace::class.java)
            .taskFunctionFieldList(
                MySharedPreference.getUser(this)?.id.toString(),
               updateTaskList?.id.toString(), "173"
            ).enqueue(this)
        val functionid = ArrayList<String>()
        val functionname = ArrayList<String>()


        spinner?.visibility = View.VISIBLE
        spinnermediatype?.visibility = View.GONE
        pack_label_desciption?.visibility = View.VISIBLE
        taskfunction_media?.visibility = View.VISIBLE
        label_attachmedia?.visibility = View.VISIBLE

        label_attachmedia?.setText("File Name")
        pack_label_desciption?.setText("Select Media Type")

        taskfunction_media?.setText(filename)

        alertDialog?.show()

        btnExecute?.setOnClickListener{


                        if (fileSizeInMB!! < 40) {
//                            progressbar_taskexecute.visibility = View.GONE

                            progressCircular?.visibility = View.VISIBLE
                            progressCircularLabel?.visibility = View.VISIBLE

                            callApiTaskExecuteButton()

                            btnExecute?.visibility = View.GONE


                        } else {
//                            progressbar_taskexecute.visibility = View.GONE
                            Toast.makeText(this, "File Must be Less than 40MB", Toast.LENGTH_SHORT).show()

                        }
                    }

                }

    private fun callApiTaskExecuteButton() {
        val mTaskID = updateTaskList?.id.toString()
        val mFunctionID = "173"
        val mUserID = MySharedPreference.getUser(this)?.id.toString()
        val mFieldID =""
        val mMediaTypeID = selectedMediatype.toString()

        if (AppUtils().isInternet(this)) {
            viewmodel?.uploadFile(
                this,
                mTaskID,
                mFunctionID,
                mUserID,
                mFieldID,
                fileVideo,
                progressCircular,
//                progressbar_taskexecute,
                progressCircularLabel,
                btnExecute,
                 mMediaTypeID
            )

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
        fileSizeInMB = viewmodel?.getFileSize(fileVideo!!)

        taskfunction_media?.setText(fileVideo!!.name)
    }

    override fun PickiTonMultipleCompleteListener(
        paths: java.util.ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
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

    @SuppressLint("SetTextI18n")
//    private fun processVideo() {
//
//        GlobalScope.launch {
//            VideoCompressor.start(
//                context = applicationContext,
//                uris,
//                isStreamable = true,
//                saveAt = Environment.DIRECTORY_MOVIES,
//                listener = object : CompressionListener {
//                    override fun onProgress(index: Int, percentage: Float) {
//                        if (percentage <= 100 && percentage.toInt() % 5 == 0)
//                            runOnUiThread {
//                                AppUtils.logDebug(TAG, "onProgress" + percentage.toInt().toString())
//                                progressCircular?.setProgressCompat(
//                                    percentage.toInt(),
//                                    true
//                                )
//                                progressCircularLabel?.text = "${percentage.toInt()}%"
//                            }
//                    }
//
//                    override fun onStart(index: Int) {
//                        runOnUiThread {
//                            AppUtils.logDebug(TAG, "on onCancelled" + index.toString())
//
//                            progressVisible(true)
//                        }
//                    }
//
//                    override fun onSuccess(index: Int, size: Long, path: String?) {
//                        runOnUiThread {
//                            path?.let {
//                                AppUtils.logDebug(TAG, "on onSuccess" + path)
//
//                                fileVideo = File(it)
//                                progressVisible(false)
//
//                            }
//                        }
//                    }
//
//                    override fun onFailure(index: Int, failureMessage: String) {
//                        runOnUiThread {
//                            AppUtils.logDebug(TAG, "on Failure" + failureMessage)
//
//                            progressVisible(false)
//                        }
//                    }
//
//                    override fun onCancelled(index: Int) {
//                        runOnUiThread {
//                            AppUtils.logDebug(TAG, "on onCancelled" + index.toString())
//
//                            progressVisible(false)
//                        }
//                    }
//                },
//                configureWith = Configuration(
//                    quality = VideoQuality.LOW,
//                    isMinBitrateCheckEnabled = true
//                )
//            )
//        }
//    }

    private fun progressVisible(isVisible: Boolean) {
        if (isVisible) {
//            btnExecute.visibility = View.GONE
            progressCircularLabel?.visibility = View.VISIBLE
            progressCircular?.visibility = View.VISIBLE
            progressCircularTitle?.visibility = View.VISIBLE
        } else {
//            btnExecute.visibility = View.VISIBLE
            progressCircularLabel?.visibility = View.GONE
            progressCircular?.visibility = View.GONE
            progressCircularTitle?.visibility = View.GONE
        }
    }

    override fun onCancel(p0: DialogInterface?) {
        alertDialog?.dismiss()
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
                val mediatype = ArrayList<Listmedia_types>()

                if (!baseResponse.TaskFunction.isNullOrEmpty()) {
                    baseResponse.TaskFunction.forEach { tsk ->
                        fieldListTaskFunctions.add(tsk)
                        functionName = tsk.name
                    }
                }
                if (!baseResponse.media_types.isNullOrEmpty()) {
                    baseResponse.media_types.forEach {
                        mediatype.add(it)
                    }


                }
                if (functionName == "ATTACH_MEDIA") {

                    setAdapterFieldMediaType(mediatype)

                }
            }

        } else {
            AppUtils.logDebug(TAG, "on Failure")
        }
    }

    private fun setAdapterFieldMediaType(mediatype: ArrayList<Listmedia_types>) {
        field_mediatype?.visibility = View.VISIBLE
        val functionName = ArrayList<String>()
        val functionId = ArrayList<String>()


        for (i in 0 until mediatype.size) {
            functionName.add(mediatype[i].name)
            functionId.add(mediatype[i].id.toString())
        }
        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, functionName)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = dd

//        if (selectedFunctionNo.isNotEmpty()) {
//            for ((count, item) in functionId.withIndex()) {
//                if (item == selectedFunctionNo) {
//                    field_mediatype.setSelection(count)
//                }
//            }
//        }

        setFieldListenerMediatype(spinner, mediatype)
    }

    private fun setFieldListenerMediatype(fieldMediatype: Spinner?, mediatype: ArrayList<Listmedia_types>) {
        spinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    selectedMediatype = mediatype[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
    }

    override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {
        try {
            AppUtils.logDebug(TAG, t.message.toString())
        } catch (e: java.lang.Exception) {
            AppUtils.logDebug(TAG, e.message.toString())

        }
    }
}


