package com.pbt.myfarm.DataBase

import android.R.attr.path
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.HttpResponse.PackCommunityList
import com.pbt.myfarm.Unit
import com.pbt.myfarm.Util.AppConstant.Companion.COL_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_LOCAL_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACKNEW_Status
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_CONFIG_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_DESC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_IS_ACTIVE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_LAST_CHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_PACK_NEW_DLETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_COMMUNITYGROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_DELTED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_DELTED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activities_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_COLLECT_ACTIVITY_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_CREATEDAT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_CREATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_DELETEAT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_DELETEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_LISTID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_RESULTCLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_RESULTNAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_TYPEID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_UNITID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_UPDATEDAT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_UPDATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_unit_COLLECT_ACITIVITY_RESULT_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_unit_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_unit_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_activity_results_unit_UNITID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_CREATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_CollectActivityId
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_DELETED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_DURATION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_NEWVALUE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_ResulId
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_ResultClass
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_SENSORID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_UNITID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_collect_data_pack_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_COMM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_DELTED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_DESCIPRTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_community_groups_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_CAPACITY_UNITS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_CREATED_DATE_UTC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_LAST_CHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_LAST_CHANGED_UTC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_MAX_CAPACITY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_NOTIFICATION_LEVEL
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_PARENT_CONTAINER
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_STATUS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_ZONE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_ADDED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_ADDED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_ADDED_UTC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_CONTAINERNO
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_OBJECTNAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_OBJECT_NO
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_SESSIN_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_container_object_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_ACTUAL_DURATION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_ACTUAL_END_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_ACTUAL_STR_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_ASSIGN_TEAM
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_CLOSED
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_CLOSED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_CLOSED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_EXP_DURATION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_EXP_END_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_EXP_STR_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_LAST_CHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_RESPONSIBLE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_STATUS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_TASK_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_events_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_ALTITUDE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_ALTITUDE_UNIT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_AREA_UNIT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_CLIMATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_COMMUNITY_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_COUNTRY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_DELETED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_FIELD_BOUNDARY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_FIELD_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_FIELD_CONTACT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_FIELD_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_HARVEST_PERIOD
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_HUMIDITY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_HUMIDITY_UNIT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LAST_VISITED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LAST_VISITED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LAST_VISITED_UTC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LATITUDE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LIST_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LOCALITY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_LONGITUDE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_MAIN_CULTURE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_NUMBER_OF_PLANT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_OTHER_CULTURE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_PLANT_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_PLUVIOMETRY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_PLUVIOMETRY_UNIT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_REGION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_SOIL_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_SURFACE_AREA
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_TEAM_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_TEMPERATURE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_TEMP_UNIT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_UNIT_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_fields_VEGETATION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_CHARTID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_GRAPH_CHARTID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_LAST_CHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_LINETYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_REF_CLTL_POINT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_RESULT_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_objects_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_CHARTID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_CreateAt
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_DURATION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_PACKID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_chart_points_VALUE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_ABCISSA_TITLE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_LASTCHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_LASTCHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_OBJECTCLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_ORDINATE_TITLE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_graph_charts_TITLE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_CHOICE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_CHOICE_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_COM_GROUP_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_LISTID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_list_choices_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_COM_GROUP_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_DELETED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_DESC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_lists_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_collect_activity_PRIMARY_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_collect_activity_SERVER_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_collect_activity_collect_activity_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_collect_activity_pack_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_PRIMARY_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_SERVER_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_created_by
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_created_date
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_default_value
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_deleted_at
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_editable
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_field_description
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_field_name
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_field_type
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_last_changed_by
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_last_changed_date
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_list
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_config_fields_pack_config_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_COLLECTACTIVITY_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_COMGROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_CREATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_GRAPHCHCHART_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_LAST_CHNAGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_NAMEPREFIX
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_PRIMARY_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_SERVER_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_configs_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_fields_PRIMARY_ID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_fields_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_fields_field_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_fields_pack_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_pack_fields_value
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_ADDRESS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_BIRTHPLACE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_CERTIFICAITON
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_CITIZENSHIP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_COMMUNITY_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_CONTACT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_DELETED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_DEWSCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_DOB
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_EMAIl
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_FNAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_IS_IN_COOP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_IS_KAKAOMUNDO
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_KAKAOMUNDO_CENTER
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_LASTCERFICATION_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_LNAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_PERSON_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_PERSON_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_PHOTO
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_people_USERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_BRAND
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_CONNECTEDBOARD
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_CONTAINERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_CREATEDAT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_CREATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_DELETEDAT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_DELETEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_MAXIMUM
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_MINIMUM
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_MODEL
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_OWNER
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_SENSORID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_SENSORIP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_TYPEID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_UNITID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_UPDATEDAT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_UPDATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_sensors_USERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_created_by
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_created_date
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_deleted_at
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_editable
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_field_description
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_field_name
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_field_type
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_last_changed_by
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_last_changed_date
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_list
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_fields_task_config_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_SERVERKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_created_by
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_created_date
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_deleted_at
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_description
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_last_changed_by
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_last_changed_date
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_privilege
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_task_config_id
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_config_functions_task_name
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_CLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_LAST_CHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_NAME_PREFIX
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_RECORD_EVENT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_REPORTABLE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_configs__LAST_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_fields_FIELDID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_fields_PRIMARY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_fields_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_fields_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_fields_VALUE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_media_files_LINK
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_media_files_LOCAL_FILE_PATH
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_media_files_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_media_files_PRIMARYID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_media_files_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_task_media_files_TASKID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_DELTED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_DESC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_ENDED_LATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_LASTCHANGED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_LAST_CHANGED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_SERVERid
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_STARTED_LATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_STATUS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_TASKFUNC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_TASK_CONFIGID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_ADDRESS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_COMGROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_CONTACT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_CREATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_DELETED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_DESC
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_EMAIL
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_LOGO
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_PRIMARYID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_RESPONSIBLE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_TEAMCLASS
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_TEAMTYPE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_team_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_COM_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_CREATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_DELETED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_DELETED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_DESCRIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_PRIMARYID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_SERVERID
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_UPDATED_AT
import com.pbt.myfarm.Util.AppConstant.Companion.COL_units_UPDATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_DATABASE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_DATABASE_VERSION
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_END_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_EXP_END_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_EXP_STR_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_STR_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_NEW_TASK
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACKCONFIG_FIELDLIST_TABLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACKCONFIG_FIELDLIST_field_list_TABLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACKS_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_ITEM
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_description
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_id
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_id_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_name
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_name_name
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_type
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_type_packid
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_value
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_field_value_configid
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIG_FIELDLIST_fieldlist_ITEM
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CREATEDBY
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CommunityGroupID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CommunityGroupITEM_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CommunityGroupNAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CommunityGroupTABLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CommunityGroupcommunity_group
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_DETAIL
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_LABELTYPE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_LABEL_DESCIPTION
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_NAME_PREFIX
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_NAME_PREFIX_padzero
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_PADZERO
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_TYPE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TABLE_PACK
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_DETAIL
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERNAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERPASS
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERROLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERS_TABLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USER_ID
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_CREAT_PACK
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_PACKCONFIG
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_collect_activities
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_collect_activity_results
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_collect_activity_results_unit
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_collect_data
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_community_groups
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_container
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_container_object
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_events
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_graph_chart_objects
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_graph_chart_points
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_graph_charts
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_list_choices
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_lists
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_collect_activity
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_config_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_people
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_sensors
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_config_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_config_functions
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_configs
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_media_files
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_tasks
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_team
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_units
import com.pbt.myfarm.Util.AppUtils
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.security.AccessController.getContext


@Suppress("DEPRECATION")
class DbHelper(var context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, CONST_DATABASE_NAME, factory, CONST_DATABASE_VERSION) {

    private val TAG = "DbHelper"
companion object {
    var localFilePath: String ?=null
}



    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + CONST_USERS_TABLE + " ("
                + CONST_ID + " INTEGER PRIMARY KEY, " +
                CONST_USERNAME + " TEXT," +
                CONST_USERROLE + " TEXT," + CONST_USERPASS + " TEXT" + ")")
        db?.execSQL(query)

        val userTaskTable = ("CREATE TABLE " + CONST_NEW_TASK + " ("
                + CONST_ID + " INTEGER PRIMARY KEY, " +
                CONST_USER_ID + " TEXT," +
                CONST_USERNAME + " TEXT," +
                CONST_TASK_NAME + " TEXT," +
                CONST_TASK_TYPE + " TEXT," +
                CONST_EXPECTED_EXP_STR_DATE + " TEXT," +
                CONST_EXPECTED_EXP_END_DATE + " TEXT," +
                CONST_EXPECTED_STR_DATE + " TEXT," +
                CONST_EXPECTED_END_DATE + " TEXT," +
                CONST_TASK_DETAIL + " TEXT" + ")")

        db?.execSQL(userTaskTable)

        val userPackTable = ("CREATE TABLE " + CONST_TABLE_PACK + " ("
                + CONST_PACK_ID + " INTEGER PRIMARY KEY, " +
                CONST_PACKS_ID + " TEXT," +
                CONST_PACK_NAME + " TEXT," +
                CONST_PACK_TYPE + " TEXT," +
                CONST_PACK_LABELTYPE + " TEXT," +
                CONST_PACK_TYPE_ID + " TEXT," +
                CONST_PACK_LABEL_DESCIPTION + " TEXT," +
                CONST_PACK_DETAIL + " TEXT," +
                CONST_PACK_NAME_PREFIX_padzero + " TEXT," +
                CONST_PACK_NAME_PREFIX + " TEXT," +
                CONST_PACK_CREATEDBY + " TEX T," +
                CONST_PACK_PADZERO + " TEXT," +
                CONST_PACK_GROUP + " TEXT" + ")")

        db?.execSQL(userPackTable)
//
//        val packConfigListTable = ("CREATE TABLE " + CONST_PACKCONFIG_LIST_TABLE + " ("
//                + CONST_PACK_CONFIGLIST_ITEM + " INTEGER PRIMARY KEY, " +
//                CONST_PACK_CONFIGLIST_NAME + " TEXT," +
//                CONST_PACK_CONFIGLIST_ID + " TEXT" + ")")
//
//        db?.execSQL(packConfigListTable)


//ravi- offline table -------pack_configs------------------------------------->

        val pack_configs = ("CREATE TABLE " + TABLE_PACKCONFIG + " ("
                + COL_pack_configs_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                COL_pack_configs_SERVER_ID + " TEXT," +
                COL_pack_configs_NAME + " TEXT," +
                COL_pack_configs_DESCIPTION + " TEXT," +
                COL_pack_configs_TYPE + " TEXT," +
                COL_pack_configs_CLASS + " TEXT," +
                COL_pack_configs_COMGROUP + " TEXT," +
                COL_pack_configs_NAMEPREFIX + " TEXT," +
                COL_pack_configs_COLLECTACTIVITY_ID + " TEXT," +
                COL_pack_configs_GRAPHCHCHART_ID + " TEXT," +
                COL_pack_configs_CREATEDBY + " TEXT," +
                COL_pack_configs_CREATED_DATE + " TEXT," +
                COL_pack_configs_LAST_CHANGED_BY + " TEXT," +
                COL_pack_configs_LAST_CHNAGED_DATE + " TEXT," +
                COL_pack_configs_DELETED_AT + " TEXT " + ")")

        db?.execSQL(pack_configs)
        //   <----------------------------------------------------------->
        // ravi -offline table ---pack_collect_activity

        val pack_collect_activity = ("CREATE TABLE " + TABLE_pack_collect_activity + " ("
                + COL_pack_collect_activity_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                COL_pack_collect_activity_SERVER_ID + " TEXT," +
                COL_pack_collect_activity_collect_activity_id + " TEXT," +
                COL_pack_collect_activity_pack_id + " TEXT " + ")")

        db?.execSQL(pack_collect_activity)

        // ravi -offline table ---task_media_files


        val task_media_files = ("CREATE TABLE " + TABLE_task_media_files + " ("
                + COL_task_media_files_PRIMARYID + " INTEGER PRIMARY KEY, " +
                COL_task_media_files_SERVERID + " TEXT," +
                COL_task_media_files_TASKID + " TEXT," +
                COL_task_media_files_NAME + " TEXT," +
                COL_task_media_files_LOCAL_FILE_PATH + " TEXT," +
                COL_task_media_files_LINK + " TEXT " + ")")

        db?.execSQL(task_media_files)
        //--------------//

        // ravi -offline table ---pack_config_fields
        val pack_config_fields = ("CREATE TABLE " + TABLE_pack_config_fields + " ("
                + COL_pack_config_fields_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                COL_pack_config_fields_SERVER_ID + " TEXT," +
                COL_pack_config_fields_pack_config_id + " TEXT," +
                COL_pack_config_fields_field_name + " TEXT," +
                COL_pack_config_fields_field_description + " TEXT," +
                COL_pack_config_fields_editable + " TEXT," +
                COL_pack_config_fields_field_type + " TEXT," +
                COL_pack_config_fields_list + " TEXT," +
                COL_pack_config_fields_default_value + " TEXT," +
                COL_pack_config_fields_created_by + " TEXT," +
                COL_pack_config_fields_last_changed_by + " TEXT," +
                COL_pack_config_fields_last_changed_date + " TEXT," +
                COL_pack_config_fields_deleted_at + " TEXT," +
                COL_pack_config_fields_created_date + " TEXT " + ")")

        db?.execSQL(pack_config_fields)
        //--------------//

        // ravi -offline table ---pack_fields

        val pack_fields = ("CREATE TABLE " + TABLE_pack_fields + " ("
                + COL_pack_fields_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                COL_pack_fields_pack_id + " TEXT," +
                COL_pack_fields_value + " TEXT," +
                COL_pack_fields_SERVERID + " TEXT," +
                COL_pack_fields_field_id + " TEXT " + ")")
        db?.execSQL(pack_fields)
        //--------------//

        val packConfigFieldListTable = ("CREATE TABLE " + CONST_PACKCONFIG_FIELDLIST_TABLE + " ("
                + CONST_PACK_CONFIG_FIELDLIST_ITEM + " INTEGER PRIMARY KEY, " +
                CONST_PACK_CONFIG_FIELDLIST_field_id + " TEXT," +
                CONST_PACK_CONFIG_FIELDLIST_field_name + " TEXT," +
                CONST_PACK_CONFIG_FIELDLIST_field_description + " TEXT," +
                CONST_PACK_CONFIG_FIELDLIST_field_type + " TEXT," +
                CONST_PACK_CONFIG_FIELDLIST_field_value + " TEXT" + ")")
        db?.execSQL(packConfigFieldListTable)

        val packConfigFieldList_FieldList_Table =
            ("CREATE TABLE " + CONST_PACKCONFIG_FIELDLIST_field_list_TABLE + " ("
                    + CONST_PACK_CONFIG_FIELDLIST_fieldlist_ITEM + " INTEGER PRIMARY KEY, " +
                    CONST_PACK_CONFIG_FIELDLIST_field_id_ID + " TEXT," +
                    CONST_PACK_CONFIG_FIELDLIST_field_name_name + " TEXT," +
                    CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid + " TEXT," +
                    CONST_PACK_CONFIG_FIELDLIST_field_type_packid + " TEXT," +
                    CONST_PACK_CONFIG_FIELDLIST_field_value_configid + " TEXT" + ")")
        db?.execSQL(packConfigFieldList_FieldList_Table)

        val packCommunityGroup = ("CREATE TABLE " + CONST_PACK_CommunityGroupTABLE + " ("
                + CONST_PACK_CommunityGroupITEM_ID + " INTEGER PRIMARY KEY, " +
                CONST_PACK_CommunityGroupID + " TEXT," +
                CONST_PACK_CommunityGroupNAME + " TEXT," +
                CONST_PACK_CommunityGroupcommunity_group + " TEXT" + ")")
        db?.execSQL(packCommunityGroup)


        val pack_new = ("CREATE TABLE " + TABLE_CREAT_PACK + " ("
                + COL_LOCAL_ID + " INTEGER PRIMARY KEY, " +
                COL_ID + " TEXT," +
                COL_PACK_NAME + " TEXT," +
                COL_PACK_DESC + " TEXT," +
                COL_PACK_CONFIG_ID + " TEXT," +
                COL_PACK_GROUP + " TEXT," +
                COL_PACKNEW_Status + " TEXT," +
                COL_PACK_IS_ACTIVE + " TEXT," +
                COL_PACK_CREATED_BY + " TEXT," +
                COL_PACK_LAST_CHANGED_BY + " TEXT," +
                COL_PACK_CREATED_DATE + " TEXT," +
                COL_PACK_LAST_CHANGED_DATE + " TEXT," +
                COL_PACK_NEW_DLETED_AT + " TEXT" + ")")
        db?.execSQL(pack_new)


        // ravi -offline table ---collect_data


        val collect_data = ("CREATE TABLE " + TABLE_collect_data + " ("
                + COL_collect_data_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_collect_data_SERVERID + " TEXT," +
                COL_collect_data_pack_id + " TEXT," +
                COL_collect_data_ResulId + " TEXT," +
                COL_collect_data_ResultClass + " TEXT," +
                COL_collect_data_CollectActivityId + " TEXT," +
                COL_collect_data_NEWVALUE + " TEXT," +
                COL_collect_data_UNITID + " TEXT," +
                COL_collect_data_SENSORID + " TEXT," +
                COL_collect_data_CREATEDBY + " TEXT," +
                COL_collect_data_DURATION + " TEXT," +
                COL_collect_data_UPDATED_BY + " TEXT," +
                COL_collect_data_DELETED_BY + " TEXT," +
                COL_collect_data_CREATED_AT + " TEXT," +
                COL_collect_data_UPDATED_AT + " TEXT," +
                COL_collect_data_DELETED_AT + " TEXT " + ")")
        db?.execSQL(collect_data)
        //--------------//

        // ravi -offline table ---collect_activity_result


        val collect_activity_results = ("CREATE TABLE " + TABLE_collect_activity_results + " ("
                + COL_collect_activity_results_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_collect_activity_results_SERVERID + " TEXT," +
                COL_collect_activity_results_COLLECT_ACTIVITY_ID + " TEXT," +
                COL_collect_activity_results_RESULTNAME + " TEXT," +
                COL_collect_activity_results_UNITID + " TEXT," +
                COL_collect_activity_results_TYPEID + " TEXT," +
                COL_collect_activity_results_LISTID + " TEXT," +
                COL_collect_activity_results_RESULTCLASS + " TEXT," +
                COL_collect_activity_results_CREATEDBY + " TEXT," +
                COL_collect_activity_results_UPDATEDBY + " TEXT," +
                COL_collect_activity_results_DELETEDBY + " TEXT," +
                COL_collect_activity_results_CREATEDAT + " TEXT," +
                COL_collect_activity_results_UPDATEDAT + " TEXT," +
                COL_collect_activity_results_DELETEAT + " TEXT " + ")")
        db?.execSQL(collect_activity_results)

        // ravi -offline table ---people

        val people = ("CREATE TABLE " + TABLE_people + " ("
                + COL_people_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_people_SERVERID + " TEXT," +
                COL_people_FNAME + " TEXT," +
                COL_people_LNAME + " TEXT," +
                COL_people_EMAIl + " TEXT," +
                COL_people_CONTACT + " TEXT," +
                COL_people_BIRTHPLACE + " TEXT," +
                COL_people_DOB + " TEXT," +
                COL_people_PHOTO + " TEXT," +
                COL_people_ADDRESS + " TEXT," +
                COL_people_CITIZENSHIP + " TEXT," +
                COL_people_CERTIFICAITON + " TEXT," +
                COL_people_IS_IN_COOP + " TEXT," +
                COL_people_LASTCERFICATION_DATE + " TEXT," +
                COL_people_IS_KAKAOMUNDO + " TEXT," +
                COL_people_KAKAOMUNDO_CENTER + " TEXT," +
                COL_people_USERID + " TEXT," +
                COL_people_COMMUNITY_GROUP + " TEXT," +
                COL_people_PERSON_CLASS + " TEXT," +
                COL_people_PERSON_TYPE + " TEXT," +
                COL_people_DEWSCIPTION + " TEXT," +
                COL_people_CREATED_BY + " TEXT," +
                COL_people_UPDATED_BY + " TEXT," +
                COL_people_UPDATED_AT + " TEXT," +
                COL_people_CREATED_AT + " TEXT," +
                COL_people_DELETED_BY + " TEXT," +
                COL_people_DELETED_AT + " TEXT " + ")")
        db?.execSQL(people)
        //--------------//

