package com.example.smartgreenscape

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartgreenscape.databinding.FragmentHistoryDataBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.json.JSONArray


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var macAddress: String
    private lateinit var spinner: Spinner
    private lateinit var airTemperatureBarChart: BarChart
    private lateinit var soilHumidityBarChart: BarChart
    private lateinit var binding: FragmentHistoryDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryDataBinding.inflate(inflater, container, false)

        //Spinner
//        spinner = binding.dataInterval
//        ArrayAdapter.createFromResource(
//            this.requireContext(),
//            R.array.list,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//        }

        //取得macAddress
        if (activity?.intent?.hasExtra("macAddress") == true) {
            macAddress = activity?.intent?.getStringExtra("macAddress").toString()
        } else {
            macAddress = "A0:B7:65:DE:0C:08"
        }

        get24HData(macAddress){ jsonArray ->
            //airTemperatureLineChart
            airTemperatureBarChart(binding, jsonArray)

            //soilHumidityLineChart
            soilHumidityBarChart(binding, jsonArray)
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun get24HData(macAddress: String?, callback: (JSONArray) -> Unit){
        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.0.188:8000/api/plant/${macAddress}/data/24hr"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("HKT", "Response: $response")
                val jsonArray = JSONArray(response)
                callback(jsonArray)
            },
            { _ ->
                Toast.makeText(context, "取得資料失敗!", Toast.LENGTH_LONG).show();
            })
        queue.add(stringRequest)
    }

    private fun airTemperatureBarChart(binding: FragmentHistoryDataBinding, jsonArray: JSONArray){
        airTemperatureBarChart = binding.airTemperatureBarChart

        val entries = mutableListOf<BarEntry>()
        val xList = mutableListOf<String>()

        for(i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            entries.add(BarEntry(i.toFloat(), item.getDouble("temperature").toFloat()))
            xList.add(item.getString("hours"))
        }

        var xAxis = airTemperatureBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.GRAY
        xAxis.textSize = 12f
        xAxis.valueFormatter = IndexAxisValueFormatter(xList)

        val dataSet = BarDataSet(entries, "空氣溫度")

        val barData = BarData(dataSet)
        barData.barWidth = 0.3f // 調整長條寬度
        airTemperatureBarChart.data = barData

        var description = airTemperatureBarChart.description
        description.text = ""

        airTemperatureBarChart.setNoDataText("沒有數據")
        airTemperatureBarChart.setNoDataTextColor(R.color.dark_gray)
        airTemperatureBarChart.axisRight.isEnabled = false
    }

    private fun soilHumidityBarChart(binding: FragmentHistoryDataBinding, jsonArray: JSONArray){
        soilHumidityBarChart = binding.soilHumidityBarChart

        val entries = mutableListOf<BarEntry>()
        val xList = mutableListOf<String>()

        for(i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            entries.add(BarEntry(i.toFloat(), item.getDouble("soil_humidity").toFloat()))
            xList.add(item.getString("hours"))
        }

        Log.d("xList", xList.toString())
        var xAxis = soilHumidityBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.GRAY
        xAxis.textSize = 12f
        xAxis.valueFormatter = IndexAxisValueFormatter(xList)

        val dataSet = BarDataSet(entries, "土壤濕度")

        val barData = BarData(dataSet)
        barData.barWidth = 0.3f // 調整長條寬度
        soilHumidityBarChart.data = barData


        var description = soilHumidityBarChart.description
        description.text = ""
        description.isEnabled = false

        soilHumidityBarChart.setNoDataText("沒有數據")
        soilHumidityBarChart.setNoDataTextColor(R.color.dark_gray)
        soilHumidityBarChart.axisRight.isEnabled = false
    }
}