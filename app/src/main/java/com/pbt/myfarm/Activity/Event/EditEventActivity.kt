package com.pbt.myfarm.Activity.Event

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.Event
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CREATEEVENT
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EDITEVENT_ID
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_edit_event.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EditEventActivity : AppCompatActivity(), retrofit2.Callback<ResposneUpdateEvent> {
    var viewModel: ViewModeleditEvent? = null
    var editEventID = ""
    val TAG = "EditEventActivity"
    val communityGroupid = ArrayList<String>()
    val communityGroupname = ArrayList<String>()
    val tyepid = ArrayList<String>()
    val typename = ArrayList<String>()
    val responsibleid = ArrayList<String>()
    val responsiblename = ArrayList<String>()
    val teamid = ArrayList<String>()
    val teamname = ArrayList<String>()
    val statusid = ArrayList<String>()
    val statusname = ArrayList<String>()
    val closedid = ArrayList<String>()
    val closedname = ArrayList<String>()
    val closedarray = arrayListOf("Yes", "No")
    var idcommunity = ""
    var idresponsible = ""
    var idteam = ""
    var idstatus = ""
    var idtype = ""
    var idclosed = ""
    val myCalendar: Calendar = Calendar.getInstance()
    var isCreateEvent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        if (intent.extras != null) {
            editEventID = intent.extras!!.getString(CONST_EDITEVENT_ID)!!
            isCreateEvent = intent.extras!!.getBoolean(CONST_CREATEEVENT)
            AppUtils.logDebug(TAG,"EDITEVENTid "+editEventID +"IsCreateevent "+ isCreateEvent.toString())

            initViewModel(editEventID, isCreateEvent)
        }
        btn_submitevent.setOnClickListener {
            if (ed_event_name.text.isEmpty() &&
                ed_event_expstartdt.text.isEmpty()&& ed_event_startdt.text.isEmpty()
            ) {
                Toast.makeText(this, "Please fill Name or Expected StartDate or Start Date", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (AppUtils().isInternet(this)) {
                    if (isCreateEvent){

                        ApiClient.client.create(ApiInterFace::class.java).storeEvent(
                            MySharedPreference.getUser(this)?.id.toString(),
                            ed_event_name.text.toString(),
                            ed_event_desc.text.toString(),
                            ed_event_expstartdt.text.toString(),
                            idtype,
                            ed_event_expenddt.text.toString(),
                            ed_event_expDuration.text.toString(),
                            ed_event_startdt.text.toString(),
                            ed_event_enddt.text.toString(),
                            ed_event_Duration.text.toString(),
                            idcommunity,
                            idstatus,
                            idresponsible,
                            idteam,
                            idclosed
                        ).enqueue(this)
                    }
                    else{

                    }
                    ApiClient.client.create(ApiInterFace::class.java).updateEvent(
                        MySharedPreference.getUser(this)?.id.toString(),
                        editEventID,
                        ed_event_name.text.toString(),
                        ed_event_desc.text.toString(),
                        idtype,
                        ed_event_expstartdt.text.toString(),
                        ed_event_expenddt.text.toString(),
                        ed_event_expDuration.text.toString(),
                        ed_event_startdt.text.toString(),
                        ed_event_enddt.text.toString(),
                        ed_event_Duration.text.toString(),
                        idcommunity,
                        idstatus,
                        idresponsible,
                        idteam,
                        idclosed
                    ).enqueue(this)
                } else {

                    val db = DbHelper(this, null)

                    val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    val lastid = db.getLastofEvent()
                    val idd = lastid.toInt() + 1
                    var event: Event? = null

                    if (isCreateEvent){
                        event = Event(

                            actual_duration = ed_event_Duration.text.toString(),
                            actual_end_date = ed_event_enddt.text.toString(),
                            actual_start_date = ed_event_startdt.text.toString(),
                            assigned_team = idteam.toInt(),
                            com_group = idcommunity.toInt(),
                            description = ed_event_desc.text.toString(),
                            exp_duration = ed_event_expDuration.text.toString(),
                            exp_end_date = ed_event_expenddt.text.toString(),
                            exp_start_date = ed_event_expstartdt.text.toString(),
                            name = ed_event_name.text.toString(),
                            responsible = idresponsible.toInt(),
                            id = idd,
                            type = idtype.toInt(),
                            closed = idclosed.toInt(),
                            created_date = currentDate,last_changed_date = currentDate,
                            closed_date = "",
                            eventStatus=idstatus.toInt(),


                        )
                     val issuccess=   db.eventsCreateOffline(event, false)
                        if (issuccess){
                            finish()
                        }

                    }
                    else{

                        event = Event(
                            actual_duration = ed_event_Duration.text.toString(),
                            actual_end_date = ed_event_enddt.text.toString(),
                            actual_start_date = ed_event_startdt.text.toString(),
                            assigned_team = idteam.toInt(),
                            com_group = idcommunity.toInt(),
                            description = ed_event_desc.text.toString(),
                            exp_duration = ed_event_expDuration.text.toString(),
                            exp_end_date = ed_event_expenddt.text.toString(),
                            exp_start_date = ed_event_expstartdt.text.toString(),
                            name = ed_event_name.text.toString(),
                            responsible = idresponsible.toInt(),
                            id = editEventID.toInt(),
                            type = idtype.toInt(),
                            closed = idclosed.toInt(),
                            created_date = currentDate,
                            last_changed_date = currentDate,
                            closed_date = "",
                            eventStatus=idstatus.toInt(),

                            )
                        val issuccess=  db.eventsCreateOffline(event, true)
                        if (issuccess){
                            finish()
                        }
                    }
                }

            }
        }
        ed_event_expenddt.setOnClickListener {
            openDateDialog(ed_event_expenddt)
        }
        ed_event_startdt.setOnClickListener {
            openDateDialog(ed_event_startdt)
        }
        ed_event_expstartdt.setOnClickListener {
            openDateDialog(ed_event_expstartdt)
        }
        ed_event_enddt.setOnClickListener {
            openDateDialog(ed_event_enddt)
        }
    }

    private fun initViewModel(editEventID: String, isCreateEvent: Boolean) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModeleditEvent::class.java)
        if (AppUtils().isInternet(this)) {

            if (isCreateEvent){
                setOfflineData(false)
            }
            else{
                viewModel?.onEditEvent(editEventID, this, isCreateEvent)
                viewModel?.editeventlist?.observe(this, Observer { list ->
                    if (!list.isNullOrEmpty()) {
                        AppUtils.logDebug(TAG, "Editeevent ========" + Gson().toJson(list))
                        getSpinnerData(list)
                    }
                })
            }


        }
        else {
            if (isCreateEvent) {
                setOfflineDataCreate()
            } else {

                setOfflineData(true)

            }
        }
    }

    private fun setOfflineData(isOffline:Boolean) {

        val db = DbHelper(this, null)
        val comGrouplist = db.getCommunityGroupList()
        val teamlist = db.getTeamList()
        val responsibleList = db.getPersonList()
        val eventtype = db.getAllEventTypes()
        val eventSts = db.getAllEventStatus()


        for (i in 0 until comGrouplist.size) {
            val item = comGrouplist.get(i)
            communityGroupid.add(item.id.toString())
            communityGroupname.add(item.name!!)
        }


        for (i in 0 until teamlist.size) {
            val item = teamlist.get(i)

            teamid.add(item.id.toString())
            teamname.add(item.name.toString())
        }

        for (i in 0 until responsibleList.size) {
            val item = responsibleList.get(i)

            responsibleid.add(item.id.toString())
            responsiblename.add(item.lname + item.fname)
        }
        for (i in 0 until eventSts.size) {
            val item = eventSts.get(i)
            statusid.add(item.id)
            statusname.add(item.name)
        }
        for (i in 0 until eventtype.size) {
            val item = eventtype.get(i)

            tyepid.add(item.id)
            typename.add(item.name)
        }
        setSpinnerCommunity(sp_event_communitygroup, communityGroupname)
        setSpinnerresponsible(sp_event_responsible, responsiblename)
        setSpinnerstatus(sp_event_status, statusname)
        setSpinnerteam(sp_event_team, teamname)
        setSpinnertype(sp_event_type, typename)
        setSpinnerclosed(sp_event_closed, closedarray)

        if (isOffline){
            val editEventList = db.getEditEventData(editEventID)
            AppUtils.logDebug(TAG, "Eventlist==" + editEventList.toString())
            val dataa = editEventList
            if (dataa.field_exp_start_date.isNullOrEmpty()){
                dataa.field_actual_end_date=""
            }
            if (dataa.field_exp_end_date.isNullOrEmpty()){
                dataa.field_exp_end_date=""
            }
            if (dataa.field_exp_duration.isNullOrEmpty()){
                dataa.field_exp_duration=""
            }
            if (dataa.field_actual_start_date.isNullOrEmpty()){
                dataa.field_actual_start_date=""
            }
            if (dataa.field_actual_end_date.isNullOrEmpty()){
                dataa.field_actual_end_date=""
            }
            if (dataa.field_description.isNullOrEmpty()){
                dataa.field_description=""
            }
            if (dataa.field_name.isNullOrEmpty()){
                dataa.field_name=""
            }

            getEditData(
                dataa.field_exp_start_date,
                dataa.field_exp_end_date,
                dataa.field_exp_duration,
                dataa.field_actual_start_date,
                dataa.field_actual_end_date,
                dataa.field_description,
                dataa.field_name,
                dataa
            )
        }

//        setSpinerSelection(dataa)
    }
    private fun setOfflineDataCreate() {

        val db = DbHelper(this, null)
        val comGrouplist = db.getCommunityGroupList()
        val teamlist = db.getTeamList()
        val responsibleList = db.getPersonList()
        val eventtype=db.getAllEventTypes()
        val eventSts=db.getAllEventStatus()

        for (i in 0 until comGrouplist.size) {
            val item = comGrouplist.get(i)
            communityGroupid.add(item.id.toString())
            communityGroupname.add(item.name!!)
        }


        for (i in 0 until teamlist.size) {
            val item = teamlist.get(i)

            teamid.add(item.id.toString())
            teamname.add(item.name.toString())
        }

        for (i in 0 until responsibleList.size) {
            val item = responsibleList.get(i)

            responsibleid.add(item.id.toString())
            responsiblename.add(item.lname + item.fname)
        }
        for (i in 0 until eventSts.size) {
            val item = eventSts.get(i)
            statusid.add(item.id)
            statusname.add(item.name)
        }
        for (i in 0 until eventtype.size) {
            val item = eventtype.get(i)

            tyepid.add(item.id)
            typename.add(item.name)
        }
        setSpinnerCommunity(sp_event_communitygroup, communityGroupname)
        setSpinnerresponsible(sp_event_responsible, responsiblename)
        setSpinnerstatus(sp_event_status, statusname)
        setSpinnerteam(sp_event_team, teamname)
        setSpinnertype(sp_event_type, typename)
        setSpinnerclosed(sp_event_closed, closedarray)

    }

    private fun getSpinnerData(list: List<Data>) {

        if (AppUtils().isInternet(this)) {
            for (i in 0 until list.size) {
                val item = list.get(i)

                for (g in 0 until item.field_com_group_list.size) {
                    val itemi = item.field_com_group_list.get(g)
                    communityGroupid.add(itemi.id.toString())
                    communityGroupname.add(itemi.name)
                }
                setSpinnerCommunity(sp_event_communitygroup, communityGroupname)
                for (a in 0 until item.field_responsible_list!!.size) {
                    val itema = item.field_responsible_list!!.get(a)
                    responsibleid.add(itema.id.toString())
                    responsiblename.add(itema.name)
                }
                setSpinnerresponsible(sp_event_responsible, responsiblename)
                for (b in 0 until item.field_status_list!!.size) {
                    val itemb = item.field_status_list!!.get(b)
                    statusid.add(itemb.id.toString())
                    statusname.add(itemb.name)
                }
                setSpinnerstatus(sp_event_status, statusname)
                for (c in 0 until item.field_team_list!!.size) {
                    val itemc = item.field_team_list!!.get(c)
                    teamid.add(itemc.id.toString())
                    teamname.add(itemc.name)
                }
                setSpinnerteam(sp_event_team, teamname)
                for (d in 0 until item.field_type_list!!.size) {
                    val itemd = item.field_type_list!!.get(d)
                    tyepid.add(itemd.id.toString())
                    typename.add(itemd.name)

                }
                setSpinnertype(sp_event_type, typename)

                setSpinnerclosed(sp_event_closed, closedarray)
                if (!isCreateEvent) {
                    getEditData(
                        item.field_exp_start_date, item.field_exp_end_date,
                        item.field_exp_duration, item.field_actual_start_date,
                        item.field_actual_end_date,
                        item.field_description,
                        item.field_name, item
                    )
                }

            }
        }


    }

    private fun getEditData(
        fieldExpStartDate: String?,
        fieldExpEndDate: String?, fieldExpDuration: String?,
        fieldActualStartDate: String?, fieldActualEndDate: String?,
        fieldDescription: String?, fieldName: String?,
        data: Data
    ) {

        if (!fieldExpDuration.isNullOrEmpty()) {
            ed_event_Duration.setText(fieldExpDuration)

        }
        else{
            ed_event_Duration.setText("")

        }
        if (!fieldDescription.isNullOrEmpty()) {
            ed_event_desc.setText(fieldDescription)

        }
        else{
            ed_event_desc.setText("")

        }
        if (!fieldActualEndDate.isNullOrEmpty()) {
            ed_event_enddt.setText(fieldActualEndDate)

        }
        else{
            ed_event_enddt.setText("")

        }
        if (!fieldExpDuration.isNullOrEmpty()) {
            ed_event_expDuration.setText(fieldExpDuration)

        }
        else{
            ed_event_expDuration.setText("")

        }
        if (!fieldExpStartDate.isNullOrEmpty()) {
            ed_event_expstartdt.setText(fieldExpStartDate)

        }
        else
        {
            ed_event_expstartdt.setText("")

        }
        if (!fieldName.isNullOrEmpty()) {
            ed_event_name.setText(fieldName)

        }
        else{
            ed_event_name.setText("")

        }
        if (!fieldActualStartDate.isNullOrEmpty()) {
            ed_event_startdt.setText(fieldActualStartDate)

        }
        else{
            ed_event_startdt.setText("")

        }
        if (!fieldExpEndDate.isNullOrEmpty()) {
            ed_event_expenddt.setText(fieldExpEndDate)

        }
        else{
            ed_event_expenddt.setText("")

        }
        setSpinerSelection(data)

    }

    private fun setSpinerSelection(data: Data) {
        if (data.field_com_group != null) {
            setselectedId(data.field_com_group!!, communityGroupid, sp_event_communitygroup)
        }
        if (data.field_responsible != null) {
            setselectedId(data.field_responsible!!, responsibleid, sp_event_responsible)
        }
        if (data.field_type != null) {
            setselectedId(data.field_type!!, tyepid, sp_event_type)
        }
        if (data.field_assigned_team != null) {
            setselectedId(data.field_assigned_team!!, teamid, sp_event_team)
        }
        if (data.field_status != null) {
            setselectedId(data.field_status!!, statusid, sp_event_status)
        }


    }

    private fun setselectedId(idd: Int, list: ArrayList<String>, spipnner: Spinner) {
        for (i in 0 until list.size) {
            if (list.get(i) == idd.toString()) {
                spipnner.setSelection(i)
            }
        }
    }

    private fun setSpinnertype(spEventType: Spinner?, typename: ArrayList<String>) {
        setSpinners(spEventType, typename)
        spEventType?.setOnItemSelectedListener(myListener)
        progresbar_editevent.visibility = View.GONE

    }

    private fun setSpinnerteam(spEventTeam: Spinner?, teamname: ArrayList<String>) {
        setSpinners(spEventTeam, teamname)
        spEventTeam?.setOnItemSelectedListener(myListener)

    }

    private fun setSpinnerstatus(spEventStatus: Spinner?, statusname: ArrayList<String>) {
        setSpinners(spEventStatus, statusname)
        spEventStatus?.setOnItemSelectedListener(myListener)

    }

    private fun setSpinnerclosed(spEventClosed: Spinner?, closedname: ArrayList<String>) {
        setSpinners(spEventClosed, closedname)
        spEventClosed?.setOnItemSelectedListener(myListener)

    }

    private fun setSpinnerresponsible(
        spEventResponsible: Spinner?,
        responsiblename: ArrayList<String>
    ) {
        setSpinners(spEventResponsible, responsiblename)
        spEventResponsible?.setOnItemSelectedListener(myListener)

    }

    private fun setSpinnerCommunity(
        spEventCommunitygroup: Spinner?,
        communityGroupname: ArrayList<String>
    ) {
        setSpinners(spEventCommunitygroup, communityGroupname)
        spEventCommunitygroup?.setOnItemSelectedListener(myListener)

    }

    private fun setSpinners(spinner: Spinner?, list: ArrayList<String>) {

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        spinner?.adapter = adapter
    }


    var myListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int,
                id: Long
            ) {
                when (parent?.id) {

                    R.id.sp_event_communitygroup -> {
                        idcommunity = communityGroupid.get(position)

                    }
                    R.id.sp_event_closed -> {
                        idclosed = id.toString()
                    }
                    R.id.sp_event_responsible -> {
                        idresponsible = responsibleid.get(position)
                    }
                    R.id.sp_event_type -> {
                        idtype = tyepid.get(position)
                    }
                    R.id.sp_event_team -> {
                        idteam = teamid.get(position)
                    }
                    R.id.sp_event_status -> {
                        idstatus = statusid.get(position)
                    }
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }

    fun openDateDialog(dateEdittext: EditText) {

        val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->

            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            setSelectedDate(dateEdittext)
        }
        DatePickerDialog(
            this, date, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setSelectedDate(date: EditText) {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        date.setText(dateFormat.format(myCalendar.time))
    }

    override fun onResponse(
        call: Call<ResposneUpdateEvent>,
        response: Response<ResposneUpdateEvent>
    ) {
        if (response.body()?.error == false) {
            Toast.makeText(this,"Inserted Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
        else {
            Toast.makeText(this, response.body()?.msg.toString(), Toast.LENGTH_SHORT).show()

            try {
                AppUtils.logDebug(TAG, response.body()?.msg.toString())
            } catch (e: Exception) {
                AppUtils.logDebug(TAG, e.message.toString())

            }
        }

    }

    override fun onFailure(call: Call<ResposneUpdateEvent>, t: Throwable) {
        try {
            AppUtils.logDebug(TAG, t.message.toString())
        } catch (e: Exception) {
            AppUtils.logDebug(TAG, e.message.toString())

        }
    }

}