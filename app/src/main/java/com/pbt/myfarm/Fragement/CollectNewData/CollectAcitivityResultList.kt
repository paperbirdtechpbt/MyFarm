package com.pbt.myfarm.Fragement.CollectNewData

import com.google.gson.annotations.SerializedName

data class CollectAcitivityResultList (
    @SerializedName("id")var id:String?=null,
    @SerializedName("result_name")var result_name:String?=null,
    var typeid:String?=null,
    var unitId:String?=null,
    var listId:String?=null,
        )
