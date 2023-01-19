package com.istudio.distancetracker.features.map.util

import android.app.RecoverableSecurityException
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.feat_gallery.presentation.models.InternalStoragePhoto
import com.istudio.distancetracker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID


private const val TAG = "FileOperations"
private const val QUALITY = 100

object FileOperations {

    fun savePhotoToInternalStorage(context: Context,bitmap:Bitmap) : Boolean{
        return try {
            val fileName =UUID.randomUUID().toString()
            context.openFileOutput("$fileName.jpg", MODE_PRIVATE).use { stream ->
                if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        }catch (ex:Exception){
            ex.printStackTrace()
            false
        }
    }

}