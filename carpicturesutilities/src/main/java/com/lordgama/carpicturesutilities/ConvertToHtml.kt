package com.lordgama.carpicturesutilities

import android.text.Html
import android.text.Spanned

fun String.setTextHTML(): Spanned
{
    val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
    return result
}