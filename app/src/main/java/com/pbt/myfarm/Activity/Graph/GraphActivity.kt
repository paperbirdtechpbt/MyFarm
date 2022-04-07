package com.pbt.myfarm.Activity.Graph

//import com.github.mikephil.charting.data.

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.PacksNew
import com.pbt.myfarm.R

import com.pbt.myfarm.Util.AppConstant.Companion.PACK_LIST_PACKID
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_graph.*

import java.util.*


class GraphActivity : AppCompatActivity() {
    val TAG = "GraphActivity"
    var viewModel: ViewmodelGraph? = null
    var lineChart: LineChart? = null
    var barChart: BarChart? = null
    var pieChart: PieChart? = null
var packid:PacksNew?=null
    var db:DbHelper?=null

    var lineData: LineData? = null
    var lineDataSet: LineDataSet? = null
    var lineDataSet2: LineDataSet? = null

    var radarData: RadarData? = null
    var radarDataSet: RadarDataSet? = null
    var radarEntries: ArrayList<RadarEntry>? = null


    var lineEntries: ArrayList<Entry>? = null
    var lineEntries2: ArrayList<Entry>? = null
    var barEntries: ArrayList<BarEntry>? = null


    var barData: BarData? = null
    var barDataSet: BarDataSet? = null

    var pieData: PieData? = null
    var pieDataSet: PieDataSet? = null
    var pieEntries: ArrayList<PieEntry>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        db= DbHelper(this,null)
        if (intent.extras!=null){
            packid= intent.extras!!.getParcelable(PACK_LIST_PACKID)
        }

