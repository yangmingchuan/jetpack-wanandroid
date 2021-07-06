package com.ymc.library_base.base

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ymc.library_base.interfaces.IView
import com.ymc.library_base.support.StatusBar
import com.ymc.library_base.widget.LoadingDialog

/**
 * Author : ymc
 * Date   : 2020/8/31  16:09
 * Class  : BaseActivity
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), IView {

    private lateinit var mLoadingDialog: LoadingDialog
    protected val TAG = javaClass.canonicalName
    var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        StatusBar().fitSystemBar(this)
        setContentView(getLayoutId())
        initView(savedInstanceState)
        initViewData(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mLoadingDialog = LoadingDialog(this, false)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    override fun initViewData(savedInstanceState: Bundle?) {
    }

    /**
     * 禁止交互
     */
    protected fun disableInteraction() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    /**
     * 允许交互
     */
    protected fun enableInteraction() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(this, false)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }

    /**
     * 设置toolbar名称
     */
    protected fun setToolbarTitle(view: TextView, title: String) {
        view.text = title
    }

    /**
     * 设置toolbar返回按键图片
     */
    protected fun setToolbarBackIcon(view: ImageView, id: Int) {
        view.setBackgroundResource(id)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        mBinding?.unbind()
        super<AppCompatActivity>.onDestroy()
    }

}