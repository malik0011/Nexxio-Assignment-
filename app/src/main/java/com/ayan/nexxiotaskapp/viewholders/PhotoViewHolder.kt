package com.ayan.nexxiotaskapp.viewholders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.ayan.nexxiotaskapp.databinding.PhotoItemBinding
import com.ayan.nexxiotaskapp.interfaces.ListClickListener
import com.ayan.nexxiotaskapp.models.ResponseItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.Rotate

class PhotoViewHolder(
    private val binding: PhotoItemBinding,
    private val listener: ListClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ResponseItem) {

        binding.tvPhotoTitle.text = data.title

        if (data.imageBitmap != null) {
            Glide.with(binding.root.context)
                .asBitmap()
                .load(data.imageBitmap)
                .transform(Rotate(90)) // Automatically correct rotation based on Exif data
                .transition(BitmapTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // DiskCacheStrategy to improve performance
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(binding.mainPhoto)
        } else binding.mainPhoto.setImageBitmap(null) //for handling corner cases

        binding.btnRemove.setOnClickListener {
            binding.mainPhoto.setImageBitmap(null)
            data.imageBitmap?.recycle() //recycling for better performance
            data.imageBitmap = null
            listener.imageItemClicked(position, 2)
        }
        binding.mainPhoto.setOnClickListener {
            if (data.imageBitmap == null) listener.imageItemClicked(position, 1)
        }
    }
}