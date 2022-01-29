package com.pbt.myfarm.Activity.Pack

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewPackModelClass (var packname:String,var packType:String,
var packdesciption:String,var communitygrip:String,var customer:String,var quantitiy:String,
var units:String):Parcelable
