package com.ayan.nexxiotaskapp.viewholders

import android.content.Context
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ayan.nexxiotaskapp.R
import com.ayan.nexxiotaskapp.databinding.OptionsItemBinding
import com.ayan.nexxiotaskapp.databinding.PhotoItemBinding
import com.ayan.nexxiotaskapp.interfaces.ListClickListener
import com.ayan.nexxiotaskapp.models.ResponseItem

class OptionsViewHolder(
    private val binding: OptionsItemBinding,
    private val clickListener: ListClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ResponseItem) {
        binding.title.text = data.title
        // Accessing the RadioGroup in the layout
        val radioGroup: RadioGroup = binding.rgBtn
        //removing if btn already present
        radioGroup.removeAllViews()
        // Set up RadioButtons
        setupRadioGroup(
            radioGroup,
            data.dataMap.options,
            clickListener,
            data.selectedRadioButtonText
        )
    }

    private fun setupRadioGroup(
        radioGroup: RadioGroup,
        options: List<String>,
        onRadioButtonClickListener: ListClickListener?,
        selectedOption: String?
    ) {
        for (i in options.indices) {
            val radioButton = createRadioButton(options[i], onRadioButtonClickListener)
            radioGroup.addView(radioButton)

            // Check the RadioButton if it corresponds to the default option
            if (selectedOption != null && options[i] == selectedOption) {
                radioButton.isChecked = true
            }
        }
    }

    private fun createRadioButton(text: String, clickListener: ListClickListener?): RadioButton {
        val radioButton = RadioButton(binding.root.context)
        radioButton.text = text
        radioButton.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
        radioButton.id = View.generateViewId()

        // Setting a click listener for the RadioButton
        radioButton.setOnClickListener {
            clickListener?.radioButtonCLicked(position, text)
        }

        return radioButton
    }
}