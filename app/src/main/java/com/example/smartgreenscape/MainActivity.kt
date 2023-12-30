package com.example.smartgreenscape

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.adapter.DeviceListAdapter
import com.example.smartgreenscape.databinding.ActivityMainBinding
import com.example.smartgreenscape.model.Device
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    val menu = listOf(
        Device("device 1", "仙人掌1"),
        Device("device 2", "仙人掌2"),
        Device("device 3", "仙人掌3"),
        Device("device 4", "仙人掌4")
    )
    private lateinit var binding: ActivityMainBinding
    private lateinit var deviceListView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var addDeviceButton: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceListView = binding.deviceList
        emptyTextView = binding.emptyElement
        addDeviceButton = binding.addDevice

        deviceListView.emptyView = emptyTextView

        val adapter = DeviceListAdapter( this , com.example.smartgreenscape.R.layout.device_list_textview, menu)
        deviceListView.adapter = adapter
        deviceListView.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intentPlantControlPlatformActivity = Intent(this@MainActivity, PlantControlPlatformActivity::class.java)

                sharedPreferences = getSharedPreferences("PlantInfo", MODE_PRIVATE)
                sharedPreferences.edit().putString("plantName", menu[position].plantName).apply()

                startActivity(intentPlantControlPlatformActivity)
            }
        }

        addDeviceButton.setOnClickListener{
            val intentNewDeviceSetTypeActivity = Intent(this@MainActivity, BluetoothActivity::class.java)
            startActivity(intentNewDeviceSetTypeActivity)
        }
    }
}