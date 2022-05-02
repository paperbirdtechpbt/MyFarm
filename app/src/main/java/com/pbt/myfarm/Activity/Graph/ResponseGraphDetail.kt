package com.pbt.myfarm.Activity.Graph

import com.google.gson.annotations.SerializedName

data class ResponseGraphDetail(
    @SerializedName("charts")var charts:List<ListCharts>,
    @SerializedName("error")var error:String?=null
)

data class ListCharts (
    @SerializedName("graph_type")var graph_type:String?=null,
    @SerializedName("graph_name")var graph_name:String?=null,
    @SerializedName("graph_desc")var graph_desc:String?=null,
    @SerializedName("graph_title")var graph_title:String?=null,
    @SerializedName("graph_abcissa_title")var graph_abcissa_title:String?=null,
    @SerializedName("graph_ordinate_title")var graph_ordinate_title:String?=null,
    @SerializedName("lines")var lines:List<ListLines>,

        )
data class ListLines (
    @SerializedName("id")var id:String?=null,
    @SerializedName("name")var name:String?=null,
    @SerializedName("line_type")var line_type:String?=null,
    @SerializedName("result_class")var result_class:String?=null,
    @SerializedName("points")var points:List<ListPoints>?=null,

        )
data class ListPoints (
    @SerializedName("id")var id:String?=null,
    @SerializedName("pack_id")var pack_id:String?=null,
    @SerializedName("value")var value:String?=null,
    @SerializedName("create_at")var create_at:String?=null,
    @SerializedName("duration")var duration :String?=null,

        )
data class ListMYPoints (
    @SerializedName("id")var id:String?=null,
    @SerializedName("pack_id")var pack_id:String?=null,
    @SerializedName("value")var value:String?=null,
    @SerializedName("create_at")var create_at:String?=null,
    @SerializedName("duration")var duration :String?=null,

        )
