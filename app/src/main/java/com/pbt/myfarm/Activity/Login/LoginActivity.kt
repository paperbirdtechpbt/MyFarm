package com.pbt.myfarm.Activity.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.LoginViewModel

import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_USERNAME
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


        val db=DbHelper(this, null)
        db.addUser("name","password")
        setSpinner()

        btn_login.setOnClickListener{
            if (ed_email.text.toString().equals("pbt@admin")&& ed_password.text.toString().equals("pbt@admin")){
                startActivity(Intent(this,MainActivity::class.java))
                MySharedPreference.setStringValue(this,CONST_SHARED_PREF_USERNAME,ed_email.text.toString())
                finish()
            }
            else{
                Toast.makeText(this,"Invalid User",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(this, R.array.role_array, android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_role.adapter = adapter
        }
    }
}