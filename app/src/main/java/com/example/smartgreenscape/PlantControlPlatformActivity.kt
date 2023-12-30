package com.example.smartgreenscape

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.smartgreenscape.adapter.FragmentPageAdapter
import com.example.smartgreenscape.databinding.ActivityPlantControlPlatformBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab

class PlantControlPlatformActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityPlantControlPlatformBinding
    private lateinit var lastPageButton: ImageButton
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager2:ViewPager2
    private lateinit var adapter:FragmentPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantControlPlatformBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout=findViewById(R.id.tabLayout)
        viewPager2=findViewById(R.id.viewPager)
        lastPageButton = binding.lastPage

        lastPageButton.setOnClickListener{
//            val intentMainActivity = Intent(this@PlantControlPlatformActivity, MainActivity::class.java)
//            startActivity(intentMainActivity)
            finish()
        }
        adapter= FragmentPageAdapter(supportFragmentManager,lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("基本資訊"))
        tabLayout.addTab(tabLayout.newTab().setText("即時數據"))
        tabLayout.addTab(tabLayout.newTab().setText("歷史數據"))

        viewPager2.adapter=adapter
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: Tab?) {
                if(tab!=null){
                    viewPager2.currentItem=tab.position
                }
            }

            override fun onTabUnselected(tab: Tab?) {
            }

            override fun onTabReselected(tab: Tab?) {
            }

        })
        viewPager2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}