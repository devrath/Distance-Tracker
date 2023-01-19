package com.example.feat_gallery.presentation.vm

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.app.RecoverableSecurityException
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.IntentSender
import android.database.ContentObserver
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.feat_gallery.presentation.models.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class GalleryVm(application: Application) : AndroidViewModel(application) {
    private val _images = MutableLiveData<List<InternalStoragePhoto>>()
    val images: LiveData<List<InternalStoragePhoto>> get() = _images

    fun loadImages(context:Context) {
        viewModelScope.launch {
            val imageList = loadPhotosFromInternalStorage(context)
            _images.postValue(imageList)
        }
    }

    private suspend fun loadPhotosFromInternalStorage(context: Context): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            var count = 0
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                count++
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(count,it.name, bmp)
            } ?: listOf()
        }
    }
}