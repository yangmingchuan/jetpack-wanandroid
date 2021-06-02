package com.ymc.library_base.permission

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by ymc on 6/1/21.
 *
 * @Description 权限配置类
 */
class PermissionConfig() : Parcelable{

    //必须要所有的权限都通过才能通过
    private var forceAllPermissionsGranted = false

    //设置用户点击不再提示之后的弹窗文案
    private var forceDeniedPermissionTips: String? = null

    private val check: PermissionUtil? = null

    constructor(parcel: Parcel) : this() {
    }

    fun getForceDeniedPermissionTips(): String? {
        return forceDeniedPermissionTips
    }

    fun setForceDeniedPermissionTips(forceDeniedPermissionTips: String): PermissionConfig {
        this.forceDeniedPermissionTips = forceDeniedPermissionTips
        return this
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PermissionConfig) return false

        if (forceDeniedPermissionTips != other.forceDeniedPermissionTips) return false

        return true
    }

    override fun hashCode(): Int {
        var result = forceAllPermissionsGranted.hashCode()
        result = 31 * result + (forceDeniedPermissionTips?.hashCode() ?: 0)
        return result
    }

    companion object CREATOR : Parcelable.Creator<PermissionConfig> {
        override fun createFromParcel(parcel: Parcel): PermissionConfig {
            return PermissionConfig(parcel)
        }

        override fun newArray(size: Int): Array<PermissionConfig?> {
            return arrayOfNulls(size)
        }
    }


}