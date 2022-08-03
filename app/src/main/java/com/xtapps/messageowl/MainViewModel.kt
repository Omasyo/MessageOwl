package com.xtapps.messageowl

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.models.ChatRoomUpdate
import com.xtapps.messageowl.models.UserModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

const val TAG = "MainViewModel"

class MainViewModel(
    private val application: MessageOwlApplication
) : AndroidViewModel(application) {
    val userDao = application.appDatabase.userDao()


    private var _profilePhoto: MutableLiveData<Uri> = MutableLiveData()
    val profilePhoto: LiveData<Uri> = _profilePhoto


    private var _profilePicRef: String? = null

    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()

    //    UserModel("1", "Haha", "090")
    val currentUser: LiveData<UserModel?> = _currentUser

    init {
        viewModelScope.launch {
            launch {
                userDao.getUser(authUser.uid).collect { user: UserModel? ->
                    _currentUser.value = user
                }
            }
        }
    }

    private fun generateProfileRef(): String {
        return Firebase.storage.reference
            .child("profilePics/${FirebaseAuth.getInstance().currentUser?.uid}")
            .path
    }

    suspend fun compressAndUpload(file: File) {
        val compressedImageFile = Compressor.compress(
            application.applicationContext,
            file
        ) {
            resolution(612, 816)
            format(Bitmap.CompressFormat.JPEG)
            quality(30)
        }

        _profilePicRef = generateProfileRef()
        Firebase.storage.reference.child(_profilePicRef!!).putFile(compressedImageFile.toUri())
            .addOnFailureListener {
            }
    }

    fun createUser(name: String) {

        userData.set(
            hashMapOf(
                "name" to name,
                "phoneNo" to authUser.phoneNumber!!,
                "profilePic" to _profilePicRef,
                "rooms" to arrayListOf("general")
            ), SetOptions.merge()
        ).addOnFailureListener {
            viewModelScope.launch {
                userDao.deleteUser(authUser.uid)
            }
            Log.d(TAG, "createUser: update fire store failed")
        }
        viewModelScope.launch {
            userDao.insertUser(
                UserModel(
                    id = authUser.uid,
                    name = name,
                    phoneNo = authUser.phoneNumber!!,
                    profilePic = _profilePicRef
                )
            )
        }
        _profilePicRef = null
    }

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()


    companion object {
        val authUser = Firebase.auth.currentUser!!
        val userData = Firebase.firestore.collection("users").document(authUser.uid)
        val roomDb = Firebase.firestore.collection("rooms")
    }

}

class MainViewModelFactory(
    private val application: MessageOwlApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}