package com.example.worderworld.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.WidgetKeyboardViewBinding
import com.example.worderworld.event.CheckWordPressEvent
import com.example.worderworld.event.DeleteLetterPressEvent
import com.example.worderworld.event.LetterPressEvent
import org.greenrobot.eventbus.EventBus

class CustomKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

        private var binding: WidgetKeyboardViewBinding =
        WidgetKeyboardViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var backgroundColor: Int
    private var letters = mutableMapOf<String, CustomKey>()
    private val defaultBgColor by lazy { ContextCompat.getColor(context, R.color.transparent) }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.KeyboardViewStyle, defStyleAttr, 0)

        backgroundColor = array.getColor(R.styleable.KeyboardViewStyle_keyboard_backgroundColor, defaultBgColor)
        setUI()
        array.recycle()
    }

    private fun setUI() {
        binding.apply {
            deleteLayout.setOnClickListener {
                EventBus.getDefault().post(DeleteLetterPressEvent())
            }
            checkLayout.setOnClickListener {
                EventBus.getDefault().post(CheckWordPressEvent())
            }
        }
        putAlphabet()
        //if (backgroundColor != defaultBgColor || corners > 0) {
        //    setBackgroundView(binding.containerSpiciness, backgroundColor, corners)
        //}
    }

    fun changeKeysState(badLetters: List<String>) {
        badLetters.forEach {
            letters[it]?.changeStateToUnused()
        }
    }

    fun changeGoodKeysState(badLetters: List<String>) {
        badLetters.forEach {
            letters[it]?.changeStateToUsed()
        }
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

    private fun putAlphabet() {
        binding.apply {
            letters["Q"] = q
            letters["W"] = w
            letters["E"] = e
            letters["R"] = r
            letters["T"] = t
            letters["Y"] = y
            letters["U"] = u
            letters["I"] = i
            letters["O"] = o
            letters["P"] = p
            letters["A"] = a
            letters["S"] = s
            letters["D"] = d
            letters["F"] = f
            letters["G"] = g
            letters["H"] = h
            letters["J"] = j
            letters["K"] = k
            letters["L"] = l
            letters["Z"] = z
            letters["X"] = x
            letters["C"] = c
            letters["V"] = v
            letters["B"] = b
            letters["N"] = n
            letters["M"] = m
        }
    }

}