package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.databinding.ActivityNewDeviceSetTypeBinding

class NewDeviceSetTypeActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityNewDeviceSetTypeBinding
    private lateinit var lastPageButton: ImageButton
    private lateinit var nextButton: Button
    private lateinit var cancelButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceSetTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lastPageButton = binding.lastPage
        nextButton = binding.nextButton
        cancelButton = binding.cancelButton

        nextButton.setOnClickListener {
            val intentNewDeviceSetValutActivity = Intent(this@NewDeviceSetTypeActivity, NewDeviceSetValutActivity::class.java)
            startActivity(intentNewDeviceSetValutActivity)
        }

        cancelButton.setOnClickListener {
            val intentMainActivity = Intent(this@NewDeviceSetTypeActivity, MainActivity::class.java)
            startActivity(intentMainActivity)
        }

        lastPageButton.setOnClickListener{
            val intentMainActivity = Intent(this@NewDeviceSetTypeActivity, MainActivity::class.java)
            startActivity(intentMainActivity)
        }
    }
}