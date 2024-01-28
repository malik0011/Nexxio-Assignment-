package com.ayan.nexxiotaskapp.models

import android.graphics.Bitmap

data class ResponseItem(
    val dataMap: DataMap,
    val id: String,
    val title: String,
    val type: String,
    var imageBitmap: Bitmap? = null,
    var selectedRadioButtonText: String? = null,
    var comment: String? = null,
    var isCommentBoxVisible: Boolean? = null
)