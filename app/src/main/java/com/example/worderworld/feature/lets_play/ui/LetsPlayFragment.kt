package com.agamatech.worderworld.feature.lets_play.ui

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
import com.agamatech.worderworld.databinding.FragmentLetsPlayBinding
import com.agamatech.worderworld.feature.lets_play.vm.LetsPlayViewModel
import com.example.worderworld.feature.LoadWordManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LetsPlayFragment: Fragment() {

    private var binging: FragmentLetsPlayBinding? = null
    private val viewModel: LetsPlayViewModel by viewModels()
    @Inject
    lateinit var loadWordManager: LoadWordManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentLetsPlayBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi(b)
        viewModel.getWordsGuessed()
        viewModel.setLetterCount(5)
        return b.root
    }

    private fun initUi() {
        loadWordManager.loadWords(requireContext())
        binging?.apply {
            getWord.isVisible = true
            progress.isVisible = false
            startButton.setOnClickListener {
                getWord.isVisible = false
                progress.isVisible = true
                when (viewModel.letterCount.value) {
                    5 -> {
                        val word = loadWordManager.getRandomWord(5)
                        findNavController().navigate(R.id.nav_action_lets_play_to_game, bundleOf("word" to word))
                    }
                    6 -> {
                        val word = loadWordManager.getRandomWord(6)
                        findNavController().navigate(R.id.nav_action_lets_play_to_game_6, bundleOf("word" to word))
                    }
                    7 -> {
                        val word = loadWordManager.getRandomWord(7)
                        findNavController().navigate(R.id.nav_action_lets_play_to_game_7, bundleOf("word" to word))
                    }
                    8 -> {
                        val word = loadWordManager.getRandomWord(8)
                        findNavController().navigate(R.id.nav_action_lets_play_to_game_8, bundleOf("word" to word))
                    }
                }
            }
            plus.setOnClickListener {
                if ((viewModel.letterCount.value?: 0) < 8) {
                    viewModel.addLetterCount()
                }
            }
            minus.setOnClickListener {
                if ((viewModel.letterCount.value?: 0) > 5) {
                    viewModel.minusLetterCount()
                }
            }
        }
        val margin = resources.getDimensionPixelSize(R.dimen.default_bottom_padding)
        val layoutParams = binging?.root?.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, margin)
    }

    private fun subscribeUi(b: FragmentLetsPlayBinding) {
        viewModel.letterCount.observe(viewLifecycleOwner) {
            b.lettersCount.text = it.toString()
            b.minus.isVisible = it > 5
            b.plus.isVisible = it <= 7
        }
        viewModel.wordsGuessed.observe(viewLifecycleOwner) {
            b.wordsGuessed.text = getString(R.string.words_guessed, it)
        }
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(true)
        super.onResume()
    }

}