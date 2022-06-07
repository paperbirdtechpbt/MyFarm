package com.pbt.myfarm.HttpResponse

import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TaskFieldResponse(
    @SerializedName("CommunityGroup") val CommunityGroup: JsonArray,
    @SerializedName("data")   val data: JsonArray,
    @SerializedName("error")  val error: Boolean,
    @SerializedName("users")  val users: JsonArray
)


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