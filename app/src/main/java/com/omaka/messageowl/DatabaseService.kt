package com.omaka.messageowl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.omaka.messageowl.models.ChatRoom
import com.omaka.messageowl.models.ChatRoomUpdate
import com.omaka.messageowl.models.MessageModel
import com.omaka.messageowl.models.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseService : Service() {

    private val authUser get() = Firebase.auth.currentUser!!
    private val userData = Firebase.firestore.collection("users").document(authUser.uid)
    private val roomDb = Firebase.firestore.collection("rooms")

    val database by lazy {
        (application as MessageOwlApplication).appDatabase
    }


    override fun onCreate() {

//        Toast.makeText(this, "database service starting", Toast.LENGTH_SHORT).show()

        if (Firebase.auth.currentUser == null) return

        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(com.omaka.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                val rooms = snapshot.data?.get("rooms") as ArrayList<String>? ?: arrayListOf()
                for (room in rooms) {
//                    Toast.makeText(this, "listening too room $room", Toast.LENGTH_SHORT).show()
                    listentoMessageUpdates(room)
                }
            }
        }
        listenToRoomUpdates()
        listentoUserUpdates(authUser.uid)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    private fun listentoUserUpdates(uid: String) = Firebase.firestore.collection("users").document(uid)
        .addSnapshotListener { snapshot, e ->
            
        if (e != null) {
            Log.w(com.omaka.messageowl.ui.contacts.TAG, "listen:error", e)
            return@addSnapshotListener
        }
        CoroutineScope(Dispatchers.IO).launch {
            database.userDao().insertUser(
                UserModel(
                    id = snapshot!!.id,
                    name = (snapshot.get("name") ?: "") as String,
                    phoneNo = (snapshot.get("phoneNo") ?: "") as String,
                    profilePic = snapshot.get("profilePic") as String?
                )
            )
        }
    }

    //Todo: temp remove later
    private fun listentoMessageUpdates(roomId: String) =
        roomDb.document(roomId).collection("messages").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(com.omaka.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshots != null) {
                for (dc in snapshots.documentChanges) {
                    val document = dc.document
                    CoroutineScope(Dispatchers.IO).launch {
                        launch {
                            val id = database.messageDao()
                                .insertMessage(
                                    MessageModel(
                                        id = document.id,
                                        roomId = roomId,
                                        content = document["content"] as String,
                                        senderId = document["sender"] as String,
                                        timestamp = document.getDate("time")!!,
                                    )
                                )
                            if (id != -1L) {
                                launch {
                                    database.chatRoomDao().incrementUnreadCount(roomId)
                                }
                            }
                        }
                    }
                }

            }
        }


    @Suppress("UNCHECKED_CAST")
    fun listenToRoomUpdates() =
        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(com.omaka.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val rooms = snapshot.data?.get("rooms") as ArrayList<String>? ?: arrayListOf()

                for (room in rooms) {
                    Firebase.messaging.subscribeToTopic(room)

                    roomDb.document(room)
                        .addSnapshotListener roomSnapshot@{ roomSnapshot, roomException ->
                            if (roomException != null) {
                                Log.w(
                                    com.omaka.messageowl.ui.contacts.TAG,
                                    "Listen failed.",
                                    roomException
                                )
                                return@roomSnapshot
                            }

                            if (roomSnapshot != null && roomSnapshot.exists()) {
                                val document = roomSnapshot.data!!
                                val participants = document["participants"] as ArrayList<String>

                                for(participant in participants) listentoUserUpdates(participant)

                                CoroutineScope(Dispatchers.IO).launch {
                                    val result =
                                        database.chatRoomDao()
                                            .updateRoom(
                                                ChatRoomUpdate(
                                                    id = roomSnapshot.id,
                                                    name = document["name"] as String?,
                                                    participants = participants,
                                                )
                                            )
                                    launch {
                                        if (result == 0) {
                                            database.chatRoomDao()
                                                .insertRoom(
                                                    ChatRoom(
                                                        id = roomSnapshot.id,
                                                        name = document["name"] as String?,
                                                        isGroup = document["group"] as Boolean,
                                                        unread = 0,
                                                        participants = participants,
                                                    )
                                                )
                                        }
                                    }
                                }
                            } else {
                                Log.e(com.omaka.messageowl.ui.contacts.TAG, "No such room $room")
                            }
                        }
                }
            }
        }
}