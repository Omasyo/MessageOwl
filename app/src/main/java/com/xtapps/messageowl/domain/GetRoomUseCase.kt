package com.xtapps.messageowl.domain

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

fun requestPrivateRoom(
    participantId: String,
    chatRoomDao: ChatRoomDao,
    userDao: UserDao,
    messageDao: MessageDao,
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
***REMOVED***.addOnCompleteListener {
                roomDb.document(roomId).collection("messages").addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                        return@addSnapshotListener
                    ***REMOVED***

                    if (snapshots != null) {
                        for (dc in snapshots.documentChanges) {
                            val document = dc.document
                            CoroutineScope(Dispatchers.IO).launch {
                                launch {
                                    messageDao
                                        .insertMessage(
                                            MessageModel(
                                                id = document.id,
                                                roomId = roomId,
                                                content = document["content"] as String,
                                                senderId = document["sender"] as String,
                                                timestamp = document.getDate("time")!!,
                                ***REMOVED***
                            ***REMOVED***
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***

                    ***REMOVED***
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
