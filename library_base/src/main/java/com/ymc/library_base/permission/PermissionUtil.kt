package com.ymc.library_base.permission

import android.Manifest
import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by ymc on 6/1/21.
 * @Description 权限工具类
 */

class PermissionUtil(mContext: Activity) {

    val VOICE_REQUIRE_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val CAMERA_REQUIRE_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val FILE_REQUIRE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val CALL_PERMISSIONS =
        arrayOf(Manifest.permission.CALL_PHONE)
    val LOCATION_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val READ_PHONE_STATE_PERMISSIONS =
        arrayOf(Manifest.permission.READ_PHONE_STATE)
    val CAMERA_PERMISSIONS =
        arrayOf(Manifest.permission.CAMERA)

    //宿主Activity
    private var mContext: WeakReference<Activity>? = null

    //回调监听
    private val listener: PermissionListener? = null

    //存储所有的权限列表
    private val permissions: List<String> = ArrayList()

    private val checkConfig: PermissionConfig? = null

    init {
        this.mContext = WeakReference(mContext)
    }

    fun with(context: Activity): PermissionUtil {
        return PermissionUtil(context)
    }

}