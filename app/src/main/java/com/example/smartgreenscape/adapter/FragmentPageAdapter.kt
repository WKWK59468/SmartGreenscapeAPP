package com.example.smartgreenscape.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smartgreenscape.CurrentDataFragment
import com.example.smartgreenscape.HistoryDataFragment
import com.example.smartgreenscape.InfoFragment


class FragmentPageAdapter(
    fragmentManager:FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0)
            InfoFragment()
        else if(position==1)
            CurrentDataFragment()
        else
            HistoryDataFragment()
    }

}