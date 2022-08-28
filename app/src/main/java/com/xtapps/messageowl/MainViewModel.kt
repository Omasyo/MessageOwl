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
                }
            }
        }

        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val rooms = snapshot.data?.get("rooms") as ArrayList<String>? ?: arrayListOf()
                for (room in rooms) {
                    listentoMessageUpdates(room)
                }
            }
        }

//        listentoMessageUpdates("general")
//
//        listentoMessageUpdates("DeL3RDsOliZEXuFQRWdoHkL9vr82Zs377WUKDxhIDYTcvOwYoOkt0xV2")
        listentoUserUpdates()
    }

    fun listentoUserUpdates() = userData.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
            return@addSnapshotListener
        }
//        if( snapshot == null || snapshot.exists()) {
//            Log.w(com.xtapps.messageowl.ui.contacts.TAG, "snapshot does not exits")
//            return@addSnapshotListener
//        }

        viewModelScope.launch {
            userDao.insertUser(
                UserModel(
                    id = authUser.uid,
                    name = (snapshot?.get("name") ?: "") as String,
                    phoneNo = authUser.phoneNumber!!,
                    profilePic = snapshot?.get("profilePic") as String?
                )
            )
        }
    }

//Todo: temp remove later
    fun listentoMessageUpdates(roomId: String) =
        roomDb.document(roomId).collection("messages").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

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
                                )
                            )
                        }
                        launch {
//                            application.appDatabase.chatRoomDao().
                        }
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

        _profilePicFileName = generateProfileRef()
        val ref = Firebase.storage.reference.child(_profilePicFileName!!)
        ref.putFile(compressedImageFile.toUri()).onSuccessTask {
            ref.downloadUrl.onSuccessTask {
                userData.update("profilePic", it)
            }
        }
    }

    fun createUser(name: String) {

        userData.set(
            hashMapOf(
                "name" to name,
                "phoneNo" to authUser.phoneNumber!!,
//                "profilePic" to _profilePicRef,
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
                    profilePic = null
                )
            )
        }
        _profilePicFileName = null
    }

    fun signOutUser() =
        FirebaseAuth.getInstance().signOut()
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