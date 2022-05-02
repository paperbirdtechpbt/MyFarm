package com.pbt.myfarm.Activity.Home


import android.app.AlertDialog
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
import com.pbt.myfarm.ListPrivilege
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_NAME
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList


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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mLayoutManager: LayoutManager = GridLayoutManager(this, 2)
        recyclerview_main.setLayoutManager(mLayoutManager)
//        val selectedroldid = intent.extras?.get(CONST_ROLEID).toString()
        AppUtils.logDebug(TAG,"ON create Main Activity")

        if (chechpermission()) {
            startService(Intent(this, MyFarmService::class.java))
        } else {
            Toast.makeText(this, "Permission Mandatory", Toast.LENGTH_SHORT).show()
        }
        val adminname=MySharedPreference.getStringValue(this,CONST_PREF_ROLE_NAME,"")

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
        if (selectedroldid != "0") {
            ApiClient.client.create(ApiInterFace::class.java)
                .getAllprivileges(selectedroldid.toString()).enqueue(this@MainActivity)
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
            showAlertDialog()

            return true
        }
        return true
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("LogOut")
            .setMessage("Are you sure you want to Logout")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    MySharedPreference.clearPref(this)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
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