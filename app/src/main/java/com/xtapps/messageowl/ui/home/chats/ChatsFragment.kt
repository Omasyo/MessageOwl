package com.xtapps.messageowl.ui.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialSharedAxis
import com.xtapps.messageowl.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[ChatsViewModel::class.java]

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.floatingActionButton.setOnClickListener {
////            (activity as MainActivity).testOpenDrawer()
//            findNavController().navigate(R.id.contactsFragment)
//        }


        recyclerView = binding.chatsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ChatsRecycleViewAdapter()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}