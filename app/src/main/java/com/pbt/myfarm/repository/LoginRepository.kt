package com.pbt.myfarm.repository

import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace

class LoginRepository(private val apiService:ApiInterFace) {
    suspend fun doLogin(email:String, password:String)=apiService.login(email,password)
}