package com.pbt.myfarm

import android.app.Application
import android.view.View
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
    init {
        email= ObservableField("")
        password= ObservableField("")
        userLogin = MutableLiveData<HttpResponse>()
    }
    fun login(view: View){
        ApiClient.client.create(ApiInterFace::class.java).login(
            email = email?.get()!!, password = password?.get()!!
        ).enqueue(this)

    }

    override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
        userLogin?.value = response.body()
        val list: loginResult = Gson().fromJson(response.body()?.data.toString(), loginResult::class.java)
        AppUtils.logDebug(TAG, "Success Response : " + Gson().toJson(response.body()))
    }

    override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
        AppUtils.logError(TAG,  "Failure Response : " + t.message)

    }


}