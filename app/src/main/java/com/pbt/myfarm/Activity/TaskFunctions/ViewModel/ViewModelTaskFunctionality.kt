package com.pbt.myfarm.Activity.TaskFunctions.ViewModel

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.Activity.TaskFunctions.ResponseTaskFunctionaliyt
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat
import java.util.*


class ViewModelTaskFunctionality(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<ResponseTaskFunctionaliyt> {
    private val CAMERA_REQUEST = 1888
    private val GELARY_REQUEST = 1088
    private val MY_CAMERA_PERMISSION_CODE = 1001
    val TAG = "ViewModelTaskFunctionality"
    var context: Context? = null
    var listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()

    init {
        listTaskFuntions = MutableLiveData<List<ListTaskFunctions>>()
    }


    fun onTaskFunctionList(context: Context, updateTaskId: String) {

        ApiClient.client.create(ApiInterFace::class.java)
            .taskFunctionList(
                MySharedPreference.getUser(context)?.id.toString(),
                updateTaskId
            ).enqueue(this)

    }




    override fun onResponse(
        call: Call<ResponseTaskFunctionaliyt>,
        response: Response<ResponseTaskFunctionaliyt>
    ) {
        if (response.body()?.error == false) {
            val baseresponse: ResponseTaskFunctionaliyt = Gson().fromJson(
                Gson().toJson(response.body()),
                ResponseTaskFunctionaliyt::class.java
            )
            val list = ArrayList<ListTaskFunctions>()
            if (baseresponse != null) {
                baseresponse.data.forEach { item ->
                    list.add(item)
                }
                if (!list.isNullOrEmpty()) {
                    listTaskFuntions.value = list
                    AppUtils.logDebug(TAG, "resposenselist ==>" + Gson().toJson(list))

                }

            }
        }


    }

    override fun onFailure(call: Call<ResponseTaskFunctionaliyt>, t: Throwable) {
    }

}