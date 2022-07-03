package com.xtapps.messageowl.ui.auth

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
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by activityViewModels()

    private val _listener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if(user != null) {
            findNavController().navigate(R.id.action_verifyFragment_to_completeProfileFragment)
        ***REMOVED***
    ***REMOVED***

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


        FirebaseAuth.getInstance().addAuthStateListener(_listener)

        binding.proceedButton.setOnClickListener {
            Toast.makeText(context, binding.otpEditText.text, Toast.LENGTH_SHORT)
                .show()
            (activity as AuthActivity).verifyNumber(binding.otpEditText.text.toString())
        ***REMOVED***
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        ***REMOVED***


        return binding.root
    ***REMOVED***

    override fun onDestroyView() {
        FirebaseAuth.getInstance().removeAuthStateListener(_listener)
        super.onDestroyView()
        _binding = null

    ***REMOVED***
***REMOVED***