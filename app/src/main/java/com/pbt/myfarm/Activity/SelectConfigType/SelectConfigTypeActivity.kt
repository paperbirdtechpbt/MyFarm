package com.pbt.myfarm.Activity.SelectConfigType

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Adapter.SelectConfigType.AdapterSelectconfigType
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.ModelClass.ConfigTypeModel
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CONFIGTYPE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CONFIGTYPE_TYPE_ID
import kotlinx.android.synthetic.main.activity_select_config_type.*
import kotlinx.android.synthetic.main.activity_view_task.*

class SelectConfigTypeActivity : AppCompatActivity() {
    var configtypelist=ArrayList<ConfigTypeModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_config_type)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        recylcerview_selectConfigType.layoutManager=LinearLayoutManager(this)
        setdata()
        setadapter()
    }

    private fun setdata() {
        configtypelist.add(ConfigTypeModel("TRANSPORTATION"))
        configtypelist.add(ConfigTypeModel("VTEST"))
        configtypelist.add(ConfigTypeModel("TEST3"))
        configtypelist.add(ConfigTypeModel("FERMENTATION"))
        configtypelist.add(ConfigTypeModel("NETTOYAGE"))
        configtypelist.add(ConfigTypeModel("TestTask"))
        configtypelist.add(ConfigTypeModel("DEMOFERM"))

    }

    private fun setadapter() {
        val adapter = AdapterSelectconfigType(this,configtypelist){position,name ->
            val intent= Intent(this,CreateTaskActivity::class.java)
            intent.putExtra(CONST_CONFIGTYPE_NAME,name)
            intent.putExtra(CONST_CONFIGTYPE_TYPE_ID,position)
            startActivity(intent)
            finish()
        }
        recylcerview_selectConfigType.adapter = adapter
    }
}