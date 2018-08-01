package com.lordgama.carpicturesutilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
//import android.support.media.ExifInterface
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.os.Build
import android.support.v4.content.FileProvider
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by Daniel on 21/12/2017.
 */

class ImageHandler(){
    companion object {
        @Throws(IOException::class)
        fun createImageFileJPG(context: Context,fileName: String): File {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val imageFileName = fileName+ "_" + timeStamp + ""
            //val storageDir = context.getFilesDir()
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File(storageDir, imageFileName + ".jpg")
        }

        fun calculateInSampleSize(
                options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }

        fun decodeSampledBitmapFromFile(filePath: String,
                                            reqWidth: Int, reqHeight: Int,ctx: Context): Bitmap {
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false

            val imagen = BitmapFactory.decodeFile(filePath, options)
            var ei: ExifInterface? = null
            try {

                //ei = ExifInterface(filePath)
                var file = File(filePath)

                val AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider"
                val photoURI = FileProvider.getUriForFile(ctx, AUTHORITY,file)
                ei = getExifInterface(ctx,photoURI)
                val orientation = ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED)
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(imagen, 90)
                    ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(imagen, 180)
                    ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(imagen, 270)
                    ExifInterface.ORIENTATION_NORMAL -> return imagen
                    else -> return imagen
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return imagen
            }
            //return BitmapFactory.decodeFile(filePath, options)
        }

        fun getExifInterface(context: Context, uri: Uri): ExifInterface? {
            try {
               val path = uri.toString()
                if (path.startsWith("file://")) {
                    return ExifInterface(path)
                }
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (path.startsWith("content://")) {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        return ExifInterface(inputStream)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        internal fun rotateImage(b: Bitmap, deg: Int): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(deg.toFloat())
            return Bitmap.createBitmap(b, 0, 0, b.width, b.height, matrix, true)
        }

        fun getBitmapFromURL(imageUrl: String): Bitmap? {
            try {
                val url = URL(imageUrl)
                val connection =

                        url.openConnection() as HttpURLConnection
                connection.setDoInput(true)
                connection.connect()
                val inputStream = connection.getInputStream()
                return BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        }

        fun writeBitmapToSD(aFileName: String, aBitmap: Bitmap) :String?{
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val imageFileName = aFileName+ "_" + timeStamp + ""

            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                return null
            }

            val sdPath = Environment.getExternalStorageDirectory()
            val sdFile = File(sdPath, imageFileName)

            if (sdFile.exists()) {
                sdFile.delete()
            }

            try {
                val out = FileOutputStream(sdFile)
                aBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                return imageFileName

            } catch (e: Exception) {
                return null
            }

        }
    }
}