package com.frhanklindevs.bantukuy.donor.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OpeningHours(

    @field:SerializedName("open_now")
    val openNow: Boolean? = null,

    @field:SerializedName("periods")
    val periods: List<PeriodsItem?>? = null,

    @field:SerializedName("weekday_text")
    val weekdayText: List<String?>? = null
) : Parcelable