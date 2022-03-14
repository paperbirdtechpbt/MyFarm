package com.pbt.myfarm.ModelClass


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
    val dashboard_setting_objects: List<DashboardSettingObject>,
    val dashboard_settings: List<DashboardSetting>,
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
    val communitygroup: Int,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val id: Int,
    val name: String,
    val updated_at: String,
    val updated_by: Int
)

data class CollectActivityResultUnit(
    val collect_activity_result_id: Int,
    val id: Int,
    val unit_id: Int
)

data class CollectActivityResult(
    val collect_activity_id: Int,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val id: Int,
    val list_id: Any,
    val result_class: Any,
    val result_name: String,
    val type_id: String,
    val unit_id: String,
    val updated_at: String,
    val updated_by: Int
)

data class CollectActivityUser(
    val collect_activity_id: Int,
    val id: Int,
    val user_id: Int
)

data class CollectData(
    val collect_activity_id: Any,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val duration: Int,
    val id: Int,
    val new_value: Any,
    val pack_id: Int,
    val result_class: Any,
    val result_id: Int,
    val sensor_id: Int,
    val unit_id: Int,
    val updated_at: String,
    val updated_by: Int,
    val value: String
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
    val community_group: String,
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

data class Container(
    val capacity_units: Int,
    val `class`: Any,
    val com_group: String,
    val created_by: Int,
    val created_date: String,
    val created_date_utc: String,
    val deleted_at: Any,
    val description: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val last_changed_utc: String,
    val max_capacity: Int,
    val name: String,
    val notification_level: Any,
    val parent_container: Any,
    val status: String,
    val type: String,
    val zone: String
)

data class ContainerObject(
    val added_by: Int,
    val added_date: String,
    val added_utc: String,
    val `class`: String,
    val container_no: String,
    val deleted_at: Any,
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
    val actual_duration: String,
    val actual_end_date: String,
    val actual_start_date: String,
    val assigned_team: Int,
    val closed: Int,
    val closed_by: Any,
    val closed_date: Any,
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: String,
    val exp_duration: String,
    val exp_end_date: String,
    val exp_start_date: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: String,
    val responsible: Int,
    val status: Int,
    val task_id: Any,
    val type: Int
)

data class Field(
    val altitude: String,
    val altitude_unit: Any,
    val area_unit: Any,
    val climate: Any,
    val communitygroup: String,
    val country: String,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val description: String,
    val field_boundary: String,
    val field_class: String,
    val field_contact: Int,
    val field_type: String,
    val harvest_period: Any,
    val humidity: String,
    val humidity_unit: String,
    val id: Int,
    val last_visited_by: Int,
    val last_visited_date: Any,
    val last_visited_date_utc: Any,
    val latitude: String,
    val lists_id: Any,
    val locality: String,
    val longitude: String,
    val main_culture: Any,
    val name: String,
    val number_of_plant: String,
    val other_culture: Any,
    val plant_type: Any,
    val pluviometry: String,
    val pluviometry_unit: String,
    val region: String,
    val soil_type: Any,
    val surface_area: String,
    val team_id: Any,
    val temp_unit: Any,
    val temperature: String,
    val unit_id: Any,
    val updated_at: String,
    val updated_by: Any,
    val vegetation: String
)

data class GraphChartObject(
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val graphs_charts_id: Int,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: Any,
    val line_type: String,
    val name: String,
    val ref_ctrl_points: String,
    val result_class: String
)

data class GraphChart(
    val abcissa_title: String,
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: Any,
    val name: String,
    val object_class: Int,
    val ordinate_title: String,
    val title: String
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
    val choice: Any,
    val choice_communitygroup: String,
    val community_group_id: Any,
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val lists_id: Int,
    val name: String,
    val updated_at: String
)

data class Lists(
    val community_group_id: Any,
    val communitygroup: String,
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
    val created_by: Int,
    val created_date: String,
    val default_value: String,
    val deleted_at: Any,
    val editable: Int,
    val field_description: Any,
    val field_name: String,
    val field_type: String,
    val id: Int,
    val last_changed_by: Any,
    val last_changed_date: Any,
    val list: String,
    val pack_config_id: Int
)

data class PackConfig(
    val `class`: Int,
    val collect_activity_id: Any,
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: String,
    val graph_chart_id: Any,
    val id: Int,
    val last_changed_by: Any,
    val last_changed_date: Any,
    val name: String,
    val name_prefix: Any,
    val type: Int
)

data class PackField(
    val field_id: String,
    val id: Int,
    val pack_id: Int,
    val value: String
)

data class PacksNew(
    val collect_activity_id: Any,
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: String,
    val description: String,
    val id: Int,
    val initial_task_no: Any,
    val is_active: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: Int,
    val pack_config_id: Int
)

data class People(
    val address: String,
    val birth_place: String,
    val certification: Any,
    val citizenship: String,
    val communitygroup: String,
    val contact: String,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val description: String,
    val dob: Any,
    val email: String,
    val fname: String,
    val id: Int,
    val is_in_coop: Int,
    val is_kakaomundo: Int,
    val kakaomundo_center: Any,
    val last_certification_date: Any,
    val lname: String,
    val person_class: String,
    val person_type: String,
    val photo: Any,
    val updated_at: String,
    val updated_by: Int,
    val user_id: Any
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
    val brand: String,
    val community_group: String,
    val connected_board: Any,
    val container_id: Any,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val id: Int,
    val maximum: Any,
    val minimum: Any,
    val model: String,
    val name: String,
    val owner: Any,
    val sensorId: String,
    val sensorIp: String,
    val sensor_type_id: Int,
    val unit_id: Int,
    val updated_at: String,
    val updated_by: Any,
    val user_id: Int
)

data class TaskConfigField(
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val editable: Int,
    val field_description: Any,
    val field_name: String,
    val field_type: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val list: String,
    val task_config_id: Int
)

data class TaskConfigFunction(
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: String,
    val privilege: Int,
    val task_config_id: Int
)

data class TaskConfig(
    val `class`: Int,
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val description: String,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: String,
    val name_prefix: String,
    val record_event: Int,
    val reportable: String,
    val type: Int
)

data class TaskField(
    val field_id: String,
    val id: Int,
    val task_id: Int,
    val value: String
)

data class TaskMediaFile(
    val created_by: Int,
    val created_date: String,
    val deleted_at: Any,
    val id: Int,
    val latitude: Any,
    val longitude: Any,
    val name: String,
    val task_id: Int
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

data class Task(
    val com_group: Int,
    val created_by: Int,
    val created_date: String,
    val deleted_at: String,
    val description: String,
    val ended_late: Int,
    val id: Int,
    val last_changed_by: Int,
    val last_changed_date: String,
    val name: Int,
    val started_late: Int,
    val status: String,
    val task_config_id: Int,
    val task_func: Int
)

data class Team(
    val address: String,
    val communitygroup: String,
    val contact: String,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val deleted_by: Any,
    val description: String,
    val email: String,
    val id: Int,
    val logo: Any,
    val name: String,
    val responsible: String,
    val team_class: String,
    val team_type: String,
    val updated_at: String,
    val updated_by: Int
)

data class Unit(
    val communitygroup: Int,
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