package com.xtapps.messageowl.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xtapps.messageowl.database.AppDao
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

class GroupRoomViewModel(
    private val appDao: AppDao,
    private val roomId: String
) : ViewModel() {
    val messages: Flow<List<MessageModel>> get() = appDao.getMessages(roomId)
***REMOVED***

class GroupViewModelFactory(
    private val messageModelDao: AppDao,
    private val groupId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupRoomViewModel::class.java)) {
            return GroupRoomViewModel(messageModelDao, groupId) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***