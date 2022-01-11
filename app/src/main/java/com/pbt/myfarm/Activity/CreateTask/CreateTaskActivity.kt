package com.pbt.myfarm.Activity.CreateTask


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.CreatetaskViewModel
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CONFIGTYPE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CONFIGTYPE_TYPE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ActivityCreateTaskBinding
import kotlinx.android.synthetic.main.activity_create_task.*
import java.text.SimpleDateFormat

import java.util.*
import java.util.Locale


class CreateTaskActivity : AppCompatActivity(), View.OnClickListener {
    val myCalendar: Calendar = Calendar.getInstance()
    var viewmodel: CreatetaskViewModel? = null
    var binding: ActivityCreateTaskBinding? = null
    private val TAG="CreateTaskActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_task)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_task)
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CreatetaskViewModel::class.java)
        binding?.viewModel = viewmodel
        setSpinner()

        val viewtask = intent.getParcelableExtra<ViewTaskModelClass>(CONST_VIEWMODELCLASS_LIST)
        btn_update_task.setOnClickListener{
            viewmodel?.update()


        }
        btn_create_task.setOnClickListener {
            viewmodel?.login(it)
            finish()
        }
        if (viewtask!=null){
            AppUtils.logDebug(TAG,viewtask.toString())
            btn_create_task.visibility=View.GONE
            btn_update_task.visibility=View.VISIBLE
            lablel_createnewtask.setText("Update A Task")
//            val viewtask = intent.getParcelableExtra<ViewTaskModelClass>(CONST_VIEWMODELCLASS_LIST)
          setdata(viewtask)


        }

        else {


            val configTypeName: String? = intent.getStringExtra(CONST_CONFIGTYPE_NAME)
            val num: Int = intent.getIntExtra(CONST_CONFIGTYPE_TYPE_ID, -1)

            viewmodel?.confiType?.set(configTypeName)
            setPrefix(num)
        }
        ed_expectedDate.setOnClickListener(this)
        ed_endDate.setOnClickListener(this)
        ed_startDate.setOnClickListener(this)
        ed_expectedEndDate.setOnClickListener(this)
    }

    private fun setdata(viewtask: ViewTaskModelClass?) {

        viewmodel?.namePrefix?.set(viewtask?.ENTRYNAME)
        viewmodel?.confiType?.set(viewtask?.ENTRYTYPE)
        viewmodel?.desciption?.set(viewtask?.ENTRYDETAIL)
        viewmodel?.expectedStartDate?.set(viewtask?.ExpectedStartDate)
        viewmodel?.expectedEndDate?.set(viewtask?.ExpectedEndDate)
        viewmodel?.startDate?.set(viewtask?.StartDate)
        viewmodel?.EndDate?.set(viewtask?.EndDate)

    }

    private fun updateLabel(view: View?) {
        val myFormat = "dd/MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        when(view?.id){
            R.id.ed_expectedDate -> {
                ed_expectedDate.setText(dateFormat.format(myCalendar.time))
            }
            R.id.ed_endDate -> {
                ed_endDate.setText(dateFormat.format(myCalendar.time))

            }
            R.id.ed_startDate -> {
                ed_startDate.setText(dateFormat.format(myCalendar.time))
            }
            R.id.ed_expectedEndDate -> {
                ed_expectedEndDate.setText(dateFormat.format(myCalendar.time))
            }
        }

    }


    private fun setPrefix(num: Int) {
        when (num) {
            0 ->   viewmodel?.namePrefix?.set("TRP")
            1 ->viewmodel?.namePrefix?.set("VTST")
            2 -> viewmodel?.namePrefix?.set("TST3")
            3 -> viewmodel?.namePrefix?.set("FRMTN")
            4 -> viewmodel?.namePrefix?.set("NET")
            5 -> viewmodel?.namePrefix?.set("TSK")
            6 -> viewmodel?.namePrefix?.set("DEFRM")
        }
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            this, R.array.array_communitygroup, android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_Grip.adapter = adapter
        }

    }

    override fun onClick(view: View?) {
        val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel(view)
        }

        DatePickerDialog(
            this, date, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()


    }


}