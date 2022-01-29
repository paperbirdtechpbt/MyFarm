package com.pbt.myfarm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ModelClass.ViewTaskModelClass


class CreatetaskViewModel(var activity: Application) : AndroidViewModel(activity) {
    companion object {
        const val TAG: String = "CreateTaskViewModel"
    }
    val context:Context=activity


    var namePrefix: ObservableField<String>? = null
    var confiType: ObservableField<String>? = null
    var desciption: ObservableField<String>? = null
    var expectedStartDate: ObservableField<String>? = null
    var expectedEndDate: ObservableField<String>? = null
    var startDate: ObservableField<String>? = null
    var EndDate: ObservableField<String>? = null

    init {
        namePrefix = ObservableField("")
        confiType = ObservableField("")
        desciption = ObservableField("")
        expectedStartDate = ObservableField("")
        expectedEndDate = ObservableField("")
        startDate = ObservableField("")
        EndDate = ObservableField("")
    }

    fun login(view: View) {
        val db = DbHelper(activity, null)
        val newTask=ViewTaskModelClass(ENTRYNAME = namePrefix?.get().toString(),
        ENTRYTYPE = confiType?.get().toString(),ENTRYDETAIL =  desciption?.get().toString(),
        ExpectedStartDate = expectedStartDate?.get().toString(),
        ExpectedEndDate =expectedEndDate?.get().toString(),
        StartDate = startDate?.get().toString(), EndDate = EndDate?.get().toString() )
        db.addTask(
            "pbt",newTask
        )



    }

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


}