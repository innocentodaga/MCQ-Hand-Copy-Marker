package com.example.mcqhardcopymarker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun  addUser(user: User)

    @Query("SELECT * FROM usersTable ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM usersTable WHERE firstName = :firstName AND lastName = :lastName")
    fun readUserNames(firstName: String, lastName: String) : LiveData<List<User>>

    @Query("SELECT * from usersTable where password = :password and email = :email")
    fun readEmailPassword(password: String, email: String) : LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("Delete from usersTable where email = :email")
    suspend fun deleteUserByEmail(email: String)

    @Query("SELECT * FROM usersTable where email = :email")
    fun readEmail(email: String): LiveData<List<User>>

    @Query("SELECT * FROM usersTable where email = :email LIMIT 1")
    fun getUserByEmail(email: String): LiveData<User>
}