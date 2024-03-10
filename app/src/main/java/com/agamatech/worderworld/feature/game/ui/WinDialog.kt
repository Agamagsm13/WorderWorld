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
import com.agamatech.worderworld.databinding.DialogWinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WinDialog: DialogFragment() {

    private var binding: DialogWinBinding? = null

    override fun getTheme(): Int = R.style.NoBackgroundDialog

    companion object {
        fun newInstance(): WinDialog {
            return WinDialog().apply {
                arguments = bundleOf()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = DialogWinBinding.inflate(inflater, container, false).also { binding = it }
        setupView(b)
        return b.root
    }

    private fun setupView(b: DialogWinBinding) {
        b.closeButton.setOnClickListener { dismissAllowingStateLoss() }
        b.okButton.setOnClickListener { dismissAllowingStateLoss() }
        runAnim()
    }

    private fun runAnim() {
        binding?.one?.let {
            val anim = AnimatorSet().apply {
                duration = 3000
            }
            anim.playTogether(
                ObjectAnimator.ofFloat(it, "rotation", 0f, -20f, 0f, 20f, 0f, -20f, 0f, 20f, 0f, -20f, 0f, 20f, 0f),
            )
            anim.doOnEnd {
                it.start()
            }
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