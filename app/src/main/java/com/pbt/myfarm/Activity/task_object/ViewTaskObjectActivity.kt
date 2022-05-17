package com.pbt.myfarm.Activity.task_object

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

        initViewModel()
    }

    private fun initViewModel() {

        adapter = AdapterViewTaskObjects(this, listTaskObject)
        recyclerViewTaskObject.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewTaskObjectViewModel::class.java]

        updateTaskList = intent.getParcelableExtra(AppConstant.CONST_TASKFUNCTION_TASKID)

        viewModel?.let { model ->

            model.getObjectsList(updateTaskList!!.id.toString())

            model.taskObjectList.observe(this) { taskObjectList ->
                if (!taskObjectList.isNullOrEmpty()) {
                    listTaskObject.addAll(taskObjectList)

                    Log.d("##1234650", " observer List Item  ==>> " + taskObjectList.size)
                    adapter!!.notifyDataSetChanged()

                    adapter?.editItemClick = {
                    }
                    adapter?.deleteItemClick = {
                        model.deleteTaskObject(it.id.toString())
                    }

                } else {
                    Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }

            model.deleteHttpResponse.observe(this) {

                val mArrayList: MutableList<TaskObject> = mutableListOf()

                mArrayList.addAll(model.taskObjectList.value!!)

                Log.d("##1234650","List Size ${mArrayList.size}")

                var removeIndex = 0;

                for ((count, item) in mArrayList.withIndex()) {
                    if (model.deleteID.value == item.id.toString()) {
                        removeIndex = count
                        Log.d("##1234650", "List Size ${item.id}")
                    }
                }

                val removeArray = mArrayList.toMutableList().apply {
                    removeAt(removeIndex)
                }

                adapter.apply {
                    this?.notifyItemChanged(removeIndex)
                }

                adapter!!.notifyDataSetChanged()

//                adapter!!.notifyItemRangeChanged(removeIndex, mArrayList.size);
//                model.taskObjectList.postValue(removeArray)



                Log.d("##1234650","List Size ${removeArray.size}")

//                model.taskObjectList.postValue(removArray)
            }
        }
    }
}