package com.ymc.library_base.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Created by ymc on 6/11/21.
 * @Description permission ext
 */
private val permissionsTaskListener = object : OnPermissionsTaskListener() {
    override fun onPermissionsTask() {
    }
}

/**
 * activity
 *
 *
 */
//使用案例
//private val cameraTask = arrayOf("android.permission.CAMERA")
//
//view.setOnClickListener {
//    permissionCheck(cameraTask, object : OnPermissionsTaskListener() {
//        override fun onPermissionsTask() {
//            toast("权限申请通过")
//        }
//
//        override fun onDenied() {
//            toast("权限被拒绝")
//        }
//
//        override fun onNeverAskAgain() {
//            toast("权限被拒绝,并勾选不再提示")
//        }
//    })
//}

fun FragmentActivity.permissionCheck(
    permissions: Array<String>,
    listener: OnPermissionsTaskListener = permissionsTaskListener
) {
    val dialog = PermissionsDialog()
    dialog.setOnPermissionsTaskListener(listener)
    dialog.setNeedsPermission(permissions)
    dialog.showDialog(supportFragmentManager)
}

fun Fragment.permissionCheck(
    permissions: Array<String>,
    listener: OnPermissionsTaskListener = permissionsTaskListener
) {
    val dialog = PermissionsDialog()
    dialog.setOnPermissionsTaskListener(listener)
    dialog.setNeedsPermission(permissions)
    dialog.showDialog(childFragmentManager)
}