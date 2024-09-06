package com.example.mydrawing

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimpleViewModel : ViewModel() {
    // MutableLiveData for the bitmap
    private val _bitmap: MutableLiveData<Bitmap> =
        MutableLiveData(Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888))
    val bitmap: LiveData<Bitmap> = _bitmap

    // Method to update the bitmap in the ViewModel
    fun updateBitmap(newBitmap: Bitmap) {
        _bitmap.value = newBitmap
    }

}