package com.pbt.myfarm.Activity.TaskFunctions

import com.google.gson.annotations.SerializedName

data class ResponseTaskFunctionaliyt (
    @SerializedName("error") var error:Boolean,
    @SerializedName("data") var data:ArrayList<ListTaskFunctions>,
        )

data class ListTaskFunctions (
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
        )

