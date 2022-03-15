package com.pbt.myfarm.DataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.HttpResponse.PackCommunityList
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.PackFieldList
import com.pbt.myfarm.ModelClass.*
import com.pbt.myfarm.PackConfigList
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_TABLENAME
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_collect_activity_id
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_created_at
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_created_by
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_deleted_at
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_deleted_by
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_duration
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_new_value
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_packid
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_result_class
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_resultid
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_sensor_id
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_unit_id
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_updated_at
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_updated_by
import com.pbt.myfarm.Util.AppConstant.Companion.COLLECTDATA_value
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
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_CREATED_BY
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_CREATED_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.COL_tasks_DELTED_AT
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
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACKCONFIG_LIST_TABLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACKS_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIGLIST_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIGLIST_ITEM
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PACK_CONFIGLIST_NAME
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
import com.pbt.myfarm.Util.AppConstant.Companion.PACKFIELDS_TABLENAME
import com.pbt.myfarm.Util.AppConstant.Companion.PACKFIELDS_fieldid
import com.pbt.myfarm.Util.AppConstant.Companion.PACKFIELDS_packid
import com.pbt.myfarm.Util.AppConstant.Companion.PACKFIELDS_value
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_CONFIGID
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_Created_at
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_DESC
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_Deleted_at
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_GROUP
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_NAMEPREFIX
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_PRIMARYKEY
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_Status
import com.pbt.myfarm.Util.AppConstant.Companion.PACKNEW_Updated_at
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
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_CREAT_PACK
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_collect_activity
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_config_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_configs
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_pack_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_config_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_config_functions
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_configs
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_task_fields
import com.pbt.myfarm.Util.AppConstant.Companion.TABLE_tasks
import com.pbt.myfarm.Util.AppConstant.Companion.colctactyrslt_unit_Key
import com.pbt.myfarm.Util.AppConstant.Companion.colctactyrslt_unit_TABLE
import com.pbt.myfarm.Util.AppConstant.Companion.colctactyrslt_unit_collectactiivtid
import com.pbt.myfarm.Util.AppConstant.Companion.colctactyrslt_unit_unitid
import com.pbt.myfarm.Util.AppUtils


class DbHelper(var context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, CONST_DATABASE_NAME, factory, CONST_DATABASE_VERSION) {

    private val TAG = "DbHelper"


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

        val packConfigListTable = ("CREATE TABLE " + CONST_PACKCONFIG_LIST_TABLE + " ("
                + CONST_PACK_CONFIGLIST_ITEM + " INTEGER PRIMARY KEY, " +
                CONST_PACK_CONFIGLIST_NAME + " TEXT," +
                CONST_PACK_CONFIGLIST_ID + " TEXT" + ")")

        db?.execSQL(packConfigListTable)


//ravi- offline table -------pack_configs------------------------------------->

        val pack_configs = ("CREATE TABLE " + TABLE_pack_configs + " ("
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

            //ravi -offline table ---pack_collect_activity

        val pack_collect_activity = ("CREATE TABLE " + TABLE_pack_collect_activity + " ("
                + COL_pack_collect_activity_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                COL_pack_collect_activity_SERVER_ID + " TEXT," +
                COL_pack_collect_activity_collect_activity_id + " TEXT," +
                COL_pack_collect_activity_pack_id + " TEXT " + ")")

        db?.execSQL(pack_collect_activity)
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
                + PACKNEW_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                PACKNEW_NAMEPREFIX + " TEXT," +
                PACKNEW_NAME + " TEXT," +
                PACKNEW_DESC + " TEXT," +
                PACKNEW_CONFIGID + " TEXT," +
                PACKNEW_GROUP + " TEXT," +
                PACKNEW_Status + " TEXT," +
                PACKNEW_Created_at + " TEXT," +
                PACKNEW_Updated_at + " TEXT," +
                PACKNEW_Deleted_at + " TEXT" + ")")
        db?.execSQL(pack_new)

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

