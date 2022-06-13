package com.xtapps.messageowl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class SplashAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitvity)

        val isAuth = FirebaseAuth.getInstance().currentUser != null
        val intent = if (isAuth) {
            Intent(this, MainActivity::class.java)
        ***REMOVED*** else {
            Intent(this, AuthActivity::class.java)
        ***REMOVED***

        startActivity(intent)
        finish()
    ***REMOVED***
***REMOVED***