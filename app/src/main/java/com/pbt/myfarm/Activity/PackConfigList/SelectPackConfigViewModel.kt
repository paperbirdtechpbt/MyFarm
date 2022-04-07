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
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.ConfigResponse
import com.pbt.myfarm.PackConfig
import com.pbt.myfarm.PackConfigList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.ViewTaskViewModel
import retrofit2.Call
import retrofit2.Response

class SelectPackConfigViewModel(var activity: Application): AndroidViewModel(activity),
    retrofit2.Callback<PackConfigResponse> {
    companion object {
        const val TAG: String = "SelectPackConfigViewModel"
    }
    var configlist = MutableLiveData<List<PackConfig>>()

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null

    init {

        configlist = MutableLiveData<List<PackConfig>>()

    }

    fun onConfigTypeRequest(context: Context) {
        if (AppUtils().isInternet(context)){
        ApiClient.client.create(ApiInterFace::class.java)
            .packConfigList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
        }
        else{
            val db=DbHelper(context,null)
            val list=db.getAllPackConfig()
            configlist.value=list
        }


    }





    override fun onResponse(call: Call<PackConfigResponse>, response: Response<PackConfigResponse>
    ) {

        if (response.body()?.error==false){

            try{
                configlist.value = emptyList()
                val baseList : PackConfigResponse =  Gson().fromJson(
                    Gson().toJson(response.body()),
                    PackConfigResponse::class.java)
                val upCommingTripList = ArrayList<PackConfig>()
                baseList.data.forEach { routes ->
                    upCommingTripList.add(routes)

                }
                configlist.value = upCommingTripList

            }
            catch (e:Exception){
                AppUtils.logDebug(TAG,e.message.toString())
            }

        }



    }

    override fun onFailure(call: Call<PackConfigResponse>, t: Throwable) {
        AppUtils.logDebug(TAG,t.message.toString())

    }

}
