package com.pbt.myfarm.Activity.ViewMediaActivity

import android.app.DownloadManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant

import kotlinx.android.synthetic.main.activity_play_scress.*


class PlayScreenActivity : AppCompatActivity() {
    var filetype: String? = null
    var file: ListFunctionFieldlist? = null
    var viewmodel: ViewModelPlayScreen? = null

    private var url: String? = null
    private val pdfLayout: LinearLayout? = null
    val TAG = "PlayScreenActivity"
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
        viewmodel?.pdflayout = pdf_layout
        viewmodel?.playerview = playerView

        if (filetype == "image")
            progressbar_playvideo.visibility = View.GONE

        viewmodel?.viewImage(this, file!!)
        if (filetype == "video")
//            progressbar_playvideo.visibility=View.VISIBLE

            viewmodel?.playVideo(this, file!!)
        if (filetype == "file") {

            playvideoView.visibility = View.GONE
            viewFullScreenImage.visibility = View.GONE
            pdf_layout.visibility = View.VISIBLE
            progressbar_playvideo.visibility = View.VISIBLE
            webView.visibility=View.VISIBLE
            viewmodel?.openWebView(this, file!!, webView)

        }
        if (filetype=="pdf"|| filetype=="PDF"){
            playvideoView.visibility = View.GONE
            viewFullScreenImage.visibility = View.GONE
            pdf_layout.visibility = View.GONE
            progressbar_playvideo.visibility = View.GONE
            webView.visibility=View.GONE

            pdfView.visibility=View.VISIBLE
            viewmodel?.openPdfViewver(this, file!!,pdfView)


        }
        if (filetype == "music") {

            viewmodel?.playVideo(this, file!!)

        }


    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()

    }

    override fun onStop() {
        super.onStop()
        viewmodel?.releasePlayer()

    }

}


