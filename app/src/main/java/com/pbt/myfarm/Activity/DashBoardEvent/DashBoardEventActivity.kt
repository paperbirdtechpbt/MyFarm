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

    private fun getFormattedDate(time: Date) {
//        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dd = simpleDateFormat.format(time)
        AppUtils.logDebug(TAG, "ddddatae----" + dd.toString())
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


        for (i in 0 until 4) {

            val item = eventList[i]

            AppUtils.logError(TAG, item.toString() + "============")

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val calendar: Calendar = Calendar.getInstance()
            val todaysdate = sdf.format(calendar.time)

            val expstartdate = item.exp_start_date
            val actualstartdate = item.actual_start_date
            val actualenddate = item.actual_end_date
            val expenddate = item.exp_end_date


            if (!item.actual_start_date.isEmpty()) {
                if (todaysdate == expstartdate && actualstartdate == null) {

                    val startdate = sdf.parse(expstartdate)
                    val enddate = sdf.parse(expenddate)


                    if (expenddate != null && expstartdate != null) {
//                      for (i in 0 days)
                        val diff: Long = enddate.getTime() - startdate.getTime()
                        val seconds = diff / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val days = hours / 24
                        val totaldays: Int = days.toInt() + 1

                        for (h in 0 until totaldays) {

                            val c = Calendar.getInstance()
                            c.time = sdf.parse(item.exp_start_date)
                            c.add(Calendar.DATE, totaldays)
                            val dt = sdf.format(c.time)
                            AppUtils.logDebug(TAG, "ddddt===$dt")


                            calendar.setTime(sdf.parse(item.exp_start_date))

//                       events.add(EventDay(calendar, R.drawable.ic_baseline_event_red, Color.parseColor("#228B22"))
                            events.add(
                                EventDay(
                                    calendar,
                                    R.drawable.ic_baseline_event_red,
                                    Color.parseColor("#228B22")
                                )
                            )
                        }
                    }


                } else if (sdf.parse(todaysdate) > sdf.parse(item.exp_start_date) && actualstartdate == null) {

                    val startdate = sdf.parse(expstartdate)
                    val enddate = sdf.parse(expenddate)


                    if (expenddate != null && expstartdate != null) {
//                      for (i in 0 days)
                        val diff: Long = enddate.getTime() - startdate.getTime()
                        val seconds = diff / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val days = hours / 24
                        val totaldays: Int = days.toInt() + 1
                        for (i in 0 until totaldays) {
                            val c = Calendar.getInstance()
                            c.setTime(sdf.parse(item.exp_start_date))
                            c.time = sdf.parse(item.exp_start_date)
                            c.add(Calendar.DATE, totaldays)
                            val dt = sdf.format(c.time)
                            AppUtils.logDebug(TAG, "ddddt===$dt")


//                       events.add(EventDay(calendar, R.drawable.ic_baseline_event_red, Color.parseColor("#228B22"))
                            events.add(
                                EventDay(
                                    c,
                                    R.drawable.ic_baseline_event_red,
                                    Color.parseColor("#228B22")
                                )
                            )
                        }
                    }
//                  calendar.setTime(sdf.parse(item.exp_start_date))
//
//                  events.add(
//                  EventDay(
//                      calendar,
//                      R.drawable.ic_baseline_event_red,
//                      Color.parseColor("#228B22")
//                  )
//              )
                } else if (sdf.parse(todaysdate) < sdf.parse(item.exp_start_date) && actualstartdate == null) {
                    val startdate = sdf.parse(expstartdate)
                    val enddate = sdf.parse(expenddate)


                    if (expenddate != null && expstartdate != null) {
//                      for (i in 0 days)
                        val diff: Long = enddate.getTime() - startdate.getTime()
                        val seconds = diff / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val days = hours / 24
                        val totaldays: Int = days.toInt() + 1
                        for (i in 0 until totaldays) {
                            val c = Calendar.getInstance()
                            c.setTime(sdf.parse(item.exp_start_date))
                            c.time = sdf.parse(item.exp_start_date)
                            c.add(Calendar.DATE, totaldays)
                            val dt = sdf.format(c.time)
                            AppUtils.logDebug(TAG, "ddddt===$dt")


//                       events.add(EventDay(calendar, R.drawable.ic_baseline_event_red, Color.parseColor("#228B22"))
                            events.add(
                                EventDay(
                                    c,
                                    R.drawable.ic_baseline_event_blue,
                                    Color.parseColor("#228B22")
                                )
                            )
                        }
                    }
//                  calendar.setTime(sdf.parse(item.exp_start_date))
//
//                  events.add(
//                  EventDay(
//                      calendar,
//                      R.drawable.ic_baseline_event_blue,
//                      Color.parseColor("#228B22")
//                  )
//              )
                } else if (actualstartdate != null) {
                    val startdate = sdf.parse(expstartdate)
                    val enddate = sdf.parse(expenddate)


                    if (expenddate != null && expstartdate != null) {
//                      for (i in 0 days)
                        val diff: Long = enddate.getTime() - startdate.getTime()
                        val seconds = diff / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val days = hours / 24
                        val totaldays: Int = days.toInt() + 1
                        for (i in 0 until totaldays) {
                            val c = Calendar.getInstance()
                            c.setTime(sdf.parse(item.exp_start_date))
                            c.time = sdf.parse(item.exp_start_date)
                            c.add(Calendar.DATE, totaldays)
                            val dt = sdf.format(c.time)
                            AppUtils.logDebug(TAG, "ddddt===$dt")


//                       events.add(EventDay(calendar, R.drawable.ic_baseline_event_red, Color.parseColor("#228B22"))
                            events.add(
                                EventDay(
                                    c,
                                    R.drawable.ic_baseline_event_green,
                                    Color.parseColor("#228B22")
                                )
                            )
                        }
                    }
//                  calendar.setTime(sdf.parse(item.exp_start_date))
//
//                  events.add(
//                  EventDay(
//                      calendar,
//                      R.drawable.ic_baseline_event_green,
//                      Color.parseColor("#228B22")
//                  )
//              )
                } else if (actualenddate != null) {
                    val startdate = sdf.parse(expstartdate)
                    val enddate = sdf.parse(expenddate)


                    if (expenddate != null && expstartdate != null) {
//                      for (i in 0 days)
                        val diff: Long = enddate.getTime() - startdate.getTime()
                        val seconds = diff / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val days = hours / 24
                        val totaldays: Int = days.toInt() + 1

                        for (i in 0 until totaldays) {
                            val c = Calendar.getInstance()
                            c.setTime(sdf.parse(item.exp_start_date))

                            c.time = sdf.parse(item.exp_start_date)
                            c.add(Calendar.DATE, totaldays)
                            val dt = sdf.format(c.time)
                            AppUtils.logDebug(TAG, "ddddt===$dt")


                            events.add(
                                EventDay(
                                    c,
                                    R.drawable.ic_baseline_event_grey,
                                    Color.parseColor("#228B22")
                                )
                            )
                        }
                    }
//                  calendar.setTime(sdf.parse(item.exp_start_date))
//
//                  events.add(
//                  EventDay(
//                      calendar,
//                      R.drawable.ic_baseline_event_grey,
//                      Color.parseColor("#228B22")
//                  )
//              )
                }


            }
        }

        val calendarView: com.applandeo.materialcalendarview.CalendarView =
            findViewById<View>(R.id.calendarView) as com.applandeo.materialcalendarview.CalendarView


        calendarView.setEvents(events)

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar

                events.clear()
                events.add(EventDay(clickedDayCalendar))



                getFormattedDate(eventDay.calendar.time)



                AppUtils.logDebug(TAG, "onDayClick ${events}")

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

//class MonthYearPickerDialog : DialogFragment() {
//    private var listener: OnDateSetListener? = null
//    fun setListener(listener: OnDateSetListener?) {
//        this.listener = listener
//    }
//
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//        // Get the layout inflater
//        val inflater = requireActivity().layoutInflater
//        val cal = Calendar.getInstance()
//        val dialog: View = inflater.inflate(R.layout.date_picker_dialog, null)
//        val monthPicker = dialog.findViewById<View>(R.id.picker_month) as NumberPicker
//        val yearPicker = dialog.findViewById<View>(R.id.picker_year) as NumberPicker
//        monthPicker.minValue = 0
//        monthPicker.maxValue = 11
//        monthPicker.value = cal[Calendar.MONTH]
//        val year = cal[Calendar.YEAR]
//        yearPicker.minValue = year
//        yearPicker.maxValue = MAX_YEAR
//        yearPicker.value = year
//        builder.setView(dialog) // Add action buttons
//            .setPositiveButton(
//               "Ok",
//                DialogInterface.OnClickListener { dialog, id ->
//                    listener!!.onDateSet(
//                        null,
//                        yearPicker.value,
//                        monthPicker.value,
//                        0
//                    )
//                })
//            .setNegativeButton("Cancle",
//                DialogInterface.OnClickListener { dialog, id -> this@MonthYearPickerDialog.dialog!!.cancel() })
//        return builder.create()
//    }
//
//    companion object {
//        private const val MAX_YEAR = 2099
//    }
//}