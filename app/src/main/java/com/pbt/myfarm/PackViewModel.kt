package com.pbt.myfarm


import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.packconfiglist
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PackViewModel(val activity:Application
) :AndroidViewModel(activity)  ,
    retrofit2.Callback<PackFieldResponse> {

     val TAG: String = "PackViewModel"
    companion object {
        val packCommunityList = ArrayList<PackCommunityList>()
        val packCommunityListname = ArrayList<String>()
        val packconfigList = ArrayList<PackConfigFieldList>()
        var  packconfigobject :Packconfig?=null

        var groupArrayPack:ArrayList<String>?= ArrayList()
        var groupArrayIdPack:ArrayList<String>?= ArrayList()
    }
    val context: Context =activity
    var configlist = MutableLiveData<List<PackConfigFieldList>>()
    lateinit var activityContext : Activity


    var namePrefix: ObservableField<String>? = null
    var confiType: ObservableField<String>? = null
    var desciption: ObservableField<String>? = null
 var communityGroup:ObservableField<String>?=null
 var units:ObservableField<String>?=null
 var quantity:ObservableField<String>?=null
 var customer:ObservableField<String>?=null
    var listsize:ObservableField<Int>?=null

    init {
        namePrefix = ObservableField("")
        confiType = ObservableField("")
        desciption = ObservableField("")
        units = ObservableField("")
        quantity = ObservableField("")
        customer = ObservableField("")
        configlist = MutableLiveData<List<PackConfigFieldList >>()

    }

    fun addPack() {
        val db = DbHelper(activity, null)

        val newTask= ViewPackModelClass( packname = namePrefix?.get().toString(),
            packType = confiType?.get().toString(),packdesciption =  desciption?.get().toString(),
          communitygrip = communityGroup?.get().toString(),customer = customer?.get().toString(),
            quantitiy = quantity?.get().toString(),units = units?.get().toString()
        )

        db.addPack(newTask)





    }

    fun updatePack() {
        val db= DbHelper(context,null)
        val newTask= ViewPackModelClass(
        packname = namePrefix?.get().toString(),
        packdesciption = desciption?.get().toString(),
        packType =confiType?.get().toString(),
        communitygrip = communityGroup?.get().toString(),customer = customer?.get().toString(),
            quantitiy = quantity?.get().toString(),units = units?.get().toString())
       val result= db.updatePack(newTask, namePrefix?.get()!!)
        if (result >= 0) {
            Toast.makeText(context, "Update Sucessfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, PackActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)




        } else {
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show()

        }
    }



    fun onConfigFieldList(context: Context, updateTaskIdBoolean: Boolean, updateTaskId: String) {
//        if (updateTaskIdBoolean){
//            ApiClient.client.create(ApiInterFace::class.java)
//                .packConfigFieldList("2","5", updateTaskId).enqueue(this) }
//        else{
        if (updateTaskId=="0"){
            ApiClient.client.create(ApiInterFace::class.java)
                .packConfigFieldList("2", packList?.pack_config_id.toString(), packList?.id!!).enqueue(this)

        }
        else{
            ApiClient.client.create(ApiInterFace::class.java)
                .packConfigFieldList("2",packconfiglist?.id.toString(),"").enqueue(this)
        }


//        }



    }

    override fun onResponse(call: Call<PackFieldResponse>, response: Response<PackFieldResponse>) {
        configlist.value = emptyList()
        packconfigList.clear()
        packCommunityList.clear()
        packCommunityListname.clear()
        val baseList : PackFieldResponse =  Gson().fromJson(
            Gson().toJson(response.body()),
            PackFieldResponse::class.java)
        packconfigobject=null

        packconfigobject=baseList.packconfig

        baseList.data.forEach { routes ->
            packconfigList.add(routes)

        }
        baseList.CommunityGroup.forEach { routes ->
            packCommunityList.add(routes)
            packCommunityListname.add(routes.name)
        }

        configlist.value = packconfigList
        var comConfigFieldList:ArrayList<PackCommunityList>?=null
        comConfigFieldList= Gson().fromJson(Gson().toJson(response.body()?.CommunityGroup),ArrayList<PackCommunityList>()::class.java)


        for (i in 0 until comConfigFieldList.size){
            val row: Any = comConfigFieldList!!.get(i)
            val rowmap: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
            val name = rowmap["name"].toString()
            val communitygroupid = rowmap["id"].toString()

            groupArrayPack?.add( name)
            groupArrayIdPack?.add(communitygroupid)
            AppUtils.logDebug(TAG,"array<String>"+ name)
        }
    }

    override fun onFailure(call: Call<PackFieldResponse>, t: Throwable) {
        AppUtils.logDebug(TAG,"response failure PackViewmodel"+ t.message)

    }


}