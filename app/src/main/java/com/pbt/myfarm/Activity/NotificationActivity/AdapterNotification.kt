package com.pbt.myfarm.Activity.NotificationActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.ListNotificationData
import com.pbt.myfarm.R

import com.pbt.myfarm.databinding.ItemNotificationlistBinding


class AdapterNotification(var context: Context, var list: List<ListNotificationData>) :
    RecyclerView.Adapter<AdapterNotification.ViewHolder>() {

    inner class ViewHolder(val binding: ItemNotificationlistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_notificationlist, parent, false))


    override fun onBindViewHolder(holder: AdapterNotification.ViewHolder, position: Int) {
        holder.binding.model = list[position] }

    override fun getItemCount(): Int {
        return list.size
    }
}

