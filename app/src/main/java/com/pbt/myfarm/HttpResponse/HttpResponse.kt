package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class HttpResponse(
    @Json(name = "error")
    val error: Boolean,
    @Json(name = "msg")
    val message: String,
    @Json(name = "data")
    val data: Map<String?, Object>,
    @Json(name = "status")
    val status: String? = null
)

