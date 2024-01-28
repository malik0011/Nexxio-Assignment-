package com.ayan.nexxiotaskapp.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayan.nexxiotaskapp.databinding.CommentItemBinding
import com.ayan.nexxiotaskapp.databinding.OptionsItemBinding
import com.ayan.nexxiotaskapp.databinding.PhotoItemBinding
import com.ayan.nexxiotaskapp.interfaces.ListClickListener
import com.ayan.nexxiotaskapp.models.ResponseItem
import com.ayan.nexxiotaskapp.viewholders.CommentViewHolder
import com.ayan.nexxiotaskapp.viewholders.OptionsViewHolder
import com.ayan.nexxiotaskapp.viewholders.PhotoViewHolder

class ListAdapter(
    private val dataList: List<ResponseItem>,
    private val listener: ListClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val PHOTO = "PHOTO"
    private val COMMENT = "COMMENT"
    private val OPTIONS = "SINGLE_CHOICE"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            1 -> {
                val binding = PhotoItemBinding.inflate(inflater, parent, false)
                PhotoViewHolder(binding, listener)
            }

            2 -> {
                val binding = CommentItemBinding.inflate(inflater, parent, false)
                CommentViewHolder(binding, listener)
            }

            3 -> {
                val binding = OptionsItemBinding.inflate(inflater, parent, false)
                OptionsViewHolder(binding, listener)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        when (holder) {
            is PhotoViewHolder -> holder.bind(item)
            is CommentViewHolder -> holder.bind(item)
            is OptionsViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        // Return the layout resource ID based on the item type
        val item = dataList[position]
        return when (item.type) {
            PHOTO -> 1
            COMMENT -> 2
            OPTIONS -> 3
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun updatePhoto(position: Int, imageBitmap: Bitmap) {
        dataList[position].imageBitmap = imageBitmap
        notifyItemChanged(position)
    }

    fun updateSelectedRadioBtn(position: Int, text: String) {
        dataList[position].selectedRadioButtonText = text
        notifyItemChanged(position)
    }

    fun updateCommentItem(position: Int, commentBoxVisible: Boolean, comment: String?) {
        dataList[position].isCommentBoxVisible = commentBoxVisible
        if (comment != null) dataList[position].comment = comment
        notifyItemChanged(position)
    }

    fun getUpdatedList(): List<ResponseItem> {
        return dataList
    }
}