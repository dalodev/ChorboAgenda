/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.service

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.paulrybitskyi.hiltbinder.BindType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.util.UUID
import javax.inject.Inject

interface ImageGalleryService {
    fun createMediaFile(imageId: String?, name: String): String?
    fun removeMediaFile(uri: Uri): Int
}

@BindType
internal class ImageGalleryServiceImpl @Inject constructor(
        @ApplicationContext private val context: Context
) : ImageGalleryService {

    companion object {
        private const val QUALITY = 100
    }

    override fun createMediaFile(imageId: String?, name: String): String? = try {
        val bitmapImage = imageId?.toUri()?.let(::getBitmap)
        bitmapImage?.let { saveMediaImage(it, name, imageId) }
    } catch (e: FileNotFoundException) {
        null
    }

    private fun getBitmap(uri: Uri): Bitmap {
        val imageStream = context.contentResolver?.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos)
        val bytes = baos.toByteArray()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun saveMediaFileLegacy(
            imageToSave: Bitmap,
            fileName: String,
            name: String
    ): String {
        val directoryPath = getExternalStorageDirectory().toString()
        val directory = File("${directoryPath}/$fileName")

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, UUID.randomUUID().toString())
        saveImageToStream(imageToSave, FileOutputStream(file))
        val values = contentValues(name)
        values.put(MediaStore.Images.Media.DATA, file.absolutePath)
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values).toString()
    }

    private fun saveMediaImage(imageToSave: Bitmap, name: String, imageId: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveMediaImageQ(imageToSave, name, imageId)
        } else {
            saveMediaFileLegacy(imageToSave, name, imageId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveMediaImageQ(imageToSave: Bitmap, name: String, imageId: String): String {
        val relativeLocation = "${Environment.DIRECTORY_PICTURES}/$name"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageId)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        }
        val resolver = context.contentResolver

        val contentUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val uri = resolver.insert(contentUri, contentValues)
                ?: throw IOException("Failed to create new MediaStore record.")

        val stream = resolver.openOutputStream(uri)
                ?: throw IOException("Failed to get output stream.")

        if (!imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
            throw IOException("Failed to save bitmap")
        }
        stream.close()
        return uri.toString()
    }

    override fun removeMediaFile(uri: Uri): Int {
        return try {
            val resolver = context.contentResolver
            resolver.delete(uri, null, null)
        } catch (e: Exception) {
            -1
        }
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun contentValues(id: String) : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, id)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        return values
    }
}
