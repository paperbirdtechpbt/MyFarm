package com.pbt.myfarm.Util

class AppConstant {
    companion object {
        //Database
        const val CONST_ROLEID = "adminroleid"

        const val CONST_DATABASE_NAME = "myfarm"
        const val CONST_DATABASE_VERSION = 1
        const val CONST_USERS_TABLE = "MyFarmUsers"
        const val CONST_ID = "id"
        const val CONST_USERNAME = "username"
        const val CONST_USERROLE = "userrole"
        const val CONST_USERPASS = "userpass"

           //packid
        val PACK_LIST_PACKID="packlistpackid"
        val CONST_SELECTED_COM_GROUP="selected_communityGroup"

        //SharePreference
        const val CONST_SHARED_PREF_NAME = "mysharedpreference"
        const val CONST_SHARED_PREF_TOKEN = "mysharedpreferenceToken"
        const val CONST_SHARED_PREF_USERNAME = "username"
        const val CONST_PREF_IS_LOGIN = "isLogin"
        const val CONST_PREF_ROLE_ID = "RoleId"
        const val CONST_PREF_ROLE_NAME = "RoleName"

        //TABLE NewTask
        const val CONST_NEW_TASK = "newtask"
        const val CONST_USER_ID = "userid"
        const val CONST_TASK_NAME = "taskname"
        const val CONST_TASK_TYPE = "tasktype"
        const val CONST_TASK_DETAIL = "taskdetail"
        const val CONST_EXPECTED_EXP_STR_DATE = "expectedstartdate"
        const val CONST_EXPECTED_EXP_END_DATE = "expectedenddate"
        const val CONST_EXPECTED_STR_DATE = "startdate"
        const val CONST_EXPECTED_END_DATE = "enddate"


        //Intent PutExra
        const val CONST_CONFIGTYPE_NAME = "configtypename"
        const val CONST_CONFIGTYPE_TYPE_ID = "configtypeid"
        const val CONST_VIEWMODELCLASS_LIST = "ViewModelClassList"
        const val CONST_VIEWMODELCLASS_CONFIG_LIST = "ViewModelClassList"
        const val CONST_TASK_UPDATE = "taskupdate"
        const val CONST_TASK_UPDATE_BOOLEAN = "taskupdateboolean"
        const val CONST_TASK_UPDATE_LIST = "taskupdatelist"
        const val CONST_PACK_UPDATE_LIST = "packupdatelist"
        const val CONST_TASKFUNCTION_TASKID = "taskfunction"
        const val CONT_PACK = "packlist"

        //TABLE newpack
        const val CONST_TABLE_PACK = "tablepack"
        const val CONST_PACK_NAME = "PACKname"
        const val CONST_PACKS_ID = "PACKSID"
        const val CONST_PACK_TYPE_ID = "PACKtypeID"
        const val CONST_PACK_TYPE = "PACKtype"
        const val CONST_PACK_DETAIL = "PACKdetail"
        const val CONST_PACK_GROUP = "PACKgroup"
        const val CONST_PACK_NAME_PREFIX = "PACKnameprefix"
        const val CONST_PACK_LABELTYPE = "packlalbeltype"
        const val CONST_PACK_LABEL_DESCIPTION = "packlabeldesciption"
        const val CONST_PACK_PADZERO = "packlabelpadzero"
        const val CONST_PACK_NAME_PREFIX_padzero = "PACKnameprefixpadzero"
        const val CONST_PACK_CREATEDBY = "packCreatedBy"
        const val CONST_PACK_ID = "packid"
        const val CONST_PACK_CUSTOMER = "packcustomer"
        const val CONST_PACK_UNITS = "packunit"
        const val CONST_PACK_QUANTITY = "packquanity"
        const val CONST_LIST_SIZE = "listsize"

        //collect data
        const val CONST_TABLE_COLLECT = "collectid"
        const val CONST_COLLECT_ID = "collectid"
        const val CONST_ACTIVITY = "collectactivity"
        const val CONST_RESULT = "collectresult"
        const val CONST_VALUE = "collectvalue"
        const val CONST_UNITS = "collectunits"
        const val CONST_SENSOR = "collectsensor"
        const val CONST_DURATION = "collectduration"

        //PACk CONFIGLIST TABLE
        const val CONST_PACKCONFIG_LIST_TABLE = "CONST_PACKCONFIG_LIST_TABLE"
        const val CONST_PACK_CONFIGLIST_ITEM = "CONST_PACK_CONFIGLIST_ITEM"
        const val CONST_PACK_CONFIGLIST_NAME = "CONST_PACK_CONFIGLIST_NAME"
        const val CONST_PACK_CONFIGLIST_ID = "CONST_PACK_CONFIGLIST_ID"

        //Pack ConfigFieldlist
        const val CONST_PACKCONFIG_FIELDLIST_TABLE = "CONST_PACKCONFIG_FIELDLIST_TABLE"
        const val CONST_PACK_CONFIG_FIELDLIST_ITEM = "CONST_PACK_CONFIG_FIELDLIST_ITEM"
        const val CONST_PACK_CONFIG_FIELDLIST_field_id = "CONST_PACK_CONFIG_FIELDLIST_field_id"
        const val CONST_PACK_CONFIG_FIELDLIST_field_name = "CONST_PACK_CONFIG_FIELDLIST_field_name"
        const val CONST_PACK_CONFIG_FIELDLIST_field_description =
            "CONST_PACK_CONFIG_FIELDLIST_field_description"
        const val CONST_PACK_CONFIG_FIELDLIST_field_type = "CONST_PACK_CONFIG_FIELDLIST_field_type"
        const val CONST_PACK_CONFIG_FIELDLIST_field_value =
            "CONST_PACK_CONFIG_FIELDLIST_field_value"

        //Pack ConfigFieldlist_field_list
        const val CONST_PACKCONFIG_FIELDLIST_field_list_TABLE =
            "CONST_PACKCONFIG_FIELDLIST_field_list_TABLE"
        const val CONST_PACK_CONFIG_FIELDLIST_fieldlist_ITEM =
            "CONST_PACK_CONFIG_FIELDLIST_fieldlist_ITEM"
        const val CONST_PACK_CONFIG_FIELDLIST_field_id_ID =
            "CONST_PACK_CONFIG_FIELDLIST_field_id_ID"
        const val CONST_PACK_CONFIG_FIELDLIST_field_name_name =
            "CONST_PACK_CONFIG_FIELDLIST_field_name_name"
        const val CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid =
            "CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid"
        const val CONST_PACK_CONFIG_FIELDLIST_field_type_packid =
            "CONST_PACK_CONFIG_FIELDLIST_field_type_packid"
        const val CONST_PACK_CONFIG_FIELDLIST_field_value_configid =
            "CONST_PACK_CONFIG_FIELDLIST_field_value_configid"


        //communitygroup
        const val CONST_PACK_CommunityGroupTABLE = "CONST_PACK_CommunityGroupTABLE"
        const val CONST_PACK_CommunityGroupID = "CONST_PACK_CommunityGroupID"
        const val CONST_PACK_CommunityGroupITEM_ID = "CONST_PACK_CommunityGroupITEM_ID"
        const val CONST_PACK_CommunityGroupNAME = "CONST_PACK_CommunityGroupNAME"
        const val CONST_PACK_CommunityGroupcommunity_group =
            "CONST_PACK_CommunityGroupcommunity_group"

        //event
        const val CONST_EDITEVENT_ID = "editeventid"
        const val CONST_CREATEEVENT = "createevent"

        //TABLE
        const val TABLE_CREAT_PACK = "packs_new"
        const val COL_LOCAL_ID = "packnewprimarykey"
        const val COL_ID = "packnew_serverid"
        const val COL_PACK_DESC = "packnewdesc"
        const val COL_PACK_CONFIG_ID = "packnewid"
        const val COL_PACK_GROUP = "packnewGroup"
        const val COL_PACKNEW_Status = "Status"
        const val COL_PACK_NAME = "PACKNEW_NAME"
        const val COL_PACK_CREATED_BY = "Created_at"
        const val COL_PACK_LAST_CHANGED_BY = "Updated_at"
        const val COL_PACK_NEW_DLETED_AT = "Deleted_at"


        const val COL_PACK_IS_ACTIVE = "pack_new_is_Active"
        const val COL_PACK_CREATED_DATE = "pack_new_isActive"
        const val COL_PACK_LAST_CHANGED_DATE = "pack_new_last_changed_Date"

        //TABLE_packFields
        const val PACKFIELDS_TABLENAME = "packfieldtablename"
        const val PACKFIELDS_PRIMARYKEY = "packfieldsprimarykey"
        const val PACKFIELDS_packid = "packfieldpackid"
        const val PACKFIELDS_fieldid = "packfieldfieldid"
        const val PACKFIELDS_value = "packfieldvalue"

        //collect_data TABLE
        const val COLLECTDATA_TABLENAME = "COLLECTDATA_TABLENAME"
        const val COLLECTDATA_PRIMARYKEY = "id"
        const val COLLECTDATA_packid = "packid"
        const val COLLECTDATA_resultid = "result_id"
        const val COLLECTDATA_result_class = "result_class"
        const val COLLECTDATA_collect_activity_id = "collect_activity_id"
        const val COLLECTDATA_new_value = "new_value"
        const val COLLECTDATA_value = "value"
        const val COLLECTDATA_unit_id = "unit_id"
        const val COLLECTDATA_sensor_id = "sensor_id"
        const val COLLECTDATA_duration = "duration"
        const val COLLECTDATA_updated_by = "updated_by"
        const val COLLECTDATA_created_by = "created_by"
        const val COLLECTDATA_deleted_by = "deleted_by"
        const val COLLECTDATA_created_at = "created_at"
        const val COLLECTDATA_updated_at = "updated_at"
        const val COLLECTDATA_deleted_at = "deleted_at"

        //collect_activity_results
        const val colctactyrslt_TABLE = "collect_activity_results"
        const val colctactyrslt_Key = "id"
        const val colctactyrslt_collectactiivtid = "collect_activity_id"
        const val colctactyrslt_result_name = "resultname"
        const val colctactyrslt_unit_id = "unit_id"
        const val colctactyrslt_type_id = "collect_activity_results"
        const val colctactyrslt_list_id = "collect_activity_results"
        const val colctactyrslt_result_class = "collect_activity_results"
        const val colctactyrslt_created_by = "created_by"
        const val colctactyrslt_deleted_by = "deleted_by"
        const val colctactyrslt_created_at = "created_at"
        const val colctactyrslt_updated_at = "updated_at"
        const val colctactyrslt_deleted_at = "deleted_at"

        //collect_activity_result_unit
        const val colctactyrslt_unit_TABLE = "collect_activity_results_unit"
        const val colctactyrslt_unit_Key = "id"
        const val colctactyrslt_unit_collectactiivtid = "collect_activity_result_id"
        const val colctactyrslt_unit_unitid = "unit_id"

        //pack_config_fields

        const val COL_SERVER_ID = "packconfig_server_id"
        const val COL_PACKCONFIGPRIMARY_ID = "packconfig_primary_id"
        const val TABLE_PACK_CONFIG_FIELDS = "pack_config_fields"
        const val COL_PACKCONFIG_ID = "packconfigid"
        const val COL_FIELD_NAME = "field_name"
        const val COL_field_description = "field_description"
        const val COL_field_type = "field_type"
        const val COL_list = "list"
        const val COL_default_value = "default_value"
        const val COL_created_by = "created_by"
        const val COL_created_date = "created_date"
        const val COL_last_changed_by = "last_changed_by"
        const val COL_last_changed_date = "last_changed_date"
        const val COL_eleted_at = "deleted_at"

        //ravi offline- table --pack_configs
        const val TABLE_PACKCONFIG = "packconfigs"
        const val COL_pack_configs_SERVER_ID = "pack_configs_server_id"
        const val COL_pack_configs_PRIMARY_ID = "pack_configs_primary_id"
        const val COL_pack_configs_NAME = "pack_configs_name"
        const val COL_pack_configs_DESCIPTION = "pack_configs_desciption"
        const val COL_pack_configs_TYPE = "pack_configs_type"
        const val COL_pack_configs_CLASS = "pack_configs_class"
        const val COL_pack_configs_COMGROUP = "pack_configs_com_group"
        const val COL_pack_configs_NAMEPREFIX = "pack_configs_nameprefix"
        const val COL_pack_configs_COLLECTACTIVITY_ID = "pack_configs_collectivityid"
        const val COL_pack_configs_GRAPHCHCHART_ID = "pack_configs_graph_chart_id"
        const val COL_pack_configs_CREATEDBY = "pack_configs_created_by"
        const val COL_pack_configs_CREATED_DATE = "pack_configs_created_date"
        const val COL_pack_configs_LAST_CHANGED_BY = "pack_configs_last_changed_by"
        const val COL_pack_configs_LAST_CHNAGED_DATE = "pack_configs_last_changed_date"
        const val COL_pack_configs_DELETED_AT = "pack_configs_deleted_at"

        //ravi offline --table--pack_collect_activity----

        const val TABLE_pack_collect_activity = "pack_collect_activity"
        const val COL_pack_collect_activity_SERVER_ID = "pack_collect_activity_server_id"
        const val COL_pack_collect_activity_PRIMARY_ID = "pack_collect_activity_primary_id"
        const val COL_pack_collect_activity_collect_activity_id =
            "pack_collect_activity_collect_activity_id"
        const val COL_pack_collect_activity_pack_id = "pack_collect_activity_pack_id"

        //ravi offline----table---pack_config_fields

        const val TABLE_pack_config_fields = "pack_config_fields"
        const val COL_pack_config_fields_PRIMARY_ID = "pack_config_fields_primary_id"
        const val COL_pack_config_fields_SERVER_ID = "pack_config_fields_server_id"
        const val COL_pack_config_fields_pack_config_id = "pack_config_fields_pack_config_id"
        const val COL_pack_config_fields_field_name = "pack_config_fields_field_name"
        const val COL_pack_config_fields_field_description = "pack_config_fields_field_description"
        const val COL_pack_config_fields_editable = "pack_config_fields_editable"
        const val COL_pack_config_fields_field_type = "pack_config_fields_field_type"
        const val COL_pack_config_fields_list = "pack_config_fields_config_list"
        const val COL_pack_config_fields_default_value = "pack_config_fields_default_value"
        const val COL_pack_config_fields_created_by = "pack_config_fields_created_by"
        const val COL_pack_config_fields_last_changed_by = "pack_config_fields_last_changed_by"
        const val COL_pack_config_fields_last_changed_date = "pack_config_fields_last_changed_date"
        const val COL_pack_config_fields_deleted_at = "pack_config_fields_deleted_at"
        const val COL_pack_config_fields_created_date = "pack_config_fields_created_date"

        //ravi offline----table---pack_fields

        const val TABLE_pack_fields = "pack_fields"
        const val COL_pack_fields_PRIMARY_ID = "pack_fields_primaryid"
        const val COL_pack_fields_SERVERID = "pack_fields_serverid"
        const val COL_pack_fields_STATUS = "pack_fields_status"
        const val COL_pack_fields_pack_id = "pack_fields_pack_id"
        const val COL_pack_fields_value = "pack_fields_value"
        const val COL_pack_fields_field_id = "pack_fields_field_id"

        //ravi offline----table---tasks

        const val TABLE_TASKS = "tasks"
        const val COL_TASKS_LOCAL_ID = "tasks_primarykey"
        const val COL_TASKS_ID = "tasks_serverid"
        const val COL_TASKS_NAME = "tasks_name"
        const val COL_TASKS_DESC = "tasks_descp"
        const val COL_TASKS_GROUP = "tasks_comgroup"
        const val COL_TASKS_TASK_CONFIG_ID = "tasks_task_configid"
        const val COL_TASKS_TASK_FUNCTION = "tasks_taskfun"
        const val COL_TASKS_STATUS = "tasks_status"
        const val COL_TASKS_SERVER_STATUS = "task_server_status"
        const val COL_TASKS_STARTED_LATE = "tasks_started_late"
        const val COL_TASKS_ENDED_LATE = "tasks_ended_late"
        const val COL_TASKS_CREATED_BY = "tasks_created_By"
        const val COL_TASKS_CREATED_DATE = "tasks_created_date"
        const val COL_TASKS_LAST_CHANGED_BY = "tasks_lastchangedby"
        const val COL_TASKS_LAST_CHANGED_DATE = "tasks_last_change_date"
        const val COL_TASKS_DELETED_AT = "tasks_deleted_At"

        //ravi offline----table---task_fields
        const val TABLE_TASK_FIELDS = "task_fields"
        const val COL_task_fields_PRIMARY = "task_fields_primary"
        const val COL_TASK_FIELDS_ID = "task_fields_id"
        const val COL_TASK_FIELDS_TASK_ID = "task_fields_task_id"
        const val COL_TASK_FIELDS_FIELD_ID = "task_fields_fieldid"
        const val COL_TASK_FIELDS_VALUE = "task_fields_value"
        const val COL_TASK_FIELDS_STATUS = "task_fields_status"


        //ravi offline----table---task_configs

        const val TABLE_task_configs = "task_configs"
        const val COL_task_configs_PRIMARYKEY = "task_configs_primary"
        const val COL_task_configs_SERVERID = "task_configs_serverid"
        const val COL_task_configs_NAME = "task_configs_name"
        const val COL_task_configs_DESCIPTION = "task_configs_description"
        const val COL_task_configs_TYPE = "task_configs_type"
        const val COL_task_configs_CLASS = "task_configs_class"
        const val COL_task_configs_COM_GROUP = "task_configs_com_group"
        const val COL_task_configs_NAME_PREFIX = "task_configs_name_prefix"
        const val COL_task_configs_RECORD_EVENT = "task_configs_record_event"
        const val COL_task_configs_REPORTABLE = "task_configs_reportable"
        const val COL_task_configs_CREATED_BY = "task_configs_created_by"
        const val COL_task_configs_CREATED_DATE = "task_configs_created_date"
        const val COL_task_configs_LAST_CHANGED_BY = "task_configs_last_changed_by"
        const val COL_task_configs_LAST_CHANGED_DATE = "task_configs_last_changed_date"
        const val COL_task_configs__LAST_DELETED_AT = "task_configs_last_deleted_at"

        //ravi offline----table---task_config_fields

        const val TABLE_task_config_fields = "task_config_fields"
        const val COL_task_config_fields_PRIMARYKEY = "task_config_fields_primary"
        const val COL_task_config_fields_SERVERID = "task_config_fields_serverid"
        const val COL_task_config_fields_task_config_id = "task_config_fields_task_config_id"
        const val COL_task_config_fields_field_name = "task_config_fields_field_name"
        const val COL_task_config_fields_field_description = "task_config_fields_field_description"
        const val COL_task_config_fields_editable = "task_config_fields_editable"
        const val COL_task_config_fields_field_type = "task_config_fields_field_type"
        const val COL_task_config_fields_list = "task_config_fields_list"
        const val COL_task_config_fields_created_by = "task_config_fields_created_by"
        const val COL_task_config_fields_created_date = "task_config_fields_created_date"
        const val COL_task_config_fields_last_changed_by = "task_config_fields_last_changed_by"
        const val COL_task_config_fields_last_changed_date = "task_config_fields_last_changed_date"
        const val COL_task_config_fields_deleted_at = "task_config_fields_deleted_at"


        //ravi offline----table---task_config_functions

        const val TABLE_task_config_functions = "task_config_functions"
        const val COL_task_config_functions_PRIMARYKEY = "task_config_functions_primary"
        const val COL_task_config_functions_SERVERKEY = "task_config_functions_serverid"
        const val COL_task_config_functions_task_config_id = "task_config_functions_task_config_id"
        const val COL_task_config_functions_task_name = "task_config_functions_task_name"
        const val COL_task_config_functions_description = "task_config_functions_description"
        const val COL_task_config_functions_privilege = "task_config_functions_privilege"
        const val COL_task_config_functions_created_by = "task_config_functions_created_by"
        const val COL_task_config_functions_created_date = "task_config_functions_created_date"
        const val COL_task_config_functions_last_changed_by =
            "task_config_functions_last_changed_by"
        const val COL_task_config_functions_last_changed_date =
            "task_config_functions_last_changed_date"
        const val COL_task_config_functions_deleted_at = "task_config_functions_deleted_at"


        //ravi offline----table---collect_data


        const val TABLE_collect_data = "collect_data"
        const val COL_collect_data_PRIMARYKEY = "collectDataPrimary"
        const val COL_COLLECT_DATA_ID = "collectData_serverid"
        const val COL_collect_data_STATUS = "collectData_serverid_status"
        const val COL_COLLECT_DATA_PACK_ID = "collectDataPackid"
        const val COL_collect_data_ResulId = "collectDataResultIid"
        const val COL_COLLECT_DATA_RESULE_CLASS = "collectData_resultclass"
        const val COL_collect_data_CollectActivityId = "collectDataCollectActivityId"
        const val COL_COLLECT_DATA_VALUE = "collectData_new_value"
        const val COL_collect_data_UNITID = "collectData_unit_id"
        const val COL_collect_data_SENSORID = "collectData_sensor_id"
        const val COL_COLLECT_DATA_DURATION = "collectData_duration"
        const val COL_collect_data_CREATEDBY = "collectData_created_by"
        const val COL_collect_data_UPDATED_BY = "collectData_updated_by"
        const val COL_collect_data_DELETED_BY = "collectData_deleted_by"
        const val COL_COLLECT_DATA_CREATED_AT = "collectData_created_at"
        const val COL_collect_data_UPDATED_AT = "collectData_updated_at"
        const val COL_collect_data_DELETED_AT = "collectData_deleted_at"
        //ravi offline----table---community_groups

        const val TABLE_community_groups = "community_groups"
        const val COL_community_groups_PRIMARYKEY = "community_groups_Primary"
        const val COL_community_groups_SERVERID = "community_groups_serverid"
        const val COL_community_groups_NAME = "community_groups_name"
        const val COL_community_groups_DESCIPRTION = "community_groups_description"
        const val COL_community_groups_COMM_GROUP = "community_groups_community_group"
        const val COL_community_groups_CREATED_BY = "community_groups_created_by"
        const val COL_community_groups_UPDATED_BY = "community_groups_updated_by"
        const val COL_community_groups_DELTED_BY = "community_groups_deleted_by"
        const val COL_community_groups_CREATED_AT = "community_groups_created_at"
        const val COL_community_groups_UPDATED_AT = "community_groups_updated_at"
        const val COL_community_groups_DELETED_AT = "community_groups_deleted_at"

        //ravi -offline---------table-----collect_activities

        const val TABLE_collect_activities = "collect_activities"
        const val COL_collect_activities_PRIMARYKEY = "collect_activities_Primary"
        const val COL_collect_activities_SERVERID = "collect_activities_serverid"
        const val COL_collect_activities_NAME = "collect_activities_name"
        const val COL_collect_activities_COMMUNITYGROUP = "collect_activities_communitygroup"
        const val COL_collect_activities_CREATED_BY = "collect_activities_created_by"
        const val COL_collect_activities_UPDATED_BY = "collect_activities_updated_by"
        const val COL_collect_activities_DELTED_BY = "collect_activities_deleted_by"
        const val COL_collect_activities_CREATED_AT = "collect_activities_created_at"
        const val COL_collect_activities_UPDATED_AT = "collect_activities_updated_at"
        const val COL_collect_activities_DELTED_AT = "collect_activities_deleted_at"


        //ravi -offline---------table-----collect_activity_results

        const val TABLE_collect_activity_results = "collect_activity_results"
        const val COL_collect_activity_results_PRIMARYKEY = "collect_activity_results_Primary"
        const val COL_collect_activity_results_SERVERID = "collect_activity_results_SERVERID"
        const val COL_collect_activity_results_COLLECT_ACTIVITY_ID =
            "collect_activity_results_collect_activity_id"
        const val COL_collect_activity_results_RESULTNAME = "collect_activity_results_result_name"
        const val COL_collect_activity_results_UNITID = "collect_activity_results_unit_id"
        const val COL_collect_activity_results_TYPEID = "collect_activity_results_type_id"
        const val COL_collect_activity_results_LISTID = "collect_activity_results_list_id"
        const val COL_collect_activity_results_RESULTCLASS = "collect_activity_results_result_class"
        const val COL_collect_activity_results_CREATEDBY = "collect_activity_results_created_by"
        const val COL_collect_activity_results_UPDATEDBY = "collect_activity_results_updated_by"
        const val COL_COLLECT_ACTIVITY_RESULTS_DELETED_BY = "collect_activity_results_deleted_by"
        const val COL_collect_activity_results_CREATEDAT = "collect_activity_results_created_at"
        const val COL_collect_activity_results_UPDATEDAT = "collect_activity_results_updated_at"
        const val COL_collect_activity_results_DELETEAT = "collect_activity_results_deleted_at"

        //ravi -offline---------table-----collect_activity_results_unit

        const val TABLE_collect_activity_results_unit = "collect_activity_results_unit"
        const val COL_collect_activity_results_unit_PRIMARYKEY =
            "collect_activity_results_unit_Primary"
        const val COL_collect_activity_results_unit_SERVERID =
            "collect_activity_results_unit_SERVERID"
        const val COL_collect_activity_results_unit_COLLECT_ACITIVITY_RESULT_ID =
            "collect_activity_results_unit_collect_activity_result_id"
        const val COL_collect_activity_results_unit_UNITID = "collect_activity_results_unit_unit_id"

        //ravi -offline---------table-----people

        const val TABLE_PEOPLE = "people"
        const val COL_PEOPLE_LOCAL_ID = "people_Primary"
        const val COL_PEOPLE_ID = "people_Serverid"
        const val COL_PEOPLE_FNAME = "people_fname"
        const val COL_PEOPLE_LNAME = "people_lname"
        const val COL_PEOPLE_EMAIl = "people_email"
        const val COL_PEOPLE_CONTACT = "people_contact"
        const val COL_PEOPLE_BIRTH_PLACE = "people_birth_place"
        const val COL_PEOPLE_DOB = "people_dob"
        const val COL_PEOPLE_PHOTO = "people_photo"
        const val COL_PEOPLE_ADDRESS = "people_address"
        const val COL_PEOPLE_CITIZENSHIP = "people_citizenship"
        const val COL_PEOPLE_CERTIFICAITON = "people_certification"
        const val COL_PEOPLE_LASTCERFICATION_DATE = "people_last_certification_date"
        const val COL_PEOPLE_IS_IN_COOP = "people_is_in_coop"
        const val COL_PEOPLE_IS_KAKAOMUNDO = "people_is_kakaomundo"
        const val COL_PEOPLE_KAKAOMUNDO_CENTER = "people_is_kakaomundo_center"
        const val COL_PEOPLE_USERID = "people_is_user_id"
        const val COL_PEOPLE_COMMUNITY_GROUP = "people_is_communitygroup"
        const val COL_PEOPLE_PERSON_CLASS = "people_person_class"
        const val COL_PEOPLE_PERSON_TYPE = "people_person_type"
        const val COL_PEOPLE_DESCRIPTION = "people_description"
        const val COL_PEOPLE_CREATED_BY = "people_created_by"
        const val COL_PEOPLE_UPDATED_BY = "people_updated_by"
        const val COL_PEOPLE_UPDATED_AT = "people_updated_at"
        const val COL_PEOPLE_CREATED_AT = "people_created_at"
        const val COL_PEOPLE_DELETED_BY = "people_deleted_by"
        const val COL_PEOPLE_DELETED_AT = "people_deleted_at"

        //ravi -offline---------table-----container


        const val TABLE_CONTAINER = "container"
        const val COL_CONTAINER_LOCAL_ID = "container_Primary"
        const val COL_CONTAINER_ID = "container_servid"
        const val COL_CONTAINER_NAME = "container_name"
        const val COL_CONTAINER_DESCRIPTION = "container_description"
        const val COL_CONTAINER_COM_GROUP = "container_com_group"
        const val COL_CONTAINER_TYPE = "container_type"
        const val COL_CONTAINER_STATUS = "container_status"
        const val COL_CONTAINER_MAX_CAPACITY = "container_max_capacity"
        const val COL_CONTAINER_CAPACITY_UNITS = "container_capacity_units"
        const val COL_CONTAINER_ZONE = "container_zone"
        const val COL_CONTAINER_CLASS = "container_class"
        const val COL_CONTAINER_NOTIFICATION_LEVEL = "container_notification_level"
        const val COL_CONTAINER_PARENT_CONTAINER = "container_parent_container"
        const val COL_CONTAINER_DELETED_AT = "container_deleted_at"
        const val COL_CONTAINER_CREATED_DATE = "container_created_date"
        const val COL_CONTAINER_CREATED_BY = "container_created_by"
        const val COL_CONTAINER_CREATED_DATE_UTC = "container_created_date_utc"
        const val COL_CONTAINER_LAST_CHANGED_DATE = "container_last_changed_date"
        const val COL_CONTAINER_LAST_CHANGED_BY = "container_last_changed_by"
        const val COL_CONTAINER_LAST_CHANGED_UTC = "container_last_changed_utc"


        //ravi -offline---------table-----container_object

        const val TABLE_container_object = "container_object"
        const val COL_container_object_PRIMARYKEY = "container_object_Primary"
        const val COL_container_object_SERVERID = "container_object_serverid"
        const val COL_container_object_OBJECTNAME = "container_object_object_name"
        const val COL_container_object_CONTAINERNO = "container_object_container_no"
        const val COL_container_object_OBJECT_NO = "container_object_object_no"
        const val COL_container_object_TYPE = "container_object_type"
        const val COL_container_object_CLASS = "container_object_class"
        const val COL_container_object_SESSIN_ID = "container_object_session_id"
        const val COL_container_object_ADDED_DATE = "container_object_added_date"
        const val COL_container_object_ADDED_BY = "container_object_added_by"
        const val COL_container_object_ADDED_UTC = "container_object_added_utc"
        const val COL_container_object_DELETED_AT = "container_object_deleted_at"


        //ravi -offline---------table-----events

        const val TABLE_events = "events"
        const val COL_events_PRIMARYKEY = "events_Primary"
        const val COL_events_SERVERID = "events_server_id"
        const val COL_events_NAME = "events_name"
        const val COL_events_DESCIPTION = "events_description"
        const val COL_events_TYPE = "events_type"
        const val COL_events_EXP_STR_DATE = "events_exp_start_date"
        const val COL_events_EXP_END_DATE = "events_exp_end_date"
        const val COL_events_EXP_DURATION = "events_exp_duration"
        const val COL_events_ACTUAL_STR_DATE = "events_actual_start_date"
        const val COL_events_ACTUAL_END_DATE = "events_actual_end_date"
        const val COL_events_ACTUAL_DURATION = "events_actual_duration"
        const val COL_events_CLOSED = "events_closed"
        const val COL_events_CLOSED_DATE = "events_closed_date"
        const val COL_events_CLOSED_BY = "events_closed_by"
        const val COL_events_COM_GROUP = "events_com_group"
        const val COL_events_STATUS = "events_status"
        const val COL_events_RESPONSIBLE = "events_responsible"
        const val COL_events_ASSIGN_TEAM = "events_assigned_team"
        const val COL_events_TASK_ID = "events_task_id"
        const val COL_events_CREATED_BY = "events_created_by"
        const val COL_events_CREATED_DATE = "events_created_date"
        const val COL_events_LAST_CHANGED_BY = "events_last_changed_by"
        const val COL_events_LAST_CHANGED_DATE = "events_last_changed_date"
        const val COL_events_DELETED_AT = "events_deleted_at"
        const val COL_events_EVENTSTATUS = "events_eventStatus"

        //ravi -offline---------table-----fields

        const val TABLE_fields = "fields"
        const val COL_fields_PRIMARYKEY = "fields_Primary"
        const val COL_fields_SERVERID = "fields_serverid"
        const val COL_fields_NAME = "fields_name"
        const val COL_fields_DESCIPTION = "fields_description"
        const val COL_fields_COUNTRY = "fields_country"
        const val COL_fields_REGION = "fields_region"
        const val COL_fields_LOCALITY = "fields_locality"
        const val COL_fields_SURFACE_AREA = "fields_surface_area"
        const val COL_fields_AREA_UNIT = "fields_area_unit"
        const val COL_fields_NUMBER_OF_PLANT = "fields_number_of_plant"
        const val COL_fields_MAIN_CULTURE = "fields_main_culture"
        const val COL_fields_OTHER_CULTURE = "fields_other_culture"
        const val COL_fields_COMMUNITY_GROUP = "fields_communitygroup"
        const val COL_fields_PLANT_TYPE = "fields_plant_type"
        const val COL_fields_SOIL_TYPE = "fields_soil_type"
        const val COL_fields_VEGETATION = "fields_vegetation"
        const val COL_fields_CLIMATE = "fields_climate"
        const val COL_fields_ALTITUDE = "fields_altitude"
        const val COL_fields_ALTITUDE_UNIT = "fields_altitude_unit"
        const val COL_fields_TEMPERATURE = "fields_temperature"
        const val COL_fields_TEMP_UNIT = "fields_temp_unit"
        const val COL_fields_HUMIDITY = "fields_humidity"
        const val COL_fields_HUMIDITY_UNIT = "fields_humidity_unit"
        const val COL_fields_PLUVIOMETRY = "fields_pluviometry"
        const val COL_fields_PLUVIOMETRY_UNIT = "fields_pluviometry_unit"
        const val COL_fields_HARVEST_PERIOD = "fields_harvest_period"
        const val COL_fields_FIELD_CLASS = "fields_field_class"
        const val COL_fields_FIELD_TYPE = "fields_field_type"
        const val COL_fields_FIELD_BOUNDARY = "fields_field_boundary"
        const val COL_fields_LATITUDE = "fields_latitude"
        const val COL_fields_LONGITUDE = "fields_longitude"
        const val COL_fields_FIELD_CONTACT = "fields_field_contact"
        const val COL_fields_LAST_VISITED_BY = "fields_last_visited_by"
        const val COL_fields_UNIT_ID = "fields_unit_id"
        const val COL_fields_LIST_ID = "fields_lists_id"
        const val COL_fields_TEAM_ID = "fields_team_id"
        const val COL_fields_LAST_VISITED_DATE = "fields_last_visited_date"
        const val COL_fields_LAST_VISITED_UTC = "fields_last_visited_date_utc"
        const val COL_fields_CREATED_BY = "fields_created_by"
        const val COL_fields_CREATED_AT = "fields_created_at"
        const val COL_fields_UPDATED_AT = "fields_updated_at"
        const val COL_fields_UPDATED_BY = "fields_updated_by"
        const val COL_fields_DELETED_BY = "fields_deleted_by"
        const val COL_fields_DELETED_AT = "fields_deleted_at"

        //ravi -offline---------table-----graph_charts

        const val TABLE_GRAPH_CHARTS = "graph_charts"
        const val COL_graph_charts_PRIMARYKEY = "graph_charts_Primary"
        const val COL_GRAPH_CHARTS_ID = "graph_charts_serverid"
        const val COL_graph_charts_NAME = "graph_charts_name"
        const val COL_graph_charts_DESCIPTION = "graph_charts_description"
        const val COL_graph_charts_OBJECTCLASS = "graph_charts_object_class"
        const val COL_graph_charts_COM_GROUP = "graph_charts_com_group"
        const val COL_graph_charts_TITLE = "graph_charts_title"
        const val COL_graph_charts_ABCISSA_TITLE = "graph_charts_abcissa_title"
        const val COL_graph_charts_ORDINATE_TITLE = "graph_chartsordinate_title"
        const val COL_graph_charts_CREATED_DATE = "graph_charts_created_date"
        const val COL_graph_charts_CREATED_BY = "graph_charts_created_by"
        const val COL_graph_charts_LASTCHANGED_BY = "graph_charts_last_changed_by"
        const val COL_graph_charts_LASTCHANGED_DATE = "graph_charts_last_changed_date"
        const val COL_graph_charts_DELETED_AT = "graph_charts_deleted_at"

        //ravi -offline---------table-----graph_chart_objects

        const val TABLE_graph_chart_objects = "graph_chart_objects"
        const val COL_graph_chart_objects_PRIMARYKEY = "graph_charts_Primary"
        const val COL_GRAPH_CHART_OBJECT_ID = "graph_chart_objects_serverid"
        const val COL_graph_chart_objects_CHARTID = "graph_chart_objects_graphs_charts_id"
        const val COL_GRAPH_CHART_OBJECT_NAME = "graph_chart_objects_name"
        const val COL_graph_chart_objects_LINETYPE = "graph_chart_objects_line_type"
        const val COL_GRAPH_CHART_OBJECT_RESULT_CLASS = "graph_chart_objects_result_class"
        const val COL_GRAPH_CHART_OBJECT_REFF_CLTL_POINT = "graph_chart_objects_ref_ctrl_points"
        const val COL_graph_chart_objects_GRAPH_CHARTID =
            "graph_chart_objects_graphs_graphcharts_id"
        const val COL_graph_chart_objects_CREATED_BY = "graph_chart_objects_created_by"
        const val COL_graph_chart_objects_CREATED_DATE = "graph_chart_objects_created_date"
        const val COL_graph_chart_objects_LAST_CHANGED_BY = "graph_chart_objects_last_changed_by"
        const val COL_graph_chart_objects_LAST_CHANGED_DATE =
            "graph_chart_objects_last_changed_date"
        const val COL_graph_chart_objects_DELETED_AT = "graph_chart_objects_deleted_at"

        //ravi -offline---------table-----graph_chart_points

        const val TABLE_graph_chart_points = "graph_chart_points"
        const val COL_graph_chart_points_PRIMARYKEY = "graph_chart_points_primary"
        const val COL_graph_chart_points_SERVERID = "graph_chart_points_serverid"
        const val COL_graph_chart_points_PACKID = "graph_chart_points_packid"
        const val COL_graph_chart_points_VALUE = "graph_chart_points_value"
        const val COL_graph_chart_points_CreateAt = "graph_chart_points_createAt"
        const val COL_graph_chart_points_CHARTID = "graph_chart_points_chartid"
        const val COL_graph_chart_points_DURATION = "graph_chart_points_duration"


        //ravi -offline---------table-----lists

        const val TABLE_LISTS = "lists"
        const val COL_LISTS_PRIMARYKEY = "lists_Primary"
        const val COL_LISTS_ID = "lists_serverid"
        const val COL_LISTS_NAME = "lists_name"
        const val COL_LISTS_COM_GROUP_ID = "lists_community_group_id"
        const val COL_LISTS_DESC = "lists_description"
        const val COL_LISTS_COM_GROUP = "lists_communitygroup"
        const val COL_LISTS_CREATED_BY = "lists_created_by"
        const val COL_LISTS_UPDATED_BY = "lists_updated_by"
        const val COL_LISTS_DELETED_BY = "lists_deleted_by"
        const val COL_LISTS_CREATED_AT = "lists_created_at"
        const val COL_LISTS_UPDATED_AT = "lists_updated_at"
        const val COL_LISTS_DELETED_AT = "lists_deleted_at"

        //ravi -offline---------table-----list_choices

        const val TABLE_LIST_CHOICES = "list_choices"
        const val COL_list_choices_PRIMARYKEY = "list_choices_Primary"
        const val COL_LIST_CHOICES_ID = "list_choices_serverid"
        const val COL_LIST_CHOICES_LISTS_ID = "list_choices_lists_id"
        const val COL_LIST_CHOICES_CHOICE = "list_choices_choice"
        const val COL_LIST_CHOICES_NAME = "list_choices_name"
        const val COL_LIST_CHOICES_COM_GROUP = "list_choices_choice_communitygroup"
        const val COL_LIST_CHOICES_COM_GROUP_ID = "list_choices_community_group_id"
        const val COL_LIST_CHOICES_CREATED_AT = "list_choices_created_at"
        const val COL_LIST_CHOICES_UPDATED_AT = "list_choices_updated_at"
        const val COL_LIST_CHOICES_DELETED_AT = "list_choices_deleted_at"

        //ravi -offline---------table-----sensors

        const val TABLE_sensors = "sensors"
        const val COL_sensors_PRIMARYKEY = "sensors_primarykey"
        const val COL_sensors_SERVERID = "sensors_serverid"
        const val COL_sensors_TYPEID = "sensors_sensor_type_id"
        const val COL_sensors_NAME = "sensors_name"
        const val COL_sensors_SENSORID = "sensors_sensorId"
        const val COL_sensors_MODEL = "sensors_model"
        const val COL_sensors_BRAND = "sensors_brand"
        const val COL_sensors_SENSORIP = "sensors_sensorIp"
        const val COL_sensors_OWNER = "sensors_owner"
        const val COL_sensors_USERID = "sensors_user_id"
        const val COL_sensors_UNITID = "sensors_unit_id"
        const val COL_sensors_MINIMUM = "sensors_minimum"
        const val COL_sensors_MAXIMUM = "sensors_maximum"
        const val COL_sensors_COM_GROUP = "sensors_community_group"
        const val COL_sensors_CONNECTEDBOARD = "sensors_connected_board"
        const val COL_sensors_CONTAINERID = "sensors_container_id"
        const val COL_sensors_CREATEDBY = "sensors_created_by"
        const val COL_sensors_UPDATEDBY = "sensors_updated_by"
        const val COL_sensors_DELETEDBY = "sensors_deleted_by"
        const val COL_sensors_CREATEDAT = "sensors_created_at"
        const val COL_sensors_UPDATEDAT = "sensors_updated_at"
        const val COL_sensors_DELETEDAT = "sensors_deleted_at"

        //ravi -offline---------table-----units

        const val TABLE_units = "units"
        const val COL_units_PRIMARYID = "units_primarykey"
        const val COL_units_SERVERID = "units_serverid"
        const val COL_units_NAME = "units_name"
        const val COL_units_DESCRIPTION = "units_description"
        const val COL_units_COM_GROUP = "units_communitygroup"
        const val COL_units_CREATED_BY = "units_created_by"
        const val COL_units_UPDATED_BY = "units_updated_by"
        const val COL_units_DELETED_BY = "units_deleted_by"
        const val COL_units_CREATED_AT = "units_created_at"
        const val COL_units_UPDATED_AT = "units_updated_at"
        const val COL_units_DELETED_AT = "units_deleted_at"

        //ravi -offline---------table-----team

        const val TABLE_team = "team"
        const val COL_team_PRIMARYID = "team_primarykey"
        const val COL_team_SERVERID = "team_serverid"
        const val COL_team_NAME = "team_name"
        const val COL_team_DESC = "team_desciption"
        const val COL_team_EMAIL = "team_email"
        const val COL_team_CONTACT = "team_contact"
        const val COL_team_ADDRESS = "team_address"
        const val COL_team_TEAMCLASS = "team_teamclass"
        const val COL_team_TEAMTYPE = "team_teamtype"
        const val COL_team_RESPONSIBLE = "team_responsible"
        const val COL_team_COMGROUP = "team_comgroup"
        const val COL_team_LOGO = "team_logo"
        const val COL_team_CREATEDBY = "team_createdBy"
        const val COL_team_CREATED_AT = "team_created_at"
        const val COL_team_UPDATED_BY = "team_updatedBy"
        const val COL_team_UPDATED_AT = "team_updatedAT"
        const val COL_team_DELETED_BY = "team_deletedby"
        const val COL_team_DELETED_AT = "team_deletedAt"


        //ravi -offline---------table-----task_media_files

        const val TABLE_task_media_files = "task_media_files"
        const val COL_task_media_files_PRIMARYID = "task_media_files_primary"
        const val COL_task_media_files_SERVERID = "task_media_files_serverid"
        const val COL_task_media_files_TASKID = "task_media_files_taskid"
        const val COL_task_media_files_NAME = "task_media_files_FILENAME"
        const val COL_task_media_files_LINK = "task_media_files_link"
        const val COL_task_media_files_LOCAL_FILE_PATH = "task_media_files_localFILEpath"

        //ravi -offline---------table-----eventType

        const val TABLE_eventType = "eventType"
        const val COL_eventType_PRIMARYID = "eventType_primary"
        const val COL_eventType_SERVERID = "eventType_serverid"
        const val COL_eventType_NAME = "eventType_name"

        //ravi -offline---------table-----eventStatus

        const val TABLE_eventStatus = "eventStatus"
        const val COL_eventStatus_PRIMARYID = "eventStatus_primary"
        const val COL_eventStatus_SERVERID = "eventStatus_serverid"
        const val COL_eventStatus_NAME = "eventStatus_name"

        //ravi--offline--------table-task_objects

//        const val TABLE_TASKS_OBJECTS = "task_objects"
//        const val COL_TASKS_OBJECTS_LOCAL_ID = "task_objects_primaryid"
//        const val COL_TASKS_OBJECTS_ID = "task_objects_serverid"
//        const val COL_TASKS_OBJECTS_TASK_ID = "task_objects_taskId"
//        const val COL_TASKS_OBJECTS_FUNCTION = "task_objects_function"
//        const val COL_TASKS_OBJECTS_CONTAINER = "task_objects_container"
//        const val COL_TASKS_OBJECTS_STATUS = "task_objects_status"
//        const val COL_TASKS_OBJECTS_LAST_CHANGED_DATE = "task_objects_lastchnagedDATE"

        const val TABLE_TASKS_OBJECTS = "task_objects"
        const val COL_TASKS_OBJECTS_LOCAL_ID = "local_id"
        const val COL_TASKS_OBJECTS_ID = "id"
        const val COL_TASKS_OBJECTS_TASK_ID = "task_id"
        const val COL_TASKS_OBJECTS_FUNCTION = "function"
        const val COL_TASKS_OBJECTS_NO = "task_no"
        const val COL_TASKS_OBJECTS_NAME = "name"
        const val COL_TASKS_OBJECTS_TYPE = "type"
        const val COL_TASKS_OBJECTS_CLASS = "class"
        const val COL_TASKS_OBJECTS_ORIGIN= "origin"
        const val COL_TASKS_OBJECTS_CONTAINER = "container"
        const val COL_TASKS_OBJECTS_STATUS = "local_status"


        const val TABLE_privileges = "table_privileges"
        const val COL_privileges_SERVERID = "privileges_serverid"
        const val COL_privileges_PRIMARYID = "privileges_primary"
        const val COL_privileges_NAME = "privileges_name"


        const val CON_PACK_ID = "id"
        const val COL_UPDATED_AT = "updated_at"
        const val COL_UPDATED_BY = "updated_by"
        const val COL_CREATED_AT = "created_at"
        const val COL_CREATED_BY = "created_by"
        const val COL_DELETED_AT = "deleted_at"
        const val COL_DELETED_BY = "deleted_by"
        const val COL_LOCAL_STATUS = "local_status"
        const val COL_LAST_CHANGE_BY = "last_changed_by"
        const val COL_LAST_CHANGE_DATE = "last_changed_date"

        const val TABLE_ROLE_PRIVILEGES  = "role_privileges"
        const val COL_ROLE_PRIVILEGES_LOCAL_ID = "lid"
        const val COL_ROLE_PRIVILEGES_ID = "id"
        const val COL_ROLE_PRIVILEGES_ROLE_ID = "role_id"
        const val COL_ROLE_PRIVILEGES_PRIVILEGE = "privilege"

        const val DATE_TIME_FORMATE = "yyyy-MM-dd hh:mm"
        const val DATE_TIME_FORMATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss"
        const val DATE_TIME_FORMATE_DATE = "yyyy-MM-dd"
        const val DATE_TIME_FORMATE_T = "yyyy-MM-dd'T'hh:mm"

    }
}