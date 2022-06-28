package com.pbt.myfarm.Activity.PdfReport

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import com.google.gson.Gson
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_pack_report.*
import kotlinx.android.synthetic.main.activity_pdf_demo.*
import java.io.File
import java.io.FileOutputStream


class PdfDemoActivity : AppCompatActivity() {
    val TAG="PdfDemoActivity"
    var PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_pdf_demo)


        if (checkPermissions()) {

            PdfGenerator.getBuilder()
                .setContext(this)
                .fromLayoutXMLSource()
                .fromLayoutXML(R.layout.activity_pdf_demo,R.layout.activity_pdf_demo)
                .setFileName("Test-PDF")
                .setFolderNameOrPath("MyFolder/MyDemoList/")
//                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
                .build(object : PdfGeneratorListener() {
                    override fun onFailure(failureResponse: FailureResponse) {
                        super.onFailure(failureResponse)
                        AppUtils.logDebug(TAG,"onFailure"+failureResponse.toString())

                    }

                    override fun onStartPDFGeneration() {
                        AppUtils.logDebug(TAG,"onStartPDFGeneration")

                    }

                    override fun onFinishPDFGeneration() {
                        AppUtils.logDebug(TAG,"onFinishPDFGeneration")
                    }

                    override fun showLog(log: String) {
                        super.showLog(log)
                        AppUtils.logDebug(TAG,"log"+log.toString())
                    }

                    override fun onSuccess(response: SuccessResponse) {
                        super.onSuccess(response)
                        AppUtils.logDebug(TAG,"response"+ Gson().toJson(response))

                    }
                })
        } else {
            requestPermission()
        }

    }

    fun checkPermissions(): Boolean {

        val writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )


        val readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_CODE
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_CODE) {

            if (grantResults.size > 0) {


                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED
                ) {

                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                } else {


                    Toast.makeText(this, "Permission Denined..", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}