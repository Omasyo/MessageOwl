package com.xtapps.messageowl.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatWithRecent
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.MessageWithSender
import kotlinx.coroutines.flow.*

class ChatsViewModel(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao
) : ViewModel() {

    fun allChats(): Flow<List<ChatRoom>> = chatRoomDao.getNonEmptyRooms()

    fun recentMessage(roomId: String): Flow<MessageWithSender>
    = messageDao.getRecentMessage(roomId)

//        .map { list ->
//            list.map {
//                if (it.name == null) {
//                    val friendId = it.participants.find { id -> id != authUser.uid }
//                    userDao.getUser(friendId!!).collect {
//
//                    }
//                    it
//                } else {
//                    it
//                }
//            }
//        }
//        .combine(messageDao.getAllUsers()) { roomList, userList ->
//            roomList.map {
//                if(it.isGroup) it
//                else {
//                    it.copy(name = user)
//                }
//            }
//        }

    companion object {
        val authUser = FirebaseAuth.getInstance().currentUser!!
    }
}

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao, messageDao, userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}