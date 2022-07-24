package com.xtapps.messageowl

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()
    //    UserModel("1", "Haha", "090")
    val currentUser = _currentUser

    init {
        viewModelScope.launch {
            userDao.getUser("0").collect {user: UserModel? ->
                _currentUser.value = user
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()

***REMOVED***

class MainViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***