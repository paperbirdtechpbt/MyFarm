package com.pbt.myfarm.Activity.Pack

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewPackModelClass(

    val id: String,
    val name: String,
    val name_prefix: String,
    val pack_config_id: String,
    val pack_config_name: String,
    var type:String,
    var labeldesciption:String,
    val description: String,
    val com_group: String,
    val created_by: String,
    var padzero:String="",

) : Parcelable
