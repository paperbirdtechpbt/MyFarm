package com.pbt.myfarm.Activity.CreatePack


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
import com.pbt.myfarm.Activity.CreateTask.FieldModel
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.desciptioncompanian
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.PackFieldResponse
import com.pbt.myfarm.PackViewModel.Companion.packCommunityList
import com.pbt.myfarm.PackViewModel.Companion.packCommunityListname
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_VIEWMODELCLASS_LIST
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityCreatePackBinding
import kotlinx.android.synthetic.main.activity_create_pack.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList

class CreatePackActivity : AppCompatActivity(), retrofit2.Callback<PackFieldResponse> {
    var viewmodel: PackViewModel? = null
    var binding: ActivityCreatePackBinding? = null
    var adapter: CreatePackAdapter? = null
    val successObject = JSONArray()
    val fieldModel = ArrayList<FieldModel>()
    var db: DbHelper? = null
    var task: Task? = null


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

        AppUtils.logDebug(TAG, "desciption=====>>>>>>" + desciptioncompanian.toString())

        desciptioncompanian = ""
        initViewModel()


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
        task = intent.getParcelableExtra(AppConstant.CONST_TASKFUNCTION_TASKLIST)
        AppUtils.logError(TAG, "packConfiglist" + packconfiglist?.id.toString())

        viewmodel?.onConfigFieldList(this, false, packconfiglist?.id.toString(), "")
        viewmodel?.configlist?.observe(this, Observer { list ->
            if (!list.isNullOrEmpty()) {
                progressbar_createPackActivity.visibility = View.GONE
            }

//            setCommunityGroup()
            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<PackConfigFieldList>()::class.java)
            recyclerview_createPack?.layoutManager = LinearLayoutManager(this)
//            adapter = CreatePackAdapter(
//                this, config, true, packCommunityList,
//                packCommunityListname
//            )

            adapter = CreatePackAdapter(
                this, config, false, packCommunityList,
                packCommunityListname
            ) { list, name ->

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

                    val lastValueOfPacknew = db?.getLastofPackNew()
                    val idd = lastValueOfPacknew!!.toInt() + 1

//                    addPackValue(list.get(i),name.get(i),idd.toString())
                    db?.addPackValues(list.get(i), name.get(i), idd.toString(), false)
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
            if (desciptioncompanian.isNullOrEmpty()) {
                Toast.makeText(this, "Desription Is required", Toast.LENGTH_SHORT).show()
            } else {
                btn_create_pack.visibility = View.GONE
                adapter?.callBackss()

                if (AppUtils().isInternet(this)) {
                    progressbar_createPackActivity.visibility=View.VISIBLE
                    val listdata = ArrayList<String>()

                    if (successObject != null) {
                        for (i in 0 until successObject.length()) {
                            listdata.add(successObject.getString(i))
                        }
                    }
                    val userId = MySharedPreference.getUser(this)?.id

                    if (desciptioncompanian!!.isEmpty()) {
                        Toast.makeText(this, "Desciption is Required", Toast.LENGTH_SHORT).show()
                        btn_create_pack.visibility = View.VISIBLE

                    } else {
                        val db = DbHelper(this, null)
                        AppUtils.logDebug(TAG, "taskid==${task.toString()}")
                        if (task == null) {
                            ApiClient.client.create(ApiInterFace::class.java)
                                .storePack(
                                    packconfiglist?.id.toString(),
                                    desciptioncompanian!!,
                                    selectedCommunityGroup,
                                    userId.toString(),
                                    successObject.toString(),
                                    packconfiglist?.name!!,
                                ).enqueue(this)
                        } else {
                            ApiClient.client.create(ApiInterFace::class.java)
                                .storePackwithTaskid(
                                    packconfiglist?.id.toString(),
                                    desciptioncompanian!!,
                                    selectedCommunityGroup,
                                    userId.toString(),
                                    successObject.toString(),
                                    packconfiglist?.name!!,
                                    task?.id.toString()
                                ).enqueue(this)
                        }


                    }
                } else {
                    val sucess = viewmodel?.createPackOffline(this)
                    if (sucess == true) {
                        finish()
                    }
                }
            }
        }

    }

    private fun addPackValue(list: String, name: String, idd: String) {
        db?.addPackValues(list, name, idd, false)

    }


    override fun onResponse(call: Call<PackFieldResponse>, response: Response<PackFieldResponse>) {
        if (response.body()?.error == false) {
            btn_create_pack.visibility = View.VISIBLE
            progressbar_createPackActivity.visibility=View.GONE
            Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, CreateTaskActivity::class.java)
//            startActivity(intent)
            finish()
        } else {
            progressbar_createPackActivity.visibility=View.GONE
            btn_create_pack.visibility = View.VISIBLE
            AppUtils.logDebug(TAG, "error true= " + Gson().toJson(response.body()?.msg).toString())
//            Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<PackFieldResponse>, t: Throwable) {
        btn_create_pack.visibility = View.VISIBLE
        progressbar_createPackActivity.visibility=View.GONE

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