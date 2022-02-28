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


class CollectDataAdapter(private val list: List<CollectDataFieldListItem>, var context: Context) :
    RecyclerView.Adapter<CollectDataAdapter.ViewHolder>() {
    val TAG="CollectDataAdapter"

    inner class ViewHolder(val binding: ItemlistCollectdataBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.itemlist_collectdata, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        AppUtils.logDebug(TAG,"list value->"+list.toString())
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


        holder.itemView.txt_valueunits.setText(unit_value)
        holder.itemView.txt_collectactivity_USer.setText(user_collecting)
        holder.itemView.txt_collected_date.setText(datetime_collected)
        holder.itemView.txt_sensors.setText(sensor)
        holder.itemView.txt_duration.setText(duration)
        holder.itemView.txt_value.setText(value)
        holder.itemView.txt_resultname.setText(result_name)
        holder.itemView.txt_collectactivity.setText(collect_activity)
        holder.itemView.txt_valueunits.setText(unit_value)



     holder.itemView.icon_edit.setOnClickListener{
       Toast.makeText(context, "Editing $result_name", Toast.LENGTH_SHORT).show()
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
