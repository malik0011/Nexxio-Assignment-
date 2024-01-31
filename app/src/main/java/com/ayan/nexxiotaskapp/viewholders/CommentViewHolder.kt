package com.ayan.nexxiotaskapp.viewholders

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
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

    fun bind(data: ResponseItem) {

        //setting title
        binding.title.text = data.title

        if (data.comment != null) {
            binding.editText.text = Editable.Factory.getInstance().newEditable(data.comment)
        }

        if (data.isCommentBoxVisible != null) {
            binding.toggleBtn.isChecked = data.isCommentBoxVisible!!
            binding.editText.isVisible = data.isCommentBoxVisible!!
        }

        binding.toggleBtn.setOnCheckedChangeListener { _, isChecked ->
            // Handle the switch state change
            binding.editText.isVisible = isChecked
        }

        binding.editText.doAfterTextChanged {
            data.comment = it.toString()
            listener.commentItemInteracted( position, it.toString(), binding.editText.isVisible)
        }
    }
}