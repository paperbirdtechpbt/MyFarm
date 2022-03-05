package com.pbt.myfarm.Activity.PackConfigList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigViewModel
import com.pbt.myfarm.Adapter.SelectConfigType.AdapterSelectconfigType
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_CONFIG_LIST
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import kotlinx.android.synthetic.main.activity_pack_config_list.*
import kotlinx.android.synthetic.main.activity_select_config_type.*
import kotlinx.android.synthetic.main.activity_select_config_type.recylcerview_selectConfigType

class PackConfigListActivity : AppCompatActivity() {
    private var adapter: AdapterPackSelectconfigType? = null
    private var parentlayout: ConstraintLayout? = null
    var viewModell: SelectPackConfigViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack_config_list)
        initViewModel()
    }

    private fun initViewModel() {
        viewModell = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(SelectPackConfigViewModel::class.java)


        viewModell?.progressbar=progressbar_packconfiglist
        viewModell?.onConfigTypeRequest(this)
        viewModell?.configlist?.observe(this, Observer { configtype ->
            select_packconfiglist.layoutManager= LinearLayoutManager(this)

            adapter = AdapterPackSelectconfigType(this, configtype!!) { position, taskname ,list->


                val intent = Intent(this, CreatePackActivity::class.java)
                intent.putExtra(CONST_VIEWMODELCLASS_LIST,list)
                startActivity(intent)
                finish()

            }
            select_packconfiglist.adapter = adapter

        })
    }
}