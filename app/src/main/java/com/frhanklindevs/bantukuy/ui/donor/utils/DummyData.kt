package com.frhanklindevs.bantukuy.ui.donor.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frhanklindevs.bantukuy.ui.donor.data.*

object DummyData {

    fun getDummySearchResult(): List<PlaceItem> {
        val item = PlaceItem(
            formattedAddress = "Jl. Murdai I No.1, RT.8/RW.13, Cemp. Putih Bar., Kec. Cemp. Putih, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10520, Indonesia",
            types = arrayListOf(
                "point_of_interest",
                "establishment"
            ),
            businessStatus = "OPERATIONAL",
            icon= "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/school-71.png",
            rating = 5.0,
            iconBackgroundColor = "#7B9EB0",
            reference = "ChIJr9XR21f0aS4Rzfs2NtCW4k8",
            userRatingsTotal = 2,
            name = "Panti Asuhan diJakarta Griya Asih",
            geometry = Geometry(
                Viewport(
                    northeast = Northeast(
                        lng = 106.8659333298927,
                        lat = -6.182201220107278
                    ),
                    southwest = Southwest(
                        lng= 106.8632336701073,
                        lat= -6.184900879892722
                    )
                ),
                Location(
                    lng = 106.8646551,
                    lat = -6.183528799999999
                )
            ),
            iconMaskBaseUri = "https://maps.gstatic.com/mapfiles/place_api/icons/v2/school_pinlet",
            plusCode = PlusCode(
                compoundCode = "RV87+HV West Cempaka Putih, Central Jakarta City, Jakarta",
                globalCode = "6P58RV87+HV"
            ),
            placeId = "ChIJr9XR21f0aS4Rzfs2NtCW4k8",
            photos = arrayListOf<PhotosItem>(
                PhotosItem(
                    photoReference = "Aap_uEAs4STGsTc3CN9Eqk5ernKC9Tw-T1ZdttdD3uOFf45CVxLNsDTKdmbRI853tHBT50X2l9K4RteLNYMt1TQY5bnwfgNoqMEYd4oFR-0S6pu394B1lP1365inJB3EnCnb3Es8CPviJA5gf1Z-lqGYByldwQ2e5ZBK92CDoFJmiIReV9YS",
                    width = 3120,
                    htmlAttributions = arrayListOf(
                        "<a href=\"https://maps.google.com/maps/contrib/111188951296261393994\">A Google User</a>"
                    ),
                    height = 4160
                )
            ),
            openingHours = OpeningHours(
                openNow = true
            ),
            permanentlyClosed = null
        )

        return arrayListOf(
            item, item, item, item, item, item, item
        )
    }

    fun getDummyLiveData(): LiveData<List<PlaceItem>> {
        val data = getDummySearchResult()
        val mutableLiveData = MutableLiveData<List<PlaceItem>>()
        mutableLiveData.value = data

        val liveData: LiveData<List<PlaceItem>> = mutableLiveData
        return liveData
    }
}