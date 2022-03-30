package com.pbt.myfarm.Activity.Pack

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreateTask.FieldModel
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity
import com.pbt.myfarm.Activity.ViewPackViewModel
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.ModelClass.*
import com.pbt.myfarm.PacksNew
import com.pbt.myfarm.PackList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_LIST_SIZE
import com.pbt.myfarm.Util.AppConstant.Companion.CONT_PACK
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.AppUtils.Companion.paramRequestBody
import kotlinx.android.synthetic.main.activity_create_pack.*
import kotlinx.android.synthetic.main.activity_pack.*
import kotlinx.android.synthetic.main.activity_view_event.*
import kotlinx.android.synthetic.main.activity_view_task.btn_create_task
import kotlinx.android.synthetic.main.activity_view_task.layout_nodatavailable
import kotlinx.android.synthetic.main.activity_view_task.recyclerview_viewtask
import kotlinx.android.synthetic.main.activity_view_task.tasklistSize
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.StringReader
import java.util.*
import kotlin.collections.ArrayList


class PackActivity : AppCompatActivity(), retrofit2.Callback<testresponse> {

    private var adapter: AdapterViewPack? = null
    var db: DbHelper? = null
    var viewModel: ViewPackViewModel? = null
    var listsize = 0
    val mypacklist= ArrayList<PackList>()
    var tasklist: ArrayList<ViewPackModelClass>? = null

//    var binding: ActivityPackBinding? = null

    companion object {
        var TAG = "PackActivity"
        var packList: PacksNew? = null
        var updatePackBoolen = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_pack)
        actionBar?.setDisplayHomeAsUpEnabled(true)


//        getDataFromDatabase()
        val checkInternet = checkInternetConnection()
        if (checkInternet) {
            initViewModel()
        }
        else
        {
//            setDatafromlocal()
//            db = DbHelper(this, null)
//          val   packconfig= db?.getAllPackConfig()
//            val packlist = db?.getAllPack()
////            progressViewPack.visibility=View.GONE
//
//            val packsnew = ArrayList<PacksNew>()
//            packlist?.forEach { routes ->
//                if (packconfig!!.isNotEmpty()){
//                    for (i in 0 until  packconfig.size){
//                        if (routes.pack_config_id==packconfig.get(i).id.toString()){
//                            val configname=packconfig.get(i).name_prefix
//                            if (configname!=null){
//                                routes.padzero= configname+routes.name.padStart(4, '0')
//                            }
//                            else{
//                                routes.padzero= routes.name.padStart(4, '0')
//                            }
//                            routes.type=" Type: "
//                            routes.labeldesciption=" Desciption: "
//                        }
//                    }
//                }
//                packsnew.add(routes)
//            }
//            packsnew.removeAt(0 )
//            adapter = AdapterViewPack(this, packsnew) { position, packname, boolean, list ->
//                packList = list
//                if (boolean) {
//                    showAlertDailog(packname, position, packList!!)
//                } else {
//
//                    updatePackBoolen = true
//                    val intent = Intent(this, UpdatePackActivity::class.java)
//                    startActivity(intent)
//
//                }
//            }
//
//            recyclerview_viewtask.layoutManager = LinearLayoutManager(this)
//            recyclerview_viewtask.adapter = adapter
//
//
//
        }


