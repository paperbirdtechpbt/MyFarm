package com.pbt.myfarm.Activity.QrDemoActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_qr_code.*


class QrCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
        btn_demoscanner.setOnClickListener{
            openZingScanner()
        }
    }

    private fun openZingScanner() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setPrompt("My Farm Code Scanner")
        intentIntegrator.setCameraId(0)
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.setBeepEnabled(true)
        intentIntegrator.initiateScan()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)


        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(this, "${intentResult.contents}", Toast.LENGTH_LONG).show()
                demo_dataset.setText(intentResult.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}