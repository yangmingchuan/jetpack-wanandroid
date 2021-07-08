package com.ymc.home

import android.os.Bundle
import com.ymc.home.databinding.HomeFragmentHomeBinding
import com.ymc.library_base.base.BaseFragment

/**
 * 首页 Fragment
 */

class HomeFragment : BaseFragment<HomeFragmentHomeBinding>(){

    override fun getLayoutId(): Int {
        return R.layout.home_fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}