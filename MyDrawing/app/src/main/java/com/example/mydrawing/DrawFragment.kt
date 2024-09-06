package com.example.mydrawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mydrawing.databinding.FragmentDrawBinding

class DrawFragment : Fragment() {

    // Shared ViewModel to retain the drawing across fragments
    private val viewModel: SimpleViewModel by activityViewModels()
    private lateinit var binding: FragmentDrawBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawBinding.inflate(inflater, container, false)

        // Observe the bitmap from ViewModel and update the CustomView when it changes
        viewModel.bitmap.observe(viewLifecycleOwner) { savedBitmap ->
            savedBitmap?.let {
                binding.customView.passBitmap(it) // Pass the saved bitmap to the custom view
            }
        }

        // Return the inflated layout for the DrawFragment
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Ensure we have access to the custom view and save the current bitmap to the ViewModel.
        // This will preserve the drawing even when the fragment is paused.
        val currentBitmap = binding.customView.getBitmap()

        // Save the current bitmap in the ViewModel to persist it across fragment changes.
        viewModel.updateBitmap(currentBitmap)
    }


}