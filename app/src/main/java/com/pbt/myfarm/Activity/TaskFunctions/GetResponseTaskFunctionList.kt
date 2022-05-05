package com.pbt.myfarm.Activity.TaskFunctions

data class GetResponseTaskFunctionList(
    val `data`: List<ListOfTaskFunctions>,
    val error: Boolean
)

data class ListOfTaskFunctions(
    val created_by: String?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val id: String?=null,
    val last_changed_by: String?=null,
    val last_changed_date: String?=null,
    val listid: String?=null,
    val name: String?=null,
    val name1: String?=null,
    val privilege: String?=null,
    val privilegename: String?=null,
    val task_config_id: String?=null
)