package com.pbt.myfarm.Activity.Event

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.ResponseEventList
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistVieweventBinding
import kotlinx.android.synthetic.main.itemlist_viewevent.view.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class AdapterEventList(var context: Context, var list: List<EventList>,var callbacks:(String,Int,Boolean)->Unit)
    :RecyclerView.Adapter<AdapterEventList.ViewHolder>(),retrofit2.Callback <ResponseEventList>{
    val TAG="AdapterEventList"

    inner class ViewHolder(val binding: ItemlistVieweventBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
 =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.itemlist_viewevent, parent, false))


    override fun onBindViewHolder(holder: AdapterEventList.ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                holder.binding.viewevent = list[position]
                val item = list[position]

                holder.itemView.icon_delete_event.setOnClickListener {

                   callbacks.invoke(item.id,position,true)
                }
                holder.itemView.icon_edit_event.setOnClickListener {

                    callbacks.invoke(item.id,position,false)


                }}


        }

    }


    override fun getItemCount(): Int {
return list.size
    }

    override fun onResponse(call: Call<ResponseEventList>, response: Response<ResponseEventList>) {
        if (response.body()?.error==false){
            Toast.makeText(context, response.body()!!.msg, Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onFailure(call: Call<ResponseEventList>, t: Throwable) {
        try {
           AppUtils.logError(TAG,t.message.toString())
        }
        catch (e:Exception){
            AppUtils.logError(TAG,e.toString())

        }
    }

}
