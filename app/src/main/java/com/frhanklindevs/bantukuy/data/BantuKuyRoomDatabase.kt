package com.frhanklindevs.bantukuy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.frhanklindevs.bantukuy.data.user.UserDao
import com.frhanklindevs.bantukuy.data.user.UserEntity
import com.frhanklindevs.bantukuy.donor.data.box.*
import java.util.concurrent.Executors

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
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { taskDatabase ->
                                Executors.newSingleThreadScheduledExecutor().execute {
                                    fillWithStartingData(taskDatabase.donorBoxDao(), taskDatabase.userDao())
                                }
                            }
                        }
                    }).build()
                }
            }
            return INSTANCE as BantuKuyRoomDatabase
        }

        private fun fillWithStartingData(donorBoxDao: DonationBoxDao, userDao: UserDao) {
            donorBoxDao.insertStartingExpeditionData(
                ExpeditionServices(
                    expeditionCompany = "BantuKuy Expedition",
                    planName = "Reguler",
                    planPricePerKg = 3000.0
                )
            )
            userDao.insert(
                UserEntity(
                    fullName = "AK",
                    email = "A014R4042@dicoding.org",
                    userName = "test",
                    password = "11111111"
                )
            )
        }

    }

}