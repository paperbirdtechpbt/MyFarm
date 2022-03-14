package com.pbt.myfarm.Service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Event.ResponseOfflineSync
import com.pbt.myfarm.Activity.Event.ResposneUpdateEvent
import com.pbt.myfarm.Activity.PackConfigList.PackConfigResponse
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Fragement.CollectNewData.Data
import com.pbt.myfarm.Fragement.CollectNewData.ResponsecollectAcitivityResultValue
import com.pbt.myfarm.Fragement.CollectNewData.UnitList
import com.pbt.myfarm.ModelClass.OffLineSyncModel
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFarmService() : Service() {

    val TAG = "MyFarmService"
    var userID = ""

    val db= DbHelper(this,null)

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG," Service run ${System.currentTimeMillis()}")

        userID =  MySharedPreference.getUser(this)?.id.toString()

        GlobalScope.launch(Dispatchers.IO){
            syncOfflineData(userID)
        }


        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
    }

    private fun syncOfflineData(userID : String){

        val service = ApiClient.getClient()!!.create(ApiInterFace::class.java)
        val apiInterFace = service.offLineSync(userID)

        apiInterFace.enqueue(object : Callback<OffLineSyncModel> {

            override fun onFailure(call: Call<OffLineSyncModel>, t: Throwable) {
                try {
                    AppUtils.logError(TAG, t.message.toString())
                } catch (e: Exception) {
                    AppUtils.logError(TAG, e.localizedMessage)
                }
            }
            override fun onResponse(
                p0: Call<OffLineSyncModel>,
                p1: Response<OffLineSyncModel>
            ) {
                if(p1.isSuccessful){
                    p1.body()?.let {
                        if(it.Data.packs_new.isNotEmpty()){
                            for(pack in it.Data.packs_new) {
                                db.addNewPack(pack)
                            }
                        }
                    }
                }

            }
        })
    }
}