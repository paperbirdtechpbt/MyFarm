package com.pbt.myfarm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class OffLineSyncModel(
    val Data: Data,
    val error: Boolean,
    val msg:String?=null
)

data class BaseHttpResponse(
    val data: Data,
    val error: Boolean,
    val msg:String?=null
)

data class Data(
    val alert_ranges: List<AlertRange>,
    val alerts: List<Alert>,
    val arch_container_object: List<ArchContainerObject>,
    val collect_activities: List<CollectActivity>,
    val collect_activity_result_unit: List<CollectActivityResultUnit>,
    val collect_activity_results: List<CollectActivityResult>,
    val collect_activity_user: List<CollectActivityUser>,
    val collect_data: List<CollectData>,
    val community_group_user: List<CommunityGroupUser>,
    val community_groupables: List<CommunityGroupable>,
    val community_groups: List<CommunityGroup>,
    val container: List<Container>,
    val container_object: List<ContainerObject>,
//    val dashboard_setting_objects: List<DashboardSettingObject>,
//    val dashboard_settings: List<DashboardSetting>,
    val events: List<Event>,
    val fields: List<Field>,
    val graph_chart_objects: List<GraphChartObject>,
    val graph_charts: List<GraphChart>,
    val incidents: List<Incident>,
    val list_choices: List<Choices>,
    val lists: List<Lists>,
    val notifications: List<Notification>,
    val pack_collect_activity: List<PackCollectActivity>,
    val pack_config_fields: List<PackConfigField>,
    val pack_configs: List<PackConfig>,
    val pack_fields: List<PackField>,
    val pack_parent: List<Any>,
    val packs_new: List<PacksNew>,
    val people: List<People>,
    val person_team: List<PersonTeam>,
    val privileges: List<Privilege>,
    val reminders: List<Reminder>,
    val role_privileges: List<RolePrivilege>,
    val role_user: List<RoleUser>,
    val roles: List<Role>,
    val sensor_types: List<SensorType>,
    val sensors: List<Sensor>,
    val task_config_fields: List<TaskConfigField>,
    val task_config_functions: List<TaskConfigFunction>,
    val task_configs: List<TaskConfig>,
    val task_fields: List<TaskField>,
    val task_media_files: List<TaskMediaFile>,
    val task_objects: List<TaskObject>,
    val tasks: List<Task>,
    val teams: List<Team>,
    val units: List<Unit>,
    val user_collect_activity: List<UserCollectActivity>,
    val users: List<User>,
    val zone: List<Zone>
)

data class AlertRange(
    val alert_id: Int?=null,
    val alert_type: String?=null,
    val collect_activity_id: Int?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: Any?=null,
    val deleted_by: Any?=null,
    val duration_max_value: Any?=null,
    val duration_min_value: Any?=null,
    val id: Int?=null,
    val max_value: Int?=null,
    val min_value: Int?=null,
    val notif_level: String?=null,
    val notif_message: String?=null,
    val result_id: Any?=null,
    val updated_at: String?=null,
    val updated_by: Any?=null
)

data class Alert(
    val communitygroup: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: Any?=null,
    val deleted_by: Any?=null,
    val description: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: Any?=null
)

data class ArchContainerObject(
    val added_by: Int?=null,
    val added_date: String?=null,
    val added_utc: String?=null,
    val `class`: String?=null,
    val container_no: String?=null,
    val deleted_by: Any?=null,
    val deleted_date: Any?=null,
    val deleted_utc: Any?=null,
    val id: Int?=null,
    val object_name: String?=null,
    val object_no: String?=null,
    val session_id: String?=null,
    val type: String?=null
)

data class CollectActivity(
    val communitygroup: Int?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null,
)

data class CollectActivityResultUnit(
    val collect_activity_result_id: Int?=null,
    val id: Int?=null,
    val unit_id: Int?=null
)

