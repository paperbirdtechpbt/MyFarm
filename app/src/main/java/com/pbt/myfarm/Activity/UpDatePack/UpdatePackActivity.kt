package com.pbt.myfarm.Activity.UpDatePack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListNameOffline
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment
import com.pbt.myfarm.Fragement.CreatePack.UpdatePackFragement
import com.pbt.myfarm.Fragement.PackCollect.CollectDataFragement
import com.pbt.myfarm.PacksNew
import com.pbt.myfarm.R
import com.pbt.myfarm.TasklistDataModel
import com.pbt.myfarm.Util.AppConstant.Companion.CON_PACK_ID
import com.pbt.myfarm.Util.AppUtils


class UpdatePackActivity : AppCompatActivity() {
    var viewtask: TasklistDataModel? = null

    companion object {
        var positionnn = 0
        var packlist: PacksNew? = null
    }

    var packID: String = ""
    override fun onDestroy() {
        super.onDestroy()
//        desciptioncompanian =" "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pack)
//        supportActionBar?.hide()

        val extras = intent.extras

        if (extras != null ) {
            extras.getString("fragment")?.let {
                positionnn = extras.getString("fragment")!!.toInt()
            }

        }

        if (!extras?.getString(CON_PACK_ID).isNullOrEmpty()) {
            packID = extras?.getString(CON_PACK_ID) ?: ""
        }

        CreateNewCollectDataFragment.newInstance("Hi Test Data pass")

        val tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        val tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)

        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)

        tab_viewpager.setCurrentItem(positionnn)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)



        if (positionnn == 0) {

            val updatePackFragment = UpdatePackFragement()
            val bundle = Bundle();
            bundle.putString(CON_PACK_ID, packID);
            updatePackFragment.arguments = bundle;
            adapter.addFragment(updatePackFragment, "UpdatePack")

            if (AppUtils().isInternet(this)) {
                if (privilegeListName.contains("CollectData")) {
                    adapter.addFragment(CollectDataFragement(), "Collect Data")
                }
//         adapter.addFragment(CollectDataFragement(), "Collect Data")
                if (privilegeListName.contains("InsertCollectData")) {
                    adapter.addFragment(CreateNewCollectDataFragment(), "Edit Data ")
                }
            } else {
                if (privilegeListNameOffline.contains("CollectData")) {
                    adapter.addFragment(CollectDataFragement(), "Collect Data")
                }
//         adapter.addFragment(CollectDataFragement(), "Collect Data")
                if (privilegeListNameOffline.contains("InsertCollectData")) {
                    adapter.addFragment(CreateNewCollectDataFragment(), "Edit Data ")
                }
            }


//         adapter.addFragment(CreateNewCollectDataFragment(), "Collect New ")
        } else {

            val updatePackFragment = UpdatePackFragement()
            val bundle = Bundle();
            bundle.putString(CON_PACK_ID, packID);
            updatePackFragment.arguments = bundle;
            adapter.addFragment(updatePackFragment, "UpdatePack")

            if (AppUtils().isInternet(this)) {
                if (privilegeListName.contains("CollectData")) {
                    adapter.addFragment(CollectDataFragement(), "Collect Data")
                }

                if (privilegeListName.contains("InsertCollectData")) {
                    adapter.addFragment(CreateNewCollectDataFragment(), "Edit Data ")
                }
            } else {
                if (privilegeListNameOffline.contains("CollectData")) {
                    adapter.addFragment(CollectDataFragement(), "Collect Data")
                }

                if (privilegeListNameOffline.contains("InsertCollectData")) {
                    adapter.addFragment(CreateNewCollectDataFragment(), "Edit Data ")
                }
            }


        }



        viewpager.setAdapter(adapter)
    }

    class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
        FragmentPagerAdapter(supportFragmentManager) {


        private var fragmentList1: ArrayList<Fragment> = ArrayList()
        private var fragmentTitleList1: ArrayList<String> = ArrayList()


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