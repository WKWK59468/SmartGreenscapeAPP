package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.content.Context
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartgreenscape.databinding.ActivityNewDeviceSetTypeBinding
import com.example.smartgreenscape.model.Plant
import com.example.smartgreenscape.model.Tag
import org.json.JSONArray
import org.json.JSONObject

class NewDeviceSetTypeActivity : AppCompatActivity()  {
    private val dataList = mutableListOf<Tag>()
    private lateinit var tag: Tag
    private lateinit var binding: ActivityNewDeviceSetTypeBinding
    private lateinit var lastPageButton: ImageButton
    private lateinit var nextButton: Button
    private lateinit var cancelButton: Button
    private lateinit var deviceName: EditText
    private lateinit var deviceAddress:TextView

    private lateinit var buttonContainer: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceSetTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceAddress=binding.deviceAddress
        if (intent.hasExtra("macAddress")) {
            val macAddress = intent.getStringExtra("macAddress")
            deviceAddress.text=macAddress.toString()
            Log.d("intentA",macAddress.toString())
        }
        lastPageButton = binding.lastPage
        nextButton = binding.nextButton
        cancelButton = binding.cancelButton
        deviceName = binding.deviceName
        buttonContainer = binding.buttonContainer
        getDefaultPlantClass(){
            for (data in dataList) {
                val button = RadioButton(this)
                button.text = data.name
                button.setBackgroundResource(R.drawable.tag_button)
                button.textSize = resources.getDimension(R.dimen.button_text_size)

                //設定按鈕樣式
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    resources.getDimensionPixelSize(R.dimen.fixed_height)
                )
                params.setMargins(0, 15, 30, 0)
                button.layoutParams = params
                button.setPaddingRelative(10,5,30,5)

                //按鈕事件監聽
                button.setOnClickListener {
                    tag = data
                }
                buttonContainer.addView(button)
            }
        }


        //返回按鈕
        lastPageButton.setOnClickListener{
            previousStep()
        }

        //取消按鈕
        cancelButton.setOnClickListener {
            previousStep()
        }

        //下一步按鈕
        nextButton.setOnClickListener {
            showAlertDialog(tag, deviceName.text.toString())
        }
    }
    fun getDefaultPlantClass(callback: () -> Unit){
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.213.10:8000/api/default-class"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                for(i in 0 until jsonArray.length()) {

                    val item = jsonArray.getJSONObject(i)
                    val plantDefaultClass = Tag(
                        id = item.getInt("id"),
                        name = item.getString("name") ,
                        min_temperature = item.getDouble("min_temperature"),
                        max_temperature= item.getDouble("max_temperature"),
                        min_humidity= item.getDouble("min_humidity"),
                        max_humidity= item.getDouble("max_humidity"),
                        min_soil_humidity= item.getDouble("min_soil_humidity"),
                        max_soil_humidity= item.getDouble("max_soil_humidity")
                    )

                    dataList.add(plantDefaultClass)
                }
                callback()
            },
            { _ ->
                Toast.makeText(this, "取得資料失敗!", Toast.LENGTH_LONG).show();
            })
        queue.add(stringRequest)
    }

    private fun showAlertDialog(tag: Tag?, deviceName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("植物生長資訊")
        builder.setMessage("植物生長資訊是否使用預設建議")

        // 是的點擊事件
        builder.setPositiveButton("是") { dialog, which ->
            //TODO 取得預設數值接口 帶入tag
            nextStep(tag, deviceName, deviceAddress.text.toString())
        }

        // 否的點擊事件
        builder.setNegativeButton("否") { dialog, which ->
            nextStep(null, deviceName, null)
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun previousStep(){
//        val intentMainActivity = Intent(this@NewDeviceSetTypeActivity, MainActivity::class.java)
//        startActivity(intentMainActivity)
        finish()
    }
    private fun nextStep(tag: Tag?, deviceName: String, macAddress: String?){
        val intentNewDeviceSetTypeActivity = Intent(this@NewDeviceSetTypeActivity, NewDeviceSetValutActivity::class.java)
        intentNewDeviceSetTypeActivity.putExtra("deviceName", deviceName)
        macAddress?.let {
            intentNewDeviceSetTypeActivity.putExtra("macAddress", macAddress)
        }
        tag?.let {
            intentNewDeviceSetTypeActivity.putExtra("temperature_min", it.min_temperature)
            intentNewDeviceSetTypeActivity.putExtra("temperature_max", it.max_temperature)
            intentNewDeviceSetTypeActivity.putExtra("humidity_min", it.min_humidity)
            intentNewDeviceSetTypeActivity.putExtra("humidity_max", it.max_humidity)
            intentNewDeviceSetTypeActivity.putExtra("soil_humidity_min", it.min_soil_humidity)
            intentNewDeviceSetTypeActivity.putExtra("soil_humidity_max", it.max_soil_humidity)
        }
        startActivity(intentNewDeviceSetTypeActivity)
    }

}