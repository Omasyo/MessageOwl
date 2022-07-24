package com.xtapps.messageowl

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.databinding.ActivityMainBinding
import com.xtapps.messageowl.databinding.ImageDialogBinding
import com.xtapps.messageowl.ui.home.HomeFragmentDirections
import com.xtapps.messageowl.utils.asTempFile
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder(StrictMode.getVmPolicy())
//                .detectLeakedClosableObjects()
//                .build()
//        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (fragment != null) {
            navController = fragment.findNavController()
        }
        if (false) {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToCompleteProfileFragmentFirst())
        }
    }


    private suspend fun compressAndUpload(file: File) {
        val compressedImageFile = Compressor.compress(
            this,
            file
        ) {
            resolution(612, 816)
            format(Bitmap.CompressFormat.JPEG)
            quality(30)
        }
        val profilePicRef =
            Firebase.storage.reference.child("profilePics/${FirebaseAuth.getInstance().currentUser?.uid}")

        profilePicRef.putFile(compressedImageFile.toUri())
            .addOnFailureListener(this) {
                Log.w(com.xtapps.messageowl.ui.home.TAG, "Error uploading image: $it")
                Toast.makeText(
                    this, resources.getString(R.string.photo_upload_error),
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                lifecycleScope.launch {
                    compressAndUpload(
                        uri.asTempFile(this@MainActivity)
                    )
                }
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                imageFile?.let { file ->
                    lifecycleScope.launch {
                        compressAndUpload(file)
                    }
                }
            }
        }

    fun showImageDialog() {
        val dialogBinding = ImageDialogBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.image_dialog_title))
            .setView(dialogBinding.root)
            .show()


        dialogBinding.dialogImage.setOnClickListener {
            galleryLauncher.launch("image/*")
            dialog.dismiss()
        }
        dialogBinding.dialogCamera.setOnClickListener {
            val result =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
            if (result == PackageManager.PERMISSION_GRANTED) {
                imageFile = File.createTempFile(
                    "profile",
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
                val imageUri: Uri? = FileProvider.getUriForFile(
                    this,
                    "$packageName.provider",
                    imageFile!!
                )
                takePictureLauncher.launch(imageUri)

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 1
                )
            }
            dialog.dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2) {
            grantResults.forEach {
                if(it != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}