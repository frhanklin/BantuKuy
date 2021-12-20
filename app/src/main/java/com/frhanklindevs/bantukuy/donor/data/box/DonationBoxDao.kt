package com.frhanklindevs.bantukuy.donor.data.box

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DonationBoxDao {

    //Checking for user's active box
    @Query("SELECT EXISTS(SELECT * FROM dbox_table WHERE (user_id=:userId AND completed=0) LIMIT 1)")
    fun checkIfUserHasActiveBox(userId: Int): Boolean

    //If user doesn't have an active box, create one
    @Insert
    fun initializeBoxForUser(box: DonationBoxEntity)

    //If user have active box, fetch one of them and get Box
    @Query("SELECT * FROM dbox_table WHERE (user_id=:userId AND completed=0) LIMIT 1")
    fun getAnActiveBoxForUser(userId: Int): DonationBoxEntity

    //Updates the place ID of certain box
    @Query("UPDATE dbox_table SET place_id=:placeId WHERE box_id=:boxId")
    fun updatePlaceByBoxId(boxId: Int, placeId: String)








    //Fetch all items inside the box
    @Query("SELECT * FROM cash_items WHERE box_id=:boxId")
    fun getAllCashItems(boxId: Int): List<DonationCashItems>

    @Query("SELECT * FROM goods_items WHERE box_id=:boxId")
    fun getAllGoodsItems(boxId: Int): List<DonationGoodsItems>


    //Inserting Items into Box
    @Insert
    fun insertCashToBox(cashItem: DonationCashItems)
    @Insert
    fun insertGoodsToBox(goodsItem: DonationGoodsItems)
    @Insert
    fun insertExpeditionType(expeditionItem: DonationExpeditionItem)


    //Updating Items in the box
    @Update
    fun updateCash(cashItem: DonationCashItems)
    @Update
    fun updateGoods(goodsItem: DonationGoodsItems)
    @Update
    fun updateExpedition(expeditionItem: DonationExpeditionItem)


    //Deleting Items From Box
    @Delete
    fun deleteCash(cashItem: DonationCashItems)
    @Delete
    fun deleteGoods(goodsItem: DonationGoodsItems)








}