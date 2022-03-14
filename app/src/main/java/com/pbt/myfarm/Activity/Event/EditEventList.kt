package com.pbt.myfarm.Activity.Event

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.*

data class EditEventList(
    val `data`: List<Data>,
    val error: Boolean
)
data class ResposneUpdateEvent(
    @SerializedName("error")  val error: Boolean,
    @SerializedName("msg")  val msg: String)

data class ResponseOfflineSync(
    @SerializedName("error")  val error: Boolean,
    @SerializedName("msg")  val msg: Objects)