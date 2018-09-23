package net.laggedhero.myreceipts.ui.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import net.laggedhero.myreceipts.R
import net.laggedhero.myreceipts.databinding.MainActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<MainActivityBinding>(this, R.layout.activity_main)
        setUpBindings(binding)
        binding.viewModel = viewModel
    }

    private fun setUpBindings(binding: MainActivityBinding) {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.usersSelector.adapter = adapter

        binding.usersSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onUserSelected(position)
            }
        }

        binding.receiptList.layoutManager = LinearLayoutManager(this)
        binding.receiptList.adapter = ReceiptListAdapter()
        binding.receiptList.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        val itemTouchHelper =
            ReceiptItemTouchHelper(resources) { position -> viewModel.deleteReceipt(position) }
        itemTouchHelper.attachToRecyclerView(binding.receiptList)
    }
}
