package com.example.feat_gallery.presentation.models

import android.graphics.Bitmap
import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.Date

data class InternalStoragePhoto(
    val id: Int, val name: String, val bmp: Bitmap
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<InternalStoragePhoto>() {
            override fun areItemsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto) =
                oldItem == newItem
        }
    }
}