package com.pbt.myfarm

import android.app.Application
import android.util.Log

class MyFarmApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Log.d("Application","Oncreate Call Application")
    }
}