package com.pbt.myfarm.Adapter.SelectConfigType

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.ModelClass.ConfigTypeModel
import com.pbt.myfarm.R

class AdapterSelectconfigType(var context: Context,
                              var list: ArrayList<ConfigTypeModel>,
                              var callbacks: (Int,String) -> Unit,) :
    RecyclerView.Adapter<AdapterSelectconfigType.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var configtypeName: TextView = itemView.findViewById(R.id.txt_configTypeName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_config_type_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list[position]
        holder.configtypeName.text = item.CONGIFTYPE
holder.itemView.setOnClickListener{
    callbacks.invoke(position,item.CONGIFTYPE)
}

    }

    override fun getItemCount(): Int {
        return list.size
    }
}