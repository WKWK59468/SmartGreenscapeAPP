package com.example.smartgreenscape

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartgreenscape.databinding.FragmentCurrentDataBinding
import com.example.smartgreenscape.databinding.FragmentInfoBinding
import com.example.smartgreenscape.model.Plant
import org.json.JSONObject
import java.nio.charset.Charset

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var macAddress: String
    private lateinit var plantData: Plant
    private lateinit var binding: FragmentInfoBinding
    private lateinit var temperature_min: EditText
    private lateinit var temperature_max: EditText
    private lateinit var humidity_min: EditText
    private lateinit var humidity_max: EditText
    private lateinit var soil_humidity_min: EditText
    private lateinit var soil_humidity_max: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

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
        binding= FragmentInfoBinding.inflate(inflater,container,false)
        temperature_min = binding.temperatureMin
        temperature_max = binding.temperatureMax
        humidity_min = binding.humidityMin
        humidity_max = binding.humidityMax
        soil_humidity_min = binding.soilHumidityMin
        soil_humidity_max = binding.soilHumidityMax
        saveButton = binding.saveButton
        cancelButton = binding.cancelButton

        saveButton.setOnClickListener {
            //TODO macAddress要修改
            updateInfo(macAddress, plantData)
        }

        cancelButton.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity?.intent?.hasExtra("macAddress") == true) {
            macAddress = activity?.intent?.getStringExtra("macAddress").toString()
        } else {
            macAddress = "A0:B7:65:DE:0C:08"
        }

        getInfo(macAddress){ plant ->
            temperature_min.text = Editable.Factory.getInstance().newEditable(plant.min_temperature.toString())
            temperature_max.text = Editable.Factory.getInstance().newEditable(plant.max_temperature.toString())
            humidity_min.text = Editable.Factory.getInstance().newEditable(plant.min_humidity.toString())
            humidity_max.text = Editable.Factory.getInstance().newEditable(plant.max_humidity.toString())
            soil_humidity_min.text = Editable.Factory.getInstance().newEditable(plant.min_soil_humidity.toString())
            soil_humidity_max.text = Editable.Factory.getInstance().newEditable(plant.max_soil_humidity.toString())
            plantData = plant
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getInfo(macAddress: String?, callback: (Plant) -> Unit){
        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.0.188:8000/api/plant/${macAddress}"
        Log.d("HKsdfsdfT", url+"dsfsdf"+macAddress)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("HKT", "Response: $response")
                val jsonObject = JSONObject(response.toString())

                val plant = Plant(
                    min_temperature = jsonObject.getDouble("min_temperature"),
                    max_temperature = jsonObject.getDouble("max_temperature"),
                    min_humidity = jsonObject.getDouble("min_humidity"),
                    max_humidity = jsonObject.getDouble("max_humidity"),
                    min_soil_humidity = jsonObject.getDouble("min_soil_humidity"),
                    max_soil_humidity = jsonObject.getDouble("max_soil_humidity")
                )
                callback(plant)
            },
            { _ ->
                Toast.makeText(context, "取得資料失敗!", Toast.LENGTH_LONG).show();
            })
        queue.add(stringRequest)
    }

    fun updateInfo(macAddress: String?, plant: Plant){
        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.0.188:8000/api/plant"
        val requestBody = "your_request_body_here"

        val stringRequest = object : StringRequest(
            Method.PUT, url, { response ->
                Log.d("HKT", "Response: $response")
//                val jsonObject = JSONObject(response.toString())
//
//                if(password == jsonObject.getString("password")){
//                    val pref = getSharedPreferences("Access", AppCompatActivity.MODE_PRIVATE)
//                    pref.edit().putString("ACCOUNT",account).commit()
//                    pref.edit().putString("PASSWORD",jsonObject.getString("password")).commit()
//                    pref.edit().putString("USERNAME",jsonObject.getString("username")).commit()
//                }else{
//                    Toast.makeText(context, "帳號或密碼錯誤!", Toast.LENGTH_LONG).show();
//                }
                Toast.makeText(context, "更新資料成功!", Toast.LENGTH_LONG).show();
            },
            { _ ->
                Toast.makeText(context, "更新資料失敗!", Toast.LENGTH_LONG).show();
            }){
            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charset.defaultCharset())
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue.add(stringRequest)
    }
}