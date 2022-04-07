package com.pbt.myfarm.Util

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import com.pbt.myfarm.BuildConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class AppUtils {
    companion object {



        val DEBUG: Boolean = BuildConfig.DEBUG

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
        fun isFileAccessPermissionGranted(context:Context) :Boolean {
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
        fun isFileWriteAccessPermissionGranted(context:Context) :Boolean {
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
        val services: List<ActivityManager.RunningServiceInfo> = activityManager.getRunningServices(Int.MAX_VALUE)
        for (runningServiceInfo in services) {
            Log.d(
            "Service:%s", runningServiceInfo.service.getClassName())
            if (runningServiceInfo.service.getClassName().equals(serviceClass.name)) {
                return true
            }
        }
        return false
    }

     fun isInternet(context: Context):Boolean {
        val ConnectionManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            return true
        } else {
            return false
        }
//        return false
    }

    }