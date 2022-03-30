package com.pbt.myfarm.Activity.PackConfigList

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.ConfigTaskList
import com.pbt.myfarm.PackConfig
import com.pbt.myfarm.PackConfigList
import kotlinx.android.parcel.Parcelize

data class PackConfigResponse(
    @SerializedName("data")  val data: List<PackConfig>,
    @SerializedName("error")   val error: Boolean
)
