package com.pbt.myfarm.Activity.Event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Event
import com.pbt.myfarm.EventList
import com.pbt.myfarm.R
import com.pbt.myfarm.ResponseEventList
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistVieweventBinding
import kotlinx.android.synthetic.main.activity_view_event.*
import kotlinx.android.synthetic.main.itemlist_viewevent.view.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class AdapterEventList(var context: Context, var list: List<Event>,
                       var callbacks:(String,Int,Boolean)->Unit)
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

                if (AppUtils().isInternet(context)){
                    if (MainActivity.privilegeListName.contains("EditEvent")){
                        holder.binding.iconEditEvent.visibility=View.VISIBLE
                    }
                    if (MainActivity.privilegeListName.contains("DeleteEvent")){
                        holder.binding.iconDeleteEvent.visibility=View.VISIBLE
                    }

                }
                else{
                    if (MainActivity.privilegeListNameOffline.contains("EditEvent")){
                        holder.binding.iconEditEvent.visibility=View.VISIBLE
                    }
                    if (MainActivity.privilegeListNameOffline.contains("DeleteEvent")){
                        holder.binding.iconDeleteEvent.visibility=View.VISIBLE
                    }

                }

                holder.itemView.icon_delete_event.setOnClickListener {

                   callbacks.invoke(item.id!!.toString(),position,true)
                }
                holder.itemView.icon_edit_event.setOnClickListener {

                    callbacks.invoke(item.id!!.toString(),position,false)


                }


            }


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
