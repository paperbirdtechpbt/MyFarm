package com.pbt.myfarm.Activity.Map

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Event.ListOFMarkerPoints
import com.pbt.myfarm.Activity.Event.ResponsefieldClasses
import com.pbt.myfarm.Activity.Event.ResposneMarkerPoints
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.hollowPolygon
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.list
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.markerName
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.myMarker
import com.pbt.myfarm.Activity.Map.MapsActivity.Companion.selectedItemAreas
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
import java.net.URL


class MapActivityViewModel(val activity: Application) : AndroidViewModel(activity),
    Callback<ResposneMarkerPoints> {
    var gMap: GoogleMap? = null
    var context: Context? = null
    var imgview: ImageView? = null
    var mCheckBox: ProgressDialog? = null
    var isLoopFinished = MutableLiveData<Boolean>()
    private val markerLiveList = MutableLiveData<List<ListOFMarkerPoints>>()

    val listMaker: LiveData<List<ListOFMarkerPoints>>
        get() = markerLiveList

    companion object {
        var jsonFeature: JSONArray? = null
        var markersdata: List<ListOFMarkerPoints>? = null

    }

    init {
        isLoopFinished = MutableLiveData<Boolean>()
    }

    val fieldClaseslist = MutableLiveData<List<ResponsefieldClasses.ListofFieldClasses>>()

    fun setMarkerPoints(mMap: GoogleMap, context: Context, classid: String) {
        AppUtils.logError("TAG", "chooseItem" + classid)

        ApiClient.client.create(ApiInterFace::class.java).fieldFarm(
            MySharedPreference.getUser(context)?.id.toString(),
            selectedItemAreas.toString()
        ).enqueue(this)

    }

    override fun onResponse(
        call: Call<ResposneMarkerPoints>,
        response: Response<ResposneMarkerPoints>
    ) {
        if (!response.body()?.data.isNullOrEmpty()) {
            markersdata = ArrayList()
            markersdata = response.body()?.data
            markerLiveList.postValue(response.body()?.data)
        }
    }

//    fun setMarker(markersdata: List<ListOFMarkerPoints>?, mMap: GoogleMap) {
//
//        markersdata?.forEach {
//            val locate = LatLng(it.lat.toDouble(), it.lng.toDouble())
//
//
//            var bmp: Bitmap? = null
//            val url = URL(it.icon)
//
//            try {
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//
//            markerName = gMap?.addMarker(
//                myMarker
//                    .position(locate).title(it.name)
//                    .icon(BitmapDescriptorFactory.fromBitmap(bmp))
//            )
//
//        }
//
//
//    }


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

                setAreas(jsonFeature!!, context)

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
                    list.clear()

                    for (x in 0 until itemz.length()) {

                        val itemx = itemz.get(x) as JSONArray
                        list.add(
                            LatLng((itemx.get(1) as Double), itemx.get(0) as Double)
                        )
                    }

                    (context as Activity).runOnUiThread(Runnable {
                        if (!list.isNullOrEmpty()) {
                            val mlist = ArrayList<LatLng>()
                            mlist.addAll(list)
//
                            hollowPolygon = gMap?.addPolygon(
                                PolygonOptions()
                                    .addAll(mlist)
                                    .fillColor(Color.GREEN)
                                    .strokeWidth(2f)
                            )
                        }
                    })


                    AppUtils.logDebug("##map", "jso0" + Gson().toJson(list))

                }
            }
        }
        isLoopFinished.postValue(true)
        mCheckBox?.dismiss()

    }

    fun callFieldClasesListApi(context: Context, userid: String) {
        val apiInterFace = ApiClient.client.create(ApiInterFace::class.java)
        val call = apiInterFace.fieldClasses(userid)
        call.enqueue(object : Callback<ResponsefieldClasses> {
            override fun onResponse(
                call: Call<ResponsefieldClasses>,
                response: Response<ResponsefieldClasses>
            ) {
                if (response.isSuccessful) {
                    if (!response.body()?.data.isNullOrEmpty()) {
                        val baseResponse: ResponsefieldClasses = Gson().fromJson(
                            Gson().toJson(response.body()),
                            ResponsefieldClasses::class.java
                        )

                        fieldClaseslist.postValue(baseResponse.data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponsefieldClasses>, t: Throwable) {
            }

        })
    }

}






