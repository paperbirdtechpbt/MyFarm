package com.pbt.myfarm.Activity.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.LoginViewModel

import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_IS_LOGIN
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_USERNAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityLogin2Binding
import kotlinx.android.synthetic.main.activity_login2.*

class LoginActivity : AppCompatActivity() {
    var viewModel: LoginViewModel? = null
var binding:ActivityLogin2Binding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2)

        supportActionBar?.hide()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LoginViewModel::class.java)

       binding?.loginmodel=viewModel

initObservable()

        setSpinner()


    }

    private fun initObservable() {
        viewModel?.userLogin?.observe(this, Observer { response ->
            if (response?.error == false) {
                MySharedPreference.setStringValue(this,CONST_SHARED_PREF_USERNAME,ed_email.text.toString())
                MySharedPreference.setStringValue(this,CONST_PREF_IS_LOGIN,"true")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

                AppUtils.logDebug("LoginActivity","Login api Respose ====>> "+ Gson().toJson(response.data))
             Toast.makeText(this,"${response.message}",Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this,"${response.message}",Toast.LENGTH_LONG).show()
        })
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(this, R.array.role_array, android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_role.adapter = adapter
        }
    }
}