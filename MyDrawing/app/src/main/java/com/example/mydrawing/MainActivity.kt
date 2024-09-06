package com.example.mydrawing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydrawing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        val binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            // Load ClickFragment initially
            val clickFragment = ClickFragment()
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, clickFragment, "click_fragment")
                .commit()

            // Set up the button function to navigate to DrawFragment
            clickFragment.setButtonFunction {
                val drawFragment = DrawFragment()
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainerView.id, drawFragment, "draw_fragment")
                    .addToBackStack(null) // Add to back stack to enable back navigation
                    .commit()
            }
        }

        setContentView(binding.root)
    }

}