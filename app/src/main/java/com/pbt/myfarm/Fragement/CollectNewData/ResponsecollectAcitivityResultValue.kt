package com.pbt.myfarm.Fragement.CollectNewData

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class ResponsecollectAcitivityResultValue (
    @SerializedName("error")val error:Boolean,
    @SerializedName("data")val data:Data,
    @SerializedName("unit_list")val unit_list:List<UnitList>,
        )

data class UnitList (
    @SerializedName("id")val id:String ,
    @SerializedName("name")val name:String,)

data class Data (
    @SerializedName("type")val type:String,
    @SerializedName("list_id")val list_id:String="0",
    @SerializedName("list_array")val list_array:JsonArray,
        )
