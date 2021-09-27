package com.task.krishinetwork.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.task.krishinetwork.api.model.OtherMandi
import com.task.krishinetwork.databinding.ViewCropBinding

class CropAdapter(private val list: List<OtherMandi>): RecyclerView.Adapter<CropAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ViewCropBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewCropBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CropAdapter.ViewHolder, position: Int) {

        with(holder) {
            with(list[position]) {
                binding.cropBackImg.load(this.image)
                binding.marketName.text = this.market
                binding.distCityName.text = "${this.district}, ${this.state}"
                binding.cityName.text = this.district
                binding.distanceText.text = "${this.km} Km"
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



}