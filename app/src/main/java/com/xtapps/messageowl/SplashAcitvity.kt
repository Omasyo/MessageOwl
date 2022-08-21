package com.xtapps.messageowl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.ui.auth.AuthActivity

class SplashAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitvity)

        Intent(this, MainService::class.java).also { intent ->
            startService(intent)
        }

        FirebaseAuth.getInstance().addAuthStateListener {
            val isAuth = FirebaseAuth.getInstance().currentUser != null
            val intent = if (isAuth) {
                //TODO: if profile incomplete sign out
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, AuthActivity::class.java)
            }

            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }
}