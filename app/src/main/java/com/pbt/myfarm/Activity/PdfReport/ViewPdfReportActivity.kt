package com.pbt.myfarm.Activity.PdfReport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.barteksc.pdfviewer.PDFView
import com.pbt.myfarm.Activity.ViewMediaActivity.ViewModelPlayScreen
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_play_scress.*

class ViewPdfReportActivity : AppCompatActivity() {
    var viewmodel: ViewModelViewPdf? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pdf_report)
        initViewModel()


    }

    override fun onResume() {
        super.onResume()
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelViewPdf::class.java)
        val pdfView:PDFView=findViewById(R.id.mpdfView  )
        viewmodel?.openPdfViewver(this, pdfView)

    }
}