data class CollectActivityResult(
    val collect_activity_id: Int?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val id: Int?=null,
    val list_id: String?=null,
    val result_class: String?=null,
    val result_name: String?=null,
    val type_id: String?=null,
    val unit_id: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null,
)

data class CollectActivityUser(
    val collect_activity_id: Int?=null,
    val id: Int?=null,
    val user_id: Int?=null
)

data class CollectData(
    @SerializedName("collect_activity")  val collect_activity:String?=null,
    @SerializedName("unit_value")  val unit_value:String?=null,
    @SerializedName("result_name") val result_name:String?=null,
    @SerializedName("sensor")val sensor:String?=null,
    @SerializedName("user_collecting")val user_collecting:String?=null,
    @SerializedName("datetime_collected")val datetime_collected:String?=null,

    val collect_activity_id: String?=null,
    val serverid: String?=null,
    val created_at: String?=null,
    val created_by: String?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val duration: String?=null,
    val id: String?=null,
    val new_value: String?=null,
    val pack_id: String?=null,
    val result_class: String?=null,
    val result_id: String?=null,
    val sensor_id: String?=null,
    val unit_id: String?=null,
    val updated_at: String?=null,
    val updated_by: String?=null,
    val value: String?=null,
    val collectactivity_name: String?=null,
    val resultname: String?=null,
    val unitname: String?=null,
    val date:String?=null,
    val sensorname:String?=null,
)

data class CommunityGroupUser(
    val community_group_id: Int?=null,
    val id: Int?=null,
    val user_id: Int?=null
)

data class CommunityGroupable(
    val community_group_id: Int?=null,
    val community_groupable_id: Int?=null,
    val community_groupable_type: String?=null,
    val id: Int?=null
)

data class CommunityGroup(
    val community_group: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null,
)

data class Container(
    val capacity_units: Int?=null,
    val `class`: String?=null,
    val com_group: String?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val created_date_utc: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val last_changed_by: Int?=null,
    val last_changed_date: String?=null,
    val last_changed_utc: String?=null,
    val max_capacity: Int?=null,
    val name: String?=null,
    val notification_level: String?=null,
    val parent_container: String?=null,
    val status: String?=null,
    val type: String?=null,
    val zone: String?=null
)

data class ContainerObject(
    val added_by: Int?=null,
    val added_date: String?=null,
    val added_utc: String?=null,
    val `class`: String?=null,
    val container_no: String?=null,
    val deleted_at: String?=null,
    val id: Int?=null,
    val object_name: String?=null,
    val object_no: String?=null,
    val session_id: String?=null,
    val type: String?=null
)

//data class DashboardSettingObject(
//    val created_by: Int?=null,
//    val created_date: String,
//    val dashboard_setting_id: Int,
//    val deleted_at: Any,
//    val id: Int,
//    val last_changed_by: Int,
//    val last_changed_date: String,
//    val object_class: Int,
//    val object_key: Int
//)

//data class DashboardSetting(
//    val com_group: Int,
//    val created_by: Int,
//    val created_date: String,
//    val deleted_at: Any,
//    val description: String,
//    val id: Int,
//    val last_changed_by: Int,
//    val last_changed_date: String,
//    val max_number: Int,
//    val name: String,
//    val title: String
//)

data class Event(
    val actual_duration: String?=null,
    val actual_end_date: String?=null,
    val actual_start_date: String?=null,
    val assigned_team: Int?=null,
    val closed: Int?=null,
    val closed_by: String?=null,
    val closed_date: String?=null,
    val com_group: Int?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    var description: String?=null,
    val exp_duration: String?=null,
    val exp_end_date: String?=null,
    var exp_start_date: String?=null,
    val id: Int?=null,
    val last_changed_by: Int?=null,
    val last_changed_date: String?=null,
    var name: String?=null,
    val responsible: Int?=null,
    val status: Int?=null,
    val task_id: String?=null,
    val type: Int?=null,
    val eventStatus:Int?=null
)

