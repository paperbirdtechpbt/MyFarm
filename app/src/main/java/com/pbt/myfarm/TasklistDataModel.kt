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