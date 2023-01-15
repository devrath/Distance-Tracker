package com.example.feat_gallery.presentation.vm

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.app.RecoverableSecurityException
import android.content.ContentResolver
import android.content.ContentUris
import android.content.IntentSender
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.feat_gallery.presentation.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class GalleryVm(application: Application) : AndroidViewModel(application) {
    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> get() = _images

    private var contentObserver: ContentObserver? = null

    private var pendingDeleteImage: Image? = null
    private val _permissionNeededForDelete = MutableLiveData<IntentSender?>()
    val permissionNeededForDelete: LiveData<IntentSender?> = _permissionNeededForDelete


    fun loadImages() {
        viewModelScope.launch {
            val imageList = queryImages()
            _images.postValue(imageList)

            if (contentObserver == null) {
                /**
                 * Listening for Changes With ContentObserver
                 * *
                 * ContentObserver is a class that listens for changes whenever the data in the content provider changes.
                 * Since data will change whenever you delete any image in the app, you need to use a ContentObserver
                 */
                contentObserver = getApplication<Application>().contentResolver.registerObserver(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ) {
                    loadImages()
                }
            }
        }
    }

    fun deleteImage(image: Image) {
        viewModelScope.launch {
            performDeleteImage(image)
        }
    }

    fun deletePendingImage() {
        pendingDeleteImage?.let { image ->
            pendingDeleteImage = null
            deleteImage(image)
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private suspend fun queryImages(): List<Image> {
        var imageList = mutableListOf<Image>()

        withContext(Dispatchers.IO) {
            /**
             * An array that contains all the information you need.
             * It’s similar to the SELECT clause of an SQL statement.
             */
            /**
             * An array that contains all the information you need.
             * It’s similar to the SELECT clause of an SQL statement.
             */
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
            )

            /**
             * Similar to the WHERE clause in SQL, this lets you specify any condition.
             * The ? in the statement is a placeholder that will get its value from selectionArgs
             */
            /**
             * Similar to the WHERE clause in SQL, this lets you specify any condition.
             * The ? in the statement is a placeholder that will get its value from selectionArgs
             */
            val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
            // 3
            val selectionArgs = arrayOf(
                dateToTimestamp(day = 1, month = 1, year = 2020).toString()
            )
            // Order of sorting
            val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
            /**
             * ContentResolver that takes in all the above as parameters as well as an additional
             * Uri parameter that maps to the required table in the provider.
             * *
             * In this case, the required Uri is EXTERNAL_CONTENT_URI since you are requesting images from outside the app.
             * Uri is always mandatory. Hence, it is a non-nullable parameter while the rest of the parameters are nullable.
             */
            /**
             * ContentResolver that takes in all the above as parameters as well as an additional
             * Uri parameter that maps to the required table in the provider.
             * *
             * In this case, the required Uri is EXTERNAL_CONTENT_URI since you are requesting images from outside the app.
             * Uri is always mandatory. Hence, it is a non-nullable parameter while the rest of the parameters are nullable.
             */
            getApplication<Application>().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                imageList = addImagesFromCursor(cursor)
            }
        }

        return imageList
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun addImagesFromCursor(cursor: Cursor): MutableList<Image> {
        val images = mutableListOf<Image>()

        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
        val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (cursor.moveToNext()) {

            val id = cursor.getLong(idColumn)
            val dateTaken = Date(cursor.getLong(dateTakenColumn))
            val displayName = cursor.getString(displayNameColumn)

            val contentUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )

            val image = Image(id, displayName, dateTaken, contentUri)
            images += image
        }
        return images
    }

    private suspend fun performDeleteImage(image: Image) {
        withContext(Dispatchers.IO) {
            // Here, you call contentResolver.delete() inside a try block since this method can throw a SecurityException at runtime.
            try {
                /**
                 * URI:-> Content URI to be deleted
                 * WHERE:-> You specify that you want to delete an image based on its _ID
                 * LAST PARAM:-> You pass the string version of the _ID in an array
                 */
                /**
                 * URI:-> Content URI to be deleted
                 * WHERE:-> You specify that you want to delete an image based on its _ID
                 * LAST PARAM:-> You pass the string version of the _ID in an array
                 */
                getApplication<Application>().contentResolver.delete(
                    image.contentUri, "${MediaStore.Images.Media._ID} = ?",
                    arrayOf(image.id.toString())
                )
            }
            // 2
            catch (securityException: SecurityException) {
                /**
                 * Why catching a exception is needed:-> In Android 10 and above, it isn’t possible to delete or modify items from MediaStore directly.
                 * You need permission for these actions. The correct approach is to first catch RecoverableSecurityException, which contains an intentSender that can prompt the user to grant permission.
                 * You pass intentSender to the activity by calling postValue() on your MutableLiveData
                 */
                /**
                 * Why catching a exception is needed:-> In Android 10 and above, it isn’t possible to delete or modify items from MediaStore directly.
                 * You need permission for these actions. The correct approach is to first catch RecoverableSecurityException, which contains an intentSender that can prompt the user to grant permission.
                 * You pass intentSender to the activity by calling postValue() on your MutableLiveData
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val recoverableSecurityException =
                        securityException as? RecoverableSecurityException
                            ?: throw securityException
                    pendingDeleteImage = image
                    _permissionNeededForDelete.postValue(
                        recoverableSecurityException.userAction.actionIntent.intentSender
                    )
                } else {
                    throw securityException
                }
            }
        }
    }

  /**
   *  It is a utility function that accepts a day, a month and a year.
   *  It returns the corresponding timestamp value, which selectionArgs requires.
   */
  @Suppress("SameParameterValue")
    @SuppressLint("SimpleDateFormat")
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            formatter.parse("$day.$month.$year")?.time ?: 0
        }


    override fun onCleared() {
        contentObserver?.let {
            getApplication<Application>().contentResolver.unregisterContentObserver(it)
        }
    }
}

/**
 * Extension method to register a [ContentObserver]
 */
private fun ContentResolver.registerObserver(
    uri: Uri,
    observer: (selfChange: Boolean) -> Unit
): ContentObserver {
    val contentObserver = object : ContentObserver(Handler()) {
        // ContentObserver overrides onChange()
        // loadImages() passed as a lambda. A best practice is to always use a Handler() when creating ContentObserver.
        override fun onChange(selfChange: Boolean) {
            observer(selfChange)
        }
    }
    registerContentObserver(uri, true, contentObserver)
    return contentObserver
}
