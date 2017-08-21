package org.tlauncher.tlauncherpe.mc.presentationlayer.details

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceItemDecorator(val itemSpace : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect : Rect?, view : View?, parent : RecyclerView?, state : RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent?.getChildAdapterPosition(view) != 0) {
            outRect?.left = itemSpace
        }
    }
}