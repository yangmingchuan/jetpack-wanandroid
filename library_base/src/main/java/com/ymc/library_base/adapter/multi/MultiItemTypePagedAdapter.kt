package com.ymc.library_base.adapter.multi

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ymc.library_base.adapter.base.BaseViewHolder
import com.ymc.library_base.adapter.base.ItemDelegateManager
import com.ymc.library_base.adapter.interfaces.ItemDelegate
import com.ymc.library_base.adapter.interfaces.OnItemClickListener
import java.util.*

/**
 * Created by ymc on 2020/9/14.
 * @Description paging adapter
 */
class MultiItemTypePagedAdapter<T>(@NonNull diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, BaseViewHolder>(diffCallback) {

    private var mData: List<T> = ArrayList()
    private val mItemDelegateManager: ItemDelegateManager<T> = ItemDelegateManager()
    private var mOnItemClickListener: OnItemClickListener? = null

    private val mHeaders = SparseArray<View>()
    private val mFooters = SparseArray<View>()

    private var BASE_ITEM_TYPE_HEADER = 100000
    private var BASE_ITEM_TYPE_FOOTER = 200000

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (mHeaders.indexOfKey(viewType) >= 0) {
            val view = mHeaders[viewType]
            return BaseViewHolder(view)
        }

        if (mFooters.indexOfKey(viewType) >= 0) {
            val view = mFooters[viewType]
            return BaseViewHolder(view)
        }

        val itemViewDelegate: ItemDelegate<T>? = mItemDelegateManager.getItemViewDelegate(viewType)
        val layoutId: Int? = itemViewDelegate?.layoutId()
        val holder: BaseViewHolder =
            BaseViewHolder.createViewHolder(parent.context, parent, layoutId)
        setListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        var indexPos = position
        //????????????????????????itemView??? position ????????????????????????headerView?????????
        indexPos -= mHeaders.size()
        getItem(indexPos)?.let {
            mItemDelegateManager.convert(
                holder, it, holder.adapterPosition
            )
        }
    }

    private var mSpanSizeLookup: SpanSizeLookup? = null

    interface SpanSizeLookup {
        fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int
    }

    fun setSpanSizeLookup(spanSizeLookup: SpanSizeLookup?) {
        mSpanSizeLookup = spanSizeLookup
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val defSpanSizeLookup = manager.spanSizeLookup
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mSpanSizeLookup == null) {
                        if (isFixedViewType(position)) manager.spanCount else defSpanSizeLookup.getSpanSize(
                            position
                        )
                    } else {
                        if (isFixedViewType(position)) manager.spanCount else mSpanSizeLookup!!.getSpanSize(
                            manager, position - mHeaders.size()
                        )
                    }
                }
            }
        }
    }

    /**
     * ?????? Item Type
     */
    override fun getItemViewType(position: Int): Int {
        if (position < mHeaders.size()) {
            //?????????position?????????headerview???  viewType
            return mHeaders.keyAt(position)
        }

        if (position >= itemCount - mFooters.size()) {
            //footer????????????????????????????????????position????????????
            return mFooters.keyAt(position - (itemCount - mFooters.size()))
        }
        val currentPos = position - mHeaders.size()
        return if (mItemDelegateManager.itemViewDelegateCount() <=0) {
            super.getItemViewType(currentPos)
        }else if (getItem(currentPos) != null) {
            val s = getItem(currentPos)
            s!!.let {
                mItemDelegateManager.getItemViewType(s, position)
            }
        } else -1
    }


    private fun isFooterPosition(position: Int): Boolean {
        return position >= itemCount - mFooters.size()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < mHeaders.size()
    }

    /**
     * ????????????
     */
    private fun setListener(holder: BaseViewHolder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { v ->
                var position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                position -= mHeaders.size()
                mOnItemClickListener!!.onItemClick(v, holder, position)
            }
            holder.itemView.setOnLongClickListener { v ->
                var position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnLongClickListener false
                }
                position -= mHeaders.size()
                mOnItemClickListener!!.onItemLongClick(v, holder, position)
            }
        }
    }

    /**
     * ?????????
     */
    fun setDataItems(data: List<T>) {
        val oldData: PagedList<T>? = currentList
        val dataSource = MutablePageKeyedDataSource<T>()
        dataSource.data = data
        val pagedList: PagedList<T>? = dataSource.buildNewPagedList(oldData?.config)
        submitList(pagedList)
    }

    /**
     * ?????? Item ??????
     */
    fun addItemDelegate(itemViewDelegate: ItemDelegate<T>?): MultiItemTypePagedAdapter<T>? {
        mItemDelegateManager.addDelegate(itemViewDelegate)
        return this
    }

    fun addItemDelegate(viewType: Int, itemViewDelegate: ItemDelegate<T>?): MultiItemTypePagedAdapter<T>? {
        mItemDelegateManager.addDelegate(viewType, itemViewDelegate)
        return this
    }

    /**
     * ?????? HeaderView
     */
    fun addHeaderView(view: View?) {
        //?????????View???????????????????????????mHeaders????????????
        if (mHeaders.indexOfValue(view) < 0) {
            mHeaders.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyDataSetChanged()
        }
    }

    /**
     * ?????? FooterView
     */
    fun addFooterView(view: View?) {
        //?????????View???????????????????????????mFooters????????????
        if (mFooters.indexOfValue(view) < 0) {
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyDataSetChanged()
        }
    }

    /**
     * ?????? HeaderView
     */
    fun removeHeaderView(view: View?) {
        val index = mHeaders.indexOfValue(view)
        if (index < 0) {
            return
        }
        mHeaders.removeAt(index)
        notifyDataSetChanged()
    }

    /**
     * ?????? FooterView
     */
    fun removeFooterView(view: View?) {
        val index = mFooters.indexOfValue(view)
        if (index < 0) {
            return
        }
        mFooters.removeAt(index)
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener?) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    private fun isFixedViewType(position: Int): Boolean {
        return position < mHeaders.size() || position >= itemCount - mFooters.size()
    }

}