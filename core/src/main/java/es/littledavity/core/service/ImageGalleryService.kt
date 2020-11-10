package es.littledavity.core.service

import android.R.attr.mimeType
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import androidx.core.content.ContextCompat
import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.database.chorbo.ChorboRepository
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class ImageGalleryService @Inject constructor(
    internal val context: Context,
    internal val chorboRepository: ChorboRepository
) {

    companion object {
        private const val QUALITY = 100
    }

    fun getBitmap(uri: Uri): Bitmap {
        val imageStream = context.contentResolver?.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos)
        val bytes = baos.toByteArray()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun getBitmap(imageBase64: String): Bitmap {
        val decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    suspend fun deleteChorboDirectory(idList: List<Long>) {
        idList.forEach {
            val chorbo = chorboRepository.getChorbo(it)
            chorbo?.let { item ->
                val file = File(item.image)
                val flagFile = File(item.flag)
                if (file.exists()) {
                    file.delete()
                    Timber.i("image File deleted ${file.path}")
                }
                if (flagFile.exists()) {
                    flagFile.delete()
                    Timber.i("flag file deleted ${flagFile.path}")
                }
            }
        }
    }

    fun createDirectoryAndSaveFile(
        imageToSave: Bitmap,
        fileName: String
    ): String {
        val externalStorageVolumes = ContextCompat.getExternalFilesDirs(context, null)
        val primaryExternalStorage =
            externalStorageVolumes[0]?.path
                ?: externalStorageVolumes[1].path
                ?: throw IllegalStateException()

        val dirName = "$primaryExternalStorage/chorboagenda"

        val direct = File(
            dirName
        )

        if (!direct.exists()) {
            val wallpaperDirectory = File(dirName)
            wallpaperDirectory.mkdirs()
        }
        val uuid = UUID.randomUUID().toString()
        val file = File(dirName, fileName + uuid)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, QUALITY, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.path
    }

    fun saveMediaImage(imageToSave: Bitmap, chorbo: Chorbo): String {
        val relativeLocation = "${Environment.DIRECTORY_PICTURES}/${chorbo.name}"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, chorbo.id)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        }
        val resolver = context.contentResolver

        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
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

    fun saveMediaImage(imageToSave: Bitmap, chorbo: Chorbo, fileName: String): String {
        val relativeLocation = "${Environment.DIRECTORY_PICTURES}/${chorbo.name}"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        }
        val resolver = context.contentResolver

        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val uri = resolver.insert(contentUri, contentValues)
            ?: throw IOException("Failed to create new MediaStore record.")

        val stream = resolver.openOutputStream(uri)
            ?: throw IOException("Failed to get output stream.")

        if (!imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
            throw IOException("Failed to save bitmap")
        }
        stream.close()
        return uri.path.orEmpty()
    }

    // Checks if a volume containing external storage is available
    // for read and write.
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(
            Environment.MEDIA_MOUNTED,
            Environment.MEDIA_MOUNTED_READ_ONLY
        )
    }
}
