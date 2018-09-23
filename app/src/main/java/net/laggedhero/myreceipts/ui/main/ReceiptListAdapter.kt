package net.laggedhero.myreceipts.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.myreceipts.databinding.ReceiptListItemBinding

class ReceiptListAdapter : RecyclerView.Adapter<ReceiptListItemViewHolder>() {

    private val adapterData = ArrayList<ReceiptListItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptListItemViewHolder {
        val binding = ReceiptListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReceiptListItemViewHolder(binding)
    }

    override fun getItemCount() = adapterData.size

    override fun onBindViewHolder(holder: ReceiptListItemViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    fun setData(data: List<ReceiptListItemViewModel>?) {
        adapterData.clear()
        if (data == null) return
        adapterData.addAll(data)
        notifyDataSetChanged()
    }
}
