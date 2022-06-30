package com.xtapps.messageowl.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.MessageOwlApplication
import com.xtapps.messageowl.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ChatsViewModel by activityViewModels {
        ChatsViewModelFactory(
            (activity?.application as MessageOwlApplication).chatRoomDatabase.chatRoomDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        recyclerView = binding.chatsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.allChats().asLiveData().observe(viewLifecycleOwner) {
            recyclerView.adapter = ChatsRecyclerViewAdapter(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}