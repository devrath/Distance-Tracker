package com.example.feat_gallery.presentation.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feat_gallery.presentation.adapter.GalleryAdapter
import com.example.feat_gallery.presentation.models.Image
import com.example.feat_gallery.utils.PermissionUtils
import com.example.feat_gallery.databinding.ActivityDemoOneBinding
import com.example.feat_gallery.presentation.vm.GalleryVm
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

private const val READ_EXTERNAL_STORAGE_REQUEST = 1
private const val DELETE_PERMISSION_REQUEST = 2

@AndroidEntryPoint
class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoOneBinding
    private val viewModel: GalleryVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initImageGridAdapter()
        setClickListeners()
        observeVm()
        if (!PermissionUtils.haveStoragePermission(this)) {
            binding.albumContainer.visibility = View.VISIBLE
        } else {
            showImages()
        }
    }

    private fun observeVm() {

        viewModel.permissionNeededForDelete.observe(this, Observer { intentSender ->
            /* startIntentSenderForResult() launches intentSender, which you passed to it.
             * DELETE_PERMISSION_REQUEST is a unique request code used to identify and handle the action when the request completes.
             * */
            intentSender?.let {
                val intentSenderRequest = IntentSenderRequest.Builder(it).build()
                phonePickIntentResultLauncher.launch(intentSenderRequest)
            }
        })
    }

    private fun setClickListeners() {
        binding.apply {
            openAlbumButton.setOnClickListener { openMediaStore() }
            grantPermissionButton.setOnClickListener { openMediaStore() }
        }
    }

    private fun initImageGridAdapter() {
        // Create a instance of adapter
        val galleryAdapter = GalleryAdapter { image ->
            // Pass the onclick action as lambda for deleting the image to the instance
            deleteImage(image)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImages()
                } else {
                    // If we weren't granted the permission, check to see if we should show
                    // rationale for the permission.
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    if (showRationale) {
                        showNoAccess()
                    } else {
                        goToSettings()
                    }
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == DELETE_PERMISSION_REQUEST) {
            viewModel.deletePendingImage()
        }
    }

    private fun showImages() {
        viewModel.loadImages()
        binding.apply {
            albumContainer.visibility = View.GONE
            permissionContainer.visibility = View.GONE
        }
    }

    private fun showNoAccess() {
        binding.apply {
            albumContainer.visibility = View.GONE
            permissionContainer.visibility = View.VISIBLE
        }
    }

    private fun openMediaStore() {
        if (PermissionUtils.haveStoragePermission(this)) {
            showImages()
        } else {
            requestPermission()
        }
    }

    private fun goToSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            startActivity(intent)
        }
    }





    private fun requestPermission() {
        if (!PermissionUtils.haveStoragePermission(this)) {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(
                // The reference of the Activity requesting permissions.
                this,
                // A string array of the required permissions.
                permissions,
                // The requestCode, which must be unique since onRequestPermissionsResult() uses this same code to handle various user actions.
                READ_EXTERNAL_STORAGE_REQUEST
            )
        }
    }

    /**
     * Dialog prompt to delete image
     */
    private fun deleteImage(image: Image) {
        val dialog = MaterialAlertDialogBuilder(this).apply {
            setTitle(com.istudio.core_ui.R.string.delete_dialog_title)
            setMessage(getString(com.istudio.core_ui.R.string.delete_dialog_message, image.displayName))
            setPositiveButton(com.istudio.core_ui.R.string.delete_dialog_positive) { _: DialogInterface, _: Int ->
                viewModel.deleteImage(image)
            }
            setNegativeButton(com.istudio.core_ui.R.string.delete_dialog_negative) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    private val phonePickIntentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result != null) {
                viewModel.deletePendingImage()
            }
        }
}