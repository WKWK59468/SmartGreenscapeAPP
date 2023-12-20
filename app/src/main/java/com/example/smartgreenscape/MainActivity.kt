package com.example.smartgreenscape

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val menu = arrayOf("第一章 光學發展歷史","第二章 光的反射","第三章 波動光學","第四章 光的產生")
    private lateinit var binding: ActivityMainBinding
    private lateinit var deviceListView: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceListView = binding.deviceList
        emptyTextView = binding.emptyElement  // Replace with the actual ID of your TextView

        // Set the empty view for the ListView
//        deviceListView.emptyView = emptyTextView

        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, menu)
        deviceListView.adapter = adapter

        deviceListView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                println("選中的項目： ${menu[position]}")
            }

        }

    }
}