//        val pack_fields = ("CREATE TABLE " + PACKFIELDS_TABLENAME + " ("
//                + PACKFIELDS_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
//                PACKFIELDS_packid + " TEXT," +
//                PACKFIELDS_fieldid + " TEXT," +
//                PACKFIELDS_value + " TEXT" + ")")
//        db?.execSQL(pack_fields)

//        val pack_config_fields = ("CREATE TABLE " + TABLE_PACK_CONFIG_FIELDS + " ("
//                + COL_PACKCONFIGPRIMARY_ID + " INTEGER PRIMARY KEY, " +
//                COL_SERVER_ID + " TEXT," +
//                COL_PACKCONFIG_ID + " TEXT," +
//                COL_FIELD_NAME + " TEXT," +
//                COL_field_description + " TEXT," +
//                COL_field_type + " TEXT," +
//                COL_list + " TEXT," +
//                COL_default_value + " TEXT," +
//                COL_created_date + " TEXT," +
//                COL_created_by + " TEXT," +
//                COL_last_changed_by + " TEXT," +
//                COL_last_changed_date + " TEXT," +
//                COL_eleted_at + " TEXT" + ")")
//        db?.execSQL(pack_config_fields)

//ravi- offline ---------table------task-------//

        val tasks = ("CREATE TABLE " + TABLE_tasks + " ("
                + COL_tasks_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_tasks_SERVERid + " TEXT," +
                COL_tasks_NAME + " TEXT," +
                COL_tasks_DESC + " TEXT," +
                COL_tasks_GROUP + " TEXT," +
                COL_tasks_TASK_CONFIGID + " TEXT," +
                COL_tasks_TASKFUNC + " TEXT," +
                COL_tasks_STATUS + " TEXT," +
                COL_tasks_STARTED_LATE + " TEXT," +
                COL_tasks_ENDED_LATE + " TEXT," +
                COL_tasks_CREATED_BY + " TEXT," +
                COL_tasks_CREATED_DATE + " TEXT," +
                COL_tasks_LAST_CHANGED_BY + " TEXT," +
                COL_tasks_LASTCHANGED_DATE + " TEXT," +
                COL_tasks_DELTED_AT + " TEXT" + ")")
        db?.execSQL(tasks)


        //ravi -offline---------table-----task_fields

        val task_fields = ("CREATE TABLE " + TABLE_task_fields + " ("
                + COL_task_fields_PRIMARY + " INTEGER PRIMARY KEY, " +
                COL_task_fields_SERVERID + " TEXT," +
                COL_task_fields_TASKID + " TEXT," +
                COL_task_fields_FIELDID + " TEXT," +
                COL_task_fields_VALUE + " TEXT" + ")")
        db?.execSQL(task_fields)

        //ravi -offline---------table-----task_configs

        val task_configs = ("CREATE TABLE " + TABLE_task_configs + " ("
                + COL_task_configs_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_task_configs_SERVERID + " TEXT," +
                COL_task_configs_NAME + " TEXT," +
                COL_task_configs_DESCIPTION + " TEXT," +
                COL_task_configs_TYPE + " TEXT," +
                COL_task_configs_CLASS + " TEXT," +
                COL_task_configs_COM_GROUP + " TEXT," +
                COL_task_configs_NAME_PREFIX + " TEXT," +
                COL_task_configs_RECORD_EVENT + " TEXT," +
                COL_task_configs_REPORTABLE + " TEXT," +
                COL_task_configs_CREATED_BY + " TEXT," +
                COL_task_configs_CREATED_DATE + " TEXT," +
                COL_task_configs_LAST_CHANGED_BY + " TEXT," +
                COL_task_configs_LAST_CHANGED_DATE + " TEXT," +
                COL_task_configs__LAST_DELETED_AT + " TEXT" + ")")
        db?.execSQL(task_configs)

        //ravi -offline---------table-----task_config_fields
        val task_config_fields = ("CREATE TABLE " + TABLE_task_config_fields + " ("
                + COL_task_config_fields_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_task_config_fields_SERVERID + " TEXT," +
                COL_task_config_fields_task_config_id + " TEXT," +
                COL_task_config_fields_field_name + " TEXT," +
                COL_task_config_fields_field_description + " TEXT," +
                COL_task_config_fields_editable + " TEXT," +
                COL_task_config_fields_field_type + " TEXT," +
                COL_task_config_fields_list + " TEXT," +
                COL_task_config_fields_created_by + " TEXT," +
                COL_task_config_fields_created_date + " TEXT," +
                COL_task_config_fields_last_changed_by + " TEXT," +
                COL_task_config_fields_last_changed_date + " TEXT," +
                COL_task_config_fields_deleted_at + " TEXT" + ")")
        db?.execSQL(task_config_fields)

        //ravi -offline---------table-----task_config_functions

        val task_config_functions = ("CREATE TABLE " + TABLE_task_config_functions + " ("
                + COL_task_config_functions_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_task_config_functions_SERVERKEY + " TEXT," +
                COL_task_config_functions_task_config_id + " TEXT," +
                COL_task_config_functions_task_name + " TEXT," +
                COL_task_config_functions_description + " TEXT," +
                COL_task_config_functions_privilege + " TEXT," +
                COL_task_config_functions_created_by + " TEXT," +
                COL_task_config_functions_created_date + " TEXT," +
                COL_task_config_functions_last_changed_by + " TEXT," +
                COL_task_config_functions_deleted_at + " TEXT," +
                COL_task_config_functions_last_changed_date + " TEXT" + ")")
        db?.execSQL(task_config_functions)

        //ravi -offline---------table-----community_groups


        val community_groups = ("CREATE TABLE " + TABLE_community_groups + " ("
                + COL_community_groups_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_community_groups_SERVERID + " TEXT," +
                COL_community_groups_NAME + " TEXT," +
                COL_community_groups_DESCIPRTION + " TEXT," +
                COL_community_groups_COMM_GROUP + " TEXT," +
                COL_community_groups_CREATED_BY + " TEXT," +
                COL_community_groups_DELTED_BY + " TEXT," +
                COL_community_groups_UPDATED_BY + " TEXT," +
                COL_community_groups_CREATED_AT + " TEXT," +
                COL_community_groups_UPDATED_AT + " TEXT," +
                COL_community_groups_DELETED_AT + " TEXT" + ")")
        db?.execSQL(community_groups)

        //ravi -offline---------table-----collect_activities


        val collect_activities = ("CREATE TABLE " + TABLE_collect_activities + " ("
                + COL_collect_activities_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_collect_activities_SERVERID + " TEXT," +
                COL_collect_activities_NAME + " TEXT," +
                COL_collect_activities_COMMUNITYGROUP + " TEXT," +
                COL_collect_activities_CREATED_BY + " TEXT," +
                COL_collect_activities_UPDATED_BY + " TEXT," +
                COL_collect_activities_DELTED_BY + " TEXT," +
                COL_collect_activities_CREATED_AT + " TEXT," +
                COL_collect_activities_UPDATED_AT + " TEXT," +
                COL_collect_activities_DELTED_AT + " TEXT" + ")")
        db?.execSQL(collect_activities)

        //ravi -offline---------table-----collect_activities_result_unit

        val collect_activities_result_unit =
            ("CREATE TABLE " + TABLE_collect_activity_results_unit + " ("
                    + COL_collect_activity_results_unit_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                    COL_collect_activity_results_unit_SERVERID + " TEXT," +
                    COL_collect_activity_results_unit_COLLECT_ACITIVITY_RESULT_ID + " TEXT," +
                    COL_collect_activity_results_unit_UNITID + " TEXT" + ")")
        db?.execSQL(collect_activities_result_unit)

        //ravi -offline---------table-----container

        val container = ("CREATE TABLE " + TABLE_container + " ("
                + COL_container_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_container_SERVERID + " TEXT," +
                COL_container_NAME + " TEXT," +
                COL_container_DESCIPTION + " TEXT," +
                COL_container_COM_GROUP + " TEXT," +
                COL_container_TYPE + " TEXT," +
                COL_container_STATUS + " TEXT," +
                COL_container_MAX_CAPACITY + " TEXT," +
                COL_container_CAPACITY_UNITS + " TEXT," +
                COL_container_ZONE + " TEXT," +
                COL_container_CLASS + " TEXT," +
                COL_container_NOTIFICATION_LEVEL + " TEXT," +
                COL_container_PARENT_CONTAINER + " TEXT," +
                COL_container_DELETED_AT + " TEXT," +
                COL_container_CREATED_DATE + " TEXT," +
                COL_container_CREATED_BY + " TEXT," +
                COL_container_CREATED_DATE_UTC + " TEXT," +
                COL_container_LAST_CHANGED_DATE + " TEXT," +
                COL_container_LAST_CHANGED_BY + " TEXT," +
                COL_container_LAST_CHANGED_UTC + " TEXT" + ")")
        db?.execSQL(container)
        //ravi -offline---------table-----sensors

        val sensors = ("CREATE TABLE " + TABLE_sensors + " ("
                + COL_sensors_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_sensors_SERVERID + " TEXT," +
                COL_sensors_TYPEID + " TEXT," +
                COL_sensors_NAME + " TEXT," +
                COL_sensors_SENSORID + " TEXT," +
                COL_sensors_MODEL + " TEXT," +
                COL_sensors_BRAND + " TEXT," +
                COL_sensors_SENSORIP + " TEXT," +
                COL_sensors_OWNER + " TEXT," +
                COL_sensors_USERID + " TEXT," +
                COL_sensors_UNITID + " TEXT," +
                COL_sensors_MINIMUM + " TEXT," +
                COL_sensors_MAXIMUM + " TEXT," +
                COL_sensors_COM_GROUP + " TEXT," +
                COL_sensors_CONNECTEDBOARD + " TEXT," +
                COL_sensors_CONTAINERID + " TEXT," +
                COL_sensors_CREATEDBY + " TEXT," +
                COL_sensors_UPDATEDBY + " TEXT," +
                COL_sensors_DELETEDBY + " TEXT," +
                COL_sensors_CREATEDAT + " TEXT," +
                COL_sensors_UPDATEDAT + " TEXT," +
                COL_sensors_DELETEDAT + " TEXT" + ")")
        db?.execSQL(sensors)

        //ravi -offline---------table-----units
        val units = ("CREATE TABLE " + TABLE_units + " ("
                + COL_units_PRIMARYID + " INTEGER PRIMARY KEY, " +
                COL_units_SERVERID + " TEXT," +
                COL_units_NAME + " TEXT," +
                COL_units_DESCRIPTION + " TEXT," +
                COL_units_COM_GROUP + " TEXT," +
                COL_units_CREATED_BY + " TEXT," +
                COL_units_UPDATED_BY + " TEXT," +
                COL_units_DELETED_BY + " TEXT," +
                COL_units_CREATED_AT + " TEXT," +
                COL_units_UPDATED_AT + " TEXT," +
                COL_units_DELETED_AT + " TEXT" + ")")
        db?.execSQL(units)

        //ravi -offline---------table-----container_object

        val container_object = ("CREATE TABLE " + TABLE_container_object + " ("
                + COL_container_object_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_container_object_SERVERID + " TEXT," +
                COL_container_object_OBJECTNAME + " TEXT," +
                COL_container_object_CONTAINERNO + " TEXT," +
                COL_container_object_OBJECT_NO + " TEXT," +
                COL_container_object_TYPE + " TEXT," +
                COL_container_object_CLASS + " TEXT," +
                COL_container_object_SESSIN_ID + " TEXT," +
                COL_container_object_ADDED_DATE + " TEXT," +
                COL_container_object_ADDED_BY + " TEXT," +
                COL_container_object_ADDED_UTC + " TEXT," +
                COL_container_object_DELETED_AT + " TEXT" + ")")
        db?.execSQL(container_object)

        //ravi -offline---------table-----events

        val events = ("CREATE TABLE " + TABLE_events + " ("
                + COL_events_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_events_SERVERID + " TEXT," +
                COL_events_NAME + " TEXT," +
                COL_events_DESCIPTION + " TEXT," +
                COL_events_TYPE + " TEXT," +
                COL_events_EXP_STR_DATE + " TEXT," +
                COL_events_EXP_END_DATE + " TEXT," +
                COL_events_EXP_DURATION + " TEXT," +
                COL_events_ACTUAL_STR_DATE + " TEXT," +
                COL_events_ACTUAL_END_DATE + " TEXT," +
                COL_events_ACTUAL_DURATION + " TEXT," +
                COL_events_CLOSED + " TEXT," +
                COL_events_CLOSED_DATE + " TEXT," +
                COL_events_CLOSED_BY + " TEXT," +
                COL_events_COM_GROUP + " TEXT," +
                COL_events_RESPONSIBLE + " TEXT," +
                COL_events_STATUS + " TEXT," +
                COL_events_ASSIGN_TEAM + " TEXT," +
                COL_events_TASK_ID + " TEXT," +
                COL_events_CREATED_BY + " TEXT," +
                COL_events_CREATED_DATE + " TEXT," +
                COL_events_LAST_CHANGED_BY + " TEXT," +
                COL_events_LAST_CHANGED_DATE + " TEXT," +
                COL_events_DELETED_AT + " TEXT" + ")")
        db?.execSQL(events)

        //ravi -offline---------table-----fields

        val fields = ("CREATE TABLE " + TABLE_fields + " ("
                + COL_fields_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_fields_SERVERID + " TEXT," +
                COL_fields_NAME + " TEXT," +
                COL_fields_DESCIPTION + " TEXT," +
                COL_fields_COUNTRY + " TEXT," +
                COL_fields_REGION + " TEXT," +
                COL_fields_LOCALITY + " TEXT," +
                COL_fields_SURFACE_AREA + " TEXT," +
                COL_fields_AREA_UNIT + " TEXT," +
                COL_fields_NUMBER_OF_PLANT + " TEXT," +
                COL_fields_MAIN_CULTURE + " TEXT," +
                COL_fields_OTHER_CULTURE + " TEXT," +
                COL_fields_COMMUNITY_GROUP + " TEXT," +
                COL_fields_PLANT_TYPE + " TEXT," +
                COL_fields_SOIL_TYPE + " TEXT," +
                COL_fields_VEGETATION + " TEXT," +
                COL_fields_CLIMATE + " TEXT," +
                COL_fields_ALTITUDE + " TEXT," +
                COL_fields_ALTITUDE_UNIT + " TEXT," +
                COL_fields_TEMPERATURE + " TEXT," +
                COL_fields_TEMP_UNIT + " TEXT," +
                COL_fields_HUMIDITY + " TEXT," +
                COL_fields_HUMIDITY_UNIT + " TEXT," +
                COL_fields_PLUVIOMETRY + " TEXT," +
                COL_fields_PLUVIOMETRY_UNIT + " TEXT," +
                COL_fields_HARVEST_PERIOD + " TEXT," +
                COL_fields_FIELD_CLASS + " TEXT," +
                COL_fields_FIELD_TYPE + " TEXT," +
                COL_fields_FIELD_BOUNDARY + " TEXT," +
                COL_fields_LATITUDE + " TEXT," +
                COL_fields_LONGITUDE + " TEXT," +
                COL_fields_FIELD_CONTACT + " TEXT," +
                COL_fields_UNIT_ID + " TEXT," +
                COL_fields_LAST_VISITED_BY + " TEXT," +
                COL_fields_LIST_ID + " TEXT," +
                COL_fields_TEAM_ID + " TEXT," +
                COL_fields_LAST_VISITED_DATE + " TEXT," +
                COL_fields_LAST_VISITED_UTC + " TEXT," +
                COL_fields_CREATED_BY + " TEXT," +
                COL_fields_CREATED_AT + " TEXT," +
                COL_fields_UPDATED_AT + " TEXT," +
                COL_fields_UPDATED_BY + " TEXT," +
                COL_fields_DELETED_BY + " TEXT," +
                COL_fields_DELETED_AT + " TEXT" + ")")
        db?.execSQL(fields)

        //ravi -offline---------table-----graph_charts

        val graph_charts = ("CREATE TABLE " + TABLE_graph_charts + " ("
                + COL_graph_charts_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_graph_charts_SERVERID + " TEXT," +
                COL_graph_charts_NAME + " TEXT," +
                COL_graph_charts_DESCIPTION + " TEXT," +
                COL_graph_charts_OBJECTCLASS + " TEXT," +
                COL_graph_charts_COM_GROUP + " TEXT," +
                COL_graph_charts_TITLE + " TEXT," +
                COL_graph_charts_ABCISSA_TITLE + " TEXT," +
                COL_graph_charts_ORDINATE_TITLE + " TEXT," +
                COL_graph_charts_CREATED_DATE + " TEXT," +
                COL_graph_charts_CREATED_BY + " TEXT," +
                COL_graph_charts_LASTCHANGED_BY + " TEXT," +
                COL_graph_charts_LASTCHANGED_DATE + " TEXT," +
                COL_graph_charts_DELETED_AT + " TEXT" + ")")
        db?.execSQL(graph_charts)
        //ravi -offline---------table-----graph_chart_objects

        val graph_chart_objects = ("CREATE TABLE " + TABLE_graph_chart_objects + " ("
                + COL_graph_chart_objects_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_graph_chart_objects_SERVERID + " TEXT," +
                COL_graph_chart_objects_CHARTID + " TEXT," +
                COL_graph_chart_objects_NAME + " TEXT," +
                COL_graph_chart_objects_LINETYPE + " TEXT," +
                COL_graph_chart_objects_GRAPH_CHARTID + " TEXT," +
                COL_graph_chart_objects_RESULT_CLASS + " TEXT," +
                COL_graph_chart_objects_REF_CLTL_POINT + " TEXT," +
                COL_graph_chart_objects_CREATED_BY + " TEXT," +
                COL_graph_chart_objects_CREATED_DATE + " TEXT," +
                COL_graph_chart_objects_LAST_CHANGED_BY + " TEXT," +
                COL_graph_chart_objects_LAST_CHANGED_DATE + " TEXT," +
                COL_graph_chart_objects_DELETED_AT + " TEXT" + ")")
        db?.execSQL(graph_chart_objects)

        //ravi -offline---------table-----graph_chart_points

        val graph_chart_points = ("CREATE TABLE " + TABLE_graph_chart_points + " ("
                + COL_graph_chart_points_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_graph_chart_points_SERVERID + " TEXT," +
                COL_graph_chart_points_PACKID + " TEXT," +
                COL_graph_chart_points_VALUE + " TEXT," +
                COL_graph_chart_points_CreateAt + " TEXT," +
                COL_graph_chart_points_CHARTID + " TEXT," +
                COL_graph_chart_points_DURATION + " TEXT" + ")")
        db?.execSQL(graph_chart_points)

        //ravi -offline---------table-----lists

        val lists = ("CREATE TABLE " + TABLE_lists + " ("
                + COL_lists_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_lists_SERVERID + " TEXT," +
                COL_lists_NAME + " TEXT," +
                COL_lists_COM_GROUP_ID + " TEXT," +
                COL_lists_DESC + " TEXT," +
                COL_lists_COM_GROUP + " TEXT," +
                COL_lists_CREATED_BY + " TEXT," +
                COL_lists_UPDATED_BY + " TEXT," +
                COL_lists_DELETED_BY + " TEXT," +
                COL_lists_CREATED_AT + " TEXT," +
                COL_lists_UPDATED_AT + " TEXT," +
                COL_lists_DELETED_AT + " TEXT" + ")")
        db?.execSQL(lists)

        //ravi -offline---------table-----list_choices

        val list_choices = ("CREATE TABLE " + TABLE_list_choices + " ("
                + COL_list_choices_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COL_list_choices_SERVERID + " TEXT," +
                COL_list_choices_LISTID + " TEXT," +
                COL_list_choices_CHOICE + " TEXT," +
                COL_list_choices_NAME + " TEXT," +
                COL_list_choices_CHOICE_COM_GROUP + " TEXT," +
                COL_list_choices_COM_GROUP_ID + " TEXT," +
                COL_list_choices_CREATED_AT + " TEXT," +
                COL_list_choices_UPDATED_AT + " TEXT," +
                COL_list_choices_DELETED_AT + " TEXT" + ")")
        db?.execSQL(list_choices)

        //ravi -offline---------table-----team

        val team = ("CREATE TABLE " + TABLE_team + " ("
                + COL_team_PRIMARYID + " INTEGER PRIMARY KEY, " +
                COL_team_SERVERID + " TEXT," +
                COL_team_NAME + " TEXT," +
                COL_team_DESC + " TEXT," +
                COL_team_EMAIL + " TEXT," +
                COL_team_CONTACT + " TEXT," +
                COL_team_ADDRESS + " TEXT," +
                COL_team_TEAMCLASS + " TEXT," +
                COL_team_TEAMTYPE + " TEXT," +
                COL_team_RESPONSIBLE + " TEXT," +
                COL_team_COMGROUP + " TEXT," +
                COL_team_LOGO + " TEXT," +
                COL_team_CREATEDBY + " TEXT," +
                COL_team_CREATED_AT + " TEXT," +
                COL_team_UPDATED_BY + " TEXT," +
                COL_team_UPDATED_AT + " TEXT," +
                COL_team_DELETED_BY + " TEXT," +
                COL_team_DELETED_AT + " TEXT" + ")")
        db?.execSQL(team)


