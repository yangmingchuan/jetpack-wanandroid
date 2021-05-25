package com.ymc.library_base

import android.content.Context
import com.ymc.library_base.utils.LogUtils
import com.ymc.library_base.utils.ToastUtils

/**
 * Author : ymc
 * Date   : 2020/9/7  17:04
 * Class  : ArchAction
 */

/**
 * toast
 *
 * @param text
 */
fun Context.toast(text: String) {
    ToastUtils.normal(text)
}

/**
 * log e
 *
 * @param text
 */
fun Context.loge(text: String) {
    LogUtils.e(text)
}

