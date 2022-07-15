package com.pbt.myfarm.Activity.TaskFunctions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseTaskFunctionaliyt(
    @SerializedName("error") var error: Boolean,
    @SerializedName("data") var data: ArrayList<ListTaskFunctions>,
    @SerializedName("msg") var msg: String,
    @SerializedName("Task Function") var TaskFunction: ArrayList<FieldListTaskFunctions>,
    @SerializedName("Function Field list") var Function: ArrayList<ListFunctionFieldlist>,
    @SerializedName("MediaFile") var MediaFile: ArrayList<ListMediaFile>,
    @SerializedName("media_types") var media_types: ArrayList<Listmedia_types>,
)


data class HttpResponse (
    @SerializedName("error") var error:Boolean,
    @SerializedName("data") private  var data: Map<String?, Any?>,
    @SerializedName("msg") var msg:String
    )
data class Listmedia_types (
    @SerializedName("id") var id:Int,
    @SerializedName("name")   var name: String,
    )

data class ListTaskFunctions (
    @SerializedName("id") var id:String?="",
    @SerializedName("name") var name:String?="",
    @SerializedName("name1") var name1:String?="",
    @SerializedName("privilegename") var privilegeName:String?="",
    var object_class:String?="",
    var ordinateTitle:String?="",
)



data class FieldListTaskFunctions (
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
    @SerializedName("link") var link:String,
        )
@Parcelize
data class ListFunctionFieldlist (
    @SerializedName("id") var id:String?=null,
    @SerializedName("name") var name:String?=null,
    @SerializedName("link") var link:String?=null,
  var localPath:String?=null,
        ):Parcelable

data class ListMediaFile(
    @SerializedName("id") var id: String,
    @SerializedName("task_id") var task_id: String,
    @SerializedName("name") var name: String,
)

