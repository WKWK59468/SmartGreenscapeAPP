package com.example.smartgreenscape

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.LocalDensity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartgreenscape.databinding.FragmentCurrentDataBinding
import com.example.smartgreenscape.model.CurrentPlantData
import com.example.smartgreenscape.model.Environmental
import com.example.smartgreenscape.model.Plant
import com.example.smartgreenscape.model.Tag
import com.example.smartgreenscape.service.EnvironmentalApi
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding:FragmentCurrentDataBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var environmentalList:MutableList<Environmental.Record> = mutableListOf()
    private lateinit var macAddress: String

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
        binding= FragmentCurrentDataBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(){
            binding.currentTime.text=environmentalList[0].monitordate
            val filteredHumidity = environmentalList.filter { it.itemid == "38" }
            filteredHumidity.forEach {
                binding.opendataAirHumidity.text=it.itemname+"："+it.concentration+it.itemunit
            }
            val filteredTemperature = environmentalList.filter { it.itemid == "14" }
            filteredTemperature.forEach {
                binding.opendataAirTemperature.text=it.itemname+"："+it.concentration+it.itemunit
            }
        }

        if (activity?.intent?.hasExtra("macAddress") == true) {
            macAddress = activity?.intent?.getStringExtra("macAddress").toString()
        } else {
            macAddress = "A0:B7:65:DE:0C:08"
        }
        getCurrentData(macAddress){ currentPlantData->
            binding.soilHumidity.text = currentPlantData.soil_humidity.toString()
            binding.airTemperature.text = currentPlantData.temperature.toString()
            binding.airHumidity.text = currentPlantData.humidity.toString()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CurrentDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurrentDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getData(callback: () -> Unit){
        EnvironmentalApi
            .retofitService
            .getEnvironmental(key="0b8bd6a6-1f66-4215-9e91-689b504acc47")
            .enqueue(object:retrofit2.Callback<Environmental>{
                override fun onResponse(
                    call: Call<Environmental>,
                    response: Response<Environmental>
                ) {
                    if(response.isSuccessful){
                        environmentalList= response.body()?.records?.filter{
                            (it.sitename=="西屯" &&(it.itemid=="14"|| it.itemid=="38"))
                        } as MutableList<Environmental.Record>
                    }
                    environmentalList= environmentalList.take(2) as MutableList<Environmental.Record>

                    callback()

                }

                override fun onFailure(call: Call<Environmental>, t: Throwable) {
                    Log.d("fail",t.toString())
                }


            })
    }
    fun getCurrentData(macAddress: String?,callback: (CurrentPlantData) -> Unit){
        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.213.10:8000/api/plant/${macAddress}/data/timely"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response.toString())
                val currentPlantData = CurrentPlantData(
                    temperature = jsonObject.getDouble("temperature"),
                    humidity = jsonObject.getDouble("humidity"),
                    soil_humidity = jsonObject.getDouble("soil_humidity"),
                    time = jsonObject.getString("time"),
                )
                callback(currentPlantData)
            },
            { _ ->
                Toast.makeText(context, "取得資料失敗!", Toast.LENGTH_LONG).show();
            })
        queue.add(stringRequest)
    }
}