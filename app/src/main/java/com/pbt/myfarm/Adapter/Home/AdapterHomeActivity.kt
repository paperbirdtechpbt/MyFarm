package com.pbt.myfarm.Adapter.Home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.DashBoardEvent.DashBoardEventActivity
import com.pbt.myfarm.Activity.Event.ViewEventActivity
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeList
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.QrDemoActivity.QrCodeActivity
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.MyFarmService
import com.pbt.myfarm.Util.AppUtils

class AdapterHomeActivity(var context: Context, var list:List<EventList>):
    RecyclerView.Adapter<AdapterHomeActivity.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_itemlist)
        val textView: TextView = itemView.findViewById(R.id.label_itemlist)
        val layout: ConstraintLayout = itemView.findViewById(R.id.layout_itemlist_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlist_main, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.imageView.setImageResource(item.eventicon)
        holder.textView.text = item.evenetName
        holder.layout.setOnClickListener{
            if (item.evenetName=="Task"){
                context.startActivity(Intent(context, ViewTaskActivity::class.java))

            }
            else if(item.evenetName=="Pack"){

                context.startActivity(Intent(context, PackActivity::class.java))}
            else if(item.evenetName=="DashboardEvent"){
                context.startActivity(Intent(context, DashBoardEventActivity::class.java))
            }
            else if(item.evenetName=="Event"){
                context.startActivity(Intent(context, ViewEventActivity::class.java))
            }
            else if(item.evenetName=="QR Demo"){
                context.startActivity(Intent(context, QrCodeActivity::class.java))
            }

//            AppUtils().isServiceRunning(context, MyFarmService::class.java)
        }

    }

    override fun getItemCount(): Int {
return list.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
}