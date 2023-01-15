package com.example.feat_gallery.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.feat_gallery.presentation.models.Image
import com.example.feat_gallery.presentation.vh.ImageViewHolder
import com.example.feat_gallery.R

class GalleryAdapter(val onClick: (Image) -> Unit) :
    ListAdapter<Image, ImageViewHolder>(Image.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.image_layout, parent, false)
        return ImageViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.rootView.tag = image

        Glide.with(holder.imageView)
            .load(image.contentUri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.imageView)
    }
}