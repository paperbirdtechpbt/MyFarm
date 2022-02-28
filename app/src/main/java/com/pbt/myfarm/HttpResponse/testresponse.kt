package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.Fragement.PackCollect.CollectActivityList
import com.pbt.myfarm.Fragement.PackCollect.SensorsList
import com.pbt.myfarm.TasklistDataModel

data class testresponse(
    @SerializedName("data")  val data: List<TasklistDataModel>,
    @SerializedName("error")   val error: Boolean,
    @SerializedName("msg")   val msg: String,
    @SerializedName("sensors")  val sensors :List<SensorsList>,
    @SerializedName("collect_activity")   val collect_activity: List<CollectActivityList>,
)