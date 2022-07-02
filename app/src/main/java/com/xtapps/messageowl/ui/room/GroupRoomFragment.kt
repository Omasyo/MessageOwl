package com.xtapps.messageowl.ui.room

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentGroupRoomBinding
import com.xtapps.messageowl.databinding.FragmentPrivateRoomBinding

class GroupRoomFragment : Fragment() {

    private var _binding: FragmentGroupRoomBinding? = null
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
        _binding = FragmentGroupRoomBinding.inflate(inflater, container, false)
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

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition()
                if(lastIndex == adapter.itemCount - 1) {
                    binding.scrollButton.visibility = View.GONE
                ***REMOVED*** else {
                    binding.scrollButton.visibility = View.VISIBLE
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***)
        binding.scrollButton.setOnClickListener {
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        ***REMOVED***

        viewModel.messages.asLiveData().observe(viewLifecycleOwner) {
            val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                .findLastCompletelyVisibleItemPosition()
            it.let { adapter.submitList(it) ***REMOVED***
            if(lastIndex == adapter.itemCount - 2) {
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            ***REMOVED*** else {

            ***REMOVED***
        ***REMOVED***

        return binding.root
    ***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.drawable.solid_background)
    ***REMOVED***
***REMOVED***