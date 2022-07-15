package com.xtapps.messageowl.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.*


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

fun takePicture(activity: Activity, takePicture: ActivityResultLauncher<Uri>): File {
    val imageFile = File.createTempFile(
        "profile",
        ".jpg",
        activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )
    Log.d("TAG", "takePicture: ${imageFile.freeSpace}")
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
        Log.d("TAG", "takePicture: ${imageFile.freeSpace}")

    } else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1
        )
    }
    return imageFile
}

//Recursice call for image upload fail
//val onCompleteListener = object : OnCompleteListener<UploadTask.TaskSnapshot> {
//    override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
//        binding.progressIndicator.visibility = View.GONE
//        if(p0.isSuccessful) {
//            binding.profileImage.setImageURI(compressedImageFile.toUri())
//        } else {
//            Snackbar.make(binding.root, resources.getString(R.string.photo_upload_error), Snackbar.LENGTH_LONG)
//                .setAction(R.string.retry) { profilePicRef.putFile(compressedImageFile.toUri()).addOnCompleteListener(requireActivity(), this) }
//                .show()
//        }
//    }