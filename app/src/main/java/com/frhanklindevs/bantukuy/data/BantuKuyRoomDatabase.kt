package com.frhanklindevs.bantukuy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frhanklindevs.bantukuy.data.user.UserDao
import com.frhanklindevs.bantukuy.data.user.UserEntity

@Database(
    entities = [UserEntity::class],
    exportSchema = true,
    version = 1)
abstract class BantuKuyRoomDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: BantuKuyRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): BantuKuyRoomDatabase {
            if (INSTANCE == null) {
                synchronized(BantuKuyRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        BantuKuyRoomDatabase::class.java,
                        "bantu_kuy_db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as BantuKuyRoomDatabase
        }

    }

}