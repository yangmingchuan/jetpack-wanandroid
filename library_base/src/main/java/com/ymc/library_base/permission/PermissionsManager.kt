package com.ymc.library_base.permission

import android.os.Build

/**
 * Created by ymc on 6/7/21.
 * @Description 权限管理工具类
 */
object PermissionsManager {

    private const val REQUEST_TASK = 101

    fun withPermissionCheck(
        target: PermissionsDialog,
        permissions: Array<String>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.hasSelfPermissions(target.requireActivity(), permissions)) {
                target.onPermissionsTask()
            } else {
                target.requestPermissions(permissions, REQUEST_TASK)
//                if (PermissionUtils.shouldShowRequestPermissionRationale(target, permissions)) {
//                    target.showNeedsReason()
//                } else {
//                    target.requestPermissions(permissions, REQUEST_TASK)
//                }
            }
        } else {
            target.onPermissionsTask()
        }
    }

    fun onRequestPermissionsResult(
        target: PermissionsDialog, requestCode: Int,
        grantResults: IntArray,
        permissions: Array<String>
    ) {
        when (requestCode) {
            REQUEST_TASK -> {
                if (PermissionUtils.verifyPermissions(*grantResults)) {
                    target.onPermissionsTask()
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(
                            target,
                            permissions
                        )
                    ) {
                        target.onNeverAskAgain()
                    } else {
                        target.onDenied()
                    }
                }
            }
            else -> {
            }
        }
    }

}