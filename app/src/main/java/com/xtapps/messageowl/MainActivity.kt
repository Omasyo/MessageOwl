package com.xtapps.messageowl

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.xtapps.messageowl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
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
            binding.materialToolbar.title = it.title
            navController.navigate(it.itemId)
            true
        }
    }

}