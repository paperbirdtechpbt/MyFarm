package com.pbt.myfarm.Activity.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.Login.LoginActivity

import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_USERNAME
import com.pbt.myfarm.Util.MySharedPreference


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val isLoginNotEmpty=MySharedPreference.getStringValue(this,CONST_SHARED_PREF_USERNAME,"")

        Log.d("SplashActivity","$isLoginNotEmpty : ${isLoginNotEmpty.isNullOrEmpty()}")
//
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLoginNotEmpty.isNullOrEmpty()){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }, 2000)
    }
}