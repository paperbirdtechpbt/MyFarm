package com.pbt.myfarm.Service

import com.google.gson.annotations.SerializedName

data class FieldList (
    @SerializedName("id")var id:String,
    @SerializedName("name")var name:String,)
