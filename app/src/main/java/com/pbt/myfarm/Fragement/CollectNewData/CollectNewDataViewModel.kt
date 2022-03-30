package com.pbt.myfarm.Fragement.CollectNewData

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.packconfiglist
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment.Companion.collectActivityList
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment.Companion.collectid
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment.Companion.collectname
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment.Companion.sensorid
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment.Companion.sensorlist
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment.Companion.sensorname
import com.pbt.myfarm.Fragement.PackCollect.CollectActivityList
import com.pbt.myfarm.Fragement.PackCollect.SensorsList

import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.PackViewModel.Companion.packconfigList
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
    var progressbar:ProgressBar?=null

    fun onCollectDataFieldList(context: Context) {
//        ApiClient.client.create(ApiInterFace::class.java).collectDataFieldList(
//            MySharedPreference.getUser(context)?.id.toString(),
//            packList?.id.toString()
//        ).enqueue(this)

        val db=DbHelper(context,null)
        val cltid =db.getSinglDataFromPackConfig(packList?.pack_config_id.toString())
        val sensorList =db.getSensorList()

        val collectActivityListOffline=  db.setCollectActivitySpinner(cltid.get(0).collect_activity_id.toString())
        collectname.clear()
        collectActivityList.clear()
        collectid.clear()
        sensorlist.clear()
        sensorname.clear()
        sensorid.clear()


        collectActivityList.add(CollectActivityList("0","Select"))
        collectid.add("0")
        collectname.add("Select")

        sensorlist.add(SensorsList("0","Select"))
        sensorid.add("0")
        sensorname.add("Select")

      for (i in 0 until collectActivityListOffline.size){
          val item=collectActivityListOffline.get(i)
          collectActivityList.add(CollectActivityList(item.id.toString(), item.name!!))
          collectid.add(item.id.toString())
          collectname.add(item.name)
      }
        for (i in 0 until sensorList.size){
            val item=sensorList.get(i)

            sensorlist.add(SensorsList(item.id.toString(), item.name!!))
            sensorid.add(item.id.toString())
            sensorname.add(item.name)
        }







    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            val baseresponse: testresponse =
                Gson().fromJson(Gson().toJson(response.body()), testresponse::class.java)

            progressbar?.visibility= View.GONE

            sensorlist.clear()
            sensorname.clear()
            sensorid.clear()

            sensorlist.add(SensorsList("0","Select"))
            sensorid.add("0")
            sensorname.add("Select")
            baseresponse.sensors.forEach {

                sensorlist.add(it)
                sensorid.add(it.id)
                sensorname.add(it.name)


            }
            collectname.clear()
            collectActivityList.clear()
            collectid.clear()

            collectActivityList.add(CollectActivityList("0","Select"))
            collectid.add("0")
            collectname.add("Select")
            baseresponse.collect_activity.forEach {

                collectActivityList.add(it)
                collectid.add(it.id)
                collectname.add(it.name)
            }

        }

    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        progressbar?.visibility= View.GONE

        AppUtils.logDebug(TAG, t.message.toString())
    }

}
