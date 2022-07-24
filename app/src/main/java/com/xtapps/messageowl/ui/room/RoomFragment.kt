package com.xtapps.messageowl.ui.room

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.xtapps.messageowl.ui.home.HomeFragmentDirections

class RoomFragment : Fragment() {

    private var _binding: ViewBinding? = null
    private val binding get() = _binding as FragmentRoomBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var participantsRecyclerView: RecyclerView

    val viewModel: RoomViewModel by activityViewModels {
        with((activity?.application as MessageOwlApplication).appDatabase) {
            RoomViewModelFactory(
                messageDao(),
                chatRoomDao(),
                userDao(),
***REMOVED***
        ***REMOVED***
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

        val roomId = requireArguments().getString("room_id")!!
        val isGroup = requireArguments().getBoolean("is_group")

        val adapter = RoomRecyclerViewAdapter(isGroup)
        val participantsAdapter = ParticipantsRecyclerViewAdapter { particpantId ->
            viewModel.getPrivateRoom(particpantId).asLiveData().observe(viewLifecycleOwner) {
                val action =
                    RoomFragmentDirections.actionRoomFragmentSelf(it.id, false)
                findNavController().navigate(action)
            ***REMOVED***
        ***REMOVED***

        binding.apply {

            toolbar.apply {
                setNavigationOnClickListener { findNavController().popBackStack() ***REMOVED***
                setOnMenuItemClickListener {
                    binding.container.openDrawer(GravityCompat.END)
                    true
                ***REMOVED***
                if (!isGroup) {
                    drawer.visibility = View.GONE
                    toolbar.menu.clear()
                ***REMOVED***
            ***REMOVED***

            participantsRecyclerView = participantsRecyclerview
            participantsRecyclerView.layoutManager = LinearLayoutManager(context)
            participantsRecyclerView.adapter = participantsAdapter

            viewModel.getRoom(roomId).asLiveData().observe(viewLifecycleOwner) { room ->
                toolbar.title = room.name
                viewModel.getUsers(room.participants).asLiveData().observe(viewLifecycleOwner) {
                    it.let { participantsAdapter.submitList(it) ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            this@RoomFragment.recyclerView = this.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true ***REMOVED***
            recyclerView.adapter = adapter

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    if (lastIndex == adapter.itemCount - 1) {
                        scrollButton.visibility = View.GONE
                    ***REMOVED*** else {
                        scrollButton.visibility = View.VISIBLE
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***)
            scrollButton.setOnClickListener {
                recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
            ***REMOVED***

            sendButton.setOnClickListener {
                val message = messageField.text.toString()

                if(message.isNotEmpty()) {
                    viewModel.sendMessage(messageField.text.toString(), roomId)
                ***REMOVED***
                messageField.text.clear()
            ***REMOVED***
        ***REMOVED***


        viewModel.getMessages(roomId).asLiveData().observe(viewLifecycleOwner) {
            val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                .findLastCompletelyVisibleItemPosition()
            it.let { adapter.submitList(it) ***REMOVED***
            if (lastIndex == adapter.itemCount - 2) {
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            ***REMOVED***
        ***REMOVED***

        return binding.root
    ***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.drawable.solid_background)
    ***REMOVED***
***REMOVED***