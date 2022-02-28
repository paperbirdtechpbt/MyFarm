package com.pbt.myfarm.Adapter.SelectConfigType

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.ConfigTaskList
import com.pbt.myfarm.R
import com.pbt.myfarm.databinding.ItemConfigTypeListBindingImpl
import com.pbt.myfarm.databinding.ItemlistViewtaskBinding

class AdapterSelectconfigType(var context: Context,
                              var list: List<ConfigTaskList>,
                              var callbacks: (Int, String, ConfigTaskList) -> Unit) :
    RecyclerView.Adapter<AdapterSelectconfigType.ViewHolder>() {
    inner class ViewHolder(val binding: ItemConfigTypeListBindingImpl): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_config_type_list, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.configtype = list[position]
        var item = list[position]
holder.itemView.setOnClickListener{
    callbacks.invoke(position,item.name,item)
}

    }

    override fun getItemCount(): Int {
        return list.size
    }
}