package com.example.worderworld.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.WidgetKeyViewBinding
import com.example.worderworld.event.LetterPressEvent
import org.greenrobot.eventbus.EventBus

class CustomKey @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: WidgetKeyViewBinding =
        WidgetKeyViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var keyText: String?
    private var keyState: Int?

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.KeyViewStyle, defStyleAttr, 0)
        keyText = array.getText(R.styleable.KeyViewStyle_key_text).toString()
        keyState = array.getInt(R.styleable.KeyViewStyle_key_state, 0)
        setUI()
        array.recycle()
    }

    private fun setUI() {
        binding.keyText.text = keyText
        binding.keyBtn.setOnClickListener {
            EventBus.getDefault().post(LetterPressEvent(keyText?:""))
        }
    }

    fun changeStateToUnused() {
        binding.apply {
            keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.grey_dark)
        }
    }

    fun changeStateToUsed() {
        binding.apply {
            keyCard.setCardBackgroundColor(ContextCompat.getColor(binding.keyCard.context, R.color.lime_5))
        }
    }

    fun resetState() {
        binding.apply {
            keyCard.setCardBackgroundColor(ContextCompat.getColor(binding.keyCard.context, R.color.grey_dark))
            keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.lime)
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

}