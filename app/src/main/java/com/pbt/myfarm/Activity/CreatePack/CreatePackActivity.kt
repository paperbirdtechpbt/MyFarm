package com.pbt.myfarm.Activity.CreatePack

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_LIST_SIZE
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ActivityCreatePackBinding
import kotlinx.android.synthetic.main.activity_create_pack.*
import kotlinx.android.synthetic.main.activity_create_task.lablel_createnewtask
import kotlinx.android.synthetic.main.activity_create_task.spinner_Grip
import java.security.AccessController
import java.security.AccessController.getContext

class CreatePackActivity : AppCompatActivity() {
    var viewmodel: PackViewModel? = null
    var binding: ActivityCreatePackBinding? = null
    companion object{
        val TAG="CreatePackActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_pack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_pack)
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PackViewModel::class.java)
        binding?.viewModel = viewmodel
        setSpinner()

        val viewtask = intent.getParcelableExtra<ViewPackModelClass>(AppConstant.CONST_VIEWMODELCLASS_LIST)
        val listSize = intent.getIntExtra(CONST_LIST_SIZE,0)+1
        btn_update_pack.setOnClickListener{
            viewmodel?.updatePack()
        }
        btn_create_pack.setOnClickListener {
            viewmodel?.addPack()

        }
        if (viewtask!=null){
            AppUtils.logDebug(TAG,viewtask.toString())
            btn_create_pack.visibility= View.GONE
            btn_update_pack.visibility= View.VISIBLE


            lablel_createnewtask.setText("Update A Pack")
//            val viewtask = intent.getParcelableExtra<ViewTaskModelClass>(CONST_VIEWMODELCLASS_LIST)
            setdata(viewtask)
        }
        else {
            val configTypeName: String = "FVSKM"
            viewmodel?.confiType?.set(configTypeName)
            viewmodel?.namePrefix?.set("DDD"+listSize)
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

    private fun setdata(viewtask: ViewPackModelClass?) {

        viewmodel?.namePrefix?.set(viewtask?.packname)
        viewmodel?.confiType?.set(viewtask?.packType)
        viewmodel?.desciption?.set(viewtask?.packdesciption)
        viewmodel?.quantity?.set(viewtask?.quantitiy)
        viewmodel?.units?.set(viewtask?.units)
        viewmodel?.customer?.set(viewtask?.customer)
        viewmodel?.communityGroup?.set(viewtask?.communitygrip)




    }

}