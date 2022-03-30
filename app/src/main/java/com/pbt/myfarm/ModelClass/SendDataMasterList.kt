package com.pbt.myfarm.ModelClass

data class SendDataMasterList(
    var collect_data: List<CollectData>,
    var events: List<Event>,
    var packs_new: List<PacksNew>,
    var task_fields: List<TaskField>,
    var task_objects: List<TaskObject>,
    var tasks: List<Task>
)

data class CollectData(
    val collect_activity_id: String?=null,
    val created_at: String?=null,
    val deleted_at: String?=null,
    val duration: String?=null,
    val new_value: String?=null,
    val pack_id: String?=null,
    val result_class: String?=null,
    val result_id: String?=null,
    val sensor_id: String?=null,
    val status: Int?=null,
    val unit_id: String?=null,
    val updated_at: String?=null,
    val user_id: Int?=null,
    val value: String?=null
)

data class Event(
    val actual_duration: String?=null,
    val actual_end_date: String?=null,
    val actual_start_date: String?=null,
    val assigned_team: String?=null,
    val closed: String?=null,
    val closed_date: String?=null,
    val com_group: String?=null,
    val description: String?=null,
    val event_status: String?=null,
    val exp_duration: String?=null,
    val exp_end_date: String?=null,
    val exp_start_date: String?=null,
    val id: String?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val responsible: String?=null,
    val status: Int?=null,
    val type: String?=null,
    val user_id: Int?=null
)

data class PacksNew(
    val com_group: String?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val pack_config_id: String?=null,
    val pack_fields: List<PackField>,
    val status: Int?=null,
    val user_id: Int?=null
)

data class TaskField(
    val field_id: String?=null,
    val status: Int?=null,
    val task_func: String?=null,
    val task_id: String?=null,
    val user_id: Int?=null,
    val value: String?=null
)

data class TaskObject(
    val container: String?=null,
    val last_changed_date: String?=null,
    val status: Int?=null,
    val task_func: String?=null,
    val task_id: String?=null,
    val user_id: Int?=null
)

data class Task(
    val com_group: String?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val events: List<EventX>,
    val last_changed_date: String?=null,
    val name: String?=null,
    val status: Int?=null,
    val task_config_id: String?=null,
    val task_fields: List<TaskFieldX>,
    val user_id: Int?=null
)

data class PackField(
    val field_id: String?=null,
    val value: String?=null
)

data class EventX(
    val actual_duration: String?=null,
    val actual_end_date: String?=null,
    val actual_start_date: String?=null,
    val assigned_team: String?=null,
    val com_group: String?=null,
    val created_date: String?=null,
    val description: String?=null,
    val exp_duration: String?=null,
    val exp_end_date: String?=null,
    val exp_start_date: String?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val responsible: String?=null,
    val task_id: String?=null
)

data class TaskFieldX(
    val field_id: String?=null,
    val value: String?=null
)