package com.pbt.myfarm

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class AllPriviledgeListResponse (
    @Json(name = "error")
    val error:Boolean,
    @Json(name = "privilage")
    val privilage:List<ListPrivilege>

)
data class ListPrivilege (
    @Json(name = "id")
    val id:String?=null,
    @Json(name = "privilege")
  val privilege:String?=null)