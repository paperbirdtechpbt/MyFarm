package com.pbt.myfarm.Activity.ViewMediaActivity

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_play_scress.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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

        // Bind the player to the view.
        playerview?.player = mPlayer
        playerview?.useArtwork=true
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

    fun openWebView(context: Context, file: ListFunctionFieldlist, webView: WebView) {
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("${file.link}")
    }

    fun openPdfViewver(context: Context, file: ListFunctionFieldlist, pdfView: PDFView) {
        RetrivePDFfromUrl(pdfView).execute(file.link)
    }
    internal class RetrivePDFfromUrl(var pdfView: PDFView) :
        AsyncTask<String?, Void?, InputStream?>() {
         override fun doInBackground(vararg strings: String?): InputStream? {
            // we are using inputstream
            // for getting out PDF.
            var inputStream: InputStream? = null
            try {
                val url = URL(strings[0])
                // below is the step where we are
                // creating our connection.
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.getResponseCode() === 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = BufferedInputStream(urlConnection.getInputStream())
                }
            } catch (e: IOException) {
                // this is the method
                // to handle errors.
                e.printStackTrace()
                return null
            }
            return inputStream
        }

        override fun onPostExecute(inputStream: InputStream?) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load()
        }
    }

}



