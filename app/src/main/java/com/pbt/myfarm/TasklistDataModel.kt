package com.pbt.myfarm

import android.os.Parcelable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TasklistDataModel(
    @SerializedName("id")  val id: String,
    @SerializedName("name")  val name: String,
    @SerializedName("description")  val description: String,
    @SerializedName("name_prefix")  val name_prefix: String,
    @SerializedName("task_config_name")  val task_config_name: String,
    var deleteicon:Int,
    var editicon:Int,
    var padzero:String="",
    var expand : Boolean = false

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