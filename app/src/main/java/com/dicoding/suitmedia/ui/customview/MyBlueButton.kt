package com.dicoding.suitmedia.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.suitmedia.R

class MyBlueButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs) {
    private var blueBackground: Drawable = ContextCompat.getDrawable(context, R.drawable.bg_button_blue) as Drawable

    init {
        setGravity(android.view.Gravity.CENTER)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = blueBackground
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}