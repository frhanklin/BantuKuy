package com.frhanklindevs.bantukuy.donor.data.box

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cash_items")
@Parcelize
data class DonationCashItems(

    @NonNull
    @ColumnInfo(name = "box_id")
    var boxId: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0,

    @NonNull
    @ColumnInfo(name = "cash_name")
    var cashName: String,

    @NonNull
    @ColumnInfo(name = "cash_value")
    var cashValue: Double = 0.00

): Parcelable