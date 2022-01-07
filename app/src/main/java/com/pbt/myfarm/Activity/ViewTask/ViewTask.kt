package com.pbt.myfarm.Activity.ViewTask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_view_task.*

class ViewTask : AppCompatActivity() {
    val viewTaskModelClass = ArrayList<ViewTaskModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        actionBar?.setDisplayHomeAsUpEnabled(true)



        setdata()
        setadapter()
        recyclerview_viewtask.layoutManager = LinearLayoutManager(this)


btn_create_task.setOnClickListener{
    startActivity(Intent(this,CreateTaskActivity::class.java))
}
    }

    private fun setdata() {
        viewTaskModelClass.add(ViewTaskModelClass("TRP0001","TRANSPORTATION","Product transportation"))
        viewTaskModelClass.add(ViewTaskModelClass("TRP0002","TRANSPORTATION","Product transportation"))
        viewTaskModelClass.add(ViewTaskModelClass("DFERM0043","NETTOYAGE","Task"))
        viewTaskModelClass.add(ViewTaskModelClass("TRP0004","TRANSPORTATION","Product transportation"))
        viewTaskModelClass.add(ViewTaskModelClass("TRP0005","TRANSPORTATION","Product transportation"))
        viewTaskModelClass.add(ViewTaskModelClass("TRP0006","NETTOYAGE","Task"))
        viewTaskModelClass.add(ViewTaskModelClass("DFERM0043","NETTOYAGE","Task"))
        viewTaskModelClass.add(ViewTaskModelClass("TRP0012","FERMENTATION","alert for notification"))
    }

    private fun setadapter() {
        val adapter = AdapterViewTask(this,viewTaskModelClass)
        recyclerview_viewtask.adapter = adapter
    }
}