        val collectdata = ("CREATE TABLE " + COLLECTDATA_TABLENAME + " ("
                + COLLECTDATA_PRIMARYKEY + " INTEGER PRIMARY KEY, " +
                COLLECTDATA_packid + " TEXT," +
                COLLECTDATA_resultid + " TEXT," +
                COLLECTDATA_result_class + " TEXT," +
                COLLECTDATA_collect_activity_id + " TEXT," +
                COLLECTDATA_new_value + " TEXT," +
                COLLECTDATA_value + " TEXT," +
                COLLECTDATA_unit_id + " TEXT," +
                COLLECTDATA_sensor_id + " TEXT," +
                COLLECTDATA_duration + " TEXT," +
                COLLECTDATA_updated_by + " TEXT," +
                COLLECTDATA_created_by + " TEXT," +
                COLLECTDATA_deleted_by + " TEXT," +
                COLLECTDATA_created_at + " TEXT," +
                COLLECTDATA_deleted_at + " TEXT," +
                COLLECTDATA_updated_at + " TEXT" + ")")
        db?.execSQL(collectdata)

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


        val collect_activity_results_unit = ("CREATE TABLE " + colctactyrslt_unit_TABLE + " ("
                + colctactyrslt_unit_Key + " INTEGER PRIMARY KEY, " +
                colctactyrslt_unit_collectactiivtid + " TEXT," +
                colctactyrslt_unit_unitid + " TEXT" + ")")
        db?.execSQL(collect_activity_results_unit)




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

