package com.pbt.myfarm.Activity.ViewMediaActivity

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R
import com.squareup.picasso.Picasso

class ViewModelPlayScreen(val activity: Application) : AndroidViewModel(activity) {
    val TAG = "ViewModelPlayScreen"
    var videoView: VideoView? = null
    var  imgView: ImageView? = null
    var  progressbar: ProgressBar? = null
    var  pdflayout: LinearLayout? = null

    fun playVideo(context: Context,item: ListFunctionFieldlist){
        imgView?.visibility=View.GONE
        videoView?.visibility = View.VISIBLE
        progressbar?.visibility = View.VISIBLE
        pdflayout?.visibility = View.VISIBLE

        val uri: Uri = Uri.parse(item.link)
        videoView?.setVideoURI(uri)
        val mediaController = MediaController(context)

        mediaController.setAnchorView(videoView)

        mediaController.setMediaPlayer(videoView)
        videoView?.setMediaController(mediaController)

        videoView?.start()
        videoView?.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
                mp.setOnBufferingUpdateListener(object : MediaPlayer.OnBufferingUpdateListener {
                    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
                        if (percent == 100) {
                            progressbar?.visibility = View.GONE
                        }
                    }
                })
            }
        })

    }
    fun viewImage(context: Context,file: ListFunctionFieldlist){
        imgView?.visibility=View.VISIBLE
        videoView?.visibility = View.GONE
        progressbar?.visibility = View.GONE
        pdflayout?.visibility = View.GONE

        Glide.with(context)
            .load(file.link).fitCenter()
            .placeholder(R.drawable.ic_loadingimage)
            .error(R.drawable.ic_loadingerror)
            .into(imgView!!)
    }


}
