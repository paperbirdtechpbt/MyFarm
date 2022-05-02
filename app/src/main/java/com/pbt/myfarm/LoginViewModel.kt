package com.pbt.myfarm

import android.app.Application
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.HttpResponse.loginResult
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_NAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(val activity: Application): AndroidViewModel(activity),
    Callback<HttpResponse> {
    companion object{
        const val TAG : String = "LoginViewModel"
    }

    val context = activity
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var userLogin: MutableLiveData<HttpResponse>? = null
    lateinit var progressBar:ProgressBar
    lateinit var btnlogin:Button
     var rolesIdString:String?=null
     var rolesName:String?=null
    init {
        email= ObservableField("")
        password= ObservableField("")
        userLogin = MutableLiveData<HttpResponse>()
    }
    fun login(view: View){

AppUtils.logDebug(TAG,"RoleidString=="+rolesIdString.toString())
        progressBar.visibility=View.VISIBLE
        btnlogin.visibility=View.GONE

       if ( rolesIdString!="0"){
           ApiClient.client.create(ApiInterFace::class.java).login(
               email = email?.get()!!, password = password?.get()!!
           ).enqueue(this)
       }
        else{
            Toast.makeText(context,"Please Select Role ",Toast.LENGTH_SHORT).show()
           btnlogin.visibility=View.VISIBLE
           progressBar.visibility=View.GONE


       }


    }

    override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
        if (response.body()?.error==false){
            progressBar.visibility=View.GONE
            btnlogin.visibility=View.VISIBLE
            userLogin?.value = response.body()
            MySharedPreference.setStringValue(context, CONST_PREF_ROLE_ID, rolesIdString)
            MySharedPreference.setStringValue(context, CONST_PREF_ROLE_NAME, rolesName)

        }
        else{
            Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show()

            progressBar.visibility=View.GONE
            btnlogin.visibility=View.VISIBLE
        }

//        val list: loginResult = Gson().fromJson(Gson().toJson(response.body()?.data), loginResult::class.java)
    }

    override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
        progressBar.visibility=View.GONE
        btnlogin.visibility=View.VISIBLE
        Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show()
        AppUtils.logError(TAG,  "Failure Response : " + t.message)

    }


}