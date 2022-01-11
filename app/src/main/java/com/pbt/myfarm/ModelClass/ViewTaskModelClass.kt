package com.pbt.myfarm.ModelClass

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewTaskModelClass(
    var ENTRYNAME: String,
    var ENTRYTYPE: String,
    var ENTRYDETAIL: String,
    var ExpectedStartDate: String,
    var ExpectedEndDate: String,
    var StartDate: String,
    var EndDate: String
):Parcelable