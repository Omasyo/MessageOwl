package com.xtapps.messageowl.ui.home

import HomeFragmentAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.databinding.FragmentHomeBinding
import com.xtapps.messageowl.ui.auth.AuthActivity
import com.xtapps.messageowl.ui.auth.TAG
import com.xtapps.messageowl.ui.chats.ChatsViewModel
import com.xtapps.messageowl.ui.chats.ChatsViewModelFactory
import com.xtapps.messageowl.utils.takePicture
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
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val storageRef = Firebase.storage.reference


        val localFile = File.createTempFile("images", "jpg")
        val profilePicRef =
            Firebase.storage.reference.child("135351695_424295822143941_6336600627701852695_n.jpg")
        profilePicRef.getFile(localFile).addOnSuccessListener {
            binding.profilePhoto.setImageURI(localFile.toUri())
        ***REMOVED***.addOnFailureListener {
            Log.d(TAG, "onCreateView error: ${it.message***REMOVED***")
        ***REMOVED***

        var imageUri: Uri? = null
        val takePictureContract =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
                if (success) {
                    binding.profilePhoto.setImageURI(null)
                    binding.profilePhoto.setImageURI(imageUri)
                    imageUri?.let { uri ->
                        storageRef.child(localFile.name).putFile(uri)
                            .addOnSuccessListener {
                                binding.profilePhoto.setImageURI(localFile.toUri())
                            ***REMOVED***.addOnFailureListener {
                                Log.d(TAG, "onCreateView error: ${it.message***REMOVED***")
                            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***

        binding.apply {
            //Open drawer
            toolbar.setOnMenuItemClickListener {
                root.openDrawer(GravityCompat.END)
                true
            ***REMOVED***

            profilePhoto.setImageURI(FirebaseAuth.getInstance().currentUser?.photoUrl)
            username.text = FirebaseAuth.getInstance().currentUser?.displayName

            cameraButton.setOnClickListener {
                imageUri = takePicture(requireActivity(), takePictureContract)
            ***REMOVED***

            updateNumberButton.setOnClickListener {
                startActivity(Intent(activity, AuthActivity::class.java))
                activity?.finish()
            ***REMOVED***

            button2.setOnClickListener {
                viewModel.signOutUser()
            ***REMOVED***

            return root
        ***REMOVED***
    ***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabs
        val viewPager = binding.pager
        viewPager.adapter = HomeFragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if(position == 0) "Chats" else "Contacts" // FIXME: hardcoding
        ***REMOVED***.attach()
    ***REMOVED***
***REMOVED***