package com.xtapps.messageowl.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class ChatsViewModel(private val chatRoomDao: ChatRoomDao, private val messageDao: MessageDao) : ViewModel() {

    fun allChats(): Flow<List<ChatRoom>> = chatRoomDao.getAll()
//        .combine(messageDao.getAllUsers()) { roomList, userList ->
//            roomList.map {
//                if(it.isGroup) it
//                else {
//                    it.copy(name = user)
//                }
//            }
//        }
}

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao, messageDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}