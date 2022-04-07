package com.pbt.myfarm.Activity.Home


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Adapter.Home.AdapterHomeActivity
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val data = ArrayList<EventList>()
    var viewModel:MainActivityViewModel? = null
    var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:Int=100



    companion object
    {
        var ExpAmtArray = ArrayList<String>()
        var ExpName = ArrayList<String>()
        var ExpNameKey = ArrayList<String>()
        var ExpAmtArrayKey =ArrayList<String>()
        var selectedCommunityGroup = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mLayoutManager: LayoutManager = GridLayoutManager(this, 2)
        recyclerview_main.setLayoutManager(mLayoutManager)

        if (chechpermission())
        {
            startService(Intent(this,MyFarmService::class.java))

        }
        else{
            Toast.makeText(this, "Permission Mandatory", Toast.LENGTH_SHORT).show()
        }




        if (AppUtils().isInternet(this)){
            initViewModel()
         }

        setdata()
        setadapter()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainActivityViewModel::class.java)

//        viewModel?.packConfigList(this)
//        viewModel?.packCOnfigFielList(this, packconfiglist)

        AppUtils().isServiceRunning(this,MyFarmService::class.java)

    }


    private fun setadapter() {
        val adapter = AdapterHomeActivity(this,data)
        recyclerview_main.adapter = adapter
    }

    private fun setdata() {
        data.add(EventList("DashBoard",R.drawable.ic_dashboaradicon))
        data.add(EventList("Pack",R.drawable.ic_box))
        data.add(EventList("Task",R.drawable.ic__task))
        data.add(EventList("DashBoard Event",R.drawable.ic__dashboardevent))
        data.add(EventList("Event",R.drawable.ic_icon_list))

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
                    startActivity(Intent(this,LoginActivity::class.java))
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
                ), 100)

        }
        return true
    }
}