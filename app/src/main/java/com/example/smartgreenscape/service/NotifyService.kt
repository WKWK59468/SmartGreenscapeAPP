package com.example.smartgreenscape.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.appcompat.resources.R
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class NotifyService : Service() {
    private val CHANNEL_ID:String="channel01"
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scheduleJob(this)
//        showNotification()
        return super.onStartCommand(intent, flags, startId)
    }
    private fun scheduleJob(context: Context) {
        Log.d("servicedfsdfsdf","scheduleJob")
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(context, JobApiService::class.java)

        val jobInfo = JobInfo.Builder(1, componentName)
            .setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(5000))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // 需要任何網絡條件
            .build()

        jobScheduler.schedule(jobInfo)
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name="MyNotification"
            val importance=NotificationManager.IMPORTANCE_HIGH
            val notificationChannel=NotificationChannel(CHANNEL_ID,name,importance)
            val description="My notification channel description"
            notificationChannel.description=description
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
    private fun showNotification(){
        createNotificationChannel()
        val vibrationEffect:LongArray= longArrayOf(3000,3000,3000,3000)
        val date=Date()
        val notificationId=SimpleDateFormat("ddHHmmss", Locale.CHINESE).format(date).toInt()
        val notificationBuilder=NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("您的植物生長環境數據異常")
            .setContentText("sdfsdfsdf")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(vibrationEffect)
        val notificationManagerCompat=NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(notificationId,notificationBuilder.build())
    }
}