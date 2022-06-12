package com.xtapps.messageowl.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.xtapps.messageowl.AuthActivity
import com.xtapps.messageowl.AuthViewModel
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var _binding: FragmentWelcomeBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        val ccp = _binding.countryCode as CountryCodePicker
        val carrierText = _binding.carrierNumber as EditText
        ccp.registerCarrierNumberEditText(carrierText)

        _binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_verifyFragment)
            viewModel.sendVerification(ccp.fullNumberWithPlus, this.activity as AuthActivity)
            Toast.makeText(context, ccp.fullNumberWithPlus, Toast.LENGTH_SHORT)
                .show()
        ***REMOVED***

        return _binding.root
    ***REMOVED***

***REMOVED***