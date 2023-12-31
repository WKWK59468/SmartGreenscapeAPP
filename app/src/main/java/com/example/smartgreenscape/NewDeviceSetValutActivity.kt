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
            plant = Plant(
                temperatureMin = temperature_min.text.toString().toDouble(),
                temperatureMax = temperature_max.text.toString().toDouble(),
                humidityMin = humidity_min.text.toString().toDouble(),
                humidityMax = humidity_max.text.toString().toDouble(),
                soilHumidityMin = soil_humidity_min.text.toString().toDouble(),
                soilHumidityMax = soil_humidity_max.text.toString().toDouble()
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
        val url = "http://localhost:8000/api/plant"
        val requestBody = plant.toString()

        val stringRequest = object : StringRequest(
            Method.POST, url, { response ->
                Log.d("HKT", "Response: $response")
                val jsonObject = JSONObject(response.toString())
//
//                if(password == jsonObject.getString("password")){
//                    val pref = getSharedPreferences("Access", AppCompatActivity.MODE_PRIVATE)
//                    pref.edit().putString("ACCOUNT",account).commit()
//                    pref.edit().putString("PASSWORD",jsonObject.getString("password")).commit()
//                    pref.edit().putString("USERNAME",jsonObject.getString("username")).commit()
//                }else{
//                    Toast.makeText(context, "帳號或密碼錯誤!", Toast.LENGTH_LONG).show();
//                }
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