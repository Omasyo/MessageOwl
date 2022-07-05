package com.xtapps.messageowl.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.xtapps.messageowl.R
import com.xtapps.messageowl.databinding.ActivityAuthBinding
import java.util.concurrent.TimeUnit

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.auth_fragment)
        if (fragment != null) {
            navController = fragment.findNavController()
        ***REMOVED***
    ***REMOVED***

    override fun onBackPressed() {
        if(FirebaseAuth.getInstance().currentUser != null) {
            navController.navigate(R.id.action_completeProfileFragment_to_welcomeFragment)
        ***REMOVED***
    ***REMOVED***

    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
        ***REMOVED***

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            ***REMOVED*** else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            ***REMOVED***
            // Show a message and update the UI
        ***REMOVED***

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token

            navController.navigate(R.id.action_welcomeFragment_to_verifyFragment)
        ***REMOVED***
    ***REMOVED***

    fun sendVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    ***REMOVED***

    fun verifyNumber(code: String) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        val user = FirebaseAuth.getInstance().currentUser

        val onCompleteListener = { task: Task<*> ->
            if (task.isSuccessful) {
                navController.navigate(R.id.action_verifyFragment_to_completeProfileFragment)
            ***REMOVED*** else {
                var errorMessage = "An error occurred"
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                    errorMessage = "The verification code entered was invalid"
                ***REMOVED*** else if (task.exception is FirebaseAuthUserCollisionException) {
                    errorMessage = "A user already exists with this number"
                ***REMOVED***
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
                    .show()
            ***REMOVED***
        ***REMOVED***

        if (user == null) { //Log new/different user
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, onCompleteListener)
        ***REMOVED*** else { //Update user number
            user.updatePhoneNumber(credential).addOnCompleteListener(this, onCompleteListener)
        ***REMOVED***
        //TODO HANDLE EXCEPTION FirebaseAuthUserCollisionException thrown if there already exists an account with the given phone number
        //TODO FirebaseAuthRecentLoginRequiredException thrown if the user's last sign-in time does not meet the security threshold. Use reauthenticate(AuthCredential) to resolve. This does not apply if the user is anonymous.
        // todo FirebaseAuthInvalidUserException  thrown if the current user's account has been disabled, deleted, or its credentials are no longer valid
    ***REMOVED***
***REMOVED***