package com.example.mydrawing

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.example.mydrawing.databinding.FragmentClickBinding

class ClickFragment: Fragment(){
    private var buttonFunction: () -> Unit = {}
    private val viewModel : SimpleViewModel by activityViewModels()
    fun setButtonFunction(newFunc: () -> Unit){
        buttonFunction = newFunc
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentClickBinding.inflate(inflater, container, false)

        // Set up the click listener for the button
        binding.clickMe.setOnClickListener{
            // Save the current bitmap from the ViewModel before navigating
            val currentBitmap = viewModel.bitmap.value
            currentBitmap?.let {
                // Save the bitmap (optional, if needed)
                viewModel.updateBitmap(it)
            }
            buttonFunction()
        }
        return binding.root
    }

}