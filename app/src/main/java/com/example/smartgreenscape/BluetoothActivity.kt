package com.example.smartgreenscape

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService

class BluetoothActivity : AppCompatActivity() {
    private var btPermission =false
    private lateinit var deviceNameTextView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)
        deviceNameTextView=findViewById(R.id.device_name)

    }
    fun scanBt(view:View){
        val bluetoothManager:BluetoothManager=getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter:BluetoothAdapter?=bluetoothManager.adapter
        val connectPermission = "android.permission.BLUETOOTH_CONNECT"
        val adminPermission = "android.permission.BLUETOOTH_ADMIN"

        if(bluetoothAdapter==null){

        }
        else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
                blueToothPermissionLauncher.launch(connectPermission)
            }
            else{
                blueToothPermissionLauncher.launch(adminPermission)

            }
        }
    }
    private val blueToothPermissionLauncher=registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted:Boolean->
        if(isGranted){
            val bluetoothManager:BluetoothManager=getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter:BluetoothAdapter?=bluetoothManager.adapter
            btPermission=true
            if(bluetoothAdapter?.isEnabled==false){
                val enableBtIntent=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                btActivityResultLauncher.launch(enableBtIntent)
            }
            else{
                scanBt()
            }
        }
    }
    private val btActivityResultLauncher=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result:ActivityResult->
        if(result.resultCode== RESULT_OK){
            scanBt()
        }

    }

    @SuppressLint("MissingPermission")
    private fun scanBt(){
        val bluetoothManager:BluetoothManager=getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter:BluetoothAdapter?=bluetoothManager.adapter
        val builder=AlertDialog.Builder(this@BluetoothActivity)
        val inflater=layoutInflater
        val dialogView:View=inflater.inflate(R.layout.scan_bluetooth,null)
        builder.setCancelable(false)
        builder.setView(dialogView)
        val btlst=dialogView.findViewById<ListView>(R.id.bt_list)
        val dialog=builder.create()
        val pairedDevices:Set<BluetoothDevice> = bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
        val ADAhere:SimpleAdapter
        var data:MutableList<Map<String?,Any?>?>?=null
        data=ArrayList()
        if(pairedDevices.isNotEmpty()){
            val datanum1:MutableMap<String?,Any?> = HashMap()
            datanum1["A"]=""
            datanum1["B"]=""
            data.add(datanum1)
            for(device in pairedDevices){
                val datanum:MutableMap<String?,Any?> = HashMap()
                datanum["A"]=device.name
                datanum["B"]=device.address
                data.add(datanum)
            }
            val fromwhere= arrayOf("A")
            val viewswhere= intArrayOf(R.id.item)
            ADAhere= SimpleAdapter(this@BluetoothActivity,data,R.layout.blutetooth_list,fromwhere,viewswhere)
            btlst.adapter=ADAhere
            ADAhere.notifyDataSetChanged()
            btlst.onItemClickListener=AdapterView.OnItemClickListener{adapterView,view,position,l->
                val string = ADAhere.getItem(position) as HashMap<String,String>
                val deviceName=string["A"]
                val deviceAddress=string["B"]
                deviceNameTextView.text=deviceName
                dialog.dismiss()
            }
        }
        else{
            val value="No Devices Found"
            Toast.makeText(this,value,Toast.LENGTH_LONG).show()
            return
        }
        dialog.show()
    }
}