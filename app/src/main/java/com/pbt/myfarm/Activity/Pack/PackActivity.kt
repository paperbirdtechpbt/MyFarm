package com.pbt.myfarm.Activity.Pack

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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


class PackActivity : AppCompatActivity() ,retrofit2.Callback<testresponse>{
    private var adapter: AdapterViewPack? = null
    var db: DbHelper? = null
    var viewModel: ViewPackViewModel? = null
    var listsize = 0
    var tasklist: ArrayList<ViewPackModelClass>? = null
//    var binding: ActivityPackBinding? = null

    companion object {
        var TAG = "PackActivity"

        var packList: PackList? = null
        var updatePackBoolen=false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_pack)
        actionBar?.setDisplayHomeAsUpEnabled(true)


//        getDataFromDatabase()

        initViewModel()



        btn_create_task.setOnClickListener {
            val intent = Intent(this, PackConfigListActivity::class.java)
            intent.putExtra(CONST_LIST_SIZE, listsize)
            startActivity(intent)
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewPackViewModel::class.java)
//        binding?.viewmodel = viewModel

viewModel?.progressBAr=progressViewPack
        viewModel?.onPackListRequest(this)
        viewModel?.packlist?.observe(this, androidx.lifecycle.Observer { packlist ->

            tasklistSize.setText("Total Tasks-" + packlist.size)
            adapter = AdapterViewPack(this, packlist!!) { position, packname, boolean, list ->
                packList=list
              AppUtils.logDebug(TAG,"deletepack"+list.id)
                if (boolean) {
                showAlertDailog(packname, position, packList!!)
            }
                else{

                    updatePackBoolen=true
                    val intent=Intent(this, UpdatePackActivity::class.java)
//                        intent.putExtra("fragment","1")
//                        intent.putExtra(CONST_PACK_UPDATE_LIST, packList)
//                    val mBundle = Bundle()
//                    mBundle.putParcelable(CONST_PACK_UPDATE_LIST, packList)
//
//                    CreatePackFrament().arguments = mBundle
                        startActivity(intent)
                        finish()
//

                }
            }

            recyclerview_viewtask.layoutManager = LinearLayoutManager(this)
            recyclerview_viewtask.adapter = adapter
        })
    }

    @SuppressLint("Range")
    fun getDataFromDatabase() {

        db = DbHelper(this, null)
        tasklist = db!!.readPackData()

        listsize = tasklist?.size!!

        AppUtils.logDebug(TAG, tasklist?.size.toString())

//        adapter = AdapterViewPack(this, tasklist!!) { position, taskname, checkAction, list ->
//            if (checkAction) {
//                showAlertDailog(taskname, position)
//            } else {
//
//
//                Packlist = list
//
//                val intent = Intent(this, UpdatePackActivity::class.java)
//                intent.putExtra("fragment", 1)
////                val bundle = Bundle()
////                bundle.putParcelable(CONST_VIEWMODELCLASS_LIST, list)
////                intent.putExtras(bundle)
//
//
//                AppUtils.logDebug(TAG, list.toString())
//
//                startActivity(intent)
//
//
//            }
//
//        }
//        recyclerview_viewtask.adapter = adapter
//        checkListEmptyOrNot(tasklist!!)

    }

    private fun showAlertDailog(taskname: String, position: Int, list: PackList) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete $taskname") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    ApiClient.client.create(ApiInterFace::class.java).deletePack(list.id)
                        .enqueue(this)
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
