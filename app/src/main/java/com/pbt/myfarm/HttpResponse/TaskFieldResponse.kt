package com.pbt.myfarm.HttpResponse

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class TaskFieldResponse(
    @SerializedName("CommunityGroup") val CommunityGroup: JsonArray,
    @SerializedName("data")   val data: JsonArray,
    @SerializedName("error")  val error: Boolean
)

//data class CommunityGroup(
//    val community_group: String,
//    val created_at: String,
//    val created_by: Int,
//    val deleted_at: Any,
//    val deleted_by: Any,
//    val description: String,
//    val id: Int,
//    val name: String,
//    val updated_at: String,
//    val updated_by: Int
//)

//data class Data(
//    val editable: Int,
//    val field_description: String,
//    val field_id: String,
//    val field_list: List<Field>,
//    val field_name: String,
//    val field_type: String,
//    val field_value: Any
//)

//data class Field(
//    val id: Int,
//    val name: String
//)