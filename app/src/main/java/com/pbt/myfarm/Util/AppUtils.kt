package com.pbt.myfarm.Util

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pbt.myfarm.BuildConfig
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CRASH_FOLDER_NAME
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import java.util.regex.Pattern


class AppUtils {
    companion object {


        val DEBUG: Boolean = BuildConfig.DEBUG

        private val fileImages = arrayOf(
            "jpg",
            "png",
            "gif",
            "jpeg"
        )

        fun isEmailValid(email: String): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun logError(tag: String, message: String) {
            if (DEBUG == true)
                Log.e("##" + tag, message)
        }

        fun logDebug(tag: String, message: String) {
            if (DEBUG == true)
                Log.d("##" + tag, message)
        }

        fun logWarning(tag: String, message: String) {
            if (DEBUG == true)
                Log.w("##" + tag, message)
        }

        fun paramRequestBody(param: String): RequestBody {
            val parameter: RequestBody =
                param.toRequestBody("multipart/form-data".toMediaTypeOrNull());
            return parameter
        }

        fun paramRequestTextBody(param: String): RequestBody {
            return param.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        fun isFileAccessPermissionGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return Environment.isExternalStorageManager()
            } else {
                val readExtStorage = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                return readExtStorage == PackageManager.PERMISSION_GRANTED
            }
        }

        fun isFileWriteAccessPermissionGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return Environment.isExternalStorageManager()
            } else {
                val readExtStorage = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                return readExtStorage == PackageManager.PERMISSION_GRANTED
            }
        }

    }


    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services: List<ActivityManager.RunningServiceInfo> =
            activityManager.getRunningServices(Int.MAX_VALUE)
        for (runningServiceInfo in services) {
            Log.d(
                "Service:%s", runningServiceInfo.service.getClassName()
            )
            if (runningServiceInfo.service.getClassName().equals(serviceClass.name)) {
                return true
            }
        }
        return false
    }

    fun isInternet(context: Context): Boolean {
        val ConnectionManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun checkImageFile(file: File): Boolean {
        for (extension in fileImages) {
            if (file.getName().lowercase(Locale.getDefault()).endsWith(extension)) {
                return true
            }
        }
        return false
    }

    fun checkExtension(extension: String): String {
        val arrayExtensionIMage = arrayListOf<String>("jpg", "png", "jpeg", "JPEG", "PNG", "JPG")
        val arrayExtensionVideo = arrayListOf<String>(
            "MP4",
            "MOV",
            "WMV",
            "AVI",
            "AVCHD",
            "MKV",
            "mp4",
            "mov",
            "wmv",
            "avi",
            "mkv"
        )
        val arrayExtensionDocument = arrayListOf<String>(
            "DOC",
            "DOCX",
            "HTML",
            "ODT",
            "PDF",
            "XLS",
            "XLSX",
            "PPT",
            "PPTX",
            "TXT",
            "doc",
            "docx",
            "html",
            "odt",
            "pdf",
            "xls",
            "xlsx",
            "ppt",
            "pptx",
            "txt"
        )

        var filetype: String = ""
        if (arrayExtensionIMage.contains(extension)) {
            filetype = "image"
        } else if (arrayExtensionVideo.contains(extension)) {
            filetype = "video"
        } else if (arrayExtensionDocument.contains(extension)) {
            filetype = "file"
        }
         else{
             filetype="otherDocument"
         }
        return filetype

    }
    fun createCrashFoler(context: Context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            var extStorageDirectory: String? = null
            val file: File
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                file = File(context.getFilesDir(), CONST_CRASH_FOLDER_NAME)
            } else {
                extStorageDirectory = Environment.getExternalStorageDirectory().toString()
                file = File(extStorageDirectory, CONST_CRASH_FOLDER_NAME)
            }
            if (!file.exists()) {
                file.mkdirs()
            }
        }

    fun askForStoragePermission(context: Activity) {
        ActivityCompat.requestPermissions(
            context, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            AppConstant.STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    fun specialPermissionStorage(context: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivityForResult(intent,  AppConstant.STORAGE_PERMISSION_REQUEST_CODE);
    }

    fun askForCameraPermission(context: Activity) {
        ActivityCompat.requestPermissions(
            context, arrayOf(
                Manifest.permission.CAMERA
            ),
            AppConstant.CAMERA_PERMISSION_REQUEST_CODE
        )
    }

}}