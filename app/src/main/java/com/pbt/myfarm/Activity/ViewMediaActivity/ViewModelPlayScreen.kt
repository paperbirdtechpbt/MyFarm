package com.pbt.myfarm.Activity.ViewMediaActivity

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R

class ViewModelPlayScreen(val activity: Application) : AndroidViewModel(activity) {
    val TAG = "ViewModelPlayScreen"
    var videoView: VideoView? = null
    var imgView: ImageView? = null
    var progressbar: ProgressBar? = null
    var pdflayout: LinearLayout? = null
    var playerview: PlayerView? = null
    private var mPlayer: SimpleExoPlayer? = null


    //    fun playVideo(context: Context,item: ListFunctionFieldlist){
//
//        imgView?.visibility=View.GONE
//        videoView?.visibility = View.VISIBLE
//        progressbar?.visibility = View.VISIBLE
//        pdflayout?.visibility = View.VISIBLE
//
//        val uri: Uri = Uri.parse(item.link)
//        videoView?.setVideoURI(uri)
//        val mediaController = MediaController(context)
//
//        mediaController.setAnchorView(videoView)
//
//        mediaController.setMediaPlayer(videoView)
//        videoView?.setMediaController(mediaController)
//        videoView?.start()
//
//        videoView?.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
//            override fun onPrepared(mp: MediaPlayer) {
//
//                mp.setOnBufferingUpdateListener(object : MediaPlayer.OnBufferingUpdateListener {
//                    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
//                        if (percent == 100) {
//
//                            progressbar?.visibility = View.GONE
//                        }
//                        else{
//                        }
//                    }
//                })
//            }
//        })
//
//    }
    fun playVideo(context: Context, item: ListFunctionFieldlist) {

        imgView?.visibility = View.GONE
        videoView?.visibility = View.VISIBLE
        progressbar?.visibility = View.GONE
        pdflayout?.visibility = View.GONE
        playerview?.visibility = View.VISIBLE
        mPlayer = SimpleExoPlayer.Builder(context).build()

        // Bind the player to the view.
        playerview?.player = mPlayer

        //setting exoplayer when it is ready.
        mPlayer!!.playWhenReady = true

        // Set the media source to be played.
        mPlayer!!.setMediaSource(buildMediaSource(item.link))

        mPlayer!!.prepare()


    }

    fun viewImage(context: Context, file: ListFunctionFieldlist) {
        imgView?.visibility = View.VISIBLE
        videoView?.visibility = View.GONE
        progressbar?.visibility = View.GONE
        pdflayout?.visibility = View.GONE

        Glide.with(context)
            .load(file.link).fitCenter()
            .placeholder(R.drawable.ic_loadingimage)
            .error(R.drawable.ic_loadingerror)
            .into(imgView!!)
    }

    private fun buildMediaSource(videoURL: String?): MediaSource {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL!!))

        return mediaSource
    }

    fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        //release player when done
        mPlayer!!.release()
        mPlayer = null
    }


}
