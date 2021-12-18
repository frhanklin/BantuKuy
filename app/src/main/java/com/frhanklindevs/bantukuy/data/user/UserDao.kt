package com.frhanklindevs.bantukuy.data.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE user_id=:userId")
    fun getUserById(userId: Int): UserEntity

    @Query("SELECT * FROM user_table WHERE username=:username")
    fun getUserByUsername(username: String): UserEntity

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE username=:username)")
    fun checkUsernameExists(username: String): Boolean

}