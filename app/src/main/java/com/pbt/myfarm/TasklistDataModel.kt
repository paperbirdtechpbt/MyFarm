package com.pbt.myfarm

import android.os.Parcelable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TasklistDataModel(

    @SerializedName("id")  val id: String?=null,
    @SerializedName("name")  val name: String?=null,
    @SerializedName("description")  val description: String?=null,
    @SerializedName("name_prefix")  var name_prefix: String?=null,
    @SerializedName("task_config_name")  val task_config_name: String?=null,
    @SerializedName("task_config_id")  val task_config_id: String?=null,

    var deleteicon:Int?=null,
    var editicon:Int?=null,
    var padzero:String?=null,
    var expand : Boolean = false,
    var type:String?=null,
    var labeldesciption:String?=null,

): Parcelable



data class TasknewOffline(
     val name: String,
     val desciption: String,
    val task_config_id: String,
  val task_func: String,
    var com_group:String,
    var status:String,
    var startDate:String="",
    var EndDate:String="",
    var created_By:String="",
    var created_at:String="",
    var lastchanged_by : String,
    var last_changed_date : String,
    var deleted_at :String
    )