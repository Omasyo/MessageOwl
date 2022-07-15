package com.xtapps.messageowl

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.databinding.ActivityMainBinding
import com.xtapps.messageowl.ui.auth.AuthActivity
import com.xtapps.messageowl.ui.home.HomeFragmentDirections


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder(StrictMode.getVmPolicy())
//                .detectLeakedClosableObjects()
//                .build()
//        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (fragment != null) {
            navController = fragment.findNavController()
        ***REMOVED***
        if(false) {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToCompleteProfileFragmentFirst())
        ***REMOVED***

    ***REMOVED***

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2) {
            grantResults.forEach {
                if(it != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
        ***REMOVED***.show()
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***