package com.xtapps.messageowl.ui.chats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ChatRoomUpdate
import com.xtapps.messageowl.ui.contacts.TAG
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatsViewModel(
    chatRoomDao: ChatRoomDao,
    private val userDao: UserDao,
) : ViewModel() {
    private val authUser get() = FirebaseAuth.getInstance().currentUser!!

    val allChats = chatRoomDao.getChatCards().map { list ->
        var i = 0
        list.map { cardModel ->
            Log.d("ChatsViewModel", "${++i***REMOVED***. chats are: $cardModel")
            if (!cardModel.isGroup) {
                val friend = cardModel.participants.first { it != authUser.uid ***REMOVED***
                val details = userDao.getUserDetails(friend)
                Log.d(TAG, "cardModel image ${cardModel.image***REMOVED***: ")
                cardModel.copy(
                    roomName = details.name,
                    image = details.profilePic,
    ***REMOVED***
            ***REMOVED*** else cardModel
        ***REMOVED***.map { it.copy(senderName = it.senderName ?: "deleted") ***REMOVED***
    ***REMOVED***


***REMOVED***

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao, userDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***