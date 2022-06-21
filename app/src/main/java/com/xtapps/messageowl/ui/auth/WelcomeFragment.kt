package com.xtapps.messageowl.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        val ccp = binding.countryCode
        val carrierText = binding.carrierNumber
        ccp.registerCarrierNumberEditText(carrierText)

        binding.proceedButton.setOnClickListener {
            if(ccp.isValidFullNumber) {
                viewModel.sendVerification(ccp.fullNumberWithPlus, this.activity as AuthActivity)

                findNavController().navigate(R.id.action_welcomeFragment_to_verifyFragment)
            ***REMOVED*** else {
                Snackbar.make(binding.root, "Please enter a valid phone number" ,Snackbar.LENGTH_LONG)
                    .show()
            ***REMOVED***
        ***REMOVED***

        return binding.root
    ***REMOVED***

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    ***REMOVED***

***REMOVED***