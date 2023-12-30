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
import android.view.ViewGroup
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
import com.example.smartgreenscape.adapter.BluetoothListAdapter
import com.example.smartgreenscape.adapter.DeviceListAdapter
import com.example.smartgreenscape.adapter.OnButtonClickListener
import com.example.smartgreenscape.model.Bluetooth


class BluetoothActivity : AppCompatActivity(), OnButtonClickListener {
    private var btPermission =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)
    }
    fun scanBt(view:View){
        val bluetoothManager:BluetoothManager=getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter:BluetoothAdapter?=bluetoothManager.adapter
        val connectPermission = "android.permission.BLUETOOTH_CONNECT"
        val adminPermission = "android.permission.BLUETOOTH_ADMIN"
        if(bluetoothAdapter==null)
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
        val closeDialog=dialogView.findViewById<Button>(R.id.close)
        var bluetoothListData = mutableListOf<Bluetooth>()
        val dialog=builder.create()
        closeDialog.setOnClickListener{
            dialog.dismiss()
        }
        val pairedDevices:Set<BluetoothDevice> = bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
        if(pairedDevices.isNotEmpty()){
            for(device in pairedDevices){
                val newItem = Bluetooth(device.name, device.address)
                bluetoothListData.add(newItem)
            }
            val adapter = BluetoothListAdapter( this , com.example.smartgreenscape.R.layout.blutetooth_list, bluetoothListData)
            adapter.onButtonClickListener = this
            btlst.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        else{
            val value="No Devices Found"
            Toast.makeText(this,value,Toast.LENGTH_LONG).show()
            return
        }
        dialog.show()
    }
    override fun onButtonClick(adapter: BluetoothListAdapter, position: Int,macAddress:String) {
        val intentNewDeviceSetTypeActivity = Intent(this@BluetoothActivity, NewDeviceSetTypeActivity::class.java)
        intentNewDeviceSetTypeActivity.putExtra("macAddress", macAddress)
        startActivity(intentNewDeviceSetTypeActivity)
    }
}
