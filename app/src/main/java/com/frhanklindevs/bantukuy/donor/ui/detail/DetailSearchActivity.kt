package com.frhanklindevs.bantukuy.donor.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.donor.data.PhotosItem
import com.frhanklindevs.bantukuy.databinding.ActivityDetailSearchBinding
import com.frhanklindevs.bantukuy.donor.data.PlaceDetails
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import java.lang.StringBuilder

class DetailSearchActivity : AppCompatActivity() {

    private var _binding : ActivityDetailSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var placeId : String
    private lateinit var photosItem : List<PhotosItem?>

    private lateinit var viewModel: DetailSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Gunakan ketika sudah pakai API
        placeId = intent.extras?.getString(EXTRA_PLACE) as String

        supportActionBar?.hide()


        initView()
        setViewBehaviors()
    }

    private fun setViewBehaviors() {
        //Back Button
        binding.detailBtnBack.setOnClickListener {
            onBackPressed()
        }

        //Contact Button
        binding.detailBtnContact.setOnClickListener {
            val phoneNumber = viewModel.placeDetail.value?.formattedPhoneNumber
            if (phoneNumber != null) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Tidak terdapat nomor telepon", Toast.LENGTH_SHORT).show()
            }
        }

        //Visit Web Button
        binding.detailBtnSite.setOnClickListener {
            val url = viewModel.placeDetail.value?.website
            if (url != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Tidak terdapat situs web", Toast.LENGTH_SHORT).show()
            }
        }

        //Open Maps Button
        binding.detailBtnMaps.setOnClickListener {
            val urlMaps = viewModel.placeDetail.value?.url
            if (urlMaps != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urlMaps)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

        //Select Home Button
        binding.detailBtnSelect.setOnClickListener {

        }

    }

    private fun initView() {
        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[DetailSearchViewModel::class.java]

        viewModel.isContentVisible.observe(this, contentObserver)
        viewModel.isLoading.observe(this, loadingObserver)
        viewModel.warningText.observe(this, warningObserver)
        viewModel.placeDetail.observe(this, detailObserver)

        viewModel.setPlace(placeId)
    }

    private val contentObserver = Observer<Boolean> {
        binding.detailContainer.visibility = if (it) View.VISIBLE else View.GONE
    }
    private val loadingObserver = Observer<Boolean> {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
    private val warningObserver = Observer<String> {
        binding.warningText.text = it
        binding.warningText.visibility = if (it == "") View.GONE else View.VISIBLE
    }
    private val detailObserver = Observer<PlaceDetails> {
        if (it != null) {
            binding.detailName.text = it.name
            binding.detailContact.text = it.formattedPhoneNumber
            binding.detailLocation.text = it.formattedAddress
            binding.detailOpeningHours.text = formatOpeningHours(it.openingHours?.weekdayText)

            if (it.photos != null) {
                photosItem = it.photos
                val fragments = ArrayList<Fragment>()

                for (item in photosItem) {
                    val fragment = ImageGalleryFragment.getInstance(item?.photoReference)
                    fragments.add(fragment)

                    val galleryPagerAdapter = DetailPagerAdapter(supportFragmentManager, fragments)
                    binding.viewPager.adapter = galleryPagerAdapter
                    binding.tabLayout.setupWithViewPager(binding.viewPager, true)

//                    val pagerAdapter = PageView

                }
            }
        }
    }

    private fun formatOpeningHours(weekdayText: List<String?>?): String {
        val openingHours = StringBuilder("")
        if (weekdayText != null) {
            for (item in weekdayText) {
                openingHours.append(item + "\n")
            }
        }
        return openingHours.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_PLACE = "extra_place"
    }
}