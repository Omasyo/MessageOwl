package com.xtapps.messageowl.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.database.ChatRoomDao
import kotlinx.coroutines.flow.Flow

class ChatsViewModel(private val chatRoomDao: ChatRoomDao) : ViewModel() {

    fun allChats(): Flow<List<ChatRoom>> = chatRoomDao.getAll()
}

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}