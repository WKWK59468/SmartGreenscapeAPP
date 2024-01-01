package com.example.smartgreenscape.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import com.android.volley.Request

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartgreenscape.model.Device
import com.example.smartgreenscape.model.RequestMacData
import com.example.smartgreenscape.model.Tag
import com.google.gson.Gson
import org.json.JSONArray
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JobApiService : JobService() {
    private val CHANNEL_ID:String="channel01"

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("servicedfsdfsdf","onStartJob:")

        getDefaultPlantClass { result ->
            if (result.isNotEmpty()) {
                val jsonArray = JSONArray(result)
                for(i in 0 until jsonArray.length()) {

                    val item = jsonArray.getJSONObject(i)
                    val messagesArray=item.getJSONArray("messagges")
                    val messagesList = mutableListOf<String>()
                    for (i in 0 until messagesArray.length()) {
                        val message = messagesArray.getString(i)
                        messagesList.add(message)
                    }
                    val result = messagesList.joinToString(", ")
                    showNotification(result)

                }

            }
            jobFinished(p0, false)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("servicedfsdfsdf","onStopJob:")

        return false
    }
    private fun getDefaultPlantClass(callback: (String) -> Unit) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.213.10:8000/api/plant/status"
        Log.d("servicedfsdfsdf","url:"+url)

        val macAddresses = listOf("A0:B7:65:DE:0C:08")
        val requestBody = RequestMacData(mac_addresses = macAddresses)
        val gson = Gson()
        val requestBodyJson = gson.toJson(requestBody)
        val stringRequest = object : StringRequest(
            Method.POST, url, { response ->
                Log.d("servicedfsdfsdf","新sdfsdf增資料成功:"+response)

                Toast.makeText(this, "新sdfsdf增資料成功!", Toast.LENGTH_LONG).show();
                callback(response)
            },
            { _ ->
                Toast.makeText(this, "新增sdfsdf資料失敗!", Toast.LENGTH_LONG).show();
            }){
            override fun getBody(): ByteArray {
                return requestBodyJson.toByteArray(Charset.defaultCharset())
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue.add(stringRequest)
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name="MyNotification"
            val importance= NotificationManager.IMPORTANCE_HIGH
            val notificationChannel= NotificationChannel(CHANNEL_ID,name,importance)
            val description="My notification channel description"
            notificationChannel.description=description
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
    @SuppressLint("MissingPermission")
    private fun showNotification(text:String){
        Log.d("ffsdfsdf","sdfsdfsdf:"+text)
        createNotificationChannel()
        val vibrationEffect:LongArray= longArrayOf(3000,3000,3000,3000)
        val date= Date()
        val notificationId= SimpleDateFormat("ddHHmmss", Locale.CHINESE).format(date).toInt()
        val notificationBuilder= NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("您的植物生長環境數據異常")
            .setContentText(text)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(vibrationEffect)
        val notificationManagerCompat= NotificationManagerCompat.from(this)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
        notificationManagerCompat.notify(notificationId,notificationBuilder.build())
    }
}