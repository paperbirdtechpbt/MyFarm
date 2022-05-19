package com.pbt.myfarm.Activity.task_object

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.TaskFunctions.ListTaskFunctions
import com.pbt.myfarm.Activity.TaskFunctions.TaskFunctionActivity
import com.pbt.myfarm.HttpResponse.HttpResponse
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
    private var listTaskFunctionList = ArrayList<ListTaskFunctions>()
    private var listTaskFunctionsStr = ArrayList<String>()
    private var selectedTaskFunctionsStr = "Select Task Function"
    private var selectedTaskFUnctionID = ""
    private var checkStatus: HttpResponse? = null
    private var dialog: Dialog? = null
    val TAG = "ViewTaskObjectActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task_object)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
    }

    private fun initViewModel() {
        listTaskObject.clear()

        recyclersViewVisible(recyclerViewTaskObject, true)


        conNoData(false)
        progressBar(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewTaskObjectViewModel::class.java]

        updateTaskList = intent.getParcelableExtra(AppConstant.CONST_TASKFUNCTION_TASKID)
        if (updateTaskList == null) {
            updateTaskList = CreateTaskActivity.taskcompainionobject
        }

        viewModel?.let { model ->

            model.getObjectsList(updateTaskList!!.id.toString())
            model.checkStatus(updateTaskList!!.id.toString())

            model.checkStatusobject.observe(this) {
                if (it != null) {
                    if (it.error == false) {
                        checkStatus = it
                    } else {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            model.taskObjectList.observe(this) { taskObjectList ->

                progressBar(false)
                conNoData(false)

                if (!taskObjectList.isNullOrEmpty()) {

//                    recyclersViewVisible(recyclerViewTaskObject, true)

                    listTaskObject.addAll(taskObjectList)

                    adapter = AdapterViewTaskObjects(this, listTaskObject)
                    recyclerViewTaskObject.adapter = adapter

                    adapter?.editItemClick = { taskObj ->
                        val intent = Intent(this, TaskFunctionActivity::class.java)
                        intent.putExtra(AppConstant.CONST_TASKFUNCTION_TASKID, updateTaskList)
                        intent.putExtra(AppConstant.CONST_TASKFUNCTION_OBJECT, taskObj)
                        intent.putExtra(AppConstant.CONST_TASKFUNCTION_OBJECTI_ISUPDATE, true)
                        startActivity(intent)
                        finish()

                    }
                    adapter?.deleteItemClick = {
                        if (checkStatus?.status == "completed" || checkStatus?.status == null) {

                            if (checkStatus?.status == null || checkStatus?.status == "null")
                                Toast.makeText(this, "Task Not Started ", Toast.LENGTH_SHORT).show()

                            if (checkStatus?.status == "completed")
                                Toast.makeText(this, "Task Is Ended ", Toast.LENGTH_SHORT).show()

                        }
                        else {
                            model.deleteTaskObject(it.id.toString(),this)
                        }
                    }
                    adapter!!.notifyDataSetChanged()


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

//
                    if (items.isNullOrEmpty())
                        conNoData(true)
                }
            }

            model.taskTaskFunctionList.observe(this) {
                Log.d("##1234650", " getTaskFunctionList observer List   ==> ")

                if (dialog == null) {
                    showDialog(this, it)
                }
            }
        }
    }

    private fun recyclersViewVisible(recyclerView: RecyclerView, isVisible: Boolean) {
        if (isVisible)
            recyclerView.visibility = View.VISIBLE
        else
            recyclerView.visibility = View.GONE
    }

    private fun conNoData(isVisible: Boolean) {
        if (isVisible)
            conNoData.visibility = View.VISIBLE
        else
            conNoData.visibility = View.GONE
    }

    private fun progressBar(isVisible: Boolean) {
        if (isVisible)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private fun showDialog(activity: Activity, list: List<ListTaskFunctions>) {

        list.let {

            selectedTaskFunctionsStr = "Select Task Function"
            listTaskFunctionList.clear()
            listTaskFunctionsStr.clear()

            listTaskFunctionList.addAll(it)

            listTaskFunctionsStr.add(selectedTaskFunctionsStr)

            for ((count, item) in it.withIndex()) {
                item.name1?.let { name ->
                    listTaskFunctionsStr.add(name)
                }
            }

            val taskFunctionsAdapter =
                ArrayAdapter(this, R.layout.item_spinner, listTaskFunctionsStr)
            val taskFunctionsFieldAdapter =
                ArrayAdapter(this, R.layout.item_spinner, listTaskFunctionsStr)

            dialog = Dialog(activity)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.custom_layout_task_object)

            val spinnerTaskFunction = dialog?.findViewById(R.id.spinnerTaskFunction) as Spinner
            val spinnerTaskFunctionField = dialog?.findViewById(R.id.spinnerTaskField) as Spinner
            val btnCancel = dialog?.findViewById(R.id.btnCancel) as TextView
            val txtLableAddPerson = dialog?.findViewById(R.id.txtLableAddPerson) as TextView

            spinnerTaskFunction.adapter = taskFunctionsAdapter
            spinnerTaskFunctionField.adapter = taskFunctionsFieldAdapter

            spinnerTaskFunction.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        selectedTaskFunctionsStr = listTaskFunctionsStr[position]

                        Log.d(
                            "##1234650120",
                            "Selected TaskFunctionID ==> ${selectedTaskFunctionsStr}"
                        )
                        Log.d(
                            "##1234650120",
                            "Selected TaskFunctionID ==> ${listTaskFunctionList.size}"
                        )

                        for ((count, item) in listTaskFunctionList.withIndex()) {
                            if (item.name1 == selectedTaskFunctionsStr) {
                                selectedTaskFUnctionID = item.id.toString()
                                viewModel?.selectedTaskFUnctionID?.postValue(item.id)
                                break
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            spinnerTaskFunctionField.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
//                        (view as TextView).setTextColor(Color.BLACK)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }

            btnCancel.setOnClickListener {
                dialog?.dismiss()
                dialog = null

            }

            dialog?.show()

        }


    }

}