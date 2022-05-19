package com.pbt.myfarm.Adapter.ViewTask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListNameOffline
import com.pbt.myfarm.R
import com.pbt.myfarm.Task
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistViewtaskBinding
import kotlinx.android.synthetic.main.itemlist_viewtask.view.*

class AdapterViewTask(
    var context: Context, var list: List<Task>,
    var callbacks: (Int, String, Boolean, Task) -> Unit,
) : RecyclerView.Adapter<AdapterViewTask.ViewHolder>() {
    inner class ViewHolder(val binding: ItemlistViewtaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.itemlist_viewtask, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.viewtask = list[position]

                val item = list[position]
                if (AppUtils().isInternet(context)) {
                    if (privilegeListName.contains("EditTask")) {
                        holder.binding.iconEdit.visibility = View.VISIBLE
                    }
                    if (privilegeListName.contains("DeleteTask")) {
                        holder.binding.iconDelete.visibility = View.VISIBLE

                    }
                } else {
                    if (privilegeListNameOffline.contains("EditTask")) {
                        holder.binding.iconEdit.visibility = View.VISIBLE
                    }
                    if (privilegeListNameOffline.contains("DeleteTask")) {
                        holder.binding.iconDelete.visibility = View.VISIBLE

                    }
                }

                itemView.icon_delete.setOnClickListener {
                    callbacks.invoke(position, name.toString(), true, item)
                }
                itemView.icon_edit.setOnClickListener {
                    callbacks.invoke(position, item.name!!, false, item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}