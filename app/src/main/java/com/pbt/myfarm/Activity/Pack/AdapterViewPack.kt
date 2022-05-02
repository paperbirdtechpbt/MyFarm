package com.pbt.myfarm.Activity.Pack

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.PacksNew
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ItemlistPackBinding
import kotlinx.android.synthetic.main.activity_view_task.*
import kotlinx.android.synthetic.main.itemlist_pack.view.*


class AdapterViewPack(
    var context: Context, var list: List<PacksNew>,
    var callbacks: (Int, String, Boolean, PacksNew) -> Unit,
)
    : RecyclerView.Adapter<AdapterViewPack.ViewHolder>() {
    val TAG="AdapterViewPack"
    inner class ViewHolder(val binding: ItemlistPackBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.itemlist_pack, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
//                binding.exapanedviewpack.visibility = if (this.expand) View.VISIBLE else View.GONE
//
//
//                binding.layoutItemlist.setOnClickListener {
//                    this.expand = !this.expand
//
//                    notifyItemChanged(position)
//
//                }

                holder.binding.viewpack = list[position]
        val item = list[position]
                if (privilegeListName.contains("DeletePack")){
                    AppUtils.logDebug(TAG,"contaicns Delete PAkc")
//                    holder.itemView.icon_delete.visibility= View.VISIBLE
                    holder.binding.iconDelete.visibility=View.VISIBLE
                }
                    if (privilegeListName.contains("EditPack")){
                        AppUtils.logDebug(TAG,"contaicns EditPack PAkc")
                        holder.binding.iconEdit.visibility=View.VISIBLE

//                        holder.itemView.icon_edit.visibility= View.VISIBLE
                }

        holder.itemView.icon_delete.setOnClickListener {

            callbacks.invoke(position, item.name.toString(), true, item)
        }
                holder.itemView.icon_edit.setOnClickListener {

                    callbacks.invoke(position, item.name.toString(),false,item)


                }}


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }}




