package com.xtapps.messageowl

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.databinding.ActivityMainBinding
import com.xtapps.messageowl.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Add auth status listener
        listenToAuthState()

    ***REMOVED***

    private fun listenToAuthState() {
        val listener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if(user == null) {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            ***REMOVED***
        ***REMOVED***
        FirebaseAuth.getInstance().addAuthStateListener(listener)
    ***REMOVED***
***REMOVED***