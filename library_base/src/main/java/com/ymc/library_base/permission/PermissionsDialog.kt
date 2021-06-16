package com.ymc.library_base.permission

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*

/**
 * Created by ymc on 6/8/21.
 * @Description 权限管理弹框
 */
class PermissionsDialog : BasePermissionDialogFragment(){

    override val layoutRes: Int = 0
    private var listener: OnPermissionsTaskListener? = null
    private var mPermissions: Array<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mPermissions != null) {
            PermissionsManager.withPermissionCheck(this, mPermissions!!)
        } else {
            dismiss()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (mPermissions != null) {
            PermissionsManager.onRequestPermissionsResult(
                this,
                requestCode,
                grantResults,
                mPermissions!!
            )
        } else {
            dismiss()
        }
    }

    fun setOnPermissionsTaskListener(listener: OnPermissionsTaskListener) {
        this.listener = listener
    }

    fun setNeedsPermission(permissions: Array<String>) {
        this.mPermissions = permissions
    }

    internal fun onPermissionsTask() {
        listener?.onPermissionsTask()
        dismiss()
    }

    internal fun onNeverAskAgain() {
        listener?.onNeverAskAgain()
        dismiss()
    }

    internal fun onDenied() {
        listener?.onDenied()
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) return
        val window = dialog!!.window ?: return
        val params = window.attributes ?: return
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        params.dimAmount = 0.0f // 通明度
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        window.attributes = params
    }

}

interface PermissionsTaskListener {
    //    fun showNeedsReason()
    fun onPermissionsTask()
    fun onNeverAskAgain()
    fun onDenied()
}

abstract class OnPermissionsTaskListener : PermissionsTaskListener {
    //    override fun showNeedsReason() {}
    override fun onNeverAskAgain() {}
    override fun onDenied() {}
}