data class Field(
    val altitude: String?=null,
    val altitude_unit: String?=null,
    val area_unit: String?=null,
    val climate: String?=null,
    val communitygroup: String?=null,
    val country: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val description: String?=null,
    val field_boundary: String?=null,
    val field_class: String?=null,
    val field_contact: Int?=null,
    val field_type: String?=null,
    val harvest_period: String?=null,
    val humidity: String?=null,
    val humidity_unit: String?=null,
    val id: Int?=null,
    val last_visited_by: Int?=null,
    val last_visited_date: String?=null,
    val last_visited_date_utc: String?=null,
    val latitude: String?=null,
    val lists_id: String?=null,
    val locality: String?=null,
    val longitude: String?=null,
    val main_culture: String?=null,
    val name: String?=null,
    val number_of_plant: String?=null,
    val other_culture: String?=null,
    val plant_type: String?=null,
    val pluviometry: String?=null,
    val pluviometry_unit: String?=null,
    val region: String?=null,
    val soil_type: String?=null,
    val surface_area: String?=null,
    val team_id: String?=null,
    val temp_unit: String?=null,
    val temperature: String?=null,
    val unit_id: String?=null,
    val updated_at: String?=null,
    val updated_by: String?=null,
    val vegetation: String?=null,
)

data class GraphChartObject(
    val id: Int?=null,
    val line_type: String?=null,
    val name: String?=null,
    val ref_ctrl_points: String?=null,
    val result_class: String?=null,
    @SerializedName("points") val points: ArrayList<Points>,
    @SerializedName("graphs_charts_id") val graphs_charts_id:String?=null,
    //    val created_by: Int,
//    val created_date: String,
//    val deleted_at: String,
//    val graphs_charts_id: Int,
    //    val last_changed_by: Int,
//    val last_changed_date: String,
)

data class Points (
    @SerializedName("id") val id:String?=null,
    @SerializedName( "pack_id") val packId:String?=null,
    @SerializedName("value") val value:String?=null,
    @SerializedName("create_at") val createAt:String?=null,
@SerializedName("duration") val duration:String?=null,
        )

data class GraphChart(
    val abcissa_title: String?=null,
    val com_group: Int?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val last_changed_by: Int?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val object_class: Int?=null,
    val ordinate_title: String?=null,
    val title: String?=null
)

data class Incident(
    val closed_by: Any?=null,
    val closed_date: Any?=null,
    val com_group: Any?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: Any?=null,
    val description: Any?=null,
    val id: Int?=null,
    val pack_reference: Any?=null,
    val pic_link: Any?=null,
    val resolution: Any?=null,
    val status: Any?=null,
    val title: Any?=null,
    val video_link: Any?=null
)

data class Choices(
    val choice: String?=null,
    val choice_communitygroup: String?=null,
    val community_group_id: String?=null,
    val created_at: String?=null,
    val deleted_at: String?=null,
    val id: Int?=null,
    val lists_id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null
)

data class Lists(
    val community_group_id: String?=null,
    val communitygroup: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null
)

data class Notification(
    val closed: String?=null,
    val closed_by: Int?=null,
    val closed_date: String?=null,
    val com_group: Any?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: Any?=null,
    val id: Int?=null,
    val level: Int?=null,
    val message: String?=null,
    val ref_class: Any?=null,
    val ref_container: Any?=null,
    val ref_object_name: Any?=null,
    val ref_object_no: Any?=null,
    val ref_zone: Any?=null,
    val result_id: Int?=null,
    val status: String?=null,
    val type: Int?=null
)

data class PackCollectActivity(
    val collect_activity_id: Int?=null,
    val id: Int?=null,
    val pack_id: Int?=null
)

