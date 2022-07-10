package com.xtapps.messageowl.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

fun takePicture(activity: Activity, takePicture: ActivityResultLauncher<Uri>): Uri {
    val imageFile = File.createTempFile(
        "profile",
        ".jpg",
        activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )
    Log.d("TAG", "takePicture: ${imageFile.freeSpace***REMOVED***")
    val imageUri: Uri = FileProvider.getUriForFile(
        activity,
        activity.packageName + ".provider",
        imageFile
    )

    val result =
        ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        )
    if (result == PackageManager.PERMISSION_GRANTED) {
        takePicture.launch(imageUri)
        Log.d("TAG", "takePicture: ${imageFile.freeSpace***REMOVED***")

    ***REMOVED*** else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
***REMOVED***, 1
        )
    ***REMOVED***
    return imageUri
***REMOVED***