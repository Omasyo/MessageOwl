package com.xtapps.messageowl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    ***REMOVED***

//    override fun onBackPressed() {
//        println("QWER ${supportFragmentManager.backStackEntryCount***REMOVED***")
//        if (supportFragmentManager.backStackEntryCount > 0) {
//            println("QWER of")
//            FirebaseAuth.getInstance().signOut()
//            supportFragmentManager.popBackStack(R.id.welcomeFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        ***REMOVED*** else {
//            println("QWER else")
//            finish()
//        ***REMOVED***
//    ***REMOVED***
***REMOVED***