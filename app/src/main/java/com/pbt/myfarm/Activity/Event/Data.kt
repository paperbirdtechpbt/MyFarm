package com.pbt.myfarm.Activity.Event

data class Data(
    var field_actual_duration: String?=null,
    var field_actual_end_date: String?=null,
    var field_actual_start_date: String?=null,
    var field_assigned_team: Int?=null,
    var field_closed: Int?=null,
    var field_com_group: Int?=null,
    var field_com_group_list: List<FieldComGroup>,
    var field_description: String?=null,
    var field_exp_duration: String?=null,
    var field_exp_end_date: String?=null,
    var field_exp_start_date: String?=null,
    var field_id: Int?=null,
    var field_name: String?=null,
    var field_responsible: Int?=null,
    var field_responsible_list: List<FieldResponsible>?=null,
    var field_status: Int?=null,
    var field_status_list: List<FieldStatus>?=null,
    var field_team_list: List<FieldTeam>?=null,
    var field_type: Int?=null,
    var field_type_list: List<FieldType>?=null,)


