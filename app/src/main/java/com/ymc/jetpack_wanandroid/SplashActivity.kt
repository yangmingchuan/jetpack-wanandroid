package com.ymc.jetpack_wanandroid

import android.content.Intent
import android.os.Bundle
import com.ymc.jetpack_wanandroid.databinding.AppActivitySplashBinding
import com.ymc.library_base.base.BaseActivity

/**
 * 欢迎界面
 */

class SplashActivity : BaseActivity<AppActivitySplashBinding>(){

    override fun initViewData(savedInstanceState: Bundle?) {
        super.initViewData(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId(): Int = R.layout.app_activity_splash

}