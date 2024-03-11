package com.agamatech.worderworld.feature.game.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.DialogLoseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoseDialog: DialogFragment() {

    private var binding: DialogLoseBinding? = null

    override fun getTheme(): Int = R.style.NoBackgroundDialog

    companion object {
        fun newInstance(word: String): LoseDialog {
            return LoseDialog().apply {
                arguments = bundleOf("word" to word)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = DialogLoseBinding.inflate(inflater, container, false).also { binding = it }
        setupView(b)
        return b.root
    }

    private fun setupView(b: DialogLoseBinding) {
        b.closeButton.setOnClickListener { dismissAllowingStateLoss() }
        b.okButton.setOnClickListener { dismissAllowingStateLoss() }
        b.show.setOnClickListener {
            runShowAnim(b)
            b.show.setOnClickListener {  }
        }
        b.correctWord.text = arguments?.getString("word", "")
        runAnim(b)
    }

    private fun runAnim(b: DialogLoseBinding) {
        b.apply {
            val anim = AnimatorSet().apply {
                duration = 3000
            }
            anim.playTogether(
                ObjectAnimator.ofFloat(show, "rotation", 0f, -20f, 0f, 20f, 0f, -20f, 0f, 20f, 0f, -20f, 0f, 20f, 0f),
            )
            anim.doOnEnd {
                it.start()
            }
            anim.start()
        }
    }

    private fun runShowAnim(b: DialogLoseBinding) {
        b.apply {
            val anim = AnimatorSet().apply {
                duration = 2000
            }
            anim.playTogether(
                ObjectAnimator.ofFloat(show, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(correctWord, "alpha", 0f, 1f),
            )
            anim.start()
        }
    }

    override fun onResume() {
        super.onResume()
        val window: Window? = dialog?.window
        val size = Point()
        val display: Display? = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        super.onResume()
    }
}