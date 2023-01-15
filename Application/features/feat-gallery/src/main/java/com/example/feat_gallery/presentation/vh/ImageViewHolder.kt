package com.example.feat_gallery.presentation.vh

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.feat_gallery.presentation.models.Image
import com.example.feat_gallery.R

class ImageViewHolder(view: View, onClick: (Image) -> Unit) :
    RecyclerView.ViewHolder(view) {
    val rootView = view
    val imageView: ImageView = view.findViewById(R.id.image)

    init {
        imageView.setOnClickListener {
            val image = rootView.tag as? Image ?: return@setOnClickListener
            onClick(image)
        }
    }
}
