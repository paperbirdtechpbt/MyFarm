package com.pbt.myfarm.Activity.Event

data class Data(
    val field_actual_duration: String?=null,
    val field_actual_end_date: String?=null,
    val field_actual_start_date: String?=null,
    val field_assigned_team: Int?=null,
    val field_closed: Int?=null,
    val field_com_group: Int?=null,
    val field_com_group_list: List<FieldComGroup>,
    val field_description: String?=null,
    val field_exp_duration: String?=null,
    val field_exp_end_date: String?=null,
    val field_exp_start_date: String?=null,
    val field_id: Int?=null,
    val field_name: String?=null,
    val field_responsible: Int?=null,
    val field_responsible_list: List<FieldResponsible>?=null,
    val field_status: Int?=null,
    val field_status_list: List<FieldStatus>?=null,
    val field_team_list: List<FieldTeam>?=null,
    val field_type: Int?=null,
    val field_type_list: List<FieldType>?=null,)


