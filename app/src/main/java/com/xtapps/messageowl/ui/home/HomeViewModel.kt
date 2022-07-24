package com.xtapps.messageowl.ui.home

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.UserDataSource
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.UserModel
import com.xtapps.messageowl.ui.room.RoomViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "HomeViewModel"

class HomeViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private var _currentUser: MutableLiveData<UserModel> = MutableLiveData()
//    UserModel("1", "Haha", "090")
    val currentUser = _currentUser

    init {
        viewModelScope.launch {
            userDao.getUser("1").collect {
                _currentUser.value = it
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()

***REMOVED***

class HomeViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***