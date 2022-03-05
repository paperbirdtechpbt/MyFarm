package com.pbt.myfarm.Activity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackListModel
import com.pbt.myfarm.PackList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response

class ViewPackViewModel(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<PackListModel>  {


    companion object {
        const val TAG: String = "ViewPackViewModel"
    }

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null
    @SuppressLint("StaticFieldLeak")
    var packlist = MutableLiveData<List<PackList>>()
    var progressBAr:ProgressBar?=null
    init {

        packlist = MutableLiveData<List<PackList>>()

    }
    fun onPackListRequest(context: Context) {
        val id=MySharedPreference.getUser(context)?.id.toString()
        ApiClient.client.create(ApiInterFace::class.java)
            .packList(id).enqueue(this)

    }

    override fun onResponse(call: Call<PackListModel>, response: Response<PackListModel>) {
        if(response.body()?.error==false){
            packlist.value = emptyList()
            val packListModel : PackListModel =  Gson().fromJson(
                Gson().toJson(response.body()),
                PackListModel::class.java)

            val upCommingPackList = ArrayList<PackList>()
            packListModel.data.forEach { routes ->

                routes.padzero= routes.name.padStart(4, '0')
                routes.type=" Type: "
                routes.labeldesciption=" Desciption: "

                upCommingPackList.add(routes)

            }
            packlist.value = upCommingPackList
            progressBAr?.visibility= View.GONE
        }
        else{

            AppUtils.logDebug(TAG,"Something went Wrong")
//            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show()
        }
      
    }

    override fun onFailure(call: Call<PackListModel>, t: Throwable) {
        AppUtils.logDebug(TAG, " Failure : " + t.localizedMessage)
    }

}
