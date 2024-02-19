package com.example.worderworld.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.WidgetKeyboardViewBinding

class CustomKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

        private var binding: WidgetKeyboardViewBinding =
        WidgetKeyboardViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var backgroundColor: Int
    private val defaultBgColor by lazy { ContextCompat.getColor(context, R.color.transparent) }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.KeyboardViewStyle, defStyleAttr, 0)

        backgroundColor = array.getColor(R.styleable.KeyboardViewStyle_keyboard_backgroundColor, defaultBgColor)
        setUI()
        array.recycle()
    }

    private fun setUI() {
        //if (backgroundColor != defaultBgColor || corners > 0) {
        //    setBackgroundView(binding.containerSpiciness, backgroundColor, corners)
        //}
    }


    private fun setBackgroundView(layout: View, backgroundColor: Int, corners: Int) {
        //val shape = GradientDrawable()
        //with(shape) {
        //    this.shape = GradientDrawable.RECTANGLE
        //    cornerRadius = corners.dpToPx()
        //    setColor(backgroundColor)
        //}
        //layout.background = shape
    }

}