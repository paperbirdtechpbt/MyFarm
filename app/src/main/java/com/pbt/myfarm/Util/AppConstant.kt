package com.pbt.myfarm.Util

import com.google.gson.annotations.SerializedName

class AppConstant {
    companion object {
        //Database
        val CONST_ROLEID="adminroleid"

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
        val CONST_PREF_ROLE_ID = "RoleId"
        val CONST_PREF_ROLE_NAME = "RoleName"

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
        val CONST_TASK_UPDATE_BOOLEAN = "taskupdateboolean"
        val CONST_TASK_UPDATE_LIST = "taskupdatelist"
        val CONST_PACK_UPDATE_LIST = "packupdatelist"
        val CONST_TASKFUNCTION_TASKID = "taskfunction"
        val CONT_PACK = "packlist"

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
        val COL_LOCAL_ID="packnewprimarykey"
        val COL_ID="packnew_serverid"
        val COL_PACK_DESC="packnewdesc"
        val COL_PACK_CONFIG_ID="packnewid"
        val COL_PACK_GROUP="packnewGroup"
        val COL_PACKNEW_Status="Status"
        val COL_PACK_NAME="PACKNEW_NAME"
        val COL_PACK_CREATED_BY="Created_at"
        val COL_PACK_LAST_CHANGED_BY="Updated_at"
        val COL_PACK_NEW_DLETED_AT="Deleted_at"


        val COL_PACK_IS_ACTIVE="pack_new_is_Active"
        val COL_PACK_CREATED_DATE="pack_new_isActive"
        val COL_PACK_LAST_CHANGED_DATE="pack_new_last_changed_Date"

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
        val TABLE_PACKCONFIG= "packconfigs"
        val COL_pack_configs_SERVER_ID="pack_configs_server_id"
        val COL_pack_configs_PRIMARY_ID="pack_configs_primary_id"
        val COL_pack_configs_NAME="pack_configs_name"
        val COL_pack_configs_DESCIPTION="pack_configs_desciption"
        val COL_pack_configs_TYPE="pack_configs_type"
        val COL_pack_configs_CLASS="pack_configs_class"
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
        val COL_pack_fields_STATUS="pack_fields_status"
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
        val COL_task_fields_STATUS="task_fields_status"


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


        //ravi offline----table---collect_data


        val TABLE_collect_data="collect_data"
        val COL_collect_data_PRIMARYKEY="collectDataPrimary"
        val COL_collect_data_SERVERID="collectData_serverid"
        val COL_collect_data_STATUS="collectData_serverid_status"
        val COL_collect_data_pack_id="collectDataPackid"
        val COL_collect_data_ResulId="collectDataResultIid"
        val COL_collect_data_ResultClass="collectData_resultclass"
        val COL_collect_data_CollectActivityId="collectDataCollectActivityId"
        val COL_collect_data_NEWVALUE="collectData_new_value"
        val COL_collect_data_UNITID="collectData_unit_id"
        val COL_collect_data_SENSORID="collectData_sensor_id"
        val COL_collect_data_DURATION="collectData_duration"
        val COL_collect_data_CREATEDBY="collectData_created_by"
        val COL_collect_data_UPDATED_BY="collectData_updated_by"
        val COL_collect_data_DELETED_BY="collectData_deleted_by"
        val COL_collect_data_CREATED_AT="collectData_created_at"
        val COL_collect_data_UPDATED_AT="collectData_updated_at"
        val COL_collect_data_DELETED_AT="collectData_deleted_at"
 //ravi offline----table---community_groups

        val TABLE_community_groups="community_groups"
        val COL_community_groups_PRIMARYKEY="community_groups_Primary"
        val COL_community_groups_SERVERID="community_groups_serverid"
        val COL_community_groups_NAME="community_groups_name"
        val COL_community_groups_DESCIPRTION="community_groups_description"
        val COL_community_groups_COMM_GROUP="community_groups_community_group"
        val COL_community_groups_CREATED_BY="community_groups_created_by"
        val COL_community_groups_UPDATED_BY="community_groups_updated_by"
        val COL_community_groups_DELTED_BY="community_groups_deleted_by"
        val COL_community_groups_CREATED_AT="community_groups_created_at"
        val COL_community_groups_UPDATED_AT="community_groups_updated_at"
        val COL_community_groups_DELETED_AT="community_groups_deleted_at"

        //ravi -offline---------table-----collect_activities

        val TABLE_collect_activities="collect_activities"
        val COL_collect_activities_PRIMARYKEY="collect_activities_Primary"
        val COL_collect_activities_SERVERID="collect_activities_serverid"
        val COL_collect_activities_NAME="collect_activities_name"
        val COL_collect_activities_COMMUNITYGROUP="collect_activities_communitygroup"
        val COL_collect_activities_CREATED_BY="collect_activities_created_by"
        val COL_collect_activities_UPDATED_BY="collect_activities_updated_by"
        val COL_collect_activities_DELTED_BY="collect_activities_deleted_by"
        val COL_collect_activities_CREATED_AT="collect_activities_created_at"
        val COL_collect_activities_UPDATED_AT="collect_activities_updated_at"
        val COL_collect_activities_DELTED_AT="collect_activities_deleted_at"


  //ravi -offline---------table-----collect_activity_results

        val TABLE_collect_activity_results="collect_activity_results"
        val COL_collect_activity_results_PRIMARYKEY="collect_activity_results_Primary"
        val COL_collect_activity_results_SERVERID="collect_activity_results_SERVERID"
        val COL_collect_activity_results_COLLECT_ACTIVITY_ID="collect_activity_results_collect_activity_id"
        val COL_collect_activity_results_RESULTNAME="collect_activity_results_result_name"
        val COL_collect_activity_results_UNITID="collect_activity_results_unit_id"
        val COL_collect_activity_results_TYPEID="collect_activity_results_type_id"
        val COL_collect_activity_results_LISTID="collect_activity_results_list_id"
        val COL_collect_activity_results_RESULTCLASS="collect_activity_results_result_class"
        val COL_collect_activity_results_CREATEDBY="collect_activity_results_created_by"
        val COL_collect_activity_results_UPDATEDBY="collect_activity_results_updated_by"
        val COL_collect_activity_results_DELETEDBY="collect_activity_results_deleted_by"
        val COL_collect_activity_results_CREATEDAT="collect_activity_results_created_at"
        val COL_collect_activity_results_UPDATEDAT="collect_activity_results_updated_at"
        val COL_collect_activity_results_DELETEAT="collect_activity_results_deleted_at"

        //ravi -offline---------table-----collect_activity_results_unit

        val TABLE_collect_activity_results_unit="collect_activity_results_unit"
        val COL_collect_activity_results_unit_PRIMARYKEY="collect_activity_results_unit_Primary"
        val COL_collect_activity_results_unit_SERVERID="collect_activity_results_unit_SERVERID"
        val COL_collect_activity_results_unit_COLLECT_ACITIVITY_RESULT_ID="collect_activity_results_unit_collect_activity_result_id"
        val COL_collect_activity_results_unit_UNITID="collect_activity_results_unit_unit_id"

        //ravi -offline---------table-----people

        val TABLE_people="people"
        val COL_people_PRIMARYKEY="people_Primary"
        val COL_people_SERVERID="people_Serverid"
        val COL_people_FNAME="people_fname"
        val COL_people_LNAME="people_lname"
        val COL_people_EMAIl="people_email"
        val COL_people_CONTACT="people_contact"
        val COL_people_BIRTHPLACE="people_birth_place"
        val COL_people_DOB="people_dob"
        val COL_people_PHOTO="people_photo"
        val COL_people_ADDRESS="people_address"
        val COL_people_CITIZENSHIP="people_citizenship"
        val COL_people_CERTIFICAITON="people_certification"
        val COL_people_LASTCERFICATION_DATE="people_last_certification_date"
        val COL_people_IS_IN_COOP="people_is_in_coop"
        val COL_people_IS_KAKAOMUNDO="people_is_kakaomundo"
        val COL_people_KAKAOMUNDO_CENTER="people_is_kakaomundo_center"
        val COL_people_USERID="people_is_user_id"
        val COL_people_COMMUNITY_GROUP="people_is_communitygroup"
        val COL_people_PERSON_CLASS="people_person_class"
        val COL_people_PERSON_TYPE="people_person_type"
        val COL_people_DEWSCIPTION="people_description"
        val COL_people_CREATED_BY="people_created_by"
        val COL_people_UPDATED_BY="people_updated_by"
        val COL_people_UPDATED_AT="people_updated_at"
        val COL_people_CREATED_AT="people_created_at"
        val COL_people_DELETED_BY="people_deleted_by"
        val COL_people_DELETED_AT="people_deleted_at"

        //ravi -offline---------table-----container


        val TABLE_container="container"
        val COL_container_PRIMARYKEY="container_Primary"
        val COL_container_SERVERID="container_servid"
        val COL_container_NAME="container_name"
        val COL_container_DESCIPTION="container_description"
        val COL_container_COM_GROUP="container_com_group"
        val COL_container_TYPE="container_type"
        val COL_container_STATUS="container_status"
        val COL_container_MAX_CAPACITY="container_max_capacity"
        val COL_container_CAPACITY_UNITS="container_capacity_units"
        val COL_container_ZONE="container_zone"
        val COL_container_CLASS="container_class"
        val COL_container_NOTIFICATION_LEVEL="container_notification_level"
        val COL_container_PARENT_CONTAINER="container_parent_container"
        val COL_container_DELETED_AT="container_deleted_at"
        val COL_container_CREATED_DATE="container_created_date"
        val COL_container_CREATED_BY="container_created_by"
        val COL_container_CREATED_DATE_UTC="container_created_date_utc"
        val COL_container_LAST_CHANGED_DATE="container_last_changed_date"
        val COL_container_LAST_CHANGED_BY="container_last_changed_by"
        val COL_container_LAST_CHANGED_UTC="container_last_changed_utc"


        //ravi -offline---------table-----container_object

        val TABLE_container_object="container_object"
        val COL_container_object_PRIMARYKEY="container_object_Primary"
        val COL_container_object_SERVERID="container_object_serverid"
        val COL_container_object_OBJECTNAME="container_object_object_name"
        val COL_container_object_CONTAINERNO="container_object_container_no"
        val COL_container_object_OBJECT_NO="container_object_object_no"
        val COL_container_object_TYPE="container_object_type"
        val COL_container_object_CLASS="container_object_class"
        val COL_container_object_SESSIN_ID="container_object_session_id"
        val COL_container_object_ADDED_DATE="container_object_added_date"
        val COL_container_object_ADDED_BY="container_object_added_by"
        val COL_container_object_ADDED_UTC="container_object_added_utc"
        val COL_container_object_DELETED_AT="container_object_deleted_at"


        //ravi -offline---------table-----events

        val TABLE_events="events"
        val COL_events_PRIMARYKEY="events_Primary"
        val COL_events_SERVERID="events_server_id"
        val COL_events_NAME="events_name"
        val COL_events_DESCIPTION="events_description"
        val COL_events_TYPE="events_type"
        val COL_events_EXP_STR_DATE="events_exp_start_date"
        val COL_events_EXP_END_DATE="events_exp_end_date"
        val COL_events_EXP_DURATION="events_exp_duration"
        val COL_events_ACTUAL_STR_DATE="events_actual_start_date"
        val COL_events_ACTUAL_END_DATE="events_actual_end_date"
        val COL_events_ACTUAL_DURATION="events_actual_duration"
        val COL_events_CLOSED="events_closed"
        val COL_events_CLOSED_DATE="events_closed_date"
        val COL_events_CLOSED_BY="events_closed_by"
        val COL_events_COM_GROUP="events_com_group"
        val COL_events_STATUS="events_status"
        val COL_events_RESPONSIBLE="events_responsible"
        val COL_events_ASSIGN_TEAM="events_assigned_team"
        val COL_events_TASK_ID="events_task_id"
        val COL_events_CREATED_BY="events_created_by"
        val COL_events_CREATED_DATE="events_created_date"
        val COL_events_LAST_CHANGED_BY="events_last_changed_by"
        val COL_events_LAST_CHANGED_DATE="events_last_changed_date"
        val COL_events_DELETED_AT="events_deleted_at"
        val COL_events_EVENTSTATUS="events_eventStatus"

        //ravi -offline---------table-----fields

        val TABLE_fields="fields"
        val COL_fields_PRIMARYKEY="fields_Primary"
        val COL_fields_SERVERID="fields_serverid"
        val COL_fields_NAME="fields_name"
        val COL_fields_DESCIPTION="fields_description"
        val COL_fields_COUNTRY="fields_country"
        val COL_fields_REGION="fields_region"
        val COL_fields_LOCALITY="fields_locality"
        val COL_fields_SURFACE_AREA="fields_surface_area"
        val COL_fields_AREA_UNIT="fields_area_unit"
        val COL_fields_NUMBER_OF_PLANT="fields_number_of_plant"
        val COL_fields_MAIN_CULTURE="fields_main_culture"
        val COL_fields_OTHER_CULTURE="fields_other_culture"
        val COL_fields_COMMUNITY_GROUP="fields_communitygroup"
        val COL_fields_PLANT_TYPE="fields_plant_type"
        val COL_fields_SOIL_TYPE="fields_soil_type"
        val COL_fields_VEGETATION="fields_vegetation"
        val COL_fields_CLIMATE="fields_climate"
        val COL_fields_ALTITUDE="fields_altitude"
        val COL_fields_ALTITUDE_UNIT="fields_altitude_unit"
        val COL_fields_TEMPERATURE="fields_temperature"
        val COL_fields_TEMP_UNIT="fields_temp_unit"
        val COL_fields_HUMIDITY="fields_humidity"
        val COL_fields_HUMIDITY_UNIT="fields_humidity_unit"
        val COL_fields_PLUVIOMETRY="fields_pluviometry"
        val COL_fields_PLUVIOMETRY_UNIT="fields_pluviometry_unit"
        val COL_fields_HARVEST_PERIOD="fields_harvest_period"
        val COL_fields_FIELD_CLASS="fields_field_class"
        val COL_fields_FIELD_TYPE="fields_field_type"
        val COL_fields_FIELD_BOUNDARY="fields_field_boundary"
        val COL_fields_LATITUDE="fields_latitude"
        val COL_fields_LONGITUDE="fields_longitude"
        val COL_fields_FIELD_CONTACT="fields_field_contact"
        val COL_fields_LAST_VISITED_BY="fields_last_visited_by"
        val COL_fields_UNIT_ID="fields_unit_id"
        val COL_fields_LIST_ID="fields_lists_id"
        val COL_fields_TEAM_ID="fields_team_id"
        val COL_fields_LAST_VISITED_DATE="fields_last_visited_date"
        val COL_fields_LAST_VISITED_UTC="fields_last_visited_date_utc"
        val COL_fields_CREATED_BY="fields_created_by"
        val COL_fields_CREATED_AT="fields_created_at"
        val COL_fields_UPDATED_AT="fields_updated_at"
        val COL_fields_UPDATED_BY="fields_updated_by"
        val COL_fields_DELETED_BY="fields_deleted_by"
        val COL_fields_DELETED_AT="fields_deleted_at"

        //ravi -offline---------table-----graph_charts

        val TABLE_graph_charts="graph_charts"
        val COL_graph_charts_PRIMARYKEY="graph_charts_Primary"
        val COL_graph_charts_SERVERID="graph_charts_serverid"
        val COL_graph_charts_NAME="graph_charts_name"
        val COL_graph_charts_DESCIPTION="graph_charts_description"
        val COL_graph_charts_OBJECTCLASS="graph_charts_object_class"
        val COL_graph_charts_COM_GROUP="graph_charts_com_group"
        val COL_graph_charts_TITLE="graph_charts_title"
        val COL_graph_charts_ABCISSA_TITLE="graph_charts_abcissa_title"
        val COL_graph_charts_ORDINATE_TITLE="graph_chartsordinate_title"
        val COL_graph_charts_CREATED_DATE="graph_charts_created_date"
        val COL_graph_charts_CREATED_BY="graph_charts_created_by"
        val COL_graph_charts_LASTCHANGED_BY="graph_charts_last_changed_by"
        val COL_graph_charts_LASTCHANGED_DATE="graph_charts_last_changed_date"
        val COL_graph_charts_DELETED_AT="graph_charts_deleted_at"

 //ravi -offline---------table-----graph_chart_objects

        val TABLE_graph_chart_objects="graph_chart_objects"
        val COL_graph_chart_objects_PRIMARYKEY="graph_charts_Primary"
        val COL_graph_chart_objects_SERVERID="graph_chart_objects_serverid"
        val COL_graph_chart_objects_CHARTID="graph_chart_objects_graphs_charts_id"
        val COL_graph_chart_objects_NAME="graph_chart_objects_name"
        val COL_graph_chart_objects_LINETYPE="graph_chart_objects_line_type"
        val COL_graph_chart_objects_RESULT_CLASS="graph_chart_objects_result_class"
        val COL_graph_chart_objects_REF_CLTL_POINT="graph_chart_objects_ref_ctrl_points"
        val COL_graph_chart_objects_GRAPH_CHARTID="graph_chart_objects_graphs_graphcharts_id"
        val COL_graph_chart_objects_CREATED_BY="graph_chart_objects_created_by"
        val COL_graph_chart_objects_CREATED_DATE="graph_chart_objects_created_date"
        val COL_graph_chart_objects_LAST_CHANGED_BY="graph_chart_objects_last_changed_by"
        val COL_graph_chart_objects_LAST_CHANGED_DATE="graph_chart_objects_last_changed_date"
        val COL_graph_chart_objects_DELETED_AT="graph_chart_objects_deleted_at"

        //ravi -offline---------table-----graph_chart_points

        val TABLE_graph_chart_points="graph_chart_points"
        val COL_graph_chart_points_PRIMARYKEY="graph_chart_points_primary"
        val COL_graph_chart_points_SERVERID="graph_chart_points_serverid"
        val COL_graph_chart_points_PACKID="graph_chart_points_packid"
        val COL_graph_chart_points_VALUE="graph_chart_points_value"
        val COL_graph_chart_points_CreateAt="graph_chart_points_createAt"
        val COL_graph_chart_points_CHARTID="graph_chart_points_chartid"
        val COL_graph_chart_points_DURATION="graph_chart_points_duration"


        //ravi -offline---------table-----lists

        val TABLE_lists="lists"
        val COL_lists_PRIMARYKEY="lists_Primary"
        val COL_lists_SERVERID="lists_serverid"
        val COL_lists_NAME="lists_name"
        val COL_lists_COM_GROUP_ID="lists_community_group_id"
        val COL_lists_DESC="lists_description"
        val COL_lists_COM_GROUP="lists_communitygroup"
        val COL_lists_CREATED_BY="lists_created_by"
        val COL_lists_UPDATED_BY="lists_updated_by"
        val COL_lists_DELETED_BY="lists_deleted_by"
        val COL_lists_CREATED_AT="lists_created_at"
        val COL_lists_UPDATED_AT="lists_updated_at"
        val COL_lists_DELETED_AT="lists_deleted_at"

      //ravi -offline---------table-----list_choices

        val TABLE_list_choices="list_choices"
        val COL_list_choices_PRIMARYKEY="list_choices_Primary"
        val COL_list_choices_SERVERID="list_choices_serverid"
        val COL_list_choices_LISTID="list_choices_lists_id"
        val COL_list_choices_CHOICE="list_choices_choice"
        val COL_list_choices_NAME="list_choices_name"
        val COL_list_choices_CHOICE_COM_GROUP="list_choices_choice_communitygroup"
        val COL_list_choices_COM_GROUP_ID="list_choices_community_group_id"
        val COL_list_choices_CREATED_AT="list_choices_created_at"
        val COL_list_choices_UPDATED_AT="list_choices_updated_at"
        val COL_list_choices_DELETED_AT="list_choices_deleted_at"

        //ravi -offline---------table-----sensors

        val TABLE_sensors="sensors"
        val COL_sensors_PRIMARYKEY="sensors_primarykey"
        val COL_sensors_SERVERID="sensors_serverid"
        val COL_sensors_TYPEID="sensors_sensor_type_id"
        val COL_sensors_NAME="sensors_name"
        val COL_sensors_SENSORID="sensors_sensorId"
        val COL_sensors_MODEL="sensors_model"
        val COL_sensors_BRAND="sensors_brand"
        val COL_sensors_SENSORIP="sensors_sensorIp"
        val COL_sensors_OWNER="sensors_owner"
        val COL_sensors_USERID="sensors_user_id"
        val COL_sensors_UNITID="sensors_unit_id"
        val COL_sensors_MINIMUM="sensors_minimum"
        val COL_sensors_MAXIMUM="sensors_maximum"
        val COL_sensors_COM_GROUP="sensors_community_group"
        val COL_sensors_CONNECTEDBOARD="sensors_connected_board"
        val COL_sensors_CONTAINERID="sensors_container_id"
        val COL_sensors_CREATEDBY="sensors_created_by"
        val COL_sensors_UPDATEDBY="sensors_updated_by"
        val COL_sensors_DELETEDBY="sensors_deleted_by"
        val COL_sensors_CREATEDAT="sensors_created_at"
        val COL_sensors_UPDATEDAT="sensors_updated_at"
        val COL_sensors_DELETEDAT="sensors_deleted_at"

        //ravi -offline---------table-----units

        val TABLE_units="units"
        val COL_units_PRIMARYID="units_primarykey"
        val COL_units_SERVERID="units_serverid"
        val COL_units_NAME="units_name"
        val COL_units_DESCRIPTION="units_description"
        val COL_units_COM_GROUP="units_communitygroup"
        val COL_units_CREATED_BY="units_created_by"
        val COL_units_UPDATED_BY="units_updated_by"
        val COL_units_DELETED_BY="units_deleted_by"
        val COL_units_CREATED_AT="units_created_at"
        val COL_units_UPDATED_AT="units_updated_at"
        val COL_units_DELETED_AT="units_deleted_at"

   //ravi -offline---------table-----team

        val TABLE_team="team"
        val COL_team_PRIMARYID="team_primarykey"
        val COL_team_SERVERID="team_serverid"
        val COL_team_NAME="team_name"
        val COL_team_DESC="team_desciption"
        val COL_team_EMAIL="team_email"
        val COL_team_CONTACT="team_contact"
        val COL_team_ADDRESS="team_address"
        val COL_team_TEAMCLASS="team_teamclass"
        val COL_team_TEAMTYPE="team_teamtype"
        val COL_team_RESPONSIBLE="team_responsible"
        val COL_team_COMGROUP="team_comgroup"
        val COL_team_LOGO="team_logo"
        val COL_team_CREATEDBY="team_createdBy"
        val COL_team_CREATED_AT="team_created_at"
        val COL_team_UPDATED_BY="team_updatedBy"
        val COL_team_UPDATED_AT="team_updatedAT"
        val COL_team_DELETED_BY="team_deletedby"
        val COL_team_DELETED_AT="team_deletedAt"


        //ravi -offline---------table-----task_media_files

        val TABLE_task_media_files="task_media_files"
        val COL_task_media_files_PRIMARYID="task_media_files_primary"
        val COL_task_media_files_SERVERID="task_media_files_serverid"
        val COL_task_media_files_TASKID="task_media_files_taskid"
        val COL_task_media_files_NAME="task_media_files_FILENAME"
        val COL_task_media_files_LINK="task_media_files_link"
        val COL_task_media_files_LOCAL_FILE_PATH="task_media_files_localFILEpath"

        //ravi -offline---------table-----eventType

        val TABLE_eventType="eventType"
        val COL_eventType_PRIMARYID="eventType_primary"
        val COL_eventType_SERVERID="eventType_serverid"
        val COL_eventType_NAME="eventType_name"

        //ravi -offline---------table-----eventStatus

        val TABLE_eventStatus="eventStatus"
        val COL_eventStatus_PRIMARYID="eventStatus_primary"
        val COL_eventStatus_SERVERID="eventStatus_serverid"
        val COL_eventStatus_NAME="eventStatus_name"

        //ravi--offline--------table-task_objects

        val TABLE_task_objects="task_objects"
        val COL_task_objects_PRIMARYID="task_objects_primaryid"
        val COL_task_objects_SERVERID="task_objects_serverid"
        val COL_task_objects_TASKID="task_objects_taskId"
        val COL_task_objects_FUNCTION="task_objects_function"
        val COL_task_objects_CONTAINER="task_objects_container"
        val COL_task_objects_STATUS="task_objects_status"
        val COL_task_objects_LASTCHANGEDDATE="task_objects_lastchnagedDATE"




    }
}