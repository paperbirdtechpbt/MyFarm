package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class PackConfigFieldList(
    @SerializedName("field_id")var field_id:String,
    @SerializedName("field_name")var field_name:String,
    @SerializedName("field_description")var field_description:String,
    @SerializedName("field_type")var field_type:String,
    @SerializedName("field_value")var field_value:String,
    @SerializedName("editable")var editable:String,
    @SerializedName("field_list")var field_list:List<PackFieldList>,
)
