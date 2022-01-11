package com.pbt.myfarm.Adapter.ViewTask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R

class AdapterViewTask(
    var context: Context, var list: List<ViewTaskModelClass>,
    var callbacks: (Int, String,Boolean,ViewTaskModelClass) -> Unit,
) : RecyclerView.Adapter<AdapterViewTask.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.itemlist_viewtask, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(list.get(position))

        holder.deleteicon.setOnClickListener {
            callbacks.invoke(position, item.ENTRYNAME,true,item)



        }
        holder.editicon.setOnClickListener {

            callbacks.invoke(position, item.ENTRYNAME,false,item)

            Toast.makeText(context, "Edit---${item.ENTRYNAME}", Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var entryname: TextView = itemView.findViewById(R.id.txt_entryname)
        var entrytype: TextView = itemView.findViewById(R.id.txt_type)
        var entrydetail: TextView = itemView.findViewById(R.id.txt_details)
        var deleteicon: ImageView = itemView.findViewById(R.id.icon_delete)
        var editicon: ImageView = itemView.findViewById(R.id.icon_edit)
        fun bind(blog: ViewTaskModelClass) {
            entryname.text = blog.ENTRYNAME
            entrytype.text = blog.ENTRYTYPE
            entrydetail.text = blog.ENTRYDETAIL
        }
    }

}