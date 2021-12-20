package com.frhanklindevs.bantukuy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frhanklindevs.bantukuy.data.user.UserDao
import com.frhanklindevs.bantukuy.data.user.UserEntity
import com.frhanklindevs.bantukuy.donor.data.box.*

@Database(
    entities = [
        UserEntity::class,
        DonationBoxEntity::class,
        DonationCashItems::class,
        DonationGoodsItems::class,
        DonationExpeditionItem::class,
        ExpeditionServices::class

               ],
    exportSchema = true,
    version = 1)
abstract class BantuKuyRoomDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun donorBoxDao(): DonationBoxDao

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
                    ).build()
                }
            }
            return INSTANCE as BantuKuyRoomDatabase
        }

    }

}