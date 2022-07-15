package com.xtapps.messageowl.ui.auth

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.MainActivity
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentCompleteProfileBinding
import com.xtapps.messageowl.utils.asTempFile
import com.xtapps.messageowl.utils.takePicture
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.launch
import java.io.File

class CompleteProfileFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteProfileBinding.inflate(inflater, container, false)

        binding.apply {
            var imageFile: File? = null
            var imageRef: String? = null

            suspend fun compressAndUpload(file: File) {
                val compressedImageFile = Compressor.compress(
                    requireContext(),
                    file
                ) {
                    resolution(612, 816)
                    format(Bitmap.CompressFormat.JPEG)
                    quality(30)
                }
                val profilePicRef =
                    Firebase.storage.reference.child("profilePics/${FirebaseAuth.getInstance().currentUser?.uid}")

                profilePicRef.putFile(compressedImageFile.toUri())
                    .addOnCompleteListener(requireActivity()) {
                        if (it.isSuccessful) {
                            imageRef = it.result.storage.path
                            profilePhoto.setImageURI(file.toUri())
                        } else {
                            Log.w(com.xtapps.messageowl.ui.home.TAG, "Error uploading image: $it")
                            Toast.makeText(
                                context, resources.getString(R.string.photo_upload_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

            val galleryLauncher =
                registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                    lifecycleScope.launch {
                        if (uri != null) {
                            compressAndUpload(
                                uri.asTempFile(requireContext())
                            )
                        }
                    }
                }

            val takePictureLauncher =
                registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
                    if (success) {
                        imageFile?.let { file ->
                            Log.d(com.xtapps.messageowl.ui.home.TAG, "onCreateView: image $imageFile")
                            lifecycleScope.launch {
                                compressAndUpload(file)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }

            proceedButton.setOnClickListener {
                updateProfileDetails(
                    usernameField.text.toString(),
                    imageRef
                )
                findNavController().navigate(CompleteProfileFragmentDirections.actionCompleteProfileFragmentToHomeFragment())
            }

            backButton.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                findNavController().popBackStack(R.id.welcomeFragment, false)
            }
            cameraButton.setOnClickListener {
                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.image_dialog_title))
                    .setView(R.layout.image_dialog)
                    .show()

                dialog.findViewById<Button>(R.id.dialog_image)?.setOnClickListener {
                    galleryLauncher.launch("image/*")
                    dialog.dismiss()
                }
                dialog.findViewById<Button>(R.id.dialog_camera)?.setOnClickListener {
                    imageFile = takePicture(requireActivity(), takePictureLauncher)
                    dialog.dismiss()
                }
            }
            return root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun updateProfileDetails(name: String, photo: String?) {
        val user = FirebaseAuth.getInstance().currentUser!!

        val usersDb = Firebase.firestore.collection("users")
        val userDoc = usersDb.document(user.phoneNumber!!)


        userDoc.set(
            hashMapOf(
                "name" to name,
                "uid" to user.uid,
                "profilePic" to photo,
                "rooms" to listOf("general")
            )
        ).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {

            } else {
                Log.w(TAG, "Error writing document", it.exception)
                Toast.makeText(context, "Error completing profile", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}