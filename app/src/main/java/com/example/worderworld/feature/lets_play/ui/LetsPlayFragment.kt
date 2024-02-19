package com.agamatech.worderworld.feature.lets_play.ui

import android.R.attr.animation
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.FragmentLetsPlayBinding
import com.agamatech.worderworld.feature.lets_play.vm.LetsPlayViewModel
import com.agamatech.worderworld.utils.dpToPx
import com.agamatech.worderworld.utils.getDrawableId
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LetsPlayFragment: Fragment() {

    private var binging: FragmentLetsPlayBinding? = null
    private val viewModel: LetsPlayViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentLetsPlayBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            startButton.setOnClickListener {
                findNavController().navigate(R.id.nav_action_lets_play_to_game)
            }
            val display: Display = requireActivity().windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y
            ball.x = width.toFloat()/2 - 50f.dpToPx()
            ball.y =  height.toFloat()/1.3f - 50f.dpToPx()
        }
    }

    private fun subscribeUi() {
        viewModel.apply {
            bestScore.observe(viewLifecycleOwner) {
                binging?.bestScore?.text = getString(R.string.best_score, it)
            }
            selectedBall.observe(viewLifecycleOwner) {
                val resId = getDrawableId(requireContext(), it)
                if (resId != null && resId != 0) {
                    binging?.ballImage?.setImageResource(resId)
                } else {
                    binging?.ballImage?.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ball_1))
                }
            }
        }
        viewModel.getBestScore()
        viewModel.getSelectedBall()
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(true)
        super.onResume()
    }

}