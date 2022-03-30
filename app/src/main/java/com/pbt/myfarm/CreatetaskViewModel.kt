package com.pbt.myfarm

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.HttpResponse.ComunityGroup
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.HttpResponse.TaskFieldResponse
import com.pbt.myfarm.Service.*
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
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

//    fun update() {
//        val db= DbHelper(context,null)
//        val newTask=ViewTaskModelClass(ENTRYNAME = namePrefix?.get().toString(),
//            ENTRYTYPE = confiType?.get().toString(),ENTRYDETAIL =  desciption?.get().toString(),
//            ExpectedStartDate = expectedStartDate?.get().toString(),
//            ExpectedEndDate =expectedEndDate?.get().toString(),
//            StartDate = startDate?.get().toString(), EndDate = EndDate?.get().toString() )
//      val result=  db.updateTask(newTask,newTask.ENTRYNAME)
//        if (result >= 0) {
//            Toast.makeText(context, "Update Sucessfully", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, ViewTaskActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//
//            Activity().finish()
//
//
//        } else {
//            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show()
//
//        }
//
//    }

    fun onConfigFieldList(
        context: Context,
        updateTaskIdBoolean: Boolean,

        updateTaskList: TasklistDataModel?
    ) {

            ApiClient.client.create(ApiInterFace::class.java)
                .configFieldList(MySharedPreference.getUser(context)?.id.toString(),updateTaskList?.task_config_id.toString(),
                updateTaskList?.id.toString()).enqueue(this)


    }

    fun onConfigFieldListFalse(
        context: Context,

        configId: ConfigTaskList?,

    ) {

            AppUtils.logError(TAG,"false configidd"+configId?.id.toString())
            ApiClient.client.create(ApiInterFace::class.java)
                .configFieldList(MySharedPreference.getUser(context)?.id.toString(),
                    configId?.id.toString(),"").enqueue(this)


    }

    override fun onResponse(
        call: Call<TaskFieldResponse>,
        response: Response<TaskFieldResponse>
    ) {
        try{
            if (response.body()?.error==false){
                progressbar?.visibility= View.GONE
                val  configlistt= Gson().fromJson(Gson().toJson(response.body()?.data), ArrayList<ConfigFieldList>()::class.java)
                if (!configlistt.isNullOrEmpty()){
                    for (i in 0 until configlistt.size){
                        ExpAmtArray.add("0")
                        ExpName.add("0")
                        ExpNameKey.add("f_id")
                        ExpAmtArrayKey.add("f_value")

                    }
                    AppUtils.logDebug(TAG,"arrayid"+ CreatePackActivity.arrayID +"\n"+ CreatePackActivity.arrayName)

                }
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
                }
            }
            else{
                println("error true")
            }
        }
        catch (e:Exception){
        AppUtils.logError(TAG,e.toString())
        }
     }

    override fun onFailure(call: Call<TaskFieldResponse>, t: Throwable) {
        try {
            AppUtils.logError(TAG,"Failure -->"+t.message)
        }
        catch (e:Exception){
            AppUtils.logError(TAG,"Failure -->"+e.message)
        }

    }


}