//        val collectdata = ("CREATE TABLE " + COLLECTDATA_TABLENAME + " ("
//                + COLLECTDATA_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
//                COLLECTDATA_packid + " TEXT," +
//                COLLECTDATA_resultid + " TEXT," +
//                COLLECTDATA_result_class + " TEXT," +
//                COLLECTDATA_collect_activity_id + " TEXT," +
//                COLLECTDATA_new_value + " TEXT," +
//                COLLECTDATA_value + " TEXT," +
//                COLLECTDATA_unit_id + " TEXT," +
//                COLLECTDATA_sensor_id + " TEXT," +
//                COLLECTDATA_duration + " TEXT," +
//                COLLECTDATA_updated_by + " TEXT," +
//                COLLECTDATA_created_by + " TEXT," +
//                COLLECTDATA_deleted_by + " TEXT," +
//                COLLECTDATA_created_at + " TEXT," +
//                COLLECTDATA_deleted_at + " TEXT," +
//                COLLECTDATA_updated_at + " TEXT" + ")")
//        db?.execSQL(collectdata)

//        val collect_activity_results = ("CREATE TABLE " + colctactyrslt_TABLE + " ("
//                + colctactyrslt_Key + " INTEGER PRIMARY KEY, " +
//                colctactyrslt_collectactiivtid + " TEXT," +
//                colctactyrslt_result_name + " TEXT," +
//                colctactyrslt_unit_id + " TEXT," +
//                colctactyrslt_type_id + " TEXT," +
//                colctactyrslt_list_id + " TEXT," +
//                colctactyrslt_result_class + " TEXT," +
//                colctactyrslt_created_by + " TEXT," +
//                colctactyrslt_deleted_by + " TEXT," +
//                colctactyrslt_updated_at + " TEXT," +
//                colctactyrslt_deleted_at + " TEXT" + ")")
//        db?.execSQL(collect_activity_results)


//        val userCollectdata = ("CREATE TABLE " + CONST_TABLE_COLLECT + " ("
//                + CONST_COLLECT_ID + " INTEGER PRIMARY KEY, " +
//                CONST_ACTIVITY + " TEXT," +
//                CONST_RESULT + " TEXT," +
//                CONST_VALUE + " TEXT," +
//                CONST_UNITS + " TEXT," +
//                CONST_SENSOR + " TEXT," +
//                CONST_DURATION + " TEXT" + ")")
//        db?.execSQL(userCollectdata)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + CONST_USERS_TABLE)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getLastValue_pack_new(configid: String): String {

        var myid = ""
        val list: ArrayList<PackCommunityList> = ArrayList()
        val db = this.readableDatabase

        val selectQuery = "SELECT  * FROM $TABLE_CREAT_PACK WHERE $COL_PACK_CONFIG_ID =$configid "

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(selectQuery)
            return myid
        }
        if (cursor.moveToFirst()) {
            do {
                val result = cursor.getInt(0)
                AppUtils.logDebug(TAG, "resultt" + result)
                myid = cursor.getString(cursor.getColumnIndex(COL_PACK_NAME))
            } while (cursor.moveToNext())
        }
        return myid

    }

    @SuppressLint("Range")
    fun getLastofPackNew(): String {

        var myid = ""
        val db = this.readableDatabase

        val selectQuery = "SELECT  * FROM $TABLE_CREAT_PACK"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(selectQuery)
            return myid
        }
        if (cursor.moveToFirst()) {
            do {
                val result = cursor.getInt(0)
                AppUtils.logDebug(TAG, "resultt" + result)
                myid = cursor.getString(cursor.getColumnIndex(COL_LOCAL_ID))
            } while (cursor.moveToNext())
        }
        return myid

    }

    @SuppressLint("Range")
    fun getLastValue_task_new(configid: String): String {

        var myid = ""
        val db = this.readableDatabase

        val selectQuery = "SELECT  * FROM $TABLE_tasks WHERE $COL_tasks_TASK_CONFIGID =$configid "

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(selectQuery)
            return myid
        }
        if (cursor.moveToFirst()) {
            do {
                val result = cursor.getInt(0)
                AppUtils.logDebug(TAG, "resultt" + result)
                myid = cursor.getString(cursor.getColumnIndex(COL_tasks_NAME))
            } while (cursor.moveToNext())
        }
        return myid
    }


//    fun addUser() {
//        val values = ContentValues()
//        values.put(CONST_USERNAME, "pbt@admin")
//        values.put(CONST_USERROLE, "admin")
//        values.put(CONST_USERPASS, "pbt@admin")
//        val db = this.writableDatabase
//        db.insert(CONST_USERS_TABLE, null, values)
//        db.close()
//
//    }

//    fun addTask(username: String, viewtask: ViewTaskModelClass) {
//
//        try {
//            val values = ContentValues()
//            values.put(CONST_USERNAME, username)
//            values.put(CONST_TASK_NAME, viewtask.ENTRYNAME)
//            values.put(CONST_TASK_TYPE, viewtask.ENTRYTYPE)
//            values.put(CONST_EXPECTED_EXP_STR_DATE, viewtask.ExpectedStartDate)
//            values.put(CONST_EXPECTED_EXP_END_DATE, viewtask.ExpectedEndDate)
//            values.put(CONST_EXPECTED_STR_DATE, viewtask.StartDate)
//            values.put(CONST_EXPECTED_END_DATE, viewtask.EndDate)
//            values.put(CONST_TASK_DETAIL, viewtask.ENTRYDETAIL)
//            val db = this.writableDatabase
//            val result = db.insert(CONST_NEW_TASK, null, values)
//            db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, ViewTaskActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
//                CreateTaskActivity().finish()
//
//
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//
//            }
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//        }
//
//
//    }


    //    fun addNewPack(viewtask: ViewPackModelClass, created_at: String, deleted_at: String,
