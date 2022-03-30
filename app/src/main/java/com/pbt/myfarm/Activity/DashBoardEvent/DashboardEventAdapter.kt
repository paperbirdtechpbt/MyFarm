package com.pbt.myfarm.Activity.DashBoardEvent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.Event.EditEventActivity
import com.pbt.myfarm.Activity.TaskFunctions.AdapterTaskFunction
import com.pbt.myfarm.R
import com.pbt.myfarm.EventList
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EDITEVENT_ID


class DashboardEventAdapter(var context: Context,var list: ArrayList<EventList>) : RecyclerView.Adapter<DashboardEventAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
 val eventname:TextView=itemView.findViewById(R.id.eventname)
 val expstartdate:TextView=itemView.findViewById(R.id.eventexpStartDate)
 val actualstartdate:TextView=itemView.findViewById(R.id.eventactStartDate)
 val editicon:ImageView=itemView.findViewById(R.id.img_edit_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list.get(position)
        if (item.name==null){
            holder.eventname.setText("*Event: ")

        }
        else{
            holder.eventname.setText("*Event: "+item.name)
        }
        if (item.exp_start_date==null){
            holder.expstartdate.setText("*Exp StartDate: ")

        }
        else{
            holder.expstartdate.setText("*Exp StartDate: "+item.exp_start_date)
        }
        if (item.actual_start_date==null){
            holder.actualstartdate.setText("*Actu StartDate: ")
        }
        else{
            holder.actualstartdate.setText("*Actu StartDate: "+item.actual_start_date)
        }
        holder.editicon.setOnClickListener{
            val intent=Intent(context,EditEventActivity::class.java)
            intent.putExtra(CONST_EDITEVENT_ID,item.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
