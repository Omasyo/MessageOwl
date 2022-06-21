package com.xtapps.messageowl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.databinding.FragmentHomeBinding
import com.xtapps.messageowl.ui.chats.ChatsFragment
import com.xtapps.messageowl.ui.contacts.ContactsFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.toolbar.setOnMenuItemClickListener {
            binding.container.openDrawer(GravityCompat.END)
            true
        ***REMOVED***

        binding.button2.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
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
***REMOVED***

class HomeFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2 // FIXME: do not hardcode

    override fun createFragment(position: Int): Fragment {
        // FIXME: hardcoding
        val fragment = when(position) {
            0 -> ChatsFragment()
            else -> ContactsFragment()
        ***REMOVED***
        fragment.arguments = Bundle().apply {

        ***REMOVED***
        return fragment
    ***REMOVED***
***REMOVED***