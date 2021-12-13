package com.frhanklindevs.bantukuy.network

import com.frhanklindevs.bantukuy.donor.data.PlaceDetailResponse
import com.frhanklindevs.bantukuy.donor.data.PlaceTextSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("place/textsearch/json")
    fun getPlaceTextSearch(
        @Query("query") query: String,
        @Query("key") key: String
    ): Call<PlaceTextSearchResponse>

    @GET("place/details/json?" +
            "fields=name" +
            "%2Cformatted_phone_number" +
            "%2Cwebsite" +
            "%2Curl" +
            "%2Cphotos" +
            "%2Copening_hours" +
            "%2Cformatted_address")
    fun getPlaceDetail(
        @Query("place_id") placeId: String,
        @Query("key") key: String
    ): Call<PlaceDetailResponse>



}