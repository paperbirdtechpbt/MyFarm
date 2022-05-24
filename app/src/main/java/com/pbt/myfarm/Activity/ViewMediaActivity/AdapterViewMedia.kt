package com.pbt.myfarm.Activity.ViewMediaActivity

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pbt.myfarm.Activity.TaskFunctions.ListFunctionFieldlist
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.itemlist_medialist.view.*
import java.io.File


class AdapterViewMedia(var context: Context, var list: List<ListFunctionFieldlist>) :
    RecyclerView.Adapter<AdapterViewMedia.ViewHolder>() {
    val TAG = "AdapterViewMedia"

    var onitemClick: ((ListFunctionFieldlist,String) -> Unit)? = null


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.img_media)
        val viewfile = itemView.findViewById<ImageView>(R.id.img_file)
        val viewvideo = itemView.findViewById<ImageView>(R.id.img_video)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlist_medialist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)

        val file = File(list.get(position).name)
        val type = AppUtils().checkExtension(file.extension)

        if (type == "image") {

            holder.itemView.icon_playbutton.visibility=View.GONE
            holder.imageView.visibility = View.VISIBLE
            hideViews(holder.viewfile, holder.viewvideo)

            Glide.with(context)
                .load(item.link).fitCenter()
                .placeholder(R.drawable.ic_loadingimage)
                .error(R.drawable.ic_loadingerror)
                .into(holder.imageView)

            holder.imageView?.setOnClickListener{
                onitemClick?.invoke(item,type)

            }


        } else if (type == "video") {

            holder.viewvideo.visibility = View.VISIBLE
            hideViews(holder.viewfile, holder.imageView)

            Glide
                .with(context)
                .asBitmap()
                .load(item.link)
                .into(holder.viewvideo)
            holder.itemView.icon_playbutton.visibility=View.VISIBLE

            holder.viewvideo?.setOnClickListener{
                onitemClick?.invoke(item,type)

            }


        } else if (type == "file") {



            holder.itemView.icon_playbutton.visibility=View.GONE
            holder.viewfile.visibility = View.VISIBLE
            hideViews(holder.imageView, holder.viewvideo)

            holder.viewfile?.setOnClickListener{
                onitemClick?.invoke(item,type)

            }
            val f = File(item.link)
            val extension=f.extension
          if (extension=="pdf"||extension=="PDF"){
              holder.viewfile.setImageResource(R.drawable.ic_pdficon)

          }
            if (extension=="txt"||extension=="TXT"){
                holder.viewfile.setImageResource(R.drawable.ic_txticon)

          }
            if (extension=="doc"||extension=="DOC" ||extension=="docx"||extension=="DOCX"){
                holder.viewfile.setImageResource(R.drawable.ic_doc_icon)

          }



        } else {
            holder.imageView.visibility = View.VISIBLE
            hideViews(holder.viewfile, holder.viewvideo)
        }

    }

    private fun hideViews(file: ImageView?, video: ImageView?) {
        file?.visibility = View.GONE
        video?.visibility = View.GONE
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
