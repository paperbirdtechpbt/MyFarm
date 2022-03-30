package com.pbt.myfarm

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class EventViewModel(var activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<ResponseEventList> {
    var event = MutableLiveData<List<Event>>()
    val TAG = "EventViewModel"
    var progressbar: ProgressBar? = null

    fun onEventList(context: Context) {
//        ApiClient.client.create(ApiInterFace::class.java).myeventList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
        val userid = MySharedPreference.getUser(context)?.id.toString()
        val db = DbHelper(context, null)
        val eventList = db.getAllEventListData(userid)
        var events=ArrayList<Event>()
        eventList.forEach{list ->

            list.name= list.name?.replace("null","")
            if (list.description.isNullOrEmpty()){
                list.description="Desciption"
            }

            if (list.exp_start_date.isNullOrEmpty()){
                if (!list.actual_start_date.isNullOrEmpty()){
                    list.exp_start_date="Actual Start Date: ${list.actual_start_date}"
                }
                else{
                    list.exp_start_date="ExpStart Date: "
                }
            }
            else{
                list.exp_start_date="ExpStart Date: ${list.exp_start_date}"

            }
        }
        event.value = eventList
    }

    override fun onResponse(call: Call<ResponseEventList>, response: Response<ResponseEventList>) {
        if (!response.body()?.events.isNullOrEmpty()) {
            progressbar?.visibility = View.GONE
            val basereponse: ResponseEventList =
                Gson().fromJson(Gson().toJson(response.body()), ResponseEventList::class.java)
            if (!basereponse.events.isNullOrEmpty()) {
                event.value = emptyList()
                val myeventlist = ArrayList<Event>()
                basereponse.events.forEach {
                    myeventlist.add(it)

                }
                event.value = myeventlist
            }

        } else {
            progressbar?.visibility = View.GONE
        }


    }

    override fun onFailure(call: Call<ResponseEventList>, t: Throwable) {
        try {
            progressbar?.visibility = View.GONE
            AppUtils.logError(TAG, t.message.toString())
        } catch (e: Exception) {
            progressbar?.visibility = View.GONE
            AppUtils.logError(TAG, e.message.toString())

        }
    }


}
