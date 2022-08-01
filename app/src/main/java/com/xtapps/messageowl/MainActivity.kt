package com.xtapps.messageowl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.xtapps.messageowl.databinding.ActivityMainBinding
import com.xtapps.messageowl.databinding.ImageDialogBinding
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
    }

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
        viewModel.currentUser.observe(this) {
            if(it == null) {
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToEditProfileFragmentFirst())
            }
        }
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

//    private fun retrieveContacts() {
//        val phones = contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            arrayOf(
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                ContactsContract.CommonDataKinds.Phone.NUMBER
//            ),
//            null,
//            null,
//            null
//        )
//        if (phones != null) {
//
//            val contacts = hashMapOf<String, String>()
//
//            while (phones.moveToNext()) {
//                val name: String =
//                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                val phoneNumber: String =
//                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//
//                Log.d("Contacts", "Name: $name, Phone: $phoneNumber")
//                contacts[name] = formatPhone(phoneNumber)
//            }
//            Log.d("Contacts", "Size = ${phones.count} new = ${contacts.size}")
//            Log.d("Contacts", contacts.toString())
//            phones.close()
//        }
//    }
//
//    fun formatPhone(number: String): String {
//        val phoneUtil = PhoneNumberUtil.getInstance()
//
//        try {
//            val numberProto = phoneUtil.parse(
//                number,
//                (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
//                    .networkCountryIso
//            )
//            return phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
//        } catch (e: NumberParseException) {
//            System.err.println("NumberParseException was thrown: $number $e")
//
//        }
//        return "Invalid Number"
//    }

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