//        updated_at: String
//    ) {
    fun addNewPack(pack: PacksNew, status: String) {
        AppUtils.logDebug(TAG, "viewTask====" + Gson().toJson(pack))

        try {

            val i = checkEntry(pack.id, TABLE_CREAT_PACK, COL_ID)
            if (i < 1) {
                val values = ContentValues()
                values.put(COL_ID, pack.id)
                values.put(COL_PACK_NAME, pack.name)
                values.put(COL_PACK_CONFIG_ID, pack.pack_config_id)
                values.put(COL_PACK_CREATED_BY, pack.created_by)
                values.put(COL_PACK_NEW_DLETED_AT, pack.deleted_at)
                values.put(COL_PACK_LAST_CHANGED_BY, pack.last_changed_by)
                values.put(COL_PACK_DESC, pack.description)
                values.put(COL_PACK_GROUP, pack.com_group)
                values.put(COL_PACKNEW_Status, status)
                values.put(COL_PACK_IS_ACTIVE, pack.is_active)
                values.put(COL_PACK_CREATED_DATE, pack.created_date)
                values.put(COL_PACK_LAST_CHANGED_DATE, pack.last_changed_date)

                val db = this.writableDatabase
                val result = db.insert(TABLE_CREAT_PACK, null, values)
                db.close()
                //    if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
                //  } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    fun addPackValues(fieldid: String, fieldname: String, lastValueOfPacknew: String?): Boolean {

        var checkDataSaved = false
        try {

//            val i=checkEntry(pack.id,TABLE_CREAT_PACK,COL_ID)
//            if (i<1){


            val values = ContentValues()
            values.put(COL_pack_fields_pack_id, lastValueOfPacknew)
            values.put(COL_pack_fields_value, fieldname)
            values.put(COL_pack_fields_field_id, fieldid)

            val db = this.writableDatabase
            val result = db.insert(TABLE_pack_fields, null, values)
            db.close()
            if (result >= 0) {
                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
                checkDataSaved = true
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                checkDataSaved = false
            }


        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
        return checkDataSaved
    }

    //ravi - offline ----------insert
    fun pack_configscreate(pack: PackConfig) {
        AppUtils.logDebug(TAG, "viewTask====" + Gson().toJson(pack))

        try {
            val i = checkEntry(pack.id, TABLE_PACKCONFIG, COL_pack_configs_SERVER_ID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_pack_configs_SERVER_ID, pack.id)
                values.put(COL_pack_configs_NAME, pack.name)
                values.put(COL_pack_configs_DESCIPTION, pack.description)
                values.put(COL_pack_configs_TYPE, pack.type)
                values.put(COL_pack_configs_CLASS, pack.mclass)
                values.put(COL_pack_configs_COMGROUP, pack.com_group)
                values.put(COL_pack_configs_NAMEPREFIX, pack.name_prefix)
                values.put(COL_pack_configs_COLLECTACTIVITY_ID, pack.collect_activity_id)
                values.put(COL_pack_configs_GRAPHCHCHART_ID, pack.graph_chart_id)
                values.put(COL_pack_configs_CREATEDBY, pack.created_by)
                values.put(COL_pack_configs_CREATED_DATE, pack.created_date)
                values.put(COL_pack_configs_LAST_CHANGED_BY, pack.last_changed_by)
                values.put(COL_pack_configs_LAST_CHNAGED_DATE, pack.last_changed_date)
                values.put(COL_pack_configs_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_PACKCONFIG, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline -----insert-----task_media_files


    fun taskMediaFilescCreate(pack: TaskMediaFile) {
        AppUtils.logDebug(TAG, "viewTask====" + Gson().toJson(pack))

        try {
            val i = checkEntry(pack.id, TABLE_task_media_files, COL_task_media_files_SERVERID)
            if (i < 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    donwloadMediaFiles(pack)
                }


                val values = ContentValues()
               AppUtils.logDebug(TAG,"Local Path of imahe"+localFilePath.toString())

                values.put(COL_task_media_files_SERVERID, pack.id)
                values.put(COL_task_media_files_TASKID, pack.task_id)
                values.put(COL_task_media_files_NAME, pack.name)
                values.put(COL_task_media_files_LOCAL_FILE_PATH,"/storage/emulated/0/MyFarm/"+pack.name)
                values.put(COL_task_media_files_LINK, pack.link)
                localFilePath=""


                val db = this.writableDatabase
                db.insert(TABLE_task_media_files, null, values)
                db.close()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }


    @SuppressLint("SdCardPath")
    private fun donwloadMediaFiles(pack: TaskMediaFile) {

        val url = pack.link
        val filename = pack.name

        //                    progressBar.setTag(position);
        DownloadFileFromURL(filename).execute(url)


//        var manager: DownloadManager? = null
//            manager = context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
//            val uri = Uri.parse(url)
//            val request = DownloadManager.Request(uri)
//
//            request.setTitle(filename)
//            request.setDescription("Downloading ")
//            request.setAllowedOverRoaming(false)
////            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
////            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//
//
////                File storage = new File(Environment.getExternalStorageDirectory() + File.separator + "/Download/");
//        val storage =
//            File(Environment.getExternalStorageDirectory().toString() + File.separator + "/MyFarm/")
//
//        if (!storage.exists()) {
//            storage.mkdirs()
//        }
//
//        Log.e("check_path", "" + storage.absolutePath)
//        try{
//            request.setDestinationInExternalPublicDir(storage.toString(),filename)
//
//        }
//        catch (e:Exception){
//            AppUtils.logError(TAG, e.message.toString())
//        }
//
////
////            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
////    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//            val reference: Long = manager.enqueue(request)


    }


    //ravi - offline ----------insert--table---pack_collect_activity

    fun pack_collect_activity_create(pack: PackCollectActivity) {

        try {
            val i = checkEntry(
                pack.id,
                TABLE_pack_collect_activity,
                COL_pack_collect_activity_SERVER_ID
            )
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_pack_collect_activity_SERVER_ID, pack.id)
                values.put(COL_pack_collect_activity_collect_activity_id, pack.collect_activity_id)
                values.put(COL_pack_collect_activity_pack_id, pack.pack_id)

                val db = this.writableDatabase
                db.insert(TABLE_pack_collect_activity, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---pack_fields

    fun pack_fields_create(pack: PackField) {

        try {
            val i = checkEntry(pack.id, TABLE_pack_fields, COL_pack_fields_SERVERID)
            if (i < 1) {


                val values = ContentValues()
                values.put(COL_pack_fields_pack_id, pack.pack_id)
                values.put(COL_pack_fields_value, pack.value)
                values.put(COL_pack_fields_SERVERID, pack.id)
                values.put(COL_pack_fields_field_id, pack.field_id)

                val db = this.writableDatabase
                db.insert(TABLE_pack_fields, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi - offline ----------insert--table---pack_config_fields

    fun pack_config_fields_create(pack: PackConfigField) {

        try {
            val i = checkEntry(pack.id, TABLE_pack_config_fields, COL_pack_config_fields_SERVER_ID)
            if (i < 1) {


                val values = ContentValues()
                values.put(COL_pack_config_fields_SERVER_ID, pack.id)
                values.put(COL_pack_config_fields_pack_config_id, pack.pack_config_id)
                values.put(COL_pack_config_fields_field_name, pack.field_name)
                values.put(COL_pack_config_fields_field_description, pack.field_description)
                values.put(COL_pack_config_fields_field_type, pack.field_type)
                values.put(COL_pack_config_fields_default_value, pack.default_value)
                values.put(COL_pack_config_fields_created_by, pack.created_by)
                values.put(COL_pack_config_fields_last_changed_by, pack.last_changed_by)
                values.put(COL_pack_config_fields_last_changed_date, pack.last_changed_date)
                values.put(COL_pack_config_fields_created_date, pack.created_by)
                values.put(COL_pack_config_fields_editable, pack.editable)
                values.put(COL_pack_config_fields_list, pack.list)
                values.put(COL_pack_config_fields_deleted_at, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_pack_config_fields, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---collectdata


    fun collectDataCreate(pack: CollectData) {

        try {
            val i = checkEntry(pack.id?.toInt(), TABLE_collect_data, COL_collect_data_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_collect_data_SERVERID, pack.id)
                values.put(COL_collect_data_pack_id, pack.pack_id)
                values.put(COL_collect_data_ResulId, pack.result_id)
                values.put(COL_collect_data_ResultClass, pack.result_class)
                values.put(COL_collect_data_CollectActivityId, pack.collect_activity_id)
                values.put(COL_collect_data_NEWVALUE, pack.new_value)
                values.put(COL_collect_data_UNITID, pack.unit_id)
                values.put(COL_collect_data_SENSORID, pack.sensor_id)
                values.put(COL_collect_data_DURATION, pack.duration)
                values.put(COL_collect_data_UPDATED_BY, pack.created_by)
                values.put(COL_collect_data_DELETED_BY, pack.deleted_by)
                values.put(COL_collect_data_CREATED_AT, pack.created_at)
                values.put(COL_collect_data_UPDATED_AT, pack.updated_at)
                values.put(COL_collect_data_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_collect_data, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---collect_activities

    fun collectActivitiesCreate(pack: CollectActivity) {

        try {
            val i = checkEntry(pack.id, TABLE_collect_activities, COL_collect_activities_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_collect_activities_SERVERID, pack.id)
                values.put(COL_collect_activities_NAME, pack.name)
                values.put(COL_collect_activities_COMMUNITYGROUP, pack.communitygroup)
                values.put(COL_collect_activities_CREATED_BY, pack.created_by)
                values.put(COL_collect_activities_UPDATED_BY, pack.updated_by)
                values.put(COL_collect_activities_DELTED_BY, pack.deleted_by)
                values.put(COL_collect_activities_CREATED_AT, pack.created_at)
                values.put(COL_collect_activities_UPDATED_AT, pack.updated_at)
                values.put(COL_collect_activities_DELTED_AT, pack.deleted_at)

                val db = this.writableDatabase
                db.insert(TABLE_collect_activities, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi - offline ----------insert--table---collect_activity_results

    fun collectActivitiesResultsCreate(pack: CollectActivityResult) {

        try {
            val i = checkEntry(
                pack.id,
                TABLE_collect_activity_results,
                COL_collect_activity_results_SERVERID
            )
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_collect_activity_results_SERVERID, pack.id)
                values.put(
                    COL_collect_activity_results_COLLECT_ACTIVITY_ID,
                    pack.collect_activity_id
                )
                values.put(COL_collect_activity_results_RESULTNAME, pack.result_name)
                values.put(COL_collect_activity_results_UNITID, pack.unit_id)
                values.put(COL_collect_activity_results_TYPEID, pack.type_id)
                values.put(COL_collect_activity_results_LISTID, pack.list_id)
                values.put(COL_collect_activity_results_RESULTCLASS, pack.result_class)
                values.put(COL_collect_activity_results_CREATEDBY, pack.created_by)
                values.put(COL_collect_activity_results_UPDATEDBY, pack.updated_by)
                values.put(COL_collect_activity_results_DELETEDBY, pack.deleted_by)
                values.put(COL_collect_activity_results_CREATEDAT, pack.created_at)
                values.put(COL_collect_activity_results_UPDATEDAT, pack.updated_at)
                values.put(COL_collect_activity_results_DELETEAT, pack.deleted_at)

                val db = this.writableDatabase
                db.insert(TABLE_collect_activity_results, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---collect_activities_result_unit

    fun collectActivitiesResultsUnitCreate(pack: CollectActivityResultUnit) {

        try {
            val i = checkEntry(
                pack.id,
                TABLE_collect_activity_results_unit,
                COL_collect_activity_results_unit_SERVERID
            )
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_collect_activity_results_unit_SERVERID, pack.id)
                values.put(
                    COL_collect_activity_results_unit_COLLECT_ACITIVITY_RESULT_ID,
                    pack.collect_activity_result_id
                )
                values.put(COL_collect_activity_results_unit_UNITID, pack.unit_id)

                val db = this.writableDatabase
                db.insert(TABLE_collect_activity_results_unit, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi - offline ----------insert--table---people

    fun peopleCreate(pack: People) {

        try {
            val i = checkEntry(pack.id, TABLE_people, COL_people_SERVERID)
            if (i < 1) {


                val values = ContentValues()
                values.put(COL_people_SERVERID, pack.id)
                values.put(COL_people_FNAME, pack.fname)
                values.put(COL_people_LNAME, pack.lname)
                values.put(COL_people_EMAIl, pack.email)
                values.put(COL_people_CONTACT, pack.contact)
                values.put(COL_people_BIRTHPLACE, pack.birth_place)
                values.put(COL_people_DOB, pack.dob)
                values.put(COL_people_PHOTO, pack.photo)
                values.put(COL_people_ADDRESS, pack.address)
                values.put(COL_people_CITIZENSHIP, pack.citizenship)
                values.put(COL_people_CERTIFICAITON, pack.certification)
                values.put(COL_people_IS_IN_COOP, pack.is_in_coop)
                values.put(COL_people_LASTCERFICATION_DATE, pack.last_certification_date)
                values.put(COL_people_IS_KAKAOMUNDO, pack.is_kakaomundo)
                values.put(COL_people_KAKAOMUNDO_CENTER, pack.kakaomundo_center)
                values.put(COL_people_USERID, pack.user_id)
                values.put(COL_people_COMMUNITY_GROUP, pack.communitygroup)
                values.put(COL_people_PERSON_CLASS, pack.person_class)
                values.put(COL_people_PERSON_TYPE, pack.person_type)
                values.put(COL_people_DEWSCIPTION, pack.description)
                values.put(COL_people_CREATED_BY, pack.created_by)
                values.put(COL_people_UPDATED_BY, pack.updated_by)
                values.put(COL_people_UPDATED_AT, pack.updated_at)
                values.put(COL_people_CREATED_AT, pack.created_at)
                values.put(COL_people_DELETED_BY, pack.deleted_by)
                values.put(COL_people_DELETED_AT, pack.deleted_at)

                val db = this.writableDatabase
                db.insert(TABLE_people, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---container

    fun containerCreate(pack: Container) {

        try {
            val i = checkEntry(pack.id, TABLE_container, COL_container_SERVERID)
            if (i < 1) {
                val values = ContentValues()
                values.put(COL_container_SERVERID, pack.id)
                values.put(COL_container_NAME, pack.name)
                values.put(COL_container_DESCIPTION, pack.description)
                values.put(COL_container_COM_GROUP, pack.com_group)
                values.put(COL_container_TYPE, pack.type)
                values.put(COL_container_STATUS, pack.status)
                values.put(COL_container_MAX_CAPACITY, pack.max_capacity)
                values.put(COL_container_CAPACITY_UNITS, pack.capacity_units)
                values.put(COL_container_ZONE, pack.zone)
                values.put(COL_container_CLASS, pack.`class`)
                values.put(COL_container_NOTIFICATION_LEVEL, pack.notification_level)
                values.put(COL_container_PARENT_CONTAINER, pack.parent_container)
                values.put(COL_container_DELETED_AT, pack.deleted_at)
                values.put(COL_container_CREATED_DATE, pack.created_date)
                values.put(COL_container_CREATED_BY, pack.created_by)
                values.put(COL_container_CREATED_DATE_UTC, pack.created_date_utc)
                values.put(COL_container_LAST_CHANGED_DATE, pack.last_changed_date)
                values.put(COL_container_LAST_CHANGED_BY, pack.last_changed_by)
                values.put(COL_container_LAST_CHANGED_UTC, pack.last_changed_utc)

                val db = this.writableDatabase
                db.insert(TABLE_container, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---container_object

    fun containerObjectCreate(pack: ContainerObject) {

        try {
            val i = checkEntry(pack.id, TABLE_container_object, COL_container_object_SERVERID)
            if (i < 1) {
                val values = ContentValues()
                values.put(COL_container_object_SERVERID, pack.id)
                values.put(COL_container_object_OBJECTNAME, pack.object_name)
                values.put(COL_container_object_CONTAINERNO, pack.container_no)
                values.put(COL_container_object_OBJECT_NO, pack.object_no)
                values.put(COL_container_object_TYPE, pack.type)
                values.put(COL_container_object_CLASS, pack.`class`)
                values.put(COL_container_object_SESSIN_ID, pack.session_id)
                values.put(COL_container_object_ADDED_DATE, pack.added_date)
                values.put(COL_container_object_ADDED_BY, pack.added_by)
                values.put(COL_container_object_ADDED_UTC, pack.added_utc)
                values.put(COL_container_object_DELETED_AT, pack.deleted_at)

                val db = this.writableDatabase
                db.insert(TABLE_container_object, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi-offline-----table----insert--community_groups


    fun communityGroupsCreate(pack: CommunityGroup) {

        try {
            val i = checkEntry(pack.id, TABLE_community_groups, COL_community_groups_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_community_groups_SERVERID, pack.id)
                values.put(COL_community_groups_NAME, pack.name)
                values.put(COL_community_groups_DESCIPRTION, pack.description)
                values.put(COL_community_groups_COMM_GROUP, pack.community_group)
                values.put(COL_community_groups_CREATED_BY, pack.created_by)
                values.put(COL_community_groups_DELTED_BY, pack.deleted_by)
                values.put(COL_community_groups_UPDATED_BY, pack.updated_by)
                values.put(COL_community_groups_UPDATED_AT, pack.updated_at)
                values.put(COL_community_groups_CREATED_AT, pack.created_at)
                values.put(COL_community_groups_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_community_groups, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---task_configs


    fun task_configs_create(pack: TaskConfig) {

        try {
            val i = checkEntry(pack.id, TABLE_task_configs, COL_task_configs_SERVERID)
            if (i < 1) {
                val values = ContentValues()
                values.put(COL_task_configs_SERVERID, pack.id)
                values.put(COL_task_configs_NAME, pack.name)
                values.put(COL_task_configs_DESCIPTION, pack.description)
                values.put(COL_task_configs_TYPE, pack.type)
                values.put(COL_task_configs_CLASS, pack.nclass)
                values.put(COL_task_configs_COM_GROUP, pack.com_group)
                values.put(COL_task_configs_NAME_PREFIX, pack.name_prefix)
                values.put(COL_task_configs_RECORD_EVENT, pack.record_event)
                values.put(COL_task_configs_REPORTABLE, pack.reportable)
                values.put(COL_task_configs_CREATED_BY, pack.created_by)
                values.put(COL_task_configs_CREATED_DATE, pack.created_date)
                values.put(COL_task_configs_LAST_CHANGED_BY, pack.last_changed_by)
                values.put(COL_task_configs_LAST_CHANGED_DATE, pack.last_changed_date)
                values.put(COL_task_configs__LAST_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_task_configs, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---task_config_fields


    fun task_config_fields_create(pack: TaskConfigField) {

        try {
            val i = checkEntry(pack.id, TABLE_task_config_fields, COL_task_config_fields_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_task_config_fields_SERVERID, pack.id)
                values.put(COL_task_config_fields_task_config_id, pack.task_config_id)
                values.put(COL_task_config_fields_field_name, pack.field_name)
                values.put(COL_task_config_fields_field_description, pack.field_description)
                values.put(COL_task_config_fields_editable, pack.editable)
                values.put(COL_task_config_fields_field_type, pack.field_type)
                values.put(COL_task_config_fields_list, pack.list)
                values.put(COL_task_config_fields_created_by, pack.created_by)
                values.put(COL_task_config_fields_created_date, pack.created_date)
                values.put(COL_task_config_fields_last_changed_by, pack.last_changed_by)
                values.put(COL_task_config_fields_last_changed_date, pack.last_changed_date)
                values.put(COL_task_config_fields_deleted_at, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_task_config_fields, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---lists


    fun listsCreate(pack: Lists) {

        try {
            val i = checkEntry(pack.id, TABLE_lists, COL_lists_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_lists_SERVERID, pack.id)
                values.put(COL_lists_NAME, pack.name)
                values.put(COL_lists_COM_GROUP_ID, pack.community_group_id)
                values.put(COL_lists_DESC, pack.description)
                values.put(COL_lists_COM_GROUP, pack.communitygroup)
                values.put(COL_lists_CREATED_BY, pack.created_by)
                values.put(COL_lists_UPDATED_BY, pack.updated_by)
                values.put(COL_lists_DELETED_BY, pack.deleted_by)
                values.put(COL_lists_CREATED_AT, pack.created_at)
                values.put(COL_lists_UPDATED_AT, pack.updated_at)
                values.put(COL_lists_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_lists, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi - offline ----------insert--table---list_choices

    fun listChoicesCreate(pack: Choices) {

        try {
            val i = checkEntry(pack.id, TABLE_list_choices, COL_list_choices_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_list_choices_SERVERID, pack.id)
                values.put(COL_list_choices_LISTID, pack.lists_id)
                values.put(COL_list_choices_CHOICE, pack.choice)
                values.put(COL_list_choices_NAME, pack.name)
                values.put(COL_list_choices_CHOICE_COM_GROUP, pack.choice_communitygroup)
                values.put(COL_list_choices_COM_GROUP_ID, pack.community_group_id)
                values.put(COL_list_choices_CREATED_AT, pack.created_at)
                values.put(COL_list_choices_UPDATED_AT, pack.updated_at)
                values.put(COL_list_choices_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_list_choices, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---team

    fun teamCreate(pack: Team) {

        try {
            val i = checkEntry(pack.id, TABLE_team, COL_team_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_team_SERVERID, pack.id)
                values.put(COL_team_NAME, pack.name)
                values.put(COL_team_DESC, pack.description)
                values.put(COL_team_EMAIL, pack.email)
                values.put(COL_team_CONTACT, pack.contact)
                values.put(COL_team_ADDRESS, pack.address)
                values.put(COL_team_TEAMCLASS, pack.team_class)
                values.put(COL_team_TEAMTYPE, pack.team_type)
                values.put(COL_team_RESPONSIBLE, pack.responsible)
                values.put(COL_team_COMGROUP, pack.communitygroup)
                values.put(COL_team_LOGO, pack.logo)
                values.put(COL_team_CREATEDBY, pack.created_by)
                values.put(COL_team_CREATED_AT, pack.created_at)
                values.put(COL_team_UPDATED_BY, pack.updated_by)
                values.put(COL_team_UPDATED_AT, pack.updated_at)
                values.put(COL_team_DELETED_BY, pack.deleted_by)
                values.put(COL_team_DELETED_AT, pack.deleted_at)

                val db = this.writableDatabase
                db.insert(TABLE_team, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi - offline ----------insert--table---task_config_functions


    fun task_config_functions_create(pack: TaskConfigFunction) {

        try {
            val i = checkEntry(
                pack.id,
                TABLE_task_config_functions,
                COL_task_config_functions_SERVERKEY
            )
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_task_config_functions_SERVERKEY, pack.id)
                values.put(COL_task_config_functions_task_config_id, pack.task_config_id)
                values.put(COL_task_config_functions_task_name, pack.name)
                values.put(COL_task_config_functions_description, pack.description)
                values.put(COL_task_config_functions_privilege, pack.privilege)
                values.put(COL_task_config_functions_created_by, pack.created_by)
                values.put(COL_task_config_functions_created_date, pack.created_date)
                values.put(COL_task_config_functions_last_changed_by, pack.last_changed_by)
                values.put(COL_task_config_functions_deleted_at, pack.deleted_at)
                values.put(COL_task_config_functions_last_changed_date, pack.last_changed_date)


                val db = this.writableDatabase
                val result = db.insert(TABLE_task_config_functions, null, values)
                db.close()
                if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
                } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

                }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---events

    fun eventsCreate(pack: Event) {

        try {
            val i = checkEntry(pack.id, TABLE_events, COL_events_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_events_SERVERID, pack.id)
                values.put(COL_events_NAME, pack.name)
                values.put(COL_events_DESCIPTION, pack.description)
                values.put(COL_events_TYPE, pack.type)
                values.put(COL_events_EXP_STR_DATE, pack.exp_start_date)
                values.put(COL_events_EXP_END_DATE, pack.exp_end_date)
                values.put(COL_events_EXP_DURATION, pack.exp_duration)
                values.put(COL_events_ACTUAL_STR_DATE, pack.actual_start_date)
                values.put(COL_events_ACTUAL_END_DATE, pack.actual_end_date)
                values.put(COL_events_ACTUAL_DURATION, pack.actual_duration)
                values.put(COL_events_CLOSED, pack.closed)
                values.put(COL_events_CLOSED_DATE, pack.closed_date)
                values.put(COL_events_CLOSED_BY, pack.closed_by)
                values.put(COL_events_COM_GROUP, pack.com_group)
                values.put(COL_events_RESPONSIBLE, pack.responsible)
                values.put(COL_events_STATUS, pack.status)
                values.put(COL_events_ASSIGN_TEAM, pack.assigned_team)
                values.put(COL_events_TASK_ID, pack.task_id)
                values.put(COL_events_CREATED_BY, pack.created_by)
                values.put(COL_events_CREATED_DATE, pack.created_date)
                values.put(COL_events_LAST_CHANGED_BY, pack.last_changed_by)
                values.put(COL_events_LAST_CHANGED_DATE, pack.last_changed_date)
                values.put(COL_events_DELETED_AT, pack.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_events, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }


//    fun addpackFieldValue(id: String, packid: String, value: String) {
//        try {
//
//            val values = ContentValues()
//            values.put(PACKFIELDS_fieldid, id)
//            values.put(PACKFIELDS_packid, packid)
//            values.put(PACKFIELDS_value, value)
//
//            val db = this.writableDatabase
//            val result = db.insert(PACKFIELDS_TABLENAME, null, values)
//            db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added PackFieldSuccessfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//
//            }
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//        }
//    }

    //ravi offline -----insert---table-----tasks//

    fun tasksCreate(task: Task) {

        try {
            val i = checkEntry(task.id, TABLE_tasks, COL_tasks_SERVERid)
            if (i < 1) {

                val values = ContentValues()

                values.put(COL_tasks_SERVERid, task.id)
                values.put(COL_tasks_NAME, task.name)
                values.put(COL_tasks_DESC, task.description)
                values.put(COL_tasks_GROUP, task.com_group)
                values.put(COL_tasks_TASK_CONFIGID, task.task_config_id)
                values.put(COL_tasks_TASKFUNC, task.task_func)
                values.put(COL_tasks_STATUS, task.status)
                values.put(COL_tasks_STARTED_LATE, task.started_late)
                values.put(COL_tasks_ENDED_LATE, task.ended_late)
                values.put(COL_tasks_CREATED_BY, task.created_by)
                values.put(COL_tasks_CREATED_DATE, task.created_date)
                values.put(COL_tasks_LASTCHANGED_DATE, task.last_changed_date)
                values.put(COL_tasks_LAST_CHANGED_BY, task.last_changed_by)
                values.put(COL_tasks_DELTED_AT, task.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_tasks, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added TAskSuccessfully", Toast.LENGTH_SHORT).show()


//            } else {
//                Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //offline--ravi -------insert---table------task_field
    fun task_fields_create(task: TaskField) {

        try {
            val i = checkEntry(task.id, TABLE_task_fields, COL_task_fields_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_task_fields_SERVERID, task.id)
                values.put(COL_task_fields_TASKID, task.task_id)
                values.put(COL_task_fields_FIELDID, task.field_id)
                values.put(COL_task_fields_VALUE, task.value)


                val db = this.writableDatabase
                db.insert(TABLE_task_fields, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added TaskFieldSuccessfully", Toast.LENGTH_SHORT).show()


//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //offline--ravi -------insert---table------graph_charts
    fun graphChartsCreate(task: GraphChart) {

        try {
            val i = checkEntry(task.id, TABLE_graph_charts, COL_graph_charts_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_graph_charts_SERVERID, task.id)
                values.put(COL_graph_charts_NAME, task.name)
                values.put(COL_graph_charts_DESCIPTION, task.description)
                values.put(COL_graph_charts_OBJECTCLASS, task.object_class)
                values.put(COL_graph_charts_COM_GROUP, task.com_group)
                values.put(COL_graph_charts_TITLE, task.title)
                values.put(COL_graph_charts_ABCISSA_TITLE, task.abcissa_title)
                values.put(COL_graph_charts_ORDINATE_TITLE, task.ordinate_title)
                values.put(COL_graph_charts_CREATED_DATE, task.created_date)
                values.put(COL_graph_charts_CREATED_BY, task.created_by)
                values.put(COL_graph_charts_LASTCHANGED_BY, task.last_changed_by)
                values.put(COL_graph_charts_LASTCHANGED_DATE, task.last_changed_date)
                values.put(COL_graph_charts_DELETED_AT, task.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_graph_charts, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added TaskFieldSuccessfully", Toast.LENGTH_SHORT).show()


//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //offline--ravi -------insert---table------graph_chart_objects

    fun graphChartObjectsCreate(task: GraphChartObject) {

        try {
            val i = checkEntry(task.id, TABLE_graph_chart_objects, COL_graph_chart_objects_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_graph_chart_objects_SERVERID, task.id)
//            values.put(COL_graph_chart_objects_CHARTID, task.graphs_charts_id)
                values.put(COL_graph_chart_objects_NAME, task.name)
                values.put(COL_graph_chart_objects_LINETYPE, task.line_type)
                values.put(COL_graph_chart_objects_RESULT_CLASS, task.result_class)
                values.put(COL_graph_chart_objects_REF_CLTL_POINT, task.ref_ctrl_points)
                values.put(COL_graph_chart_objects_GRAPH_CHARTID, task.graphs_charts_id)
//            values.put(COL_graph_chart_objects_CREATED_BY, task.created_by)
//            values.put(COL_graph_chart_objects_CREATED_DATE, task.created_date)
//            values.put(COL_graph_chart_objects_LAST_CHANGED_BY, task.last_changed_by)
//            values.put(COL_graph_chart_objects_LAST_CHANGED_DATE, task.last_changed_date)
//            values.put(COL_graph_chart_objects_DELETED_AT, task.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_graph_chart_objects, null, values)
                db.close()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
        if (task.points.isNotEmpty()) {
            for (z in 0 until task.points.size) {
                val item = task.points.get(z)

                try {
                    val d = checkEntry(
                        task.id,
                        TABLE_graph_chart_points,
                        COL_graph_chart_points_SERVERID
                    )
                    if (d < 1) {

                        val myvalues = ContentValues()
                        myvalues.put(COL_graph_chart_points_SERVERID, item.id)
                        myvalues.put(COL_graph_chart_points_PACKID, item.packId)
                        myvalues.put(COL_graph_chart_points_VALUE, item.value)
                        myvalues.put(COL_graph_chart_points_CHARTID, task.id)
                        myvalues.put(COL_graph_chart_points_CreateAt, item.createAt)
                        myvalues.put(COL_graph_chart_points_DURATION, item.duration)

                        val db = this.writableDatabase
                        db.insert(TABLE_graph_chart_points, null, myvalues)
                        db.close()
                    }
                } catch (e: Exception) {
                    AppUtils.logError(TAG, e.message!!)
                }
            }
        }


    }



    //offline--ravi -------insert---table------sensors

    fun sensorCreate(task: Sensor) {

        try {
            val i = checkEntry(task.id, TABLE_sensors, COL_sensors_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_sensors_SERVERID, task.id)
                values.put(COL_sensors_TYPEID, task.sensor_type_id)
                values.put(COL_sensors_NAME, task.name)
                values.put(COL_sensors_SENSORID, task.sensorId)
                values.put(COL_sensors_MODEL, task.model)
                values.put(COL_sensors_BRAND, task.brand)
                values.put(COL_sensors_SENSORIP, task.created_by)
                values.put(COL_sensors_OWNER, task.owner)
                values.put(COL_sensors_USERID, task.user_id)
                values.put(COL_sensors_UNITID, task.unit_id)
                values.put(COL_sensors_MINIMUM, task.minimum)
                values.put(COL_sensors_MAXIMUM, task.maximum)
                values.put(COL_sensors_COM_GROUP, task.community_group)
                values.put(COL_sensors_CONNECTEDBOARD, task.connected_board)
                values.put(COL_sensors_CONTAINERID, task.container_id)
                values.put(COL_sensors_CREATEDBY, task.created_by)
                values.put(COL_sensors_UPDATEDBY, task.updated_by)
                values.put(COL_sensors_DELETEDBY, task.deleted_by)
                values.put(COL_sensors_CREATEDAT, task.created_at)
                values.put(COL_sensors_UPDATEDAT, task.updated_at)
                values.put(COL_sensors_DELETEDAT, task.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_sensors, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added TaskFieldSuccessfully", Toast.LENGTH_SHORT).show()


//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //offline--ravi -------insert---table------units


    fun unitsCreate(task: Unit) {

        try {
            val i = checkEntry(task.id, TABLE_units, COL_units_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_units_SERVERID, task.id)
                values.put(COL_units_NAME, task.name)
                values.put(COL_units_DESCRIPTION, task.description)
                values.put(COL_units_COM_GROUP, task.communitygroup)
                values.put(COL_units_CREATED_BY, task.created_by)
                values.put(COL_units_UPDATED_BY, task.updated_by)
                values.put(COL_units_DELETED_BY, task.deleted_by)
                values.put(COL_units_CREATED_AT, task.created_at)
                values.put(COL_units_UPDATED_AT, task.updated_at)
                values.put(COL_units_DELETED_AT, task.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_units, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added TaskFieldSuccessfully", Toast.LENGTH_SHORT).show()


//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //offline--ravi -------insert---table------fields

    fun fieldsCreate(task: Field) {

        try {
            val i = checkEntry(task.id, TABLE_fields, COL_fields_SERVERID)
            if (i < 1) {

                val values = ContentValues()
                values.put(COL_fields_SERVERID, task.id)
                values.put(COL_fields_NAME, task.name)
                values.put(COL_fields_DESCIPTION, task.description)
                values.put(COL_fields_COUNTRY, task.country)
                values.put(COL_fields_REGION, task.region)
                values.put(COL_fields_LOCALITY, task.locality)
                values.put(COL_fields_SURFACE_AREA, task.surface_area)
                values.put(COL_fields_AREA_UNIT, task.area_unit)
                values.put(COL_fields_NUMBER_OF_PLANT, task.number_of_plant)
                values.put(COL_fields_MAIN_CULTURE, task.main_culture)
                values.put(COL_fields_OTHER_CULTURE, task.other_culture)
                values.put(COL_fields_COMMUNITY_GROUP, task.communitygroup)
                values.put(COL_fields_PLANT_TYPE, task.plant_type)
                values.put(COL_fields_SOIL_TYPE, task.soil_type)
                values.put(COL_fields_VEGETATION, task.vegetation)
                values.put(COL_fields_CLIMATE, task.climate)
                values.put(COL_fields_ALTITUDE, task.altitude)
                values.put(COL_fields_ALTITUDE_UNIT, task.altitude_unit)
                values.put(COL_fields_TEMPERATURE, task.temperature)
                values.put(COL_fields_TEMP_UNIT, task.temp_unit)
                values.put(COL_fields_HUMIDITY, task.humidity)
                values.put(COL_fields_HUMIDITY_UNIT, task.humidity_unit)
                values.put(COL_fields_PLUVIOMETRY, task.pluviometry)
                values.put(COL_fields_PLUVIOMETRY_UNIT, task.pluviometry_unit)
                values.put(COL_fields_HARVEST_PERIOD, task.harvest_period)
                values.put(COL_fields_FIELD_CLASS, task.field_class)
                values.put(COL_fields_FIELD_TYPE, task.field_type)
                values.put(COL_fields_FIELD_BOUNDARY, task.field_boundary)
                values.put(COL_fields_LATITUDE, task.latitude)
                values.put(COL_fields_LONGITUDE, task.longitude)
                values.put(COL_fields_FIELD_CONTACT, task.field_contact)
                values.put(COL_fields_UNIT_ID, task.unit_id)
                values.put(COL_fields_LAST_VISITED_BY, task.last_visited_by)
                values.put(COL_fields_LIST_ID, task.lists_id)
                values.put(COL_fields_TEAM_ID, task.team_id)
                values.put(COL_fields_LAST_VISITED_DATE, task.last_visited_date)
                values.put(COL_fields_LAST_VISITED_UTC, task.last_visited_date_utc)
                values.put(COL_fields_CREATED_BY, task.created_by)
                values.put(COL_fields_CREATED_AT, task.created_at)
                values.put(COL_fields_UPDATED_AT, task.updated_at)
                values.put(COL_fields_UPDATED_BY, task.updated_by)
                values.put(COL_fields_DELETED_BY, task.deleted_by)
                values.put(COL_fields_DELETED_AT, task.deleted_at)


                val db = this.writableDatabase
                db.insert(TABLE_fields, null, values)
                db.close()
//            if (result >= 0) {
//                Toast.makeText(context, "Added TaskFieldSuccessfully", Toast.LENGTH_SHORT).show()


//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

//            }
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

//    //    fun addCollectData(data: CollectDataModel){
////        try {
////
////            val values = ContentValues()
////            values.put(  CONST_ACTIVITY, data.activity)
////            values.put(CONST_RESULT, data.result)
////            values.put(CONST_VALUE, data.value)
////            values.put(CONST_UNITS, data.units)
////            values.put(CONST_SENSOR, data.sensor)
////            values.put(CONST_DURATION, data.duration)
////
////
////            val db = this.writableDatabase
////            val result = db.insert(CONST_TABLE_COLLECT, null, values)
////            db.close()
////            AppUtils.logDebug(TAG, result.toString())
////            if (result >= 0) {
////                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
////                val intent = Intent(context, UpdatePackActivity::class.java)
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                context.startActivity(intent)
////
////
////
////            } else {
////                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
////
////            }
////        }catch (e:Exception){
////    }}
//    @SuppressLint("Range")
////    fun readCollectData(): ArrayList<CollectDataModel> {
////        val list: ArrayList<CollectDataModel> = ArrayList()
////        val db = this.readableDatabase
////        val query = "Select * from $CONST_TABLE_COLLECT"
////        val cursor: Cursor?
////        try {
////            cursor = db.rawQuery(query, null)
////        } catch (e: Exception) {
////            AppUtils.logError(TAG, e.message!!)
////            db.execSQL(query)
////            return ArrayList()
////        }
////        var activity: String
////        var result: String
////        var value: String
////
////        var units: String
////        var sensor: String
////        var duration: String
////
////
////
////        if (cursor.moveToFirst()) {
////            do {
////                activity = cursor.getString(cursor.getColumnIndex(CONST_ACTIVITY))
////                result = cursor.getString(cursor.getColumnIndex(CONST_RESULT))
////                value= cursor.getString(cursor.getColumnIndex(CONST_VALUE))
////                units=cursor.getString(cursor.getColumnIndex(CONST_UNITS))
////                sensor=cursor.getString(cursor.getColumnIndex(CONST_SENSOR))
////                duration=cursor.getString(cursor.getColumnIndex(CONST_DURATION))
////
////
////                val lst = CollectDataModel(
////
////                    activity = activity, result = result,
////                    value = value,sensor = sensor,
////                    units = units,duration = duration
////                )
////                list.add(lst)
////            } while (cursor.moveToNext())
////        }
////        return list
////
////    }


    fun addPack(viewtask: ViewPackModelClass) {

        try {

            val values = ContentValues()
            values.put(CONST_PACK_NAME, viewtask.name)
            values.put(CONST_PACKS_ID, viewtask.id)
            values.put(CONST_PACK_DETAIL, viewtask.description)
            values.put(CONST_PACK_TYPE, viewtask.pack_config_name)
            values.put(CONST_PACK_TYPE_ID, viewtask.pack_config_id)
            values.put(CONST_PACK_GROUP, viewtask.com_group)
            values.put(CONST_PACK_NAME_PREFIX, viewtask.name_prefix)
            values.put(CONST_PACK_LABELTYPE, viewtask.type)
            values.put(CONST_PACK_LABEL_DESCIPTION, viewtask.labeldesciption)
            values.put(CONST_PACK_PADZERO, viewtask.padzero)
            values.put(CONST_PACK_CREATEDBY, viewtask.created_by)
            values.put(CONST_PACK_NAME_PREFIX_padzero, viewtask.name_prefix + viewtask.padzero)


            val db = this.writableDatabase
            val result = db.insert(CONST_TABLE_PACK, null, values)
            db.close()
            if (result >= 0) {
                AppUtils.logDebug(TAG, "ADDed packlist to database ")
            } else {
                AppUtils.logDebug(TAG, "Failed to Add Packlist to database ")
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //    fun updateTask(task: ViewTaskModelClass, entryname: String): Int {
//        val db = this.writableDatabase
//
//        val contentValues = ContentValues()
//        contentValues.put(CONST_TASK_NAME, task.ENTRYNAME)
//        contentValues.put(CONST_TASK_TYPE, task.ENTRYTYPE)
//        contentValues.put(CONST_TASK_DETAIL, task.ENTRYDETAIL)
//        contentValues.put(CONST_EXPECTED_EXP_STR_DATE, task.ExpectedStartDate)
//        contentValues.put(CONST_EXPECTED_EXP_END_DATE, task.ExpectedEndDate)
//        contentValues.put(CONST_EXPECTED_STR_DATE, task.StartDate)
//        contentValues.put(CONST_EXPECTED_END_DATE, task.EndDate)
////        val success=db.update(CONST_NEW_TASK,contentValues,"$CONST_TASK_NAME="+task.ENTRYNAME,
////    null)
//        val success =
//            db.update(CONST_NEW_TASK, contentValues, "$CONST_TASK_NAME = ?", arrayOf(entryname))
//        db.close()
//        return success
//    }
    @SuppressLint("Range")
    fun getAllPack(): ArrayList<PacksNew> {


        val upCommingPackList = ArrayList<PacksNew>()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_CREAT_PACK"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val collect_activity_id: Int? =
                    0//cursor.getString(cursor.getColumnIndex(DatabaseConstant.COL_PACK)) ?: 0
                val com_group: String? = cursor.getString(cursor.getColumnIndex(COL_PACK_GROUP))
                val created_by: String? =
                    cursor.getString(cursor.getColumnIndex(COL_PACK_CREATED_BY))
                val created_date: String? =
                    cursor.getString(cursor.getColumnIndex(COL_PACK_CREATED_DATE))
                val deleted_at: String? =
                    "" //cursor.getString(cursor.getColumnIndex(DatabaseConstant.COL_PACK_D))
                val description: String? = cursor.getString(cursor.getColumnIndex(COL_PACK_DESC))
                val id: String? = cursor.getString(cursor.getColumnIndex(COL_ID))
                val initial_task_no: String? =
                    ""//cursor.getString(cursor.getColumnIndex(DatabaseConstant.COL_PACK_GROUP))
                val is_active: String? = cursor.getString(cursor.getColumnIndex(COL_PACK_IS_ACTIVE))
                val last_changed_by: String? =
                    cursor.getString(cursor.getColumnIndex(COL_PACK_LAST_CHANGED_BY))
                val last_changed_date: String? =
                    cursor.getString(cursor.getColumnIndex(COL_PACK_LAST_CHANGED_DATE)) ?: ""
                val name: String? = cursor.getString(cursor.getColumnIndex(COL_PACK_NAME))
                val pack_config_id: String? =
                    cursor.getString(cursor.getColumnIndex(COL_PACK_CONFIG_ID))
                val primaryKey: String? = cursor.getString(cursor.getColumnIndex(COL_LOCAL_ID))

                val packsNew = PacksNew(
                    collect_activity_id.toString(),
                    com_group?.toInt(),
                    created_by?.toInt(),
                    created_date,
                    deleted_at,
                    description,
                    id?.toInt(),
                    initial_task_no,
                    is_active?.toInt(),
                    last_changed_by?.toInt(),
                    last_changed_date,
                    name,
                    pack_config_id, "TYPE-  ",
                    "Desciption-  ", "", "",
                    "", primaryKey?.toInt()

                )
                upCommingPackList.add(packsNew)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()
        // return notes list
        return upCommingPackList
    }


    @SuppressLint("Range")
    fun getAllPackConfig(): ArrayList<PackConfig> {
//        val list: ArrayList<PackConfigList> = ArrayList()

        val upCommingPackCONFIGList = ArrayList<PackConfig>()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_PACKCONFIG"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        if (cursor.moveToFirst()) {
            do {
                var name: String?
                var id: String?
                var desciption: String?
                var packConfigType: String?
                var packConfigClass: String?
                var comgroup: String?
                var nameprefix: String?
                var collectactivityid: String?
                var graphchartid: String?
                var createdBY: String?
                var createdDate: String?
                var lastChangedBy: String?
                var lastChnagedDate: String?
                var deletedAt: String?

                name = cursor.getString(cursor.getColumnIndex(COL_pack_configs_NAME))
                id = cursor.getString(cursor.getColumnIndex(COL_pack_configs_SERVER_ID))
                desciption = cursor.getString(cursor.getColumnIndex(COL_pack_configs_DESCIPTION))
                packConfigType = cursor.getString(cursor.getColumnIndex(COL_pack_configs_TYPE))
                packConfigClass = cursor.getString(cursor.getColumnIndex(COL_pack_configs_CLASS))
                comgroup = cursor.getString(cursor.getColumnIndex(COL_pack_configs_COMGROUP))
                nameprefix = cursor.getString(cursor.getColumnIndex(COL_pack_configs_NAMEPREFIX))
                collectactivityid =
                    cursor.getString(cursor.getColumnIndex(COL_pack_configs_COLLECTACTIVITY_ID))
                graphchartid =
                    cursor.getString(cursor.getColumnIndex(COL_pack_configs_GRAPHCHCHART_ID))
                createdBY = cursor.getString(cursor.getColumnIndex(COL_pack_configs_CREATEDBY))
                createdDate = cursor.getString(cursor.getColumnIndex(COL_pack_configs_CREATED_DATE))
                lastChangedBy =
                    cursor.getString(cursor.getColumnIndex(COL_pack_configs_LAST_CHANGED_BY))
                lastChnagedDate =
                    cursor.getString(cursor.getColumnIndex(COL_pack_configs_LAST_CHNAGED_DATE))
                deletedAt = cursor.getString(cursor.getColumnIndex(COL_pack_configs_DELETED_AT))

                val packconfig = PackConfig(
                    packConfigClass.toInt(), collectactivityid,
                    comgroup.toInt(), createdBY.toInt(), createdDate, deletedAt,
                    desciption, graphchartid, id.toInt(), lastChangedBy, lastChnagedDate,
                    name, nameprefix, packConfigType.toInt(),
                )
//                val packconfig = PackConfig(
//                 0,"",
//                    0,0,"","",
//                    "","",id,"",
//                    "","","",0
//                )
                upCommingPackCONFIGList.add(packconfig)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun setCollectActivitySpinner(myid: String): ArrayList<CollectActivity> {
//        val list: ArrayList<PackConfigList> = ArrayList()

        val upCommingPackCONFIGList = ArrayList<CollectActivity>()
        val db = this.readableDatabase
        val query =
            "Select * from $TABLE_collect_activities Where $COL_collect_activities_SERVERID In($myid)"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        if (cursor.moveToFirst()) {
            do {

                val id: String? =
                    cursor.getString(cursor.getColumnIndex(COL_collect_activities_SERVERID))
                val name: String? =
                    cursor.getString(cursor.getColumnIndex(COL_collect_activities_NAME))


                val packconfig = CollectActivity(id = id?.toInt(), name = name)
//                val packconfig = PackConfig(
//                 0,"",
//                    0,0,"","",
//                    "","",id,"",
//                    "","","",0
//                )
                upCommingPackCONFIGList.add(packconfig)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getAllCollectData(selectedPackid: String): ArrayList<CollectData> {

        val query =
            "SELECT $TABLE_collect_data.*, " +
                    " strftime('%Y-%m-%d %H:%M:%S', $TABLE_collect_data.$COL_collect_data_CREATED_AT) as 'DATE'," +
                    " $TABLE_collect_activities.$COL_collect_activities_NAME, " +
                    " $TABLE_collect_activity_results.$COL_collect_activity_results_RESULTNAME, " +
                    " $TABLE_units.$COL_units_NAME, " +
                    " $TABLE_sensors.$COL_sensors_NAME " +
                    " FROM  $TABLE_collect_data " +
                    " INNER JOIN $TABLE_collect_activities  ON " +
                    " $TABLE_collect_data.$COL_collect_data_CollectActivityId = $TABLE_collect_activities.$COL_collect_activities_SERVERID " +
                    " INNER JOIN $TABLE_collect_activity_results  ON" +
                    " $TABLE_collect_activity_results.$COL_collect_activity_results_SERVERID = $TABLE_collect_data.$COL_collect_data_ResulId " +
                    " INNER JOIN $TABLE_units ON " +
                    " $TABLE_collect_data.$COL_collect_data_UNITID = $TABLE_units.$COL_units_SERVERID " +
                    " INNER JOIN $TABLE_sensors ON " +
                    " $TABLE_collect_data.$COL_collect_data_SENSORID = $TABLE_sensors.$COL_sensors_SERVERID " +
                    "  Where $TABLE_collect_data.$COL_collect_data_pack_id = '$selectedPackid'"
        AppUtils.logError(TAG, "my query " + query)


        val upCommingPackCONFIGList = ArrayList<CollectData>()
        val db = this.readableDatabase
//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    var collect_activity_id: String?
                    var created_at: String?
                    var created_by: String?
                    var deleted_at: String?
                    var deleted_by: String?
                    var duration: String?
                    var id: String?
                    var new_value: String?
                    var pack_id: String?
                    var result_class: String?
                    var sensor_id: String?
                    var result_id: String?
                    var unit_id: String?
                    var updated_at: String?
                    var updated_by: String?
                    var value: String?
                    var activityname: String?
                    var resultname: String?
                    var unitname: String?
                    var date: String?
                    var sensorname: String?

                    collect_activity_id =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_CollectActivityId))
                    created_at =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_CREATED_AT))
                    created_by = cursor.getString(cursor.getColumnIndex(COL_collect_data_CREATEDBY))
                    deleted_at =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_DELETED_AT))
                    deleted_by =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_DELETED_BY))
                    duration = cursor.getString(cursor.getColumnIndex(COL_collect_data_DURATION))
                    id = cursor.getString(cursor.getColumnIndex(COL_collect_data_SERVERID))
                    new_value = cursor.getString(cursor.getColumnIndex(COL_collect_data_NEWVALUE))
                    pack_id = cursor.getString(cursor.getColumnIndex(COL_collect_data_pack_id))
                    result_class =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_ResultClass))
                    sensor_id = cursor.getString(cursor.getColumnIndex(COL_collect_data_SENSORID))
                    result_id = cursor.getString(cursor.getColumnIndex(COL_collect_data_ResulId))
                    unit_id = cursor.getString(cursor.getColumnIndex(COL_collect_data_UNITID))
                    updated_at =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_UPDATED_AT))
                    updated_by =
                        cursor.getString(cursor.getColumnIndex(COL_collect_data_UPDATED_BY))
                    value = cursor.getString(cursor.getColumnIndex(COL_collect_data_NEWVALUE))

                    activityname =
                        cursor.getString(cursor.getColumnIndex(COL_collect_activities_NAME))
                    resultname =
                        cursor.getString(
                            cursor.getColumnIndex(
                                COL_collect_activity_results_RESULTNAME
                            )
                        )
                    unitname =
                        cursor.getString(cursor.getColumnIndex(COL_units_NAME))
                    sensorname =
                        cursor.getString(cursor.getColumnIndex(COL_sensors_NAME))
                    date =
                        cursor.getString(cursor.getColumnIndex("DATE"))
                    AppUtils.logDebug(TAG, "activityname==" + activityname)


                    val packconfig = CollectData(
                        collect_activity_id, created_at, created_by,
                        deleted_at, deleted_by, duration, id, new_value,
                        pack_id, result_class, result_id, sensor_id,
                        unit_id, updated_at,
                        updated_by, value, activityname, resultname,
                        unitname, date,
                        sensorname
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getAllEventListData(userId: String): ArrayList<Event> {

        val query = "SELECT * FROM $TABLE_events "


        val upCommingPackCONFIGList = ArrayList<Event>()
        val db = this.readableDatabase
//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_SERVERID))
                    val name: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_NAME))
                    val desc: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_DESCIPTION))
                    val type: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_TYPE))
                    val expStrDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_EXP_STR_DATE))
                    val expEndDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_EXP_END_DATE))
                    val duration: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_EXP_DURATION))
                    val actStrDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_ACTUAL_STR_DATE))
                    val actEndDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_ACTUAL_END_DATE))
                    val actDuration: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_ACTUAL_DURATION))
                    val eventClose: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_CLOSED))
                    val closedDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_CLOSED_DATE))
                    val closedBy: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_CLOSED_BY))
                    val comGroup: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_COM_GROUP))
                    val status: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_STATUS))
                    val responsible: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_RESPONSIBLE))

                    val assgnTeam: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_ASSIGN_TEAM))
                    val taskid: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_TASK_ID))
                    val createdBy: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_CREATED_BY))
                    val createdDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_CREATED_DATE))
                    val lastChangedBy: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_LAST_CHANGED_BY))
                    val lastChangedDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_LAST_CHANGED_DATE))
                    val deletedAt: String? =
                        cursor.getString(cursor.getColumnIndex(COL_events_DELETED_AT))


                    val packconfig = Event(
                        actDuration,
                        actEndDate,
                        actStrDate,
                        assgnTeam?.toInt(),
                        eventClose?.toInt(),
                        closedBy,
                        closedDate,
                        comGroup?.toInt(),
                        createdBy?.toInt(),
                        createdDate,
                        deletedAt,
                        desc,
                        duration,
                        expEndDate,
                        expStrDate,
                        id?.toInt(),
                        lastChangedBy?.toInt(),
                        lastChangedDate,
                        name,
                        responsible?.toInt(),
                        status?.toInt(),
                        taskid,
                        type?.toInt()
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getPackConfigFieldList(configId: String): ArrayList<PackConfigField> {

        val query =
            "SELECT * FROM $TABLE_pack_config_fields Where $COL_pack_config_fields_pack_config_id ='$configId' "

        AppUtils.logError(TAG, "getPackConfigFieldList Query" + query)

        val upCommingPackCONFIGList = ArrayList<PackConfigField>()
        val db = this.readableDatabase
//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_SERVER_ID))
                    val packConfigID: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_pack_config_id))
                    val fieldName: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_field_name))
                    val fieldDesciption: String? =
                        cursor.getString(
                            cursor.getColumnIndex(
                                COL_pack_config_fields_field_description
                            )
                        )
                    val editable: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_editable))
                    val fieldType: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_field_type))
                    val fieldList: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_list))
                    val defaultValue: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_default_value))
                    val createdBy: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_created_by))
                    val lastChangedBy: String? =
                        cursor.getString(
                            cursor.getColumnIndex(
                                COL_pack_config_fields_last_changed_by
                            )
                        )
                    val lastChangedDate: String? = cursor.getString(
                        cursor.getColumnIndex(COL_pack_config_fields_last_changed_date)
                    )
                    val deletedAt: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_deleted_at))
                    val createdDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_pack_config_fields_created_date))


                    val packconfig = PackConfigField(
                        createdBy?.toInt(),
                        createdDate,
                        defaultValue,
                        deletedAt,
                        editable?.toInt(),
                        fieldDesciption,
                        fieldName,
                        fieldType,
                        id?.toInt(),
                        lastChangedBy,
                        lastChangedDate,
                        fieldList,
                        packConfigID?.toInt()
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getTaskConfigFieldList(configId: String): ArrayList<TaskConfigField> {

        val query =
            "SELECT * FROM $TABLE_task_config_fields Where $COL_task_config_fields_task_config_id ='$configId' "

        AppUtils.logError(TAG, "getPackConfigFieldList Query" + query)

        val upCommingPackCONFIGList = ArrayList<TaskConfigField>()
        val db = this.readableDatabase
//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_SERVERID))
                    val taskConfigID: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_task_config_id))
                    val fieldName: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_field_name))
                    val fieldDesciption: String? =
                        cursor.getString(
                            cursor.getColumnIndex(
                                COL_task_config_fields_field_description
                            )
                        )
                    val editable: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_editable))
                    val fieldType: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_field_type))
                    val fieldList: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_list))
                    val createdBy: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_created_by))
                    val createdDate: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_created_date))
                    val lastChangedBy: String? =
                        cursor.getString(
                            cursor.getColumnIndex(
                                COL_task_config_fields_last_changed_by
                            )
                        )
                    val lastChangedDate: String? = cursor.getString(
                        cursor.getColumnIndex(COL_task_config_fields_last_changed_date)
                    )
                    val deletedAt: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_fields_deleted_at))

                    val packconfig = TaskConfigField(
                        createdBy?.toInt(), createdDate, deletedAt,
                        editable?.toInt(), fieldDesciption, fieldName, fieldType, id?.toInt(),
                        lastChangedBy?.toInt(),
                        lastChangedDate, fieldList
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getFieldList(): ArrayList<Field> {

        val query = "SELECT * FROM $TABLE_fields"

        AppUtils.logError(TAG, "getPackConfigFieldList Query" + query)

        val upCommingPackCONFIGList = ArrayList<Field>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getListChoice Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val id: String? = cursor.getString(cursor.getColumnIndex(COL_fields_SERVERID))
                    val name: String? = cursor.getString(cursor.getColumnIndex(COL_fields_NAME))


                    val packconfig = Field(
                        null, null, null,
                        null, null, null, null,
                        null, null, null,
                        null, null, null, null,
                        null, null, null, null,
                        id?.toInt(), null, null, null,
                        null, null, null, null, null,
                        name, null, null,
                        null, null, null, null,
                        null, null, null, null,
                        null, null, null, null,
                        null
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getGraphChartNames(graphId: String) :ArrayList<GraphChart>{

        val query = "SELECT * FROM $TABLE_graph_charts Where ${TABLE_graph_charts}.${COL_graph_charts_SERVERID} In($graphId)"

        AppUtils.logError(TAG, "getGraphChartNames Query" + query)

        val upCommingPackCONFIGList = ArrayList<GraphChart>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getGraphChartNames Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val id: String? = cursor.getString(cursor.getColumnIndex(COL_graph_charts_SERVERID))
                    val name: String? = cursor.getString(cursor.getColumnIndex(COL_graph_charts_NAME))
                    val objectClass: String? = cursor.getString(cursor.getColumnIndex(COL_graph_charts_OBJECTCLASS))


                    val packconfig = GraphChart(id = id?.toInt(), name = name,
                        object_class = objectClass?.toInt())

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getGraphChartObjects(chartID: String) :ArrayList<GraphChartObject>{

        var pointlist=getAllPointsList(chartID)

        val query = "SELECT * FROM $TABLE_graph_chart_objects " +
                "Where $COL_graph_chart_objects_GRAPH_CHARTID='$chartID'"

        AppUtils.logError(TAG, "getGraphChartNames Query" + query)

        val upCommingPackCONFIGList = ArrayList<GraphChartObject>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getGraphChartNames Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val name: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_objects_NAME))
                    val lineType: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_objects_LINETYPE))
                    val resultClass: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_objects_RESULT_CLASS))
                    val refCltPoint: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_objects_REF_CLTL_POINT))
                    val graphchartid: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_objects_GRAPH_CHARTID))


                    val packconfig = GraphChartObject(name = name, line_type = lineType,
                    result_class = resultClass, ref_ctrl_points = refCltPoint, graphs_charts_id = graphchartid.toString(),
                        points = pointlist)

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    private fun getAllPointsList(chartID: String): ArrayList<Points> {



        val query = "SELECT * FROM $TABLE_graph_chart_points " +
                "Where $COL_graph_chart_points_CHARTID='$chartID'"

        AppUtils.logError(TAG, "getGraphChartNames Query" + query)

        val upCommingPackCONFIGList = ArrayList<Points>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getGraphChartNames Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val value: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_points_VALUE))
                    val duration: String? = cursor.getString(cursor.getColumnIndex(COL_graph_chart_points_DURATION))

                    val packconfig = Points(value=value, duration = duration)

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getListChoice(listId: String): ArrayList<Choices> {

        val query = "SELECT * FROM $TABLE_list_choices Where $COL_list_choices_LISTID ='$listId' "

        AppUtils.logError(TAG, "getPackConfigFieldList Query" + query)

        val upCommingPackCONFIGList = ArrayList<Choices>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getListChoice Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_SERVERID))
                    val listID: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_LISTID))
                    val choice: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_CHOICE))
                    val name: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_NAME))
                    val choiceComGroup: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_CHOICE_COM_GROUP))
                    val comGroupId: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_COM_GROUP_ID))
                    val createdAt: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_CREATED_AT))
                    val updatedAt: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_UPDATED_AT))
                    val deletedAt: String? =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_DELETED_AT))


                    val packconfig = Choices(
                        choice, choiceComGroup,
                        comGroupId, createdAt, deletedAt, id?.toInt(), listID?.toInt(),
                        name, updatedAt
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getListChoiceSingleValue(listId: String): String{
        var  chartname:String?=null

        val query = "SELECT * FROM $TABLE_list_choices Where $COL_list_choices_SERVERID ='$listId' "

        AppUtils.logError(TAG, "getPackConfigFieldList Query" + query)

        val upCommingPackCONFIGList = ArrayList<Choices>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getListChoice Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return chartname.toString()
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    chartname =
                        cursor.getString(cursor.getColumnIndex(COL_list_choices_NAME))


                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return chartname.toString()
    }

    @SuppressLint("Range")
    fun getUnitList(unitId: String): ArrayList<Unit> {

        val query = "SELECT * FROM $TABLE_units Where $COL_units_SERVERID In($unitId)"

        AppUtils.logError(TAG, "getPackConfigFieldList Query" + query)

        val upCommingPackCONFIGList = ArrayList<Unit>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "getListChoice Query" + query)

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val id: String? = cursor.getString(cursor.getColumnIndex(COL_units_SERVERID))
                    val unitname: String? = cursor.getString(cursor.getColumnIndex(COL_units_NAME))


                    val packconfig = Unit(
                        id = id?.toInt(), name = unitname
                    )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getFieldNameFromListChoice(id: String): String {

        val query = "SELECT * FROM $TABLE_list_choices Where $COL_list_choices_SERVERID ='$id' "

        AppUtils.logError(TAG, "getFieldNameFromListChoice Query " + query)

        var name: String? = null
        val db = this.readableDatabase

//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return name!!
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    name = cursor.getString(cursor.getColumnIndex(COL_list_choices_NAME))


                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return name!!
    }

    @SuppressLint("Range")
    fun getPersonList(): ArrayList<People> {

        val query = "SELECT * FROM $TABLE_people"

        AppUtils.logError(TAG, "getPersonList Query" + query)

        val upCommingPackCONFIGList = ArrayList<People>()
        val db = this.readableDatabase


//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    val id: String? = cursor.getString(cursor.getColumnIndex(COL_people_SERVERID))
                    val lname: String? = cursor.getString(cursor.getColumnIndex(COL_people_FNAME))
                    val fname: String? = cursor.getString(cursor.getColumnIndex(COL_people_LNAME))


                    val packconfig = People(
                        null, null, null,
                        null, null,
                        null, null, null, null,
                        null, null, null, null,
                        fname, id?.toInt(), null, null,
                        null, null, lname, null,
                        null, null, null, null, null,

                        )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }


    @SuppressLint("Range")
    fun getContainerList(): ArrayList<Container> {

        val query = "SELECT * FROM $TABLE_container"

        AppUtils.logError(TAG, "getPersonList Query" + query)

        val upCommingPackCONFIGList = ArrayList<Container>()
        val db = this.readableDatabase


//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_container_SERVERID))
                    val name: String? = cursor.getString(cursor.getColumnIndex(COL_container_NAME))


                    val packconfig = Container(id = id?.toInt(), name = name)

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getImageList(taskid:String): ArrayList<TaskMediaFile> {

        val query = "SELECT * FROM $TABLE_task_media_files Where $COL_task_media_files_TASKID='$taskid'"

        AppUtils.logError(TAG, "getPersonList Query" + query)

        val upCommingPackCONFIGList = ArrayList<TaskMediaFile>()
        val db = this.readableDatabase


//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val taskid: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_media_files_TASKID))
                    val filename: String? = cursor.getString(cursor.getColumnIndex(COL_task_media_files_NAME))
                    val link: String? = cursor.getString(cursor.getColumnIndex(COL_task_media_files_LINK))
                    val localpath: String? = cursor.getString(cursor.getColumnIndex(COL_task_media_files_LOCAL_FILE_PATH))


                    val packconfig = TaskMediaFile(task_id = taskid?.toInt(), filePathLocal = localFilePath,
                    name = filename , link = link)

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getTeamList(): ArrayList<Team> {

        val query = "SELECT * FROM $TABLE_team"

        AppUtils.logError(TAG, "getPersonList Query" + query)

        val upCommingPackCONFIGList = ArrayList<Team>()
        val db = this.readableDatabase


//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    val id: String? = cursor.getString(cursor.getColumnIndex(COL_team_SERVERID))
                    val name: String? = cursor.getString(cursor.getColumnIndex(COL_team_NAME))


                    val packconfig = Team(
                        null, null, null,
                        null, null,
                        null, null, null, null,
                        id?.toInt(), null, name, null,
                        null, null, null, null,

                        )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getTaskFunctionList(updateTaskId: String): ArrayList<TaskConfigFunction> {

        val query = "SELECT * FROM $TABLE_task_config_functions " +
                "Where $COL_task_config_functions_task_config_id='$updateTaskId'"

        AppUtils.logError(TAG, "getPersonList Query" + query)

        val upCommingPackCONFIGList = ArrayList<TaskConfigFunction>()
        val db = this.readableDatabase


//        val query = "SELECT  * FROM ${TABLE_collect_data}   Where ${COL_collect_data_pack_id}  = '$selectedPackid'"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_functions_SERVERKEY))
                    val taskconfigId: String? = cursor.getString(
                        cursor.getColumnIndex(COL_task_config_functions_task_config_id)
                    )
                    val functionId: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_functions_task_name))
                    val functionnames: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_config_functions_description))

                    val packconfig = TaskConfigFunction(
                        null, null, null,
                        functionnames, functionId?.toInt(),
                        null, null, functionnames, null,


                        )

                    upCommingPackCONFIGList.add(packconfig)

                } while (cursor.moveToNext())
            }
            AppUtils.logDebug(TAG, "upCommingPackCONFIGList" + upCommingPackCONFIGList.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }


    fun updatePackNew(
        packList1: PacksNew?,
        desciptioncompanian: String,
        selectedCommunityGroup: String
    ) {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_PACK_NAME, packList1?.name)
        contentValues.put(COL_PACK_CONFIG_ID, packList1?.pack_config_id)
        contentValues.put(COL_PACK_NEW_DLETED_AT, packList1?.deleted_at)
        contentValues.put(COL_PACK_CREATED_BY, packList1?.created_by)
        contentValues.put(COL_PACK_LAST_CHANGED_BY, packList1?.last_changed_by)
        contentValues.put(COL_PACK_DESC, desciptioncompanian)
        contentValues.put(COL_PACK_GROUP, selectedCommunityGroup)
        contentValues.put(COL_PACKNEW_Status, "1")
        contentValues.put(COL_PACK_IS_ACTIVE, packList1?.is_active)
        contentValues.put(COL_PACK_CREATED_DATE, packList1?.created_date)
        contentValues.put(COL_PACK_LAST_CHANGED_DATE, packList1?.last_changed_date)


        val result = db.update(
            TABLE_CREAT_PACK,
            contentValues,
            "$COL_ID = ?",
            arrayOf(packList1?.id.toString())
        )
        if (result >= 0) {
            AppUtils.logDebug(TAG, "updated Pacl")
        } else {
            AppUtils.logDebug(TAG, "Failed to Update")
        }
    }

    fun deletePackNew(packid: String) {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_LOCAL_ID, packid)
        try {
            val succ = db.delete(TABLE_CREAT_PACK, "$COL_LOCAL_ID=?", arrayOf(packid))

        } catch (e: Exception) {
            AppUtils.logError(TAG, e.toString())
        }

        db.close()

    }

    @SuppressLint("Range")
    fun getAllTask(): ArrayList<Task> {

        val upCommingPackList = ArrayList<Task>()
        val db = this.readableDatabase
//        val query = "Select $TABLE_tasks.*," +
//                " $TABLE_task_configs.$ from $TABLE_tasks"
        val query = "SELECT $TABLE_tasks.*, " +
                " $TABLE_task_configs.$COL_task_configs_NAME, " +
                " $TABLE_task_configs.$COL_task_configs_DESCIPTION, " +
                " $TABLE_task_configs.$COL_task_configs_NAME_PREFIX " +
                " FROM  $TABLE_tasks " +
                " INNER JOIN $TABLE_task_configs  ON " +
                " $TABLE_tasks.$COL_tasks_TASK_CONFIGID = $TABLE_task_configs.$COL_task_configs_SERVERID "

        AppUtils.logError(TAG, "my query " + query)
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {


                val id: String? = cursor.getString(cursor.getColumnIndex(COL_tasks_SERVERid))
                val name: String? = cursor.getString(cursor.getColumnIndex(COL_tasks_NAME))
                val description: String? = cursor.getString(cursor.getColumnIndex(COL_tasks_DESC))
                val comGroup: String? = cursor.getString(cursor.getColumnIndex(COL_tasks_GROUP))
                val taskconfigId: String? =
                    cursor.getString(cursor.getColumnIndex(COL_tasks_TASK_CONFIGID))
                val taskFunc: String? = cursor.getString(cursor.getColumnIndex(COL_tasks_TASKFUNC))
                val status: String? = cursor.getString(cursor.getColumnIndex(COL_tasks_STATUS))

                val taskConfigName: String? =
                    cursor.getString(cursor.getColumnIndex(COL_task_configs_NAME))
                val taskConfigDescption: String? =
                    cursor.getString(cursor.getColumnIndex(COL_task_configs_DESCIPTION))
                val taskConfigNamePrefix: String? =
                    cursor.getString(cursor.getColumnIndex(COL_task_configs_NAME_PREFIX))


                val task = Task(
                    comGroup?.toInt(),
                    0,
                    "",
                    "",
                    description,
                    0,
                    id?.toInt(),
                    0,
                    "",
                    name,
                    0,
                    status,
                    taskconfigId?.toInt(),
                    taskFunc,
                    taskConfigName,
                    taskConfigDescption,
                    taskConfigNamePrefix,
                    ""
                )

                upCommingPackList.add(task)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()
        // return notes list
        return upCommingPackList
    }

    @SuppressLint("Range")
    fun getTaskConfigList(userId: String): ArrayList<TaskConfig> {
        val query = "SELECT  * FROM  $TABLE_task_configs"
        val upCommingPackCONFIGList = ArrayList<TaskConfig>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "my query " + query)

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val taskConfigId: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_SERVERID))
                    val taskConfigName: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_NAME))
                    val desc: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_DESCIPTION))
                    val type: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_TYPE))
                    val mclass: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_CLASS))
                    val comGroup: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_COM_GROUP))
                    val namePrefix: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_NAME_PREFIX))
                    val recordevent: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_RECORD_EVENT))
                    val reportable: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_REPORTABLE))
                    val createdby: String? =
                        cursor.getString(cursor.getColumnIndex(COL_task_configs_CREATED_BY))


                    val taskconfig = TaskConfig(
                        mclass, comGroup!!.toInt(), createdby!!.toInt(), "",
                        "",
                        desc,
                        taskConfigId!!.toInt(), "",
                        "", taskConfigName, namePrefix,
                        recordevent!!, reportable!!, type?.toInt()!!
                    )

                    upCommingPackCONFIGList.add(taskconfig)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for Taskconfig" + e.toString())
        }
        cursor.close()
        db.close()

        return upCommingPackCONFIGList
    }


    @SuppressLint("Range")
    fun getCommunityGroupList(): ArrayList<CommunityGroup> {
        val query = "SELECT  * FROM  $TABLE_community_groups "
        val upCommingPackCONFIGList = ArrayList<CommunityGroup>()
        val db = this.readableDatabase
        AppUtils.logError(TAG, "my query " + query)

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_community_groups_SERVERID))
                    val name: String? =
                        cursor.getString(cursor.getColumnIndex(COL_community_groups_NAME))
                    val desc: String? =
                        cursor.getString(cursor.getColumnIndex(COL_community_groups_DESCIPRTION))
                    val comGroup: String? =
                        cursor.getString(cursor.getColumnIndex(COL_community_groups_COMM_GROUP))


                    val taskconfig = CommunityGroup(
                        comGroup, null, null, null, null,
                        desc, id?.toInt(), name, null, null
                    )

                    upCommingPackCONFIGList.add(taskconfig)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for Taskconfig" + e.toString())
        }

        db.close()

        return upCommingPackCONFIGList
    }


    @SuppressLint("Range")

    fun getSinglDataFromPackConfig(selectedPackid: String): ArrayList<PackConfig> {

        val query = "SELECT  * FROM ${TABLE_PACKCONFIG} " +
                "  Where ${COL_pack_configs_SERVER_ID}  = '$selectedPackid'"


        val upCommingPackCONFIGList = ArrayList<PackConfig>()
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {


                    var collectactivityId: String? = null




                    collectactivityId =
                        cursor.getString(cursor.getColumnIndex(COL_pack_configs_COLLECTACTIVITY_ID))

                    val packconfig = PackConfig(
                        collect_activity_id = collectactivityId
                    )

                    upCommingPackCONFIGList.add(packconfig)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }


    @SuppressLint("Range")
    fun getSensorList(): ArrayList<Sensor> {

        val query = "SELECT  * FROM ${TABLE_sensors} "

        val upCommingPackCONFIGList = ArrayList<Sensor>()
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    var id: String?
                    var name: String?




                    id = cursor.getString(cursor.getColumnIndex(COL_sensors_SERVERID))
                    name = cursor.getString(cursor.getColumnIndex(COL_sensors_NAME))

                    val packconfig = Sensor(id = id?.toInt(), name = name)

                    upCommingPackCONFIGList.add(packconfig)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    @SuppressLint("Range")
    fun getCollectActivityResult(collectactiivtyId: String): ArrayList<CollectActivityResult> {


        val query = "SELECT  * FROM ${TABLE_collect_activity_results}" +
                " Where ${COL_collect_activity_results_COLLECT_ACTIVITY_ID}='$collectactiivtyId'"
        AppUtils.logDebug(TAG, "getCollectActivityResult QUERY   " + query.toString())

        val upCommingPackCONFIGList = ArrayList<CollectActivityResult>()
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            if (cursor.moveToFirst()) {
                do {

                    val id: String? =
                        cursor.getString(cursor.getColumnIndex(COL_collect_activity_results_SERVERID))
                    val name: String? = cursor.getString(
                        cursor.getColumnIndex(COL_collect_activity_results_RESULTNAME)
                    )
                    val listid: String? =
                        cursor.getString(cursor.getColumnIndex(COL_collect_activity_results_LISTID))
                    val unitId: String? =
                        cursor.getString(cursor.getColumnIndex(COL_collect_activity_results_UNITID))
                    val typeid: String? =
                        cursor.getString(cursor.getColumnIndex(COL_collect_activity_results_TYPEID))

                    val packconfig = CollectActivityResult(
                        id = id?.toInt(), result_name = name,
                        type_id = typeid, unit_id = unitId, list_id = listid
                    )


                    upCommingPackCONFIGList.add(packconfig)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, "exceptopn for c0llectdata" + e.toString())
        }
        // close db connection
        db.close()
        // return notes list
        return upCommingPackCONFIGList
    }

    private fun checkEntry(ID: Int?, TABLE: String, SERVERID: String): Int {
        val countQuery = "SELECT  * FROM ${TABLE}   Where ${SERVERID}  = '$ID'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }


//    fun updateTask(task: ViewTaskModelClass, entryname: String): Int {
//
//        val db = this.writableDatabase
//
//        val contentValues = ContentValues()
//        contentValues.put(CONST_TASK_NAME, task.ENTRYNAME)
//        contentValues.put(CONST_TASK_TYPE, task.ENTRYTYPE)
//        contentValues.put(CONST_TASK_DETAIL, task.ENTRYDETAIL)
//        contentValues.put(CONST_EXPECTED_EXP_STR_DATE, task.ExpectedStartDate)
//        contentValues.put(CONST_EXPECTED_EXP_END_DATE, task.ExpectedEndDate)
//        contentValues.put(CONST_EXPECTED_STR_DATE, task.StartDate)
//        contentValues.put(CONST_EXPECTED_END_DATE, task.EndDate)
////        val success=db.update(CONST_NEW_TASK,contentValues,"$CONST_TASK_NAME="+task.ENTRYNAME,
////    null)
//        val success =
//            db.update(CONST_NEW_TASK, contentValues, "$CONST_TASK_NAME = ?", arrayOf(entryname))
//        db.close()
//        return success
//    }
//    fun updatePack(task: ViewPackModelClass, entryname: String):Int{
//        val db=this.writableDatabase
//
//
//        val contentValues = ContentValues()
//        contentValues.put(CONST_PACK_NAME, task.packname)
//        contentValues.put(CONST_PACK_TYPE, task.packType)
//        contentValues.put(CONST_PACK_DETAIL, task.packdesciption)
//        contentValues.put(CONST_PACK_GROUP, task.packdesciption)
//        contentValues.put(CONST_PACK_UNITS, task.units)
//        contentValues.put(CONST_PACK_QUANTITY, task.quantitiy)
//        contentValues.put(CONST_PACK_CUSTOMER, task.customer)
//
////        val success=db.update(CONST_NEW_TASK,contentValues,"$CONST_TASK_NAME="+task.ENTRYNAME,
////    null)
//      val success=  db.update(CONST_TABLE_PACK, contentValues,"$CONST_PACK_NAME = ?", arrayOf(entryname))
//        db.close()
//        return success
//    }

//    fun selectPack(){
//
//        var nombr = 0
//        val cursor: Cursor =
//            sqlDatabase.rawQuery("SELECT column FROM table WHERE column = Value", null)
//        nombr = cursor.count
//
//        val db = this.readableDatabase
//        val query = "SELECT column FROM $TABLE_CREAT_PACK WHERE column = Value"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//        }
//    }

//    @SuppressLint("Range")
//    fun readData(): ArrayList<ViewTaskModelClass> {
//        val list: ArrayList<ViewTaskModelClass> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_NEW_TASK"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//        var taskname: String
//        var tasktype: String
//        var taskdetail: String
//        var expectedStartDate: String
//        var expectedEndDate: String
//        var startdate: String
//        var endDate: String
//        if (cursor.moveToFirst()) {
//            do {
//                taskname = cursor.getString(cursor.getColumnIndex(CONST_TASK_NAME))
//                tasktype = cursor.getString(cursor.getColumnIndex(CONST_TASK_TYPE))
//                taskdetail = cursor.getString(cursor.getColumnIndex(CONST_TASK_DETAIL))
//                expectedStartDate = cursor.getString(
//                    cursor.getColumnIndex(
//                        CONST_EXPECTED_EXP_STR_DATE
//                    )
//                )
//                expectedEndDate =
//                    cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_EXP_END_DATE))
//                startdate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_STR_DATE))
//                endDate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_END_DATE))
//                val lst = ViewTaskModelClass(
//                    ENTRYDETAIL = taskdetail, ENTRYNAME = taskname,
//                    ENTRYTYPE = tasktype, ExpectedStartDate = expectedStartDate,
//                    ExpectedEndDate = expectedEndDate, StartDate = startdate,
//                    EndDate = endDate
//                )
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }

//    @SuppressLint("Range")
//    fun readPackData(): ArrayList<ViewPackModelClass> {
//        val list: ArrayList<ViewPackModelClass> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_TABLE_PACK"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//
//        var PACK_NAME: String
//        var PACKS_ID: String
//        var PACK_DETAIL: String
//        var PACK_TYPE: String
//        var PACK_TYPE_ID: String
//        var PACK_GROUP: String
//        var PACK_NAME_PREFIX: String
//        var PACK_LABELTYPE: String
//        var PACK_LABEL_DESCIPTION: String
//        var PACK_PADZERO: String
//        var PACK_NAME_PREFIX_padzero: String
//        var PACK_CREATEDBY: String
//
//
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                PACK_NAME = cursor.getString(cursor.getColumnIndex(CONST_PACK_NAME))
//                PACKS_ID = cursor.getString(cursor.getColumnIndex(CONST_PACKS_ID))
//                PACK_DETAIL = cursor.getString(cursor.getColumnIndex(CONST_PACK_DETAIL))
//                PACK_TYPE = cursor.getString(cursor.getColumnIndex(CONST_PACK_TYPE))
//                PACK_TYPE_ID = cursor.getString(cursor.getColumnIndex(CONST_PACK_TYPE_ID))
//                PACK_GROUP = cursor.getString(cursor.getColumnIndex(CONST_PACK_GROUP))
//                PACK_NAME_PREFIX = cursor.getString(cursor.getColumnIndex(CONST_PACK_NAME_PREFIX))
//                PACK_LABELTYPE = cursor.getString(cursor.getColumnIndex(CONST_PACK_LABELTYPE))
//                PACK_LABEL_DESCIPTION =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_LABEL_DESCIPTION))
//                PACK_PADZERO = cursor.getString(cursor.getColumnIndex(CONST_PACK_PADZERO))
//                PACK_CREATEDBY = cursor.getString(cursor.getColumnIndex(CONST_PACK_CREATEDBY))
//                PACK_NAME_PREFIX_padzero =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_NAME_PREFIX_padzero))
//
//                val lst = ViewPackModelClass(
//                    name = PACK_NAME, id = PACKS_ID,
//                    description = PACK_DETAIL,
//                    pack_config_name = PACK_TYPE,
//                    pack_config_id = PACK_TYPE_ID,
//                    com_group = PACK_GROUP,
//                    name_prefix = PACK_NAME_PREFIX,
//                    type = PACK_LABELTYPE,
//                    labeldesciption = PACK_LABEL_DESCIPTION,
//                    padzero = PACK_NAME_PREFIX_padzero, created_by = PACK_CREATEDBY
//
//                )
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }


//    fun deleteTask(taskname: String) {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(CONST_TASK_NAME, taskname)
//        db.delete(CONST_NEW_TASK, "taskname=?", arrayOf(taskname))
//        db.close()
//
//    }

//    fun deletenewPack(packid: String, configid: String) {
//
//        val db = this.writableDatabase
//        val success = db.delete(
//            TABLE_CREAT_PACK,
//            PACKNEW_CONFIGID + "=" + configid + " and " + PACKNEW_NAME + "=" + packid,
//            null
//        )
//        if (success > 0) {
//            Toast.makeText(context, "DeleteSuccessFull", Toast.LENGTH_SHORT).show()
//        }
//        db.close()
//
//    }


//    fun deletePack(taskname: String) {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(CONST_PACK_NAME, taskname)
//        val succ = db.delete(CONST_TABLE_PACK, "PACKname=?", arrayOf(taskname))
//
//        db.close()
//
//    }

//    fun dropPackTable() {
//
//        val selectQuery = "DELETE FROM $CONST_TABLE_PACK"
//        val db = this.writableDatabase
//
//        db.execSQL(selectQuery)
//    }

//    fun addPackConfigList(packconfiglist: PackConfigList) {
//        try {
//
//            val values = ContentValues()
//            values.put(CONST_PACK_CONFIGLIST_NAME, packconfiglist.name)
//            values.put(CONST_PACK_CONFIGLIST_ID, packconfiglist.id)
//
//
//            val db = this.writableDatabase
//            val result = db.insert(CONST_PACKCONFIG_LIST_TABLE, null, values)
//            db.close()
//            if (result >= 0) {
//                AppUtils.logDebug(TAG, "ADDed packconfiglist to database ")
//            } else {
//                AppUtils.logDebug(TAG, "Failed to Add packconfiglist to database ")
//            }
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//        }
//
//    }

//    @SuppressLint("Range")
//    fun readPackConfiglistData(): ArrayList<PackConfigList> {
//        val list: ArrayList<PackConfigList> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_PACKCONFIG_LIST_TABLE"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//
//        var config_NAME: String
//        var config_ID: String
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                config_NAME = cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIGLIST_NAME))
//                config_ID = cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIGLIST_ID))
//
//                val lst = PackConfigList(name = config_NAME, id = config_ID)
//
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }

