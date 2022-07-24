package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_rooms ORDER BY id DESC")
    fun getAll(): Flow<List<ChatRoom>>

    @Query("SELECT * FROM chat_rooms WHERE id = :roomId")
    fun getRoom(roomId: String): Flow<ChatRoom>

    @Query("SELECT * FROM chat_rooms WHERE `group` = 0 AND participants like :userId")
    fun getPrivateRoom(userId: String): Flow<ChatRoom>


    @Insert(entity = ChatRoom::class)
    suspend fun insertRoom(chatRoom: ChatRoom)

//    @Query("SELECT participants FROM chat_rooms WHERE id = :roomId")
//    fun getParticipants(roomId: String): Flow<List<String>>
}