package com.agamatech.worderworld.feature.daily.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.FragmentLetsPlayDailyBinding
import com.agamatech.worderworld.feature.LoadWordManager
import com.agamatech.worderworld.feature.daily.vm.LetsPlayDailyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LetsPlayDailyFragment: Fragment() {

    private var binging: FragmentLetsPlayDailyBinding? = null
    private val viewModel: LetsPlayDailyViewModel by viewModels()
    @Inject
    lateinit var loadWordManager: LoadWordManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentLetsPlayDailyBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi(b)
        viewModel.getWordsGuessed()
        viewModel.getDate()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            progress.isVisible = false
            startButton.setOnClickListener {
                val word = loadWordManager.getRandomWord(5)
                findNavController().navigate(R.id.nav_action_lets_play_to_game, bundleOf("word" to word))
            }
        }
        val margin = resources.getDimensionPixelSize(R.dimen.default_bottom_padding)
        val layoutParams = binging?.root?.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, margin)
    }

    private fun subscribeUi(b: FragmentLetsPlayDailyBinding) {
        viewModel.wordsGuessedRow.observe(viewLifecycleOwner) {
            b.wordsGuessed.text = getString(R.string.words_guessed_row, it)
        }
        viewModel.currentDate.observe(viewLifecycleOwner) {
            b.title.text = it
        }
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(true)
        super.onResume()
    }

}