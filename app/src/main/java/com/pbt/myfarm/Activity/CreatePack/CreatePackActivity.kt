package com.pbt.myfarm.Activity.CreatePack

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.CreatePack.CreatePackAdapter.Companion.desciptioncompanian

import com.pbt.myfarm.Activity.CreateTask.FieldModel
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Activity.Pack.ViewPackModelClass
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.PackCommunityList
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.PackFieldResponse
import com.pbt.myfarm.PackViewModel.Companion.labelPackConfigPrefix
import com.pbt.myfarm.PackViewModel.Companion.packCommunityList
import com.pbt.myfarm.PackViewModel.Companion.packCommunityListname
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreatePackActivity : AppCompatActivity(), retrofit2.Callback<PackFieldResponse> {
    var viewmodel: PackViewModel? = null
    var binding: ActivityCreatePackBinding? = null
    var adapter: CreatePackAdapter? = null
    val successObject = JSONArray()
    val fieldModel = ArrayList<FieldModel>()
    var db: DbHelper? = null


    companion object {
        val TAG = "CreatePackActivity"
        var packName: String = ""
        var packprefixName: String = ""
        var packconfiglist: PackConfig? = null
        var arrayID: ArrayList<String>? = null
        var arrayName: ArrayList<String>? = null
        var arrayIDKey: ArrayList<String>? = null
        var arrayNameKey: ArrayList<String>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        desciptioncompanian = ""
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_pack)
        db = DbHelper(this, null)
        arrayID = ArrayList()
        arrayName = ArrayList()
        arrayIDKey = ArrayList()
        arrayNameKey = ArrayList()
        arrayID!!.clear()
        arrayName!!.clear()
        arrayIDKey!!.clear()
        arrayNameKey!!.clear()




        if (checkInternetConnection()) {
            initViewModel()
        }
//        else
//        {
//            val list=   db.readPackConfigFieldList()
//            val field=   db.readPackConfigFieldList_fieldlist()
//            val communitGroup=db.readPackCommunityGroup()
//            AppUtils.logDebug(TAG,Gson().toJson(list+"\n"+field+"\n"+communitGroup))
//            val packCommunityname = ArrayList<String>()
//            for (i in 0 until communitGroup.size){
//                packCommunityname.add(communitGroup.get(i).name)
//
//            }
//            recyclerview_createPack?.layoutManager = LinearLayoutManager(this)
//            adapter = CreatePackAdapter(
//                this, list, true, communitGroup,
//                packCommunityname
//            ) { list, name ->
//                while (list.contains("0")) {
//                    list.remove("0")
//                }
//                while (name.contains("0")) {
//                    name.remove("0")
//                }
//                while (ExpNameKey.contains("0")) {
//                    ExpNameKey.remove("0")
//                }
//                while (ExpAmtArrayKey.contains("0")) {
//                    ExpAmtArrayKey.remove("0")
//                }
//
//
//
//
//                for (i in 0 until name.size) {
//                    val jsonObject = JSONObject()
//                    jsonObject.put(ExpAmtArrayKey.get(i), name.get(i))
//                    jsonObject.put(ExpNameKey.get(i), list.get(i))
//
//                    successObject.put(jsonObject)
//                    fieldModel.add(FieldModel(name.get(i), list.get(i)))
//
//                }
//
//
//            }
//            recyclerview_createPack.adapter = adapter
//
//
//        }

    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PackViewModel::class.java)
        binding?.viewModel = viewmodel
        viewmodel?.activityContext = this@CreatePackActivity
        viewmodel?.progressbar = progressbar_createPackActivity

        packconfiglist = intent.getParcelableExtra(CONST_VIEWMODELCLASS_LIST)
        AppUtils.logError(TAG, "packConfiglist" + packconfiglist?.id.toString())

        viewmodel?.onConfigFieldList(this, true, packconfiglist?.id.toString())
        viewmodel?.configlist?.observe(this, Observer { list ->


//            setCommunityGroup()
            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<PackConfigFieldList>()::class.java)
            recyclerview_createPack?.layoutManager = LinearLayoutManager(this)
//            adapter = CreatePackAdapter(
//                this, config, true, packCommunityList,
//                packCommunityListname
//            )


            adapter = CreatePackAdapter(
                this, config, true, packCommunityList,
                packCommunityListname
            )
            { list, name ->

                while (list.contains("0")) {
                    list.remove("0")
                }
                while (name.contains("0")) {
                    name.remove("0")
                }
                AppUtils.logDebug(TAG, "listname" + list.size + name.size)





                for (i in 0 until name.size) {


                    val jsonObject = JSONObject()

                    jsonObject.put(arrayIDKey!!.get(i), list.get(i))
                    jsonObject.put(arrayNameKey!!.get(i), name.get(i))

                    successObject.put(jsonObject)
                    
                   val lastValueOfPacknew=db?.getLastofPackNew()
                    db?.addPackValues(list.get(i),name.get(i),lastValueOfPacknew)
                    fieldModel.add(FieldModel(name.get(i), list.get(i)))

                }
                AppUtils.logDebug(TAG, "successobject in createPack" + successObject.toString())

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
            btn_create_pack.visibility = View.GONE
            adapter?.callBackss()

//
//            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//            val currentDate = sdf.format(Date())
            val db = DbHelper(this, null)
            var packsnew: PacksNew? = null

            val d = db.getLastValue_pack_new(packconfiglist?.id.toString())
            val userid = MySharedPreference.getUser(this)?.id
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            if (d.isEmpty()) {
                packsnew= PacksNew(null, selectedCommunityGroup?.toInt(),
                userid,currentDate,null, desciptioncompanian,null,
                null,null,null,null,
                null,packconfiglist?.id.toString(),labelPackConfigPrefix,
                null,null,null,"1")

                db.addNewPack(packsnew,"1")

            }



        else{
            val newPackname: Int = d.toInt() + 1
                packsnew= PacksNew(null, selectedCommunityGroup?.toInt(),
                    userid,currentDate,null, desciptioncompanian,null,
                    null,null,null,null,
                    newPackname.toString(),packconfiglist?.id.toString(),labelPackConfigPrefix,
                    null,null,null,"1")


                db.addNewPack(packsnew,"1")

//            val viewTask = viewtasklist.get(0)
////                db.addNewPack(viewTask,currentDate,"","")
//            for (i in 0 until ExpAmtArray.size) {
//                db.addpackFieldValue(newPackname.toString(), ExpName.get(i), ExpAmtArray.get(i))
//            }
        }}

//-------------------|||Below Code is For sacving Pack Online||||--------------//


//            adapter?.callBackss()
////            viewmodel?.addPack()
//            val listdata = ArrayList<String>()
//
//            if (successObject != null) {
//                for (i in 0 until successObject.length()) {
//                    listdata.add(successObject.getString(i))
//                }
//            }
//            val userId = MySharedPreference.getUser(this)?.id
//
//            if (desciptioncompanian.isEmpty()) {
//                Toast.makeText(this, "Desciption is Required", Toast.LENGTH_SHORT).show()
//                btn_create_pack.visibility= View.VISIBLE
//
//            }
//            else{
//                val db=DbHelper(this,null)
//
//                ApiClient.client.create(ApiInterFace::class.java)
//                    .storePack(
//                        packconfiglist?.id.toString(),
//                        desciptioncompanian!!,
//                        selectedCommunityGroup,
//                        userId.toString(),
//                        successObject.toString(),
//                        packconfiglist?.name!!
//                    ).enqueue(this)
//            }
//
//        }
        //--------------Above code is For savging pack online ------------//

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

    private fun checkInternetConnection(): Boolean {
        val ConnectionManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            return true
        } else {
            return false
        }
    }

    override fun onResponse(call: Call<PackFieldResponse>, response: Response<PackFieldResponse>) {
        if (response.body()?.error == false) {
            btn_create_pack.visibility = View.VISIBLE

            Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PackActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            btn_create_pack.visibility = View.VISIBLE

            Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<PackFieldResponse>, t: Throwable) {
        btn_create_pack.visibility = View.VISIBLE
        try {
            AppUtils.logDebug(TAG, "Failed to Add new pack" + t.message)
            Toast.makeText(this, t.localizedMessage, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()

        }


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