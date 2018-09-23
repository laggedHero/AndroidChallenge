package net.laggedhero.myreceipts.ui.main

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.myreceipts.R

class ReceiptItemTouchHelper(
    private val resources: Resources,
    onSwipeRight: ((Int) -> Unit)? = null
) : ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val paint = Paint().apply { color = Color.parseColor("#DB4437") }
    private val trashBin by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white_24)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            onSwipeRight?.invoke(viewHolder.adapterPosition)
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView

        val top = itemView.top.toFloat()
        val bottom = itemView.bottom.toFloat()
        val halfItemViewHeight = itemView.height / 2

        val right = itemView.right.toFloat()

        canvas.drawRect(
            right + dX,
            top,
            right,
            bottom,
            paint
        )

        canvas.drawBitmap(
            trashBin,
            right - trashBin.width - dpToPx(16f),
            top + halfItemViewHeight - trashBin.height / 2,
            paint
        )

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun dpToPx(dipValue: Float) = TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics)
})
