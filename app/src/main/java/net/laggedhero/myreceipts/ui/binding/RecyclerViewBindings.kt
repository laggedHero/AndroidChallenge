package net.laggedhero.myreceipts.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.myreceipts.ui.main.ReceiptListAdapter
import net.laggedhero.myreceipts.ui.main.ReceiptListItemViewModel

object RecyclerViewBindings {

    @JvmStatic
    @BindingAdapter("setData")
    fun setData(recyclerView: RecyclerView, data: List<ReceiptListItemViewModel>?) {
        (recyclerView.adapter as? ReceiptListAdapter)?.setData(data)
    }
}
