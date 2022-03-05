package com.pbt.myfarm.Fragement.PackCollect

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.CollectDataFieldListItem
import com.pbt.myfarm.CollectdataRespose
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response

class CollectDataViewModel (val activity: Application
) : AndroidViewModel(activity)  ,
    retrofit2.Callback<CollectdataRespose>{

    companion object{
                val TAG="CollectDataViewModel"
    }

    val context: Context =activity
//    var progressBar:ProgressBar?=null
    var collectdatalist = MutableLiveData<List<CollectDataFieldListItem>>()
    init{
        collectdatalist = MutableLiveData<List<CollectDataFieldListItem >>()

    }

    fun onCollectDataList(context: Context){
        ApiClient.client.create(ApiInterFace::class.java).collectDataList(
            MySharedPreference.getUser(context)?.id.toString(),packList?.id.toString()
        ).enqueue(this)
    }
    override fun onResponse(call: Call<CollectdataRespose>, response: Response<CollectdataRespose>) {
        if(response.body()?.error == false){
//            progressBar?.visibility= View.GONE

            var list = ArrayList<CollectDataFieldListItem>()
            var baseresponse:CollectdataRespose=Gson().fromJson(Gson().toJson(response.body()),CollectdataRespose::class.java)
            collectdatalist.value= emptyList()

            baseresponse.data.forEach{
                list.add(it)
            }
          collectdatalist.value=list
        }
    }

    override fun onFailure(call: Call<CollectdataRespose>, t: Throwable) {
        AppUtils.logDebug(TAG,"Collect data list Failure--"+t.message)
    }

}
