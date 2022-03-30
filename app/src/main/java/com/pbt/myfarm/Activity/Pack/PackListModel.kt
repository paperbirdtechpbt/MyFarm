package com.pbt.myfarm.Activity.Pack

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.PackList
import com.pbt.myfarm.PacksNew


data class PackListModel(
    @SerializedName("data")  val data: List<PacksNew>,
    val error: Boolean
)