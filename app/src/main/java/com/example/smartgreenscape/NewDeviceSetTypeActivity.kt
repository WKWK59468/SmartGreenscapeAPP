package com.example.smartgreenscape

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgreenscape.databinding.ActivityNewDeviceSetTypeBinding
import com.google.android.flexbox.FlexboxLayout

class NewDeviceSetTypeActivity : AppCompatActivity()  {
    private val dataList = listOf("Button 1", "Button 2", "Button 3", "Button 4")
    private lateinit var binding: ActivityNewDeviceSetTypeBinding
    private lateinit var lastPageButton: ImageButton
    private lateinit var nextButton: Button
    private lateinit var cancelButton: Button
    private lateinit var deviceName: EditText
    private lateinit var buttonContainer: FlexboxLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceSetTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lastPageButton = binding.lastPage
        nextButton = binding.nextButton
        cancelButton = binding.cancelButton
        deviceName = binding.deviceName
        buttonContainer = binding.buttonContainer

        for (buttonText in dataList) {
            val button = Button(this)
            button.text = buttonText
            button.setBackgroundResource(R.drawable.tag_button)
            button.textSize = resources.getDimension(R.dimen.button_text_size)

            //設定按鈕樣式
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                resources.getDimensionPixelSize(R.dimen.fixed_height)
            )
            params.setMargins(0, 15, 10, 0)

            button.layoutParams = params

            //按鈕事件監聽
            button.setOnClickListener {
                button.isSelected = !button.isSelected
                onButtonClick(buttonText)
            }

            buttonContainer.addView(button)
        }

        //返回按鈕
        nextButton.setOnClickListener {
            val intentNewDeviceSetValutActivity = Intent(this@NewDeviceSetTypeActivity, NewDeviceSetValutActivity::class.java)
            startActivity(intentNewDeviceSetValutActivity)
        }

        //取消按鈕
        cancelButton.setOnClickListener {
            val intentMainActivity = Intent(this@NewDeviceSetTypeActivity, MainActivity::class.java)
            startActivity(intentMainActivity)
        }

        //下一步按鈕
        lastPageButton.setOnClickListener{
            val intentMainActivity = Intent(this@NewDeviceSetTypeActivity, MainActivity::class.java)
            startActivity(intentMainActivity)
        }
    }

    private fun onButtonClick(buttonText: String) {
        // 在這裡處理按鈕被點擊時的邏輯
        // 例如，顯示一個 Toast 訊息
        Toast.makeText(this@NewDeviceSetTypeActivity, "$buttonText Clicked!", Toast.LENGTH_SHORT).show()
    }
}