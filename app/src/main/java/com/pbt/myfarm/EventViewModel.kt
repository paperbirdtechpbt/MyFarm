package com.pbt.myfarm

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response

class EventViewModel(var activity:Application):AndroidViewModel(activity) ,retrofit2.Callback<ResponseEventList>{
    var event = MutableLiveData<List<EventList>>()
    val TAG="EventViewModel"
    var progressbar:ProgressBar?=null

    fun onEventList(context:Context){
        ApiClient.client.create(ApiInterFace::class.java).myeventList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
    }
    override fun onResponse(call: Call<ResponseEventList>, response: Response<ResponseEventList>) {
        if ( !response.body()?.events.isNullOrEmpty()){
            progressbar?.visibility=View.GONE
            val basereponse:ResponseEventList= Gson().fromJson(Gson().toJson(response.body()),ResponseEventList::class.java)
            if (!basereponse.events.isNullOrEmpty()){
                event.value = emptyList()
                val myeventlist=ArrayList<EventList>()
                basereponse.events.forEach{
                    myeventlist.add(it)

                }
                event.value =myeventlist
            }

        }
        else{
            progressbar?.visibility=View.GONE
        }


    }

    override fun onFailure(call: Call<ResponseEventList>, t: Throwable) {
        try {
            progressbar?.visibility=View.GONE
            AppUtils.logError(TAG,t.message.toString())
        }
        catch (e:Exception){
            progressbar?.visibility=View.GONE
            AppUtils.logError(TAG,e.message.toString())

        }
    }


}
