package com.frhanklindevs.bantukuy.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frhanklindevs.bantukuy.data.user.UserDao
import com.frhanklindevs.bantukuy.data.user.UserEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class BantuKuyRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = BantuKuyRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun insertUser(userEntity: UserEntity) {
        executorService.execute {
            mUserDao.insert(userEntity)
        }
    }

    fun getUserById(id: Int): UserEntity = mUserDao.getUserById(id)

    fun getUserByUsername(username: String): UserEntity = mUserDao.getUserByUsername(username)

    fun checkUsernameExists(username: String): Boolean = mUserDao.checkUsernameExists(username)
}