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
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.PackConfig
import com.pbt.myfarm.PackList

import com.pbt.myfarm.PacksNew
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ViewPackViewModel(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<PackListModel> {


    companion object {
        const val TAG: String = "ViewPackViewModel"
    }

    @SuppressLint("StaticFieldLeak")
    val context: Context? = null

    @SuppressLint("StaticFieldLeak")
    var packlist = MutableLiveData<List<PacksNew>>()
    var packconfig = ArrayList<PackConfig>()
//    val viewPackModeClass = ArrayList<PacksNew>()

    var progressBAr: ProgressBar? = null

    init {

        packlist = MutableLiveData<List<PacksNew>>()

    }

    fun checkInteretConnection(context: Context) {
        val ConnectionManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            Toast.makeText(context, "Network Available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Network Not Available", Toast.LENGTH_LONG).show()
        }
    }
//        fun onPackListRequest(context: Context) {
//        val id= MySharedPreference.getUser(context)?.id.toString()
//        ApiClient.client.create(ApiInterFace::class.java)
//            .packList(id).enqueue(this)
//
//    }
    fun onPackListRequest(context: Context) {
        var database = DbHelper(context, null)

        database = DbHelper(context, null)
        val   packconfig= database.getAllPackConfig()
        val pcklist = database.getAllPack()

    AppUtils.logDebug(TAG,"packlist full"+pcklist.toString())


        val packsnew = java.util.ArrayList<PacksNew>()
        pcklist.forEach { routes ->
            if (packconfig.isNotEmpty()){
                for (i in 0 until  packconfig.size){
                    if (routes.pack_config_id==packconfig.get(i).id.toString()){
                        val configname=packconfig.get(i).name_prefix
                        if (configname!=null){
                            routes.padzero= configname+ routes.name!!.padStart(4, '0')
                        }
                        else{
                            routes.padzero= routes.name!!.padStart(4, '0')
                        }
                        routes.type=" Type: "
                        routes.labeldesciption=" Desciption: "
                        routes.pack_config_name= packconfig.get(i).name.toString()

                    }
                }
            }
            packsnew.add(routes)
        }
    if (packsnew.size>=1){
        packsnew.removeAt(0 )
        packlist.value=packsnew
    }

    }



    private fun checkInternetConnection(context: Context): Boolean {
        val ConnectionManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            return true
        } else {
            return false
        }
    }

    override fun onResponse(call: Call<PackListModel>, response: Response<PackListModel>) {
        try{
            if (response.body()?.error == false) {
                packlist.value = emptyList()
                val packListModel: PackListModel = Gson().fromJson(
                    Gson().toJson(response.body()),
                    PackListModel::class.java
                )

                val upCommingPackList = ArrayList<PacksNew>()


                packListModel.data.forEach { routes ->


//                if (packconfig.isNotEmpty()){
//                    for (i in 0 until  packconfig.size){
//                        if (routes.pack_config_id==packconfig.get(i).id.toString()){
//                            val configname=packconfig.get(i).name_prefix
//                            if (configname!=null){
//                                routes.padzero= configname+routes.name.padStart(4, '0')
//
//                            }
//                            else{
                    routes.padzero= routes.name!!.padStart(4, '0')
//
//                            }

                    routes.type=" Type: "
                    routes.labeldesciption=" Desciption: "

//                            routes.pack_config_id= packconfig.get(i).name.toString()
//                            routes.pack_config_name= packconfig.get(i).name.toString()

//                        }
//                    }
//                }



                    upCommingPackList.add(routes)
//
//                viewPackModeClass.add(ViewPackModelClass(routes.id,routes.name,routes.name_prefix,
//                    routes.pack_config_id,
//                    routes.pack_config_name," Type: "," Desciption: ",routes.description,
//                    routes.com_group,routes.created_by,routes.padzero) )

                }

                packlist.value = upCommingPackList
                progressBAr?.visibility = View.GONE
//            addPackToDatabase(viewPackModeClass)
            } else {
                progressBAr?.visibility = View.GONE
                AppUtils.logDebug(TAG, "Something went Wrong")
            }

        }
        catch (e:Exception){
            AppUtils.logDebug(TAG,e.message.toString())
        }

    }

//    private fun addPackToDatabase(viewPackModeClass: ArrayList<ViewPackModelClass>) {
//        for (i in 0 until viewPackModeClass.size){
//            val db = DbHelper(activity, null)
//            db.addPack(viewPackModeClass.get(i))
//        }


//
//    }

    override fun onFailure(call: Call<PackListModel>, t: Throwable) {
        try {
            progressBAr?.visibility = View.GONE

        }
        catch (e:Exception){
            progressBAr?.visibility = View.GONE

        }


        AppUtils.logDebug(TAG, " Failure : " + t.localizedMessage)
    }

}
