package com.zzy.nasaapod.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

internal const val ALBUM_NAME = "NasaAPOD"
private const val TAG = "BitmapUtil"

object BitmapUtil {

    fun saveImageToAppDirectory(context: Context, name: String, drawable: Drawable): String? {
        try {
            val bitmap = drawable.toBitmap()
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)


            val pictureDirectory = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                ALBUM_NAME
            )
            if (!pictureDirectory.exists()) {
                pictureDirectory.mkdirs()
            }

            val path = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path}${File.separator}$ALBUM_NAME${File.separator}$name.jpeg"
            Log.d(TAG,"Image saved , path created $path")
            val file = File(path)
            file.createNewFile()


            val fos = FileOutputStream(file)
            fos.write(baos.toByteArray())

            fos.close()

            Log.d(TAG,"Image saved ${file.exists()}")
            return path
        } catch (e: IOException) {
            //Save image failed
            e.printStackTrace()
            return null
        }
    }

    suspend fun downloadHDImageToExternalStorage(context: Context, name: String, url: String): String? = withContext(Dispatchers.IO) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        try {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            val externalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val pictureDirectory = File(
                externalPath,
                ALBUM_NAME
            )
            if (!pictureDirectory.exists()) {
                pictureDirectory.mkdirs()
            }

            val path = "$externalPath${File.separator}$ALBUM_NAME${File.separator}$name.jpeg"
            Log.d(TAG,"Image saved , path created $path")
            val file = File(path)
            file.createNewFile()


            val fos = FileOutputStream(file)
            fos.write(baos.toByteArray())

            fos.close()

            Log.d(TAG,"Image saved ${file.exists()}")

//            addImageToGallery(context, name, path)
            MediaScannerConnection.scanFile(context, arrayOf(path), arrayOf("image/jpeg" ), null);

            path
        } catch (e: IOException) {
            //Save image failed
            e.printStackTrace()
            null
        }
    }
}