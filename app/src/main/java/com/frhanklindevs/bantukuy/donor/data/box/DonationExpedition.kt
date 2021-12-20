package com.frhanklindevs.bantukuy.donor.data.box

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "expedition_table"
)
@Parcelize
data class DonationExpeditionItem (

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "box_id")
    var boxId: Int,

    @NonNull
    @ColumnInfo(name = "donation_service_id")
    var donationServiceId: Int = 0

): Parcelable

@Entity(tableName = "expedition_services")
@Parcelize
data class ExpeditionServices(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0,

    @NonNull
    @ColumnInfo(name = "expedition_company")
    var expeditionCompany: String,

    @NonNull
    @ColumnInfo(name = "plan_name")
    var planName: String,

    @NonNull
    @ColumnInfo(name = "planPricePerKg")
    var planPricePerKg: Double = 0.00

): Parcelable