package com.pbt.myfarm.Service

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.Activity.FieldLIst
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.HttpResponse.PackFieldList


data class ConfigFieldList (


    val editable: String?=null,
    val field_description: String?=null,
    val field_id: String?=null,
//    val field_list: JsonArray?=null,
    val field_list: List<FieldLIst>?=null,
    val field_name: String?=null,
    val field_type: String?=null,
    val field_value: String?=null
 )


