package net.laggedhero.myreceipts.ui.main

import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.myreceipts.databinding.ReceiptListItemBinding

class ReceiptListItemViewHolder(private val binding: ReceiptListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(receipt: ReceiptListItemViewModel) {
        binding.viewModel = receipt
        binding.executePendingBindings()
    }
}
