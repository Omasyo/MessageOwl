package com.omaka.messageowl.ui.contacts

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.omaka.messageowl.database.ChatRoomDao
import com.omaka.messageowl.database.MessageDao
import com.omaka.messageowl.database.UserDao
import com.omaka.messageowl.domain.requestPrivateRoom
import com.omaka.messageowl.models.ContactModel
import com.omaka.messageowl.models.ContactWithNumber
import com.omaka.messageowl.models.UserModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

const val TAG = "ContactsViewModel"

class ContactsViewModel(
    private val messageDao: MessageDao,
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

    fun getPrivateRoom(participantId: String) = requestPrivateRoom(participantId, chatRoomDao, userDao, messageDao)
***REMOVED***

class ContactsViewModelFactory(
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(messageDao, userDao, chatRoomDao) as T
        ***REMOVED***
        throw IllegalArgumentException("Unknown ViewModel class")
    ***REMOVED***
***REMOVED***