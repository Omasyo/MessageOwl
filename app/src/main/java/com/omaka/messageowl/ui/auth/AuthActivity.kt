package com.omaka.messageowl.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.omaka.messageowl.databinding.ActivityAuthBinding
import java.util.concurrent.TimeUnit
import com.omaka.messageowl.R

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
        }
    }

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
//            Log.d(TAG, "onVerificationCompleted:$credential")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token


            binding.loadingIndicator.visibility = View.GONE
            navController.navigate(R.id.action_welcomeFragment_to_verifyFragment)
        }
    }

    fun sendVerification(phoneNumber: String) {
        binding.loadingIndicator.visibility = View.VISIBLE

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyNumber(code: String) {

        binding.loadingIndicator.visibility = View.VISIBLE

        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        val user = FirebaseAuth.getInstance().currentUser

        val onCompleteListener = { task: Task<*> ->

            binding.loadingIndicator.visibility = View.GONE
            if (task.isSuccessful) {
//                navController.navigate(R.id.action_verifyFragment_to_completeProfileFragment)
            } else {
                var errorMessage = resources.getString(R.string.error_message)
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                    errorMessage = resources.getString(R.string.invalid_verification_code)
                } else if (task.exception is FirebaseAuthUserCollisionException) {
                    errorMessage = resources.getString(R.string.user_exists)
                }
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        if (user == null) { //Log new/different user
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, onCompleteListener)
//                .addOnSuccessListener(this) {
//                    val isNewUser = it.additionalUserInfo!!.isNewUser
//                    Log.d(TAG, "verifyNumber: isNewUser $isNewUser")
//                    if(isNewUser) {
//                        val forumRef = Firebase.firestore.collection("rooms").document("general")
//                        forumRef.update("participants", FieldValue.arrayUnion(user?.uid))
//                    }
//                }
        } else { //Update user number
            user.updatePhoneNumber(credential).addOnCompleteListener(this, onCompleteListener)
        }
    }
}