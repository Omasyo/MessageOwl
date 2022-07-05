package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Query
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM chat_room ORDER BY id DESC")
    fun getAll(): Flow<List<ChatRoom>>

    @Query("SELECT * FROM message WHERE room_id = :roomId")
    fun getMessages(roomId: Long): Flow<List<MessageModel>>

//    @Query("SELECT name FROM ")
}