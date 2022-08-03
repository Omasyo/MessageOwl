package com.xtapps.messageowl.database

import androidx.room.*
import com.xtapps.messageowl.models.ContactCard
import com.xtapps.messageowl.models.ContactModel
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id in (:ids)")
    fun getUsers(ids: List<String>): Flow<List<UserModel>>

    @Query("SELECT name FROM users WHERE id = :id")
    fun getUsername(id: String): Flow<String>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: String): Flow<UserModel>

    @Insert(entity = UserModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

//    @Update(entity = UserModel::class)
//    suspend fun updateNumber(id: String, phone: String)

    @Query("DElETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contacts: ContactModel)

    @Query(
        "SELECT users.id as id, " +
                "users.name as username, " +
                "contacts.name as contact_name " +
                "FROM contacts JOIN users ON contacts.id = users.id " +
                "ORDER BY contact_name"
    )
    fun getContacts(): Flow<List<ContactCard>>
}