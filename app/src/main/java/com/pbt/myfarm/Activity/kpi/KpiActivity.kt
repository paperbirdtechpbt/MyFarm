package com.pbt.myfarm.Activity.kpi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.pbt.myfarm.Activity.kpi.details.KpiDetailActivity
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_kpi.*


class KpiActivity : AppCompatActivity() {
    var pieChart: PieChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kpi)
        pieChart = findViewById(R.id.chartPeiChart_kpi)

        showPieChart()
        btn_kpidetails.setOnClickListener{
            startActivity(Intent(this,KpiDetailActivity::class.java))
        }

    }

    private fun showPieChart() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = ""

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Estuaire"] = 120
        typeAmountMap["Ngounié"] = 40
        typeAmountMap["Woleu-Ntem"] = 10
        typeAmountMap["Nyanga"] = 80
        typeAmountMap["Ogouee-Lolo"] = 20
        typeAmountMap["Moyen-Ogooue"] = 20
        typeAmountMap["Haut-Ogooué"] = 20
        typeAmountMap["Ogouee-vindo"] = 30
        typeAmountMap["Ogooué Maritime"] = 50


        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#FF8C00"))
        colors.add(Color.parseColor("#0000FF"))
        colors.add(Color.parseColor("#00FF00"))
        colors.add(Color.parseColor("#964B00"))
        colors.add(Color.parseColor("#808080"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.YELLOW)
        colors.add(Color.MAGENTA)
        colors.add(Color.CYAN)

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 10f
        pieDataSet.valueTextColor = Color.BLACK

        //providing color list for coloring different entries
        pieDataSet.colors = colors
        pieDataSet.sliceSpace=1F

        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(false)
        pieChart?.setDrawSliceText(false)
        pieChart?.setDrawMarkers(false)
        pieChart?.setDrawEntryLabels(false)
        pieChart?.getDescription()?.setEnabled(false)

//        pieChart?.setHoleRadius(0.0f)
        pieChart?.setDrawHoleEnabled(false)


        val l: Legend = pieChart!!.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)

        pieChart!!.data = pieData
        pieChart!!.invalidate()
    }}