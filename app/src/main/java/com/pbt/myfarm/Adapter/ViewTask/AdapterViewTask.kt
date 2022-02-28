package com.pbt.myfarm.Adapter.ViewTask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.TasklistDataModel
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistViewtaskBinding
import kotlinx.android.synthetic.main.itemlist_viewtask.view.*

class AdapterViewTask(
    var context: Context, var list: List<TasklistDataModel>,
    var callbacks: (Int, String, Boolean, TasklistDataModel) -> Unit,
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
                binding.exapanedview.visibility = if (this.expand) View.VISIBLE else View.GONE

AppUtils.logDebug("##Expand","Expand1"+this.expand.toString())

                binding.layoutItemlist.setOnClickListener {
                    this.expand = !this.expand

                    notifyItemChanged(position)

                    AppUtils.logDebug("##Expand","Expand2"+this.expand.toString())
                }
                itemView.icon_delete.setOnClickListener {
                    callbacks.invoke(position, item.name, true, item)

                }
                itemView.icon_edit.setOnClickListener {

                    callbacks.invoke(position, item.name, false, item)

                    Toast.makeText(context, "Edit---${item.name}", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
        AppUtils.logDebug("##listsize", list.size.toString())
    }


}