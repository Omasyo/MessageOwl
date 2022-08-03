package com.xtapps.messageowl.ui.chats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.MessageDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ChatRoomUpdate
import com.xtapps.messageowl.ui.contacts.TAG
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao
) : ViewModel() {

    init {
        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            ***REMOVED***

            if (snapshot != null && snapshot.exists()) {
                val rooms = snapshot.data?.get("rooms") as ArrayList<String>
                for (room in rooms) {
                    roomDb.document(room).addSnapshotListener roomSnapshot@{ snapshot, e ->
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            return@roomSnapshot
                        ***REMOVED***

                        if (snapshot != null && snapshot.exists()) {
                            val document = snapshot.data!!
                            viewModelScope.launch {
                                val result = chatRoomDao.updateRoom(
                                    ChatRoomUpdate(
                                        id = snapshot.id,
                                        name = document["name"] as String,
                                        participants = document["participants"] as ArrayList<String>,
                        ***REMOVED***
                    ***REMOVED***
                                launch {
                                    if (result == 0) {
                                        chatRoomDao.insertRoom(
                                            ChatRoom(
                                                id = snapshot.id,
                                                name = document["name"] as String,
                                                isGroup = document["group"] as Boolean,
                                                unread = 0,
                                                participants = document["participants"] as ArrayList<String>,
                                ***REMOVED***
                            ***REMOVED***
                                    ***REMOVED***
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED*** else {
                            Log.d(TAG, "No such room $room")
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    val allChats = chatRoomDao.getChatCards()

    companion object {
        val authUser = FirebaseAuth.getInstance().currentUser!!
        val userData = Firebase.firestore.collection("users")
            .document(authUser.uid)
        val roomDb = Firebase.firestore.collection("rooms")
    ***REMOVED***
***REMOVED***

class ChatsViewModelFactory(
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatRoomDao, messageDao, userDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***