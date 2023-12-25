package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.databinding.ActivityPlantControlPlatformBinding

class PlantControlPlatformActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityPlantControlPlatformBinding
    private lateinit var lastPageButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantControlPlatformBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lastPageButton = binding.lastPage

        lastPageButton.setOnClickListener{
            val intentMainActivity = Intent(this@PlantControlPlatformActivity, MainActivity::class.java)
            startActivity(intentMainActivity)
        }
    }
}