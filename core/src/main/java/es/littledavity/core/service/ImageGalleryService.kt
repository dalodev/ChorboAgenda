package es.littledavity.core.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import androidx.core.content.ContextCompat
import es.littledavity.core.database.chorbo.ChorboRepository
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.IllegalStateException
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
