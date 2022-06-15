package com.xtapps.messageowl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.xtapps.messageowl.ui.auth.CompleteProfileFragment

class SplashAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitvity)

        val isAuth = FirebaseAuth.getInstance().currentUser != null
        val intent = if (isAuth) {
            //TODO: if profile incomplete sign out
            Intent(this, MainActivity::class.java)
        ***REMOVED*** else {
            Intent(this, AuthActivity::class.java)
        ***REMOVED***

        val listener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if(user == null) {
                startActivity(Intent(this, AuthActivity::class.java))
            ***REMOVED***
            finish()
        ***REMOVED***
        FirebaseAuth.getInstance().addAuthStateListener(listener)

        startActivity(intent)
        finish()
    ***REMOVED***
***REMOVED***