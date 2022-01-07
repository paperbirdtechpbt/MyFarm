package com.pbt.myfarm

import android.app.Application
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.pbt.myfarm.Activity.Home.MainActivity


class LoginViewModel(val activity: Application): AndroidViewModel(activity){
    companion object{
        const val TAG : String = "LoginViewModel"
    }

    val context = activity
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    init {
        email= ObservableField("")
        password= ObservableField("")
    }
//    fun login(view: View){
//        if (email?.get().equals("pbt@admin") && password?.get().equals("pbt@admin")){
//
//            val i = Intent(context, MainActivity::class.java)
//            context.startActivity(i)
//
//
//        }
//        else{
//            Toast.makeText(context,"Invalid User",Toast.LENGTH_LONG).show()
//        }
//
//
//        Toast.makeText(context,"Login Button Click", Toast.LENGTH_LONG
//        ).show()
//    }


}