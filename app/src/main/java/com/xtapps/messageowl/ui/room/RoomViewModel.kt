package com.xtapps.messageowl.ui.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

class RoomViewModel(
    private val messageDao: MessageDao
) : ViewModel() {
    fun getMessages(roomId: String) = messageDao.getMessages(roomId)
}

class RoomViewModelFactory(
    private val messageDao: MessageDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(messageDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}