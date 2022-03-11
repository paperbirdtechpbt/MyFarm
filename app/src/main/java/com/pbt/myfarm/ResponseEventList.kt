package com.pbt.myfarm

import com.google.gson.annotations.SerializedName

data class ResponseEventList(
    @SerializedName("events") var events: List<EventList>,
    @SerializedName("today_date") var today_date: String,
    @SerializedName("error") var error:Boolean,
    @SerializedName("msg") var msg: String,
)

data class EventList(

    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("exp_start_date") var exp_start_date: String,
    @SerializedName("exp_end_date") var exp_end_date: String,
    @SerializedName("actual_start_date") var actual_start_date: String,
    @SerializedName("actual_end_date") var actual_end_date: String,
    @SerializedName("url") var url: String,
//    @SerializedName("task_id")var task_id:String,
    )
