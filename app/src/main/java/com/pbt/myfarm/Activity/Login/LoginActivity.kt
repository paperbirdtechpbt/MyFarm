package com.pbt.myfarm.Activity.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.LoginViewModel
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_IS_LOGIN
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_ROLEID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_USERNAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityLogin2Binding
import kotlinx.android.synthetic.main.activity_login2.*
import org.koin.android.ext.android.inject


class LoginActivity : AppCompatActivity(), LoginListner {

    val  viewModel by inject<LoginViewModel>()
    lateinit var binding: ActivityLogin2Binding
    var selectedroleId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this



        viewModel.loginListener = this
        viewModel.btnlogin = btn_login
        viewModel.spinnerRole = spinner_role

        initObservable()


        ed_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                AppUtils.logDebug("##LoginActvity", editable.toString())
                viewModel.callApiGetRoleList(this@LoginActivity, editable.toString())

            }
        })
    }


    private fun initObservable() {
        viewModel.context = this




        viewModel.userLogin?.observe(this, Observer { response ->
            if (response?.error == false) {

                viewModel.isProgressbar.postValue(View.VISIBLE)

                MySharedPreference.setStringValue(
                    this,
                    CONST_SHARED_PREF_USERNAME,
                    Gson().toJson(response.data)
                )

                MySharedPreference.setStringValue(this, CONST_PREF_IS_LOGIN, "true")

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(CONST_ROLEID, selectedroleId)
                startActivity(intent)
                finish()

                Toast.makeText(this, "${response.message}", Toast.LENGTH_LONG).show()
            } else{
                viewModel.isProgressbar.postValue(View.GONE)
                Toast.makeText(this, "${response.message}", Toast.LENGTH_LONG).show()

            }
        })
    }


    override fun showPassword(isShow: Boolean) {
        if (isShow == true)
            binding.edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
        else
            binding.edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance())
    }


}