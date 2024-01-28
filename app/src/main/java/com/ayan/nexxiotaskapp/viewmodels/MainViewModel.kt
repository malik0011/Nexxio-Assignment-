package com.ayan.nexxiotaskapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayan.nexxiotaskapp.R
import com.ayan.nexxiotaskapp.models.Response
import com.google.gson.Gson

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    fun initializeJsonData(context: Context) {
        val dataList = context.resources.openRawResource(R.raw.data).bufferedReader().use { it.readText() }
        _response.value = dataList.toObject()
    }

    // Extension function to convert JSON string to an object
    private inline fun <reified T> String.toObject(): T {
        return Gson().fromJson(this, T::class.java)
    }
}