//offline -ravi   drop----------table----packs_new------->
//    fun droptable_packnew() {
//        val selectQuery = "DELETE FROM $TABLE_CREAT_PACK"
//        val db = this.writableDatabase
//
//        db.execSQL(selectQuery)
//    }
    //--------------------------------//

    //offline-ravi   drop----table-packconfig-----//
//    fun droptable_pack_configs() {
//        val selectQuery = "DELETE FROM $TABLE_pack_configs"
//        val db = this.writableDatabase
//
//        db.execSQL(selectQuery)
//    }
    //---------//

//    fun addPackConfigffIELDList(packconfiglist: PackConfigFieldList) {
//        try {
//
//
//            val values = ContentValues()
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_id, packconfiglist.field_id)
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_name, packconfiglist.field_name)
//            values.put(
//                CONST_PACK_CONFIG_FIELDLIST_field_description,
//                packconfiglist.field_description
//            )
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_type, packconfiglist.field_type)
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_value, packconfiglist.field_value)
//
//
//            val db = this.writableDatabase
//            val result = db.insert(CONST_PACKCONFIG_FIELDLIST_TABLE, null, values)
//            db.close()
//            if (result >= 0) {
//                AppUtils.logDebug(TAG, "ADDed packconfigFieldlist to database ")
//            } else {
//                AppUtils.logDebug(TAG, "Failed to Add packconfigFieldlist to database ")
//            }
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//        }
//
//    }
//
//    fun addPackConfigffIELD_field_list(
//        packconfiglist: PackFieldList,
//        routes: String,
//        get: PackConfigList
//    ) {
//        try {
//
//            val values = ContentValues()
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_id_ID, packconfiglist.id)
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_name_name, packconfiglist.name)
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid, routes)
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_type_packid, get.id)
//            values.put(CONST_PACK_CONFIG_FIELDLIST_field_value_configid, get.id)
//
//
//            val db = this.writableDatabase
//            val result = db.insert(CONST_PACKCONFIG_FIELDLIST_field_list_TABLE, null, values)
//            db.close()
//            if (result >= 0) {
//                AppUtils.logDebug(TAG, "ADDed addPackConfigffIELD_field_list to database ")
//            } else {
//                AppUtils.logDebug(TAG, "Failed to Add addPackConfigffIELD_field_list to database ")
//            }
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//        }
//
//    }

