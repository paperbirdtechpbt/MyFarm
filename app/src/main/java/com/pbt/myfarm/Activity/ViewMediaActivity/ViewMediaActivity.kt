package com.pbt.myfarm.Activity.ViewMediaActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_MEDIAOBJECT
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_MEDIATYPE
import kotlinx.android.synthetic.main.activity_view_media.*


class ViewMediaActivity : AppCompatActivity() {
    var viewModel: ViewModelViewMedia? = null

    var adapter: AdapterViewMedia? = null
    private val TAG = "ViewMediaActivity"
    override fun onResume() {
        super.onResume()
        initviewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_media)
    }

    private fun initviewModel() {

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelViewMedia::class.java)

        viewModel?.progressbar = prgssBurreing
        prgssBurreing.visibility = View.VISIBLE


        viewModel?.callMediaListApi(this, CreateTaskActivity.taskObject?.id.toString())

        viewModel?.mediaList?.observe(this) { list ->

            recyclerview_viewMediaList.setLayoutManager(GridLayoutManager(this, 3))


            if (!list.isNullOrEmpty()) {

                prgssBurreing.visibility = View.GONE
                layout_noMedia.visibility = View.GONE

                adapter = AdapterViewMedia(this, list)
                adapter?.onitemClick = { item, filetype ->

                    if (filetype == "image" || filetype == "video") {

                        val intent = Intent(this, PlayScreenActivity::class.java)
                        intent.putExtra(CONST_MEDIATYPE, filetype)
                        intent.putExtra(CONST_MEDIAOBJECT, item)
                        startActivity(intent)

                    } else {
                        viewModel?.downloadFile(item.link, item.name, this)
                    }
                }
            }
            else {

                layout_noMedia.visibility = View.VISIBLE
                prgssBurreing.visibility = View.GONE

            }
            recyclerview_viewMediaList.adapter = adapter
            recyclerview_viewMediaList.itemAnimator?.setChangeDuration(0)

        }
    }

}