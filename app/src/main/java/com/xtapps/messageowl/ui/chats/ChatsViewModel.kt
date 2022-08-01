package com.xtapps.messageowl.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatCardModel
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.MessageWithSender
import kotlinx.coroutines.flow.*

class ChatsViewModel(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao
) : ViewModel() {

    fun allChats(): Flow<List<ChatCardModel>> = chatRoomDao.getChatCards()

    fun recentMessage(roomId: String): Flow<MessageWithSender>
    = messageDao.getRecentMessage(roomId)

    companion object {
        val authUser = FirebaseAuth.getInstance().currentUser!!
    ***REMOVED***
***REMOVED***

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao, messageDao, userDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***