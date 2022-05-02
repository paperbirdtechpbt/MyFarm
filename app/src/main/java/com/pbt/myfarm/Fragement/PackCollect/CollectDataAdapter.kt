package com.pbt.myfarm.Fragement.PackCollect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Adapter.Home.AdapterHomeActivity
import com.pbt.myfarm.CollectData
import com.pbt.myfarm.CollectDataFieldListItem
import com.pbt.myfarm.Fragement.CollectNewData.CreateNewCollectDataFragment
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistCollectdataBinding
import kotlinx.android.synthetic.main.itemlist_collectdata.view.*
import java.text.SimpleDateFormat


class CollectDataAdapter(private val list: List<CollectData>, var context: Context,
                         var callbacks: (String,Boolean,String,Int) -> Unit) :
    RecyclerView.Adapter<CollectDataAdapter.ViewHolder>() {
    val TAG="CollectDataAdapter"

//    inner class ViewHolder(val binding: ItemlistCollectdataBinding): RecyclerView.ViewHolder(binding.root)

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val txt_valueunitstest:TextView=itemView.findViewById(R.id.txt_valueunitstest)
        val txt_collectactivity_USertest:TextView=itemView.findViewById(R.id.txt_collectactivity_USertest)
        val txt_collected_datetest:TextView=itemView.findViewById(R.id.txt_collected_datetest)
        val txt_sensorstest:TextView=itemView.findViewById(R.id.txt_sensorstest)
        val txt_durationtest :TextView=itemView.findViewById(R.id.txt_durationtest)
        val txt_valuetest:TextView=itemView.findViewById(R.id.txt_valuetest)
        val txt_resultnametest:TextView=itemView.findViewById(R.id.txt_resultnametest)
        val txt_collectactivitytest:TextView=itemView.findViewById(R.id.txt_collectactivitytest)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlist_collectdata, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//           holder.binding.collectdata = list[position]


        if (MainActivity.privilegeListName.contains("DeleteCollectData")){
            holder.itemView.icon_deletetest.visibility=View.VISIBLE
        }

        if (MainActivity.privilegeListName.contains("EditCollectData")){
            holder.itemView.icon_edittest.visibility=View.VISIBLE

        }
        if (AppUtils().isInternet(context)){
            //
            val getrow: Any = this.list.get(position)
            val t: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>

            val id = t["id"].toString()
            val value = t["value"].toString()
            val serverid = t["serverid"].toString()
            val unit_value = t["unit_value"].toString()
            val collect_activity = t["collect_activity"].toString()
            val result_name = t["result_name"].toString()
            val sensor = t["sensor"].toString()
            val user_collecting = t["user_collecting"].toString()
            val datetime_collected = t["datetime_collected"].toString()
            val duration = t["duration"].toString()


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
                callbacks.invoke(id,false,serverid,position)
                Toast.makeText(context, "Editing $result_name", Toast.LENGTH_SHORT).show()
            }
            holder.itemView.icon_deletetest.setOnClickListener{
                callbacks.invoke(id,true,serverid,position)
            }

        }
        else{

            val getrow: Any = this.list.get(position)
            val t: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>
            val id = t["id"].toString()
            val duration = t["duration"].toString()
            var created_at = t["created_at"].toString()
            val collect_activity_id = t["collect_activity_id"].toString()
            val created_by = t["created_by"].toString()
            val deleted_at = t["deleted_at"].toString()
            val deleted_by = t["deleted_by"].toString()
            val new_value = t["new_value"].toString()
            val pack_id = t["pack_id"].toString()
            val serverid = t["serverid"].toString()
            val result_class = t["result_class"].toString()
            val result_id = t["result_id"].toString()
            val sensor_id = t["sensor_id"].toString()
            val unit_id = t["unit_id"].toString()
            val updated_at = t["updated_at"].toString()
            val updated_by = t["updated_by"].toString()
            val value = t["value"].toString()
            val collectactivity_name = t["collectactivity_name"].toString()
            val resultname = t["resultname"].toString()
            val unitname = t["unitname"].toString()
            val date = t["date"].toString()
            val sensorname = t["sensorname"].toString()



            val expand = t["expand"].toString().toBoolean()


            holder.txt_valueunitstest.setText("* Unit: "+unitname)
            holder.txt_collected_datetest.setText("* Date: "+date)
            holder.txt_sensorstest.setText("* Sensors: "+sensorname)
            holder.txt_durationtest.setText("* Duration: "+duration)
            holder.txt_valuetest.setText("* Value: "+value)
            holder.txt_resultnametest.setText("* Result: "+resultname)
            holder.txt_collectactivitytest.setText(collectactivity_name)

            holder.itemView.icon_edittest.setOnClickListener{
                callbacks.invoke(id,false,serverid,position)
                Toast.makeText(context, "Editing $resultname", Toast.LENGTH_SHORT).show()
            }
            holder.itemView.icon_deletetest.setOnClickListener{
                callbacks.invoke(id,true,serverid,position)
            }
        }

//        holder.txt_collectactivitytest.setText(collect_activity_id)


    }

    override fun getItemCount(): Int {
        return list.size }
    }
