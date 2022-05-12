package com.pbt.myfarm.Fragement.PackCollect

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.CollectData
import com.pbt.myfarm.CollectDataFieldListItem
import com.pbt.myfarm.CollectdataRespose
import com.pbt.myfarm.DataBase.DbHelper
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
    var progressBar:ProgressBar?=null
    var layout_nodata:ConstraintLayout?=null
    var collectdatalist = MutableLiveData<List<CollectData>>()
    init{
        collectdatalist = MutableLiveData<List<CollectData >>()

    }

    fun onCollectDataList(context: Context){

        if (AppUtils().isInternet(context)){
                    ApiClient.client.create(ApiInterFace::class.java).collectDataList(
            MySharedPreference.getUser(context)?.id.toString(),packList?.id.toString()
        ).enqueue(this)
        }
        else{
            val db = DbHelper(context, null)
            val  collectlist = db.getAllCollectData(packList?.id.toString())
            if (collectlist.isNotEmpty()){
                progressBar?.visibility= View.GONE
                layout_nodata?.visibility=View.GONE

                collectdatalist.value=collectlist
        }
            else{
                layout_nodata?.visibility=View.VISIBLE
                progressBar?.visibility= View.GONE


            }
        }
    }


    override fun onResponse(call: Call<CollectdataRespose>, response: Response<CollectdataRespose>) {
        try{
            if(response.body()?.error == false){
                AppUtils.logDebug(TAG,response.body().toString())
            progressBar?.visibility= View.GONE

                val list = ArrayList<CollectData>()
                val baseresponse:CollectdataRespose=Gson().fromJson(Gson().toJson(response.body()),CollectdataRespose::class.java)
                collectdatalist.value= emptyList()

                baseresponse.data.forEach{
                    list.add(it)
                }
                collectdatalist.value=list
            }
            else{
                progressBar?.visibility= View.GONE

            }
        }
        catch (e:Exception){
            AppUtils.logDebug(TAG,"Collect data list Failure--"+e.message)
            progressBar?.visibility= View.GONE

        }

    }

    override fun onFailure(call: Call<CollectdataRespose>, t: Throwable) {
        try {
            AppUtils.logDebug(TAG,"Collect data list Failure--"+t.message)
            progressBar?.visibility= View.GONE

        }
        catch (e:Exception){
            AppUtils.logDebug(TAG,"Collect data list Failure--"+e.message)
            progressBar?.visibility= View.GONE

        }
    }

}
