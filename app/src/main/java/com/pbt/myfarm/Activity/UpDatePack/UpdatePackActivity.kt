package com.pbt.myfarm.Activity.UpDatePack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pbt.myfarm.Fragement.CollectNewData.CollectNewData
import com.pbt.myfarm.Fragement.CreatePack.CreatePackFrament
import com.pbt.myfarm.Fragement.PackCollect.CollectPackFragement
import com.pbt.myfarm.R
import com.pbt.myfarm.TasklistDataModel


class UpdatePackActivity : AppCompatActivity() {
    var viewtask: TasklistDataModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pack)
        supportActionBar?.hide()
        var position = 0
        val extras = intent.extras
        if (extras != null) {
            position = extras.getInt("fragment")
        }


        val tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        val tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)

        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)

        tab_viewpager.setCurrentItem(position)



    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(CreatePackFrament(), "UpdatePack")
        adapter.addFragment(CollectPackFragement(), "Collect Data")
        adapter.addFragment(CollectNewData(), "Collect New ")


        viewpager.setAdapter(adapter)
    }
    class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
        FragmentPagerAdapter(supportFragmentManager) {


        private  var fragmentList1: ArrayList<Fragment> = ArrayList()
        private  var fragmentTitleList1: ArrayList<String> = ArrayList()


        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }


        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        override fun getCount(): Int {
            return fragmentList1.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }
}