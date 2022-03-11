package com.pbt.myfarm.Activity.Event

data class Data(
    val field_actual_duration: String,
    val field_actual_end_date: String,
    val field_actual_start_date: String,
    val field_assigned_team: Int,
    val field_closed: Int,
    val field_com_group: Int,
    val field_com_group_list: List<FieldComGroup>,
    val field_description: String,
    val field_exp_duration: String,
    val field_exp_end_date: String,
    val field_exp_start_date: String,
    val field_id: Int,
    val field_name: String,
    val field_responsible: Int,
    val field_responsible_list: List<FieldResponsible>,
    val field_status: Int,
    val field_status_list: List<FieldStatus>,
    val field_team_list: List<FieldTeam>,
    val field_type: Int,
    val field_type_list: List<FieldType>
)