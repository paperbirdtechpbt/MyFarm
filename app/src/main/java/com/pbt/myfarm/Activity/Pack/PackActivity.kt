package com.pbt.myfarm.Activity.Pack

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity
import com.pbt.myfarm.Activity.ViewPackViewModel
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.PackList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace

import com.pbt.myfarm.Util.AppConstant.Companion.CONST_LIST_SIZE

import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_pack.*
import kotlinx.android.synthetic.main.activity_view_task.btn_create_task
import kotlinx.android.synthetic.main.activity_view_task.layout_nodatavailable
import kotlinx.android.synthetic.main.activity_view_task.recyclerview_viewtask
import kotlinx.android.synthetic.main.activity_view_task.tasklistSize
import retrofit2.Call
import retrofit2.Response
import java.util.*


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
        var packList: PackList? = null
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
//        else
//        {
//            db = DbHelper(this, null)
//            tasklist = db!!.readPackData()
//
//            if (!tasklist.isNullOrEmpty()){
//                for(i in 0 until tasklist!!.size){
//                    val it= tasklist!!.get(i)
//                    it.padzero= it.name.padStart(4, '0')
//                    if (it.name_prefix.isNullOrEmpty()){
//                        val dd="."
//                        mypacklist.add(
//                            PackList(
//                                it.id, it.name,dd,
//                                it.pack_config_id!!,
//                                it.pack_config_name, " Type: ", " Desciption: ", it.description,
//                                it.com_group, it.created_by, it.padzero
//                            )
//                        )
//                    }
//                    else{
//                        mypacklist.add(
//                            PackList(
//                                it.id, it.name,it.name_prefix!!,
//                                it.pack_config_id!!,
//                                it.pack_config_name, " Type: ", " Desciption: ", it.description,
//                                it.com_group, it.created_by, it.padzero
//                            )
//                        )
//                    }
//
//
//                }
//                if (!mypacklist.isNullOrEmpty()){
//
//                    adapter = AdapterViewPack(this, mypacklist){e,p,ee,w ->
//
//                    }
//                    recyclerview_viewtask.layoutManager = LinearLayoutManager(this)
//                    recyclerview_viewtask.adapter = adapter
//                    progressViewPack?.visibility= View.GONE
//                }
//
//
//
//
//            }
//
//
//
//        }


        btn_create_task.setOnClickListener {
            val intent = Intent(this, PackConfigListActivity::class.java)
            intent.putExtra(CONST_LIST_SIZE, listsize)
            startActivity(intent)

        }
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

            tasklistSize.setText("Total Tasks-" + packlist.size)
            adapter = AdapterViewPack(this, packlist!!) { position, packname, boolean, list ->
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
        })
    }



    private fun showAlertDailog(taskname: String, position: Int, list: PackList) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete $taskname") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
//                    ApiClient.client.create(ApiInterFace::class.java).deletePack(list.id)
//                        .enqueue(this)
//                    val db=DbHelper(this,null)
//                    db.deletenewPack("11114","4")


//                    db?.deletePack(taskname)
//                    tasklist?.removeAt(position)
//                    adapter?.notifyItemRemoved(position)
//                    adapter?.notifyDataSetChanged()
//                    checkListEmptyOrNot(tasklist!!)
                    Toast.makeText(this, "Deleted $taskname", Toast.LENGTH_SHORT).show()
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
//        getDataFromDatabase()
    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            Toast.makeText(this, "Task Deleted SuccessFullly", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PackActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logError(TAG, t.localizedMessage)
        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
    }
}
