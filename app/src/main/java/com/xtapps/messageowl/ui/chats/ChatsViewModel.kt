package com.xtapps.messageowl.ui.chats

import androidx.core.util.rangeTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatsViewModel : ViewModel() {

    private val _chats = MutableLiveData<List<String>>().apply {
        value = (1).rangeTo(100).toList().map { "Chat $it" ***REMOVED***
    ***REMOVED***
    val chats: LiveData<List<String>> = _chats
***REMOVED***