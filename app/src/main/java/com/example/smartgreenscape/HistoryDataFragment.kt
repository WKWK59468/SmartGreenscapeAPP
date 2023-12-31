package com.example.smartgreenscape

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


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
    private var entries = listOf(
        BarEntry(1f, 11f),
        BarEntry(2f, 10f),
        BarEntry(3f, 15f),
        BarEntry(4f, 13f),
        BarEntry(5f, 12f),
    )
    private var xList = listOf("1/1","1/2","1/3","1/4","1/5")
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var spinner: Spinner
    private lateinit var airTemperatureBarChart: BarChart
    private lateinit var soilHumidityBarChart: BarChart
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
        val view = inflater.inflate(R.layout.fragment_history_data, container, false)

        //Spinner
        spinner = view.findViewById<Spinner>(R.id.dataInterval)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //airTemperatureLineChart
        airTemperatureBarChart(view)

        //soilHumidityLineChart
        soilHumidityBarChart(view)

        return view
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

    private fun airTemperatureBarChart(view: View){
        airTemperatureBarChart = view.findViewById(R.id.airTemperatureBarChart)

        var xAxis = airTemperatureBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.GRAY
        xAxis.textSize = 12f
        xAxis.valueFormatter = IndexAxisValueFormatter(xList)

        val dataSet = BarDataSet(entries, "直條圖")
        airTemperatureBarChart.data = BarData(dataSet)

        var description = airTemperatureBarChart.description
        description.text = ""

        airTemperatureBarChart.setNoDataText("沒有數據")
        airTemperatureBarChart.setNoDataTextColor(R.color.dark_gray)
        airTemperatureBarChart.axisRight.isEnabled = false
    }

    private fun soilHumidityBarChart(view: View){
        soilHumidityBarChart = view.findViewById(R.id.soilHumidityBarChart)

        var xAxis = soilHumidityBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.GRAY
        xAxis.textSize = 12f
        xAxis.valueFormatter = IndexAxisValueFormatter(xList)

        val dataSet = BarDataSet(entries, "直條圖")
        soilHumidityBarChart.data = BarData(dataSet)


        var description = soilHumidityBarChart.description
        description.text = ""

        soilHumidityBarChart.setNoDataText("沒有數據")
        soilHumidityBarChart.setNoDataTextColor(R.color.dark_gray)
        soilHumidityBarChart.axisRight.isEnabled = false

        val leftAxis = soilHumidityBarChart.axisLeft
        leftAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "" // 這裡可以根據你的需求更改單位
            }
        }

    }
}