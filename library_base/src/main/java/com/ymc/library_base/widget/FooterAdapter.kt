package com.ymc.library_base.widget

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ymc.library_base.databinding.BasePagingFooterItemBinding

private const val TAG = "FooterAdapter"

/**
 * 底部 adapter
 */

class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(binding: BasePagingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pagingBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        Log.d(TAG, "onBindViewHolder: $loadState ")

        holder.pagingBinding.run {
            progressBar.isVisible = loadState is LoadState.Loading
            btRetry.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {

        val binding =
            BasePagingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //加载失败时，点击重新请求
        binding.btRetry.setOnClickListener {
            retry()
        }
        return FooterViewHolder(binding)

    }
}