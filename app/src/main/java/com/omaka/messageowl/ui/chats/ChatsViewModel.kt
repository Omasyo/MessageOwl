package com.omaka.messageowl.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.omaka.messageowl.database.ChatRoomDao
import com.omaka.messageowl.database.UserDao
import kotlinx.coroutines.flow.map

class ChatsViewModel(
    chatRoomDao: ChatRoomDao,
    private val userDao: UserDao,
) : ViewModel() {
    private val authUser get() = FirebaseAuth.getInstance().currentUser!!

    val allChats = chatRoomDao.getChatCards().map { list ->
        var i = 0
        list.map { cardModel ->
            if (!cardModel.isGroup) {
                val friend = cardModel.participants.first { it != authUser.uid }
                val details = userDao.getUserDetails(friend)
                cardModel.copy(
                    roomName = details?.name,
                    image = details?.profilePic,
                )
            } else cardModel
        }.map { it.copy(senderName = it.senderName ?: "deleted") }
    }


}

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao, userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}