//    fun addPackCommunityGroup(packcommunityGroup: PackCommunityList) {
//        try {
//
//            val values = ContentValues()
//            values.put(CONST_PACK_CommunityGroupID, packcommunityGroup.id)
//            values.put(CONST_PACK_CommunityGroupNAME, packcommunityGroup.name)
//            values.put(CONST_PACK_CommunityGroupcommunity_group, packcommunityGroup.community_group)
//
//
//            val db = this.writableDatabase
//            val result = db.insert(CONST_PACK_CommunityGroupTABLE, null, values)
//            db.close()
//            if (result >= 0) {
//                AppUtils.logDebug(TAG, "ADDed addPackCommunityGroup to database ")
//            } else {
//                AppUtils.logDebug(TAG, "Failed to Add addPackCommunityGroup to database ")
//            }
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//        }
//
//    }

//    fun dropPackConfigffIELDFieldList() {
//        val selectQuery = "DELETE FROM $CONST_PACKCONFIG_FIELDLIST_field_list_TABLE"
//        val db = this.writableDatabase
//
//        db.execSQL(selectQuery)
//    }


//    fun dropPackConfigffIELDList() {
//        val selectQuery = "DELETE FROM $CONST_PACKCONFIG_FIELDLIST_TABLE"
//        val db = this.writableDatabase
//
//        db.execSQL(selectQuery)
//    }

