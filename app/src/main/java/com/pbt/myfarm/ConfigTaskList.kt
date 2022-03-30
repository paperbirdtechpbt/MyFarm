package com.pbt.myfarm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigTaskList (
    @SerializedName("id")var id: String?=null,
    @SerializedName("name")var name: String?=null,
    @SerializedName("name_prefix")var name_prefix: String?=null,
    @SerializedName("description")  val description: String?=null,
        ):Parcelable