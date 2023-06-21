package com.omaka.messageowl

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.omaka.messageowl.databinding.ActivityMainBinding
import com.omaka.messageowl.databinding.ImageDialogBinding
import com.omaka.messageowl.databinding.ImagePreviewBinding
import com.omaka.messageowl.ui.home.HomeFragmentDirections
import com.omaka.messageowl.utils.Codes
import com.omaka.messageowl.utils.asTempFile
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var imageFile: File? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(application as MessageOwlApplication)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder(StrictMode.getVmPolicy())
//                .detectLeakedClosableObjects()
//                .build()
//        )

        Intent(this, DatabaseService::class.java).also { serviceIntent ->
            startService(serviceIntent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (fragment != null) {
            navController = fragment.findNavController()
        }
        viewModel.currentUser.observe(this) {
            if(it == null) {
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToEditProfileFragmentFirst())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        NotificationManagerCompat.from(this).cancelAll()
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                lifecycleScope.launch {
                    viewModel.compressAndUpload(
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
                        viewModel.compressAndUpload(file)
                    }
                }
            }
        }

    fun showImagePreview(image: String?) {
            val dialogBinding = ImagePreviewBinding.inflate(layoutInflater)

        dialogBinding.profilePhoto.load(image ?: R.drawable.profile_placeholder)

            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()
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
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
                    ), Codes.CAMERA_CODE
                )
            }
            dialog.dismiss()
        }
    }

    private fun openCamera() {
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
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Codes.CAMERA_CODE -> {
                grantResults.forEach {
                    if (it == PackageManager.PERMISSION_GRANTED) {
                        openCamera()
                    } else {
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
}