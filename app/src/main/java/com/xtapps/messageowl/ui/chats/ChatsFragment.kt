package com.xtapps.messageowl.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.databinding.FragmentChatsBinding
import com.xtapps.messageowl.ui.home.HomeFragmentDirections
import kotlinx.coroutines.launch

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ChatsViewModel by activityViewModels {
        ChatsViewModelFactory(
            (activity?.application as MessageOwlApplication).appDatabase.chatRoomDao()
        )
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ChatsRecyclerViewAdapter { view, roomId, isGroup ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToRoomFragment(roomId, isGroup)
            view.findNavController().navigate(action)
        ***REMOVED***

        recyclerView = binding.chatsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allChats().flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    it.let { adapter.submitList(it) ***REMOVED***
                ***REMOVED***
        ***REMOVED***
//        viewModel.allChats().asLiveData().observe(viewLifecycleOwner) {
//            it.let { adapter.submitList(it) ***REMOVED***
//        ***REMOVED***

        return root
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    ***REMOVED***
***REMOVED***