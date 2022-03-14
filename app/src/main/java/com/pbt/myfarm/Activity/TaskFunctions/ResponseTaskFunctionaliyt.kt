package com.pbt.myfarm.Activity.TaskFunctions

import com.google.gson.annotations.SerializedName

data class ResponseTaskFunctionaliyt (
    @SerializedName("error") var error:Boolean,
    @SerializedName("data") var data:ArrayList<ListTaskFunctions>,
    @SerializedName("msg") var msg:String,
    @SerializedName("Task Function") var TaskFunction:ArrayList<FieldListTaskFunctions>,
    @SerializedName("Function Field list") var Function:ArrayList<ListFunctionFieldlist>,
    @SerializedName("MediaFile") var MediaFile:ArrayList<ListMediaFile>, )


data class ListTaskFunctions (
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
        )

data class FieldListTaskFunctions (
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
    @SerializedName("link") var link:String,
        )
data class ListFunctionFieldlist (
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
    @SerializedName("link") var link:String,
        )

data class ListMediaFile (
    @SerializedName("id") var id:String,
    @SerializedName("task_id") var task_id:String,
    @SerializedName("name") var name:String, )

