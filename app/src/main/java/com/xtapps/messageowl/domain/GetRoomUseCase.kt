package com.xtapps.messageowl.domain

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import kotlinx.coroutines.flow.combine

fun requestPrivateRoom(
    participantId: String,
    chatRoomDao: ChatRoomDao,
    userDao: UserDao
) = chatRoomDao.getPrivateRoom("%$participantId%")
    .combine(userDao.getUsername(participantId)) { room: ChatRoom?, username ->
        Log.d("TAG", "requestPrivateRoom: $room")
        if (room == null) {
            val participants = listOf(Firebase.auth.uid!!, participantId)
            val roomId = participants.sorted().reduce { acc, id -> acc + id ***REMOVED***
            val roomDb = Firebase.firestore.collection("rooms")

            roomDb.document(roomId).set(
                hashMapOf(
                    "group" to false,
                    "participants" to participants
    ***REMOVED***
***REMOVED***
            val newRoom = ChatRoom(
                id = roomId,
                name = username,
                isGroup = false,
                participants = participants,
                unread = 0
***REMOVED***
            chatRoomDao.insertRoom(newRoom)
            newRoom
        ***REMOVED*** else {
            room
        ***REMOVED***
    ***REMOVED***
