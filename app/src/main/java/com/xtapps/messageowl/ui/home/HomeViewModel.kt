package com.xtapps.messageowl.ui.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {


    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()
***REMOVED***