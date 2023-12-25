package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.databinding.ActivityNewDeviceSetValueBinding

class NewDeviceSetValutActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityNewDeviceSetValueBinding
    private lateinit var lastPageButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var previousButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceSetValueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lastPageButton = binding.lastPage
        saveButton = binding.saveButton
        previousButton = binding.previousButton

        lastPageButton.setOnClickListener{
            val intentNewDeviceSetTypeActivity = Intent(this@NewDeviceSetValutActivity, NewDeviceSetTypeActivity::class.java)
            startActivity(intentNewDeviceSetTypeActivity)
        }

        saveButton.setOnClickListener {
            val intentMainActivity = Intent(this@NewDeviceSetValutActivity, MainActivity::class.java)
            startActivity(intentMainActivity)
        }

        previousButton.setOnClickListener {
            val intentNewDeviceSetTypeActivity = Intent(this@NewDeviceSetValutActivity, NewDeviceSetTypeActivity::class.java)
            startActivity(intentNewDeviceSetTypeActivity)
        }
    }
}