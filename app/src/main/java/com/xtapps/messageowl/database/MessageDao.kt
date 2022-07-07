package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM message WHERE room_id = :roomId ORDER BY timestamp")
    fun getMessages(roomId: String): Flow<List<MessageModel>>

    @Insert(entity = MessageModel::class)
    suspend fun insertMessage(message: MessageModel)
***REMOVED***