package com.example.smartgreenscape

import android.R
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceListView = binding.deviceList
        emptyTextView = binding.emptyElement

        deviceListView.emptyView = emptyTextView

        val adapter = DeviceListAdapter( this , com.example.smartgreenscape.R.layout.device_list_textview, menu)
        deviceListView.adapter = adapter

    }
}