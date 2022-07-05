package com.xtapps.messageowl.ui.home

import HomeFragmentAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.MainActivity
import com.xtapps.messageowl.databinding.FragmentHomeBinding
import com.xtapps.messageowl.ui.auth.AuthActivity
import com.xtapps.messageowl.ui.auth.TAG

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        Log.d(TAG, "verifyNumber ${FirebaseAuth.getInstance().currentUser?.uid***REMOVED*** ${FirebaseAuth.getInstance().currentUser?.phoneNumber***REMOVED***")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.toolbar.setOnMenuItemClickListener {
            binding.container.openDrawer(GravityCompat.END)
            true
        ***REMOVED***

        binding.updateNumberButton.setOnClickListener {
            startActivity(Intent(activity, AuthActivity::class.java))
            activity?.finish()
        ***REMOVED***
        binding.button2.setOnClickListener {
            signOutUser()
        ***REMOVED***

        return binding.root
    ***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabs
        val viewPager = binding.pager
        viewPager.adapter = HomeFragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if(position == 0) "Chats" else "Contacts" // FIXME: hardcoding
        ***REMOVED***.attach()
    ***REMOVED***

    private fun signOutUser() =
        FirebaseAuth.getInstance().signOut()
***REMOVED***