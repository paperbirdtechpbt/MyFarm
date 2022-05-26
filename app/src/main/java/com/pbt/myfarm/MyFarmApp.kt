package com.pbt.myfarm

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import com.pbt.myfarm.Activity.TaskFunctions.ProgressRequestBody
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.ResponseTaskExecution
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class MyFarmApp : Application(),ProgressRequestBody.UploadCallback{

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                uploadCrashFiles(this)
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                uploadCrashFiles(this)
            }
        }

    }

    private fun uploadCrashFiles(context: Context) {

        val path = "${context.filesDir}/${AppConstant.CONST_CRASH_FOLDER_NAME}"
        Log.e("Files", "Path: $path")
        val directory = File(path)
        val files: Array<File> = directory.listFiles() as Array<File>
        files.let {
            for (i in files.indices) {
                GlobalScope.launch {
                    doUpload(files[i],context)
                    Log.e("Files", "FileName:" + Uri.fromFile(files[i]).path)
                }

            }
        }

    }

    private fun doUpload(file: File,context: Context){

        val body = ProgressRequestBody(file, "file", this)
        val fileUpload : MultipartBody.Part= MultipartBody.Part.createFormData("file", file.name, body)
        val service = ApiClient.client.create(ApiInterFace::class.java)
        val apiInterFace = service.uploadCrashFile(fileUpload)
        apiInterFace.enqueue(object : Callback<ResponseTaskExecution> {
            override fun onResponse(
                call: Call<ResponseTaskExecution>,
                response: Response<ResponseTaskExecution>
            ) {
                val deleted = file.delete()
                if (!deleted) {
                    var deleted2 = false
                    try {
                        deleted2 = file.canonicalFile.delete()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (!deleted2) {
                       context.deleteFile(file.name)
                    }
                }

            }
            override fun onFailure(call: Call<ResponseTaskExecution>, t: Throwable) {
                AppUtils.logDebug("MyFarm", "Exception : ${t.message}")
            }

        })
    }

    override fun onProgressUpdate(percentage: Int) {
    }

}