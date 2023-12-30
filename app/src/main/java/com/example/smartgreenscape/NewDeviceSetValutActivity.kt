package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.databinding.ActivityNewDeviceSetValueBinding

class NewDeviceSetValutActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityNewDeviceSetValueBinding
    private lateinit var lastPageButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var previousButton: Button
    private lateinit var temperature_min: EditText
    private lateinit var temperature_max: EditText
    private lateinit var humidity_min: EditText
    private lateinit var humidity_max: EditText
    private lateinit var soil_humidity_min: EditText
    private lateinit var soil_humidity_max: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceSetValueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lastPageButton = binding.lastPage
        saveButton = binding.saveButton
        previousButton = binding.previousButton
        temperature_min = binding.temperatureMin
        temperature_max = binding.temperatureMax
        humidity_min = binding.humidityMin
        humidity_max = binding.humidityMax
        soil_humidity_min = binding.soilHumidityMin
        soil_humidity_max = binding.soilHumidityMax

        val receivedIntent = intent
        val deviceName = receivedIntent.getStringExtra("receivedIntent")
        val temperatureMin = receivedIntent.getDoubleExtra("temperature_min",0.0)
        val temperatureMax = receivedIntent.getDoubleExtra("temperature_max",0.0)
        val humidityMin = receivedIntent.getDoubleExtra("humidity_min",0.0)
        val humidityMax = receivedIntent.getDoubleExtra("humidity_max",0.0)
        val soilHumidityMin = receivedIntent.getDoubleExtra("soil_humidity_min",0.0)
        val soilHumidityMax = receivedIntent.getDoubleExtra("soil_humidity_max",0.0)

        // 預設值
        temperature_min.setText(temperatureMin.toString())
        temperature_max.setText(temperatureMax.toString())
        humidity_min.setText(humidityMin.toString())
        humidity_max.setText(humidityMax.toString())
        soil_humidity_min.setText(soilHumidityMin.toString())
        soil_humidity_max.setText(soilHumidityMax.toString())

        //上一頁按鈕
        lastPageButton.setOnClickListener{
            finish()
        }

        //保存按鈕
        saveButton.setOnClickListener {
            saveDevice()
        }

        //上一步按鈕
        previousButton.setOnClickListener {
            finish()
        }


    }
    private fun saveDevice(){

        //TODO 保存資料接口


        val intentMainActivity = Intent(this@NewDeviceSetValutActivity, MainActivity::class.java)
        startActivity(intentMainActivity)
    }
//    private fun previousStep(){
//        val intentNewDeviceSetTypeActivity = Intent(this@NewDeviceSetValutActivity, NewDeviceSetTypeActivity::class.java)
//        startActivity(intentNewDeviceSetTypeActivity)
//    }
}