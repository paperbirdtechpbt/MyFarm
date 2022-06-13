package com.pbt.myfarm.Activity.Map

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Event.ListOFMarkerPoints
import com.pbt.myfarm.Activity.Event.ResposneMarkerPoints
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.hollowPolygon
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.list
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MapActivityViewModel(val activity: Application) : AndroidViewModel(activity),Callback<ResposneMarkerPoints> {
    var gMap:GoogleMap?=null
    var context:Context?=null
    companion object{
        lateinit var jsonFeature:JSONArray
         var markersdata:List<ListOFMarkerPoints>?=null

    }

    fun setMarkerPoints(mMap: GoogleMap, context: Context) {
        ApiClient.client.create(ApiInterFace::class.java).fieldFarm(MySharedPreference.getUser(context)?.id.toString()).enqueue(this)

    }

    override fun onResponse(
        call: Call<ResposneMarkerPoints>,
        response: Response<ResposneMarkerPoints>
    ) {
        if (!response.body()?.data.isNullOrEmpty()){
            markersdata=response.body()?.data
            setMarker(markersdata)

        }

    }

    fun setMarker(markersdata: List<ListOFMarkerPoints>?) {
        (context as Activity).runOnUiThread(Runnable {
            markersdata?.forEach {
                val locate = LatLng(it.lat.toDouble(), it.lng.toDouble())
                gMap?.addMarker(MarkerOptions().position(locate).title(it.name))


            }
        })

    }

    override fun onFailure(call: Call<ResposneMarkerPoints>, t: Throwable) {
    }

    fun getPolygonPointFromApi(context: Context) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://farm.myfarmdata.io/jsonFiles/Gabon-Protected-Areas.json")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                AppUtils.logError("##TAG", "onfailyre get jsonflie")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val str_response = response.body?.string()
                val json_contact = JSONObject(str_response)
//                val jsonFeature = json_contact.getJSONArray("features")
                 jsonFeature = json_contact.getJSONArray("features")

                setAreas(jsonFeature,context)

            }
        })
    }

     fun setAreas(jsonFeature: JSONArray, context: Context) {
        for (i in 0 until jsonFeature.length()) {

            val cordinate = jsonFeature.getJSONObject(i).getJSONObject("geometry")
                .getJSONArray("coordinates")

            for (a in 0 until cordinate.length()) {

                val itema = cordinate.get(a) as JSONArray

                for (z in 0 until itema.length()) {
//
                    val itemz = itema.get(z) as JSONArray
                    list = ArrayList()
                    for (x in 0 until itemz.length()) {

                        val itemx = itemz.get(x) as JSONArray
                        list!!.add(
                            LatLng((itemx.get(1) as Double), itemx.get(0) as Double)
                        )
                    }
                    (context as Activity).runOnUiThread(Runnable {
                        if (!list.isNullOrEmpty()) {
//                                    val hollowPolygon: Polygon = mMap.addPolygon(
//                                        PolygonOptions()
//                                            .add(
//                                                LatLng(0.0, 0.0),
//                                                LatLng(0.0, 5.0),
//                                                LatLng(3.0, 5.0),
//                                                LatLng(3.0, 0.0),
//                                                LatLng(0.0, 0.0)
//                                            )
//                                            .addHole(hole)
//                                            .fillColor(Color.BLUE)
//                                    )
                            hollowPolygon =  gMap?.addPolygon(
                                PolygonOptions()
                                    .addAll(list!!)
                                    .fillColor(Color.GREEN)
                                    .strokeWidth(2f)
//                                            .fillColor(R.color.green)
                            )
                        }
                    })
                    AppUtils.logDebug("##map", "jso0" + Gson().toJson(list))

                }
            }
        }

    }

}
