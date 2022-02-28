package com.pbt.myfarm.Service

import com.google.gson.JsonArray
import com.pbt.myfarm.HttpResponse.Field


data class ConfigFieldList (
    val editable: Int,
    val field_description: String,
    val field_id: String,
    val field_list: JsonArray,
    val field_name: String,
    val field_type: String,
    val field_value: String


        )
