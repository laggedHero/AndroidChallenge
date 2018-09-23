package net.laggedhero.myreceipts.ui.binding

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter

object ViewBindings {

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun visibleIf(view: View, isVisible: Boolean?) {
        view.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("showError")
    fun showError(view: View, error: String?) {
        if (error.isNullOrBlank()) return
        Toast.makeText(view.context, error, Toast.LENGTH_LONG).show()
    }
}
