/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.utils

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileNotFoundException
import timber.log.Timber
import java.io.ByteArrayOutputStream

object ImageUtils {
    private const val DEFAULT_MIN_WIDTH_QUALITY = 600 // min pixels
    private const val TEMP_IMAGE_NAME = "tempImage"
    private var minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY



    fun getImageBytes(imageBase64: String): ByteArray = Base64.decode(imageBase64, Base64.DEFAULT)

    fun getDecodedBitmap(imageBase64: String): Bitmap {
        val decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun getDecodedBitmap(imageByteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
    }

    fun encodeImage(context: Context?, imageUri: Uri): String {
        val imageStream = context?.contentResolver?.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun encodeImage(imageBitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getImageBytes(context: Context?, imageUri: Uri): ByteArray {
        val imageStream = context?.contentResolver?.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun getImageFromResult(context: Context, imageReturnedIntent: Uri?): Bitmap? {
        var bm: Bitmap?
        val imageFile = getTempFile(context)
        val isCamera = imageReturnedIntent == null ||
                imageReturnedIntent == Uri.fromFile(imageFile)
        val selectedImage = if (isCamera) {
            /** CAMERA  */
            Uri.fromFile(imageFile)
        } else {
            /** ALBUM  */
            imageReturnedIntent
        }
        Timber.i("selectedImage: $selectedImage!!")

        selectedImage?.let {
            bm = getImageResized(context, selectedImage)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val rotation = getRotation(context, selectedImage, isCamera)
                bm = rotate(bm, rotation)
            }
            return bm
        }
        return null
    }

    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     */
    private fun getImageResized(context: Context, selectedImage: Uri): Bitmap? {
        var bm: Bitmap
        val sampleSizes = intArrayOf(5, 3, 2, 1)
        var i = 0
        do {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i])
            Timber.i("resizer: new bitmap width  $bm.width ")
            i++
        } while (bm.width < minWidthQuality && i < sampleSizes.size)
        return bm
    }

    private fun decodeBitmap(context: Context, theUri: Uri, sampleSize: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize

        var fileDescriptor: AssetFileDescriptor? = null
        try {
            fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri, "r")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return BitmapFactory.decodeFileDescriptor(
            fileDescriptor?.fileDescriptor, null, options
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getRotation(context: Context, imageUri: Uri, isCamera: Boolean): Int {
        val rotation: Int = if (isCamera) {
            getRotationFromCamera(context, imageUri)
        } else {
            getRotationFromGallery(context, imageUri)
        }
        Timber.i("Image rotation: $rotation")
        return rotation
    }

    private fun getRotationFromCamera(context: Context, imageFile: Uri): Int {
        var rotate = 0
        try {
            context.contentResolver.notifyChange(imageFile, null)
            imageFile.path?.let {
                val exif = ExifInterface(it)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rotate
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getRotationFromGallery(context: Context, imageUri: Uri): Int {
        val columns = arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cursor = context.contentResolver.query(imageUri, columns, null, null, null)
            ?: return 0
        return try {
            cursor.moveToFirst()
            val orientationColumnIndex = cursor.getColumnIndex(columns[0])
            cursor.getInt(orientationColumnIndex)
        } catch (e: Exception) {
            0
        } finally {
            cursor.close()
        }
    }

    private fun rotate(bm: Bitmap?, rotation: Int): Bitmap? {
        if (rotation != 0) {
            val matrix = Matrix()
            matrix.postRotate(rotation.toFloat())
            bm?.let {
                return Bitmap.createBitmap(it, 0, 0, bm.width, bm.height, matrix, true)
            }
        }
        return bm
    }

    private fun getTempFile(context: Context): File {
        val imageFile = File(context.externalCacheDir, TEMP_IMAGE_NAME)
        imageFile.parentFile?.mkdirs()
        return imageFile
    }
}
