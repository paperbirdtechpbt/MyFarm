package com.pbt.myfarm.Activity.Event

import com.google.gson.annotations.SerializedName

data class EditEventList(
    val `data`: List<Data>,
    val error: Boolean
)
data class ResposneUpdateEvent(
    @SerializedName("error")  val error: Boolean,
    @SerializedName("msg")  val msg: String,


    )