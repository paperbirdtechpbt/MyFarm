package com.pbt.myfarm

import com.google.gson.annotations.SerializedName

data class AllPriviledgeListResponse (
    @SerializedName("error")val error:Boolean,
    @SerializedName("privilage")val privilage:List<ListPrivilege>,)

data class ListPrivilege (
    @SerializedName("id")val id:String,
    @SerializedName("privilege")val privilege:String,)