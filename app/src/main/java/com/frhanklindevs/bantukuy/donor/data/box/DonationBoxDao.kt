package com.frhanklindevs.bantukuy.donor.data.box

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DonationBoxDao {

    //Insert starting data
    @Insert
    fun insertStartingExpeditionData(expeditionServices: ExpeditionServices)



    //Checking for user's active box
    @Query("SELECT EXISTS(SELECT * FROM dbox_table WHERE (user_id=:userId AND completed=0) LIMIT 1)")
    fun checkIfUserHasActiveBox(userId: Int): Boolean

    //If user doesn't have an active box, create one
    @Insert
    fun initializeBoxForUser(box: DonationBoxEntity)

    //If user have active box, fetch one of them and get Box
    @Query("SELECT * FROM dbox_table WHERE (user_id=:userId AND completed=0) LIMIT 1")
    fun getAnActiveBoxForUser(userId: Int): DonationBoxEntity

    //Update Donation is Completed by ID
    @Query("UPDATE dbox_table SET completed=1 WHERE box_id=:boxId")
    fun updateDonationBox(boxId: Int)


    //Updates the place ID of certain box
    @Query("UPDATE dbox_table SET place_id=:placeId WHERE box_id=:boxId")
    fun updatePlaceByBoxId(boxId: Int, placeId: String)








    //Fetch all items inside the box
    @Query("SELECT * FROM cash_items WHERE box_id=:boxId")
    fun getAllCashItems(boxId: Int): List<DonationCashItems>

    @Query("SELECT * FROM goods_items WHERE box_id=:boxId")
    fun getAllGoodsItems(boxId: Int): List<DonationGoodsItems>

    @Query("SELECT donation_service_id FROM expedition_table WHERE box_id=:boxId")
    fun getExpeditionServiceUsed(boxId: Int): Int

    @Query("SELECT * FROM expedition_services WHERE id=:expeditionId")
    fun getExpeditionService(expeditionId: Int): ExpeditionServices


    //Checking if items is exist in Box
    @Query("SELECT EXISTS (SELECT * FROM cash_items WHERE (box_id=:boxId AND cash_name=:categoryName))")
    fun checkCashItem(boxId: Int, categoryName: String): Boolean
    @Query("SELECT EXISTS (SELECT * FROM goods_items WHERE (box_id=:boxId AND goods_name=:goodsName))")
    fun checkGoodsItem(boxId: Int, goodsName: String): Boolean

    //Getting item ID from DB
    @Query("SELECT id FROM cash_items WHERE (box_id=:boxId AND cash_name=:categoryName)")
    fun getCashItemId(boxId: Int, categoryName: String): Int
    @Query("SELECT id FROM goods_items WHERE (box_id=:boxId AND goods_name=:goodsName)")
    fun getGoodsItemId(boxId: Int, goodsName: String): Int

    //Fetching item if exists
    @Query("SELECT * FROM cash_items WHERE id=:id")
    fun getCashById(id: Int): DonationCashItems
    @Query("SELECT * FROM goods_items WHERE id=:id")
    fun getGoodsById(id: Int): DonationGoodsItems


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