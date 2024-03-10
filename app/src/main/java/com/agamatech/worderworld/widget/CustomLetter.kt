package com.agamatech.worderworld.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.WidgetLetterViewBinding
import com.agamatech.worderworld.feature.game.LetterState

class CustomLetter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: WidgetLetterViewBinding =
        WidgetLetterViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var letterState: Int?

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.LetterViewStyle, defStyleAttr, 0)
        letterState = array.getInt(R.styleable.LetterViewStyle_letter_state, 0)
        letterState?.let {
            changeLetterState(LetterState.parse(it)?: LetterState.INPUT)
        }
        array.recycle()
    }

    fun changeLetterState(state: LetterState) {
        when (state) {
            LetterState.INPUT -> {
                binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.blue)
            }
            LetterState.RESULT_WRONG_PLACE -> {
                binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.orange)
            }
            LetterState.RESULT_FALSE -> {
                binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.grey_light)
            }
            LetterState.RESULT_OK -> {
                binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.lime)
            }
        }
    }
    fun setText(value: String) {
        binding.keyText.text = value
    }

    fun getText(): String = binding.keyText.text.toString()


}