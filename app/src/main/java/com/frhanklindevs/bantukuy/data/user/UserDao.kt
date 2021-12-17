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

    @Query("SELECT * FROM userTable WHERE username=:userName")
    fun getUser(userName: String): LiveData<List<UserEntity>>

    @Query("SELECT * FROM userTable")
    fun getAllUser(): LiveData<List<UserEntity>>

}