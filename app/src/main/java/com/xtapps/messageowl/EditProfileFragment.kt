package com.xtapps.messageowl

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.databinding.FragmentCompleteProfileBinding
import com.xtapps.messageowl.models.UserModel
import com.xtapps.messageowl.ui.auth.AuthActivity
import java.io.File
import java.util.*

class EditProfileFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBinding? = null
    private val binding get() = _binding!!


    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(requireActivity().application as MessageOwlApplication)
    }

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
            proceedButton.setOnClickListener {
                viewModel.createUser(usernameField.text.toString())
                findNavController().navigate(EditProfileFragmentDirections.actionCompleteProfileFragmentToHomeFragment())
            }

            backButton.setOnClickListener {
                startActivity(Intent(activity, AuthActivity::class.java))
            }
            cameraButton.setOnClickListener {
                (activity as MainActivity).showImageDialog()
            }

//            viewModel.profilePhoto.observe(viewLifecycleOwner) { uri ->
//                profilePhoto.setImageURI(uri)
//            }

            viewModel.currentUser.observe(viewLifecycleOwner) { user: UserModel? ->
                usernameField.setText(user?.name)
            }

            return root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//
//    private fun updateProfileDetails(name: String, photo: File?) {
//        val user = FirebaseAuth.getInstance().currentUser!!
//
//        val usersDb = Firebase.firestore.collection("users")
//        val userDoc = usersDb.document(user.phoneNumber!!)
//
//
//        userDoc.set(
//            hashMapOf(
//                "name" to name,
//                "uid" to user.uid,
//                "profilePic" to photo,
//                "rooms" to listOf("general")
//            )
//        ).addOnCompleteListener(requireActivity()) {
//            if (it.isSuccessful) {
//
//            } else {
//                Log.w(com.xtapps.messageowl.ui.auth.TAG, "Error writing document", it.exception)
//                Toast.makeText(context, "Error completing profile", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
}