        initviewmodel(packid?.id.toString(),packid)
        lineChart = findViewById(R.id.chartLine)
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.chartPeiChart)

        lineChart!!.getDescription().setEnabled(false)
        barChart!!.getDescription().setEnabled(false)
        pieChart!!.getDescription().setEnabled(false)

        setMylineChart()
    }

    private fun setMylineChart() {
        val mlist = mutableListOf<Int>()
//        mlist.add(R.color.red)
//        mlist.add(R.color.green)
        mlist.add(R.color.black)


        lineEntries = ArrayList()
        lineEntries2 = ArrayList()
        lineEntries!!.add(Entry(10f, 0f))
        lineEntries!!.add(Entry(24f, 12f))
        lineEntries!!.add(Entry(48f, 24f))


        lineDataSet = LineDataSet(lineEntries, "")
//        lineDataSet2 = LineDataSet(lineEntries2, "")
        lineData = LineData(lineDataSet)
        lineChart?.setData(lineData)
        lineChart?.invalidate()
        lineDataSet!!.setColors(mlist)
        lineDataSet!!.valueTextColor = Color.BLACK
        lineDataSet!!.valueTextSize = 18f
    }

    private fun initviewmodel(packid: String, packList: PacksNew?) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewmodelGraph::class.java)
        viewModel?.onConfigFieldList(this,packid,packList)
        viewModel?.configlist?.observe(this, androidx.lifecycle.Observer { list ->

            var graphnam = ArrayList<String>()

            val spinner: Spinner = findViewById(R.id.spinner_graphlist)
            for (i in 0 until list.size) {
                graphnam.add(list.get(i).name!!)
            }
            setspinner(graphnam, spinner, list)

        })
    }

    private fun setspinner(
        graphnam: ArrayList<String>,
        spinner: Spinner,
        list: List<ListTaskFunctions>
    ) {
        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, graphnam)
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dd)

        setListner(spinner, list)
    }

    private fun setListner(spinner: Spinner, list: List<ListTaskFunctions>) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                val id = list.get(position).id
                callGraphDetailApi(id!!,list.get(position))

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }

    private fun callGraphDetailApi(id: String, list: ListTaskFunctions) {

        val chartTypeName =db?.getListChoiceSingleValue(list.object_class.toString())
        showChart(chartTypeName.toString(),list)

//
//        val service = ApiClient.client.create(ApiInterFace::class.java)
//        val apiInterFace = service.getgraphdetail(
//            packid?.id.toString(), id
//        )
//
//        apiInterFace.enqueue(object : Callback<ResponseGraphDetail> {
//            override fun onResponse(
//                call: Call<ResponseGraphDetail>,
//                response: Response<ResponseGraphDetail>
//            ) {
//                AppUtils.logDebug(TAG, Gson().toJson(response.body()))
//                if (response.body()?.error == "success") {
//                    val baserespomse: ResponseGraphDetail = Gson().fromJson(
//                        Gson().toJson(response.body()),
//                        ResponseGraphDetail::class.java
//                    )
//                    val listCharts = ArrayList<ListCharts>()
//                    var chartype: String = ""
//                    var ordinateTitle: String = ""
//                    var lines = ArrayList<ListLines>()
//                    var listPoints = ArrayList<ListPoints>()
//                    val listMYPoints = ArrayList<ListPoints>()
//                    val listMYPointsXaxis = ArrayList<String>()
//                    val listMYPointsYAxis= ArrayList<String>()
//                    baserespomse.charts.forEach {
//                        listCharts.add(it)
//                        chartype = it.graph_type
//                        ordinateTitle = it.graph_ordinate_title
//                    }
//                    for (i in 0 until listCharts.size) {
//                        lines = listCharts.get(i).lines as ArrayList<ListLines>
//                    }
//                    for (i in 0 until lines.size) {
//                        listPoints = lines.get(i).points as ArrayList<ListPoints>
//                        listMYPoints.clear()
//                        listMYPointsXaxis.clear()
//                        listMYPointsYAxis.clear()
//
//                        for (i in 0 until listPoints.size) {
//
//                            listMYPoints.add(listPoints.get(i))
//                            listMYPointsXaxis.add(listPoints.get(i).duration)
//                            listMYPointsYAxis.add(listPoints.get(i).value)
//                        }
//
//
//                    }
//
//                    AppUtils.logDebug(TAG, "listPoints" + lines.size.toString())
//                    AppUtils.logDebug(TAG, "listMYPoints" + listPoints.size.toString())
//                    if (chartype == "Bar chart") {
//
//                        lineChart?.visibility = View.GONE
//                        radarChart?.visibility = View.GONE
//                        pieChart?.visibility = View.GONE
//                        barChart?.visibility = View.VISIBLE
//                        setupBarChart(listCharts, lines, listMYPoints,ordinateTitle)
//                    } else if (chartype == "Pie chart") {
//                        lineChart?.visibility = View.GONE
//                        radarChart?.visibility = View.GONE
//                        barChart?.visibility = View.GONE
//                        pieChart?.visibility = View.VISIBLE
//                        setupLineChart(listCharts, lines, listMYPoints)
//                    }else if (chartype == "Radar chart") {
//                        lineChart?.visibility = View.GONE
//                        radarChart?.visibility = View.VISIBLE
//                        barChart?.visibility = View.GONE
//                        pieChart?.visibility = View.GONE
//                        setupRadarChart(listCharts, lines, listMYPoints)
//                    } else {
//                        lineChart?.visibility = View.VISIBLE
//                        barChart?.visibility = View.GONE
//                        radarChart?.visibility = View.GONE
//                        pieChart?.visibility = View.GONE
//                        setupPeiChart(lines,listMYPointsXaxis,listMYPointsYAxis, listMYPoints,
//                            ordinateTitle)
//                    }
//
//
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseGraphDetail>, t: Throwable) {
//                try {
//                    AppUtils.logError(TAG, t.message.toString())
//                } catch (e: Exception) {
//                    AppUtils.logError(TAG, e.localizedMessage)
//
//                }
//            }
//
//        })
    }

    private fun showChart(chartTypeName: String, list: ListTaskFunctions) {

        val db=DbHelper(this,null)
        val pointslist =db.getGraphChartObjects(list.id.toString())
        for (i in 0 until pointslist.size){
            val item=pointslist.get(i)

                if (item.points.isNotEmpty()){
AppUtils.logDebug(TAG,"pointssss----"+item.points.toString())
             
            }
        }


        AppUtils.logDebug(TAG,"==================pointslist"+pointslist.toString())

            val listCharts = ArrayList<ListCharts>()
            var chartype: String = chartTypeName
            var ordinateTitle: String = list.ordinateTitle.toString()
            var lines = ArrayList<ListLines>()
            var listPoints = ArrayList<ListPoints>()
            val listMYPoints = ArrayList<ListPoints>()
            val listMYPointsXaxis = ArrayList<String>()
            val listMYPointsYAxis= ArrayList<String>()
//
//            baserespomse.charts.forEach {
//                listCharts.add(it)
//                chartype = it.graph_type
//                ordinateTitle = it.graph_ordinate_title
//            }
//
//            for (i in 0 until listCharts.size) {
//                lines = listCharts.get(i).lines as ArrayList<ListLines>
//            }
//
//            for (i in 0 until lines.size) {
//                listPoints = lines.get(i).points as ArrayList<ListPoints>
//                listMYPoints.clear()
//                listMYPointsXaxis.clear()
//                listMYPointsYAxis.clear()
//
//                for (i in 0 until listPoints.size) {
//
//                    listMYPoints.add(listPoints.get(i))
//                    listMYPointsXaxis.add(listPoints.get(i).duration)
//                    listMYPointsYAxis.add(listPoints.get(i).value)
//                }
//
//
//            }
//
//            AppUtils.logDebug(TAG, "listPoints" + lines.size.toString())
//            AppUtils.logDebug(TAG, "listMYPoints" + listPoints.size.toString())
//            if (chartype == "Bar chart") {
//
//                lineChart?.visibility = View.GONE
//                radarChart?.visibility = View.GONE
//                pieChart?.visibility = View.GONE
//                barChart?.visibility = View.VISIBLE
//                setupBarChart(listCharts, lines, listMYPoints,ordinateTitle)
//            } else if (chartype == "Pie chart") {
//                lineChart?.visibility = View.GONE
//                radarChart?.visibility = View.GONE
//                barChart?.visibility = View.GONE
//                pieChart?.visibility = View.VISIBLE
//                setupLineChart(listCharts, lines, listMYPoints)
//            }else if (chartype == "Radar chart") {
//                lineChart?.visibility = View.GONE
//                radarChart?.visibility = View.VISIBLE
//                barChart?.visibility = View.GONE
//                pieChart?.visibility = View.GONE
//                setupRadarChart(listCharts, lines, listMYPoints)
//            } else {
//                lineChart?.visibility = View.VISIBLE
//                barChart?.visibility = View.GONE
//                radarChart?.visibility = View.GONE
//                pieChart?.visibility = View.GONE
//                setupPeiChart(lines,listMYPointsXaxis,listMYPointsYAxis, listMYPoints,
//                    ordinateTitle)
//            }



    }

    private fun setupRadarChart(listCharts: ArrayList<ListCharts>, lines: ArrayList<ListLines>, listMYPoints: ArrayList<ListPoints>) {

        radarEntries = ArrayList()
        val colors = arrayOf(

            Color.parseColor("#007a33"),
            Color.parseColor("#FF6200EE"),
            Color.parseColor("#FFBB86FC"),
            Color.parseColor("#FF018786"),
            Color.parseColor("#F44336"),
            //...more
        )

        val dataSets = ArrayList<IRadarDataSet>()

        for (z in 0 until lines.size) {
            var radarEntries= ArrayList<RadarEntry>()

            var listPointss = lines.get(z).points as ArrayList<ListPoints>

            for (i in 0 until listPointss.size) {
                val x = listPointss.get(i).duration.toFloat()
                val y = listPointss.get(i).value.toFloat()
                radarEntries.add(RadarEntry(x, y))
            }
            val d = RadarDataSet(radarEntries, lines.get(z).name+" (ra)")
            d.lineWidth = 2.5f

//            d.setCircleColor(colors.get(z))
            d.setColor(colors.get(z))
//            d.circleRadius = 4f
            dataSets.add(d)
        }
        val l: Legend = radarChart?.getLegend()!!

        l.entries

        l.position = Legend.LegendPosition.BELOW_CHART_LEFT

        l.yEntrySpace = 10f

        l.isWordWrapEnabled = true
        l.isEnabled = true


        val data = RadarData(dataSets)
        radarChart?.setData(data)
        radarChart?.invalidate()

       radarDataSet = RadarDataSet(radarEntries, "")
        radarData = RadarData(radarDataSet)
        radarChart.setData(radarData)
        radarDataSet?.setValueTextColor(Color.BLACK)
        radarDataSet?.setValueTextSize(18f)
    }

    private fun setupPeiChart(
        lines: ArrayList<ListLines>,
        linesX: ArrayList<String>,
        linesY: ArrayList<String>,
        listPoints: ArrayList<ListPoints>,
        ordinateTitle: String
    ) {


        val mlist = mutableListOf<Int>()
        mlist.add(R.color.red)
        mlist.add(R.color.green)
        mlist.add(R.color.black)
        mlist.add(R.color.purple_200)
        mlist.add(R.color.teal_200)


        lineEntries = ArrayList()
        val colors = arrayOf(

            Color.parseColor("#007a33"),
            Color.parseColor("#FF6200EE"),
            Color.parseColor("#FFBB86FC"),
            Color.parseColor("#FF018786"),
            Color.parseColor("#F44336"),
            //...more
        )

        val dataSets = ArrayList<ILineDataSet>()

        for (z in 0 until lines.size) {
            var lineEntriess= ArrayList<Entry>()

           var listPointss = lines.get(z).points as ArrayList<ListPoints>

            for (i in 0 until listPointss.size) {
                val x = listPointss.get(i).duration.toFloat()
                val y = listPointss.get(i).value.toFloat()
                lineEntriess.add(Entry(x, y))
            }
            val d = LineDataSet(lineEntriess, lines.get(z).name+" ($ordinateTitle)")
            d.lineWidth = 2.5f

            d.setCircleColor(colors.get(z))
            d.setColor(colors.get(z))
            d.circleRadius = 4f
            dataSets.add(d)
        }
        val l: Legend = lineChart?.getLegend()!!

        l.entries

        l.position = Legend.LegendPosition.BELOW_CHART_LEFT

        l.yEntrySpace = 10f

        l.isWordWrapEnabled = true
        l.isEnabled = true


        val data = LineData(dataSets)
        lineChart?.setData(data)
        lineChart?.invalidate()


    }

    private fun setupBarChart(
        listCharts: ArrayList<ListCharts>,
        lines: ArrayList<ListLines>,
        listMYPoints: ArrayList<ListPoints>,
        ordinateTitle: String
    ) {


//
//        barDataSet = BarDataSet(barEntries, "")
//        barData = BarData(barDataSet)
//        barData?.setBarWidth(12f)
//        barChart!!.data = barData
//
//
//
//        barDataSet?.setColors(mlist)
//
//        barDataSet?.setValueTextColor(Color.BLACK)
//        barDataSet?.setValueTextSize(12f)
//
//        barChart?.invalidate()
        barEntries = ArrayList()
        val colors = arrayOf(

            Color.parseColor("#007a33"),
            Color.parseColor("#FF6200EE"),
            Color.parseColor("#FFBB86FC"),
            Color.parseColor("#FF018786"),
            Color.parseColor("#F44336"),
            //...more
        )

        val dataSets = ArrayList<IBarDataSet>()

        for (z in 0 until lines.size) {
            var barEntriess= ArrayList<BarEntry>()

           var listPointss = lines.get(z).points as ArrayList<ListPoints>

            for (i in 0 until listPointss.size) {
                val x = listPointss.get(i).duration.toFloat()
                val y = listPointss.get(i).value.toFloat()
                barEntriess.add(BarEntry(x, y))
            }
            val d = BarDataSet(barEntriess, lines.get(z).name+" ($ordinateTitle)")
//            d.lineWidth = 2.5f

//            d.setCircleColor(colors.get(z))
            d.setColor(colors.get(z))
//            d.circleRadius = 4f
            dataSets.add(d)
        }
        val l: Legend = barChart?.getLegend()!!

        l.entries

        l.position = Legend.LegendPosition.BELOW_CHART_LEFT

        l.yEntrySpace = 10f

        l.isWordWrapEnabled = true
        l.isEnabled = true


        val data = BarData(dataSets)
        barData?.setBarWidth(12f)
        barChart?.setData(data)
        barChart?.invalidate()
    }

    private fun setupLineChart(
        listCharts: ArrayList<ListCharts>,
        lines: ArrayList<ListLines>,
        listMYPoints: ArrayList<ListPoints>
    ) {

        val mlist = mutableListOf<Int>()
        mlist.add(R.color.red)
        mlist.add(R.color.green)
        mlist.add(R.color.black)

        pieEntries = ArrayList()
        for (i in 0 until listMYPoints.size) {
            val x = listMYPoints.get(i).duration.toFloat()
            val y = listMYPoints.get(i).value.toFloat()
            pieEntries!!.add(PieEntry(x, y))


        }



        pieDataSet = PieDataSet(pieEntries, "")
        pieData = PieData(pieDataSet)
        pieChart!!.data = pieData
        pieChart?.invalidate()
        pieDataSet!!.setColors(*ColorTemplate.JOYFUL_COLORS)
        pieDataSet!!.sliceSpace = 2f
        pieDataSet!!.valueTextColor = Color.BLACK
        pieDataSet!!.valueTextSize = 5f
        pieDataSet!!.sliceSpace = 5f

    }
}