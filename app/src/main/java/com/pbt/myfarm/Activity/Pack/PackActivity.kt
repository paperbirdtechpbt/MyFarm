package com.pbt.myfarm.Activity.Pack

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigTypeActivity
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_LIST_SIZE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_view_task.*

class PackActivity : AppCompatActivity() {
    private var adapter: AdapterViewPack? = null
    var db: DbHelper?=null
    var listsize=0
    var tasklist:ArrayList<ViewPackModelClass>?=null
    companion object{
        var TAG="PackActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        getDataFromDatabase()

        recyclerview_viewtask.layoutManager = LinearLayoutManager(this)


        btn_create_task.setOnClickListener {
            val intent=Intent(this, CreatePackActivity::class.java)
            intent.putExtra(CONST_LIST_SIZE,listsize)
          startActivity(intent)
        }
    }
    @SuppressLint("Range")
    fun getDataFromDatabase() {

        db = DbHelper(this, null)
        tasklist = db!!.readPackData()

 listsize= tasklist?.size!!

        AppUtils.logDebug(TAG, tasklist?.size.toString())

        adapter = AdapterViewPack(this, tasklist!!) { position, taskname,checkAction ,list->
            if (checkAction){
                showAlertDailog(taskname,position)
            }
            else{
                val intent=Intent(this, CreatePackActivity::class.java)
                intent.putExtra(CONST_VIEWMODELCLASS_LIST,list)
                AppUtils.logDebug(TAG,list.toString())

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

                    db?.deletePack(taskname)
                    tasklist?.removeAt(position)
                    adapter?.notifyItemRemoved(position)
                    adapter?.notifyDataSetChanged()
                    checkListEmptyOrNot(tasklist!!)
                    Toast.makeText(this,"Deleted $taskname", Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    private fun checkListEmptyOrNot(tasklist: ArrayList<ViewPackModelClass>) {
        if (tasklist.isEmpty()){
            layout_nodatavailable.visibility= View.VISIBLE
        }
        else{
            layout_nodatavailable.visibility= View.GONE
        }
    }


    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
        getDataFromDatabase()
    }}
