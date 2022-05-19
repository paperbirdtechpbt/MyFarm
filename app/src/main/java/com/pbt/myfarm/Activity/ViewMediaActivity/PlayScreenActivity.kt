package com.pbt.myfarm.Activity.ViewMediaActivity

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant
import com.pbt.myfarm.Util.AppUtils
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import kotlinx.android.synthetic.main.activity_play_scress.*
import java.io.*


class PlayScreenActivity : AppCompatActivity() , DownloadFile.Listener{
    var filetype: String? = null
    var file: ListFunctionFieldlist? = null
    var viewmodel: ViewModelPlayScreen? = null
    private var remotePDFViewPager: RemotePDFViewPager? = null

    private var pdfPagerAdapter: PDFPagerAdapter? = null
    private var url: String? = null
    private val pdfLayout: LinearLayout? = null
    val TAG="PlayScreenActivity"
    var manager: DownloadManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_scress)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
    }

    private fun initViewModel() {
        if (intent.extras != null) {
            filetype = intent.getStringExtra(AppConstant.CONST_MEDIATYPE)
            file = intent.getParcelableExtra(AppConstant.CONST_MEDIAOBJECT)
        }
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelPlayScreen::class.java)
        viewmodel?.videoView = playvideoView
        viewmodel?.imgView = viewFullScreenImage
        viewmodel?.progressbar = progressbar_playvideo
        viewmodel?.pdflayout =pdf_layout

        if (filetype == "image")

            viewmodel?.viewImage(this, file!!)
        if (filetype == "video")
            viewmodel?.playVideo(this, file!!)
        if (filetype == "file") {
            playvideoView.visibility= View.GONE
            viewFullScreenImage.visibility= View.GONE
            pdf_layout.visibility=View.VISIBLE
            progressbar_playvideo.visibility=View.VISIBLE
            remotePDFViewPager = RemotePDFViewPager(this, file?.link, this)

        }


    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()

    }


    override fun onSuccess(url: String?, destinationPath: String?) {

        pdfPagerAdapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager!!.adapter = pdfPagerAdapter
        updateLayout()
        progressbar_playvideo.setVisibility(View.GONE)
    }

    private fun updateLayout() {
        pdfLayout?.addView(remotePDFViewPager,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

    }

    override fun onFailure(e: Exception?) {
        AppUtils.logError(TAG,"on failure"+e?.message.toString())
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
    }
}


