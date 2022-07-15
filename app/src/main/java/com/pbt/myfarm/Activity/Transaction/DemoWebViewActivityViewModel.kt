package com.pbt.myfarm.Activity.Transaction

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintJob
import android.print.PrintManager
import android.view.View
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pbt.myfarm.Activity.Pack.PackListModel
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import retrofit2.Call
import retrofit2.Response


class DemoWebViewActivityViewModel(val activity: Application) : AndroidViewModel(activity),
    retrofit2.Callback<PackListModel> ,AdapterView.OnItemSelectedListener{

    var spinner: Spinner?=null
    private val listId = MutableLiveData<ArrayList<String>>()
    private val listName = MutableLiveData<ArrayList<String>>()
    var mcontext:Context?=null
    var printWeb: WebView? = null
    lateinit var printJob: PrintJob
    var printBtnPressed = false

    val packlistname = ArrayList<String>()
    val packlistid = ArrayList<String>()
    val isToshowWebView=MutableLiveData<Boolean>()
    val packid=MutableLiveData<String>()
    init {
        isToshowWebView.postValue(false)
    }


    fun getAllPackList(context: Context) {
        mcontext=context
        val userid = MySharedPreference.getUser(context)?.id
        ApiClient.client.create(ApiInterFace::class.java).packList(userid.toString()).enqueue(this)
    }

    override fun onResponse(call: Call<PackListModel>, response: Response<PackListModel>) {

        packlistname.clear()
        packlistid.clear()

        if (response.isSuccessful) {
            response.body()?.data.let {

                packlistname.add("Select Pack")
                packlistid.add("000")

                it?.forEach { list ->
                    if (list.name_prefix != null) {
                        list.padzero = list.name_prefix + list.name!!.padStart(4, '0')
                    } else {
                        list.padzero = list.name!!.padStart(4, '0')
                    }

                    packlistname.add(list.padzero!!)
                    packlistid.add(list.id.toString())
                }
                setSpinner(packlistname, mcontext!!)
                listId.postValue(packlistid)
                listName.postValue(packlistname)
            }
        }

    }

    override fun onFailure(call: Call<PackListModel>, t: Throwable) {
    }

    fun setSpinner(list: ArrayList<String>, context: Context) {
        val aa=ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item,list)
        spinner?.adapter=aa

        spinner!!.setOnItemSelectedListener(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        if (packlistid.get(position)!="000"){
            packid.postValue(packlistid.get(position))

        }
        else{
            packid.postValue(packlistid.get(position))

        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    fun loadWebView(context: Context, webview: WebView?, progressbarmm: ProgressBar, packid: String) {
        webview?.settings?.javaScriptCanOpenWindowsAutomatically = true
        webview?.settings?.javaScriptEnabled = true
        webview?.getSettings()?.setJavaScriptEnabled(true)
        webview?.settings?.loadsImagesAutomatically = true
        webview?.settings?.databaseEnabled = true
        webview?.webChromeClient = WebChromeClient()
        webview?.settings?.setUseWideViewPort(true);

        webview?.getSettings()?.setPluginState(WebSettings.PluginState.ON)
        webview?.settings?.supportZoom()
        webview?.settings?.setAppCacheEnabled(true)
        webview?.settings?.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webview?.settings?.domStorageEnabled = true
        webview?.getSettings()?.setAllowFileAccessFromFileURLs(true)
        webview?.getSettings()?.setAllowUniversalAccessFromFileURLs(true)
        webview?.getSettings()?.setMediaPlaybackRequiresUserGesture(false)

        webview?.zoomIn()
        webview?.zoomOut()
        webview?.settings?.builtInZoomControls = true
        webview?.settings?.loadWithOverviewMode = true
        webview?.settings?.useWideViewPort = true
        webview?.getSettings()?.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webview?.settings?.setDomStorageEnabled(true)
        webview?.canGoBack()
        webview?.settings?.setBlockNetworkImage(false)
        webview?.settings?.setBlockNetworkLoads(false)
        webview?.getSettings()?.setAllowContentAccess(true)

        webview?.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        webview?.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // initializing the printWeb Object
                printWeb = webview
            }
        })
        val url="https://farm.myfarmdata.io/api/printPdf/$packid"

        webview?.loadUrl(url)

        webview?.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                progressbarmm.setProgress(progress)
                if (progress == 100) {
                    progressbarmm.setVisibility(View.GONE)
                }
            }
        })
        webview?.setDownloadListener(object : DownloadListener {
            override fun onDownloadStart(
                url: String?, userAgent: String?,
                contentDisposition: String?, mimetype: String?,
                contentLength: Long
            ) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                mcontext?.startActivity(i)
            }
        })
    }

    fun printWebPage(webview: WebView?, context: Context) {
        if (webview != null) {

            mprintWebPage(webview,context)
        } else {
            // in else condition we are simply displaying a toast message
            Toast.makeText(context, "Webpage not loaded..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mprintWebPage(webview: WebView, context: Context) {

        // set printBtnPressed true
        printBtnPressed = true

        // Creating  PrintManager instance
        val printManager = context
            .getSystemService(AppCompatActivity.PRINT_SERVICE) as PrintManager

        // setting the name of job
        val jobName = context.getString(R.string.app_name) + " webpage" + webview.url

        // Creating  PrintDocumentAdapter instance
        val printAdapter: PrintDocumentAdapter = webview.createPrintDocumentAdapter(jobName)
        assert(printManager != null)
        printJob = printManager.print(
            jobName, printAdapter,
            PrintAttributes.Builder().build()
        )


    }


}
