package com.example.worderworld.feature.game.ui

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
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.DialogInfoRulesBinding
import com.agamatech.worderworld.databinding.DialogWinBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WinDialog: DialogFragment() {

    private var binding: DialogWinBinding? = null

    override fun getTheme(): Int = R.style.NoBackgroundDialog

    companion object {
        const val BACK_TO_HOME_KEY = "BackToHomeKey"
        const val CLOSE_DIALOG_KEY = "CloseDialogKey"

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
        b.closeButton.setOnClickListener { closeWithResult() }
        b.okButton.setOnClickListener { closeWithResult() }
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

    private fun closeWithResult() {
        dismissAllowingStateLoss()
        setResultFragment()
    }

    private fun setResultFragment() {
        val bundle = bundleOf(BACK_TO_HOME_KEY to true)
        setFragmentResult(CLOSE_DIALOG_KEY, bundle)
    }

}