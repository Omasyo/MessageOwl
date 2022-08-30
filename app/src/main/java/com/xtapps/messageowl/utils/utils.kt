package com.xtapps.messageowl.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object Codes {
    const val CAMERA_CODE = 1
    const val CONTACTS_CODE = 2
}

fun Uri.asTempFile(context: Context): File {
    val inputStream = context.contentResolver.openInputStream(this)!!
    val imageFile = File.createTempFile(
        "profile",
        ".jpg",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )
    val out: OutputStream = FileOutputStream(imageFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        out.write(buf, 0, len)
    }
    out.close()
    inputStream.close()
    return imageFile
}

fun formatTime(timestamp: Date, short: Boolean = true): String {

    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.time = Date() // compute start of the day for the timestamp
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)

    val midnight = cal.time.time
    val day = 86400000
    val hour = 3600000
    val current = Date().time


//    val formatPatter = when {
//        timestamp.time > cal.time.time -> "HH:mm"
//        timestamp.time in (cal.time.time - 86400000)..cal.time.time -> "Yesterday"
//        Date().time - timestamp.time <= 345600000 -> "E"
//        else -> "MMM d"
//    }

    val formattedTime = when (timestamp.time) {
        in (midnight - day)..midnight -> "Yesterday"
        else -> when (timestamp.time) {
            in midnight..(midnight + day) -> SimpleDateFormat("HH:mm").format(timestamp)
            in (current - 4 * day)..current -> SimpleDateFormat("EEEE").format(timestamp)
            else -> SimpleDateFormat("MMM d").format(timestamp)
        }
    }

    return formattedTime
}