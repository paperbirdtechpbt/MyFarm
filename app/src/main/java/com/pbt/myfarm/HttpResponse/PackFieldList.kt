package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class PackFieldList (
    @SerializedName("id")var id:String?=null,
    @SerializedName("name")var name:String?=null,
    var field_packid: String ?=null,
    var field_config_id:String?=null,
    var field_id:String?=null,
)
