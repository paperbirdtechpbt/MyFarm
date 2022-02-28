package com.pbt.myfarm


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils

import retrofit2.Call
import retrofit2.Response


class ViewTaskViewModel(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<testresponse> {
    companion object {
        const val TAG: String = "ViewTaskViewModel"
    }

    var eventlist = MutableLiveData<List<TasklistDataModel>>()

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null
    @SuppressLint("StaticFieldLeak")
    var progress:ProgressBar?=null

    init {

        eventlist = MutableLiveData<List<TasklistDataModel>>()

    }

    fun onEventListRequest(context: Context) {

        ApiClient.client.create(ApiInterFace::class.java)
            .eventList("2").enqueue(this)

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            progress?.visibility= View.GONE
            AppUtils.logDebug(TAG, " Response : " + response.body())
            eventlist.value = emptyList()
            val baseList : testresponse =  Gson().fromJson(Gson().toJson(response.body()),
                testresponse::class.java)
            val upCommingTripList = ArrayList<TasklistDataModel>()
            baseList.data.forEach { routes ->

                routes.padzero= routes.name.padStart(4, '0')

                AppUtils.logError(TAG,"${routes.padzero}")

                    upCommingTripList.add(routes)

            }
            eventlist.value = upCommingTripList
            AppUtils.logDebug(TAG,"evenlist"+ Gson().toJson(upCommingTripList))
        }

    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logError(TAG, " onFailure : " + t.message)

    }


}