        btn_create_task.setOnClickListener {
//           var senddata: SendDataMasterList?=null
//            var datacollectdata=CollectData("","","",
//                "20","","","","2","",0,
//                "","",2,"10",)
//           val  collectData=ArrayList<CollectData>()
//
//
////           collectData.add(CollectData("17","2022-03-11 10:10:10",
////               "2022-03-11 10:10:20","20","10","51",
////                    null,"52",null,
////                    0,"4","2022-03-11 10:10:20",2,"value",)
////            )
//          collectData.add(datacollectdata)
//            AppUtils.logError(TAG,"collectdaa"+collectData.toString())
//
//
//            val  evnet=ArrayList<Event>()
//
//            var dataeveng= Event("","","","","","","","","","","","",
//                "","","","",0,"",2)
//
//            evnet.add(dataeveng)
////
////         evnet.add(
////                Event("20","2022-03-17","2022-03-16",
////                    "10","0",
////                    "2022-03-16 10:10:10","1","descp","181",
////                    "10","2022-03-17","2022-03-16","2",
////                    "2022-03-16 10:10:20","Test Api","2",)
////            )
//            val  packnew=ArrayList<com.pbt.myfarm.ModelClass.PacksNew>()
//            var datapacksnew= com.pbt.myfarm.ModelClass.PacksNew(
//                "","","","","","",
//                "", emptyList(),0,2,)
//
//packnew.add(datapacksnew)
////            packnew.add(PacksNew("1","2022-03-11 10:10:10",
////               "2022-03-11 10:10:20","Api Test",
////               "2022-03-12 10:10:20",
////                    "1","7", emptyList(),1,
////                    2,
////               ))
//            val  taskField=ArrayList<TaskField>()
//            var datataskfield= TaskField("",0,"","",2,"")
//taskField.add(datataskfield)
//
////           taskField.add(
////                TaskField("134",1,"169","112",2,
////                    "2022-03-16 08:37")
////            )
//            val  taskobject=ArrayList<TaskObject>()
//
//            var datataskobject =TaskObject("","",
//                0,"","",0,
//                )
//            taskobject.add(datataskobject)
//
////            taskobject.add(
////                TaskObject("22","2022-03-16 10:25:20",0,
////                    "176","112",
////                    2)
////            )
//            val  task=ArrayList<Task>()
//            var datatask =Task("","","","", emptyList(),"",
//                "",0,"", emptyList(),2)
//            task.add(datatask)
//
////            task.add(
////                Task("1","2022-03-14 11:56:20","2022-03-11 10:10:20",
////                    "APITEST", emptyList(),
////                    "2022-03-15 22:12:40","26",1,"10",
////                    emptyList(),2)
////            )
//
//            senddata=SendDataMasterList(collectData,evnet,packnew,taskField,taskobject,task)
//
//
////            val sData:SendDataMasterList=Gson().fromJson(Gson().toJson(senddata.toString()),SendDataMasterList::class.java)
//            AppUtils.logDebug(TAG,"SendDate"+senddata)
//
//            ApiClient.client.create(ApiInterFace::class.java).postJson(
//                senddata
//            ).enqueue(this)



            //--------------------------------------//
            val intent = Intent(this, PackConfigListActivity::class.java)
            intent.putExtra(CONST_LIST_SIZE, listsize)
            startActivity(intent)

        }
    }


    private fun setDatafromlocal() {


        db = DbHelper(this, null)
        val   packconfig= db?.getAllPackConfig()
        val packlist = db?.getAllPack()
//            progressViewPack.visibility=View.GONE

        val packsnew = ArrayList<PacksNew>()
        packlist?.forEach { routes ->
            if (packconfig!!.isNotEmpty()){
                for (i in 0 until  packconfig.size){
                    if (routes.pack_config_id==packconfig.get(i).id.toString()){
                        val configname=packconfig.get(i).name_prefix
                        if (configname!=null){
                            routes.padzero= configname+ routes.name!!.padStart(4, '0')
                        }
                        else{
                            routes.padzero= routes.name!!.padStart(4, '0')
                        }
                        routes.type=" Type: "
                        routes.labeldesciption=" Desciption: "
                    }
                }
            }
            packsnew.add(routes)
        }
        packsnew.removeAt(0 )
        tasklistSize.setText("Total Tasks-" + packsnew.size)

        adapter = AdapterViewPack(this, packsnew) { position, packname, boolean, list ->
            packList = list
            if (boolean) {
                showAlertDailog(packname, position, packList!!)
            } else {

                updatePackBoolen = true
                val intent = Intent(this, UpdatePackActivity::class.java)
                startActivity(intent)

            }
        }

        recyclerview_viewtask.layoutManager = LinearLayoutManager(this)
        recyclerview_viewtask.adapter = adapter





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

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewPackViewModel::class.java)
//        binding?.viewmodel = viewModel

        viewModel?.progressBAr = progressViewPack
        viewModel?.onPackListRequest(this)
        viewModel?.checkInteretConnection(this)
        viewModel?.packlist?.observe(this, androidx.lifecycle.Observer { packlist ->

            progressbar_createPackActivity?.visibility= View.GONE

            tasklistSize.setText("Total Tasks-" + packlist.size)
            adapter = AdapterViewPack(this, packlist!!) { position, packname, boolean, list ->
                packList = list
                if (boolean) {
                    showAlertDailog(packname, position, packList!!)
                }
                else {

                    //                    updatePackBoolen = true
                    val intent = Intent(this, UpdatePackActivity::class.java)
                    intent.putExtra("fragment","0")
                    intent.putExtra(CONT_PACK,list)
                    startActivity(intent)

                }
            }

            recyclerview_viewtask.layoutManager = LinearLayoutManager(this)
            recyclerview_viewtask.adapter = adapter
        })
    }



    private fun showAlertDailog(taskname: String, position: Int, list: PacksNew) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete $taskname") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
//                    ApiClient.client.create(ApiInterFace::class.java).deletePack(list.id.toString())
//                        .enqueue(this)
                    val db=DbHelper(this,null)

                    db.deletePackNew(list.primaryid.toString())

                    setDatafromlocal()
                    adapter?.notifyItemRemoved(position)


//                    db?.deletePack(taskname)
//                    tasklist?.removeAt(position)
//                    adapter?.notifyDataSetChanged()
//                    checkListEmptyOrNot(tasklist!!)
//                    Toast.makeText(this, "Deleted $taskname", Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    private fun checkListEmptyOrNot(tasklist: ArrayList<ViewPackModelClass>) {
        if (tasklist.isEmpty()) {
            layout_nodatavailable.visibility = View.VISIBLE
        } else {
            layout_nodatavailable.visibility = View.GONE
        }
    }


    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
        initViewModel()
//        getDataFromDatabase()
    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
                        Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()

//            Toast.makeText(this, "Pack Deleted SuccessFullly", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, PackActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logError(TAG, t.localizedMessage)
        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
    }
}
