package com.pbt.myfarm.HttpResponse

import com.google.gson.annotations.SerializedName
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions

data class BaseTaskFunction (
    @SerializedName("taskFunctionList") var listTask : ArrayList<ListTaskFunctions>
)