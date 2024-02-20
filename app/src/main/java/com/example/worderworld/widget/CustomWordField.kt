package com.example.worderworld.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.WidgetWordViewBinding
import com.example.worderworld.feature.game.LetterState

class CustomWordField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: WidgetWordViewBinding =
        WidgetWordViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var wordLength: Int?

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.WordViewStyle, defStyleAttr, 0)
        wordLength = array.getInt(R.styleable.WordViewStyle_word_length, 0)
        setUI()
        array.recycle()
    }

    private fun setUI() {
        for (i in 0..(wordLength?:0)) {
            val letter = CustomLetter(context)
            letter.changeLetterState(LetterState.INPUT)
            letter.setText(" ")
            binding.wordLayout.addView(CustomLetter(context = context))
        }
        binding.wordLayout.invalidate()
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