data class PackConfigField(

    val created_by: Int?=null,
    val created_date: String?=null,
    val default_value: String?=null,
    val deleted_at: String?=null,
    val editable: Int?=null,
    var field_description: String?=null,
    val field_name: String?=null,
    val field_type: String?=null,
    val id: Int?=null,
    val last_changed_by: String?=null,
    val last_changed_date: String?=null,
    val list: String?=null,
    val pack_config_id: Int?=null,
    var field_value:String?=null
)
@Parcelize
data class PackConfig(
    @SerializedName("class")val mclass: Int?=null,
    val collect_activity_id: String?=null,
    val com_group: Int?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val graph_chart_id: String?=null,
    val id: Int?=null,
    val last_changed_by: String?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val name_prefix: String?=null,
    val type: Int?=null
):Parcelable

data class PackField(
    val field_id: String?=null,
    val id: Int?=null,
    val pack_id: Int?=null,
    val value: String?=null
)
@Parcelize
data class PacksNew(
    var collect_activity_id: String?=null,
    var com_group: Int?=null,
    var created_by: Int?=null,
    var created_date: String?=null,
    var deleted_at: String?=null,
    var description: String?=null,
    var id: Int?=null,
    var initial_task_no: String?=null,
    var is_active: Int?=null,
    var last_changed_by: Int?=null,
    var last_changed_date: String?=null,
    var name: String?=null,
    var pack_config_id: String?=null,
    var pack_config_name: String?=null,
var type:String?=null,
    var labeldesciption:String?=null,
    var padzero:String?=null,
    var status:String?=null,
var primaryid:Int?=null,
    var name_prefix:String?=null,
):Parcelable

data class People(
    val address: String?=null,
    val birth_place: String?=null,
    val certification: String?=null,
    val citizenship: String?=null,
    val communitygroup: String?=null,
    val contact: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val description: String?=null,
    val dob: String?=null,
    val email: String?=null,
    val fname: String?=null,
    val id: Int?=null,
    val is_in_coop: Int?=null,
    val is_kakaomundo: Int?=null,
    val kakaomundo_center: String?=null,
    val last_certification_date: String?=null,
    val lname: String?=null,
    val person_class: String?=null,
    val person_type: String?=null,
    val photo: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null,
    val user_id: String?=null,
)

data class PersonTeam(
    val id: Int?=null,
    val person_id: Int?=null,
    val team_id: Int?=null
)

data class Privilege(
    val id: Int?=null,
    val name: String?=null
)

data class Reminder(
    val code: String?=null,
    val completed: Int?=null,
    val completed_at: String?=null,
    val created_at: String?=null,
    val id: Int?=null,
    val updated_at: String?=null,
    val user_id: Int?=null
)

data class RolePrivilege(
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: Any?=null,
    val deleted_by: Any?=null,
    val id: Int?=null,
    val privilege: String?=null,
    val role_id: Int?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null
)

data class RoleUser(
    val id: Int?=null,
    val role_id: Int?=null,
    val user_id: Int?=null
)

data class Role(
    val community_group: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val dashboard_view: String?=null,
    val deleted_at: Any?=null,
    val deleted_by: Int?=null,
    val description: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null
)

data class SensorType(
    val communitygroup: Int?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: Any?=null,
    val deleted_by: Any?=null,
    val description: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null
)

data class Sensor(
    val brand: String?=null,
    val community_group: String?=null,
    val connected_board: String?=null,
    val container_id: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val id: Int?=null,
    val maximum: String?=null,
    val minimum: String?=null,
    val model: String?=null,
    val name: String?=null,
    val owner: String?=null,
    val sensorId: String?=null,
    val sensorIp: String?=null,
    val sensor_type_id: Int?=null,
    val unit_id: Int?=null,
    val updated_at: String?=null,
    val updated_by: String?=null,
    val user_id: Int?=null,
)

data class TaskConfigField(
    var created_by: Int?=null,
    var created_date: String?=null,
    var deleted_at: String?=null,
    var editable: Int?=null,
    var field_description: String?=null,
    var field_name: String?=null,
    var field_type: String?=null,
    var id: Int?=null,
    var last_changed_by: Int?=null,
    var last_changed_date: String?=null,
    var list: String?=null,
    var task_config_id: Int?=null,
    var field_value:String?=null

)

