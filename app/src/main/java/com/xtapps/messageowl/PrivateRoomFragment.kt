package com.xtapps.messageowl

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.databinding.FragmentPrivateRoomBinding

class PrivateRoomFragment : Fragment() {

    private var _binding: FragmentPrivateRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivateRoomBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.toolbar.title = arguments?.getString("sampleText")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({ //Hack for flashing appbar
            binding.appBarImage.alpha = 1.0f
        }, 500)

        super.onViewCreated(view, savedInstanceState)
    }

}