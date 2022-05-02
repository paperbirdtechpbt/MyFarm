package com.pbt.myfarm.Activity.SelectConfigType

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.Adapter.SelectConfigType.AdapterSelectconfigType
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import kotlinx.android.synthetic.main.activity_select_config_type.*


class SelectConfigTypeActivity : AppCompatActivity() {
    private var adapter: AdapterSelectconfigType? = null
    private var parentlayout: ConstraintLayout? = null
    var viewModell: SelectConfigViewModel? = null


    companion object {
        val TAG = "SelectConfigTypeActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_config_type)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        parentlayout = findViewById(R.id.parentLayout)

        recylcerview_selectConfigType.layoutManager=LinearLayoutManager(this)
        initViewModel()

    }
    private fun initViewModel() {
        viewModell = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(SelectConfigViewModel::class.java)


viewModell?.progressbar=configprogress
        viewModell?.onConfigTypeRequest(this)
        viewModell?.configlist?.observe(this, Observer { configtype ->
            if(!configtype.isNullOrEmpty()){
                configprogress.visibility=View.GONE
            }

            recylcerview_selectConfigType?.layoutManager = LinearLayoutManager(this)

            adapter = AdapterSelectconfigType(this, configtype!!) { position, taskname ,list->
                updateTaskBoolen = false

                val intent = Intent(this, CreateTaskActivity::class.java)
                    intent.putExtra(CONST_VIEWMODELCLASS_LIST,list)
                    startActivity(intent)
                finish()

            }
            recylcerview_selectConfigType.adapter = adapter

        })
    }


}