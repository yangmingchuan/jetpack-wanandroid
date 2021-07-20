package com.ymc.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ymc.home.ui.DailyQuestionFragment

/**
 * 首页Fragment adapter
 */

class HomePageAdapter(fragment: Fragment)  : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DailyQuestionFragment()
            1 -> DailyQuestionFragment()
            2 -> DailyQuestionFragment()
        }
    }
}