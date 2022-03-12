package com.pbt.myfarm.Activity.Pack

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewPackModelClass(

    var id: String,
    var name: String,
    var name_prefix: String?,
    var pack_config_id: String?,
    var pack_config_name: String,
    var type:String,
    var labeldesciption:String,
    var description: String,
    var com_group: String,
    var created_by: String,
    var padzero:String="",
) : Parcelable
