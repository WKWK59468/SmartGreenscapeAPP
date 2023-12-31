package com.example.smartgreenscape

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
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
import com.example.smartgreenscape.service.JobApiService
import com.example.smartgreenscape.service.NotifyService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private var deviceList:MutableList<Device> = mutableListOf()
    private lateinit var binding: ActivityMainBinding
    private lateinit var deviceListView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var addDeviceButton: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter:DeviceListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceListView = binding.deviceList
        emptyTextView = binding.emptyElement
        addDeviceButton = binding.addDevice

        deviceListView.emptyView = emptyTextView
        sharedPreferences = getSharedPreferences("TotalDevice", MODE_PRIVATE)
        val previousUserList = getDevicerList().toMutableList()
        deviceList.addAll(previousUserList)

        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->
            val retrievedUserList = getDevicerList().toMutableList()
            deviceList.clear()
            deviceList.addAll(retrievedUserList)
            adapter = DeviceListAdapter( this , R.layout.device_list_textview, deviceList)
            deviceListView.adapter = adapter

        }

        adapter = DeviceListAdapter( this , R.layout.device_list_textview, deviceList)
        deviceListView.adapter = adapter

        deviceListView.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intentPlantControlPlatformActivity = Intent(this@MainActivity, PlantControlPlatformActivity::class.java)
                intentPlantControlPlatformActivity.putExtra("macAddress", deviceList[position].macAddress)
                intentPlantControlPlatformActivity.putExtra("plantName", deviceList[position].plantName)

                startActivity(intentPlantControlPlatformActivity)
            }
        }

        addDeviceButton.setOnClickListener{
            val intentNewDeviceSetTypeActivity = Intent(this@MainActivity, BluetoothActivity::class.java)
            startActivity(intentNewDeviceSetTypeActivity)
        }
        startService(Intent(this@MainActivity,NotifyService::class.java))
    }
    private fun getDevicerList(): List<Device> {
        val gson = Gson()
        val deviceListJson = sharedPreferences.getString("device", null)
        return if (deviceListJson != null) {
            val type = object : TypeToken<List<Device>>() {}.type
            gson.fromJson(deviceListJson, type)
        } else {
            emptyList()
        }
    }
    private fun scheduleJob(context: Context) {
        Log.d("servicedfsdfsdf","scheduleJob")
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(context, JobApiService::class.java)

        val jobInfo = JobInfo.Builder(1, componentName)
            .setMinimumLatency(TimeUnit.MINUTES.toMillis(1))
//            .setPeriodic(TimeUnit.MINUTES.toMillis(1)) // 周期性執行間隔設定為 1 分鐘
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // 需要任何網絡條件
            .build()

        jobScheduler.schedule(jobInfo)
    }
}