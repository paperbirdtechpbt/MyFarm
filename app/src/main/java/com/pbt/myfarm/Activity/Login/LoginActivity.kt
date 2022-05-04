package com.pbt.myfarm.Activity.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson

import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.CollectdataRespose

import com.pbt.myfarm.LoginViewModel

import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_IS_LOGIN
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_ROLEID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_USERNAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityLogin2Binding
import kotlinx.android.synthetic.main.activity_login2.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class LoginActivity : AppCompatActivity(), retrofit2.Callback<CollectdataRespose> {

    var viewModel: LoginViewModel? = null
    var binding: ActivityLogin2Binding? = null
    var roleList = ArrayList<String>()
    var roleListId = ArrayList<String>()
    var selectedroleId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2)

        supportActionBar?.hide()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LoginViewModel::class.java)

        binding?.loginmodel = viewModel
        viewModel?.progressBar = progressLogin
        viewModel?.btnlogin = btn_login

        initObservable()

//        setSpinner()

        ed_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


            }

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {


            }

            override fun afterTextChanged(editable: Editable) {
                AppUtils.logDebug("##LoginActvity", editable.toString())

                ApiClient.client.create(ApiInterFace::class.java).getRoleList(editable.toString())
                    .enqueue(this@LoginActivity)

            }
        })

        setSpinnerListner()

    }

    private fun setSpinnerListner() {
        spinner_role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
//                for (i in 0 until  roleListId.size ){
//                    if (position.toString()==roleListId.get(i)){
//                        selectedroleId=roleListId.get(i)
                viewModel?.rolesIdString = roleListId.get(position)
                viewModel?.rolesName = roleList.get(position)
                AppUtils.logDebug("####LoginActivity", roleListId.get(position))

//                    }
//                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }


    private fun initObservable() {

        viewModel?.userLogin?.observe(this, Observer { response ->
            if (response?.error == false) {

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
            } else
                Toast.makeText(this, "${response.message}", Toast.LENGTH_LONG).show()
        })
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            this, R.array.role_array, android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_role.adapter = adapter
        }
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//      spinner_role.adapter=adapter
    }

    override fun onResponse(
        call: Call<CollectdataRespose>,
        response: Response<CollectdataRespose>
    ) {
        AppUtils.logDebug("##LoginActivity", response.body().toString())
        try {
            if (response.body()!!.error == false) {
                val baseresponse: CollectdataRespose =
                    Gson().fromJson(Gson().toJson(response.body()), CollectdataRespose::class.java)
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

                val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList)
                dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_role.setAdapter(dd)
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


}