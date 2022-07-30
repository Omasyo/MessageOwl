package com.xtapps.messageowl

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.models.UserModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class MainViewModel(
    private val application: MessageOwlApplication
) : AndroidViewModel(application) {
    val userDao = application.appDatabase.userDao()


    private var _profilePhoto: MutableLiveData<Uri> = MutableLiveData()
    val profilePhoto: LiveData<Uri> = _profilePhoto

    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()

    //    UserModel("1", "Haha", "090")
    val currentUser: LiveData<UserModel?> = _currentUser

    init {
        viewModelScope.launch {
            userDao.getUser(authUser.uid).collect { user: UserModel? ->
                _currentUser.value = user
            ***REMOVED***
        ***REMOVED***

//        val file = File.createTempFile(
//            "profile",
//            ".jpg",
//            application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        )
//
//        profilePicRef.getFile(file).addOnCompleteListener {
//            if(it.isSuccessful) {
//                _profilePhoto.value = file.toUri()
//            ***REMOVED*** else {
//                Toast.makeText(
//                    application, application.resources.getString(R.string.photo_download_error),
//                    Toast.LENGTH_LONG
//    ***REMOVED***.show()
//            ***REMOVED***
//        ***REMOVED***
    ***REMOVED***

    private fun generateProfileRef(): String {
        return Firebase.storage.reference
            .child("profilePics/${FirebaseAuth.getInstance().currentUser?.uid***REMOVED***${Date()***REMOVED***")
            .path
    ***REMOVED***

    suspend fun compressAndUpload(file: File): String {
        val compressedImageFile = Compressor.compress(
            application.applicationContext,
            file
        ) {
            resolution(612, 816)
            format(Bitmap.CompressFormat.JPEG)
            quality(30)
        ***REMOVED***

        val profilePicRef = generateProfileRef()
        Firebase.storage.reference.child(profilePicRef).putFile(compressedImageFile.toUri())
            .addOnFailureListener {
            ***REMOVED***
        return profilePicRef
    ***REMOVED***

    fun createUser(name: String, profilePic: String?) {

        viewModelScope.launch {
            userDao.insertUser(
                UserModel(
                    id = authUser.uid,
                    name = name,
                    phoneNo = authUser.phoneNumber!!,
                    profilePic = profilePic
    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun addToForum() {
//        c
    ***REMOVED***

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()


    companion object {
        val authUser = FirebaseAuth.getInstance().currentUser!!
    ***REMOVED***

***REMOVED***

class MainViewModelFactory(
    private val application: MessageOwlApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***