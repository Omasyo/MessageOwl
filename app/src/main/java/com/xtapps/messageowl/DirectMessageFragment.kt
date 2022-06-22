package com.xtapps.messageowl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xtapps.messageowl.databinding.FragmentCompleteProfileBinding
import com.xtapps.messageowl.databinding.FragmentDirectMessageBinding

class DirectMessageFragment : Fragment() {

    private var _binding: FragmentDirectMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDirectMessageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.textView.setText(arguments?.getString("sampleText").toString())
        return binding.root
    }

}