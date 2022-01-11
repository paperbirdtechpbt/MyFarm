package com.pbt.myfarm.Activity.ViewTask

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity

import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigTypeActivity
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.CreatetaskViewModel
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ModelClass.ViewTaskModelClass

import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST

import kotlinx.android.synthetic.main.activity_view_task.*

class ViewTaskActivity : AppCompatActivity() {
    private var adapter: AdapterViewTask? = null
    var db:DbHelper?=null
    var tasklist:ArrayList<ViewTaskModelClass>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        actionBar?.setDisplayHomeAsUpEnabled(true)


        getDataFromDatabase()

        recyclerview_viewtask.layoutManager = LinearLayoutManager(this)


        btn_create_task.setOnClickListener {
            startActivity(Intent(this, SelectConfigTypeActivity::class.java))
        }
    }

    @SuppressLint("Range")
    fun getDataFromDatabase() {

         db = DbHelper(this, null)
        tasklist = db!!.readData()

        adapter = AdapterViewTask(this, tasklist!!) { position, taskname,checkAction ,list->
            if (checkAction){
                showAlertDailog(taskname,position)
            }
            else{
                val intent=Intent(this,CreateTaskActivity::class.java)
                intent.putExtra(CONST_VIEWMODELCLASS_LIST,list)
             startActivity(intent)


            }

        }


        recyclerview_viewtask.adapter = adapter
        checkListEmptyOrNot(tasklist!!)



    }

    private fun showAlertDailog(taskname: String, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete $taskname") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->

                    db?.deleteTask(taskname)
                    tasklist?.removeAt(position)
                    adapter?.notifyItemRemoved(position)
                    adapter?.notifyDataSetChanged()
                    checkListEmptyOrNot(tasklist!!)
                    Toast.makeText(this,"Deleted $taskname",Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    private fun checkListEmptyOrNot(tasklist: ArrayList<ViewTaskModelClass>) {
        if (tasklist.isEmpty()){
            layout_nodatavailable.visibility=View.VISIBLE
        }
        else{
            layout_nodatavailable.visibility=View.GONE
        }
    }


    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
        getDataFromDatabase()
    }
}