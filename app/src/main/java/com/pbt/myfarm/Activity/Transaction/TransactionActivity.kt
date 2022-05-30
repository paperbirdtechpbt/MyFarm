package com.pbt.myfarm.Activity.Transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.pbt.myfarm.R

class TransactionActivity : AppCompatActivity() {
    var viewModel: ViewModelTransactionActivity? = null

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelTransactionActivity::class.java)
    }

}