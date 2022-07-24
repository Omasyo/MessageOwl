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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
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

    private val viewModel: MainViewModel by viewModels {
        with((application as MessageOwlApplication).appDatabase) {
            MainViewModelFactory(userDao())
        ***REMOVED***
    ***REMOVED***

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
        ***REMOVED***
        viewModel.currentUser.observe(this) {
            if(it == null) {
//                navController.navigate(HomeFragmentDirections.actionHomeFragmentToCompleteProfileFragmentFirst())
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    private suspend fun compressAndUpload(file: File) {
        val compressedImageFile = Compressor.compress(
            this,
            file
        ) {
            resolution(612, 816)
            format(Bitmap.CompressFormat.JPEG)
            quality(30)
        ***REMOVED***
        val profilePicRef =
            Firebase.storage.reference.child("profilePics/${FirebaseAuth.getInstance().currentUser?.uid***REMOVED***")

        profilePicRef.putFile(compressedImageFile.toUri())
            .addOnFailureListener(this) {
                Log.w(com.xtapps.messageowl.ui.home.TAG, "Error uploading image: $it")
                Toast.makeText(
                    this, resources.getString(R.string.photo_upload_error),
                    Toast.LENGTH_LONG
    ***REMOVED***.show()
            ***REMOVED***
    ***REMOVED***

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                lifecycleScope.launch {
                    compressAndUpload(
                        uri.asTempFile(this@MainActivity)
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                imageFile?.let { file ->
                    lifecycleScope.launch {
                        compressAndUpload(file)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    fun showImageDialog() {
        val dialogBinding = ImageDialogBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.image_dialog_title))
            .setView(dialogBinding.root)
            .show()


        dialogBinding.dialogImage.setOnClickListener {
            galleryLauncher.launch("image/*")
            dialog.dismiss()
        ***REMOVED***
        dialogBinding.dialogCamera.setOnClickListener {
            val result =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
    ***REMOVED***
            if (result == PackageManager.PERMISSION_GRANTED) {
                imageFile = File.createTempFile(
                    "profile",
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    ***REMOVED***
                val imageUri: Uri? = FileProvider.getUriForFile(
                    this,
                    "$packageName.provider",
                    imageFile!!
    ***REMOVED***
                takePictureLauncher.launch(imageUri)

            ***REMOVED*** else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
        ***REMOVED***, 1
    ***REMOVED***
            ***REMOVED***
            dialog.dismiss()
        ***REMOVED***
    ***REMOVED***

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
        ***REMOVED***.show()
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***