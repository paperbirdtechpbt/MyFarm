package com.pbt.myfarm

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.*
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Login.LoginListner
import com.pbt.myfarm.HttpResponse.HttpResponse
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class LoginViewModel constructor(
    private val repository: LoginRepository,
) : ViewModel(){

    companion object {
        const val TAG: String = "LoginViewModel"
    }
    var context:Context?=null
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var userLogin: MutableLiveData<HttpResponse>? = null
     var isProgressbar=MutableLiveData(View.GONE)
    lateinit var btnlogin: Button
    var rolesIdString:String ?= null
    var rolesName:String?= null
    var loginListener: LoginListner? = null
    var isPasswordShow: ObservableBoolean? = null
    var spinnerRole: Spinner? = null
    var roleList = ArrayList<String>()
    var roleListId = ArrayList<String>()

    init {
        email = ObservableField("")
        password = ObservableField("")
        userLogin = MutableLiveData<HttpResponse>()
        isPasswordShow = ObservableBoolean(false)
    }

    fun login(view: View) {

        AppUtils.logDebug(TAG, "RoleidString==" + rolesIdString.toString())
isProgressbar.postValue(View.VISIBLE)
        btnlogin.visibility = View.GONE

        if (rolesIdString != "0") {
            viewModelScope.launch {
                repository.doLogin(email?.get().toString(), password?.get().toString()).let {
                    MySharedPreference.setStringValue(context as Activity,
                        AppConstant.CONST_PREF_ROLE_ID, rolesIdString)
                    MySharedPreference.setStringValue(context as Activity,
                        AppConstant.CONST_PREF_ROLE_NAME, rolesName)
                    userLogin?.postValue(it)
                }
            }
//            ApiClient.client.create(ApiInterFace::class.java).login(
//                email = email?.get()!!, password = password?.get()!!
//            ).enqueue(this)
        } else {
            Toast.makeText(context, "Please Select Role ", Toast.LENGTH_SHORT).show()
            btnlogin.visibility = View.VISIBLE
            isProgressbar.postValue(View.GONE)


        }


    }

    fun showHidePassword(view: View) {
        if (isPasswordShow?.get() == false) {

            isPasswordShow?.set(true)
        } else {
            isPasswordShow?.set(false)
        }

        isPasswordShow?.get()?.let { loginListener!!.showPassword(it) }
    }


//    override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
//        if (response.body()?.error == false) {
//            progressBar.visibility = View.GONE
//            btnlogin.visibility = View.VISIBLE
//            userLogin?.value = response.body()
//
//
//        } else {
//            Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show()
//
//            progressBar.visibility = View.GONE
//            btnlogin.visibility = View.VISIBLE
//        }
//
////        val list: loginResult = Gson().fromJson(Gson().toJson(response.body()?.data), loginResult::class.java)
//    }
//
//    override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
//        progressBar.visibility = View.GONE
//        btnlogin.visibility = View.VISIBLE
//        Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show()
//        AppUtils.logError(TAG, "Failure Response : " + t.message)
//
//    }

    fun callApiGetRoleList(context: Context, email: String) {
        val apiclient = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiclient.getRoleList(email)
        call.enqueue(object : Callback<CollectdataRespose> {
            override fun onResponse(
                call: Call<CollectdataRespose>,
                response: Response<CollectdataRespose>
            ) {
                AppUtils.logDebug("##LoginActivity", response.body().toString())
                try {
                    if (response.body()!!.error == false) {
                        val baseresponse: CollectdataRespose =
                            Gson().fromJson(
                                Gson().toJson(response.body()),
                                CollectdataRespose::class.java
                            )
                        val list = baseresponse.roles

                        roleList.clear()
                        roleListId.clear()
                        roleList.add("Select Role")
                        roleListId.add("0")

                        for (i in 0 until list.size) {

                            val item = baseresponse.roles.get(i).name
                            val id = baseresponse.roles.get(i).id
                            roleList.add(item)
                            roleListId.add(id)

                        }

                        val dd = ArrayAdapter(context, R.layout.simple_spinner_item, roleList)
                        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerRole?.setAdapter(dd)
                        setListner(spinnerRole)
                    }

                } catch (e: Exception) {
                    println(e.message.toString())
                }

            }

            override fun onFailure(call: Call<CollectdataRespose>, t: Throwable) {
                try {
                    println(t.message.toString())

                } catch (e: Exception) {
                    println(e.message.toString())

                }
            }
        })


    }

    private fun setListner(spinnerRole: Spinner?) {
        spinnerRole?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                rolesIdString=roleListId.get(position)
                rolesName=roleList.get(position)

                AppUtils.logDebug("####LoginActivity", roleListId.get(position))


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }


}