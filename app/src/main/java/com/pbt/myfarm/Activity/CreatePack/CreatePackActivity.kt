package com.pbt.myfarm.Activity.CreatePack


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.Activity.CreatePack.CreatePackAdapter.Companion.desciptioncompanian

import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.CreateTask.FieldModel
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.PackFieldResponse
import com.pbt.myfarm.PackConfigList
import com.pbt.myfarm.PackViewModel.Companion.packCommunityList
import com.pbt.myfarm.PackViewModel.Companion.packCommunityListname
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityCreatePackBinding
import kotlinx.android.synthetic.main.activity_create_pack.*
import kotlinx.android.synthetic.main.activity_create_task.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class CreatePackActivity : AppCompatActivity(), retrofit2.Callback<PackFieldResponse> {
    var viewmodel: PackViewModel? = null
    var binding: ActivityCreatePackBinding? = null
    var adapter: CreatePackAdapter? = null
    val successObject = JSONArray()
    val fieldModel = ArrayList<FieldModel>()

    companion object {
        val TAG = "CreatePackActivity"
        var packName: String = ""
        var packprefixName: String = ""
        var packconfiglist: PackConfigList? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ExpAmtArray.clear()
//        ExpName.clear()
//        ExpNameKey.clear()
//        ExpAmtArrayKey.clear()
//        setContentView(R.layout.activity_create_pack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_pack)
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PackViewModel::class.java)
        binding?.viewModel = viewmodel
        viewmodel?.activityContext = this@CreatePackActivity
        viewmodel?.progressbar = progressbar_createPackActivity

        packconfiglist = intent.getParcelableExtra(CONST_VIEWMODELCLASS_LIST)



        viewmodel?.onConfigFieldList(this, true, "25")
        viewmodel?.configlist?.observe(this, Observer { list ->
//            setCommunityGroup()
            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<PackConfigFieldList>()::class.java)
            recyclerview_createPack?.layoutManager = LinearLayoutManager(this)
            adapter = CreatePackAdapter(
                this, config, true, packCommunityList,
                packCommunityListname
            ) { list, name ->
                while (list.contains("0")) {
                    list.remove("0")
                }
                while (name.contains("0")) {
                    name.remove("0")
                }
                while (ExpNameKey.contains("0")) {
                    ExpNameKey.remove("0")
                }
                while (ExpAmtArrayKey.contains("0")) {
                    ExpAmtArrayKey.remove("0")
                }




                for (i in 0 until name.size) {
                    val jsonObject = JSONObject()
                    jsonObject.put(ExpAmtArrayKey.get(i), name.get(i))
                    jsonObject.put(ExpNameKey.get(i), list.get(i))

                    successObject.put(jsonObject)
                    fieldModel.add(FieldModel(name.get(i), list.get(i)))

                }


            }
            recyclerview_createPack.adapter = adapter
        })
//        setSpinner()

//        val viewtask = intent.getParcelableExtra<ViewPackModelClass>(AppConstant.CONST_VIEWMODELCLASS_LIST)
//        val listSize = intent.getIntExtra(CONST_LIST_SIZE,0)+1
        btn_update_pack.setOnClickListener {

//            viewmodel?.updatePack()
        }
        btn_create_pack.setOnClickListener {
            adapter?.callBackss()
//            viewmodel?.addPack()
            val listdata = ArrayList<String>()

            if (successObject != null) {
                for (i in 0 until successObject.length()) {
                    listdata.add(successObject.getString(i))
                }
            }

            val userId = MySharedPreference.getUser(this)?.id

            if (desciptioncompanian == null) {
                desciptioncompanian = "desciption"
            }


            ApiClient.client.create(ApiInterFace::class.java)
                .storePack(
                    packconfiglist?.id!!,
                    desciptioncompanian,
                    selectedCommunityGroup,
                    userId.toString(),
                    successObject.toString(),
                    packconfiglist?.name!!
                ).enqueue(this)

        }

//        if (viewtask!=null){
//            AppUtils.logDebug(TAG,viewtask.toString())
//            btn_create_pack.visibility= View.GONE
//            btn_update_pack.visibility= View.VISIBLE
//
//
//            lablel_createnewtask.setText("Update A Pack")
////            val viewtask = intent.getParcelableExtra<ViewTaskModelClass>(CONST_VIEWMODELCLASS_LIST)
//            setdata(viewtask)
//        }
//        else {
//            val configTypeName: String = "FVSKM"
//            viewmodel?.confiType?.set(configTypeName)
//            viewmodel?.namePrefix?.set("DDD"+listSize)
//        }
    }

    override fun onResponse(call: Call<PackFieldResponse>, response: Response<PackFieldResponse>) {

        Toast.makeText(this, "Task Created Successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, PackActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailure(call: Call<PackFieldResponse>, t: Throwable) {
        AppUtils.logDebug(TAG, "failure" + t.message)
        Toast.makeText(this, t.localizedMessage, Toast.LENGTH_SHORT).show()
    }

//     fun setCommunityGroup() {
////         name.setText(packConfig?.name)
////         nameprefix.setText(packConfig?.name_prefix)
//        val communitGroup: Spinner = findViewById(R.id.spinerCommunityGroup)
//
//        val dd = ArrayAdapter(this, android.R.layout.simple_spinner_item, groupArrayPack!!)
//        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        communitGroup.setAdapter(dd)
//         communitGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//             override fun onItemSelected(
//                 parent: AdapterView<*>, view: View, position: Int, id: Long
//             ) {
//                 selectedCommunityGroup = groupArrayIdPack!!.get(position)
//                 AppUtils.logDebug(TAG, "selectedCommunityGroup" + selectedCommunityGroup)
//
//             }
//
//             override fun onNothingSelected(parent: AdapterView<*>) {
//
//             }
//         }
//    }

//    private fun setSpinner() {
//        ArrayAdapter.createFromResource(
//            this, R.array.array_communitygroup, android.R.layout.simple_spinner_item
//        ).also { adapter ->
//
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner_Grip.adapter = adapter
//        }
//
//    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }


}