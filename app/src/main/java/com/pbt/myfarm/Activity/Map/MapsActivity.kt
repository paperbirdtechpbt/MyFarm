package com.pbt.myfarm.Activity.Map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.pbt.myfarm.Activity.Map.MapActivityViewModel.Companion.jsonFeature
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var viewModel: MapActivityViewModel? = null

    companion object {
        var list: ArrayList<LatLng>? = null
        var hollowPolygon: Polygon? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MapActivityViewModel::class.java)
        viewModel?.context=this

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        setCheckBoxListner()
    }

    private fun setCheckBoxListner() {
        isShowAreaChecked.setOnCheckedChangeListener { buttonView, isChecked ->
         if (isChecked){
             GlobalScope.launch {
                 viewModel?.setAreas(jsonFeature,this@MapsActivity)
                 viewModel?.setMarker(MapActivityViewModel.markersdata)
             }
         }
            else{
                mMap.clear()
             GlobalScope.launch {
                 viewModel?.setMarker(MapActivityViewModel.markersdata)
             }
            }

        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewModel?.gMap = googleMap
        viewModel?.getPolygonPointFromApi(this)

        viewModel?.setMarkerPoints(mMap, this)

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


}