package com.pbt.myfarm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PackList(

    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val description: String,
    val id: String,
    val is_active: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: String,
    val name_prefix: String,
    val pack_config_id: Int,
    val pack_config_name: String,
    var padzero:String="",
    var expand : Boolean = false
):Parcelable