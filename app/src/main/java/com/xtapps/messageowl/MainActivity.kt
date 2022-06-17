package com.xtapps.messageowl

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.toolbar.title = getString(R.string.chats)
        setContentView(binding.root)

        //Add auth status listener
        val listener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if(user == null) {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            ***REMOVED***
        ***REMOVED***
        FirebaseAuth.getInstance().addAuthStateListener(listener)

        val navView: BottomNavigationView = binding.navView
        val appBar: MaterialToolbar = binding.toolbar
        appBar.setOnMenuItemClickListener {
            binding.container.openDrawer(GravityCompat.END)
            true
        ***REMOVED***
        binding.button2.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        ***REMOVED***

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setOnItemSelectedListener {
            appBar.title = it.title
            navController.popBackStack()
            navController.navigate(it.itemId)
            true
        ***REMOVED***
    ***REMOVED***

***REMOVED***