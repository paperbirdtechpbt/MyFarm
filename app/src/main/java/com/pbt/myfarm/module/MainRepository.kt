package com.pbt.myfarm.module

import com.pbt.myfarm.Service.ApiInterFace

class MainRepository(val apiInterFace: ApiInterFace) {
    suspend fun callPrivilegeApi(roleId:String)=apiInterFace.getAllprivileges(roleId)
}
