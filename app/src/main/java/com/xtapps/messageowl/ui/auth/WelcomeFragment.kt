package com.xtapps.messageowl.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.hbb20.CountryCodePicker
import com.xtapps.messageowl.AuthActivity
import com.xtapps.messageowl.AuthViewModel
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var _binding: FragmentWelcomeBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        val ccp = _binding.countryCode
        val carrierText = _binding.carrierNumber
        ccp.registerCarrierNumberEditText(carrierText)

        _binding.proceedButton.setOnClickListener {
            if(ccp.isValidFullNumber) {
                viewModel.sendVerification(ccp.fullNumberWithPlus, this.activity as AuthActivity)

                findNavController().navigate(R.id.action_welcomeFragment_to_verifyFragment)
            } else {
                Snackbar.make(_binding.root, "Please enter a valid phone number" ,Snackbar.LENGTH_LONG)
                    .show()
//                Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_LONG)
//                    .show()
            }
        }

        return _binding.root
    }

}