package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class PackCommunityList (
    @SerializedName("id")var id:String,
    @SerializedName("name")var name:String,
    @SerializedName("community_group")var community_group:String,
        )
