package com.pbt.myfarm.Util

class AppConstant {
    companion object {
        //Database
        val CONST_DATABASE_NAME = "myfarm"
        val CONST_DATABASE_VERSION = 1
        val CONST_USERS_TABLE = "MyFarmUsers"
        val CONST_ID = "id"
        val CONST_USERNAME = "username"
        val CONST_USERROLE = "userrole"
        val CONST_USERPASS = "userpass"

            //packid
        val PACK_LIST_PACKID="packlistpackid"

        //SharePreference
        val CONST_SHARED_PREF_NAME = "mysharedpreference"
        val CONST_SHARED_PREF_TOKEN = "mysharedpreferenceToken"
        val CONST_SHARED_PREF_USERNAME = "username"
        val CONST_PREF_IS_LOGIN = "isLogin"

        //TABLE NewTask
        val CONST_NEW_TASK = "newtask"
        val CONST_USER_ID = "userid"
        val CONST_TASK_NAME = "taskname"
        val CONST_TASK_TYPE = "tasktype"
        val CONST_TASK_DETAIL = "taskdetail"
        val CONST_EXPECTED_EXP_STR_DATE = "expectedstartdate"
        val CONST_EXPECTED_EXP_END_DATE = "expectedenddate"
        val CONST_EXPECTED_STR_DATE = "startdate"
        val CONST_EXPECTED_END_DATE = "enddate"


        //Intent PutExra
        val CONST_CONFIGTYPE_NAME = "configtypename"
        val CONST_CONFIGTYPE_TYPE_ID = "configtypeid"
        val CONST_VIEWMODELCLASS_LIST = "ViewModelClassList"
        val CONST_VIEWMODELCLASS_CONFIG_LIST = "ViewModelClassList"
        val CONST_TASK_UPDATE = "taskupdate"
        val CONST_TASK_UPDATE_BOOLEAN = "taskupdate"
        val CONST_TASK_UPDATE_LIST = "taskupdate"
        val CONST_PACK_UPDATE_LIST = "taskupdate"
        val CONST_TASKFUNCTION_TASKID = "taskupdate"

        //TABLE newpack
        val CONST_TABLE_PACK = "tablepack"
        val CONST_PACK_NAME = "PACKname"
        val CONST_PACKS_ID = "PACKSID"
        val CONST_PACK_TYPE_ID = "PACKtypeID"
        val CONST_PACK_TYPE = "PACKtype"
        val CONST_PACK_DETAIL = "PACKdetail"
        val CONST_PACK_GROUP = "PACKgroup"
        val CONST_PACK_NAME_PREFIX = "PACKnameprefix"
        val CONST_PACK_LABELTYPE = "packlalbeltype"
        val CONST_PACK_LABEL_DESCIPTION = "packlabeldesciption"
        val CONST_PACK_PADZERO = "packlabelpadzero"
        val CONST_PACK_NAME_PREFIX_padzero = "PACKnameprefixpadzero"
        val CONST_PACK_CREATEDBY = "packCreatedBy"
        val CONST_PACK_ID = "packid"
        val CONST_PACK_CUSTOMER = "packcustomer"
        val CONST_PACK_UNITS = "packunit"
        val CONST_PACK_QUANTITY = "packquanity"
        val CONST_LIST_SIZE = "listsize"

        //collect data
        val CONST_TABLE_COLLECT = "collectid"
        val CONST_COLLECT_ID = "collectid"
        val CONST_ACTIVITY = "collectactivity"
        val CONST_RESULT = "collectresult"
        val CONST_VALUE = "collectvalue"
        val CONST_UNITS = "collectunits"
        val CONST_SENSOR = "collectsensor"
        val CONST_DURATION = "collectduration"

        //PACk CONFIGLIST TABLE
        val CONST_PACKCONFIG_LIST_TABLE = "CONST_PACKCONFIG_LIST_TABLE"
        val CONST_PACK_CONFIGLIST_ITEM = "CONST_PACK_CONFIGLIST_ITEM"
        val CONST_PACK_CONFIGLIST_NAME = "CONST_PACK_CONFIGLIST_NAME"
        val CONST_PACK_CONFIGLIST_ID = "CONST_PACK_CONFIGLIST_ID"

        //Pack ConfigFieldlist
        val CONST_PACKCONFIG_FIELDLIST_TABLE = "CONST_PACKCONFIG_FIELDLIST_TABLE"
        val CONST_PACK_CONFIG_FIELDLIST_ITEM = "CONST_PACK_CONFIG_FIELDLIST_ITEM"
        val CONST_PACK_CONFIG_FIELDLIST_field_id = "CONST_PACK_CONFIG_FIELDLIST_field_id"
        val CONST_PACK_CONFIG_FIELDLIST_field_name = "CONST_PACK_CONFIG_FIELDLIST_field_name"
        val CONST_PACK_CONFIG_FIELDLIST_field_description =
            "CONST_PACK_CONFIG_FIELDLIST_field_description"
        val CONST_PACK_CONFIG_FIELDLIST_field_type = "CONST_PACK_CONFIG_FIELDLIST_field_type"
        val CONST_PACK_CONFIG_FIELDLIST_field_value = "CONST_PACK_CONFIG_FIELDLIST_field_value"

        //Pack ConfigFieldlist_field_list
        val CONST_PACKCONFIG_FIELDLIST_field_list_TABLE =
            "CONST_PACKCONFIG_FIELDLIST_field_list_TABLE"
        val CONST_PACK_CONFIG_FIELDLIST_fieldlist_ITEM =
            "CONST_PACK_CONFIG_FIELDLIST_fieldlist_ITEM"
        val CONST_PACK_CONFIG_FIELDLIST_field_id_ID = "CONST_PACK_CONFIG_FIELDLIST_field_id_ID"
        val CONST_PACK_CONFIG_FIELDLIST_field_name_name =
            "CONST_PACK_CONFIG_FIELDLIST_field_name_name"
        val CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid =
            "CONST_PACK_CONFIG_FIELDLIST_field_description_fieldid"
        val CONST_PACK_CONFIG_FIELDLIST_field_type_packid =
            "CONST_PACK_CONFIG_FIELDLIST_field_type_packid"
        val CONST_PACK_CONFIG_FIELDLIST_field_value_configid =
            "CONST_PACK_CONFIG_FIELDLIST_field_value_configid"


        //communitygroup
        val CONST_PACK_CommunityGroupTABLE = "CONST_PACK_CommunityGroupTABLE"
        val CONST_PACK_CommunityGroupID = "CONST_PACK_CommunityGroupID"
        val CONST_PACK_CommunityGroupITEM_ID = "CONST_PACK_CommunityGroupITEM_ID"
        val CONST_PACK_CommunityGroupNAME = "CONST_PACK_CommunityGroupNAME"
        val CONST_PACK_CommunityGroupcommunity_group = "CONST_PACK_CommunityGroupcommunity_group"

        //event
        val CONST_EDITEVENT_ID="editeventid"
        val CONST_CREATEEVENT="createevent"

        //TABLE
        val TABLE_CREAT_PACK="packs_new"
        val PACKNEW_PRIMARYKEY="packnewprimarykey"
        val PACKNEW_NAMEPREFIX="packnewnameprefix"
        val PACKNEW_DESC="packnewdesc"
        val PACKNEW_CONFIGID="packnewid"
        val PACKNEW_GROUP="packnewGroup"
        val PACKNEW_Status="Status"
        val PACKNEW_NAME="PACKNEW_NAME"
        val PACKNEW_Created_at="Created_at"
        val PACKNEW_Updated_at="Updated_at"
        val PACKNEW_Deleted_at="Deleted_at"

        //TABLE_packFields
        val PACKFIELDS_TABLENAME="packfieldtablename"
        val PACKFIELDS_PRIMARYKEY="packfieldsprimarykey"
        val PACKFIELDS_packid="packfieldpackid"
        val PACKFIELDS_fieldid="packfieldfieldid"
        val PACKFIELDS_value="packfieldvalue"




        //collect_data TABLE
        val COLLECTDATA_TABLENAME="COLLECTDATA_TABLENAME"
        val COLLECTDATA_PRIMARYKEY="id"
        val COLLECTDATA_packid="packid"
        val COLLECTDATA_resultid="result_id"
        val COLLECTDATA_result_class="result_class"
        val COLLECTDATA_collect_activity_id="collect_activity_id"
        val COLLECTDATA_new_value="new_value"
        val COLLECTDATA_value="value"
        val COLLECTDATA_unit_id="unit_id"
        val COLLECTDATA_sensor_id="sensor_id"
        val COLLECTDATA_duration="duration"
        val COLLECTDATA_updated_by="updated_by"
        val COLLECTDATA_created_by="created_by"
        val COLLECTDATA_deleted_by="deleted_by"
        val COLLECTDATA_created_at="created_at"
        val COLLECTDATA_updated_at="updated_at"
        val COLLECTDATA_deleted_at="deleted_at"

   //collect_activity_results
        val colctactyrslt_TABLE="collect_activity_results"
        val colctactyrslt_Key="id"
        val colctactyrslt_collectactiivtid="collect_activity_id"
        val colctactyrslt_result_name="resultname"
        val colctactyrslt_unit_id="unit_id"
        val colctactyrslt_type_id="collect_activity_results"
        val colctactyrslt_list_id="collect_activity_results"
        val colctactyrslt_result_class="collect_activity_results"
        val colctactyrslt_created_by="created_by"
        val colctactyrslt_deleted_by="deleted_by"
        val colctactyrslt_created_at="created_at"
        val colctactyrslt_updated_at="updated_at"
        val colctactyrslt_deleted_at="deleted_at"

        //collect_activity_result_unit
        val colctactyrslt_unit_TABLE="collect_activity_results_unit"
        val colctactyrslt_unit_Key="id"
        val colctactyrslt_unit_collectactiivtid="collect_activity_result_id"
        val colctactyrslt_unit_unitid="unit_id"

        //pack_config_fields

        val COL_SERVER_ID="packconfig_server_id"
        val COL_PACKCONFIGPRIMARY_ID="packconfig_primary_id"
        val TABLE_PACK_CONFIG_FIELDS="pack_config_fields"
        val COL_PACKCONFIG_ID="packconfigid"
        val COL_FIELD_NAME="field_name"
        val COL_field_description="field_description"
        val COL_field_type="field_type"
        val COL_list="list"
        val COL_default_value="default_value"
        val COL_created_by="created_by"
        val COL_created_date="created_date"
        val COL_last_changed_by="last_changed_by"
        val COL_last_changed_date="last_changed_date"
        val COL_eleted_at="deleted_at"

        //ravi offline- table --pack_configs
        val TABLE_pack_configs="pack_configs"
        val COL_pack_configs_SERVER_ID="pack_configs_server_id"
        val COL_pack_configs_PRIMARY_ID="pack_configs_primary_id"
        val COL_pack_configs_NAME="pack_configs_name"
        val COL_pack_configs_DESCIPTION="pack_configs_desciption"
        val COL_pack_configs_TYPE="pack_configs_type"
        val COL_pack_configs_CLASS="field_type"
        val COL_pack_configs_COMGROUP="pack_configs_com_group"
        val COL_pack_configs_NAMEPREFIX="pack_configs_nameprefix"
        val COL_pack_configs_COLLECTACTIVITY_ID="pack_configs_collectivityid"
        val COL_pack_configs_GRAPHCHCHART_ID="pack_configs_graph_chart_id"
        val COL_pack_configs_CREATEDBY="pack_configs_created_by"
        val COL_pack_configs_CREATED_DATE="pack_configs_created_date"
        val COL_pack_configs_LAST_CHANGED_BY="pack_configs_last_changed_by"
        val COL_pack_configs_LAST_CHNAGED_DATE="pack_configs_last_changed_date"
        val COL_pack_configs_DELETED_AT="pack_configs_deleted_at"

        //ravi offline --table--pack_collect_activity----

        val TABLE_pack_collect_activity="pack_collect_activity"
        val COL_pack_collect_activity_SERVER_ID="pack_collect_activity_server_id"
        val COL_pack_collect_activity_PRIMARY_ID="pack_collect_activity_primary_id"
        val COL_pack_collect_activity_collect_activity_id="pack_collect_activity_collect_activity_id"
        val COL_pack_collect_activity_pack_id="pack_collect_activity_pack_id"

        //ravi offline----table---pack_config_fields

        val TABLE_pack_config_fields="pack_config_fields"
        val COL_pack_config_fields_PRIMARY_ID="pack_config_fields_primary_id"
        val COL_pack_config_fields_SERVER_ID="pack_config_fields_server_id"
        val COL_pack_config_fields_pack_config_id="pack_config_fields_pack_config_id"
        val COL_pack_config_fields_field_name="pack_config_fields_field_name"
        val COL_pack_config_fields_field_description="pack_config_fields_field_description"
        val COL_pack_config_fields_editable="pack_config_fields_editable"
        val COL_pack_config_fields_field_type="pack_config_fields_field_type"
        val COL_pack_config_fields_list="pack_config_fields_config_list"
        val COL_pack_config_fields_default_value="pack_config_fields_default_value"
        val COL_pack_config_fields_created_by="pack_config_fields_created_by"
        val COL_pack_config_fields_last_changed_by="pack_config_fields_last_changed_by"
        val COL_pack_config_fields_last_changed_date="pack_config_fields_last_changed_date"
        val COL_pack_config_fields_deleted_at="pack_config_fields_deleted_at"
        val COL_pack_config_fields_created_date="pack_config_fields_created_date"

        //ravi offline----table---pack_fields

        val TABLE_pack_fields="pack_fields"
        val COL_pack_fields_PRIMARY_ID="pack_fields_primaryid"
        val COL_pack_fields_SERVERID="pack_fields_serverid"
        val COL_pack_fields_pack_id="pack_fields_pack_id"
        val COL_pack_fields_value="pack_fields_value"
        val COL_pack_fields_field_id="pack_fields_field_id"

        //ravi offline----table---tasks

        val TABLE_tasks="tasks"
        val COL_tasks_PRIMARYKEY="tasks_primarykey"
        val COL_tasks_SERVERid="tasks_serverid"
        val COL_tasks_NAME="tasks_name"
        val COL_tasks_DESC="tasks_descp"
        val COL_tasks_GROUP="tasks_comgroup"
        val COL_tasks_TASK_CONFIGID="tasks_task_configid"
        val COL_tasks_TASKFUNC="tasks_taskfun"
        val COL_tasks_STATUS="tasks_status"
        val COL_tasks_STARTED_LATE="tasks_started_late"
        val COL_tasks_ENDED_LATE="tasks_ended_late"
        val COL_tasks_CREATED_BY="tasks_created_By"
        val COL_tasks_CREATED_DATE="tasks_created_date"
        val COL_tasks_LAST_CHANGED_BY="tasks_lastchangedby"
        val COL_tasks_LASTCHANGED_DATE="tasks_last_change_date"
        val COL_tasks_DELTED_AT="tasks_deleted_At"

        //ravi offline----table---task_fields
        val TABLE_task_fields="task_fields"
        val COL_task_fields_PRIMARY="task_fields_primary"
        val COL_task_fields_SERVERID="task_fields_id"
        val COL_task_fields_TASKID="task_fields_task_id"
        val COL_task_fields_FIELDID="task_fields_fieldid"
        val COL_task_fields_VALUE="task_fields_value"


        //ravi offline----table---task_configs

        val TABLE_task_configs="task_configs"
        val COL_task_configs_PRIMARYKEY="task_configs_primary"
        val COL_task_configs_SERVERID="task_configs_serverid"
        val COL_task_configs_NAME="task_configs_name"
        val COL_task_configs_DESCIPTION="task_configs_description"
        val COL_task_configs_TYPE="task_configs_type"
        val COL_task_configs_CLASS="task_configs_class"
        val COL_task_configs_COM_GROUP="task_configs_com_group"
        val COL_task_configs_NAME_PREFIX="task_configs_name_prefix"
        val COL_task_configs_RECORD_EVENT="task_configs_record_event"
        val COL_task_configs_REPORTABLE="task_configs_reportable"
        val COL_task_configs_CREATED_BY="task_configs_created_by"
        val COL_task_configs_CREATED_DATE="task_configs_created_date"
        val COL_task_configs_LAST_CHANGED_BY="task_configs_last_changed_by"
        val COL_task_configs_LAST_CHANGED_DATE="task_configs_last_changed_date"
        val COL_task_configs__LAST_DELETED_AT="task_configs_last_deleted_at"

        //ravi offline----table---task_config_fields

        val TABLE_task_config_fields="task_config_fields"
        val COL_task_config_fields_PRIMARYKEY="task_config_fields_primary"
        val COL_task_config_fields_SERVERID="task_config_fields_serverid"
        val COL_task_config_fields_task_config_id="task_config_fields_task_config_id"
        val COL_task_config_fields_field_name="task_config_fields_field_name"
        val COL_task_config_fields_field_description="task_config_fields_field_description"
        val COL_task_config_fields_editable="task_config_fields_editable"
        val COL_task_config_fields_field_type="task_config_fields_field_type"
        val COL_task_config_fields_list="task_config_fields_list"
        val COL_task_config_fields_created_by="task_config_fields_created_by"
        val COL_task_config_fields_created_date="task_config_fields_created_date"
        val COL_task_config_fields_last_changed_by="task_config_fields_last_changed_by"
        val COL_task_config_fields_last_changed_date="task_config_fields_last_changed_date"
        val COL_task_config_fields_deleted_at="task_config_fields_deleted_at"


        //ravi offline----table---task_config_functions

        val TABLE_task_config_functions="task_config_functions"
        val COL_task_config_functions_PRIMARYKEY="task_config_functions_primary"
        val COL_task_config_functions_SERVERKEY="task_config_functions_serverid"
        val COL_task_config_functions_task_config_id="task_config_functions_task_config_id"
        val COL_task_config_functions_task_name="task_config_functions_task_name"
        val COL_task_config_functions_description="task_config_functions_description"
        val COL_task_config_functions_privilege="task_config_functions_privilege"
        val COL_task_config_functions_created_by="task_config_functions_created_by"
        val COL_task_config_functions_created_date="task_config_functions_created_date"
        val COL_task_config_functions_last_changed_by="task_config_functions_last_changed_by"
        val COL_task_config_functions_last_changed_date="task_config_functions_last_changed_date"
        val COL_task_config_functions_deleted_at="task_config_functions_deleted_at"



    }
}