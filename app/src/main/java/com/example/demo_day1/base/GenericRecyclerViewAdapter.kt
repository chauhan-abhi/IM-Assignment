package com.example.demo_day1.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericRecyclerViewAdapter<T, VH : BaseRecyclerViewHolder<T>>(
    protected var itemLayout: Int,
    protected var mList: List<T>
) :
    RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        itemType: Int
    ): VH {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(itemLayout, parent, false)
        return BaseRecyclerViewHolder<T>(itemView) as VH
    }

    override fun onBindViewHolder(
        viewHolder: VH,
        position: Int
    ) {
        viewHolder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: List<T>) {
        this.mList = mList
        notifyDataSetChanged()
    }
}