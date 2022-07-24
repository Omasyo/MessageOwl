package com.xtapps.messageowl.ui.home

import HomeFragmentAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.*
import com.xtapps.messageowl.databinding.FragmentHomeBinding
import com.xtapps.messageowl.models.UserModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        with((activity?.application as MessageOwlApplication).appDatabase) {
            MainViewModelFactory(userDao())
        }
    }

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
            //Open drawer
            toolbar.setOnMenuItemClickListener {
                root.openDrawer(GravityCompat.END)
                true
            }
            cameraButton.setOnClickListener {
                (activity as MainActivity).showImageDialog()
            }

            editProfileButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCompleteProfileFragment())
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

            viewModel.currentUser.observe(viewLifecycleOwner) { user: UserModel? ->
//                val file = File.createTempFile(
//                    "profile",
//                    ".jpg",
//                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                )
//                user.profilePic?.let { it1 -> Firebase.storage.reference.child(it1).getFile(file).addOnSuccessListener {
//                    profilePhoto.setImageURI(file.toUri())
//                } }
                username.text = user?.name
                phoneNo.text = user?.phoneNo
            }

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