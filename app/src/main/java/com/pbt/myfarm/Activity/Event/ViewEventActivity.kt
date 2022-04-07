package com.pbt.myfarm.Activity.Event

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.Pack.AdapterViewPack
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.EventViewModel
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.R
import com.pbt.myfarm.ResponseEventList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CREATEEVENT
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EDITEVENT_ID
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_view_event.*
import kotlinx.android.synthetic.main.activity_view_task.*
import kotlinx.android.synthetic.main.activity_view_task.recyclerview_viewtask
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ViewEventActivity : AppCompatActivity(),retrofit2.Callback<ResponseEventList> {
    var viewModel: EventViewModel?=null
    var adapter:AdapterEventList?=null

    val TAG="ViewEventActivity"
    override fun onResume() {
        super.onResume()
        initViewModel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)

        initViewModel()
        btn_create_event.setOnClickListener{
            val intent=Intent(this,EditEventActivity::class.java)
            intent.putExtra(CONST_CREATEEVENT,true)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(EventViewModel::class.java)
        viewModel?.progressbar=progressViewEvent
        viewModel?.onEventList(this)
        viewModel?.event?.observe(this, Observer { list ->
            if (!list.isNullOrEmpty()){
                eventlistSize.setText("Total Events - ${list.size} ")
            }
            adapter = AdapterEventList(this, list){eventid, position,checkaction ->

                if (checkaction){
                    //true==delete
                    showAlertDailog(eventid, position)

                }
                else{

                    val intent=Intent(this,EditEventActivity::class.java)

                    intent.putExtra(CONST_EDITEVENT_ID,eventid)
                    intent.putExtra(CONST_CREATEEVENT,false)

                    startActivity(intent)

                }



            }
            recyclerview_viewEvent.layoutManager = LinearLayoutManager(this)
            recyclerview_viewEvent.adapter = adapter

        })
    }

    private fun showAlertDailog(eventid: String, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete ") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    ApiClient.client.create(ApiInterFace::class.java).deleteEvent(eventid).enqueue(this)

                    Toast.makeText(this, "Deleted ", Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    override fun onResponse(call: Call<ResponseEventList>, response: Response<ResponseEventList>) {
        if (response.body()?.error==false){
            progressViewEvent
            Toast.makeText(this, "Delted Succesfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,ViewEventActivity::class.java))
            finish()
        }
        else
        {
            progressViewEvent
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<ResponseEventList>, t: Throwable) {
        try {
            progressViewEvent
            AppUtils.logError(TAG,t.message.toString())
        }
        catch (e:Exception){
            progressViewEvent
            AppUtils.logError(TAG,e.message.toString())

        }
    }
}