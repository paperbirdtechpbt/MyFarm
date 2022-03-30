package com.pbt.myfarm.Activity.PackConfigList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.HttpResponse.Packconfig
import com.pbt.myfarm.PackConfig
import com.pbt.myfarm.PackConfigList

import com.pbt.myfarm.R
import com.pbt.myfarm.databinding.ItemPackconfiglistBinding

class AdapterPackSelectconfigType(var context: Context,
                                  var list: List<PackConfig>,
                                  var callbacks: (Int, String, PackConfig) -> Unit) :
    RecyclerView.Adapter<AdapterPackSelectconfigType.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPackconfiglistBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder=
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_packconfiglist, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.configtype = list[position]
        val item = list[position]
        holder.itemView.setOnClickListener {
            callbacks.invoke(position, item.name!!,item)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }


}