data class TaskConfigFunction(
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val last_changed_by: Int?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val privilege: String?=null,
    val task_config_id: Int?=null
)
@Parcelize
data class TaskConfig(
    @SerializedName("class") var nclass: String?=null,
    val com_group: Int?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val last_changed_by: String?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val name_prefix: String?=null,
    val record_event: String?=null,
    val reportable: String?=null,
    val type: Int?=null,
):Parcelable

data class TaskField(
    val field_id: String?=null,
    val id: Int?=null,
    val task_id: Int?=null,
    val value: String?=null
)

data class TaskMediaFile(
//    val created_by: Int?=null,
//    val created_date: Str`ing?=null,
//    val deleted_at: String?=null,
    val id: Int?=null,
//    val latitude: Long?=null,
//    val longitude: Long?=null,
    val name: String?=null,
    val task_id: Int?=null,
    val link: String?=null,
    val filePathLocal: String?=null,
)


@Parcelize
data class TaskObject(
    val `class`: String?=null,
    val deleted_at: String?=null,
    val function: String?=null,
    val id: Int?=null,
    val last_changed_by: Int?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val no: String?=null,
    val origin: String?=null,
    val task_id: Int?=null,
    val type: String?=null,
    val container: String?=null,
    var status: String?=null,
):Parcelable

data class TaskObjectOffline(
    val taskid:Int?=null,
    val task_function:String?=null,
    val user_id:String?=null,
    val container:String?=null,
)

@Parcelize
data class Task(
    val com_group: Int?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val deleted_at: String?=null,
    val description: String?=null,
    val ended_late: Int?=null,
    val id: Int?=null,
    val last_changed_by: Int?=null,
    val last_changed_date: String?=null,
    val name: String?=null,
    val started_late: Int?=null,
    val status: String?=null,
    val task_config_id: Int?=null,
    val task_func: String?=null,
    val task_config_name: String?=null,
val name_prefix: String?=null,
    var taskConfigName: String?=null,
    val taskConfigDesc: String?=null,
    var taskConfigNamePrefix: String?=null,
    var padzero:String?=null,
    var type:String?=null,
    var labeldesciption:String?=null,

    ):Parcelable

data class Team(
    val address: String?=null,
    val communitygroup: String?=null,
    val contact: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val description: String?=null,
    val email: String?=null,
    val id: Int?=null,
    val logo: String?=null,
    val name: String?=null,
    val responsible: String?=null,
    val team_class: String?=null,
    val team_type: String?=null,
    val updated_at: String?=null,
    val updated_by: Int?=null,
)

data class Unit(
    val communitygroup: Int?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val deleted_at: String?=null,
    val deleted_by: String?=null,
    val description: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val updated_at: String?=null,
    val updated_by: String?=null,
)

data class UserCollectActivity(
    val collect_activity_id: Int?=null,
    val id: Int?=null,
    val user_id: Int?=null
)

data class User(
    val collect_activity_id: String?=null,
    val community_group_id: String?=null,
    val communitygroup: Int?=null,
    val communitygroup_pass: Any?=null,
    val created_at: String?=null,
    val created_by: Any?=null,
    val deleted_at: Any?=null,
    val deleted_by: Any?=null,
    val email: String?=null,
    val external_id: String?=null,
    val family_name: String?=null,
    val id: Int?=null,
    val is_active: String?=null,
    val language: Int?=null,
    val name: String?=null,
    val timezone: Int?=null,
    val updated_at: String?=null,
    val updated_by: Any?=null
)

data class Zone(
    val `class`: String?=null,
    val com_group: String?=null,
    val created_by: Int?=null,
    val created_date: String?=null,
    val created_date_utc: String?=null,
    val deleted_at: Any?=null,
    val description: String?=null,
    val last_changed_by: Any?=null,
    val last_changed_date: String?=null,
    val last_changed_utc: String?=null,
    val name: String?=null,
    val no: Int?=null,
    val parent_zone: String?=null,
    val plan: Any?=null,
    val type: String?=null
)