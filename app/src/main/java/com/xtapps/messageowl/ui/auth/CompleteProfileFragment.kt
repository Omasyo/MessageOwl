package com.xtapps.messageowl.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.AuthActivity
import com.xtapps.messageowl.MainActivity
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.FragmentCompleteProfileBinding
import com.xtapps.messageowl.databinding.FragmentVerifyBinding

class CompleteProfileFragment : Fragment() {

    private lateinit var _binding: FragmentCompleteProfileBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteProfileBinding.inflate(inflater, container, false)
        _binding.proceedButton.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
        _binding.backButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().popBackStack(R.id.welcomeFragment, false)
        }

        return _binding.root
    }
}