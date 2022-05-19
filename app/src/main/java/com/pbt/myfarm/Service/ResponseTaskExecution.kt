package com.pbt.myfarm.Service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ResponseTaskExecution (
    @SerializedName("error")var error:Boolean,
//    @SerializedName("data")var data:JsonArray,
    @SerializedName("msg")var msg:String,
   )
