package com.ymc.library_base.permission

/**
 * Created by ymc on 6/1/21.
 * @Description 权限回调接口
 */
interface PermissionListener {

    /**
     * 授权全部通过
     */
    fun permissionRequestSuccess()

    /**
     * 授权未通过
     * @param grantedPermissions 已通过的权限
     * @param deniedPermissions 拒绝的权限
     * @param forceDeniedPermissions 永久拒绝的权限（也就是用户点击了不再提醒的那些权限）
     */
    fun permissionRequestFail(
        grantedPermissions: Array<String>?,
        deniedPermissions: Array<String>?,
        forceDeniedPermissions: Array<String>?
    )
}