package com.pbt.myfarm.Util

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pbt.myfarm.BuildConfig
import com.pbt.myfarm.Util.AppConstant.Companion.DATE_TIME_FORMATS
import com.pbt.myfarm.Util.AppConstant.Companion.DATE_TIME_FORMATS_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.DATE_TIME_FORMATS_T
import com.pbt.myfarm.Util.AppConstant.Companion.DATE_TIME_FORMATS_YYYY_MM_DD_HH_MM_SS
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
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

    fun getCurrentDateTimeAccordingToUTC(format: String): String? {
        val date = Date(System.currentTimeMillis())
        val simpleDateFormat = SimpleDateFormat(format)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return simpleDateFormat.format(date)
    }

    fun getTimeStamp(strDate : String) : Long {
//        val dateFormat = SimpleDateFormat(DATE_TIME_FORMATE)
//        val parsedDate = dateFormat.parse(strDate)
//        val timestamp = Timestamp(parsedDate.time).

        try {
            val sdf = SimpleDateFormat(DATE_TIME_FORMATS)
            val date = sdf.parse(strDate)
            return date.time
        }catch (e: Exception){
            val sdf = SimpleDateFormat(DATE_TIME_FORMATS_DATE)
            val date = sdf.parse(strDate)
            return date.time
        }

    }


    fun timeStampCovertToDateTime(timeStamp : Long) : String {
        val sdf = SimpleDateFormat(DATE_TIME_FORMATS_T)
        val tz = TimeZone.getDefault()
        val now = Date()
        val offsetFromUtc = tz.getOffset(now.time)
        val localeTimeStr = sdf.format(timeStamp + offsetFromUtc)
       return localeTimeStr
    }

    fun systemDateTime() :String{
        val dateFormat = SimpleDateFormat(DATE_TIME_FORMATS_YYYY_MM_DD_HH_MM_SS)
        val cal = Calendar.getInstance()
        return dateFormat.format(cal.time)
    }

}