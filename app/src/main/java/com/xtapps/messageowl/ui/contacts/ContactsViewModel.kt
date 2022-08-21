package com.xtapps.messageowl.ui.contacts

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.database.ChatRoomDao
import com.xtapps.messageowl.database.UserDao
import com.xtapps.messageowl.domain.requestPrivateRoom
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ContactModel
import com.xtapps.messageowl.models.ContactWithNumber
import com.xtapps.messageowl.models.UserModel
import com.xtapps.messageowl.ui.room.RoomViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "ContactsViewModel"

class ContactsViewModel(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
) : ViewModel() {

    private val userDb by lazy { Firebase.firestore.collection("users") ***REMOVED***
    private val authUser get() = FirebaseAuth.getInstance().currentUser!!
    private val roomDb by lazy { Firebase.firestore.collection("rooms") ***REMOVED***

    val contacts = userDao.getContacts().map {
        it.filter { contactCard -> contactCard.id != authUser.uid ***REMOVED***
    ***REMOVED***

    fun submitContacts(contacts: Set<ContactWithNumber>) {
        for (contact in contacts) {
            userDb
                .whereEqualTo("phoneNo", contact.phoneNo)
                .get().addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Log.w(TAG, "listen:error", it.exception)
                        return@addOnCompleteListener
                    ***REMOVED***


                    for (dc in it.result.documentChanges) {
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
                                        profilePic = document.data["profilePic"] as String?,
                        ***REMOVED***
                    ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun getPrivateRoom(participantId: String) = requestPrivateRoom(participantId, chatRoomDao, userDao)
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