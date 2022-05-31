package com.pbt.myfarm.Activity.Home


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Adapter.Home.AdapterHomeActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ModelClass.EventList

import com.pbt.myfarm.Util.AppConstant.Companion.CAMERA_PERMISSION_REQUEST_CODE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.STORAGE_PERMISSION_REQUEST_CODE
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import com.pbt.myfarm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val data = ArrayList<EventList>()
    val viewModel by inject<MainActivityViewModel>()
    val TAG = "MainActivity"
    var roleID: String? = null
    lateinit var binding: ActivityMainBinding


    private lateinit var appUtils: AppUtils

    companion object {
        var ExpAmtArray = ArrayList<String>()
        var ExpName = ArrayList<String>()
        var ExpNameKey = ArrayList<String>()
        var ExpAmtArrayKey = ArrayList<String>()
        var selectedCommunityGroup = ""
        var privilegeList = ArrayList<ListPrivilege>()
        val privilegeListName = ArrayList<String>()
        val privilegeListNameID = ArrayList<String>()
        val privilegeListNameOffline = ArrayList<String>()
        val privilegeListNameOfflineID = ArrayList<String>()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel=viewModel
        binding.lifecycleOwner = this


        if (AppUtils().isInternet(this)) {
            initViewModel()
        }
        viewModel.initCrash(this)

        val mLayoutManager: LayoutManager = GridLayoutManager(this, 2)
        recyclerview_main.setLayoutManager(mLayoutManager)

        appUtils = AppUtils()

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askForCameraPermission()

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    viewModel?.showDialogPermission(this)
                }
            } else {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel?.showDialogPermission(this)
                }
            }
        }


        val adminname = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_NAME, "")

        label_username_main.setText(MySharedPreference.getUser(this)?.name)
        label_userrole_main.setText(adminname)


//        if (!AppUtils().isServiceRunning(this, MyFarmService::class.java)) {
//            startService(Intent(this, MyFarmService::class.java))
//        }



    }

    private fun callPrivilegeAPI(selectedroldid: String?) {
        if (AppUtils().isInternet(this)) {
            if (selectedroldid != "0") {

                    viewModel.callPrivilegeAPi(this@MainActivity, selectedroldid.toString())




            }
        }

        if (!AppUtils().isInternet(this)) {
            val db = DbHelper(this, null)
            val list = db.getAllPrivilege()
            list.forEach {
                privilegeListNameOffline.add(it.name.toString())
                privilegeListNameOfflineID.add(it.id.toString())
            }
            setdata(privilegeListNameOffline)
        }
    }

    private fun initViewModel() {
        roleID = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_ID, "0")

        callPrivilegeAPI(roleID)
//        AppUtils().isServiceRunning(this, MyFarmService::class.java)
        viewModel.mprivilegeListName.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                setdata(list)
            }

        }
    }

    private fun setadapter(dataa: ArrayList<EventList>) {
        val adapter = AdapterHomeActivity(this, dataa)
        recyclerview_main.adapter = adapter
    }


    override fun onResume() {
        super.onResume()
        if (!AppUtils().isInternet(this)) {
            privilegeListNameOffline.clear()
            privilegeListNameOfflineID.clear()
            val db = DbHelper(this, null)
            val list = db.getAllPrivilege()
            list.forEach {
                privilegeListNameOffline.add(it.name.toString())
                privilegeListNameOfflineID.add(it.id.toString())
            }
            setdata(privilegeListNameOffline)
        }

    }

    private fun setdata(privilegeList: ArrayList<String>) {
        data.clear()
        if (AppUtils().isInternet(this)) {
            data.add(EventList("DashBoard", R.drawable.ic_dashboaradicon))

            if (privilegeList.contains("Pack")) {
                data.add(EventList("Pack", R.drawable.ic_box))

            }
            if (privilegeList.contains("Task")) {
                data.add(EventList("Task", R.drawable.ic__task))

            }
            if (privilegeList.contains("DashboardEvent")) {
                data.add(EventList("DashboardEvent", R.drawable.ic__dashboardevent))

            }
            if (privilegeList.contains("Event")) {
                data.add(EventList("Event", R.drawable.ic_icon_list))

            }
            data.add(EventList("QR Demo", R.drawable.ic_qrcode))
            if (!data.isNullOrEmpty()) {
//            data.add(EventList("DashBoard", R.drawable.ic_dashboaradicon))
                setadapter(data)
            }
        } else {
            data.add(EventList("DashBoard", R.drawable.ic_dashboaradicon))

            if (privilegeListNameOffline.contains("Pack")) {
                data.add(EventList("Pack", R.drawable.ic_box))

            }
            if (privilegeListNameOffline.contains("Task")) {
                data.add(EventList("Task", R.drawable.ic__task))

            }
            if (privilegeListNameOffline.contains("DashboardEvent")) {
                data.add(EventList("DashboardEvent", R.drawable.ic__dashboardevent))

            }
            if (privilegeListNameOffline.contains("Event")) {
                data.add(EventList("Event", R.drawable.ic_icon_list))

            }
            if (!data.isNullOrEmpty()) {
                setadapter(data)
            }
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_logout) {
            checkOfflineDataToSync()
//            showAlertDialog()

            return true
        }
        return true
    }

    private fun checkOfflineDataToSync() {
        val userID = MySharedPreference.getUser(this)?.id.toString()
        val db = DbHelper(this, null)

        val collectData = db.getCollectDataToBeSend(userID)
        val events = db.getEventsTobeSend(userID)
        val packnew = db.getPacksToBeSend(userID)
        val taskField = ArrayList<com.pbt.myfarm.ModelClass.TaskField>()
        val taskobject = ArrayList<com.pbt.myfarm.ModelClass.TaskObject>()
        val task = db.getTasksToBeSend(userID)

        if (collectData.isNullOrEmpty() && events.isNullOrEmpty() && packnew.isNullOrEmpty() &&
            taskField.isNullOrEmpty() && taskobject.isNullOrEmpty() && task.isNullOrEmpty()
        ) {
            showAlertDialog(this)
        } else {
            if (AppUtils().isInternet(this)) {
                Toast.makeText(this, "Uploading Offline Data", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    viewModel?.sendDataMastersApi(userID, this@MainActivity)
                }
            } else {
                Toast.makeText(
                    this,
                    "Please Connect to Internet \n Pending offline Data to Send     ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    fun showAlertDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("LogOut")
            .setMessage("Are you sure you want to Logout")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel?.truncateAllTables(this)

                    MySharedPreference.clearPref(context)
                    startActivity(Intent(context, LoginActivity::class.java))
//                   finish()
                    (context as Activity).finish()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        viewModel?.showDialogPermission(this)
                    }
                } else {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        viewModel?.showDialogPermission(this)
                    }
                }
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            Toast.makeText(this, "Storage permission granted ", Toast.LENGTH_LONG).show()
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted ", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        );
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

}
