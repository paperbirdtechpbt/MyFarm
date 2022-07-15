package com.pbt.myfarm.Activity.kpi.details

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_kpi_detail.*


class KpiDetailActivity : AppCompatActivity() {
    // variable for our bar chart
    var barChart: BarChart? = null

    // variable for our bar data set.
    var barDataSet1: BarDataSet? = null
    var barDataSet2: BarDataSet? = null
    var barDataSet3: BarDataSet?=null
    var barDataSet4: BarDataSet?=null

    lateinit var barEntriesList: ArrayList<BarEntry>


    // creating a string array for displaying days.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kpi_detail)

        btn_back.setOnClickListener{
            finish()
        }

        // initializing variable for bar chart.
        barChart = findViewById(R.id.chartBarChart_kpi)
        // creating a new array list
        val barEntries2: ArrayList<BarEntry>? = null

        barEntries2?.add(BarEntry(0.4f, 5F))
        barEntries2?.add(BarEntry(1f, 6F))
        barEntries2?.add(BarEntry(1.5f, 8F))
        barEntries2?.add(BarEntry(2f, 7F))


        barChart?.getAxisLeft()?.setDrawGridLines(false)
        barChart?.getXAxis()?.setDrawGridLines(false)
        barDataSet1 = BarDataSet(getBarChartDataForSet2(0), "Mveil 1")
        barDataSet1!!.setColor(resources.getColor(R.color.purple_200))

        barDataSet2 = BarDataSet(getBarChartDataForSet2(1), "Mveil 2")
        barDataSet2!!.setColor(resources.getColor(R.color.teal_200))

        barDataSet3 = BarDataSet(getBarChartDataForSet2(2), "Onguee")
        barDataSet3?.setColor(resources.getColor(R.color.red))

        barDataSet4 = BarDataSet(getBarChartDataForSet2(3), "Bokolo")
        barDataSet4?.setColor(resources.getColor(R.color.green))

        // on below line we are adding bar data set to bar data
        val data = BarData(barDataSet1, barDataSet2,barDataSet3,barDataSet4)
        data.setDrawValues(false)

        barChart?.data = data

        barChart?.getDescription()?.setEnabled(false)


        barChart!!.setDragEnabled(true)


        barChart!!.setVisibleXRangeMaximum(4F)

        data.barWidth = 0.4f

        barChart!!.getXAxis().setAxisMinimum(0F)

        barChart!!.animate()


        barChart!!.invalidate()
    }

    private fun getBarChartDataForSet2(i:Int): ArrayList<BarEntry> {
        barEntriesList = ArrayList()
        // on below line we are adding data
        // to our bar entries list
        barEntriesList.add(BarEntry(1f, 4f))
        barEntriesList.add(BarEntry(2f, 6f))
        barEntriesList.add(BarEntry(3f, 2f))
        barEntriesList.add(BarEntry(4f, 8f))
        barEntriesList.add(BarEntry(5f, 10f))


        val entry=ArrayList<BarEntry>()
        entry.add(barEntriesList.get(i))
        return entry
    }

}
