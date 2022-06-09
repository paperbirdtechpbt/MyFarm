package com.pbt.myfarm.Activity.NotificationActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.NotificationViewModel
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_my_notificiation.*

class MyNotificiationActivity : AppCompatActivity() {

    var viewModel: NotificationViewModel? = null
    var adapter: AdapterNotification? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_notificiation)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        initViewmodel()
    }

    private fun initViewmodel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NotificationViewModel::class.java)
        val userid = MySharedPreference.getUser(this)?.id?.toString()

        viewModel?.getNotificationList(this, userid)

        viewModel?.notificationlist?.observe(this) { list ->


            if (!list.isNullOrEmpty()) {
                progress_notificationacitivty.visibility = View.GONE
                layout_nodatavailable_notification.visibility = View.GONE

                val linearLayoutManager = LinearLayoutManager(this)
                recyclerview_notificaitons.setLayoutManager(linearLayoutManager)

                adapter = AdapterNotification(this, list)
                recyclerview_notificaitons.adapter = adapter

            } else {
                progress_notificationacitivty.visibility = View.GONE
                layout_nodatavailable_notification.visibility = View.VISIBLE
            }
        }
    }
}