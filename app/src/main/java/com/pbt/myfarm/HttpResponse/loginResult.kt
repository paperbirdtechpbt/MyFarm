package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class loginResult (
    @SerializedName("id")  val id: Int,
    @SerializedName("name")  val name: String,
    @SerializedName("email") val email:String,
    @SerializedName("family_name") val family_name:String,
    @SerializedName("external_id") val external_id:String,
    @SerializedName("is_active") val is_active:Boolean,
    @SerializedName("collect_activity_id") val collect_activity_id:String,
    @SerializedName("community_group_id") val community_group_id:String,
    @SerializedName("communitygroup") val communitygroup:String,
    @SerializedName("communitygroup_pass") val communitygroup_pass:String,
    @SerializedName("language") val language:Int,
    @SerializedName("timezone") val timezone:String,
    )
