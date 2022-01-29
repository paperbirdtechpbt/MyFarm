package com.pbt.myfarm.Activity.Pack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Adapter.ViewTask.AdapterViewTask
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.R

class AdapterViewPack(var context: Context, var list:ArrayList<ViewPackModelClass>,
                      var callbacks: (Int, String, Boolean, ViewPackModelClass) -> Unit,)
    : RecyclerView.Adapter<AdapterViewPack.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.itemlist_pack, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(list.get(position))

        holder.deleteicon.setOnClickListener {
            callbacks.invoke(position, item.packname,true,item)



        }
        holder.editicon.setOnClickListener {

            callbacks.invoke(position, item.packname,false,item)

            Toast.makeText(context, "Edit---${item.packname}", Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var entryname: TextView = itemView.findViewById(R.id.txt_packname)
        var entrytype: TextView = itemView.findViewById(R.id.txt_packtype)
        var entrydetail: TextView = itemView.findViewById(R.id.txt_packdetails)
        var deleteicon: ImageView = itemView.findViewById(R.id.icon_delete)
        var editicon: ImageView = itemView.findViewById(R.id.icon_edit)
        fun bind(blog: ViewPackModelClass) {
//            entryname.text = blog.id
            entryname.text=blog.packname
            entrytype.text = blog.packType
            entrydetail.text = blog.packdesciption
        }
    }}


