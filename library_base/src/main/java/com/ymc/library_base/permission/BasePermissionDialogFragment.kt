package com.ymc.library_base.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Created by ymc on 6/7/21.
 * @Description DialogFragment 基类
 */
abstract class BasePermissionDialogFragment : DialogFragment(){

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null && layoutRes != 0) {
            return inflater.inflate(layoutRes, container, false)
        }
        return rootView
    }

    open fun showDialog(manager: FragmentManager?) {
        if (!isAdded && !isVisible && !isRemoving) {
            show(manager!!, tag)
        }
    }

    override fun dismiss() {
        if (!isAdded) return
        super.dismissAllowingStateLoss()
    }

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