        val selectQuery = "SELECT  * FROM $TABLE_CREAT_PACK WHERE $PACKNEW_CONFIGID =$configid "

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
                myid = cursor.getString(cursor.getColumnIndex(PACKNEW_NAME))
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
        fun addNewPack(pack : PacksNew) {
        AppUtils.logDebug(TAG, "viewTask====" +Gson().toJson(pack))

        try {
            val values = ContentValues()
            values.put(PACKNEW_NAME, pack.name)
            values.put(PACKNEW_CONFIGID, pack.pack_config_id)
            values.put(PACKNEW_Created_at, pack.created_date)
            values.put(PACKNEW_Deleted_at, pack.deleted_at)
            values.put(PACKNEW_Updated_at,pack.last_changed_date)
            values.put(PACKNEW_DESC, pack.description)
            values.put(PACKNEW_GROUP, pack.com_group)
            values.put(PACKNEW_Status, "0")

            val db = this.writableDatabase
            val result = db.insert(TABLE_CREAT_PACK, null, values)
            db.close()
            if (result >= 0) {
                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi - offline ----------insert
    fun pack_configscreate(pack : PackConfig) {
        AppUtils.logDebug(TAG, "viewTask====" +Gson().toJson(pack))

        try {

            val values = ContentValues()
            values.put(COL_pack_configs_SERVER_ID, pack.id)
            values.put(COL_pack_configs_NAME, pack.name)
            values.put(COL_pack_configs_DESCIPTION, pack.description)
            values.put(COL_pack_configs_TYPE, pack.type)
            values.put(COL_pack_configs_CLASS,pack.`class`)
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
            val result = db.insert(TABLE_pack_configs, null, values)
            db.close()
            if (result >= 0) {
                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }


  //ravi - offline ----------insert--table---pack_collect_activity

    fun pack_collect_activity_create(pack : PackCollectActivity) {

        try {
            val values = ContentValues()
            values.put(COL_pack_collect_activity_SERVER_ID, pack.id)
            values.put(COL_pack_collect_activity_collect_activity_id, pack.collect_activity_id)
            values.put(COL_pack_collect_activity_pack_id,pack.pack_id)

            val db = this.writableDatabase
            val result = db.insert(TABLE_pack_collect_activity, null, values)
            db.close()
            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---pack_fields

    fun pack_fields_create(pack : PackField) {

        try {
            val values = ContentValues()
            values.put(COL_pack_fields_pack_id,pack.pack_id)
            values.put(COL_pack_fields_value,pack.value)
            values.put(COL_pack_fields_SERVERID,pack.id)
            values.put(COL_pack_fields_field_id,pack.field_id)

            val db = this.writableDatabase
            val result = db.insert(TABLE_pack_fields, null, values)
            db.close()
            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
  //ravi - offline ----------insert--table---pack_config_fields

    fun pack_config_fields_create(pack : PackConfigField) {

        try {

            val values = ContentValues()
            values.put(COL_pack_config_fields_SERVER_ID, pack.id)
            values.put(COL_pack_config_fields_pack_config_id, pack.pack_config_id)
            values.put(COL_pack_config_fields_field_name, pack.field_name)
            values.put(COL_pack_config_fields_field_description, pack.field_description)
            values.put(COL_pack_config_fields_field_type, pack.field_type)
            values.put(COL_pack_config_fields_default_value, pack.default_value)
            values.put(COL_pack_config_fields_created_by, pack.created_by)
            values.put(COL_pack_config_fields_last_changed_by,pack.last_changed_by)
            values.put(COL_pack_config_fields_last_changed_date,pack.last_changed_date)
            values.put(COL_pack_config_fields_created_date,pack.created_by)
            values.put(COL_pack_config_fields_editable,pack.editable)
            values.put(COL_pack_config_fields_list,pack.list)
            values.put(COL_pack_config_fields_deleted_at,pack.deleted_at)


            val db = this.writableDatabase
            val result = db.insert(TABLE_pack_config_fields, null, values)
            db.close()
            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---task_configs


    fun task_configs_create(pack : TaskConfig) {

        try {

            val values = ContentValues()
            values.put(COL_task_configs_SERVERID, pack.id)
            values.put(COL_task_configs_NAME, pack.name)
            values.put(COL_task_configs_DESCIPTION, pack.description)
            values.put(COL_task_configs_TYPE, pack.type)
            values.put(COL_task_configs_CLASS, pack.nclass)
            values.put(COL_task_configs_COM_GROUP, pack.com_group)
            values.put(COL_task_configs_NAME_PREFIX, pack.name_prefix)
            values.put(COL_task_configs_RECORD_EVENT,pack.record_event)
            values.put(COL_task_configs_REPORTABLE,pack.reportable)
            values.put(COL_task_configs_CREATED_BY,pack.created_by)
            values.put(COL_task_configs_CREATED_DATE,pack.created_date)
            values.put(COL_task_configs_LAST_CHANGED_BY,pack.last_changed_by)
            values.put(COL_task_configs_LAST_CHANGED_DATE,pack.last_changed_date)
            values.put(COL_task_configs__LAST_DELETED_AT,pack.deleted_at)


            val db = this.writableDatabase
            val result = db.insert(TABLE_task_configs, null, values)
            db.close()
            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }

    //ravi - offline ----------insert--table---task_config_fields


    fun task_config_fields_create(pack : TaskConfigField) {

        try {

            val values = ContentValues()
            values.put(COL_task_config_fields_SERVERID, pack.id)
            values.put(COL_task_config_fields_task_config_id, pack.task_config_id)
            values.put(COL_task_config_fields_field_name, pack.field_name)
            values.put(COL_task_config_fields_field_description, pack.field_description)
            values.put(COL_task_config_fields_editable, pack.editable)
            values.put(COL_task_config_fields_field_type, pack.field_type)
            values.put(COL_task_config_fields_list, pack.list)
            values.put(COL_task_config_fields_created_by,pack.created_by)
            values.put(COL_task_config_fields_created_date,pack.created_date)
            values.put(COL_task_config_fields_last_changed_by,pack.last_changed_by)
            values.put(COL_task_config_fields_last_changed_date,pack.last_changed_date)
            values.put(COL_task_config_fields_deleted_at,pack.deleted_at)



            val db = this.writableDatabase
            val result = db.insert(TABLE_task_config_fields, null, values)
            db.close()
            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
   //ravi - offline ----------insert--table---task_config_functions


    fun task_config_functions_create(pack : TaskConfigFunction) {

        try {

            val values = ContentValues()
            values.put(COL_task_config_functions_SERVERKEY, pack.id)
            values.put(COL_task_config_functions_task_config_id, pack.task_config_id)
            values.put(COL_task_config_functions_task_name, pack.name)
            values.put(COL_task_config_functions_description, pack.description)
            values.put(COL_task_config_functions_privilege, pack.privilege)
            values.put(COL_task_config_functions_created_by, pack.created_by)
            values.put(COL_task_config_functions_created_date, pack.created_date)
            values.put(COL_task_config_functions_last_changed_by,pack.last_changed_by)
            values.put(COL_task_config_functions_deleted_at,pack.deleted_at)
            values.put(COL_task_config_functions_last_changed_date,pack.last_changed_date)



            val db = this.writableDatabase
            val result = db.insert(TABLE_task_config_functions, null, values)
            db.close()
            if (result >= 0) {
//                Toast.makeText(context, "Added PackSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }



    fun addpackFieldValue(id: String, packid: String, value: String) {
        try {
            val values = ContentValues()
            values.put(PACKFIELDS_fieldid, id)
            values.put(PACKFIELDS_packid, packid)
            values.put(PACKFIELDS_value, value)

            val db = this.writableDatabase
            val result = db.insert(PACKFIELDS_TABLENAME, null, values)
            db.close()
            if (result >= 0) {
                Toast.makeText(context, "Added PackFieldSuccessfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }
    }
    //ravi offline -----insert---table-----tasks//

    fun tasks_create(task: Task) {

        try {
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
            val result = db.insert(TABLE_tasks, null, values)
            db.close()
            if (result >= 0) {
                Toast.makeText(context, "Added TAskSuccessfully", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }
//offline--ravi -------insert---table------task_field
    fun task_fields_create(task:TaskField) {

        try {
            val values = ContentValues()


            values.put(COL_task_fields_SERVERID, task.id)
            values.put(COL_task_fields_TASKID, task.task_id)
            values.put(COL_task_fields_FIELDID, task.field_id)
            values.put(COL_task_fields_VALUE, task.value)


            val db = this.writableDatabase
            val result = db.insert(TABLE_task_fields, null, values)
            db.close()
            if (result >= 0) {
                Toast.makeText(context, "Added TaskFieldSuccessfully", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }


    }

    //    fun addCollectData(data: CollectDataModel){
//        try {
//
//            val values = ContentValues()
//            values.put(  CONST_ACTIVITY, data.activity)
//            values.put(CONST_RESULT, data.result)
//            values.put(CONST_VALUE, data.value)
//            values.put(CONST_UNITS, data.units)
//            values.put(CONST_SENSOR, data.sensor)
//            values.put(CONST_DURATION, data.duration)
//
//
//            val db = this.writableDatabase
//            val result = db.insert(CONST_TABLE_COLLECT, null, values)
//            db.close()
//            AppUtils.logDebug(TAG, result.toString())
//            if (result >= 0) {
//                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, UpdatePackActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
//
//
//
//            } else {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//
//            }
//        }catch (e:Exception){
//    }}
    @SuppressLint("Range")
//    fun readCollectData(): ArrayList<CollectDataModel> {
//        val list: ArrayList<CollectDataModel> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $CONST_TABLE_COLLECT"
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery(query, null)
//        } catch (e: Exception) {
//            AppUtils.logError(TAG, e.message!!)
//            db.execSQL(query)
//            return ArrayList()
//        }
//        var activity: String
//        var result: String
//        var value: String
//
//        var units: String
//        var sensor: String
//        var duration: String
//
//
//
//        if (cursor.moveToFirst()) {
//            do {
//                activity = cursor.getString(cursor.getColumnIndex(CONST_ACTIVITY))
//                result = cursor.getString(cursor.getColumnIndex(CONST_RESULT))
//                value= cursor.getString(cursor.getColumnIndex(CONST_VALUE))
//                units=cursor.getString(cursor.getColumnIndex(CONST_UNITS))
//                sensor=cursor.getString(cursor.getColumnIndex(CONST_SENSOR))
//                duration=cursor.getString(cursor.getColumnIndex(CONST_DURATION))
//
//
//                val lst = CollectDataModel(
//
//                    activity = activity, result = result,
//                    value = value,sensor = sensor,
//                    units = units,duration = duration
//                )
//                list.add(lst)
//            } while (cursor.moveToNext())
//        }
//        return list
//
//    }


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

    fun updateTask(task: ViewTaskModelClass, entryname: String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CONST_TASK_NAME, task.ENTRYNAME)
        contentValues.put(CONST_TASK_TYPE, task.ENTRYTYPE)
        contentValues.put(CONST_TASK_DETAIL, task.ENTRYDETAIL)
        contentValues.put(CONST_EXPECTED_EXP_STR_DATE, task.ExpectedStartDate)
        contentValues.put(CONST_EXPECTED_EXP_END_DATE, task.ExpectedEndDate)
        contentValues.put(CONST_EXPECTED_STR_DATE, task.StartDate)
        contentValues.put(CONST_EXPECTED_END_DATE, task.EndDate)
//        val success=db.update(CONST_NEW_TASK,contentValues,"$CONST_TASK_NAME="+task.ENTRYNAME,
//    null)
        val success =
            db.update(CONST_NEW_TASK, contentValues, "$CONST_TASK_NAME = ?", arrayOf(entryname))
        db.close()
        return success
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

    @SuppressLint("Range")
    fun readData(): ArrayList<ViewTaskModelClass> {
        val list: ArrayList<ViewTaskModelClass> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_NEW_TASK"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        var taskname: String
        var tasktype: String
        var taskdetail: String
        var expectedStartDate: String
        var expectedEndDate: String
        var startdate: String
        var endDate: String
        if (cursor.moveToFirst()) {
            do {
                taskname = cursor.getString(cursor.getColumnIndex(CONST_TASK_NAME))
                tasktype = cursor.getString(cursor.getColumnIndex(CONST_TASK_TYPE))
                taskdetail = cursor.getString(cursor.getColumnIndex(CONST_TASK_DETAIL))
                expectedStartDate = cursor.getString(
                    cursor.getColumnIndex(
                        CONST_EXPECTED_EXP_STR_DATE
                    )
                )
                expectedEndDate =
                    cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_EXP_END_DATE))
                startdate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_STR_DATE))
                endDate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_END_DATE))
                val lst = ViewTaskModelClass(
                    ENTRYDETAIL = taskdetail, ENTRYNAME = taskname,
                    ENTRYTYPE = tasktype, ExpectedStartDate = expectedStartDate,
                    ExpectedEndDate = expectedEndDate, StartDate = startdate,
                    EndDate = endDate
                )
                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }

    @SuppressLint("Range")
    fun readPackData(): ArrayList<ViewPackModelClass> {
        val list: ArrayList<ViewPackModelClass> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_TABLE_PACK"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        var PACK_NAME: String
        var PACKS_ID: String
        var PACK_DETAIL: String
        var PACK_TYPE: String
        var PACK_TYPE_ID: String
        var PACK_GROUP: String
        var PACK_NAME_PREFIX: String
        var PACK_LABELTYPE: String
        var PACK_LABEL_DESCIPTION: String
        var PACK_PADZERO: String
        var PACK_NAME_PREFIX_padzero: String
        var PACK_CREATEDBY: String



        if (cursor.moveToFirst()) {
            do {

                PACK_NAME = cursor.getString(cursor.getColumnIndex(CONST_PACK_NAME))
                PACKS_ID = cursor.getString(cursor.getColumnIndex(CONST_PACKS_ID))
                PACK_DETAIL = cursor.getString(cursor.getColumnIndex(CONST_PACK_DETAIL))
                PACK_TYPE = cursor.getString(cursor.getColumnIndex(CONST_PACK_TYPE))
                PACK_TYPE_ID = cursor.getString(cursor.getColumnIndex(CONST_PACK_TYPE_ID))
                PACK_GROUP = cursor.getString(cursor.getColumnIndex(CONST_PACK_GROUP))
                PACK_NAME_PREFIX = cursor.getString(cursor.getColumnIndex(CONST_PACK_NAME_PREFIX))
                PACK_LABELTYPE = cursor.getString(cursor.getColumnIndex(CONST_PACK_LABELTYPE))
                PACK_LABEL_DESCIPTION =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_LABEL_DESCIPTION))
                PACK_PADZERO = cursor.getString(cursor.getColumnIndex(CONST_PACK_PADZERO))
                PACK_CREATEDBY = cursor.getString(cursor.getColumnIndex(CONST_PACK_CREATEDBY))
                PACK_NAME_PREFIX_padzero =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_NAME_PREFIX_padzero))

                val lst = ViewPackModelClass(
                    name = PACK_NAME, id = PACKS_ID,
                    description = PACK_DETAIL,
                    pack_config_name = PACK_TYPE,
                    pack_config_id = PACK_TYPE_ID,
                    com_group = PACK_GROUP,
                    name_prefix = PACK_NAME_PREFIX,
                    type = PACK_LABELTYPE,
                    labeldesciption = PACK_LABEL_DESCIPTION,
                    padzero = PACK_NAME_PREFIX_padzero, created_by = PACK_CREATEDBY

                )
                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }


    fun deleteTask(taskname: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONST_TASK_NAME, taskname)
        db.delete(CONST_NEW_TASK, "taskname=?", arrayOf(taskname))
        db.close()

    }

    fun deletenewPack(packid: String, configid: String) {

        val db = this.writableDatabase
        val success = db.delete(
            TABLE_CREAT_PACK,
            PACKNEW_CONFIGID + "=" + configid + " and " + PACKNEW_NAME + "=" + packid,
            null
        )
        if (success > 0) {
            Toast.makeText(context, "DeleteSuccessFull", Toast.LENGTH_SHORT).show()
        }
        db.close()

    }


    fun deletePack(taskname: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONST_PACK_NAME, taskname)
        val succ = db.delete(CONST_TABLE_PACK, "PACKname=?", arrayOf(taskname))

        db.close()

    }

    fun dropPackTable() {

        val selectQuery = "DELETE FROM $CONST_TABLE_PACK"
        val db = this.writableDatabase

        db.execSQL(selectQuery)
    }

    fun addPackConfigList(packconfiglist: PackConfigList) {
        try {

            val values = ContentValues()
            values.put(CONST_PACK_CONFIGLIST_NAME, packconfiglist.name)
            values.put(CONST_PACK_CONFIGLIST_ID, packconfiglist.id)


            val db = this.writableDatabase
            val result = db.insert(CONST_PACKCONFIG_LIST_TABLE, null, values)
            db.close()
            if (result >= 0) {
                AppUtils.logDebug(TAG, "ADDed packconfiglist to database ")
            } else {
                AppUtils.logDebug(TAG, "Failed to Add packconfiglist to database ")
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }

    }

    @SuppressLint("Range")
    fun readPackConfiglistData(): ArrayList<PackConfigList> {
        val list: ArrayList<PackConfigList> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_PACKCONFIG_LIST_TABLE"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        var config_NAME: String
        var config_ID: String

        if (cursor.moveToFirst()) {
            do {

                config_NAME = cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIGLIST_NAME))
                config_ID = cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIGLIST_ID))

                val lst = PackConfigList(name = config_NAME, id = config_ID)

                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }
//offline -ravi   drop----------table----packs_new------->
    fun droptable_packnew() {
        val selectQuery = "DELETE FROM $TABLE_CREAT_PACK"
        val db = this.writableDatabase

        db.execSQL(selectQuery)
    }
    //--------------------------------//

    //offline-ravi   drop----table-packconfig-----//
    fun droptable_pack_configs() {
        val selectQuery = "DELETE FROM $TABLE_pack_configs"
        val db = this.writableDatabase

        db.execSQL(selectQuery)
    }
    //---------//

    fun addPackConfigffIELDList(packconfiglist: PackConfigFieldList) {
        try {


            val values = ContentValues()
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_id, packconfiglist.field_id)
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_name, packconfiglist.field_name)
            values.put(
                CONST_PACK_CONFIG_FIELDLIST_field_description,
                packconfiglist.field_description
            )
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_type, packconfiglist.field_type)
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_value, packconfiglist.field_value)


            val db = this.writableDatabase
            val result = db.insert(CONST_PACKCONFIG_FIELDLIST_TABLE, null, values)
            db.close()
            if (result >= 0) {
                AppUtils.logDebug(TAG, "ADDed packconfigFieldlist to database ")
            } else {
                AppUtils.logDebug(TAG, "Failed to Add packconfigFieldlist to database ")
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }

    }

    fun addPackConfigffIELD_field_list(
        packconfiglist: PackFieldList,
        routes: String,
        get: PackConfigList
    ) {
        try {

            val values = ContentValues()
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_id_ID, packconfiglist.id)
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_name_name, packconfiglist.name)
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid, routes)
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_type_packid, get.id)
            values.put(CONST_PACK_CONFIG_FIELDLIST_field_value_configid, get.id)


            val db = this.writableDatabase
            val result = db.insert(CONST_PACKCONFIG_FIELDLIST_field_list_TABLE, null, values)
            db.close()
            if (result >= 0) {
                AppUtils.logDebug(TAG, "ADDed addPackConfigffIELD_field_list to database ")
            } else {
                AppUtils.logDebug(TAG, "Failed to Add addPackConfigffIELD_field_list to database ")
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }

    }

    fun addPackCommunityGroup(packcommunityGroup: PackCommunityList) {
        try {

            val values = ContentValues()
            values.put(CONST_PACK_CommunityGroupID, packcommunityGroup.id)
            values.put(CONST_PACK_CommunityGroupNAME, packcommunityGroup.name)
            values.put(CONST_PACK_CommunityGroupcommunity_group, packcommunityGroup.community_group)


            val db = this.writableDatabase
            val result = db.insert(CONST_PACK_CommunityGroupTABLE, null, values)
            db.close()
            if (result >= 0) {
                AppUtils.logDebug(TAG, "ADDed addPackCommunityGroup to database ")
            } else {
                AppUtils.logDebug(TAG, "Failed to Add addPackCommunityGroup to database ")
            }
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
        }

    }

    fun dropPackConfigffIELDFieldList() {
        val selectQuery = "DELETE FROM $CONST_PACKCONFIG_FIELDLIST_field_list_TABLE"
        val db = this.writableDatabase

        db.execSQL(selectQuery)
    }


    fun dropPackConfigffIELDList() {
        val selectQuery = "DELETE FROM $CONST_PACKCONFIG_FIELDLIST_TABLE"
        val db = this.writableDatabase

        db.execSQL(selectQuery)
    }

    fun dropPackCommunityGroupTable() {
        val selectQuery = "DELETE FROM $CONST_PACK_CommunityGroupTABLE"
        val db = this.writableDatabase
        db.execSQL(selectQuery)
    }

    @SuppressLint("Range")
    fun readPackConfigFieldList(): ArrayList<PackConfigFieldList> {
        val list: ArrayList<PackConfigFieldList> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_PACKCONFIG_FIELDLIST_TABLE"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        var field_id: String
        var field_name: String
        var field_description: String
        var field_type: String
        var field_value: String




        if (cursor.moveToFirst()) {
            do {


                field_id =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_id))
                field_name =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_name))
                field_description = cursor.getString(
                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_description)
                )
                field_type =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_type))


                val emptylist = ArrayList<PackFieldList>()

                val lst = PackConfigFieldList(
                    field_id, field_name, field_description,
                    field_type, "", "", emptylist
                )
                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }

    @SuppressLint("Range")
    fun readPackConfigFieldList_fieldlist(): ArrayList<PackFieldList> {
        val list: ArrayList<PackFieldList> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_PACKCONFIG_FIELDLIST_field_list_TABLE"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        var field_id_ID: String
        var field_name_NAME: String
        var field_packid: String
        var field_config_id: String
        var field_id: String




        if (cursor.moveToFirst()) {
            do {

                field_id_ID =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_id_ID))
                field_name_NAME = cursor.getString(
                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_name_name)
                )
                field_packid = cursor.getString(
                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid)
                )
                field_config_id = cursor.getString(
                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_type_packid)
                )
                field_id = cursor.getString(
                    cursor.getColumnIndex(CONST_PACK_CONFIG_FIELDLIST_field_value_configid)
                )


                val lst = PackFieldList(
                    field_id_ID, field_name_NAME, field_packid,
                    field_config_id, field_id
                )
                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }

    @SuppressLint("Range")

    fun readPackCommunityGroup(): ArrayList<PackCommunityList> {
        val list: ArrayList<PackCommunityList> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_PACK_CommunityGroupTABLE"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }

        var commnutiyId: String
        var commnutiyNAME: String
        var commnutiycommunityGroup: String





        if (cursor.moveToFirst()) {
            do {

                commnutiyId = cursor.getString(cursor.getColumnIndex(CONST_PACK_CommunityGroupID))
                commnutiyNAME =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CommunityGroupNAME))
                commnutiycommunityGroup =
                    cursor.getString(cursor.getColumnIndex(CONST_PACK_CommunityGroupcommunity_group))


                val lst = PackCommunityList(
                    id = commnutiyId,
                    name = commnutiyNAME,
                    community_group = commnutiycommunityGroup
                )
                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }

}