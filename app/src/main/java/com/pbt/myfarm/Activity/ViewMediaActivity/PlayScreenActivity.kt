package com.pbt.myfarm.Activity.ViewMediaActivity

import android.os.Bundle
import android.view.View
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


    val TAG = "PlayScreenActivity"

    override fun onResume() {
        super.onResume()

        initViewModel()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_scress)
        actionBar?.setDisplayHomeAsUpEnabled(true)

//        initViewModel()
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
        progressbar_playvideo.visibility = View.VISIBLE


        if (filetype == "image"){
            playerView.visibility = View.GONE
            viewFullScreenImage.visibility = View.VISIBLE
            pdf_layout.visibility = View.GONE
            webView.visibility=View.GONE
            progressbar_playvideo.visibility = View.GONE
            viewmodel?.viewImage(this, file!!)
        }

        if (filetype == "video"){
            playerView.visibility = View.VISIBLE
            viewFullScreenImage.visibility = View.GONE
            pdf_layout.visibility = View.GONE
            webView.visibility=View.GONE

            viewmodel?.playVideo(this, file!!)
        }
//
//        if (filetype == "file") {
//
//            playvideoView.visibility = View.GONE
//            viewFullScreenImage.visibility = View.GONE
//            pdf_layout.visibility = View.VISIBLE
//            progressbar_playvideo.visibility = View.VISIBLE
//            webView.visibility=View.VISIBLE
//            viewmodel?.openWebView(this, file!!, webView)
//
//        }
        if (filetype=="pdf"|| filetype=="PDF"){
            playvideoView.visibility = View.GONE
            viewFullScreenImage.visibility = View.GONE
            pdf_layout.visibility = View.VISIBLE
            webView.visibility=View.GONE

            pdfView.visibility=View.VISIBLE
            progressbar_playvideo.visibility = View.VISIBLE

            viewmodel?.openPdfViewver(this, file!!,pdfView)


        }
        if (filetype == "music") {
            playerView.visibility = View.VISIBLE
            viewFullScreenImage.visibility = View.GONE
            pdf_layout.visibility = View.GONE
            webView.visibility=View.GONE

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


