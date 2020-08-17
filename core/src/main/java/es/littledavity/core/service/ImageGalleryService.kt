package es.littledavity.core.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import es.littledavity.core.database.chorbo.ChorboRepository
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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
        val dirName =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/chorboagenda"
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
}
