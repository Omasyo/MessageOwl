package com.xtapps.messageowl.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ChatRoomDao
import kotlinx.coroutines.flow.Flow

class ChatsViewModel(private val chatRoomDao: ChatRoomDao) : ViewModel() {

    fun allChats(): Flow<List<ChatRoom>> = chatRoomDao.getAll()

    private val _chats = MutableLiveData<List<String>>().apply {
        value = (1).rangeTo(100).toList().map { "Chat $it" }
    }
    val chats: LiveData<List<String>> = _chats
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