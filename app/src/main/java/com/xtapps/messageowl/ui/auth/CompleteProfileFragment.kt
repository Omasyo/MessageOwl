package com.xtapps.messageowl.ui.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.MainActivity
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentCompleteProfileBinding
import com.xtapps.messageowl.utils.takePicture
import java.io.File

class CompleteProfileFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteProfileBinding.inflate(inflater, container, false)

        var imageUri: Uri? = FirebaseAuth.getInstance().currentUser?.photoUrl
        binding.profileImage.setImageURI(imageUri)

        val takePictureContract =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
                if (success) {
                    binding.profileImage.setImageURI(null)
                    binding.profileImage.setImageURI(imageUri)
                }
            }

        binding.apply {
            proceedButton.setOnClickListener {
                (activity as AuthActivity).updateProfileDetails(usernameField.text.toString(), imageUri)
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }

            backButton.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                findNavController().popBackStack(R.id.welcomeFragment, false)
            }
            cameraButton.setOnClickListener {
                imageUri = takePicture(requireActivity(), takePictureContract)
            }
            return root
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}