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
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.gson.Gson
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Activity.NotificationActivity.MyNotificiationActivity
import com.pbt.myfarm.Adapter.Home.AdapterHomeActivity
import com.pbt.myfarm.AllPriviledgeListResponse
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.ListPrivilege
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.Privilege
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppConstant.Companion.CAMERA_PERMISSION_REQUEST_CODE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PREF_ROLE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.STORAGE_PERMISSION_REQUEST_CODE
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.net.URL

class MainActivity : AppCompatActivity(), retrofit2.Callback<AllPriviledgeListResponse> {

    val data = ArrayList<EventList>()
    var viewModel: MainActivityViewModel? = null
    val TAG = "MainActivity"
    var roleID: String? = null


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

        var textCartItemCount: TextView? = null
        val mCartItemCount = 0
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppUtils().isInternet(this)) {
            initViewModel()
        }
        viewModel?.initCrash(this)

        val mLayoutManager: LayoutManager = GridLayoutManager(this, 2)
        recyclerview_main.setLayoutManager(mLayoutManager)

        roleID = MySharedPreference.getStringValue(this, CONST_PREF_ROLE_ID, "0")
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


        if (!AppUtils().isServiceRunning(this, MyFarmService::class.java)) {
            startService(Intent(this, MyFarmService::class.java))
        }
        GlobalScope.launch {
            callPrivilegeAPI(roleID)
        }

//            Handler().postDelayed({
//                GlobalScope.launch {
//                viewModel?.getPolygonPointFromApi(this@MainActivity)}
//            }, 10000)

//        AppUtils.logDebug(TAG, "pointss==" + Gson().toJson(json).toString())

    }

    private fun callPrivilegeAPI(selectedroldid: String?) {
        if (AppUtils().isInternet(this)) {
            if (selectedroldid != "0") {
                GlobalScope.launch {
                    viewModel?.callPrivilegeAPi(this@MainActivity, selectedroldid.toString())
                }
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
        viewModel?.mprivilegeListName?.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                setdata(list)
            }
        }
    }

    private fun setadapter(dataa: ArrayList<EventList>) {
        val adapter = AdapterHomeActivity(this, dataa)
        recyclerview_main.adapter = adapter
        progressbar_main.visibility=View.GONE
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
            data.add(EventList("Kpi", R.drawable.ic_kpi))

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
            if (privilegeList.contains("Event")) {
                data.add(EventList("View Report", R.drawable.ic_report))
            }
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
                data.add(EventList("Event", R.drawable.ic_pdficon))
            }
            if (!data.isNullOrEmpty()) {
                setadapter(data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu!!.findItem(R.id.notifications)

        val actionView: View = menuItem.actionView
        textCartItemCount = actionView.findViewById(R.id.cart_badge) as TextView
        setupBadge(mCartItemCount)
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }

        return true
    }

    fun setupBadge(mCartItemCount: Int) {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount!!.visibility != View.GONE) {
                    textCartItemCount!!.visibility = View.GONE
                }
            } else {
                textCartItemCount!!.text = Math.min(mCartItemCount, 99).toString()
                if (textCartItemCount!!.visibility != View.VISIBLE) {
                    textCartItemCount!!.visibility = View.VISIBLE
                }
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
        if (id == R.id.notifications) {
            openNotificationAcitivity()
            return true
        }
        return true
    }

    private fun openNotificationAcitivity() {
        startActivity(Intent(this, MyNotificiationActivity::class.java))
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

    override fun onFailure(call: Call<AllPriviledgeListResponse>, t: Throwable) {
        println(t.message.toString())
    }

}
