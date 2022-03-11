package com.pbt.myfarm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PackList(
//    var type:String,
//    var labeldesciption:String,
//    val com_group: String,
//    val created_by: String,
//    val description: String,
//    val id: String,
//    val name: String,
//    val name_prefix: String,
//    val pack_config_id: String,
//    val pack_config_name: String,
//    var padzero:String="",

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
):Parcelable