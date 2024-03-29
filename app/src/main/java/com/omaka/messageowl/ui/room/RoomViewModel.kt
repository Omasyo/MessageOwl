package com.omaka.messageowl.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.omaka.messageowl.database.ChatRoomDao
import com.omaka.messageowl.database.MessageDao
import com.omaka.messageowl.database.UserDao
import com.omaka.messageowl.domain.requestPrivateRoom
import com.omaka.messageowl.models.ChatRoom
import com.omaka.messageowl.models.deletedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class RoomViewModel(
    private val messageDao: MessageDao,
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao
) : ViewModel() {

    val authUser get() = FirebaseAuth.getInstance().currentUser!!

    fun getMessages(roomId: String) = messageDao.getMessages(roomId).map { list ->
        list.map { it ->
            if (it.user == null) it.copy(user = deletedUser) else it
        }
    }

    suspend fun getRoom(roomId: String): ChatRoom {
        val room = chatRoomDao.getRoomDetails(roomId)

        return if (!room.isGroup) {
            val friend = room.participants.first { it != authUser.uid }
            val details = userDao.getUserDetails(friend)
            room.copy(name = details?.name)
        } else room
    }

//    fun getRoom(roomId: String) = chatRoomDao.getRoom(roomId).map { room ->
//        if (!room.isGroup) {
//            val friend = room.participants.first { it != authUser.uid }
//            val details = userDao.getUserDetails(friend)
//            room.copy(name = details.name)
//        } else room
//    }


    fun getPrivateRoom(participantId: String) =
        requestPrivateRoom(participantId, chatRoomDao, userDao, messageDao)

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
                )
            )
//            .addOnSuccessListener {
//                viewModelScope.launch {
//                    messageDao.insertMessage(
//                        MessageModel(
//                            id = it.id,
//                            roomId = roomId,
//                            senderId = authUser.uid,
//                            content = content,
//                            timestamp = Date()
//                        )
//                    )
//                }
//            }
    }

    fun getUsers(senderIds: List<String>) = userDao.getUsers(senderIds)

    fun resetUnreadCount(roomId: String) = CoroutineScope(Dispatchers.IO).launch {
        chatRoomDao.resetUnreadCount(roomId)
    }

}

class RoomViewModelFactory(
    private val messageDao: MessageDao,
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(messageDao, chatRoomDao, userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}