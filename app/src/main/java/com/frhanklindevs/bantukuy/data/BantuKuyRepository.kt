package com.frhanklindevs.bantukuy.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.frhanklindevs.bantukuy.data.user.UserDao
import com.frhanklindevs.bantukuy.data.user.UserEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BantuKuyRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = BantuKuyRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getUser(userName: String): LiveData<List<UserEntity>> = mUserDao.getUser(userName)

    fun getAllUser(): LiveData<List<UserEntity>> = mUserDao.getAllUser()

    fun insertUser(userEntity: UserEntity) {
        executorService.execute {
            mUserDao.insert(userEntity)
        }
    }
}