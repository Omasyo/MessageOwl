package com.xtapps.messageowl.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*

class RoomViewModel(
    private val messageDao: MessageDao,
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao
) : ViewModel() {
    fun getMessages(roomId: String) = messageDao.getMessages(roomId)

    fun getRoom(roomId: String) = chatRoomDao.getRoom(roomId)

    fun getPrivateRoom(participantId: String) = chatRoomDao.getPrivateRoom("%$participantId%")
        .combine(userDao.getUsername(participantId)) { room: ChatRoom?, username ->
            if (room == null) {
                val tempRoom = ChatRoom(
                    id = "temp" + Calendar.getInstance().timeInMillis.toString(),
                    name = username,
                    isGroup = false,
                    participants = listOf(authUser.uid, participantId),
                    unread = 0
                )
                chatRoomDao.insertRoom(tempRoom)
                tempRoom
            } else {
                room
            }
        }
//        .map { room: ChatRoom? ->
//            if (room == null) {
//                val tempRoom = ChatRoom(
//                    id = "temp" + Calendar.getInstance().timeInMillis.toString(),
//                    name = null,
//                    isGroup = false,
//                    participants = listOf(authUser.uid, participantId)
//                )
//                chatRoomDao.insertRoom(tempRoom)
//                tempRoom
//            } else {
//                room
//            }
//        }

    fun sendMessage(
        content: String, roomId: String,
    ) {
        viewModelScope.launch {
            messageDao.insertMessage(
                MessageModel(
                    id = Calendar.getInstance().timeInMillis.toString(),
                    roomId = roomId,
                    senderId = authUser.uid,
                    content = content,
                    timestamp = Date()
                )
            )
        }
    }

    fun getUsers(senderIds: List<String>) = userDao.getUsers(senderIds)

    companion object {
        val authUser = FirebaseAuth.getInstance().currentUser!!
    }

//    fun sendMessage()

//    fun getParticipants(roomId: String): Flow<List<String>> {
//        return chatRoomDao.getParticipants(roomId)
//    }
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