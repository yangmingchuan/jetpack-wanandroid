package com.ymc.library_base.permission

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Created by ymc on 6/7/21.
 * @Description DialogFragment 基类
 */
class BasePermissionDialogFragment : DialogFragment(){

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val mDismissed = this::class.java.superclass?.getDeclaredField("mDismissed")
            val mShownByMe = this::class.java.superclass?.getDeclaredField("mShownByMe")
            mDismissed?.isAccessible = true
            mShownByMe?.isAccessible = true
            mDismissed?.setBoolean(this, false)
            mShownByMe?.setBoolean(this, true)
        } catch (e: Exception) {
        }

        val ft = manager.beginTransaction()
        if (isAdded) {
            ft.show(this)
        } else {
            ft.remove(this).add(this, tag)
        }
        ft.commitAllowingStateLoss()
    }

}