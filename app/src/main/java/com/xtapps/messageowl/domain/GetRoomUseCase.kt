package com.xtapps.messageowl.domain

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import kotlinx.coroutines.flow.combine
import java.util.*

fun requestPrivateRoom(
    participantId: String,
    chatRoomDao: ChatRoomDao,
    userDao: UserDao
) = chatRoomDao.getPrivateRoom("%$participantId%")
    .combine(userDao.getUsername(participantId)) { room: ChatRoom?, username ->
        if (room == null) {
            val tempRoom = ChatRoom(
                id = "temp" + Calendar.getInstance().timeInMillis.toString(),
                name = username,
                isGroup = false,
                participants = listOf(Firebase.auth.uid!!, participantId),
                unread = 0
***REMOVED***
            chatRoomDao.insertRoom(tempRoom)
            tempRoom
        ***REMOVED*** else {
            room
        ***REMOVED***
    ***REMOVED***
