package com.pbt.myfarm.Activity.Pack

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.PackList


data class PackListModel(
    @SerializedName("data")  val data: List<PackList>,
    val error: Boolean
)