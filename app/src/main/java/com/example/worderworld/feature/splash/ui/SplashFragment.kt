package com.agamatech.worderworld.feature.splash.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.FragmentSplashBinding
import com.agamatech.worderworld.feature.splash.vm.SplashViewModel
import com.agamatech.worderworld.utils.startLink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment: Fragment() {

    private var binging: FragmentSplashBinding? = null
    private val viewModel: SplashViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentSplashBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            textViewPrivacyPolicy.setOnClickListener {
                startLink(requireContext(), getString(R.string.policy_privacy_policy))
            }
            textViewTermOfUse.setOnClickListener {
                startLink(requireContext(), getString(R.string.policy_term_of_use))
            }
            startButton.setOnClickListener {
                findNavController().navigate(R.id.nav_action_splash_to_lets_play)
            }
            scaleUpRing()
            rotate()
        }
    }

    private fun subscribeUi() {
        viewModel.apply {

        }
    }

    private fun scaleUpRing() {
        val anim = AnimatorSet()
        anim.playTogether(
            ObjectAnimator.ofFloat(binging?.ring, "alpha", 1f, 0f),
            ObjectAnimator.ofFloat(binging?.ring, "scaleY", 1f, 2f),
            ObjectAnimator.ofFloat(binging?.ring, "scaleX", 1f, 2f)
        )
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binging?.ring?.alpha = 0f
                binging?.ring?.scaleX = 1f
                binging?.ring?.scaleY = 1f
                if (findNavController().currentDestination?.displayName.toString().endsWith("nav_splash")) {
                    scaleUpRing()
                }
                super.onAnimationEnd(animation)
            }
        })
        anim.duration = 3000
        anim.start()
    }

    private fun rotate() {
        val anim = AnimatorSet()
        anim.playTogether(
            ObjectAnimator.ofFloat(binging?.logo, "rotation", 0f, 360f),
        )
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (findNavController().currentDestination?.displayName.toString().endsWith("nav_splash")) {
                    rotate()
                }
                super.onAnimationEnd(animation)
            }
        })
        anim.duration = 3000
        anim.start()
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(false)
        super.onResume()
    }

}