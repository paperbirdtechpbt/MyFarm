package com.pbt.myfarm.Activity.PackConfigList

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigViewModel
import com.pbt.myfarm.ConfigTaskList
import com.pbt.myfarm.HttpResponse.ConfigResponse
import com.pbt.myfarm.PackConfigList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.ViewTaskViewModel
import retrofit2.Call
import retrofit2.Response

class SelectPackConfigViewModel(var activity: Application): AndroidViewModel(activity),
    retrofit2.Callback<PackConfigResponse> {
    companion object {
        const val TAG: String = "SelectPackConfigViewModel"
    }
    var configlist = MutableLiveData<List<PackConfigList>>()
    var progressbar: ProgressBar?=null

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null

    init {

        configlist = MutableLiveData<List<PackConfigList>>()

    }

    fun onConfigTypeRequest(context: Context) {

        ApiClient.client.create(ApiInterFace::class.java)
            .packConfigList("2").enqueue(this)

    }





    override fun onResponse(call: Call<PackConfigResponse>, response: Response<PackConfigResponse>
    ) {

        configlist.value = emptyList()
        val baseList : PackConfigResponse =  Gson().fromJson(
            Gson().toJson(response.body()),
            PackConfigResponse::class.java)
        val upCommingTripList = ArrayList<PackConfigList>()
        baseList.data.forEach { routes ->




            upCommingTripList.add(routes)

        }
        configlist.value = upCommingTripList

        progressbar?.visibility= View.GONE

    }

    override fun onFailure(call: Call<PackConfigResponse>, t: Throwable) {
        AppUtils.logDebug(TAG,t.message.toString())

    }

}
