package com.omaka.messageowl.ui.auth

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.omaka.messageowl.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var logoAnimation: AnimatedVectorDrawable

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    ***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        val logoImage = binding.logoImage.apply {
            logoAnimation = drawable as AnimatedVectorDrawable
        ***REMOVED***
        logoAnimation.apply {
            registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    logoAnimation.start()
                ***REMOVED***
            ***REMOVED***)
            start()
        ***REMOVED***


        val ccp = binding.countryCode
        val carrierText = binding.carrierNumber
        ccp.registerCarrierNumberEditText(carrierText)

        ccp.defaultCountryCodeWithPlus

        binding.proceedButton.setOnClickListener {
            if (ccp.isValidFullNumber) {
                (activity as AuthActivity).sendVerification(ccp.fullNumberWithPlus)
            ***REMOVED*** else {
                Snackbar.make(
                    binding.root,
                    "Please enter a valid phone number",
                    Snackbar.LENGTH_LONG
    ***REMOVED***
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