package com.ymc.jetpack_wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.ymc.jetpack_wanandroid.databinding.ActivityMainBinding
import com.ymc.library_base.arouter.ARouterConstant
import com.ymc.library_base.base.BaseActivity

@Route(path = ARouterConstant.WAN_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}