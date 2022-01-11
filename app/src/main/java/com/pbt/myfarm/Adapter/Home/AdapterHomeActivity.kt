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
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.ModelClass.EventList
import com.pbt.myfarm.R

class AdapterHomeActivity(var context: Context, var list:List<EventList>):
    RecyclerView.Adapter<AdapterHomeActivity.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_itemlist)
        val textView: TextView = itemView.findViewById(R.id.label_itemlist)
        val layout: ConstraintLayout = itemView.findViewById(R.id.layout_itemlist)
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
        }

    }

    override fun getItemCount(): Int {
return list.size
    }
}