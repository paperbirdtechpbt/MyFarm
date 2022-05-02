package com.pbt.myfarm.Activity.DashBoardEvent

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Service.*
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response


class ViewModelDashBoardEvent(var activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<ResponseDashBoardEvent>{

    var eventResponsiblelist = MutableLiveData<List<People>>()
    var eventTeamlist = MutableLiveData<List<Team>>()
    var eventlivelist = MutableLiveData<List<Event>>()
    var TAG = "ViewModelDashBoardEvent"

    fun eventTeamList(context: Context) {

//        if (AppUtils().isInternet(context)){
//
//        ApiClient.client.create(ApiInterFace::class.java)
//            .eventTeamList(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)
//        }
//        else{
            val db=DbHelper(context,null)
            val teamList=db.getTeamList()
            val responsiblelist= db.getPersonList()
            eventTeamlist.value = teamList
            eventResponsiblelist.value = responsiblelist
//        }
    }

    fun eventList(context: Context){
        val userId=MySharedPreference.getUser(context)?.id.toString()
        val db=DbHelper(context,null)
     val eventlist=   db.getAllEventListData("")
        AppUtils.logDebug(TAG,"offlibe eventList"+eventlist.toString())
        eventlivelist.value =eventlist



//        val service=ApiClient.client.create(ApiInterFace::class.java)
//        val apiInterFace=service.myeventList(MySharedPreference.getUser(context)?.id.toString())
//        apiInterFace.enqueue(object: retrofit2.Callback<ResponseEventListDashboard>{
//            override fun onResponse(
//                call: Call<ResponseEventListDashboard>,
//                response: Response<ResponseEventListDashboard>
//            ) {
//                AppUtils.logError(TAG,"on response fun eventlist"+response.body().toString())
//                if ( !response.body()?.events.isNullOrEmpty()){
//
//                    val basereponse:ResponseEventListDashboard= Gson().fromJson(Gson().toJson(response.body()),ResponseEventListDashboard::class.java)
//                    if (!basereponse.events.isNullOrEmpty()){
//                        eventlivelist.value= emptyList()
//                        val myeventlist=ArrayList<EventList>()
//                        basereponse.events.forEach{
//                            myeventlist.add(it)
//
//                        }
//                        eventlivelist.value =myeventlist
//                    }
//
//                }
//                else{
//                    AppUtils.logError(TAG,"on response fun eventlist but events is empty")
//
//                }
//
//
//
//            }
//
//            override fun onFailure(call: Call<ResponseEventListDashboard>, t: Throwable) {
//                AppUtils.logError(TAG,"on failure fun eventlist")
//
//            }
//
//        })

    }

    override fun onResponse(
        call: Call<ResponseDashBoardEvent>,
        response: Response<ResponseDashBoardEvent>
    ) {
        try{
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
//                eventTeamlist.value = team
//
//                eventResponsiblelist.value = responsible

//            }
            }
            else{
                AppUtils.logError(TAG, "error true")
            }

        }
        catch (e:Exception){
            AppUtils.logError(TAG, e.localizedMessage)
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



