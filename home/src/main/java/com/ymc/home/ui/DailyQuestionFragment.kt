package com.ymc.home.ui

import android.os.Bundle
import com.ymc.home.R
import com.ymc.home.databinding.HomeFragmentDailyQuestionBinding
import com.ymc.library_base.base.BaseFragment

/**
 * 问答
 */

class DailyQuestionFragment : BaseFragment<HomeFragmentDailyQuestionBinding>(){


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment_daily_question
    }
}