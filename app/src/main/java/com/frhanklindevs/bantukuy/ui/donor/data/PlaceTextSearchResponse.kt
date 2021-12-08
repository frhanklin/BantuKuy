package com.frhanklindevs.bantukuy.ui.donor.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceTextSearchResponse(

	@field:SerializedName("next_page_token")
	val nextPageToken: String? = null,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("results")
	val results: List<PlaceItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Southwest(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable

@Parcelize
data class Geometry(

	@field:SerializedName("viewport")
	val viewport: Viewport? = null,

	@field:SerializedName("location")
	val location: Location? = null
) : Parcelable

@Parcelize
data class Viewport(

	@field:SerializedName("southwest")
	val southwest: Southwest? = null,

	@field:SerializedName("northeast")
	val northeast: Northeast? = null
) : Parcelable

@Parcelize
data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean? = null
) : Parcelable

@Parcelize
data class PhotosItem(

	@field:SerializedName("photo_reference")
	val photoReference: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<String?>? = null,

	@field:SerializedName("height")
	val height: Int? = null
) : Parcelable

@Parcelize
data class PlusCode(

	@field:SerializedName("compound_code")
	val compoundCode: String? = null,

	@field:SerializedName("global_code")
	val globalCode: String? = null
) : Parcelable

@Parcelize
data class Location(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable

@Parcelize
data class Northeast(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable

@Parcelize
data class PlaceItem(

	@field:SerializedName("formatted_address")
	val formattedAddress: String? = null,

	@field:SerializedName("types")
	val types: List<Any?>? = null,

	@field:SerializedName("business_status")
	val businessStatus: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("icon_background_color")
	val iconBackgroundColor: String? = null,

	@field:SerializedName("reference")
	val reference: String? = null,

	@field:SerializedName("user_ratings_total")
	val userRatingsTotal: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("icon_mask_base_uri")
	val iconMaskBaseUri: String? = null,

	@field:SerializedName("plus_code")
	val plusCode: PlusCode? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null,

	@field:SerializedName("photos")
	val photos: List<Any?>? = null,

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null,

	@field:SerializedName("permanently_closed")
	val permanentlyClosed: Boolean? = null
) : Parcelable
