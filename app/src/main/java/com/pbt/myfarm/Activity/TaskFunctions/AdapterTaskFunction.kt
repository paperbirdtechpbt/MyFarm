package com.pbt.myfarm.Activity.TaskFunctions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.CreateTask.CreateTaskAdapter
import com.pbt.myfarm.R

class AdapterTaskFunction(var context: Context,var function: ArrayList<ListFunctionFieldlist>,
var callbacks:(String,String) ->Unit)
    : RecyclerView.Adapter<AdapterTaskFunction.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var link: TextView = itemView.findViewById(R.id.functionlink)
        var img_downlod: ImageView = itemView.findViewById(R.id.img_download)
//        link.setMovementMethod(LinkMovementMethod.getInstance());
//        link.setLinkTextColor(Color.BLUE)





    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_viewmedia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= function.get(position)
        holder.link.setText(item.name)
        holder.img_downlod.setOnClickListener{
           callbacks.invoke(item.name,item.link)

        }
    }

    override fun getItemCount(): Int {
        return function.size
    }

}
