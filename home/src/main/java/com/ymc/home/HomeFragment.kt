package com.ymc.home

import android.os.Bundle
import com.ymc.home.adapter.HomePageAdapter
import com.ymc.home.databinding.HomeFragmentHomeBinding
import com.ymc.library_base.base.BaseFragment
import kotlinx.android.synthetic.main.home_fragment_home.*
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 首页 Fragment
 */

class HomeFragment : BaseFragment<HomeFragmentHomeBinding>(){

    private lateinit var homePageAdapter: HomePageAdapter

    override fun getLayoutId(): Int {
        return R.layout.home_fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        homePageAdapter = HomePageAdapter(this)
        mBinding?.vpHome?.adapter = homePageAdapter


        mBinding?.run {
            // 绑定vp 和 tabLayout
            TabLayoutMediator(homeTabLayout, vpHome) { tab, position ->
                when (position) {
                    0 -> tab.text = "每日一问"
                    1 -> tab.text = "首页"
                    2 -> tab.text = "广场"
                }
            }.attach()
            vp_home.currentItem = 1
        }
    }

}