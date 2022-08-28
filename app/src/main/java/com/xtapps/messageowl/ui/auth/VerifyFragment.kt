package com.xtapps.messageowl.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)

        binding.proceedButton.setOnClickListener {
            (activity as AuthActivity).verifyNumber(binding.otpEditText.text.toString())
        ***REMOVED***
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        ***REMOVED***

        return binding.root
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    ***REMOVED***
***REMOVED***