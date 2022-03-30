package com.pbt.myfarm.Activity.Home


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

        startService(Intent(this,MyFarmService::class.java))

        if (checkInternetConnection()){
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

    private fun checkInternetConnection():Boolean {
        val ConnectionManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            return true
        } else {
            return false
        }
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
}