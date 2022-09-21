package com.omaka.messageowl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.Coil
import coil.ImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.omaka.messageowl.ui.auth.AuthActivity

class SplashAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitvity)

        val imageLoader = ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .build()
        Coil.setImageLoader(imageLoader)


        FirebaseAuth.getInstance().addAuthStateListener {
            val isAuth = FirebaseAuth.getInstance().currentUser != null
            val intent = if (isAuth) {

                //TODO: if profile incomplete sign out
                Intent(this, MainActivity::class.java)
            ***REMOVED*** else {
                Intent(this, AuthActivity::class.java)
            ***REMOVED***

            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        ***REMOVED***
    ***REMOVED***
***REMOVED***