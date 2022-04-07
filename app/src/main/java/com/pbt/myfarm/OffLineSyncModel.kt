package com.pbt.myfarm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class OffLineSyncModel(
    val Data: Data,
    val error: Boolean
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
    val alert_id: Int,
    val alert_type: String,
    val collect_activity_id: Int,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val duration_max_value: Any,
    val duration_min_value: Any,
    val id: Int,
    val max_value: Int,
    val min_value: Int,
    val notif_level: String,
    val notif_message: String,
    val result_id: Any,
    val updated_at: String,
    val updated_by: Any
)

data class Alert(
    val communitygroup: String,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val description: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val updated_by: Any
)

data class ArchContainerObject(
    val added_by: Int,
    val added_date: String,
    val added_utc: String,
    val `class`: String,
    val container_no: String,
    val deleted_by: Any,
    val deleted_date: Any,
    val deleted_utc: Any,
    val id: Int,
    val object_name: String,
    val object_no: String,
    val session_id: String,
    val type: String
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
    val collect_activity_result_id: Int,
    val id: Int,
    val unit_id: Int
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
    val collect_activity_id: Int,
    val id: Int,
    val user_id: Int
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
    val community_group_id: Int,
    val id: Int,
    val user_id: Int
)

data class CommunityGroupable(
    val community_group_id: Int,
    val community_groupable_id: Int,
    val community_groupable_type: String,
    val id: Int
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
    val added_by: Int,
    val added_date: String,
    val added_utc: String,
    val `class`: String,
    val container_no: String,
    val deleted_at: String,
    val id: Int,
    val object_name: String,
    val object_no: String,
    val session_id: String,
    val type: String
)

data class DashboardSettingObject(
    val created_by: Int,
    val created_date: String,
    val dashboard_setting_id: Int,
    val deleted_at: Any,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val object_class: Int,
    val object_key: Int
)

data class DashboardSetting(
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val max_number: Int,
    val name: String,
    val title: String
)

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
    val type: Int?=null
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
    val closed_by: Any,
    val closed_date: Any,
    val com_group: Any,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: Any,
    val id: Int,
    val pack_reference: Any,
    val pic_link: Any,
    val resolution: Any,
    val status: Any,
    val title: Any,
    val video_link: Any
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
    val community_group_id: String,
    val communitygroup: String,
    val created_at: String,
    val created_by: Int,
    val deleted_at: String,
    val deleted_by: String,
    val description: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val updated_by: Int
)

data class Notification(
    val closed: String,
    val closed_by: Int,
    val closed_date: String,
    val com_group: Any,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val id: Int,
    val level: Int,
    val message: String,
    val ref_class: Any,
    val ref_container: Any,
    val ref_object_name: Any,
    val ref_object_no: Any,
    val ref_zone: Any,
    val result_id: Int,
    val status: String,
    val type: Int
)

data class PackCollectActivity(
    val collect_activity_id: Int,
    val id: Int,
    val pack_id: Int
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
    val field_id: String,
    val id: Int,
    val pack_id: Int,
    val value: String
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
var primaryid:Int?=null
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
    val id: Int,
    val person_id: Int,
    val team_id: Int
)

data class Privilege(
    val id: Int,
    val name: String
)

data class Reminder(
    val code: String,
    val completed: Int,
    val completed_at: String,
    val created_at: String,
    val id: Int,
    val updated_at: String,
    val user_id: Int
)

data class RolePrivilege(
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val id: Int,
    val privilege: String,
    val role_id: Int,
    val updated_at: String,
    val updated_by: Int
)

data class RoleUser(
    val id: Int,
    val role_id: Int,
    val user_id: Int
)

data class Role(
    val community_group: String,
    val created_at: String,
    val created_by: Int,
    val dashboard_view: String,
    val deleted_at: Any,
    val deleted_by: Int,
    val description: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val updated_by: Int
)

data class SensorType(
    val communitygroup: Int,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val description: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val updated_by: Int
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
    val privilege: Int?=null,
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

data class TaskObject(
    val `class`: String,
    val deleted_at: Any,
    val function: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: String,
    val no: String,
    val origin: Any,
    val task_id: Int,
    val type: String
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
    val collect_activity_id: Int,
    val id: Int,
    val user_id: Int
)

data class User(
    val collect_activity_id: String,
    val community_group_id: String,
    val communitygroup: Int,
    val communitygroup_pass: Any,
    val created_at: String,
    val created_by: Any,
    val deleted_at: Any,
    val deleted_by: Any,
    val email: String,
    val external_id: String,
    val family_name: String,
    val id: Int,
    val is_active: String,
    val language: Int,
    val name: String,
    val timezone: Int,
    val updated_at: String,
    val updated_by: Any
)

data class Zone(
    val `class`: String,
    val com_group: String,
    val created_by: Int,
    val created_date: String,
    val created_date_utc: String,
    val deleted_at: Any,
    val description: String,
    val last_changed_by: Any,
    val last_changed_date: String,
    val last_changed_utc: String,
    val name: String,
    val no: Int,
    val parent_zone: String,
    val plan: Any,
    val type: String
)