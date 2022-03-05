package com.pbt.myfarm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PackConfigList(
    @SerializedName("id")var id:String,
    @SerializedName("name")var name:String
): Parcelable
