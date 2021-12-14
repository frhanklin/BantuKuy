package com.frhanklindevs.bantukuy.donor.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentImageGalleryBinding
import com.frhanklindevs.bantukuy.utils.BantuKuyDev


class ImageGalleryFragment : Fragment() {

    private var _binding: FragmentImageGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageUrl: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            imageUrl = requireArguments().getString(EXTRA_IMAGE) as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val photoUrl = PHOTO_URL + imageUrl + API_PLACEHOLDER + API_KEY

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)

        Glide.with(requireActivity())
            .setDefaultRequestOptions(requestOptions)
            .load(photoUrl)
            .into(binding.image)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstance(photoReference: String?): Fragment {

            val fragment = ImageGalleryFragment()

            if (photoReference != null) {
                val bundle = Bundle()
                bundle.putString(EXTRA_IMAGE, photoReference)
                fragment.arguments = bundle
            }

            return fragment
        }

        private const val EXTRA_IMAGE = "extra_image"
        private const val PHOTO_URL = BantuKuyDev.PHOTO_URL
        private const val API_PLACEHOLDER = BantuKuyDev.API_PLACEHOLDER
        private const val API_KEY = BantuKuyDev.API_KEY

    }

}