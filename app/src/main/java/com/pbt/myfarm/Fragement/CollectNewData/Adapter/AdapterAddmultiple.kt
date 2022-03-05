package com.pbt.myfarm.Fragement.CollectNewData.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.CreateTask.CreateTaskAdapter
import com.pbt.myfarm.Activity.Login.LoginActivity
import com.pbt.myfarm.Fragement.CollectNewData.Model.ListMultipleCollcetdata
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.MySharedPreference

class AdapterAddmultiple(var context: Context, var list: ArrayList<ListMultipleCollcetdata>) :
    RecyclerView.Adapter<AdapterAddmultiple.ViewHolder>() {
    class ViewHolder(var Itemviwe: View) : RecyclerView.ViewHolder(Itemviwe) {
        val activity: TextView = itemView.findViewById(R.id.list_activityname)
        val result: TextView = itemView.findViewById(R.id.list_activityresult)
        val unit: TextView = itemView.findViewById(R.id.list_activityUnits)
        val value: TextView = itemView.findViewById(R.id.list_activityvalue)
        val sensor: TextView = itemView.findViewById(R.id.list_activitysensor)
        val duration: TextView = itemView.findViewById(R.id.list_activityduration)
        val btndelte: ImageView = itemView.findViewById(R.id.list_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_multiple_collectdata, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item=list[position]
        holder.activity.setText("* Activity: "+item.activityname)
        holder.result.setText("* Result: "+item.resultname)
        holder.duration.setText("* Duration: "+item.duration)
        holder.value.setText("* Value: "+item.valuename)
        holder.sensor.setText("* Sensor: "+item.sensorname)
        holder.unit.setText("* Unit: "+item.unitname)
        holder.btndelte.setOnClickListener{
            showAlertDialog(position)

        }
    }

    private fun showAlertDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Delte")
            .setMessage("Are you sure you want to Delete")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    list.removeAt(position)
                    notifyDataSetChanged()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
