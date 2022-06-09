package com.pbt.myfarm

import com.google.gson.annotations.SerializedName

data class ListNotificationData(
    @SerializedName("message")  val message: String,
    @SerializedName("formatted_dob")  val formatted_dob: String,
)
