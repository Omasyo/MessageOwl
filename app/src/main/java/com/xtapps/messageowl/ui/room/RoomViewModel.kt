package com.xtapps.messageowl.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

class RoomViewModel(
    private val messageDao: MessageDao,
    private val roomId: String
) : ViewModel() {
    val messages: Flow<List<MessageModel>> get() = messageDao.getMessages(roomId)
}

class RoomViewModelFactory(
    private val messageDao: MessageDao,
    private val groupId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(messageDao, groupId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}