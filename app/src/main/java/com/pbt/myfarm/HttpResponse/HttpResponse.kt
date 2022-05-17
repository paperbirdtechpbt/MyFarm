package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class HttpResponse (
    @SerializedName("error")  val error: Boolean,
    @SerializedName("msg")  val message: String,
    @SerializedName("data") val data: Map<String?, Object>
    )

