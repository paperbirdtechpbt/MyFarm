package com.pbt.myfarm.Activity.CreateTask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pbt.myfarm.R

class CreateTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle("Create Task")


    }
}