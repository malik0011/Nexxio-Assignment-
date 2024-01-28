package com.ayan.nexxiotaskapp.interfaces

interface ListClickListener {
    fun radioButtonCLicked(position: Int, text: String)
    fun imageItemClicked(position: Int, type: Int)
    fun commentItemInteracted(position: Int, comment: String?, isCommentBoxVisible: Boolean)
}