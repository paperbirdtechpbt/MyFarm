package com.pbt.myfarm.Activity.Event

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ViewModeleditEvent(var activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<EditEventList> {
    val TAG = "ViewModeleditEvent"

    var editeventlist= MutableLiveData<List<Data>>()

    fun onEditEvent(editEventID: String, context: Context) {
        ApiClient.client.create(ApiInterFace::class.java).editEvent(
            MySharedPreference.getUser(context)?.id.toString(),
            editEventID
        ).enqueue(this)

    }

    override fun onResponse(call: Call<EditEventList>, response: Response<EditEventList>) {
        if (response.body()?.error == false) {
            editeventlist.value= emptyList()
            val baseresponse: EditEventList =
                Gson().fromJson(Gson().toJson(response.body()), EditEventList::class.java)
            val eventdata=ArrayList<Data>()
            baseresponse.data.forEach{
                eventdata.add(it)

            }
            editeventlist.value=eventdata

        } else {
            try {
                AppUtils.logError(TAG, "Error True")
            } catch (e: Exception) {
                AppUtils.logError(TAG, e.message.toString())

            }
        }
    }

    override fun onFailure(call: Call<EditEventList>, t: Throwable) {
        try {
            println(t.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }




}
