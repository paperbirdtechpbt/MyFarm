package com.pbt.myfarm.Activity.CreateTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.ListNavigationTaskFunction
import com.pbt.myfarm.R
import com.pbt.myfarm.Task
import kotlinx.android.synthetic.main.item_tasknavlist.view.*

class NavigtionListAdatapter(var list: List<ListNavigationTaskFunction>,
                             var callbacks: (ListNavigationTaskFunction) -> Unit,
) :
    RecyclerView.Adapter<NavigtionListAdatapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListNavigationTaskFunction) {
            with(itemView) {
                img_navtask.setImageResource(item.img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tasknavlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.bind(list[position])
        holder.itemView.setOnClickListener{
                callbacks.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