//    fun dropPackCommunityGroupTable() {
//        val selectQuery = "DELETE FROM $CONST_PACK_CommunityGroupTABLE"
//        val db = this.writableDatabase
//        db.execSQL(selectQuery)
//    }

//    @SuppressLint("Range")
//    fun readPackConfigFieldList(): ArrayList<PackConfigFieldList> {
//        val list: ArrayList<PackConfigFieldList> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_PACKCONFIG_FIELDLIST_TABLE"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//
//        var field_id: String
//        var field_name: String
//        var field_description: String
//        var field_type: String
//        var field_value: String
//
//
//
//
//        if (cursor.moveToFirst()) {
//            do {
//
//
//                field_id =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_id))
//                field_name =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_name))
//                field_description = cursor.getString(
//                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_description)
//                )
//                field_type =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_type))
//
//
//                val emptylist = ArrayList<PackFieldList>()
//
//                val lst = PackConfigFieldList(
//                    field_id, field_name, field_description,
//                    field_type, "", "", emptylist
//                )
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }

//    @SuppressLint("Range")
//    fun readPackConfigFieldList_fieldlist(): ArrayList<PackFieldList> {
//        val list: ArrayList<PackFieldList> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_PACKCONFIG_FIELDLIST_field_list_TABLE"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//
//        var field_id_ID: String
//        var field_name_NAME: String
//        var field_packid: String
//        var field_config_id: String
//        var field_id: String
//
//
//
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                field_id_ID =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_id_ID))
//                field_name_NAME = cursor.getString(
//                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_name_name)
//                )
//                field_packid = cursor.getString(
//                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid)
//                )
//                field_config_id = cursor.getString(
//                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_type_packid)
//                )
//                field_id = cursor.getString(
//                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_value_configid)
//                )
//
//
//                val lst = PackFieldList(
//                    field_id_ID, field_name_NAME, field_packid,
//                    field_config_id, field_id
//                )
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }

