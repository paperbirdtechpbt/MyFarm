package com.pbt.myfarm

import androidx.constraintlayout.widget.ConstraintLayout

data class CollectDataFieldListItem(
    val collect_activity: String,
    val datetime_collected: String,
    val duration: Int,
    val id: Int,
    val result_name: String,
    val sensor: String,
    val unit_value: String,
    val user_collecting: String,
    val value: String,
    var expand : Boolean = false,
    var layoutnonexpand:ConstraintLayout,
    var layoutexpand:ConstraintLayout,

)