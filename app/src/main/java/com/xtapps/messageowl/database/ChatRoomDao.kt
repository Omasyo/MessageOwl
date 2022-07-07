package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Query
import com.xtapps.messageowl.models.ChatRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room ORDER BY id DESC")
    fun getAll(): Flow<List<ChatRoom>>
}