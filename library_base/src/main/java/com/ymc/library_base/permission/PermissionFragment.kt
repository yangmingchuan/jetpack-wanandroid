package com.ymc.library_base.permission

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

/**
 * Created by ymc on 6/2/21.
 * @Description 权限请求窗口
 */
class PermissionFragment : Fragment() {
    val PERMISSION_REQUEST_CODE = 1001
    val REQUEST_PERMISSION_SETTING = 1002

    private val permissions: Array<String>? = null
    private var permissionCheckListener: PermissionListener? = null
    private val mContext: WeakReference<Activity>? = null

    private val checkConfig: PermissionConfig? = null

    private val forceDeniedPermissionTips = ""

    fun setPermissionCheckListener(listener: PermissionListener): PermissionFragment {
        permissionCheckListener = listener
        return this
    }

    fun newInstance(
        permissions: Array<String?>,
        checkConfig: PermissionConfig
    ): PermissionFragment {
        return apply {
            val args = Bundle()
            PermissionFragment()
            args.putStringArray("permissions", permissions)
            args.putParcelable("config", checkConfig)
            arguments = args
        }
    }


}