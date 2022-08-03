package com.xtapps.messageowl.ui.contacts

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ContactModel
import com.xtapps.messageowl.models.ContactWithNumber
import com.xtapps.messageowl.models.UserModel
import com.xtapps.messageowl.ui.room.RoomViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "ContactsViewModel"

class ContactsViewModel(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
) : ViewModel() {

    val contacts = userDao.getContacts()

    init {
//        userDb.
    ***REMOVED***

    fun submitContacts(contacts: Set<ContactWithNumber>) {
        for (contact in contacts) {
            if(contact.phoneNo == authUser.phoneNumber) continue
            userDb
                .whereEqualTo("phoneNo", contact.phoneNo)
                .addSnapshotListener{ snapshots, e ->

                    if (e != null) {
                        Log.w(TAG, "listen:error", e)
                        return@addSnapshotListener
                    ***REMOVED***

                    for (dc in snapshots!!.documentChanges) {
                        val document = dc.document
                        viewModelScope.launch {
                            launch {
                                userDao.insertContact(
                                    ContactModel(
                                        id = document.id,
                                        name = contact.name,
                        ***REMOVED***
                    ***REMOVED***
                            ***REMOVED***
                            launch {
                                userDao.insertUser(
                                    UserModel(
                                        id = document.id,
                                        name = document.data["name"] as String,
                                        phoneNo = document.data["phoneNo"] as String,
                                        profilePic = null
                        ***REMOVED***
                    ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun getPrivateRoom(participantId: String) = chatRoomDao.getPrivateRoom("%$participantId%")
        .combine(userDao.getUsername(participantId)) { room: ChatRoom?, username ->
            if (room == null) {
                val tempRoom = ChatRoom(
                    id = "temp" + Calendar.getInstance().timeInMillis.toString(),
                    name = username,
                    isGroup = false,
                    participants = listOf(RoomViewModel.authUser.uid, participantId),
                    unread = 0
    ***REMOVED***
                chatRoomDao.insertRoom(tempRoom)
                tempRoom
            ***REMOVED*** else {
                room
            ***REMOVED***
        ***REMOVED***

    companion object {
        val userDb = Firebase.firestore.collection("users")
        val authUser = FirebaseAuth.getInstance().currentUser!!
    ***REMOVED***
***REMOVED***

class ContactsViewModelFactory(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(userDao, chatRoomDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***