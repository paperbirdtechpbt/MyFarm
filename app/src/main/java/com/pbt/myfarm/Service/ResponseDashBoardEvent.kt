package com.pbt.myfarm.Service

import com.google.gson.annotations.SerializedName

data class ResponseDashBoardEvent(
    @SerializedName("error") var error: Boolean,
    @SerializedName("Responsible") var Responsible: List<ResponseList>,
    @SerializedName("Team") var Team: List<TeamList>,
    @SerializedName("CommunityGrp") var CommunityGrp: List<CommunityGrp>,
    @SerializedName("EventTyp") var EventTyp: List<EventTyp>,
    @SerializedName("EventSts") var EventSts: List<EventSts>,
)

data class ResponseList(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
)

data class TeamList(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
)

data class EventTyp(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
)

data class CommunityGrp(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
)

data class EventSts(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
)
