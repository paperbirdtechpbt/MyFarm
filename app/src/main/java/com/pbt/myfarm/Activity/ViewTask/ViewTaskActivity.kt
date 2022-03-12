package com.pbt.myfarm.Activity.ViewTask

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigTypeActivity
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.TasklistDataModel
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_UPDATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_UPDATE_BOOLEAN
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_UPDATE_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.ViewTaskViewModel
import com.pbt.myfarm.databinding.ActivityViewTaskBinding
import kotlinx.android.synthetic.main.activity_pack.*
import kotlinx.android.synthetic.main.activity_view_task.*
import kotlinx.android.synthetic.main.activity_view_task.btn_create_task
import kotlinx.android.synthetic.main.activity_view_task.ed_search
import kotlinx.android.synthetic.main.activity_view_task.layout_nodatavailable
import kotlinx.android.synthetic.main.activity_view_task.recyclerview_viewtask
import kotlinx.android.synthetic.main.activity_view_task.tasklistSize
import retrofit2.Call
import retrofit2.Response


class ViewTaskActivity : AppCompatActivity(), retrofit2.Callback<testresponse> {
    private var adapter: AdapterViewTask? = null
    var db: DbHelper? = null
    var tasklist: ArrayList<TasklistDataModel>? = null


    var viewModell: ViewTaskViewModel? = null
    var binding: ActivityViewTaskBinding? = null

    companion object {
        val TAG = "ViewTaskActivity"
        var mytasklist: TasklistDataModel? = null
        var updateTaskBoolen=false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_task)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()



//binding?.edSearch?.setQueryHint("Search Task")
//        binding?.edSearch?.setOnClickListener(View.OnClickListener {
//            binding?.edSearch?.setIconified(false) })

        btn_create_task.setOnClickListener {
            startActivity(Intent(this, SelectConfigTypeActivity::class.java))

        }
    }



    private fun initViewModel() {
        viewModell = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewTaskViewModel::class.java)
        binding?.viewModel = viewModell


        viewModell?.progress = progressbar_eventlist
        viewModell?.onEventListRequest(this)
        viewModell?.eventlist?.observe(this, Observer { eventlistt ->

            recyclerview_viewtask?.layoutManager = LinearLayoutManager(this)
            val animator: ItemAnimator = recyclerview_viewtask.getItemAnimator()!!
            if (animator is SimpleItemAnimator) {
                (animator as SimpleItemAnimator).supportsChangeAnimations = false
            }

            tasklistSize.setText("Total Tasks-" + eventlistt.size)
            adapter = AdapterViewTask(this, eventlistt!!) { position, taskname, checkAction, list ->


                mytasklist = list
                if (checkAction) {

                    showAlertDailog(taskname, position, mytasklist!!)
                }
                else {
                    updateTaskBoolen=true
                    val intent=Intent(this,CreateTaskActivity::class.java)
                    intent.putExtra(CONST_TASK_UPDATE,mytasklist?.id)
                    intent.putExtra(CONST_TASK_UPDATE_BOOLEAN,"1")
                    intent.putExtra(CONST_TASK_UPDATE_LIST, mytasklist)
                    startActivity(intent)
                    finish()
//
                }


            }
            recyclerview_viewtask.adapter = adapter
            if (eventlistt.isNullOrEmpty()) {
                layout_nodatavailable.visibility = View.VISIBLE
            } else {
                layout_nodatavailable.visibility = View.GONE
            }
        })

    }

//    @SuppressLint("Range")
//    fun getDataFromDatabase() {
//
//        db = DbHelper(this, null)
//        tasklist = db!!.readData()
//
//        adapter = AdapterViewTask(this, tasklist!!) { position, taskname, checkAction, list ->
//            if (checkAction) {
//                showAlertDailog(taskname, position)
//            } else {
//                val intent = Intent(this, CreateTaskActivity::class.java)
//                intent.putExtra(CONST_VIEWMODELCLASS_LIST, list)
//                startActivity(intent)
//
//
//            }
//
//        }
//
//
//        recyclerview_viewtask.adapter = adapter
//        checkListEmptyOrNot(tasklist!!)
//
//
//    }

    private fun showAlertDailog(taskname: String, position: Int, mytasklist: TasklistDataModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete $taskname") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
//                    ApiClient.client.create(ApiInterFace::class.java).deleteTask(mytasklist.id)
//                        .enqueue(this)
                    val db=DbHelper(this,null)
                    db.deletenewTask("2","5")

                    Toast.makeText(this, "Deleted $taskname", Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    private fun checkListEmptyOrNot(tasklist: ArrayList<ViewTaskModelClass>) {
        if (tasklist.isEmpty()) {
            layout_nodatavailable.visibility = View.VISIBLE
        } else {
            layout_nodatavailable.visibility = View.GONE
        }
    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            Toast.makeText(this, "Task Deleted SuccessFullly", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ViewTaskActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logError(TAG, t.localizedMessage)
        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
    }


}