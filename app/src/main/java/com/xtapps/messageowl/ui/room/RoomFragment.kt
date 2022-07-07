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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val roomId = arguments?.getString("room_id")
        val isGroup = arguments?.getBoolean("is_group")

        binding.sendButton.setOnClickListener {
            Log.d("TAG", "Datetime: ${Date()} ")
        }

        binding.toolbar.title = roomId.toString()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            binding.container.openDrawer(GravityCompat.END)
            true
        }

        val viewModel: RoomViewModel by activityViewModels {
            RoomViewModelFactory(
                (activity?.application as MessageOwlApplication).appDatabase.messageDao(),
                roomId!!
            )
        }

        val adapter = RoomRecyclerViewAdapter(isGroup!!)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition()
                if(lastIndex == adapter.itemCount - 1) {
                    binding.scrollButton.visibility = View.GONE
                } else {
                    binding.scrollButton.visibility = View.VISIBLE
                }
            }
        })
        binding.scrollButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        viewModel.messages.asLiveData().observe(viewLifecycleOwner) {
            val lastIndex = (recyclerView.layoutManager as LinearLayoutManager)
                .findLastCompletelyVisibleItemPosition()
            it.let { adapter.submitList(it) }
            if(lastIndex == adapter.itemCount - 2) {
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            } else {

            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.drawable.solid_background)
    }
}