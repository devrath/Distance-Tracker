package com.example.feat_gallery.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feat_gallery.databinding.ActivityDemoOneBinding
import com.example.feat_gallery.presentation.adapter.GalleryAdapter
import com.example.feat_gallery.presentation.vm.GalleryVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoOneBinding
    private val viewModel: GalleryVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initImageGridAdapter()
        showImages()
    }

    private fun initImageGridAdapter() {
        // Create a instance of adapter
        val galleryAdapter = GalleryAdapter { image ->
            // Pass the onclick action as lambda for deleting the image to the instance
            //vdeleteImage(image)
        }
        // Set the properties for the grid layout
        binding.imageGallery.also { view ->
            view.layoutManager = GridLayoutManager(this, 3)
            view.adapter = galleryAdapter
        }
        // Observe the changes for the image collection loaded in grid view for changes
        viewModel.images.observe(this, Observer { images ->
            galleryAdapter.submitList(images)
        })
    }

    private fun showImages() {
        viewModel.loadImages(this)
    }
}