package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName

data class PackConfigFieldList(

    @SerializedName("field_id")var field_id:String?=null,
    @SerializedName("field_name")var field_name:String?=null,
    @SerializedName("field_description")var field_description:String?=null,
    @SerializedName("field_type")var field_type:String?=null,
    @SerializedName("field_value")var field_value:String?=null,
    @SerializedName("editable")var editable:String?=null,
    @SerializedName("field_list")var field_list:List<PackFieldList>?=null,
)
