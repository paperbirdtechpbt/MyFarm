package com.pbt.myfarm.Activity.task_object

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.R
import com.pbt.myfarm.Task
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.AppConstant
import kotlinx.android.synthetic.main.activity_view_task_object.*

class ViewTaskObjectActivity : AppCompatActivity() {
    private var adapter: AdapterViewTaskObjects? = null
    var viewModel: ViewTaskObjectViewModel? = null
    private var updateTaskList: Task? = null
    private var listTaskObject = ArrayList<TaskObject>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task_object)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
    }

    private fun initViewModel() {

        adapter = AdapterViewTaskObjects(this, listTaskObject)
        recyclerViewTaskObject.adapter = adapter

        recyclersViewVisible(recyclerViewTaskObject,false)
        conNoData(false)
        progressBar(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewTaskObjectViewModel::class.java]

        updateTaskList = intent.getParcelableExtra(AppConstant.CONST_TASKFUNCTION_TASKID)

        viewModel?.let { model ->

            model.getObjectsList(updateTaskList!!.id.toString())

            model.taskObjectList.observe(this) { taskObjectList ->

                progressBar(false)
                Log.d("##1234650","List Size ==>> ${taskObjectList.size}")

                if (!taskObjectList.isNullOrEmpty()) {

                    recyclersViewVisible(recyclerViewTaskObject,true)

                    listTaskObject.addAll(taskObjectList)

                    adapter!!.notifyDataSetChanged()

                    adapter?.editItemClick = {
                        model.showDialog("Test",this)
                    }
                    adapter?.deleteItemClick = {
                        model.deleteTaskObject(it.id.toString())
                    }

                } else {
                    progressBar(false)
                    conNoData(true)
                }
            }

            model.deleteHttpResponse.observe(this) {

                var removeIndex = 0;

                adapter?.apply {
                    for ((count, item) in items.withIndex()) {
                        if (model.deleteID.value == item.id.toString()) {
                            removeIndex = count
                        }
                    }
                }

                adapter?.apply {
                    items.removeAt(removeIndex)
                    this.notifyDataSetChanged()

                    if(items.isNullOrEmpty())
                    conNoData(true)
                }


            }
        }
    }

    private fun recyclersViewVisible(recyclerView: RecyclerView, isVisible : Boolean){
        if(isVisible)
            recyclerView.visibility = View.VISIBLE
        else
            recyclerView.visibility = View.GONE
    }

    private fun conNoData( isVisible : Boolean){
        if(isVisible)
            conNoData.visibility = View.VISIBLE
        else
            conNoData.visibility = View.GONE
    }

    private fun progressBar( isVisible : Boolean){
        if(isVisible)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }




}