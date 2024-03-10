package com.agamatech.worderworld.feature.lets_play.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.FragmentLetsLevelPlayBinding
import com.agamatech.worderworld.feature.lets_play.vm.LetsLevelPlayViewModel
import com.agamatech.worderworld.feature.LoadWordManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LetsPlayLevelsFragment: Fragment() {

    private var binging: FragmentLetsLevelPlayBinding? = null
    private val viewModel: LetsLevelPlayViewModel by viewModels()
    @Inject
    lateinit var loadWordManager: LoadWordManager

    private val brPurchase = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.getLevelModeOpen()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentLetsLevelPlayBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi(b)
        viewModel.getMaxLevel()
        viewModel.getLevelModeOpen()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            if (viewModel.getLevelModeOpen()) {
                getWord.isVisible = true
                unlock.isVisible = false
            } else {
                unlock.isVisible = true
                getWord.isVisible = false
            }
            progress.isVisible = false
            startButton.setOnClickListener {
                if (unlock.isVisible) {
                    viewModel.itemList.value?.firstOrNull { it.productDetails.productId == "unlock_long_words" }?.let {
                        viewModel.launchItemPurchase(it, requireActivity())
                    }
                } else {
                    getWord.isVisible = false
                    progress.isVisible = true
                    val word = loadWordManager.getRandomWord(5)
                    findNavController().navigate(R.id.nav_action_level_play_to_game, bundleOf("word" to word))
                }
            }
        }
        val margin = resources.getDimensionPixelSize(R.dimen.default_bottom_padding)
        val layoutParams = binging?.root?.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, margin)
    }

    private fun subscribeUi(b: FragmentLetsLevelPlayBinding) {
        viewModel.maxLevel.observe(viewLifecycleOwner) {
            b.wordsGuessed.text = getString(R.string.max_level, it)
        }
        viewModel.itemList.observe(viewLifecycleOwner) { purchases ->
            purchases.firstOrNull { it.productDetails.productId == "unlock_long_words" }?.let {
                b.unlock.text = getString(R.string.unlock_mode, it.priceString)
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->

        }
        viewModel.levelModeOpened.observe(viewLifecycleOwner) {
            checkButtonState()
        }
    }

    private fun checkButtonState() {
        binging?.apply {
            if (viewModel.isLevelModeOpen()) {
                getWord.isVisible = true
                unlock.isVisible = false
            } else {
                unlock.isVisible = true
                getWord.isVisible = false
            }
        }
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(true)
        super.onResume()
        val receiverFlags = ContextCompat.RECEIVER_NOT_EXPORTED
        ContextCompat.registerReceiver(requireContext(), brPurchase,  IntentFilter("com.agamatech.worderworld.purchase"), receiverFlags)
    }

    override fun onStop() {
        super.onStop()
        try {
            requireContext().unregisterReceiver(brPurchase)
        } catch (e: Exception) {}
    }

}