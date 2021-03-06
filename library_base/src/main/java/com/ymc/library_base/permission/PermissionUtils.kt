package com.ymc.library_base.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.collection.SimpleArrayMap
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

/**
 * Created by ymc on 6/8/21.
 * @Description 权限工具类
 */
object PermissionUtils {

    private var MIN_SDK_PERMISSIONS: SimpleArrayMap<String, Int> = SimpleArrayMap(13)

    init {
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14)
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16)
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16)
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16)
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20)
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23)
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23)
        MIN_SDK_PERMISSIONS.put("android.permission.READ_PHONE_NUMBERS", 26)
        MIN_SDK_PERMISSIONS.put("android.permission.ANSWER_PHONE_CALLS", 26)
        MIN_SDK_PERMISSIONS.put("android.permission.ACCEPT_HANDOVER", 28)
        MIN_SDK_PERMISSIONS.put("android.permission.ACTIVITY_RECOGNITION", 29)
        MIN_SDK_PERMISSIONS.put("android.permission.ACCESS_MEDIA_LOCATION", 29)
        MIN_SDK_PERMISSIONS.put("android.permission.ACCESS_BACKGROUND_LOCATION", 29)
    }

    /**
     * Checks all given permissions have been granted.
     *
     * @param grantResults results
     * @return returns true if all permissions have been granted.
     */
    fun verifyPermissions(vararg grantResults: Int): Boolean {
        if (grantResults.isEmpty()) {
            return false
        }
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * Returns true if the permission exists in this SDK version
     *
     * @param permission permission
     * @return returns true if the permission exists in this SDK version
     */
    private fun permissionExists(permission: String): Boolean {
        // Check if the permission could potentially be missing on this device
        val minVersion = MIN_SDK_PERMISSIONS[permission]
        // If null was returned from the above call, there is no need for a device API level check for the permission;
        // otherwise, we check if its minimum API level requirement is met
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion
    }

    /**
     * Returns true if the Activity or Fragment has access to all given permissions.
     *
     * @param context     context
     * @param permissions permission list
     * @return returns true if the Activity or Fragment has access to all given permissions.
     */
    fun hasSelfPermissions(
        context: Context,
        permissions: Array<String>
    ): Boolean {
        for (permission in permissions) {
            if (permissionExists(permission) && !hasSelfPermission(
                    context,
                    permission
                )
            ) {
                return false
            }
        }
        return true
    }

    /**
     * Determine context has access to the given permission.
     *
     *
     * This is a workaround for RuntimeException of Parcel#readException.
     * For more detail, check this issue https://github.com/hotchemi/PermissionsDispatcher/issues/107
     *
     * @param context    context
     * @param permission permission
     * @return true if context has access to the given permission, false otherwise.
     * @see .hasSelfPermissions
     */
    private fun hasSelfPermission(
        context: Context,
        permission: String
    ): Boolean {
        return try {
            PermissionChecker.checkSelfPermission(
                context,
                permission
            ) == PermissionChecker.PERMISSION_GRANTED
        } catch (t: RuntimeException) {
            false
        }
    }

    /**
     * Checks given permissions are needed to show rationale.
     *
     * @param activity    activity
     * @param permissions permission list
     * @return returns true if one of the permission is needed to show rationale.
     */
    fun shouldShowRequestPermissionRationale(
        activity: Activity?,
        permissions: Array<String>
    ): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
                return true
            }
        }
        return false
    }

    /**
     * Checks given permissions are needed to show rationale.
     *
     * @param fragment    fragment
     * @param permissions permission list
     * @return returns true if one of the permission is needed to show rationale.
     */
    fun shouldShowRequestPermissionRationale(
        fragment: Fragment,
        permissions: Array<String>
    ): Boolean {
        for (permission in permissions) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                return true
            }
        }
        return false
    }

}