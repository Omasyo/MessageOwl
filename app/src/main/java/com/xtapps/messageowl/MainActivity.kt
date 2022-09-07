package com.xtapps.messageowl

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.xtapps.messageowl.databinding.ActivityMainBinding
import com.xtapps.messageowl.databinding.ImageDialogBinding
import com.xtapps.messageowl.databinding.ImagePreviewBinding
import com.xtapps.messageowl.ui.home.HomeFragmentDirections
import com.xtapps.messageowl.utils.Codes
import com.xtapps.messageowl.utils.asTempFile
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var imageFile: File? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(application as MessageOwlApplication)
    ***REMOVED***

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder(StrictMode.getVmPolicy())
//                .detectLeakedClosableObjects()
//                .build()
//        )

        Intent(this, DatabaseService::class.java).also { serviceIntent ->
            startService(serviceIntent)
        ***REMOVED***

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            ***REMOVED***

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "FCM token is $token"
            Log.d(TAG, msg)
        ***REMOVED***)


        Firebase.messaging.token.addOnCompleteListener {
            Log.d(TAG, "onCreate: token ${it.result***REMOVED***")
        ***REMOVED***

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (fragment != null) {
            navController = fragment.findNavController()
        ***REMOVED***
        viewModel.currentUser.observe(this) {
            if(it == null) {
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToEditProfileFragmentFirst())
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                lifecycleScope.launch {
                    viewModel.compressAndUpload(
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
                        viewModel.compressAndUpload(file)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    fun showImagePreview(image: String?) {
            val dialogBinding = ImagePreviewBinding.inflate(layoutInflater)

        dialogBinding.profilePhoto.load(image ?: R.drawable.profile_placeholder)

            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()
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
                openCamera()
            ***REMOVED*** else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
        ***REMOVED***, Codes.CAMERA_CODE
    ***REMOVED***
            ***REMOVED***
            dialog.dismiss()
        ***REMOVED***
    ***REMOVED***

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
    ***REMOVED***

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
                    ***REMOVED*** else {
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
***REMOVED***