package com.pbt.myfarm

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Event.ResposneNotificationCount
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel(val activity: Application) : AndroidViewModel(activity),
    Callback<ResposneNotificationCount> {
    var notificationlist = MutableLiveData<List<ListNotificationData>>()
    var progressbar=MutableLiveData(View.GONE)
    var layout=MutableLiveData(View.GONE)

    init {
        notificationlist = MutableLiveData<List<ListNotificationData>>()

    }

    override fun onResponse(
        call: Call<ResposneNotificationCount>,
        response: Response<ResposneNotificationCount>
    ) {
        if (response.isSuccessful) {
            val baseresponse: ResposneNotificationCount = Gson().fromJson(
                Gson().toJson(response.body()),
                ResposneNotificationCount::class.java
            )
            if (baseresponse.data.isNullOrEmpty()){
                layout.postValue(View.VISIBLE)
            }
            else{
                layout.postValue(View.GONE)

            }
            notificationlist.postValue(baseresponse.data)
            progressbar.postValue(View.GONE)

        }

    }

    override fun onFailure(call: Call<ResposneNotificationCount>, t: Throwable) {
    }

    fun getNotificationList(context: Context, userid: String?) {
        ApiClient.client.create(ApiInterFace::class.java).notificationCount(userid.toString())
            .enqueue(this)

    }

}
