package com.example.worderworld.feature.level_game.ui

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.DialogLevelWinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelCompletedDialog: DialogFragment() {

    private var binding: DialogLevelWinBinding? = null

    override fun getTheme(): Int = R.style.NoBackgroundDialog

    companion object {
        fun newInstance(): LevelCompletedDialog {
            return LevelCompletedDialog().apply {
                arguments = bundleOf()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = DialogLevelWinBinding.inflate(inflater, container, false).also { binding = it }
        setupView(b)
        return b.root
    }

    private fun setupView(b: DialogLevelWinBinding) {
        b.closeButton.setOnClickListener { dismissAllowingStateLoss() }
        b.okButton.setOnClickListener { dismissAllowingStateLoss() }
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