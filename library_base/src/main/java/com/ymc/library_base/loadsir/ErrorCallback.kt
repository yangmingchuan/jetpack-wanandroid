package com.ymc.library_base.loadsir

import com.kingja.loadsir.callback.Callback
import com.ymc.library_base.R

/**
 * 请求失败界面
 */

class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.base_layout_error
    }
}