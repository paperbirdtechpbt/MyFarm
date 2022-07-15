package com.pbt.myfarm.Activity.PdfReport

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ViewModelViewPdf(val activity: Application) : AndroidViewModel(activity) {
    var progressbar: ProgressBar? = null


    fun openPdfViewver(context: Context,    pdfView: PDFView) {
        progressbar?.visibility = View.VISIBLE

//        RetrivePDFfromUrl(pdfView,progressbar).execute(file.link)
//        RetrivePDFfromUrl(pdfView,progressbar).execute("http://www.africau.edu/images/default/sample.pdf")
        RetrivePDFfromUrl(pdfView,progressbar).execute("https://farm.myfarmdata.io/chartPdf")
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
