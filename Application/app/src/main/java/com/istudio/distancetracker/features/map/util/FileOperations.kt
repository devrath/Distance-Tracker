package com.istudio.distancetracker.features.map.util

import android.app.RecoverableSecurityException
import android.content.*
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.istudio.distancetracker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


private const val TAG = "FileOperations"
private const val QUALITY = 100

object FileOperations {

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun saveImage(context: Context, bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val collection =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

            val dirDest = File(Environment.DIRECTORY_PICTURES, context.getString(R.string.app_name))
            val date = System.currentTimeMillis()
            val extension = Bitmap.CompressFormat.PNG

            val newImage = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$date.$extension")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/$extension")
                put(MediaStore.MediaColumns.DATE_ADDED, date)
                put(MediaStore.MediaColumns.DATE_MODIFIED, date)
                put(MediaStore.MediaColumns.SIZE, bitmap.byteCount)
                put(MediaStore.MediaColumns.WIDTH, bitmap.width)
                put(MediaStore.MediaColumns.HEIGHT, bitmap.height)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "$dirDest${File.separator}")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val newImageUri = context.contentResolver.insert(collection, newImage)

            context.contentResolver.openOutputStream(newImageUri!!, "w").use {
                bitmap.compress(extension, QUALITY, it)
            }

            newImage.clear()
            newImage.put(MediaStore.Images.Media.IS_PENDING, 0)
            context.contentResolver.update(newImageUri, newImage, null, null)
        }
    }

    fun getPhotoDirFromAppStorage(dirName: String,context:Context): File? {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), dirName)
        if (!file.mkdirs()) {
            Log.e("TEST", "Directory not created")
        }
        return file
    }


}