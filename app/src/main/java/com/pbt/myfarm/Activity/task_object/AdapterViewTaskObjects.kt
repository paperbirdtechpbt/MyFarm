package com.pbt.myfarm.Activity.task_object

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.databinding.ItemviewtaskobjectBinding

class AdapterViewTaskObjects(private val context: Context, val items: ArrayList<TaskObject>) :
    RecyclerView.Adapter<AdapterViewTaskObjects.ViewHolder>() {


    var editItemClick: ((TaskObject) -> Unit)? = null
    var deleteItemClick: ((TaskObject) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemviewtaskobjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: ItemviewtaskobjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskObject) {
            with(binding) {

                binding.taskObject = item

                binding.imgEdit.setOnClickListener {
                    editItemClick?.invoke(item)
                }
                binding.imgDelete.setOnClickListener {
                    deleteItemClick?.invoke(item)
                }

                binding.executePendingBindings()
            }
        }
    }


}