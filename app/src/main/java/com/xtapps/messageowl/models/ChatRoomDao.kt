package com.xtapps.messageowl.models

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room")
    fun getAll(): List<ChatRoom>
}