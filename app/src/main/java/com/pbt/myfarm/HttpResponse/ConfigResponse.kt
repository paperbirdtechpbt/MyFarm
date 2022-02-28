package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.ConfigTaskList

data class ConfigResponse(@SerializedName("data")  val data: List<ConfigTaskList>,
                          @SerializedName("error")   val error: Boolean)