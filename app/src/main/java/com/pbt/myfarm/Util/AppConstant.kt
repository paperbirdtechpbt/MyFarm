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
        //TABLE_taskfields
        val TASKFIELDS_TABLENAME="taskfieldtablename"
        val TASKFIELDS_PRIMARYKEY="taskfieldsprimarykey"
        val TASKFIELDS_taskid="taskfieldtaskid"
        val TASKFIELDS_fieldid="taskfieldfieldid"
        val TASKFIELDS_value="taskfieldvalue"

        //TABLE taskfields

        val TASKNEW_TABLENAME="TASKNEW_TABLENAME"
        val TABLENEW_PRIMARYKEY="TABLENEW_PRIMARYKEY"
        val TABLENEW_NAMEPREFIX="TABLENEW_NAMEPREFIX"
        val TABLENEW_NAME="TABLENEW_NAME"
        val TABLENEW_DESC="TABLENEW_DESC"
        val TABLENEW_GROUP="TABLENEW_GROUP"
        val TABLENEW_CONFIGID="TABLENEW_CONFIGID"
        val TABLENEW_Status="TABLENEW_Status"
        val TABLENEW_Created_at="TABLENEW_Created_at"
        val TABLENEW_Updated_at="TABLENEW_Updated_at"
        val TABLENEW_deleted_at="TABLENEW_delted_at"
        val TABLENEW_startdate="TABLENEW_startdate"
        val TABLENEW_enddate="TABLENEW_enddate"
        val TABLENEW_lastchangedby="TABLENEW_lastchangedby"
        val TABLENEW_lastchangeddate="TABLENEW_lastchangeddate"
        val TABLENEW_TASKFUNC="TABLENEW_taskfunc"

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




    }
}