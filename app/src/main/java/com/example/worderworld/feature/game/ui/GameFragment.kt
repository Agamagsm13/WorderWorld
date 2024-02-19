package com.agamatech.worderworld.feature.game.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.FragmentGameBinding
import com.agamatech.worderworld.feature.game.vm.GameViewModel
import com.agamatech.worderworld.utils.getDrawableId
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameFragment: Fragment() {

    private var binging: FragmentGameBinding? = null
    private val viewModel: GameViewModel by viewModels()
    private var rotation: Animation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentGameBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi()
        return b.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {
        binging?.apply {
            ballImage.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    viewModel.hitBall(event.x)
                }
                true
            }
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun subscribeUi() {
        viewModel.apply {
            coordinates.observe(viewLifecycleOwner) {
                binging?.ball?.x = it.first
                binging?.ball?.y = it.second
                if ((binging?.ball?.y?: 0F) < -2 * viewModel.radiusInPx) {
                    binging?.arrow?.isVisible = true
                    binging?.arrow?.x = it.first
                } else {
                    binging?.arrow?.isVisible = false
                }
            }
            score.observe(viewLifecycleOwner) {
                if (it % 10 == 0 && it > 0) {
                    playScoreScale()
                }
                binging?.scoreText?.text = it.toString()
            }
            direction.observe(viewLifecycleOwner) {
                rotateBall(it)
            }
            selectedBall.observe(viewLifecycleOwner) {
                val resId = getDrawableId(requireContext(), it)
                if (resId != null && resId != 0) {
                    binging?.ballImage?.setImageResource(resId)
                } else {
                    binging?.ballImage?.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ball_1))
                }
            }
            leftEdgeTouched.observe(viewLifecycleOwner) {
                playLeftEdge()
            }
            rightEdgeTouched.observe(viewLifecycleOwner) {
                playRightEdge()
            }
            bumpCount.observe(viewLifecycleOwner) {
                if (it > 0) {
                    minusScore()
                    playScoreMinus(it)
                }
            }
            gameFailed.observe(viewLifecycleOwner) {
                playGameFailed()
                if (getIntersCount() == 0) {
                    (requireActivity() as? MainActivity)?.showInter()
                }
            }
            getSelectedBall()
            val display: Display = requireActivity().windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y
            setScreenSize(Pair(width.toFloat(), height.toFloat()))
            setCoordinates(Pair(width.toFloat()/2 - viewModel.radiusInPx, height.toFloat()/1.3f - viewModel.radiusInPx))
            setStartCoordinates(Pair(width.toFloat()/2 - viewModel.radiusInPx, height.toFloat()/1.3f - viewModel.radiusInPx))
        }
    }

    private fun playLeftEdge() {
        val anim =  ObjectAnimator.ofFloat(binging?.leftEdge, "alpha", 0f, 1f, 0f)
        anim.duration = 500
        anim.start()
    }

    private fun playRightEdge() {
        val anim =  ObjectAnimator.ofFloat(binging?.rightEdge, "alpha", 0f, 1f, 0f)
        anim.duration = 500
        anim.start()
    }

    private fun playGameFailed() {
        val anim =  ObjectAnimator.ofFloat(binging?.bottomEdge, "alpha", 0f, 1f, 0f)
        anim.duration = 500
        anim.start()
    }

    private fun playScoreScale() {
        val anim = AnimatorSet()
        anim.playTogether(
            ObjectAnimator.ofFloat(binging?.scoreText, "alpha", 0.2f, 1f, 0.2f),
        )
        anim.duration = 400
        anim.start()
    }

    private fun rotateBall(direction: Int) {
        binging?.ballImage?.clearAnimation()
        if (direction == 0) {
            return
        }
        rotation = AnimationUtils.loadAnimation(requireContext(), if (direction < 0) R.anim.rotate_left else R.anim.rotate_right)
        rotation?.fillAfter = true
        rotation?.interpolator = LinearInterpolator()
        binging?.ballImage?.startAnimation(rotation)
    }

    private fun playScoreMinus(value: Int) {
        val anim = AnimatorSet()
        binging?.scoreMinus?.text = "-$value"
        anim.playTogether(
            ObjectAnimator.ofFloat(binging?.scoreMinus, "alpha", 0.0f, 0.5f),
            ObjectAnimator.ofFloat(binging?.scoreMinus, "translationY", 0.0f, 300f),
            ObjectAnimator.ofFloat(binging?.scoreMinus, "scaleY", 1f, 0.5f),
            ObjectAnimator.ofFloat(binging?.scoreMinus, "scaleX", 1f, 0.5f)
        )
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binging?.scoreMinus?.alpha = 0f
                binging?.scoreMinus?.translationY = 0f
                binging?.scoreMinus?.scaleX = 1f
                binging?.scoreMinus?.scaleY = 1f
                super.onAnimationEnd(animation)
            }
        })
        anim.duration = 400
        anim.start()
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(false)
        super.onResume()
    }

    override fun onDestroy() {
        binging?.ballImage?.clearAnimation()
        super.onDestroy()
    }
}