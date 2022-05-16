package com.pbt.myfarm.Activity.ViewTaskObject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.R
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.databinding.ActivityViewTaskObjectBinding
import com.pbt.myfarm.databinding.ItemlistViewtaskBinding
import com.pbt.myfarm.databinding.ItemviewtaskobjectBinding

class AdapterViewTaskObjects(var context: Context, val list: List<TaskObject>)
    : RecyclerView.Adapter<AdapterViewTaskObjects.ViewHolder>()  {

    inner class ViewHolder(val binding: ItemviewtaskobjectBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.itemviewtaskobject, parent, false
            )
        )

    override fun onBindViewHolder(holder: AdapterViewTaskObjects.ViewHolder, position: Int) {
     holder.binding.viewmodel=list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
