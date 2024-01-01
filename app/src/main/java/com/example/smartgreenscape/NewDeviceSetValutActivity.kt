package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartgreenscape.databinding.ActivityNewDeviceSetValueBinding
import com.example.smartgreenscape.model.Plant
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import java.nio.charset.Charset

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
    private lateinit var plant: Plant
    private lateinit var macAddress:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceSetValueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("macAddress")) {
            macAddress = intent.getStringExtra("macAddress").toString()
            Log.d("sfef",macAddress)
        }
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
            plant = Plant(
                min_temperature = temperature_min.text.toString().toDouble(),
                max_temperature = temperature_max.text.toString().toDouble(),
                min_humidity = humidity_min.text.toString().toDouble(),
                max_humidity = humidity_max.text.toString().toDouble(),
                min_soil_humidity = soil_humidity_min.text.toString().toDouble(),
                max_soil_humidity = soil_humidity_max.text.toString().toDouble()
            )
            saveDevice()
        }

        //上一步按鈕
        previousButton.setOnClickListener {
            finish()
        }


    }
    private fun saveDevice(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.213.10:8000/api/plant"
        val jsonObject = JSONObject()
        jsonObject.put("min_temperature", plant.min_temperature)
        jsonObject.put("max_temperature", plant.max_temperature)
        jsonObject.put("min_humidity", plant.min_humidity)
        jsonObject.put("max_humidity", plant.max_humidity)
        jsonObject.put("min_soil_humidity", plant.min_soil_humidity)
        jsonObject.put("max_soil_humidity", plant.max_soil_humidity)
        jsonObject.put("mac_address", macAddress)

        val requestBody = jsonObject.toString()
        val stringRequest = object : StringRequest(
            Method.POST, url, { response ->
                val jsonObject = JSONObject(response.toString())
                Toast.makeText(this, "新增資料成功!", Toast.LENGTH_LONG).show();
            },
            { _ ->
                Toast.makeText(this, "新增資料失敗!", Toast.LENGTH_LONG).show();
            }){
            override fun getBody(): ByteArray {

                return requestBody.toByteArray(Charset.defaultCharset())
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue.add(stringRequest)

        val intentMainActivity = Intent(this@NewDeviceSetValutActivity, MainActivity::class.java)
        startActivity(intentMainActivity)
    }
}