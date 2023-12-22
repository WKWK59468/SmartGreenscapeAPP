package com.example.smartgreenscape.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.smartgreenscape.R
import com.example.smartgreenscape.model.DeviceList

class DeviceListAdapter(context: Context, resource: Int, objects: List<DeviceList>) :
    ArrayAdapter<DeviceList>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.device_list_textview, parent, false)

        val deviceName = rowView.findViewById<TextView>(R.id.deviceName)
        val plantName = rowView.findViewById<TextView>(R.id.plantName)

        val currentItem = getItem(position)

        deviceName.text = currentItem?.deviceName
        plantName.text = currentItem?.plantName

        return rowView
    }
}