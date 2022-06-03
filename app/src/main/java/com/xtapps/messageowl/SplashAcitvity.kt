package com.xtapps.messageowl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitvity)

        val isAuth = false
        val intent = if (isAuth) {
            Intent(this, MainActivity::class.java)
        ***REMOVED*** else {
            Intent(this, AuthActivity::class.java)
        ***REMOVED***

        startActivity(intent)
        finish()
    ***REMOVED***
***REMOVED***