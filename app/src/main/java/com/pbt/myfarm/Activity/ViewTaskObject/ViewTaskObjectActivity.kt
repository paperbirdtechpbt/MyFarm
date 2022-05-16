package com.pbt.myfarm.Activity.ViewTaskObject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.Activity.CreateTask.CreateTaskAdapter
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.R
import com.pbt.myfarm.Task
import com.pbt.myfarm.TasklistDataModel
import com.pbt.myfarm.Util.AppConstant
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_view_task_object.*
import java.util.ArrayList

class ViewTaskObjectActivity : AppCompatActivity() {
    private var adapter: AdapterViewTaskObjects? = null
    var db: DbHelper? = null
    var viewtaskObjectList: ArrayList<TasklistDataModel>? = null
    var viewmodel: ViewTaskObjectViewModel? = null
     var  updateTasklist:Task?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task_object)

        updateTasklist= intent.getParcelableExtra<Task>(AppConstant.CONST_TASKFUNCTION_TASKID)

        initViewModel()
    }

    private fun initViewModel() {
        viewmodel= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewTaskObjectViewModel::class.java)
        viewmodel!!.getObjectsList(this,updateTasklist)
        viewmodel?.mylist?.observe(this,androidx.lifecycle.Observer{list ->
          if (!list.isNullOrEmpty())  {
              adapter = AdapterViewTaskObjects(this, list)
              recyclerview_ViewtaskObject.adapter = adapter
          }
            else{
              Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        })
    }
}