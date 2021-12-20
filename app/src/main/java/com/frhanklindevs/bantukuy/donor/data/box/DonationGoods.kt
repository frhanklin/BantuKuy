package com.frhanklindevs.bantukuy.donor.data.box

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "goods_items")
@Parcelize
data class DonationGoodsItems(

    @NonNull
    @ColumnInfo(name = "box_id")
    var boxId: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0,

    @NonNull
    @ColumnInfo(name = "goods_name")
    var goodsName: String,

    @NonNull
    @ColumnInfo(name = "goods_weight")
    var goodsWeight: Int

): Parcelable