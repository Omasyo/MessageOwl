package com.xtapps.messageowl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.databinding.FragmentCompleteProfileBinding
import com.xtapps.messageowl.ui.auth.AuthActivity

class CompleteProfileFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    ***REMOVED***


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteProfileBinding.inflate(inflater, container, false)

        binding.apply {
            proceedButton.setOnClickListener {
//                updateProfileDetails(
//                    usernameField.text.toString(),
//                    imageRef
//    ***REMOVED***
                findNavController().navigate(CompleteProfileFragmentDirections.actionCompleteProfileFragmentToHomeFragment())
            ***REMOVED***

            backButton.setOnClickListener {
                startActivity(Intent(activity, AuthActivity::class.java))
            ***REMOVED***
            cameraButton.setOnClickListener {
                (activity as MainActivity).showImageDialog()
            ***REMOVED***
            return root
        ***REMOVED***
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    ***REMOVED***


    private fun updateProfileDetails(name: String, photo: String?) {
        val user = FirebaseAuth.getInstance().currentUser!!

        val usersDb = Firebase.firestore.collection("users")
        val userDoc = usersDb.document(user.phoneNumber!!)


        userDoc.set(
            hashMapOf(
                "name" to name,
                "uid" to user.uid,
                "profilePic" to photo,
                "rooms" to listOf("general")
***REMOVED***
        ).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {

            ***REMOVED*** else {
                Log.w(com.xtapps.messageowl.ui.auth.TAG, "Error writing document", it.exception)
                Toast.makeText(context, "Error completing profile", Toast.LENGTH_SHORT)
                    .show()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***