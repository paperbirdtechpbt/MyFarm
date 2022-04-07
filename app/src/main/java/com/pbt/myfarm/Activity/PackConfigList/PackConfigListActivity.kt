package com.pbt.myfarm.Activity.PackConfigList

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.SelectConfigType.SelectConfigViewModel
import com.pbt.myfarm.Adapter.SelectConfigType.AdapterSelectconfigType
import com.pbt.myfarm.DataBase.DbHelper
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
    var db: DbHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack_config_list)

//        if(checkInternetConnection()){
            initViewModel()

//        }
//        else {
//            db = DbHelper(this, null)
////          val configlist = db!!.readPackConfiglistData()
//
//            val   configlist= db?.getAllPackConfig()
//            select_packconfiglist.layoutManager= LinearLayoutManager(this)
//
//            adapter = AdapterPackSelectconfigType(this, configlist!!) { position, taskname ,list->
//
//                val intent = Intent(this, CreatePackActivity::class.java)
//                intent.putExtra(CONST_VIEWMODELCLASS_LIST,list)
//                startActivity(intent)
//                finish()
//
//            }
//            select_packconfiglist.adapter = adapter
//
//        }
    }

    private fun checkInternetConnection(): Boolean {

        val ConnectionManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun initViewModel() {
        viewModell = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(SelectPackConfigViewModel::class.java)


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