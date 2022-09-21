package com.omaka.messageowl.ui.home

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.omaka.messageowl.database.UserDao
import com.omaka.messageowl.models.UserModel
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