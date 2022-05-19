package com.pbt.myfarm.Activity.ViewMediaActivity

import android.app.Application
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.registerReceiver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.TaskFunctions.*
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response


class ViewModelViewMedia(var activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<ResponseTaskFunctionaliyt> {


    val TAG = "ViewModelViewMedia"
    var mediaList = MutableLiveData<List<ListFunctionFieldlist>>()
    var progressbar: ProgressBar? = null

    init {
        mediaList = MutableLiveData<List<ListFunctionFieldlist>>()
    }

    fun callMediaListApi(context: Context, taskid: String) {
        ApiClient.client.create(ApiInterFace::class.java)
            .taskFunctionFieldList(
                MySharedPreference.getUser(context)?.id.toString(),
                taskid, "173"
            ).enqueue(this)
    }

    override fun onResponse(
        call: Call<ResponseTaskFunctionaliyt>,
        response: Response<ResponseTaskFunctionaliyt>
    ) {


        if (response.body()?.error == false) {

            mediaList.postValue(emptyList())

            val basereponse: ResponseTaskFunctionaliyt = Gson().fromJson(
                Gson().toJson(response.body()), ResponseTaskFunctionaliyt::class.java
            )

            val attachmedia = ArrayList<ListFunctionFieldlist>()

            if (!basereponse.Function.isNullOrEmpty()) {
                basereponse.Function.forEach {
                    attachmedia.add(it)
                    AppUtils.logDebug(TAG, "in loop function" + it)

                }
            }
            if (!attachmedia.isNullOrEmpty())
                mediaList.postValue(attachmedia)


        }
    }

    override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {

        AppUtils.logDebug(TAG, t.message.toString())

    }

    fun downloadFile(link: String?, name: String?, context: Context)  {
        var manager: DownloadManager? = null
        Toast.makeText(context, "File Downloading...", Toast.LENGTH_SHORT).show()

        manager = context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(link)
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
    var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            // Do Something
        }
    }


}
