package com.ymc.jetpack_wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.ymc.jetpack_wanandroid.databinding.ActivityMainBinding
import com.ymc.library_base.arouter.ARouterConstant
import com.ymc.library_base.base.BaseActivity

@Route(path = ARouterConstant.WAN_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewData(savedInstanceState: Bundle?) {
        super.initViewData(savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**
     * 绑定底部nav
     */
    private fun setupBottomNavigationBar() {


    }

}