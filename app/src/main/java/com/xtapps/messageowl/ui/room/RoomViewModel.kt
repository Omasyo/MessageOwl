package com.xtapps.messageowl.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.domain.requestPrivateRoom
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.deletedUser
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class RoomViewModel(
    private val messageDao: MessageDao,
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao
) : ViewModel() {

    private val authUser get() = FirebaseAuth.getInstance().currentUser!!

    fun getMessages(roomId: String) = messageDao.getMessages(roomId).map { list ->
        list.map { it ->
            if (it.user == null) it.copy(user = deletedUser) else it
        ***REMOVED***
    ***REMOVED***

    fun getRoom(roomId: String) = chatRoomDao.getRoom(roomId)


    fun getPrivateRoom(participantId: String) = requestPrivateRoom(participantId, chatRoomDao, userDao)

    fun sendMessage(
        content: String, roomId: String,
    ) {
        Firebase.firestore.collection("rooms")
            .document(roomId).collection("messages")
            .add(
                hashMapOf(
                    "content" to content,
                    "sender" to authUser.uid,
                    "time" to Date()
    ***REMOVED***
***REMOVED***.addOnSuccessListener {
                viewModelScope.launch {
                    messageDao.insertMessage(
                        MessageModel(
                            id = it.id,
                            roomId = roomId,
                            senderId = authUser.uid,
                            content = content,
                            timestamp = Date()
            ***REMOVED***
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
    ***REMOVED***

    fun getUsers(senderIds: List<String>) = userDao.getUsers(senderIds)

***REMOVED***

class RoomViewModelFactory(
    private val messageDao: MessageDao,
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(messageDao, chatRoomDao, userDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***