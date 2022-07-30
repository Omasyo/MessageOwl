package com.xtapps.messageowl.ui.home

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.UserModel
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
            }
        }
    }

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()

}

class HomeViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}