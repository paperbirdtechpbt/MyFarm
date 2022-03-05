package com.pbt.myfarm.Fragement.PackCollect

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.CollectDataFieldListItem
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistCollectdataBinding
import kotlinx.android.synthetic.main.itemlist_collectdata.view.*


class CollectDataAdapter(private val list: List<CollectDataFieldListItem>, var context: Context,
var callbacks:(String,Boolean) -> Unit) :
    RecyclerView.Adapter<CollectDataAdapter.ViewHolder>() {
    val TAG="CollectDataAdapter"

    inner class ViewHolder(val binding: ItemlistCollectdataBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.itemlist_collectdata, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


//           holder.binding.collectdata = list[position]

        val getrow: Any = this.list.get(position)
        val t: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>
        val id = t["id"].toString()
        val value = t["value"].toString()
        val unit_value = t["unit_value"].toString()
        val collect_activity = t["collect_activity"].toString()
        val result_name = t["result_name"].toString()
        val sensor = t["sensor"].toString()
        val user_collecting = t["user_collecting"].toString()
        val datetime_collected = t["datetime_collected"].toString()
        val duration = t["duration"].toString()
        val expand = t["expand"].toString().toBoolean()


        holder.itemView.txt_valueunitstest.setText("* Unit: "+unit_value)
        holder.itemView.txt_collectactivity_USertest.setText("* Collecter: "+user_collecting)
        holder.itemView.txt_collected_datetest.setText("* Date: "+datetime_collected)
        holder.itemView.txt_sensorstest.setText("* Sensors: "+sensor)
        holder.itemView.txt_durationtest.setText("* Duration: "+duration)
        holder.itemView.txt_valuetest.setText("* Value: "+value)
        holder.itemView.txt_resultnametest.setText("* Result: "+result_name)
        holder.itemView.txt_collectactivitytest.setText(collect_activity)
        holder.itemView.txt_valueunitstest.setText("* Unit: "+unit_value)



     holder.itemView.icon_edittest.setOnClickListener{
         callbacks.invoke(id,false)
       Toast.makeText(context, "Editing $result_name", Toast.LENGTH_SHORT).show()
   }
        holder.itemView.icon_deletetest.setOnClickListener{
         callbacks.invoke(id,true)
   }



//        holder.binding.exapanedview.visibility = if (expand) View.VISIBLE else View.GONE
//        holder.binding.layoutConst.setOnClickListener {
//            this.list.get(position).expand = !this.list.get(position).expand
//
//            notifyItemChanged(position)
//
//        }
    }

    override fun getItemCount(): Int {
        return list.size }
    }
