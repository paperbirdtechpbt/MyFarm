package com.pbt.myfarm.Service

import com.pbt.myfarm.Activity.Pack.PackListModel
import com.pbt.myfarm.Activity.PackConfigList.PackConfigResponse
import com.pbt.myfarm.CollectdataRespose
import com.pbt.myfarm.Fragement.CollectNewData.ResponseCollectAcitivityResultList
import com.pbt.myfarm.Fragement.CollectNewData.ResponsecollectAcitivityResultValue
import com.pbt.myfarm.HttpResponse.*
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



}