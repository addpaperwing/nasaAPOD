package com.zzy.nasaapod.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
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

            val path = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path}${File.separator}$ALBUM_NAME${File.separator}$name.jpg"
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

    fun downloadHDImageToExternalStorage(context: Context, name: String, drawable: Drawable): String? {
        try {
            val bitmap = drawable.toBitmap()
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

            val path = "$externalPath${File.separator}$ALBUM_NAME${File.separator}$name.jpg"
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
}