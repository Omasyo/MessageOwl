package com.xtapps.messageowl.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.AppDao
import kotlinx.coroutines.flow.Flow

class ChatsViewModel(private val appDao: AppDao) : ViewModel() {

    fun allChats(): Flow<List<ChatRoom>> = appDao.getAll()
}

class ChatsViewModelFactory(
    private val appDao: AppDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(appDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}