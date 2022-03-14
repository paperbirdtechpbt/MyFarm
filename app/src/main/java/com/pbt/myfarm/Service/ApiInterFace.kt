package com.pbt.myfarm.Service

import com.pbt.myfarm.Activity.Event.EditEventList
import com.pbt.myfarm.Activity.Event.ResposneUpdateEvent
import com.pbt.myfarm.Activity.Graph.ResponseGraphDetail
import com.pbt.myfarm.Activity.Pack.PackListModel
import com.pbt.myfarm.Activity.PackConfigList.PackConfigResponse
import com.pbt.myfarm.Activity.TaskFunctions.ResponseTaskFunctionaliyt
import com.pbt.myfarm.CollectdataRespose
import com.pbt.myfarm.Fragement.CollectNewData.ResponseCollectAcitivityResultList
import com.pbt.myfarm.Fragement.CollectNewData.ResponsecollectAcitivityResultValue
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.ResponseEventList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Field


interface ApiInterFace {
    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<HttpResponse>

    @FormUrlEncoded
    @POST("api/taskList")
    fun eventList(
        @Field("id") id: String,
        ): Call<testresponse>

    @FormUrlEncoded
    @POST("api/taskConfigList")
    fun taskConfigList(
        @Field("id") id: String,
        ): Call<ConfigResponse>



    @FormUrlEncoded
    @POST("api/taskConfigFieldList")
    fun configFieldList(
        @Field("id") id: String,
        @Field("taskconfig_id") taskconfig_id: String,
        @Field("task_id") task_id: String,
    ):Call<TaskFieldResponse>


@FormUrlEncoded
    @POST("api/storeTask")
   fun storeTask(
        @Field("config_type") config_type: String,
        @Field("description") description: String,
        @Field("community_group") community_group: String,
        @Field("user_id") user_id: String,
        @Field("field_array") field_array:String,
        @Field("name_prefix") name_prefix: String,
    ):Call<testresponse>

   @FormUrlEncoded
    @POST("api/updateTask")
   fun updateTask(
        @Field("config_type") config_type: String,
        @Field("description") description: String,
        @Field("community_group") community_group: String,
        @Field("user_id") user_id: String,
        @Field("field_array") field_array:String,
        @Field("name_prefix") name_prefix: String,
        @Field("task_id") task_id: String,
    ):Call<testresponse>

   @FormUrlEncoded
    @POST("api/updatePack")
   fun updatePack(

        @Field("config_type") config_type: String,
        @Field("description") description: String,
        @Field("community_group") community_group: String,
        @Field("user_id") user_id: String,
        @Field("field_array") field_array:String,
        @Field("name_prefix") name_prefix: String,
        @Field("pack_id") task_id: String,
    ):Call<testresponse>

   @FormUrlEncoded
    @POST("api/storePack")
   fun storePack(
        @Field("config_type") config_type: String,
        @Field("description") description: String,
        @Field("community_group") community_group: String,
        @Field("user_id") user_id: String,
        @Field("field_array") field_array:String,
        @Field("name_prefix") name_prefix: String,
    ):Call<PackFieldResponse>

   @FormUrlEncoded
    @POST("api/deleteTask")
   fun deleteTask(
        @Field("id") id: String,
    ):Call<testresponse>

   @FormUrlEncoded
    @POST("api/packList")
   fun packList(
        @Field("user_id") id: String,
    ):Call<PackListModel>

    @FormUrlEncoded
    @POST("api/packConfigList")
    fun packConfigList(
        @Field("user_id") user_id: String,
    ): Call<PackConfigResponse>

    @FormUrlEncoded
    @POST("api/packConfigFieldList")
    fun packConfigFieldList(
        @Field("user_id") user_id: String,
        @Field("packconfig_id") packconfig_id: String,
        @Field("pack_id") pack_id: String,
    ): Call<PackFieldResponse>

    @FormUrlEncoded
    @POST("api/deletePack")
    fun deletePack(
        @Field("id") id: String,
    ):Call<testresponse>


    @FormUrlEncoded
    @POST("api/collectDataList")
    fun collectDataList(
        @Field("user_id") user_id: String,
        @Field("pack_id") pack_id: String,
    ):Call<CollectdataRespose>

    @FormUrlEncoded
    @POST("api/collectDataFieldList")
    fun collectDataFieldList(
        @Field("user_id") user_id: String,
        @Field("pack_id") pack_id: String,
    ):Call<testresponse>

    @FormUrlEncoded
    @POST("api/collectAcitivityResultList")
    fun collectAcitivityResultList(
        @Field("user_id") user_id: String,
        @Field("collectactivity_id") collectactivity_id: String,
    ):Call<ResponseCollectAcitivityResultList>



    @FormUrlEncoded
    @POST("api/collectAcitivityResultValue")
    fun collectAcitivityResultValue(
        @Field("user_id") user_id: String,
        @Field("result_id") result_id: String,
    ):Call<ResponsecollectAcitivityResultValue>

