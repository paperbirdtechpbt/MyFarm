package com.pbt.myfarm.Activity.DashBoardEvent

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.EventList
import com.pbt.myfarm.ResponseEventList
import com.pbt.myfarm.Service.*
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response


class ViewModelDashBoardEvent(var activity: Application) : AndroidViewModel(activity),
retrofit2.Callback<ResponseDashBoardEvent>{

    var eventResponsiblelist = MutableLiveData<List<ResponseList>>()
    var eventTeamlist = MutableLiveData<List<TeamList>>()
    var eventlivelist = MutableLiveData<List<EventList>>()
    var TAG = "ViewModelDashBoardEvent"

    fun eventTeamList(context: Context) {


        ApiClient.client.create(ApiInterFace::class.java)
            .eventTeamList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)




    }
    fun eventList(context: Context){

        val service=ApiClient.client.create(ApiInterFace::class.java)
        val apiInterFace=service.myeventList(MySharedPreference.getUser(context)?.id.toString())
        apiInterFace.enqueue(object: retrofit2.Callback<ResponseEventList>{
            override fun onResponse(
                call: Call<ResponseEventList>,
                response: Response<ResponseEventList>
            ) {
                if ( !response.body()?.events.isNullOrEmpty()){

                    val basereponse:ResponseEventList= Gson().fromJson(Gson().toJson(response.body()),ResponseEventList::class.java)
                    if (!basereponse.events.isNullOrEmpty()){
                        eventlivelist.value= emptyList()
                        val myeventlist=ArrayList<EventList>()
                        basereponse.events.forEach{
                            myeventlist.add(it)

                        }
                        eventlivelist.value =myeventlist
                    }

                }



            }

            override fun onFailure(call: Call<ResponseEventList>, t: Throwable) {
            }

        })

    }

    override fun onResponse(
        call: Call<ResponseDashBoardEvent>,
        response: Response<ResponseDashBoardEvent>
    ) {
        if (response.body()?.error == false) {
            var baseresponse: ResponseDashBoardEvent = Gson().fromJson(
                Gson().toJson(response.body()), ResponseDashBoardEvent::class.java
            )
            AppUtils.logDebug(TAG, Gson().toJson((response.body())))

//            if (!baseresponse.Responsible.isNullOrEmpty()) {

                eventResponsiblelist.value = emptyList()
                eventTeamlist.value = emptyList()

                var responsible = ArrayList<ResponseList>()
                var team = ArrayList<TeamList>()

                baseresponse.Responsible.forEach {
                    responsible.add(it)

                }

                baseresponse.Team.forEach {
                    team.add(it)

                }
                AppUtils.logDebug(TAG,"responseible--"+responsible.toString())
                AppUtils.logDebug(TAG,"team--"+team.toString())
//                DashBoardEventActivity().setTeamASpinner(team!!)
//                DashBoardEventActivity().setResponsibleSpinner(responsible!!)
                eventTeamlist.value = team
//
                eventResponsiblelist.value = responsible

//            }
        }

    }

    override fun onFailure(call: Call<ResponseDashBoardEvent>, t: Throwable) {
        try {
            AppUtils.logError(TAG, t.message.toString())
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.localizedMessage)

        }
    }
    }



