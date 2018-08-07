package com.lordgama.carpicturesutilities

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Daniel on 18/12/2017.
 */
class DefaultItemDecoration(var context: Context) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.left = 8.fromDps(context)
        outRect?.right = 8.fromDps(context)
        outRect?.top = if (parent?.getChildAdapterPosition(view) == 0) 8.fromDps(context) else 0.fromDps(context)
        outRect?.bottom = 8.fromDps(context)
    }
}