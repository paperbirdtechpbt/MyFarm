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
import com.pbt.myfarm.Util.AppUtils
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
    init {
        email= ObservableField("")
        password= ObservableField("")
        userLogin = MutableLiveData<HttpResponse>()
    }
    fun login(view: View){
        progressBar.visibility=View.VISIBLE
        btnlogin.visibility=View.GONE
        ApiClient.client.create(ApiInterFace::class.java).login(
            email = email?.get()!!, password = password?.get()!!
        ).enqueue(this)

    }

    override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
        progressBar.visibility=View.VISIBLE
        btnlogin.visibility=View.GONE
        userLogin?.value = response.body()
        val list: loginResult = Gson().fromJson(response.body()?.data.toString(), loginResult::class.java)
    }

    override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
        progressBar.visibility=View.GONE
        btnlogin.visibility=View.VISIBLE
        Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show()
        AppUtils.logError(TAG,  "Failure Response : " + t.message)

    }


}