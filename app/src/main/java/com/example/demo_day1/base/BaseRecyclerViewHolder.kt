package com.example.demo_day1.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindData(data: T) {

    }
}