package com.pbt.myfarm.Activity.DashBoardEvent

import android.annotation.SuppressLint
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_dash_board_event.*
import java.text.SimpleDateFormat
import java.util.*


class DashBoardEventActivity : AppCompatActivity() {
    val myCalendar: Calendar = Calendar.getInstance()
    companion object {
        val TAG = "##DashBoardEventActivity"
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_event)
        supportActionBar?.hide()

        val events: MutableList<EventDay> = ArrayList()
        val db = DbHelper(this, null)
        val tasklist = db.readData()

        tasklist.forEach {
            val dd = it.StartDate
            AppUtils.logDebug(TAG, "For Each" + dd)

            val sdf = SimpleDateFormat("yyyy-MM-dd")

            val calendar: Calendar = Calendar.getInstance()
            calendar.setTime(sdf.parse(dd))

            events.add(EventDay(calendar, R.drawable.ic_baseline_event))

            val calendarView: CalendarView = findViewById<View>(R.id.calendarView) as CalendarView
            calendarView.setEvents(events)
        }
        btn_select_month.setOnClickListener {

//            val pd = MonthYearPickerDialog()
//            pd.show(getFragmentManager(), "MonthYearPickerDialog");

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