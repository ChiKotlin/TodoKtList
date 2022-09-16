package com.app.common.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVEmptyObserver(private val emptyView: TextView, private val recyclerView: RecyclerView) :
    RecyclerView.AdapterDataObserver() {
    init {
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        val emptyViewVisible = recyclerView.adapter?.itemCount == 0
        emptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
        recyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        checkIfEmpty()
    }
}
