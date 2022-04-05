package com.pbt.myfarm.Fragement.CollectNewData

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class ResponsecollectAcitivityResultValue (
    @SerializedName("error")val error:Boolean,
    @SerializedName("data")val data:Data,
    @SerializedName("unit_list")val unit_list:List<UnitList>,
        )

data class UnitList (
    @SerializedName("id")val id:String?=null ,
    @SerializedName("name")val name:String?=null,)

data class Data (
    @SerializedName("type")val type:String?=null,
    @SerializedName("list_id")val list_id:String?=null,
    @SerializedName("list_array")val list_array:List<List_array>,
        )
data class List_array (
    @SerializedName("id")val id:String?=null ,
    @SerializedName("lists_id")val lists_id:String?=null,
    @SerializedName("name")val name:String?=null,
)