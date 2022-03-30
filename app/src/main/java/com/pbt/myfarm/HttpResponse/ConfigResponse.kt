package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.ConfigTaskList
import com.pbt.myfarm.TaskConfig

data class ConfigResponse(
    @SerializedName("data")  val data: List<TaskConfig>,
//    @SerializedName("data")  val data: List<ConfigTaskList>,
                          @SerializedName("error")   val error: Boolean)