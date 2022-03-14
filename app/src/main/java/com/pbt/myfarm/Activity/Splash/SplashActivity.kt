package com.pbt.myfarm.Activity.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.pbt.myfarm.Activity.Home.MainActivity

import com.pbt.myfarm.Activity.Login.LoginActivity

import com.pbt.myfarm.R
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_USERNAME
import com.pbt.myfarm.Util.MySharedPreference


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val checkuser=MySharedPreference.getStringValue(this,CONST_SHARED_PREF_USERNAME,null)

        startService(Intent(this,MyFarmService::class.java))

        Handler(Looper.getMainLooper()).postDelayed({
            if (checkuser==null){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }, 3000)
    }
}