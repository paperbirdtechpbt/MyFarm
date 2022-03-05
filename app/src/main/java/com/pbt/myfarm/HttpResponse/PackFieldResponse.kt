package com.pbt.myfarm.HttpResponse

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class PackFieldResponse(
    @SerializedName("CommunityGroup") val CommunityGroup:  List<PackCommunityList>,
    @SerializedName("data")   val data: List<PackConfigFieldList>,
    @SerializedName("error")  val error: Boolean,
    @SerializedName("packconfig")  val packconfig: Packconfig,
)
