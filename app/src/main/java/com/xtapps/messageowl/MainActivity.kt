package com.xtapps.messageowl

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val appBar: MaterialToolbar = binding.toolbar
        appBar.setOnMenuItemClickListener {
            binding.container.openDrawer(Gravity.RIGHT)
            true
        ***REMOVED***
        binding.button2.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        ***REMOVED***

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        val options = NavOptions.Builder()
//            .setLaunchSingleTop(true)
//            .setEnterAnim(android.R.anim.fade_in)
//            .setExitAnim(android.R.anim.fade_out)
//            .setPopEnterAnim(android.R.anim.fade_in)
//            .setPopExitAnim(android.R.anim.fade_out)
//            .build()
//        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener {
            appBar.title = it.title
            navController.navigate(it.itemId)
            true
        ***REMOVED***
    ***REMOVED***

***REMOVED***