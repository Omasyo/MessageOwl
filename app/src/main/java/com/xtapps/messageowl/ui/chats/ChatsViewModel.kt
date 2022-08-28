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
    private val chatRoomDao: ChatRoomDao,
    private val userDao: UserDao,
) : ViewModel() {
    private val authUser get() = FirebaseAuth.getInstance().currentUser!!
    private val userData = Firebase.firestore.collection("users")
        .document(authUser.uid)
    private val roomDb = Firebase.firestore.collection("rooms")

    init {
        listenToRoomUpdates()
    ***REMOVED***

    val allChats = chatRoomDao.getChatCards().map { list ->
        list.map { cardModel ->
            if (!cardModel.isGroup) {
                val friend = cardModel.participants.first { it != authUser.uid ***REMOVED***
                val details = userDao.getUserDetails(friend)
                Log.d(TAG, "cardModel image ${cardModel.image***REMOVED***: ")
                cardModel.copy(
                    roomName = details.name,
                    image = details.profilePic,
    ***REMOVED***
            ***REMOVED*** else cardModel
        ***REMOVED***
    ***REMOVED***

    fun listenToRoomUpdates() =
        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            ***REMOVED***

            if (snapshot != null && snapshot.exists()) {
                val rooms = snapshot.data?.get("rooms") as ArrayList<String>? ?: arrayListOf()
                for (room in rooms) {roomDb.document(room).addSnapshotListener roomSnapshot@{ roomSnapshot, roomException ->
                        if (roomException != null) {
                            Log.w(TAG, "Listen failed.", roomException)
                            return@roomSnapshot
                        ***REMOVED***

                        if (roomSnapshot != null && roomSnapshot.exists()) {
                            val document = roomSnapshot.data!!
                            viewModelScope.launch {
                                val result = chatRoomDao.updateRoom(
                                    ChatRoomUpdate(
                                        id = roomSnapshot.id,
                                        name = document["name"] as String?,
                                        participants = document["participants"] as ArrayList<String>,
                        ***REMOVED***
                    ***REMOVED***
                                launch {
                                    if (result == 0) {
                                        chatRoomDao.insertRoom(
                                            ChatRoom(
                                                id = roomSnapshot.id,
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