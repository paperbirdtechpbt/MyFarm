package com.pbt.myfarm.Activity.TaskFunctions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.Activity.TaskFunctions.ViewModel.ViewModelTaskFunctionality
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASKFUNCTION_TASKID


class TaskFunctionActivity : AppCompatActivity() {
    var updateTaskID=""
    var viewmodel: ViewModelTaskFunctionality? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_function)



        if (intent.extras!=null){
            updateTaskID=intent.getStringExtra(CONST_TASKFUNCTION_TASKID)!!

            initViewModel(updateTaskID)


        }
    }

    private fun initViewModel(updateTaskID: String) {
        viewmodel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelTaskFunctionality::class.java)
        viewmodel?.onTaskFunctionList(this,updateTaskID )
        viewmodel?.listTaskFuntions?.observe(this,Observer{list ->
            if (!list.isNullOrEmpty()){
                Toast.makeText(this,"List response success ",Toast.LENGTH_SHORT).show()
            }


        })

    }
}