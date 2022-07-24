package com.xtapps.messageowl.ui.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

class RoomViewModel(
    private val messageDao: MessageDao,
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao
) : ViewModel() {
    fun getMessages(roomId: String) = messageDao.getMessages(roomId)

    fun getRoom(roomId: String) = chatRoomDao.getRoom(roomId)

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