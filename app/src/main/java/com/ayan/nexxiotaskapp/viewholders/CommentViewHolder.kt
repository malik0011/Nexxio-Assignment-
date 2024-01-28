package com.ayan.nexxiotaskapp.viewholders

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.ayan.nexxiotaskapp.databinding.CommentItemBinding
import com.ayan.nexxiotaskapp.interfaces.ListClickListener
import com.ayan.nexxiotaskapp.models.ResponseItem

class CommentViewHolder(
    private val binding: CommentItemBinding,
    private val listener: ListClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private var currentText: String? = null
    private var currentPosition: Int = RecyclerView.NO_POSITION

    init {
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // If EditText loses focus, update the comment item
                Handler(Looper.getMainLooper()).post {
                    if (currentText != null) listener.commentItemInteracted(
                        currentPosition,
                        currentText,
                        binding.editText.isVisible
                    )
                }
            }
        }
    }

    fun bind(data: ResponseItem) {
        //storing text & position for future update
        currentPosition = position
        binding.title.text = data.title

        if (data.comment != null) {
            binding.editText.text.clear()
            binding.editText.text = Editable.Factory.getInstance().newEditable(data.comment)
        }
        if (data.isCommentBoxVisible != null) {
            binding.toggleBtn.isChecked = data.isCommentBoxVisible!!
            binding.editText.isVisible = data.isCommentBoxVisible!!
        }
        binding.toggleBtn.setOnCheckedChangeListener { _, isChecked ->
            // Handle the switch state change
            Handler(Looper.getMainLooper()).post {
                listener.commentItemInteracted(position, currentText, isChecked)
            }
        }
        binding.editText.doOnTextChanged { text, _, _, _ ->
            currentText = text.toString()
        }
    }
}