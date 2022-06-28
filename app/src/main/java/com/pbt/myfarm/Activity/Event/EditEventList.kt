package com.pbt.myfarm.Activity.Event

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.ListNotificationData
import org.json.JSONObject
import java.util.*

data class EditEventList(
    val `data`: List<Data>,
    val error: Boolean
)
data class ResposneUpdateEvent(
    @SerializedName("error")  val error: Boolean,
    @SerializedName("msg")  val msg: String)

data class ResponseScanCodeForTaskFunction(
    @SerializedName("error")  val error: Boolean,
    @SerializedName("msg")  val msg: String,
    @SerializedName("data")  val data: ScannedPersonData,
    )
data class ScannedPersonData(
    @SerializedName("id")  val id: Int,
    @SerializedName("name")  val name: String,
    )
data class ResposneNotificationCount(
    @SerializedName("count")  val count: Int,
    @SerializedName("data")  val data: List<ListNotificationData>,
)
data class ResposneMarkerPoints(
    @SerializedName("data")  val data: List<ListOFMarkerPoints>,
)
data class ListOFMarkerPoints(
    @SerializedName("name") val name: String,
    @SerializedName("latitude")val lat:String,
    @SerializedName("longitude")val lng:String,
    @SerializedName("field_class")val field_class:String,
    @SerializedName("filedName")val filedName:String,
    @SerializedName("icon")val icon:String
)

data class ResponsefieldClasses(
    @SerializedName("data") val data: List<ListofFieldClasses>,

) {
   data class ListofFieldClasses (
       @SerializedName("id") val id: String,
       @SerializedName("name")val name:String,
           )
}
//data class ListNotificationData(
//    @SerializedName("message")  val message: String,
//    @SerializedName("formatted_dob")  val formatted_dob: String,
//)