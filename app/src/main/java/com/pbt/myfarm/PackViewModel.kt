package com.pbt.myfarm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.DataBase.DbHelper
import java.security.AccessController.getContext

class PackViewModel(val activity:Application) :AndroidViewModel(activity){
    companion object {
        const val TAG: String = "CreateTaskViewModel"
    }
    val context: Context =activity


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



}