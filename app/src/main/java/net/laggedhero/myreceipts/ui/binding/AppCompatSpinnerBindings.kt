package net.laggedhero.myreceipts.ui.binding

import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter

object AppCompatSpinnerBindings {

    @JvmStatic
    @BindingAdapter("bindData")
    fun bindData(appCompatSpinner: AppCompatSpinner, data: List<String>?) {
        (appCompatSpinner.adapter as? ArrayAdapter<String>)?.let {
            it.clear()
            if (data != null) it.addAll(data)
        }
    }
}
