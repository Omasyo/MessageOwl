package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id in (:senderIds)")
    fun getUsers(senderIds: List<String>): Flow<List<UserModel>>

    @Query("SELECT name FROM users WHERE id = :id")
    fun getUsername(id: String): Flow<String>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: String): Flow<UserModel>

    @Insert(entity = UserModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)
***REMOVED***