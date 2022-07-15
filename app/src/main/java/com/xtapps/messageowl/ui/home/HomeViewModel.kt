package com.xtapps.messageowl.ui.home

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.UserDataSource
import com.xtapps.messageowl.models.UserModel

const val TAG = "HomeViewModel"

class HomeViewModel: ViewModel() {
    private val _currentUser = UserDataSource().userData.asLiveData()

//    val name: LiveData<String> = Firebase.firestore.collection("users")
//        .document(user.phoneNumber).addSnapshotListener()

    val currentUser: LiveData<UserModel> = _currentUser

//    fun updateUser(user: UserModel) {
//        _currentUser.value = user
//    ***REMOVED***

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()


***REMOVED***