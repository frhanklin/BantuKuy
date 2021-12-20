package com.frhanklindevs.bantukuy.data

import android.app.Application
import com.frhanklindevs.bantukuy.data.user.UserDao
import com.frhanklindevs.bantukuy.data.user.UserEntity
import com.frhanklindevs.bantukuy.donor.data.box.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BantuKuyRepository(application: Application) {
    private val mUserDao: UserDao
    private val mDonorBoxDao: DonationBoxDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = BantuKuyRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
        mDonorBoxDao = db.donorBoxDao()
    }

    fun insertUser(userEntity: UserEntity) {
        executorService.execute {
            mUserDao.insert(userEntity)
        }
    }

    fun getUserById(id: Int): UserEntity = executorService.submit( Callable{mUserDao.getUserById(id)}).get()

    fun getUserByUsername(username: String): UserEntity = executorService.submit( Callable{
        mUserDao.getUserByUsername(username)
    }).get()

    fun checkUsernameExists(username: String): Boolean = executorService.submit( Callable{
        mUserDao.checkUsernameExists(username)
    }).get()

    fun getUserActiveBox(userId: Int): DonationBoxEntity {
        val userActiveBoxStatus = executorService.submit(Callable {
            mDonorBoxDao.checkIfUserHasActiveBox(userId)
        }).get()

        if (userActiveBoxStatus) {
            val box = executorService.submit(Callable {
                mDonorBoxDao.getAnActiveBoxForUser(userId)
            }).get()
            return box
        } else {
            val insertBox = DonationBoxEntity(
                userId = userId,
                completed = 0,
                placeId = null
            )
            executorService.execute {
                mDonorBoxDao.initializeBoxForUser(insertBox)
            }
            val box = executorService.submit(Callable {
                mDonorBoxDao.getAnActiveBoxForUser(userId)
            }).get()
            executorService.execute {
                mDonorBoxDao.insertExpeditionType(
                    DonationExpeditionItem(
                        box.boxId
                    )
                )
            }
            return box
        }
    }

    fun updatePlaceByBoxId(boxId: Int, placeId: String) {
        executorService.execute {
            mDonorBoxDao.updatePlaceByBoxId(boxId, placeId)
        }
    }

    fun getAllCashItems(boxId: Int) : List<DonationCashItems> = executorService.submit(Callable {
        mDonorBoxDao.getAllCashItems(boxId)
    }).get()

    fun getTotalCash(boxId: Int) : Double {
        val cashList = getAllCashItems(boxId)
        var cashTotal = 0.0
        for (item in cashList) {
            cashTotal += item.cashValue
        }
        return cashTotal
    }

    fun getAllGoodsItems(boxId: Int) : List<DonationGoodsItems> = executorService.submit(Callable {
        mDonorBoxDao.getAllGoodsItems(boxId)
    }).get()

    fun getTotalGoodsWeight(boxId: Int) : Int {
        val goodsList = getAllGoodsItems(boxId)
        var weightTotal = 0
        for (item in goodsList) {
            weightTotal += item.goodsWeight
        }
        return weightTotal
    }

    fun getExpeditionServiceUsed(boxId: Int): Int = executorService.submit(Callable {
        mDonorBoxDao.getExpeditionServiceUsed(boxId)
    }).get()

    fun getExpeditionCostPerKg(expeditionId: Int): Double = executorService.submit( Callable{
        mDonorBoxDao.getExpeditionCostPerKg(expeditionId)
    }).get()

    fun insertCash(cashItem: DonationCashItems) {
        executorService.execute {
            mDonorBoxDao.insertCashToBox(cashItem)
        }
    }

    fun insertGoods(goodsItem: DonationGoodsItems) {
        executorService.execute {
            mDonorBoxDao.insertGoodsToBox(goodsItem)
        }
    }

    fun updateCash(cashItem: DonationCashItems) {
        executorService.execute {
            mDonorBoxDao.updateCash(cashItem)
        }
    }
    fun updateGoods(goodsItem: DonationGoodsItems) {
        executorService.execute {
            mDonorBoxDao.updateGoods(goodsItem)
        }
    }

    fun deleteCash(cashItem: DonationCashItems) {
        executorService.execute {
            mDonorBoxDao.deleteCash(cashItem)
        }
    }
    fun deleteGoods(goodsItem: DonationGoodsItems) {
        executorService.execute {
            mDonorBoxDao.deleteGoods(goodsItem)
        }
    }

}