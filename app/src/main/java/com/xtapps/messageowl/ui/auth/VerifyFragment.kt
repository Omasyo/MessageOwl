package com.xtapps.messageowl.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.xtapps.messageowl.AuthActivity
import com.xtapps.messageowl.AuthViewModel
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {

    private lateinit var _binding: FragmentVerifyBinding

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        _binding.proceedButton.setOnClickListener {
            Toast.makeText(context, _binding.otpEditText.text, Toast.LENGTH_SHORT)
                .show()
            viewModel.verifyNumber(_binding.otpEditText.text.toString(),
                this.activity as AuthActivity
***REMOVED***
//            findNavController().navigate(R.id.action_verifyFragment_to_completeProfileFragment)
        ***REMOVED***

        return _binding.root
    ***REMOVED***
***REMOVED***