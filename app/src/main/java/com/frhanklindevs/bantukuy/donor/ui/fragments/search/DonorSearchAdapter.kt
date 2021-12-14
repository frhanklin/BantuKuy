package com.frhanklindevs.bantukuy.donor.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ItemsHomeBinding
import com.frhanklindevs.bantukuy.donor.data.PlaceItem
import com.frhanklindevs.bantukuy.utils.BantuKuyDev

class DonorSearchAdapter(private val listHomes: List<PlaceItem>): RecyclerView.Adapter<DonorSearchAdapter.HomeViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemsHomeBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val home = listHomes[position]
        val photoUrl = PHOTO_URL + home.photos?.get(0)?.photoReference + API_PLACEHOLDER + API_KEY
        println("Photo Url: $photoUrl")
//        val photo = "https://awsimages.detik.net.id/community/media/visual/2020/06/22/jawara-world-landscape-photographer-3.png?w=750&q=90"

        with(holder) {
            binding.searchTvTitle.text = home.name
            binding.searchTvSubtitle.text = home.formattedAddress
            Glide.with(itemView.context)
                .load(photoUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(binding.searchImgPoster)
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(home)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listHomes.size



    interface OnItemClickCallback {
        fun onItemClicked(home: PlaceItem)
    }

    companion object{
        private const val PHOTO_URL = BantuKuyDev.PHOTO_URL
        private const val API_PLACEHOLDER = BantuKuyDev.API_PLACEHOLDER
        private const val API_KEY = BantuKuyDev.API_KEY

    }
}