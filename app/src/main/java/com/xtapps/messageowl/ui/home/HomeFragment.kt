package com.xtapps.messageowl.ui.home

import HomeFragmentAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentHomeBinding
import com.xtapps.messageowl.models.UserModel
import com.xtapps.messageowl.ui.auth.AuthActivity
import com.xtapps.messageowl.utils.asTempFile
import com.xtapps.messageowl.utils.takePicture
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.launch
import java.io.File

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
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
                            profilePhoto.setImageURI(file.toUri())
                        } else {
                            Log.w(TAG, "Error uploading image: $it")
                            Toast.makeText(
                                context, resources.getString(R.string.photo_upload_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

            var imageFile: File? = null

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
                            Log.d(TAG, "onCreateView: image $imageFile")
                            lifecycleScope.launch {
                                compressAndUpload(file)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }

            //Open drawer
            toolbar.setOnMenuItemClickListener {
                root.openDrawer(GravityCompat.END)
                true
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

            editProfileButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCompleteProfileFragment())
            }

            updateNumberButton.setOnClickListener {
                startActivity(Intent(activity, AuthActivity::class.java))
                activity?.finish()
            }

            changeNumberButton.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.change_number))
                    .setMessage(resources.getString(R.string.change_number_warning))
                    .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                        dialog.cancel()
                    }.setPositiveButton(resources.getString(R.string.proceed)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.signOutUser()
                    }.show()

            }

//            viewModel.currentUser.observe(viewLifecycleOwner) { user: UserModel ->
////                val file = File.createTempFile(
////                    "profile",
////                    ".jpg",
////                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
////                )
////                user.profilePic?.let { it1 -> Firebase.storage.reference.child(it1).getFile(file).addOnSuccessListener {
////                    profilePhoto.setImageURI(file.toUri())
////                } }
////                username.text = user.name
////                phoneNo.text = user.phoneNo
//            }

            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabs
        val viewPager = binding.pager
        viewPager.adapter = HomeFragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Chats" else "Contacts" // FIXME: hardcoding
        }.attach()
    }
}