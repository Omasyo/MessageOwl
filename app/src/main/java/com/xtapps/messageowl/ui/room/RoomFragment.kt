package com.xtapps.messageowl.ui.room

import android.os.Bundle
import android.util.Log
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
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentRoomBinding
import java.util.*

class RoomFragment : Fragment() {

    private var _binding: ViewBinding? = null
    private val binding get() = _binding as FragmentRoomBinding

    private lateinit var recyclerView: RecyclerView
    val viewModel: RoomViewModel by activityViewModels {
        RoomViewModelFactory(
            (activity?.application as MessageOwlApplication).appDatabase.messageDao(),
        )
    ***REMOVED***

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val roomId = arguments?.getString("room_id")
        val isGroup = arguments?.getBoolean("is_group")

        binding.sendButton.setOnClickListener {
            Log.d("TAG", "Datetime: ${Date()***REMOVED*** ")
        ***REMOVED***

        binding.toolbar.title = roomId.toString()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        ***REMOVED***
        binding.toolbar.setOnMenuItemClickListener {
            binding.container.openDrawer(GravityCompat.END)
            true
        ***REMOVED***

        val adapter = RoomRecyclerViewAdapter(isGroup!!)

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
            recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        ***REMOVED***

        viewModel.getMessages(roomId!!).asLiveData().observe(viewLifecycleOwner) {
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