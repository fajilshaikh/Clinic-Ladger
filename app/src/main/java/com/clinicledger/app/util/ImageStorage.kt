package com.clinicledger.app.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File

fun saveClinicLogo(context: Context, clinicId: Long, source: Uri, maxDimension: Int = 512): String? {
    return try {
        val resolver = context.contentResolver
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(source)?.use { BitmapFactory.decodeStream(it, null, bounds) }
            ?: return null

        var sampleSize = 1
        while (bounds.outWidth / sampleSize > maxDimension || bounds.outHeight / sampleSize > maxDimension) {
            sampleSize *= 2
        }

        val decodeOptions = BitmapFactory.Options().apply { inSampleSize = sampleSize }
        val bitmap = resolver.openInputStream(source)?.use { BitmapFactory.decodeStream(it, null, decodeOptions) }
            ?: return null

        val dir = File(context.filesDir, "clinic_logos").apply { mkdirs() }
        val dest = File(dir, "clinic_$clinicId.jpg")
        dest.outputStream().use { out -> bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out) }
        bitmap.recycle()
        dest.absolutePath
    } catch (e: Exception) {
        null
    }
}

fun deleteClinicLogo(path: String?) {
    if (path.isNullOrBlank()) return
    runCatching { File(path).delete() }
}
