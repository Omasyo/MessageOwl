package com.xtapps.messageowl.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentPrivateRoomBinding

class GroupRoomFragment : Fragment() {

    private var _binding: FragmentPrivateRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivateRoomBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val roomId = arguments?.getString("sampleText")
        binding.toolbar.title = roomId
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        ***REMOVED***
        binding.toolbar.setOnMenuItemClickListener {
            binding.container.openDrawer(GravityCompat.END)
            true
        ***REMOVED***

        val viewModel: GroupRoomViewModel by activityViewModels {
            GroupViewModelFactory(
                (activity?.application as MessageOwlApplication).appDatabase.appDao(),
                roomId!!
***REMOVED***
        ***REMOVED***

        val adapter = RoomRecyclerViewAdapter(true)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        ***REMOVED***
        recyclerView.adapter = adapter

        viewModel.messages.asLiveData().observe(viewLifecycleOwner) {
            it.let {
                adapter.submitList(it)
            ***REMOVED***
        ***REMOVED***

        return binding.root
    ***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.drawable.solid_background)
    ***REMOVED***
***REMOVED***