    @FormUrlEncoded
    @POST("api/storecollectdata")
    fun storecollectdata(
        @Field("pack_id") pack_id: String,
        @Field("result_id") result_id: String,
        @Field("collect_activity_id") collect_activity_id: String,
        @Field("new_value") new_value: String,
        @Field("value") value: String,
        @Field("unit_id") unit_id: String,
        @Field("sensor_id") sensor_id: String,
        @Field("duration") duration: String,
        @Field("user_id") user_id: String,
    ):Call<ResponseCollectAcitivityResultList>

    @FormUrlEncoded
    @POST("api/deletecollectdata")
    fun deletecollectdata(

        @Field("id") id: String,
    ):Call<ResponseCollectAcitivityResultList>
    @FormUrlEncoded
    @POST("api/updateCollectData")
    fun updateCollectData(

        @Field("user_id") user_id: String,
        @Field("pack_id") pack_id: String,
        @Field("result_id0") result_id0: String,
        @Field("value0") value0: String,
        @Field("unit_id0") unit_id0: String,
        @Field("sensor_id0") sensor_id0: String,
        @Field("duration0") duration0: String,
        @Field("collect_id") collect_id: String,
    ):Call<ResponseCollectAcitivityResultList>

    @FormUrlEncoded
    @POST("api/editcollectdata")
    fun editcollectdata(

        @Field("collect_id") collect_id: String,

    ):Call<Responseeditcollectdata>

    @FormUrlEncoded
    @POST("api/taskFunctionList")
    fun taskFunctionList(

        @Field("user_id") user_id: String,
        @Field("task_id") task_id: String,

    ):Call<ResponseTaskFunctionaliyt>
    @FormUrlEncoded
    @POST("api/taskFunctionFieldList")
    fun taskFunctionFieldList(

        @Field("user_id") user_id: String,
        @Field("task_id") task_id: String,
        @Field("lists_id") lists_id: String,

    ):Call<ResponseTaskFunctionaliyt>

    @FormUrlEncoded
    @POST("api/getGraphList")
    fun getGraphList(

        @Field("user_id") user_id: String,
        @Field("pack_config_id") pack_config_id: String,
        ):Call<ResponseTaskFunctionaliyt>

    @FormUrlEncoded
    @POST("api/getgraphdetail")
    fun getgraphdetail(

        @Field("pack_id") pack_id: String,
        @Field("graph_id") graph_id: String,
        ):Call<ResponseGraphDetail>



    @FormUrlEncoded
    @POST("api/eventTeamList")
    fun eventTeamList(

        @Field("user_id") user_id: String,
        ):Call<ResponseDashBoardEvent>

    @FormUrlEncoded
    @POST("api/eventList")
    fun myeventList(

        @Field("user_id") user_id: String,
        ):Call<ResponseEventList>

    @FormUrlEncoded
    @POST("api/deleteEvent")
    fun deleteEvent(
        @Field("id") id: String,
        ):Call<ResponseEventList>

//    @FormUrlEncoded

    @Multipart
    @POST("api/taskExecuteFunction")
     fun uploadFile(@Part file: MultipartBody.Part?,
                    @Part("task_id") task_id: RequestBody,
                    @Part("task_func") task_func: RequestBody,
                    @Part("user_id") user_id: RequestBody,
                    @Part("container") container: RequestBody,)
     : Call<ResponseTaskExecution>

    @FormUrlEncoded
    @POST("api/taskExecuteFunction")
    fun taskExecuteFunction(

        @Field("task_id") task_id: String,
        @Field("task_func") task_func: String,
        @Field("user_id") user_id: String,
        @Field("container") container: String,
//        @Part( file: MultipartBody.Part),
//    @Part file: MultipartBody.Part
        ):Call<ResponseTaskExecution>

    @FormUrlEncoded
    @POST("api/editEvent")
    fun editEvent(
        @Field("user_id") user_id: String,
        @Field("event_id") event_id: String,
    ):Call<EditEventList>

    @FormUrlEncoded
    @POST("api/updateEvent")
    fun updateEvent(
        @Field("user_id") user_id: String,
        @Field("event_id") event_id: String,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("type") type: String,
        @Field("exp_start_date") exp_start_date: String,
        @Field("exp_end_date") exp_end_date: String,
        @Field("exp_duration") exp_duration: String,
        @Field("actual_start_date") actual_start_date: String,
        @Field("actual_end_date") actual_end_date: String,
        @Field("actual_duration") actual_duration: String,
        @Field("community_group") community_group: String,
        @Field("status") status: String,
        @Field("responsible") responsible: String,
        @Field("assigned_team") assigned_team: String,
        @Field("closed") closed: String,
    ):Call<ResposneUpdateEvent>

}