package com.pbt.myfarm.Activity.DashBoardEvent

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.pbt.myfarm.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ResponseList
import com.pbt.myfarm.Service.TeamList
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_dash_board_event.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DashBoardEventActivity : AppCompatActivity() {
    //    val myCalendar: Calendar = Calendar.getInstance()
    var viewmodel: ViewModelDashBoardEvent? = null
    var eventList = ArrayList<EventList>()

    companion object {
        val TAG = "##DashBoardEventActivity"
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_event)
        supportActionBar?.hide()


        initViewModel()

//        val events: MutableList<EventDay> = ArrayList()
//        val db = DbHelper(this, null)
//        val tasklist = db.readData()


//        tasklist.forEach {
//            val dd = it.StartDate
//
//            val sdf = SimpleDateFormat("yyyy-MM-dd")
//
//            val calendar: Calendar = Calendar.getInstance()
//            calendar.setTime(sdf.parse(dd))
//
//            events.add(EventDay(calendar, R.drawable.ic_baseline_event))
//
//            val calendarView: com.applandeo.materialcalendarview.CalendarView = findViewById<View>(R.id.calendarView) as CalendarView
//            calendarView.setEvents(events)
//
//        }
        btn_select_month.setOnClickListener {}


    }

    private fun getFormattedDate(time: Date): String {
//        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dd = simpleDateFormat.format(time)
        AppUtils.logDebug(TAG, "ddddatae----" + dd.toString())
        return dd
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDashBoardEvent::class.java)
        viewmodel?.eventTeamList(this)
        viewmodel?.eventList(this)
        viewmodel?.eventResponsiblelist?.observe(this, androidx.lifecycle.Observer { list ->
            setResponsibleSpinner(list)

        })
        viewmodel?.eventTeamlist?.observe(this, androidx.lifecycle.Observer { teamlist ->
            setTeamASpinner(teamlist)

        })
        viewmodel?.eventlivelist?.observe(this, androidx.lifecycle.Observer { eventlist ->

            if (!eventlist.isNullOrEmpty()) {
                eventList = eventlist as ArrayList<EventList>

                setCalender(eventList)

            }

        })

    }

    private fun setCalender(eventList: ArrayList<EventList>) {
        val events: MutableList<EventDay> = ArrayList()
        var item: EventList? = null

        for (i in 0 until eventList.size) {

            item = eventList[i]

            AppUtils.logError(TAG, item.toString() + "============")

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val calendar: Calendar = Calendar.getInstance()
            val todaysdate = sdf.format(calendar.time)

            var expstartdate = item.exp_start_date
            var actualstartdate = item.actual_start_date
            var actualenddate = item.actual_end_date
            var expenddate = item.exp_end_date







            if (!actualstartdate.isNullOrEmpty()) {
                if (todaysdate == expstartdate && actualstartdate == null) {

                    calendar.setTime(sdf.parse(expstartdate))

                    events.add(
                        EventDay(
                            calendar,
                            R.drawable.ic_baseline_event_red,
                            Color.parseColor("#228B22")
                        )
                    )

                } else if (sdf.parse(todaysdate) > sdf.parse(expstartdate) && actualstartdate == null) {
                    if (expenddate != null && expstartdate != null) {


                    }
                    calendar.setTime(sdf.parse(expstartdate))

                    events.add(
                        EventDay(
                            calendar,
                            R.drawable.ic_baseline_event_red,
                            Color.parseColor("#228B22")
                        )
                    )
                } else if (sdf.parse(todaysdate) < sdf.parse(expstartdate) && actualstartdate == null) {
                    calendar.setTime(sdf.parse(expstartdate))

                    events.add(
                        EventDay(
                            calendar,
                            R.drawable.ic_baseline_event_blue,
                            Color.parseColor("#228B22")
                        )
                    )
                } else if (actualstartdate != null) {
                    calendar.setTime(sdf.parse(expstartdate))

                    events.add(
                        EventDay(
                            calendar,
                            R.drawable.ic_baseline_event_green,
                            Color.parseColor("#228B22")
                        )
                    )
                } else if (actualenddate != null) {
                    calendar.setTime(sdf.parse(expstartdate))

                    events.add(
                        EventDay(
                            calendar,
                            R.drawable.ic_baseline_event_grey,
                            Color.parseColor("#228B22")
                        )
                    )
                }


            }
        }

        val calendarView =
            findViewById<View>(R.id.calenderviewb) as com.applandeo.materialcalendarview.CalendarView


        calendarView.setEvents(events)

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                var clickedDayCalendar = eventDay.calendar

                events.clear()
                events.add(EventDay(clickedDayCalendar))
                val sdf = SimpleDateFormat("yyyy-MM-dd")


                var dd = getFormattedDate(eventDay.calendar.time)
                var datelist = ArrayList<EventList>()
                for (i in 0 until eventList.size) {
                    val item = eventList.get(i)
                    if (sdf.parse(dd) >= sdf.parse(eventList.get(i).exp_start_date) && sdf.parse(dd) <= sdf.parse(
                            eventList.get(i).exp_start_date
                        )
                    ) {
                        datelist.add(eventList.get(i))
                    }
                    if (dd == eventList.get(i).exp_start_date) {
                        datelist.add(eventList.get(i))
                    }
                }
                AppUtils.logDebug(TAG, datelist.toString())


            }
        })
    }

    private fun setTeamASpinner(teamlist: List<TeamList>?) {
        AppUtils.logDebug(TAG, teamlist.toString())
        val teamlistname = ArrayList<String>()
        for (i in 0 until teamlist!!.size) {
            teamlistname.add(teamlist.get(i).name)

        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, teamlistname
        )
        spinner_responsibleTeam.adapter = adapter

        setSpinnerListnerteam(spinner_responsibleTeam)

    }

    private fun setResponsibleSpinner(list: List<ResponseList>?) {
        AppUtils.logDebug(TAG, list.toString())
        val listname = ArrayList<String>()
        for (i in 0 until list!!.size) {
            listname.add(list.get(i).name)

        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, listname
        )
        spinner_responsible.adapter = adapter
        setSpinnerListner(spinner_responsible)
    }

    private fun setSpinnerListnerteam(spinnerResponsibleteam: Spinner?) {
        spinnerResponsibleteam?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
//                Toast.makeText(this@DashBoardEventActivity, "clicked-$position", Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }


    private fun setSpinnerListner(spinnerResponsible: Spinner?) {
        spinnerResponsible?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
//                Toast.makeText(this@DashBoardEventActivity, "clicked-$position", Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


}

private fun getDates(dateString1: String, dateString2: String): List<Date> {
    val dates = ArrayList<Date>()
    val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    var date1: Date? = null
    var date2: Date? = null
    try {
        date1 = df1.parse(dateString1)
        date2 = df1.parse(dateString2)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val cal1 = Calendar.getInstance()
    cal1.time = date1
    val cal2 = Calendar.getInstance()
    cal2.time = date2
    while (!cal1.after(cal2)) {
        dates.add(cal1.time)
        cal1.add(Calendar.DATE, 1)
    }
    return dates
}