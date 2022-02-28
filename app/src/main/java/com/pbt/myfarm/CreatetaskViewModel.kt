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
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.ComunityGroup
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.HttpResponse.TaskFieldResponse
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.Service.*
import com.pbt.myfarm.Util.AppUtils
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList


class CreatetaskViewModel(var activity: Application) : AndroidViewModel(activity)
    ,retrofit2.Callback<TaskFieldResponse> {
    companion object {
        const val TAG: String = "CreateTaskViewModel"
        var comConfigFieldList:ArrayList<ComunityGroup>?=null
        var groupArray:ArrayList<String>?= ArrayList()
        var groupArrayId:ArrayList<String>?= ArrayList()
        var field_list: ArrayList<String>?= ArrayList()
        var field: ArrayList<Field>?= ArrayList()

    }
    val context:Context=activity
    var configlist = MutableLiveData<List<ConfigFieldList>>()



    var namePrefix: ObservableField<String>? = null
    var confiType: ObservableField<String>? = null
    var desciption: ObservableField<String>? = null
    var expectedStartDate: ObservableField<String>? = null
    var expectedEndDate: ObservableField<String>? = null
    var startDate: ObservableField<String>? = null
    var EndDate: ObservableField<String>? = null
    var progressbar: ProgressBar? = null

    init {
        namePrefix = ObservableField("")
        confiType = ObservableField("")
        desciption = ObservableField("")
        expectedStartDate = ObservableField("")
        expectedEndDate = ObservableField("")
        startDate = ObservableField("")
        EndDate = ObservableField("")


            configlist = MutableLiveData<List<ConfigFieldList>>()


    }

//    fun login(view: View) {
//        val db = DbHelper(activity, null)
//        val newTask=ViewTaskModelClass(ENTRYNAME = namePrefix?.get().toString(),
//        ENTRYTYPE = confiType?.get().toString(),ENTRYDETAIL =  desciption?.get().toString(),
//        ExpectedStartDate = expectedStartDate?.get().toString(),
//        ExpectedEndDate =expectedEndDate?.get().toString(),
//        StartDate = startDate?.get().toString(), EndDate = EndDate?.get().toString() )
//        db.addTask(
//            "pbt",newTask
//        )



//    }

    fun update() {
        val db= DbHelper(context,null)
        val newTask=ViewTaskModelClass(ENTRYNAME = namePrefix?.get().toString(),
            ENTRYTYPE = confiType?.get().toString(),ENTRYDETAIL =  desciption?.get().toString(),
            ExpectedStartDate = expectedStartDate?.get().toString(),
            ExpectedEndDate =expectedEndDate?.get().toString(),
            StartDate = startDate?.get().toString(), EndDate = EndDate?.get().toString() )
      val result=  db.updateTask(newTask,newTask.ENTRYNAME)
        if (result >= 0) {
            Toast.makeText(context, "Update Sucessfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ViewTaskActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

            Activity().finish()


        } else {
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show()

        }

    }

    fun onConfigFieldList(context: Context, updateTaskIdBoolean: Boolean, updateTaskId: String) {
        if (updateTaskIdBoolean){
            AppUtils.logDebug(TAG,"on APicall updateid"+updateTaskId)
            ApiClient.client.create(ApiInterFace::class.java)
                .configFieldList("2","10", updateTaskId).enqueue(this) }
        else{
            ApiClient.client.create(ApiInterFace::class.java)
                .configFieldList("2","10","").enqueue(this)
        }



    }

    override fun onResponse(
        call: Call<TaskFieldResponse>,
        response: Response<TaskFieldResponse>
    ) {
        progressbar?.visibility= View.GONE
        AppUtils.logDebug(TAG,"Response Createtaskviewmode"+Gson().toJson(response.body()?.data))
      val  configlistt= Gson().fromJson(Gson().toJson(response.body()?.data), ArrayList<ConfigFieldList>()::class.java)

        comConfigFieldList= Gson().fromJson(Gson().toJson(response.body()?.CommunityGroup),ArrayList<ComunityGroup>()::class.java)
         configlist.value= configlistt
 groupArray?.clear()
        groupArrayId?.clear()


        for (i in 0 until comConfigFieldList!!.size){
            val row: Any = comConfigFieldList!!.get(i)
            val rowmap: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
            val name = rowmap["name"].toString()
            val communitygroupid = rowmap["id"].toString()

            groupArray?.add( name)
            groupArrayId?.add(communitygroupid)
            AppUtils.logDebug(TAG,"array<String>"+ name)
        }



     }

    override fun onFailure(call: Call<TaskFieldResponse>, t: Throwable) {
        AppUtils.logError(TAG,"Failure -->"+t.message)

    }


}