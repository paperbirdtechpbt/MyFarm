package com.pbt.myfarm.Activity.Graph

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.Activity.TaskFunctions.ResponseTaskFunctionaliyt
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ViewmodelGraph(var activity: Application):AndroidViewModel(activity) ,retrofit2.Callback<ResponseTaskFunctionaliyt> {
    var configlist = MutableLiveData<List<ListTaskFunctions>>()
    var graphlist=ArrayList<ListTaskFunctions>()



    fun onConfigFieldList(context: Context, packid: String) {

        if (packid!=null){
            try{
            ApiClient.client.create(ApiInterFace::class.java)
                .getGraphList(
                    MySharedPreference.getUser(context)?.id.toString(),
                    packid.toString()).enqueue(this)}
            catch (e:Exception){
                AppUtils.logDebug("##TAG",e.message.toString())

            }
        }else{
            ApiClient.client.create(ApiInterFace::class.java)
                .getGraphList(
                    MySharedPreference.getUser(context)?.id.toString(),
                    "5").enqueue(this)
        }





    }

    override fun onResponse(
        call: Call<ResponseTaskFunctionaliyt>,
        response: Response<ResponseTaskFunctionaliyt>
    ) {
        if(response.body()?.error==false){

            var baseresponse:ResponseTaskFunctionaliyt= Gson().fromJson(Gson().toJson(response.body()),ResponseTaskFunctionaliyt::class.java)
        baseresponse.data.forEach{
            graphlist.add(it)
        }
            configlist.value=graphlist
        }

    }

    override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {
        try{
            AppUtils.logDebug("##TAG",t.message.toString())

        }
        catch (e:Exception){
            AppUtils.logDebug("##TAG",e.message.toString())

        }
    }

}
