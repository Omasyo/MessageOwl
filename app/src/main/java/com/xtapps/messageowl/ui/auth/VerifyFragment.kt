package com.xtapps.messageowl.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.AuthActivity
import com.xtapps.messageowl.AuthViewModel
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {

    private lateinit var _binding: FragmentVerifyBinding

    private val viewModel: AuthViewModel by activityViewModels()

    val _listener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if(user != null) {
            findNavController().navigate(R.id.action_verifyFragment_to_completeProfileFragment)
        ***REMOVED***
    ***REMOVED***

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)


        FirebaseAuth.getInstance().addAuthStateListener(_listener)

        _binding.proceedButton.setOnClickListener {
            Toast.makeText(context, _binding.otpEditText.text, Toast.LENGTH_SHORT)
                .show()
            viewModel.verifyNumber(_binding.otpEditText.text.toString(),
                this.activity as AuthActivity
***REMOVED***
        ***REMOVED***
        _binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        ***REMOVED***


        return _binding.root
    ***REMOVED***

    override fun onDestroyView() {
        FirebaseAuth.getInstance().removeAuthStateListener(_listener)
        super.onDestroyView()

    ***REMOVED***
***REMOVED***