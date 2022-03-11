package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class PackFieldList (
    @SerializedName("id")var id:String,
    @SerializedName("name")var name:String,
    var field_packid: String ="",
    var field_config_id:String="",
    var field_id:String="",
)
