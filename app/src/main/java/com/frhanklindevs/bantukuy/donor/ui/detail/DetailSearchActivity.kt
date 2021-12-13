package com.frhanklindevs.bantukuy.donor.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.frhanklindevs.bantukuy.donor.data.PhotosItem
import com.frhanklindevs.bantukuy.databinding.ActivityDetailSearchBinding

class DetailSearchActivity : AppCompatActivity() {

    private var _binding : ActivityDetailSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var placeId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Gunakan ketika sudah pakai API
//        placeId = intent.extras?.getParcelable<PlaceDetails>(EXTRA_PLACE) as String


        initView()
    }

    private fun initView() {
        val fragments = ArrayList<Fragment>()

//        val photosItems = place.photos as List<PhotosItem?>
//        for (item in photosItems) {
//            val fragment = ImageGalleryFragment.getInstance(item?.photoReference)
//            fragments.add(fragment)
//        }

        val galleryPagerAdapter = DetailPagerAdapter(supportFragmentManager, fragments)
        binding.viewPager.adapter = galleryPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {
        const val EXTRA_PLACE = "extra_place"
    }
}