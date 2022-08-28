package com.xtapps.messageowl

import android.graphics.Bitmap
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ChatRoomUpdate
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.UserModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.launch
import java.io.File
import java.sql.Timestamp
import java.util.*

const val TAG = "MainViewModel"

class MainViewModel(
    private val application: MessageOwlApplication
) : AndroidViewModel(application) {
    val userDao = application.appDatabase.userDao()

    private val authUser get() = Firebase.auth.currentUser!!
    private val userData = Firebase.firestore.collection("users").document(authUser.uid)
    private val roomDb = Firebase.firestore.collection("rooms")

    private var _profilePicFileName: String? = null

    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()

    val currentUser: LiveData<UserModel?> = _currentUser

    init {
        viewModelScope.launch {
            launch {
                userDao.getUser(authUser.uid).collect { user: UserModel? ->
                    _currentUser.value = user
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            ***REMOVED***

            if (snapshot != null && snapshot.exists()) {
                val rooms = snapshot.data?.get("rooms") as ArrayList<String>? ?: arrayListOf()
                for (room in rooms) {
                    listentoMessageUpdates(room)
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

//        listentoMessageUpdates("general")
//
//        listentoMessageUpdates("DeL3RDsOliZEXuFQRWdoHkL9vr82Zs377WUKDxhIDYTcvOwYoOkt0xV2")
        listentoUserUpdates()
    ***REMOVED***

    fun listentoUserUpdates() = userData.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
            return@addSnapshotListener
        ***REMOVED***
//        if( snapshot == null || snapshot.exists()) {
//            Log.w(com.xtapps.messageowl.ui.contacts.TAG, "snapshot does not exits")
//            return@addSnapshotListener
//        ***REMOVED***

        viewModelScope.launch {
            userDao.insertUser(
                UserModel(
                    id = authUser.uid,
                    name = (snapshot?.get("name") ?: "") as String,
                    phoneNo = authUser.phoneNumber!!,
                    profilePic = snapshot?.get("profilePic") as String?
    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***

//Todo: temp remove later
    fun listentoMessageUpdates(roomId: String) =
        roomDb.document(roomId).collection("messages").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            ***REMOVED***

            if (snapshots != null) {
                for(dc in snapshots.documentChanges) {
                    val document = dc.document
                    viewModelScope.launch {
                        launch {
                            application.appDatabase.messageDao().insertMessage(
                                MessageModel(
                                    id = document.id,
                                    roomId = roomId,
                                    content = document["content"] as String,
                                    senderId = document["sender"] as String,
                                    timestamp = document.getDate("time")!!,
                    ***REMOVED***
                ***REMOVED***
                        ***REMOVED***
                        launch {
//                            application.appDatabase.chatRoomDao().
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***

            ***REMOVED***
        ***REMOVED***

    private fun generateProfileRef(): String {
        return Firebase.storage.reference
            .child("profilePics/${FirebaseAuth.getInstance().currentUser?.uid***REMOVED***")
            .path
    ***REMOVED***

    suspend fun compressAndUpload(file: File) {
        val compressedImageFile = Compressor.compress(
            application.applicationContext,
            file
        ) {
            resolution(612, 816)
            format(Bitmap.CompressFormat.JPEG)
            quality(30)
        ***REMOVED***

        _profilePicFileName = generateProfileRef()
        val ref = Firebase.storage.reference.child(_profilePicFileName!!)
        ref.putFile(compressedImageFile.toUri()).onSuccessTask {
            ref.downloadUrl.onSuccessTask {
                userData.update("profilePic", it)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun createUser(name: String) {

        userData.set(
            hashMapOf(
                "name" to name,
                "phoneNo" to authUser.phoneNumber!!,
//                "profilePic" to _profilePicRef,
                "rooms" to arrayListOf("general")
***REMOVED***, SetOptions.merge()
        ).addOnFailureListener {
            viewModelScope.launch {
                userDao.deleteUser(authUser.uid)
            ***REMOVED***
            Log.d(TAG, "createUser: update fire store failed")
        ***REMOVED***
        viewModelScope.launch {
            userDao.insertUser(
                UserModel(
                    id = authUser.uid,
                    name = name,
                    phoneNo = authUser.phoneNumber!!,
                    profilePic = null
    ***REMOVED***
***REMOVED***
        ***REMOVED***
        _profilePicFileName = null
    ***REMOVED***

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()
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