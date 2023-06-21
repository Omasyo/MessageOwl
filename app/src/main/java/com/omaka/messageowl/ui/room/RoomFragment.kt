package com.omaka.messageowl.ui.room

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.omaka.messageowl.MainActivity
import com.omaka.messageowl.MessageOwlApplication
import com.omaka.messageowl.R
import com.omaka.messageowl.databinding.FragmentRoomBinding
import kotlinx.coroutines.launch

class RoomFragment : Fragment() {

    private var _binding: ViewBinding? = null
    private val binding get() = _binding as FragmentRoomBinding

    private lateinit var roomId: String

    val viewModel: RoomViewModel by activityViewModels {
        with((activity?.application as MessageOwlApplication).appDatabase) {
            RoomViewModelFactory(
                messageDao(),
                chatRoomDao(),
                userDao(),
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)

        roomId = requireArguments().getString("room_id")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        val logoImage = binding.roomPhoto

        val logoAnimation = logoImage.drawable as AnimatedVectorDrawable
        logoAnimation.apply {
            registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    logoAnimation.start()
                }
            })
            start()
        }

        val participantsAdapter = ParticipantsRecyclerViewAdapter((activity as MainActivity)::showImagePreview) { participantId ->
            if(participantId == viewModel.authUser.uid) return@ParticipantsRecyclerViewAdapter
            viewModel.getPrivateRoom(participantId).asLiveData().observe(viewLifecycleOwner) {
                val action =
                    RoomFragmentDirections.actionRoomFragmentSelf(it.id)
                findNavController().navigate(action)
            }
        }



        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {


                val room = viewModel.getRoom(roomId)
//                    if (room == null) return@collect
                    toolbar.apply {
                        title = room.name ?: ""
                        setNavigationOnClickListener { findNavController().popBackStack() }
                        setOnMenuItemClickListener {
                            binding.container.openDrawer(GravityCompat.END)
                            true
                        }
                        if (!room.isGroup) {
                            binding.container.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                            menu.clear()
                        }
                    }

                    participantsRecyclerView.layoutManager = LinearLayoutManager(context)
                    participantsRecyclerView.adapter = participantsAdapter
                    launch {
                        viewModel.getUsers(room.participants).collect {
                            it.let { participantsAdapter.submitList(it) }
                        }
                    }

                    val adapter = RoomRecyclerViewAdapter(room.isGroup, (activity as MainActivity)::showImagePreview)
                    launch {
                        viewModel.getMessages(roomId).collect {

                            val lastIndex =
                                (binding.messageRecyclerView.layoutManager as LinearLayoutManager)
                                    .findLastCompletelyVisibleItemPosition()
                            it.let { adapter.submitList(it) }

                            binding.messageRecyclerView.scrollToPosition(adapter.itemCount - room.unread)

                            viewModel.resetUnreadCount(roomId)
                            if (lastIndex == adapter.itemCount - 2) {
                                binding.messageRecyclerView.scrollToPosition(adapter.itemCount - 1)
                            }
                        }
                    }
                    messageRecyclerView.layoutManager =
                        LinearLayoutManager(context).apply { stackFromEnd = true }
                    messageRecyclerView.adapter = adapter

                    messageRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                                .findLastCompletelyVisibleItemPosition()
                            if (lastIndex == adapter.itemCount - 1) {
                                scrollButton.visibility = View.GONE
                            } else {
                                scrollButton.visibility = View.VISIBLE
                            }
                        }
                    })
                    scrollButton.setOnClickListener {
                        messageRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
                    }

            }

            sendButton.setOnClickListener {
                val message = messageField.text.toString()

                if (message.isNotEmpty()) {
                    viewModel.sendMessage(messageField.text.toString(), roomId)
                }
                messageField.text.clear()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.drawable.solid_background)
    }
}