package com.sunagalab.smallutils

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.color
import androidx.core.text.inSpans

object StringUtils {
}

fun SpannableStringBuilder.colorRes(context: Context, resId: Int, builderAction: SpannableStringBuilder.() -> Unit): SpannableStringBuilder {
    val colorCode = ResourcesCompat.getColor(context.resources, resId, null)
    color(colorCode, builderAction)
    return this
}

fun SpannableStringBuilder.appendDrawableRes(context: Context, resId: Int): SpannableStringBuilder {
    ResourcesCompat.getDrawable(
        context.resources,
        resId,
        null
    )?.let { drawable ->
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        inSpans(ImageSpan(drawable)) { append("IMG") }
    }
    return this
}

fun SpannableStringBuilder.appendStringRes(context: Context, resId: Int): SpannableStringBuilder {
    append(context.getString(resId))
    return this
}



