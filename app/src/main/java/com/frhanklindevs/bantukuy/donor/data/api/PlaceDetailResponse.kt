package com.frhanklindevs.bantukuy.donor.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceDetailResponse(

    @field:SerializedName("result")
	val placeDetails: PlaceDetails? = null,

    @field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class PeriodsItem(

    @field:SerializedName("close")
	val close: Close? = null,

    @field:SerializedName("open")
	val open: Open? = null
) : Parcelable

@Parcelize
data class Close(

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("day")
	val day: Int? = null
) : Parcelable

@Parcelize
data class PlaceDetails(

    @field:SerializedName("formatted_address")
	val formattedAddress: String? = null,

    @field:SerializedName("website")
	val website: String? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null,

    @field:SerializedName("photos")
	val photos: List<PhotosItem?>? = null,

    @field:SerializedName("formatted_phone_number")
	val formattedPhoneNumber: String? = null,

    @field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class Open(

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("day")
	val day: Int? = null
) : Parcelable
