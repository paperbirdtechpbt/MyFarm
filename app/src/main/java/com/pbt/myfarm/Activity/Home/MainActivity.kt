package com.pbt.myfarm.Activity.Home


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Adapter.Home.AdapterHomeActivity
import com.pbt.myfarm.AllPriviledgeListResponse
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ListPrivilege
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_NAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity(), retrofit2.Callback<AllPriviledgeListResponse> {

    val data = ArrayList<EventList>()
    var viewModel: MainActivityViewModel? = null
    val TAG = "MainActivity"

    companion object {
        var ExpAmtArray = ArrayList<String>()
        var ExpName = ArrayList<String>()
        var ExpNameKey = ArrayList<String>()
        var ExpAmtArrayKey = ArrayList<String>()
        var selectedCommunityGroup = ""
        var privilegeList = ArrayList<ListPrivilege>()
        val privilegeListName = ArrayList<String>()
        val privilegeListNameOffline = ArrayList<String>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mLayoutManager: LayoutManager = GridLayoutManager(this, 2)
        recyclerview_main.setLayoutManager(mLayoutManager)
//        val selectedroldid = intent.extras?.get(CONST_ROLEID).toString()
        AppUtils.logDebug(TAG, "ON create Main Activity")

        if (chechpermission()) {
            startService(Intent(this, MyFarmService::class.java))
        } else {
            Toast.makeText(this, "Permission Mandatory", Toast.LENGTH_SHORT).show()
        }
        val adminname = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_NAME, "")

        label_username_main.setText(MySharedPreference.getUser(this)?.name)
        label_userrole_main.setText(adminname)

        if (AppUtils().isInternet(this)) {
            initViewModel()
        }

        val selectedroldid = MySharedPreference.getStringValue(
            this,
            CONST_PREF_ROLE_ID, "0"
        )
        AppUtils.logDebug(TAG, "Selected Role iD=>>" + selectedroldid)
        callPrivilegeAPI(selectedroldid)

//        setadapter()
    }

    private fun callPrivilegeAPI(selectedroldid: String?) {
        if (AppUtils().isInternet(this)){
            if (selectedroldid != "0") {
                ApiClient.client.create(ApiInterFace::class.java)
                    .getAllprivileges(selectedroldid.toString()).enqueue(this@MainActivity)
            }
        }
        else{
            val db=DbHelper(this,null)
          val  list=  db.getAllPrivilege()
            list.forEach{
                privilegeListNameOffline.add(it.name.toString())
            }
        }


    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainActivityViewModel::class.java)

//        viewModel?.packConfigList(this)
//        viewModel?.packCOnfigFielList(this, packconfiglist)

        AppUtils().isServiceRunning(this, MyFarmService::class.java)

    }

    private fun setadapter(dataa: ArrayList<EventList>) {
//        val adapter = AdapterHomeActivity(this, this.data)
        val adapter = AdapterHomeActivity(this, dataa)
        recyclerview_main.adapter = adapter
    }


    private fun setdata(privilegeList: ArrayList<String>) {
        data.clear()
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
        if (!data.isNullOrEmpty()) {
//            data.add(EventList("DashBoard", R.drawable.ic_dashboaradicon))
            setadapter(data)
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
        var userID = MySharedPreference.getUser(this)?.id.toString()
        val db = DbHelper(this, null)

        val collectData = db.getCollectDataToBeSend(userID)
        val events = db.getEventsTobeSend(userID)
        var packnew = db.getPacksToBeSend(userID)
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

                viewModel?.sendDataMastersApi(userID, this)
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

    private fun chechpermission(): Boolean {
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
            privilegeList = baseResponse.privilage as ArrayList<ListPrivilege>
            for (i in 0 until privilegeList.size) {

                privilegeListName.add(privilegeList.get(i).privilege)
            }
            setdata(privilegeListName)

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