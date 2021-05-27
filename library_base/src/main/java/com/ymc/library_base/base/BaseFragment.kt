package com.ymc.library_base.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ymc.library_base.R
import com.ymc.library_base.databinding.BaseFragmentLayoutBinding
import com.ymc.library_base.interfaces.IView
import com.ymc.library_base.widget.LoadingDialog

/**
 * Created by ymc on 5/27/21.
 * @Description 基础 Fragment
 */

abstract class BaseFragment<T : ViewDataBinding> : Fragment() , IView{
    var mBinding: T? = null
    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var mBaseContainBinding: BaseFragmentLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBaseContainBinding =
            DataBindingUtil.inflate(inflater, R.layout.base_fragment_layout, container, false)
        mBaseContainBinding.baseContainer.addView(mBinding?.root)
        return mBaseContainBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(view.context, false)
        initViewData(savedInstanceState)
    }

    override fun initViewData(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super<Fragment>.onDestroy()
        mBinding?.unbind()
    }

    /**
     * show 加载中
     */
    private fun showLoading() {
        mLoadingDialog.showDialog(mContext, false)
    }

    /**
     * dismiss loading dialog
     */
    private fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }
}