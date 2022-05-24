package com.pbt.myfarm.Activity.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Activity.PackConfigList.PackConfigResponse
import com.pbt.myfarm.BuildConfig
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.ModelClass.SendDataMasterList
import com.pbt.myfarm.PackConfig
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(val activity: Application) : AndroidViewModel(activity),
    Callback<PackConfigResponse> {
    @SuppressLint("StaticFieldLeak")
    private var mycontext: Context? = null

    val TAG = "MainActivityViewModel"


    fun packConfigList(context: Context) {
        mycontext = context
        ApiClient.client.create(ApiInterFace::class.java)
            .packConfigList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)

    }

    fun sendDataMastersApi(userID: String, context: Context) {
        var senddata: SendDataMasterList? = null
        val db = DbHelper(context, null)

        val collectData = db.getCollectDataToBeSend(userID)
        val collecrDataList = collectData

        AppUtils.logError(TAG, "collectdaa" + collectData.toString())

        val evnet = db.getEventsTobeSend(userID)
        val eventlist = evnet

        val packnew = db.getPacksToBeSend(userID)
        val packlist = packnew


        val taskField = ArrayList<com.pbt.myfarm.ModelClass.TaskField>()

        val taskobject = ArrayList<com.pbt.myfarm.ModelClass.TaskObject>()

        val task = db.getTasksToBeSend(userID)
        val tasklist = task

        senddata = SendDataMasterList(collectData, evnet, packnew, taskField, taskobject, task)
        val service = ApiClient.getClient()?.create(ApiInterFace::class.java)
        val apiInterFace = service?.postJson(senddata)

        apiInterFace?.enqueue(object : Callback<testresponse> {
            override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
                try {
                    if (response.body()?.error == false) {
                        if (!response.body()?.msg.isNullOrBlank()) {

                            if (!tasklist.isNullOrEmpty()) {
                                for (i in 0 until tasklist.size) {
                                    val item = tasklist.get(i)
                                    if (item.status != 3) {
                                        db.changeTaskStatus(
                                            item.name.toString(),
                                            item.task_config_id.toString()
                                        )
                                    }
                                }
                            }
                            if (!packlist.isNullOrEmpty()) {
                                for (i in 0 until packlist.size) {
                                    val item = packlist.get(i)
                                    if (item.status != 3) {
                                        db.changePackStatus(
                                            item.name.toString(),
                                            item.pack_config_id.toString(), item
                                        )
                                    }


                                }
                            }
                            if (!eventlist.isNullOrEmpty()) {
                                for (i in 0 until eventlist.size) {
                                    val item = eventlist.get(i)
                                    if (item.status != 3) {
                                        db.changeEventStatus(item.id.toString())
                                    }
                                }
                            }
                            if (!collecrDataList.isNullOrEmpty()) {
                                for (i in 0 until collecrDataList.size) {
                                    val item = collecrDataList.get(i)
                                    if (item.status != 3) {
                                        db.changeCollectDataStatus(item.id.toString())
                                    }
                                }
                            }
                            Toast.makeText(context, "Uploaded SuccessFull", Toast.LENGTH_SHORT)
                                .show()
//                           MainActivity().showAlertDialog(context)
                            showAlertDialog(context)

                            AppUtils.logDebug(
                                TAG,
                                "Send Data SuccessFull " + response.body()?.msg.toString()
                            )
                        }
                    } else {
                        AppUtils.logDebug(TAG, "Send Data Error " + response.body()?.msg.toString())
                    }
                } catch (e: java.lang.Exception) {
                    AppUtils.logDebug(TAG, e.message.toString())
                }
            }

            override fun onFailure(call: Call<testresponse>, t: Throwable) {
                try {
                    AppUtils.logDebug(TAG, "Send Data Failur " + t.message.toString())

                    AppUtils.logError(TAG, t.message.toString())

                } catch (e: java.lang.Exception) {
                    AppUtils.logError(TAG, t.message.toString())

                }
            }

        })
    }

    private fun showAlertDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("LogOut")
            .setMessage("Are you sure you want to Logout")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->

                    val db = DbHelper(context, null)
                    db.truncateAllTables()
                    MySharedPreference.clearPref(context)
                    context.startActivity(Intent(context, LoginActivity::class.java))
//                   finish()
                    (context as Activity).finish()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }


    override fun onResponse(
        call: Call<PackConfigResponse>,
        response: Response<PackConfigResponse>
    ) {
        if (response.body()?.error == false) {
//            val db = DbHelper(activity, null)
//            db.dropPackConfigTable()
            val packconfiglist = ArrayList<PackConfig>()

            val baseList: PackConfigResponse = Gson().fromJson(
                Gson().toJson(response.body()),
                PackConfigResponse::class.java
            )
            baseList.data.forEach { routes ->
                packconfiglist.add(routes)

            }
//            if (!packconfiglist.isNullOrEmpty()){
//                addPackToDatabase(packconfiglist)
//
//            }
            packCOnfigFielList(mycontext!!, packconfiglist)


        }


    }


    override fun onFailure(call: Call<PackConfigResponse>, t: Throwable) {
        Toast.makeText(activity, "Configlist failure response", Toast.LENGTH_SHORT).show()
    }

    fun truncateAllTables(context: Context) {
        val db = DbHelper(context, null)
        db.truncateAllTables()
    }

    fun packCOnfigFielList(context: Context, packconfiglist: ArrayList<PackConfig>) {
        if (packconfiglist != null) {
            for (i in 0 until packconfiglist.size) {

                val service = ApiClient.client.create(ApiInterFace::class.java)
                val apiInterFace = service.packConfigFieldList(
                    MySharedPreference.getUser(context)?.id.toString(),
                    packconfiglist.get(i).id.toString(), ""
                )

                apiInterFace.enqueue(object : retrofit2.Callback<PackFieldResponse> {
                    override fun onResponse(
                        call: Call<PackFieldResponse>,
                        response: Response<PackFieldResponse>
                    ) {
                        AppUtils.logDebug(TAG, Gson().toJson(response.body()))
                        if (response.body()?.error == false) {
//                            db.dropPackCommunityGroupTable()
//                            db.dropPackConfigffIELDList()
//                            db.dropPackConfigffIELDFieldList()
                            val packconfigList = ArrayList<PackConfigFieldList>()

                            val baseList: PackFieldResponse = Gson().fromJson(
                                Gson().toJson(response.body()),
                                PackFieldResponse::class.java
                            )



                            baseList.data.forEach { routes ->
                                packconfigList.add(routes)
                                for (i in 0 until routes.field_list!!.size) {
                                    val item = routes.field_list!!.get(i)
//                                    db.addPackConfigffIELD_field_list(item,routes.field_id,
//                                        packconfiglist.get(i))

                                }


                            }

                            for (i in 0 until packconfigList.size) {

                                val item = packconfigList.get(i)
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

    fun getFileAccessPermission(context: Context) {


    }


}
