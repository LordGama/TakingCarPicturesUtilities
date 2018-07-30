package com.lordgama.carpicturesutilities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gallery_item.view.*


class VehicleGalleryAdapter(var photos: MutableList<VehiclePhoto>) : RecyclerView.Adapter<VehicleGalleryAdapter.GalleryItemVH>(){
    var onEditItemManager: OnEditClickListener? = null
        set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemVH {
        return GalleryItemVH(LayoutInflater.from(parent.context)
                .inflate(R.layout.gallery_item,parent,false))
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: GalleryItemVH, position: Int) {
        holder.bind(photos.get(position))
    }

    inner class GalleryItemVH(itemView: View?): RecyclerView.ViewHolder(itemView){
        fun bind(photo: VehiclePhoto){
            itemView?.text_view_gallery_item?.text = "${photo.type.name}"

            Picasso.with(itemView.image_edit_gallery_item.context).load("file:///"+photo.photoUrlString).centerCrop().resize(104.fromDps(itemView.image_edit_gallery_item.context),104.fromDps(itemView.image_edit_gallery_item.context)).placeholder(R.drawable.loading_image).error(R.drawable.image_placeholder).into(itemView.image_gallery_item)

            itemView?.image_edit_gallery_item?.setOnClickListener {
                onEditItemManager?.onClickEdit(photo)
            }
        }
    }

    interface OnEditClickListener{
        fun onClickEdit(photo: VehiclePhoto)
    }



}