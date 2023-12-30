package com.example.smartgreenscape.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.smartgreenscape.BluetoothActivity
import com.example.smartgreenscape.R
import com.example.smartgreenscape.model.Bluetooth
import com.example.smartgreenscape.model.Device
interface OnButtonClickListener {
    fun onButtonClick(adapter: BluetoothListAdapter, position: Int,macAddress:String="")
}
class BluetoothListAdapter(context: Context, resource: Int, objects: List<Bluetooth>) :
    ArrayAdapter<Bluetooth>(context, resource, objects) {
    var onButtonClickListener: OnButtonClickListener? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.blutetooth_list, parent, false)

        val bluetoothName = rowView.findViewById<TextView>(R.id.item_name)
        val addButton = rowView.findViewById<Button>(R.id.addDevice)

        val currentItem = getItem(position)

        bluetoothName.text = currentItem?.deviceName
        addButton.setOnClickListener{
            onButtonClickListener?.onButtonClick(this,position,currentItem!!.deviceAddress)
        }
        return rowView
    }
}