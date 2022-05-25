package com.pbt.myfarm.Activity.Home


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.*
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Adapter.Home.AdapterHomeActivity
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_CRASH_FOLDER_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_NAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.CustomExceptionHandler
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.io.File


class MainActivity : AppCompatActivity(), retrofit2.Callback<AllPriviledgeListResponse> {

    val data = ArrayList<EventList>()
    var viewModel: MainActivityViewModel? = null
    val TAG = "MainActivity"
    var roleID: String? = null

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
        setContentView(R.layout.activity_main)

        val mLayoutManager: LayoutManager = GridLayoutManager(this, 2)
        recyclerview_main.setLayoutManager(mLayoutManager)

        roleID = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_ID, "0")

        if (chechpermission()){
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                val file: File
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {

                    file = File(
                        Environment.getExternalStorageDirectory()
                            .toString() + File.separator + "/$CONST_CRASH_FOLDER_NAME/"
                    )
                } else {
                    file = File(
                        Environment.getExternalStorageDirectory()
                            .toString() + File.separator + "/$CONST_CRASH_FOLDER_NAME/"
                    )
                }
                if (!file.exists()) {
                    file.mkdirs()
                }
            } }

        val adminname = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_NAME, "")

        label_username_main.setText(MySharedPreference.getUser(this)?.name)
        label_userrole_main.setText(adminname)

        if (AppUtils().isInternet(this)) {
            initViewModel()
        }
        if (!AppUtils().isServiceRunning(this, MyFarmService::class.java)) {
            startService(Intent(this, MyFarmService::class.java))
        }
        GlobalScope.launch {
            callPrivilegeAPI(roleID)
        }

//        if (!Environment.isExternalStorageManager()) {
//            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//            val uri = Uri.fromParts("package", packageName, null)
//            intent.data = uri
//            startActivity(intent)
//        }

    }

    private fun callPrivilegeAPI(selectedroldid: String?) {
        if (AppUtils().isInternet(this)) {
            if (selectedroldid != "0") {
                ApiClient.client.create(ApiInterFace::class.java)
                    .getAllprivileges(selectedroldid.toString()).enqueue(this@MainActivity)
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
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainActivityViewModel::class.java)
        AppUtils().isServiceRunning(this, MyFarmService::class.java)
    }

    private fun setadapter(dataa: ArrayList<EventList>) {
        val adapter = AdapterHomeActivity(this, dataa)
        recyclerview_main.adapter = adapter
    }

    override fun onRestart() {
        super.onRestart()
        val roleID = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_ID, "0")
        if (!roleID.equals("0")) {
            GlobalScope.launch {
                callPrivilegeAPI(roleID)

            }
        }

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
        if (AppUtils().isInternet(this)) {
            GlobalScope.launch {
                callPrivilegeAPI(roleID)

            }
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
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

     fun chechpermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ), 100
            )

        }
        return true
    }

    override fun onResponse(
        call: Call<AllPriviledgeListResponse>,
        response: Response<AllPriviledgeListResponse>
    ) {
        if (response.body()?.error == false) {
            val baseResponse: AllPriviledgeListResponse = Gson().fromJson(
                Gson().toJson(response.body()),
                AllPriviledgeListResponse::class.java
            )
            privilegeListName.clear()
            privilegeListNameID.clear()
            privilegeList = baseResponse.privilage as ArrayList<ListPrivilege>
            for (i in 0 until privilegeList.size) {
                val privilege = Privilege(
                    privilegeList.get(i).id.toDouble().toInt(),
                    privilegeList.get(i).privilege
                )

                val db = DbHelper(this, null)
                db.addPrivilege(privilege)

                privilegeListName.add(privilegeList.get(i).privilege)
                privilegeListNameID.add(privilegeList.get(i).id)
            }

            setdata(privilegeListName)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== RESULT_OK ){

            if (Thread.getDefaultUncaughtExceptionHandler() !is CustomExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(
                    CustomExceptionHandler(
                        Environment.getExternalStorageDirectory().absolutePath + "/$CONST_CRASH_FOLDER_NAME",
                        "",
                        applicationContext
                    )
                )
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) CustomExceptionHandler.sendToServer(this)

        }
    }

    override fun onFailure(call: Call<AllPriviledgeListResponse>, t: Throwable) {
        try {
            println(t.message.toString())

        } catch (e: Exception) {
            println(e.message.toString())
        }
    }


}
