package com.xtapps.messageowl.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentRoomBinding
import com.xtapps.messageowl.models.ChatRoom
import kotlinx.coroutines.launch

class RoomFragment : Fragment() {

    private var _binding: ViewBinding? = null
    private val binding get() = _binding as FragmentRoomBinding

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

        viewModel.resetUnreadCount(roomId)

        val participantsAdapter = ParticipantsRecyclerViewAdapter { participantId ->
            if(participantId == viewModel.authUser.uid) return@ParticipantsRecyclerViewAdapter
            viewModel.getPrivateRoom(participantId).asLiveData().observe(viewLifecycleOwner) {
                val action =
                    RoomFragmentDirections.actionRoomFragmentSelf(it.id)
                findNavController().navigate(action)
            ***REMOVED***
        ***REMOVED***



        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {

                viewModel.getRoom(roomId).flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { room: ChatRoom? ->
                    if (room == null) return@collect
                    toolbar.apply {
                        title = room.name ?: ""
                        setNavigationOnClickListener { findNavController().popBackStack() ***REMOVED***
                        setOnMenuItemClickListener {
                            binding.container.openDrawer(GravityCompat.END)
                            true
                        ***REMOVED***
                        if (!room.isGroup) {
                            binding.container.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                            menu.clear()
                        ***REMOVED***
                    ***REMOVED***

                    participantsRecyclerView.layoutManager = LinearLayoutManager(context)
                    participantsRecyclerView.adapter = participantsAdapter
                    launch {
                        viewModel.getUsers(room.participants).collect {
                            it.let { participantsAdapter.submitList(it) ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***

                    val adapter = RoomRecyclerViewAdapter(room.isGroup)
                    launch {
                        viewModel.getMessages(roomId).collect {

                            val lastIndex =
                                (binding.messageRecyclerView.layoutManager as LinearLayoutManager)
                                    .findLastCompletelyVisibleItemPosition()
                            it.let { adapter.submitList(it) ***REMOVED***
                            binding.messageRecyclerView.scrollToPosition(adapter.itemCount - room.unread)
                            if (lastIndex == adapter.itemCount - 2) {
                                binding.messageRecyclerView.scrollToPosition(adapter.itemCount - 1)
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                    messageRecyclerView.layoutManager =
                        LinearLayoutManager(context).apply { stackFromEnd = true ***REMOVED***
                    messageRecyclerView.adapter = adapter

                    messageRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        messageRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            sendButton.setOnClickListener {
                val message = messageField.text.toString()

                if (message.isNotEmpty()) {
                    viewModel.sendMessage(messageField.text.toString(), roomId)
                ***REMOVED***
                messageField.text.clear()
            ***REMOVED***
        ***REMOVED***

        return binding.root
    ***REMOVED***


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.drawable.solid_background)
    ***REMOVED***
***REMOVED***