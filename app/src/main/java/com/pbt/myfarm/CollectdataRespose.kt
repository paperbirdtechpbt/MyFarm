package com.pbt.myfarm

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class CollectdataRespose(
    @SerializedName("data")   val data: List<CollectData>,
//    @SerializedName("data")   val data: List<CollectDataFieldListItem>,
    @SerializedName("error")  val error: Boolean,
    @SerializedName("roles")  val roles: List<RolesAdmin>
)
data class RolesAdmin(
    @SerializedName("id")  val id: String,
    @SerializedName("name")  val name:String
)
