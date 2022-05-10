package com.pbt.myfarm.Activity.Pack

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListNameOffline
import com.pbt.myfarm.Activity.PackConfigList.PackConfigListActivity
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity
import com.pbt.myfarm.Activity.ViewPackViewModel
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.PackList
import com.pbt.myfarm.PacksNew
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_LIST_SIZE
import com.pbt.myfarm.Util.AppConstant.Companion.CON_PACK_ID
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_create_pack.*
import kotlinx.android.synthetic.main.activity_pack.*
import kotlinx.android.synthetic.main.activity_view_task.btn_create_task
import kotlinx.android.synthetic.main.activity_view_task.recyclerview_viewtask
import kotlinx.android.synthetic.main.activity_view_task.tasklistSize
import retrofit2.Call
import retrofit2.Response


class PackActivity : AppCompatActivity(), retrofit2.Callback<testresponse> {

    private var adapter: AdapterViewPack? = null
    var db: DbHelper? = null
    var viewModel: ViewPackViewModel? = null
    var listsize = 0
    val mypacklist = ArrayList<PackList>()
    var tasklist: ArrayList<ViewPackModelClass>? = null

//    var binding: ActivityPackBinding? = null

    companion object {
        var TAG = "PackActivity"
        var packList: PacksNew? = null
        var updatePackBoolen = false

        var desciptioncompanian: String? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_pack)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        desciptioncompanian = " "

        if (AppUtils().isInternet(this)) {
            if (privilegeListName.contains("InsertPack")) {
                btn_create_task.visibility = View.VISIBLE
            }
        } else {
            AppUtils.logDebug(TAG, "privilegeListNameOffline" + privilegeListNameOffline.toString())
            if (privilegeListNameOffline.contains("InsertPack")) {
                btn_create_task.visibility = View.VISIBLE
            }
        }

        initViewModel()

        btn_create_task.setOnClickListener {
            val intent = Intent(this, PackConfigListActivity::class.java)
            intent.putExtra(CONST_LIST_SIZE, listsize)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewPackViewModel::class.java)

        viewModel?.progressBAr = progressViewPack
        viewModel?.onPackListRequest(this)

        viewModel?.packlist?.observe(this, androidx.lifecycle.Observer { packlist ->

            if (!packlist.isNullOrEmpty()) {
                progressViewPack.visibility = View.GONE
            }

            progressbar_createPackActivity?.visibility = View.GONE

            tasklistSize.setText("Total Tasks-" + packlist.size)
            adapter = AdapterViewPack(this, packlist!!) { position, packname, boolean, list ->

                Log.d("PackactivityAcj", "Pack ID ${list.id}")


                packList = list
                if (boolean) {
                    showAlertDailog(packname, position, packList!!)
                } else {
                    val intent = Intent(this, UpdatePackActivity::class.java)
                    intent.putExtra(CON_PACK_ID,list.id.toString())
                    startActivity(intent)
                }
            }

            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.stackFromEnd = true
            recyclerview_viewtask.setLayoutManager(linearLayoutManager)
            recyclerview_viewtask.adapter = adapter
        })
    }

    private fun showAlertDailog(taskname: String, position: Int, list: PacksNew) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete $taskname") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    if (AppUtils().isInternet(this)) {
                        ApiClient.client.create(ApiInterFace::class.java)
                            .deletePack(list.id.toString())
                            .enqueue(this)
                    } else {
                        val db = DbHelper(this, null)
                        db.deletePackNew(list.id.toString())
                    }
                    initViewModel()

                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    override fun onResume() {
        super.onResume()
        desciptioncompanian = ""

        initViewModel()
        adapter?.notifyDataSetChanged()
    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        AppUtils.logError(TAG, t.localizedMessage)
        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
    }
}
