package com.xtapps.messageowl.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.xtapps.messageowl.MainActivity
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    lateinit var binding: FragmentChatsBinding


    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[ChatsViewModel::class.java]

        binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.chatsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ChatsRecycleViewAdapter()
        return root
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
    ***REMOVED***
***REMOVED***