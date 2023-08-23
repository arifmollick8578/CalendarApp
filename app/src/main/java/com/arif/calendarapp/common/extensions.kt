package com.arif.calendarapp.common

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat

/** Extensions class to fetch drawable from resId. */
fun View.getDrawable(drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(context, drawableId)
}

/** Extensions class to fetch color from resId. */
fun View.getColor(colorId: Int): Int {
    return ContextCompat.getColor(context, colorId)
}
