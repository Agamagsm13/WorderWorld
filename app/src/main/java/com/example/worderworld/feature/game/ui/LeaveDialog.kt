package com.example.worderworld.feature.game.ui

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
import androidx.fragment.app.setFragmentResult
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.DialogSureLeaveBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaveDialog: DialogFragment() {

    private var binding: DialogSureLeaveBinding? = null

    override fun getTheme(): Int = R.style.NoBackgroundDialog

    companion object {
        const val BACK_TO_HOME_KEY = "BackToHomeKey"
        const val CLOSE_DIALOG_KEY = "CloseDialogKey"

        fun newInstance(isLevelGame: Boolean = false): LeaveDialog {
            return LeaveDialog().apply {
                arguments = bundleOf("isLevel" to isLevelGame)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = DialogSureLeaveBinding.inflate(inflater, container, false).also { binding = it }
        setupView(b)
        return b.root
    }

    private fun setupView(b: DialogSureLeaveBinding) {
        if (arguments?.getBoolean("isLevel") == true) {
            b.sure.text = getString(R.string.lose_progress)
        }
        b.closeButton.setOnClickListener { dismissAllowingStateLoss() }
        b.noButton.setOnClickListener { dismissAllowingStateLoss() }
        b.yesButton.setOnClickListener { closeWithResult() }
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