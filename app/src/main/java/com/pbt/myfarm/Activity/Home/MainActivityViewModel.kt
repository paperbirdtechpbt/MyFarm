package com.pbt.myfarm.Activity.Home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.pbt.myfarm.Activity.PackConfigList.PackConfigResponse
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.PackConfig
import com.pbt.myfarm.PackConfigList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response

class MainActivityViewModel(val activity: Application):AndroidViewModel(activity)
    ,retrofit2.Callback<PackConfigResponse>{
    @SuppressLint("StaticFieldLeak")
    private var mycontext:Context?=null

    val TAG="MainActivityViewModel"



    fun packConfigList(context: Context) {
        mycontext=context
        ApiClient.client.create(ApiInterFace::class.java)
            .packConfigList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
    }

    override fun onResponse(
        call: Call<PackConfigResponse>,
        response: Response<PackConfigResponse>
    ) {
        if (response.body()?.error==false){
//            val db = DbHelper(activity, null)
//            db.dropPackConfigTable()
            val packconfiglist = ArrayList<PackConfig>()

            val baseList : PackConfigResponse =  Gson().fromJson(
                Gson().toJson(response.body()),
                PackConfigResponse::class.java)
            baseList.data.forEach { routes ->
                packconfiglist.add(routes)

            }
//            if (!packconfiglist.isNullOrEmpty()){
//                addPackToDatabase(packconfiglist)
//
//            }
            packCOnfigFielList(mycontext!!,packconfiglist)


        }


    }

//    private fun addPackToDatabase(packconfiglist: ArrayList<PackConfigList>) {
//        for (i in 0 until packconfiglist.size){
//            val db = DbHelper(activity, null)
//            db.addPackConfigList(packconfiglist.get(i))
//        }
//    }

    override fun onFailure(call: Call<PackConfigResponse>, t: Throwable) {
        Toast.makeText(activity, "Configlist failure response", Toast.LENGTH_SHORT).show()
    }

    fun packCOnfigFielList(context: Context, packconfiglist: ArrayList<PackConfig>,) {
        if (packconfiglist !=null){
            for(i in 0 until packconfiglist.size){

                val service = ApiClient.client.create(ApiInterFace::class.java)
                val apiInterFace = service.packConfigFieldList(
                 MySharedPreference.getUser(context)?.id.toString(),
                    packconfiglist.get(i).id.toString(),"")

                apiInterFace.enqueue(object : retrofit2.Callback<PackFieldResponse> {
                    override fun onResponse(
                        call: Call<PackFieldResponse>,
                        response: Response<PackFieldResponse>
                    ) {
                        AppUtils.logDebug(TAG, Gson().toJson(response.body()))
                        if (response.body()?.error == false) {
                            val db=DbHelper(context,null)
//                            db.dropPackCommunityGroupTable()
//                            db.dropPackConfigffIELDList()
//                            db.dropPackConfigffIELDFieldList()
                            val packconfigList = ArrayList<PackConfigFieldList>()

                            val baseList : PackFieldResponse =  Gson().fromJson(
                                Gson().toJson(response.body()),
                                PackFieldResponse::class.java)



                            baseList.data.forEach { routes ->
                                packconfigList.add(routes)
                                for (i in 0 until routes.field_list!!.size){
                                    val item= routes.field_list!!.get(i)
//                                    db.addPackConfigffIELD_field_list(item,routes.field_id,
//                                        packconfiglist.get(i))

                                }


                            }

                            for (i in 0 until packconfigList.size){

                                val item=packconfigList.get(i)
//                                db.addPackConfigffIELDList(item)

                            }
                            baseList.CommunityGroup.forEach { routes ->
//                                db.addPackCommunityGroup(routes)

                            }




                        }
                    }

                    override fun onFailure(call: Call<PackFieldResponse>, t: Throwable) {
                        try {
                            AppUtils.logError(TAG, t.message.toString())
                        } catch (e: Exception) {
                            AppUtils.logError(TAG, e.localizedMessage)

                        }
                    }

                })

            }
        }




    }


}
