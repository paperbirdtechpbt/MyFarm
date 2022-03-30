package com.pbt.myfarm


import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayID
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayIDKey
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayName
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayNameKey
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.packconfiglist
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
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


class PackViewModel(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<PackFieldResponse> {

    val TAG: String = "PackViewModel"

    companion object {
        val packCommunityList = ArrayList<PackCommunityList>()
        val packCommunityListname = ArrayList<String>()
        val packconfigList = ArrayList<PackConfigFieldList>()
        var packconfigobject: Packconfig? = null
        var labelPackConfigName: String? = null
        var labelPackConfigPrefix: String? = null

        var groupArrayPack: ArrayList<String>? = ArrayList()
        var groupArrayIdPack: ArrayList<String>? = ArrayList()
    }

    val context: Context = activity
    var configlist = MutableLiveData<List<PackConfigFieldList>>()
    lateinit var activityContext: Activity
    var progressbar: ProgressBar? = null


    var namePrefix: ObservableField<String>? = null
    var confiType: ObservableField<String>? = null
    var desciption: ObservableField<String>? = null
    var communityGroup: ObservableField<String>? = null
    var units: ObservableField<String>? = null
    var quantity: ObservableField<String>? = null
    var customer: ObservableField<String>? = null
    var listsize: ObservableField<Int>? = null

    init {
        namePrefix = ObservableField("")
        confiType = ObservableField("")
        desciption = ObservableField("")
        units = ObservableField("")
        quantity = ObservableField("")
        customer = ObservableField("")
        configlist = MutableLiveData<List<PackConfigFieldList>>()

    }

//    fun addPack() {
//        val db = DbHelper(activity, null)
//
//        val newTask= ViewPackModelClass( packname = namePrefix?.get().toString(),
//            packType = confiType?.get().toString(),packdesciption =  desciption?.get().toString(),
//          communitygrip = communityGroup?.get().toString(),customer = customer?.get().toString(),
//            quantitiy = quantity?.get().toString(),units = units?.get().toString()
//        )
//
//        db.addPack(newTask)
//
//    }

//    fun updatePack() {
//        val db= DbHelper(context,null)
//        val newTask= ViewPackModelClass(
//        packname = namePrefix?.get().toString(),
//        packdesciption = desciption?.get().toString(),
//        packType =confiType?.get().toString(),
//        communitygrip = communityGroup?.get().toString(),customer = customer?.get().toString(),
//            quantitiy = quantity?.get().toString(),units = units?.get().toString())
//       val result= db.updatePack(newTask, namePrefix?.get()!!)
//        if (result >= 0) {
//            Toast.makeText(context, "Update Sucessfully", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, PackActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//
//
//
//
//        } else {
//            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show()
//
//        }
//    }


    fun onConfigFieldList(context: Context, updateTaskIdBoolean: Boolean, updateTaskId: String) {


//        if (updateTaskId=="0"){
//            ApiClient.client.create(ApiInterFace::class.java)
//                .packConfigFieldList(MySharedPreference.getUser(context)?.id.toString(),
//                    packList?.pack_config_id.toString(), packList?.id!!.toString()
//                ).enqueue(this)
//
//        }
//        else{
//            ApiClient.client.create(ApiInterFace::class.java)
//                .packConfigFieldList(MySharedPreference.getUser(context)?.id.toString(),
//                    packconfiglist?.id.toString(),"").enqueue(this)
//        }
        val db = DbHelper(context, null)
        val communityyGrouplist = db.getCommunityGroupList()


        communityyGrouplist.forEach {
            packCommunityList.add(
                PackCommunityList(
                    it.id.toString(), it.name!!,
                    it.community_group!!
                )
            )
            packCommunityListname.add(it.name)

        }

        progressbar?.visibility = View.GONE

        val list = db.getPackConfigFieldList(packconfiglist?.id.toString())

        val packconfigList = db.getAllPackConfig()
        AppUtils.logDebug(TAG, "PakcOCmmunityList" + packconfigList)


        for (i in 0 until packconfigList.size) {

            val item = packconfigList.get(i)

            if (item.id == packconfiglist?.id) {
                labelPackConfigName = item.name
                labelPackConfigPrefix = item.name_prefix
                break
            }
        }

        val packConfigField = ArrayList<PackConfigFieldList>()
        if (list.size >= 1) {

                for (i in 0 until packconfigList.size) {
                    arrayID!!.add("0")
                    arrayName!!.add("0")
                    arrayIDKey!!.add("f_id")
                    arrayNameKey!!.add("f_value")
                }

            for (i in 0 until list.size) {
                val item = list.get(i)
                if (item.field_description == null) {
                    val name = db.getFieldNameFromListChoice(item.field_name.toString())
                    item.field_description = name
                }


                if (item.list != "N/A") {

                    if (item.list == "Field") {

                        val fieldList = db.getFieldList()
                        val packfieldList = ArrayList<PackFieldList>()

                        for (i in 0 until fieldList.size) {

                            val itm = fieldList.get(i)
                            packfieldList.add(
                                PackFieldList(itm.id.toString(), itm.name, null, null, null)
                            )

                        }
                        packConfigField.add(
                            PackConfigFieldList(
                                item.field_name, item.field_description.toString(),
                                item.field_description.toString(),
                                item.field_type.toString(), null,
                                item.editable.toString(), packfieldList
                            )
                        )
                    } else if (item.list == "Person") {

                        val packfieldList = ArrayList<PackFieldList>()

                        val fiellist = db.getPersonList()

                        for (i in 0 until fiellist.size) {

                            val itm = fiellist.get(i)
                            packfieldList.add(
                                PackFieldList(
                                    itm.id.toString(),
                                    itm.lname + itm.fname,
                                    null,
                                    null,
                                    null
                                )
                            )

                        }
                        packConfigField.add(
                            PackConfigFieldList(
                                item.field_name, item.field_description.toString(),
                                item.field_description.toString(),
                                item.field_type.toString(), null,
                                item.editable.toString(), packfieldList
                            )
                        )
                    } else if (item.list == "Team") {

                        val packfieldList = ArrayList<PackFieldList>()

                        val fiellist = db.getTeamList()

                        for (i in 0 until fiellist.size) {

                            val itm = fiellist.get(i)
                            packfieldList.add(
                                PackFieldList(itm.id.toString(), itm.name, null, null, null)
                            )

                        }
                        packConfigField.add(
                            PackConfigFieldList(
                                item.field_name, item.field_description.toString(),
                                item.field_description.toString(),
                                item.field_type.toString(), null,
                                item.editable.toString(), packfieldList
                            )
                        )

                    } else {

                        val listid = item.list

                        val fieldList = db.getListChoice(listid!!)
                        val packfieldList = ArrayList<PackFieldList>()

                        for (i in 0 until fieldList.size) {

                            val itm = fieldList.get(i)
                            packfieldList.add(
                                PackFieldList(itm.id.toString(), itm.name, null, null, null)
                            )

                        }
                        AppUtils.logDebug(TAG, " packfieldList" + packfieldList.toString())

                        packConfigField.add(
                            PackConfigFieldList(
                                item.field_name, item.field_description.toString(),
                                item.field_description.toString(),
                                item.field_type.toString(), null,
                                item.editable.toString(), packfieldList
                            )
                        )
                    }

                } else {
                    packConfigField.add(
                        PackConfigFieldList(
                            item.field_name, item.field_description.toString(),
                            item.field_description.toString(),
                            item.field_type.toString(), null,
                            item.editable.toString(), emptyList()
                        )
                    )
                }


            }
            AppUtils.logDebug(TAG, "config fieldList" + packConfigField.toString())
        }
        configlist.value = packConfigField


    }

    override fun onResponse(call: Call<PackFieldResponse>, response: Response<PackFieldResponse>) {
        progressbar?.visibility = View.GONE

        configlist.value = emptyList()
        packconfigList.clear()
        packCommunityList.clear()
        packCommunityListname.clear()
        try {
            val baseList: PackFieldResponse = Gson().fromJson(
                Gson().toJson(response.body()),
                PackFieldResponse::class.java
            )
            packconfigobject = null

            packconfigobject = baseList.packconfig

            baseList.data.forEach { routes ->
                packconfigList.add(routes)

            }
            baseList.CommunityGroup.forEach { routes ->
                packCommunityList.add(routes)
                packCommunityListname.add(routes.name)
            }

            if (!packconfigList.isNullOrEmpty()) {
                for (i in 0 until packconfigList.size) {
                    arrayID!!.add("0")
                    arrayName!!.add("0")
                    arrayIDKey!!.add("f_id")
                    arrayNameKey!!.add("f_value")
                }
                AppUtils.logDebug(
                    TAG,
                    "arrayid" + CreatePackActivity.arrayID + "\n" + CreatePackActivity.arrayName
                )

            }


            configlist.value = packconfigList
            var comConfigFieldList: ArrayList<PackCommunityList>? = null
            comConfigFieldList = Gson().fromJson(
                Gson().toJson(response.body()?.CommunityGroup),
                ArrayList<PackCommunityList>()::class.java
            )


            for (i in 0 until comConfigFieldList.size) {
                val row: Any = comConfigFieldList!!.get(i)
                val rowmap: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
                val name = rowmap["name"].toString()
                val communitygroupid = rowmap["id"].toString()

                groupArrayPack?.add(name)
                groupArrayIdPack?.add(communitygroupid)
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.localizedMessage.toString())
        }


    }

    override fun onFailure(call: Call<PackFieldResponse>, t: Throwable) {
        AppUtils.logDebug(TAG, "response failure PackViewmodel" + t.message)

    }


}