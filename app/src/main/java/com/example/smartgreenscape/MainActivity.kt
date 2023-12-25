package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.adapter.DeviceListAdapter
import com.example.smartgreenscape.databinding.ActivityMainBinding
import com.example.smartgreenscape.model.DeviceList


class MainActivity : AppCompatActivity() {
    val menu = listOf(
        DeviceList("device 1", "仙人掌1"),
        DeviceList("device 2", "仙人掌2"),
        DeviceList("device 3", "仙人掌3"),
        DeviceList("device 4", "仙人掌4")
    )
    private lateinit var binding: ActivityMainBinding
    private lateinit var deviceListView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var addDeviceButton: ImageButton

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
                startActivity(intentPlantControlPlatformActivity)
            }
        }

        addDeviceButton.setOnClickListener{
            val intentNewDeviceSetTypeActivity = Intent(this@MainActivity, NewDeviceSetTypeActivity::class.java)
            startActivity(intentNewDeviceSetTypeActivity)
        }
    }
}