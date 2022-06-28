package com.pbt.myfarm.Activity.PdfReport

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_pack_report.*
import java.io.File
import java.io.FileOutputStream

class PackReportActivity : AppCompatActivity() {

    lateinit var generatePDFBtn: Button
    private val r = Rect()


    var pageHeight = 1120
    var pageWidth = 792

    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    var PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack_report)

        generatePDFBtn = findViewById(R.id.idBtnGeneratePdf)

        bmp = BitmapFactory.decodeResource(resources, R.drawable.splashlogo)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 100, 100, false)


        if (checkPermissions()) {
            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }

        generatePDFBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                generatePDF()
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF() {

        val pdfDocument = PdfDocument()


        val paint = Paint()
        val title = Paint()
        val productCharacteristic = Paint()


        val myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        val canvas: Canvas = myPage.canvas


        canvas.drawBitmap(scaledbmp, 640F, 40F, paint)


        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))


        title.textSize = 15F
        title.setColor(ContextCompat.getColor(this, R.color.black))


        val packno = "L01001"


        canvas.drawText("Certificate of KAKAOMUNDO TRACEABILTY CHAIN", 200F, 80F, title)
        canvas.drawText("Pack number $packno", 300F, 110F, title)

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.black))
        title.textSize = 15F

        canvas.drawLine(25F, 160F, 770F, 161F, paint)

        productCharacteristic.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        productCharacteristic.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        productCharacteristic.textSize = 15F

        canvas.drawText("Main product Characteristic", 30F, 200F, productCharacteristic)

        val name = arrayListOf("Description", "Quantity", "Vendu")
        var m = 10F

        for (i in 0 until name.size) {
            val bitmap: String = name.get(i)


            if (myPage == null) {
                return
            }
            val canvas: Canvas = myPage.getCanvas()
            canvas.drawText(bitmap, 30F, 225F + m, title)
            m = m + 50F
        }
//        canvas.drawRect(30F,225F,0F,0F,paint)

        pdfDocument.finishPage(myPage)

        val file = File(Environment.getExternalStorageDirectory(), "GFG.pdf")
        pdfViewm.fromFile(file)
        pdfViewm.visibility = View.VISIBLE

        try {

            pdfDocument.writeTo(FileOutputStream(file))

//            Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }

        pdfDocument.close()
    }


    fun checkPermissions(): Boolean {

        val writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            WRITE_EXTERNAL_STORAGE
        )


        val readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            READ_EXTERNAL_STORAGE
        )

        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE
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