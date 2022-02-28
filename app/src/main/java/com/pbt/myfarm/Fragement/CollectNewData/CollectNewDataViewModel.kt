package com.pbt.myfarm.Fragement.CollectNewData

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData.Companion.collectActivityList
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData.Companion.collectid
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData.Companion.collectname
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData.Companion.sensorid
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData.Companion.sensorlist
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData.Companion.sensorname

import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response

class CollectNewDataViewModel(
    val activity: Application
) : AndroidViewModel(activity),
    retrofit2.Callback<testresponse> {
    val TAG = "CollectNewDataViewModel"

    fun onCollectDataFieldList(context: Context) {
        ApiClient.client.create(ApiInterFace::class.java).collectDataFieldList(
            MySharedPreference.getUser(context)?.id.toString(),
            packList?.id.toString()
        ).enqueue(this)

    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            val baseresponse: testresponse =
                Gson().fromJson(Gson().toJson(response.body()), testresponse::class.java)

            sensorlist.clear()
            sensorname.clear()
            sensorid.clear()
            baseresponse.sensors.forEach {

                sensorlist.add(it)
             sensorid.add(it.id)
               sensorname.add(it.name)


            }
            collectname.clear()
            collectActivityList.clear()
            collectid.clear()
            baseresponse.collect_activity.forEach {

                collectActivityList.add(it)
                collectid.add(it.id)
                collectname.add(it.name)
            }

        }

    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logDebug(TAG, t.message.toString())
    }

}
