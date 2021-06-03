package com.ymc.library_base.permission

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ymc on 6/1/21.
 *
 * @Description 权限配置类
 */
@SuppressLint("ParcelCreator")
@Parcelize
class PermissionConfig() : Parcelable {

    //必须要所有的权限都通过才能通过
    private var forceAllPermissionsGranted = false

    //设置用户点击不再提示之后的弹窗文案
    private var forceDeniedPermissionTips: String? = null

    private var mPermissionUtil: PermissionUtil? = null

    fun getForceDeniedPermissionTips(): String? = forceDeniedPermissionTips

    fun setForceDeniedPermissionTips(forceDeniedPermissionTips: String): PermissionConfig {
        this.forceDeniedPermissionTips = forceDeniedPermissionTips
        return this
    }

    fun setPermission(m:PermissionUtil){
        mPermissionUtil = m
    }


}