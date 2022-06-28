package com.pbt.myfarm.Activity.Map

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anurag.multiselectionspinner.MultiSelectionSpinnerDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.pbt.myfarm.Activity.Event.ListOFMarkerPoints
import com.pbt.myfarm.Activity.Event.ResponsefieldClasses
import com.pbt.myfarm.Activity.Map.MapActivityViewModel.Companion.jsonFeature
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    MultiSelectionSpinnerDialog.OnMultiSpinnerSelectionListener {

    private lateinit var mMap: GoogleMap
    var viewModel: MapActivityViewModel? = null
    var fieldclassName = ArrayList<String>()
    var fieldclass = ArrayList<ResponsefieldClasses.ListofFieldClasses>()
    var fieldclassID = ArrayList<String>()
    var TAG = "MapsActivity"
    var progressDialog: ProgressDialog? = null
   private var markersList: List<ListOFMarkerPoints> =ArrayList()

    companion object {
        var list = ArrayList<LatLng>()
        var hollowPolygon: Polygon? = null
        val myMarker = MarkerOptions()
        var markerName: Marker? = null
//        var markerList: ArrayList<Marker> = ArrayList()
        val selectedItemAreas = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
supportActionBar?.hide()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading Protected Areas")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()

        initviewModel()
    }

    private fun initviewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MapActivityViewModel::class.java)
        viewModel?.context = this
        viewModel?.mCheckBox = progressDialog
        viewModel?.imgview = img1
        viewModel?.getPolygonPointFromApi(this)

        setCheckBoxListner()

        viewModel?.callFieldClasesListApi(this, MySharedPreference.getUser(this)?.id.toString())
        viewModel?.fieldClaseslist?.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                fieldclassID.clear()
                fieldclassName.clear()
                fieldclass.clear()

                for (i in 0 until list.size) {
                    val item = list.get(i)
                    fieldclass.add(item)
                    fieldclassID.add(item.id)
                    fieldclassName.add(item.name)
                    setSpinner(fieldclassName, fieldclassID)
                }
            }
        }


        viewModel?.let { model ->
            model.listMaker.observe(this) {
                markersList=it

                setMarker(it, mMap)
            }
        }
    }

    private fun setSpinner(fieldclassName: ArrayList<String>, fieldclassID: ArrayList<String>) {
        val urlList: MutableList<String> = ArrayList()
        urlList.addAll(fieldclassName)

        spinner_fieldclasses!!.setAdapterWithOutImage(this, urlList, this)
        spinner_fieldclasses.initMultiSpinner(this, spinner_fieldclasses)

    }

    override fun OnMultiSpinnerItemSelected(chosenItems: MutableList<String>?) {
        prgsBar_mapScreen.visibility = View.VISIBLE
        //This is where you get all your items selected from the Multi Selection Spinner :)
        selectedItemAreas.clear()
        mMap.clear()

        AppUtils.logError(TAG, "chooseItemfieldclassID" + chosenItems.toString())

        for (i in 0 until chosenItems!!.size) {
            val choItem = chosenItems.get(i)
            for (z in 0 until fieldclassName.size) {
                if (fieldclassName.get(z) == choItem) {
                    selectedItemAreas.add(fieldclassID.get(z))
                }
            }
        }

        viewModel?.setMarkerPoints(mMap, this@MapsActivity, selectedItemAreas.toString())
        val isfinishedLoop = viewModel?.setAreas(jsonFeature!!, this@MapsActivity)

        Handler(Looper.getMainLooper()).postDelayed({
            prgsBar_mapScreen.visibility = View.GONE
        }, 1500)

    }

    private fun setCheckBoxListner() {

        isShowAreaChecked.setOnCheckedChangeListener { buttonView, isChecked ->

            val progressD = ProgressDialog(this)
            progressD.setMessage("Loading Protected Areas")
            progressD.setCancelable(false)
            progressD.show()
            Handler(Looper.getMainLooper()).postDelayed({
                progressD.dismiss()
            }, 2300)


            if (isChecked) {
                GlobalScope.launch {
                    viewModel?.setAreas(jsonFeature!!, this@MapsActivity)
                }
            } else {
                progressD.dismiss()
                mMap.clear()
//                GlobalScope.launch {
                    setMarker(markersList,mMap)
//                    viewModel?.setMarker(MapActivityViewModel.markersdata, mMap)

//                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewModel?.gMap = googleMap

        val sydney = LatLng(0.4162, 9.4673)

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    sydney.latitude,
                    sydney.longitude
                ), 07.0f
            )
        )
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Gabon"))

    }

    fun setMarker(markersdata: List<ListOFMarkerPoints>?, mMap: GoogleMap) {

        markersdata?.forEach {
            val locate = LatLng(it.lat.toDouble(), it.lng.toDouble())

            val url = URL(it.icon)

             var bmp :Bitmap?=null

            val obj = @SuppressLint("StaticFieldLeak")
            object : MyAsync(it.icon) {
                @Deprecated("Deprecated in Java")
                override fun onPostExecute(result: Bitmap?) {
                    super.onPostExecute(result)
                    bmp = result
                    val b = Bitmap.createScaledBitmap(result!!, 120, 120, false)

                    markerName = mMap.addMarker(
                        myMarker
                            .position(locate).title(it.name)
                            .icon(BitmapDescriptorFactory.fromBitmap(b!!)))

                }
            }
            obj.execute()




        }
    }
}
open class MyAsync(var urlString:String) : AsyncTask<Void?, Void?, Bitmap?>() {
    override fun doInBackground(vararg params: Void?): Bitmap? {
        return try {
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}


