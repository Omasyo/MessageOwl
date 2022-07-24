package com.xtapps.messageowl.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.map
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
        .map { room: ChatRoom? ->
            if (room == null) {
                val tempRoom = ChatRoom(
                    id = Calendar.getInstance().timeInMillis.toString(),
                    name = null,
                    isGroup = false,
                    participants = listOf("1", participantId)
    ***REMOVED***
                chatRoomDao.insertRoom(tempRoom)
                tempRoom
            ***REMOVED*** else {
                room
            ***REMOVED***
        ***REMOVED***

    fun sendMessage(
        content: String, roomId: String,
    ) {
        viewModelScope.launch {
            messageDao.insertMessage(
                MessageModel(
                    id = Calendar.getInstance().timeInMillis.toString(),
                    roomId = roomId,
                    senderId = "1",
                    content = content,
                    timestamp = Date()
    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun getUsers(senderIds: List<String>) = userDao.getUsers(senderIds)

//    fun sendMessage()

//    fun getParticipants(roomId: String): Flow<List<String>> {
//        return chatRoomDao.getParticipants(roomId)
//    ***REMOVED***
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