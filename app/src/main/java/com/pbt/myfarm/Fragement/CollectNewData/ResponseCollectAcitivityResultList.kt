package com.pbt.myfarm.Fragement.CollectNewData

import com.google.gson.annotations.SerializedName

data class  ResponseCollectAcitivityResultList (
    @SerializedName("error")var error:Boolean,
    @SerializedName("data")var data:List<CollectAcitivityResultList>,
    @SerializedName("msg")var msg:String,
        )
