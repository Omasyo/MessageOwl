package com.xtapps.messageowl.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xtapps.messageowl.R

class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

//    override fun onBackPressed() {
//        println("QWER ${supportFragmentManager.backStackEntryCount}")
//        if (supportFragmentManager.backStackEntryCount > 0) {
//            println("QWER of")
//            FirebaseAuth.getInstance().signOut()
//            supportFragmentManager.popBackStack(R.id.welcomeFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        } else {
//            println("QWER else")
//            finish()
//        }
//    }
}