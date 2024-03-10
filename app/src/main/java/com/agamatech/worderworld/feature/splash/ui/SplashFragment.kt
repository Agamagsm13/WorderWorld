package com.agamatech.worderworld.feature.splash.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
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

    private var binding: FragmentSplashBinding? = null
    private val viewModel: SplashViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentSplashBinding.inflate(inflater, container, false).also { binding = it }
        initUi()
        subscribeUi()
        return b.root
    }

    private fun initUi() {
        binding?.apply {
            textViewPrivacyPolicy.setOnClickListener {
                startLink(requireContext(), getString(R.string.policy_privacy_policy))
            }
            textViewTermOfUse.setOnClickListener {
                startLink(requireContext(), getString(R.string.policy_term_of_use))
            }
            startButton.setOnClickListener {
                findNavController().navigate(R.id.nav_action_splash_to_lets_play)
            }
            l1.setText("W")
            l2.setText("O")
            l3.setText("R")
            l4.setText("D")
            l5.setText("E")
            l6.setText("R")
            l7.setText("W")
            l8.setText("O")
            l9.setText("R")
            l10.setText("L")
            l11.setText("D")

            startAnim()

        }
    }

    private fun startAnim() {
        binding?.name?.let {
            val anim = AnimatorSet().apply {
                duration = 3000
            }
            anim.playTogether(
                ObjectAnimator.ofFloat(it, "rotation", 0f, -10f, 0f, 10f, 0f, -10f, 0f, 10f, 0f,),
                ObjectAnimator.ofFloat(it, "alpha", 1f, 0.6f, 1f, 0.6f, 1f, 0.6f, 1f),
            )
            anim.doOnEnd {
                it.start()
            }
            anim.start()
        }
    }

    private fun subscribeUi() {
        viewModel.apply {

        }
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(false)
        super.onResume()
    }

}