//    @SuppressLint("Range")

//    fun readPackCommunityGroup(): ArrayList<PackCommunityList> {
//        val list: ArrayList<PackCommunityList> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_PACK_CommunityGroupTABLE"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//
//        var commnutiyId: String
//        var commnutiyNAME: String
//        var commnutiycommunityGroup: String
//
//
//
//
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                commnutiyId = cursor.getString(cursor.getColumnIndex(CONST_PACK_CommunityGroupID))
//                commnutiyNAME =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CommunityGroupNAME))
//                commnutiycommunityGroup =
//                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CommunityGroupcommunity_group))
//
//
//                val lst = PackCommunityList(
//                    id = commnutiyId,
//                    name = commnutiyNAME,
//                    community_group = commnutiycommunityGroup
//                )
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }

    class DownloadFileFromURL(var filename: String?) :
        AsyncTask<String?, String?, String?>() {



        override fun onPreExecute() {
            super.onPreExecute()
            AppUtils.logDebug("##DownloadFile", "Before Downloading Downloading-----File")
        }

        override fun doInBackground(vararg f_url: String?): String? {
            var count: Int
            try {
                val url = URL(f_url[0])
                val connection = url.openConnection()
                connection.connect()
                val lenghtOfFile = connection.contentLength
                val input: InputStream = BufferedInputStream(url.openStream(), 8192)

//                File storage = new File(Environment.getExternalStorageDirectory() + File.separator + "/Download/");
                val storage = File(
                    Environment.getExternalStorageDirectory().toString() + File.separator + "/MyFarm/"
                )

                if (!storage.exists()) {
                    storage.mkdirs()
                }
                val FileName = "/" + filename

                localFilePath = storage.absolutePath.toString() + FileName

                val checkFileExistense = File(storage.absolutePath.toString() + FileName)

                Log.e("check_path", "" + localFilePath.toString() )

                if (checkFileExistense.isFile) {
                } else {

                    val output = FileOutputStream(storage.toString() + FileName)
                    val data = ByteArray(1024)
                    var total: Long = 0
                    while (input.read(data).also { count = it } != -1) {
                        total += count.toLong()
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (total * 100 / lenghtOfFile).toInt())
                        // writing data to file
                        output.write(data, 0, count)
                    }
                    // flushing output
                    output.flush()
                    // closing streams
                    output.close()
                    input.close()
                }

            } catch (e: java.lang.Exception) {
                Log.e("Error: ", e.message!!)
            }
            return null
        }


        override fun onPostExecute(file_url: String?) {

        }

    }
}

