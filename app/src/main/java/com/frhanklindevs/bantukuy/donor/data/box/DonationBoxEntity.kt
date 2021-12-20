package com.frhanklindevs.bantukuy.donor.data.box

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dbox_table")
@Parcelize
data class DonationBoxEntity (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "box_id")
    var boxId: Int = 0,

    @NonNull
    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "place_id")
    var placeId: String?,

    @NonNull
    @ColumnInfo(name = "completed")
    var completed: Int = 0

): Parcelable