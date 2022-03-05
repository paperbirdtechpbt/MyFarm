package com.pbt.myfarm.Activity.SelectConfigType


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.HttpResponse.ConfigResponse
import com.pbt.myfarm.ConfigTaskList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.ViewTaskViewModel
import retrofit2.Call
import retrofit2.Response


class SelectConfigViewModel(var activity: Application):AndroidViewModel(activity),
    retrofit2.Callback<ConfigResponse> {
    companion object {
        const val TAG: String = "SelectConfigViewModel"
    }
    var configlist = MutableLiveData<List<ConfigTaskList>>()
    var progressbar:ProgressBar?=null

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null

    init {

        configlist = MutableLiveData<List<ConfigTaskList>>()

    }

    fun onConfigTypeRequest(context: Context) {

        ApiClient.client.create(ApiInterFace::class.java)
            .taskConfigList("2").enqueue(this)

    }

    override fun onResponse(call: Call<ConfigResponse>, response: Response<ConfigResponse>) {
        if (response.body()?.error == false) {

            configlist.value = emptyList()
            val baseList : ConfigResponse =  Gson().fromJson(
                Gson().toJson(response.body()),
                ConfigResponse::class.java)
            val upCommingTripList = ArrayList<ConfigTaskList>()
            baseList.data.forEach { routes ->




                upCommingTripList.add(routes)

            }
            configlist.value = upCommingTripList
            progressbar?.visibility= View.GONE
        }
    }

    override fun onFailure(call: Call<ConfigResponse>, t: Throwable) {
        AppUtils.logDebug(TAG,t.message.toString())

    }

}
