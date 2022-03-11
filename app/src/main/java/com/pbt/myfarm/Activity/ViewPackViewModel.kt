package com.pbt.myfarm.Activity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.PackListModel
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.DataBase.DbHelper
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
    val viewPackModeClass = ArrayList<ViewPackModelClass>()

    var progressBAr:ProgressBar?=null
    init {

        packlist = MutableLiveData<List<PackList>>()

    }
    fun checkInteretConnection(context: Context){
        val ConnectionManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            Toast.makeText(context, "Network Available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Network Not Available", Toast.LENGTH_LONG).show()
        }
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
            val db = DbHelper(activity, null)
          db.dropPackTable()


            packListModel.data.forEach { routes ->

                routes.padzero= routes.name.padStart(4, '0')
                routes.type=" Type: "
                routes.labeldesciption=" Desciption: "

                upCommingPackList.add(routes)

                viewPackModeClass.add(ViewPackModelClass(routes.id,routes.name,routes.name_prefix,
                    routes.pack_config_id,
                    routes.pack_config_name," Type: "," Desciption: ",routes.description,
                    routes.com_group,routes.created_by,routes.padzero) )

            }

            packlist.value = upCommingPackList
            progressBAr?.visibility= View.GONE
            addPackToDatabase(viewPackModeClass)
        }
        else{

            AppUtils.logDebug(TAG,"Something went Wrong")
        }
      
    }

    private fun addPackToDatabase(viewPackModeClass: ArrayList<ViewPackModelClass>) {
        for (i in 0 until viewPackModeClass.size){
            val db = DbHelper(activity, null)
            db.addPack(viewPackModeClass.get(i))
        }



    }

    override fun onFailure(call: Call<PackListModel>, t: Throwable) {
        AppUtils.logDebug(TAG, " Failure : " + t.localizedMessage)
    }

}
