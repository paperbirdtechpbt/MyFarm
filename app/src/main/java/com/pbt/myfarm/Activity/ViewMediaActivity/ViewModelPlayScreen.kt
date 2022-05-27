package com.pbt.myfarm.Activity.ViewMediaActivity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


@SuppressLint("StaticFieldLeak")
class ViewModelPlayScreen(val activity: Application) : AndroidViewModel(activity) {

    val TAG = "ViewModelPlayScreen"
    var videoView: VideoView? = null
    var imgView: ImageView? = null
    var progressbar: ProgressBar? = null
    var pdflayout: LinearLayout? = null
    var playerview: PlayerView? = null
    private var mPlayer: SimpleExoPlayer? = null


    fun playVideo(context: Context, item: ListFunctionFieldlist) {

        imgView?.visibility = View.GONE
        videoView?.visibility = View.VISIBLE
        progressbar?.visibility = View.GONE
        pdflayout?.visibility = View.GONE
        playerview?.visibility = View.VISIBLE
        mPlayer = SimpleExoPlayer.Builder(context).build()

        playerview?.player = mPlayer
        playerview?.useArtwork = true
        mPlayer!!.playWhenReady = true

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
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL!!))

        return mediaSource
    }

    fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        mPlayer!!.release()
        mPlayer = null
    }


    fun openPdfViewver(context: Context, file: ListFunctionFieldlist, pdfView: PDFView) {
        progressbar?.visibility = View.VISIBLE

        RetrivePDFfromUrl(pdfView,progressbar).execute(file.link)
    }
    @SuppressLint("StaticFieldLeak")
    internal class RetrivePDFfromUrl(private var pdfView: PDFView, var progressbar: ProgressBar?) :
        AsyncTask<String?, Void?, InputStream?>() {

        override fun doInBackground(vararg strings: String?): InputStream? {

            var inputStream: InputStream? = null
            try {
                val url = URL(strings[0])

                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.getResponseCode() === 200) {

                    inputStream = BufferedInputStream(urlConnection.getInputStream())
                }
            } catch (e: IOException) {

                e.printStackTrace()
                return null
            }
            return inputStream
        }


        override fun onPostExecute(inputStream: InputStream?) {
            pdfView.fromStream(inputStream).onLoad(object : OnLoadCompleteListener {
                override fun loadComplete(nbPages: Int) {
                    progressbar?.visibility = View.GONE
                }
            }).load()
        }
    }



}



