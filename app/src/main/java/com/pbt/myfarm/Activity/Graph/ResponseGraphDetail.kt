package com.pbt.myfarm.Activity.Graph

import com.google.gson.annotations.SerializedName

data class ResponseGraphDetail(
    @SerializedName("charts")var charts:List<ListCharts>,
    @SerializedName("error")var error:String
)

data class ListCharts (
    @SerializedName("graph_type")var graph_type:String,
    @SerializedName("graph_name")var graph_name:String,
    @SerializedName("graph_desc")var graph_desc:String,
    @SerializedName("graph_title")var graph_title:String,
    @SerializedName("graph_abcissa_title")var graph_abcissa_title:String,
    @SerializedName("graph_ordinate_title")var graph_ordinate_title:String,
    @SerializedName("lines")var lines:List<ListLines>,

        )
data class ListLines (
    @SerializedName("id")var id:String,
    @SerializedName("name")var name:String,
    @SerializedName("line_type")var line_type:String,
    @SerializedName("result_class")var result_class:String,
    @SerializedName("points")var points:List<ListPoints>,

        )
data class ListPoints (
    @SerializedName("id")var id:String,
    @SerializedName("pack_id")var pack_id:String,
    @SerializedName("value")var value:String,
    @SerializedName("create_at")var create_at:String,
    @SerializedName("duration")var duration :String,

        )
data class ListMYPoints (
    @SerializedName("id")var id:String,
    @SerializedName("pack_id")var pack_id:String,
    @SerializedName("value")var value:String,
    @SerializedName("create_at")var create_at:String,
    @SerializedName("duration")var duration :String,

        )
