package com.agamatech.worderworld.feature.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.databinding.FragmentGame6Binding
import com.agamatech.worderworld.feature.game.vm.GameViewModel
import com.agamatech.worderworld.utils.showSingle
import com.agamatech.worderworld.event.CheckWordPressEvent
import com.agamatech.worderworld.event.DeleteLetterPressEvent
import com.agamatech.worderworld.event.LetterPressEvent
import com.agamatech.worderworld.feature.game.LetterState
import com.agamatech.worderworld.widget.CustomLetter
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class GameFragment6: Fragment() {

    private var binging: FragmentGame6Binding? = null
    private val args by navArgs<GameFragment6Args>()
    private val LETTERS_COUNT = 6
    private val viewModel: GameViewModel by viewModels()
    private var fullWordsList: List<List<CustomLetter>?>? = null
    private var try0Letters: List<CustomLetter>? = null
    private var try1Letters: List<CustomLetter>? = null
    private var try2Letters: List<CustomLetter>? = null
    private var try3Letters: List<CustomLetter>? = null
    private var try4Letters: List<CustomLetter>? = null
    private var try5Letters: List<CustomLetter>? = null
    private var try6Letters: List<CustomLetter>? = null

    private val backCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleBackButton()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, backCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentGame6Binding.inflate(inflater, container, false).also { binging = it }
        childFragmentManager.setFragmentResultListener(LeaveDialog.CLOSE_DIALOG_KEY, this) { key: String, bundle: Bundle ->
            val isSuccess = bundle.getBoolean(LeaveDialog.BACK_TO_HOME_KEY)
            if (isSuccess) {
                if (viewModel.getIntersCount() == 1) {
                    (requireActivity() as? MainActivity)?.showInter()
                }
                viewModel.setIntersCount()
                findNavController().popBackStack()
            }
        }
        EventBus.getDefault().register(this)
        initUi()
        subscribeUi()
        return b.root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: LetterPressEvent) {
        if ((viewModel.activeLetter.value?:0) <= LETTERS_COUNT - 1) {
            fullWordsList?.getOrNull(viewModel.activeTry.value?: 0)
                ?.getOrNull(viewModel.activeLetter.value?:0)?.let {
                    it.setText(event.letter)
                    viewModel.goToNextLetter()
                }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: DeleteLetterPressEvent) {
        fullWordsList?.getOrNull(viewModel.activeTry.value?: 0)
            ?.getOrNull((viewModel.activeLetter.value?:0) - 1)?.let {
                it.setText("")
                viewModel.goToPreviousLetter()
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: CheckWordPressEvent) {
        if (fullWordsList?.getOrNull(viewModel.activeTry.value?: 0)
                ?.any { it.getText().isEmpty()} != false
        ) {
            Toast.makeText(requireContext(), "Enter full word", Toast.LENGTH_LONG).show()
        } else {
            var word = ""
            fullWordsList?.getOrNull(viewModel.activeTry.value?: 0)?.forEach {
                word += it.getText()
            }
            if (viewModel.passedTries.value?.contains(word) == true) {
                Toast.makeText(requireContext(), "You already tried this one", Toast.LENGTH_LONG).show()
            } else {
                if (!viewModel.checkWord(word)) {
                    Toast.makeText(requireContext(), "Enter real noun", Toast.LENGTH_LONG).show()
                } else {
                    if (word == viewModel.wordValue.value) {
                        redrawLetters()
                        viewModel.addWordToGuessed()
                        resetGame()
                        WinDialog.newInstance().showSingle(childFragmentManager, "Win")
                    } else {
                        redrawLetters()
                        viewModel.addTry(word)
                    }
                }
            }
        }
    }

    private fun redrawLetters() {
        fullWordsList?.getOrNull(viewModel.activeTry.value?: 0)?.forEachIndexed { index, letter ->
            if (viewModel.wordValue.value?.contains(letter.getText()) == true) {
                if (viewModel.wordValue.value?.getOrNull(index).toString() == letter.getText()) {
                    viewModel.addGoodLetter(letter.getText())
                    letter.changeLetterState(LetterState.RESULT_OK)
                } else {
                    viewModel.addGoodLetter(letter.getText())
                    letter.changeLetterState(LetterState.RESULT_WRONG_PLACE)
                }
            } else {
                viewModel.addBadLetter(letter.getText())
                letter.changeLetterState(LetterState.RESULT_FALSE)
            }
        }
    }

    private fun initUi() {
        binging?.apply {
            if (viewModel.firstGame.value == true) {
                viewModel.setWord(args.word)
            }
            try0Letters = listOf(l00, l01, l02, l03, l04, l05)
            try1Letters = listOf(l10, l11, l12, l13, l14, l15)
            try2Letters = listOf(l20, l21, l22, l23, l24, l25)
            try3Letters = listOf(l30, l31, l32, l33, l34, l35)
            try4Letters = listOf(l40, l41, l42, l43, l44, l45)
            try5Letters = listOf(l50, l51, l52, l53, l54, l55)
            try6Letters = listOf(l60, l61, l62, l63, l64, l65)
            fullWordsList = listOf(try0Letters, try1Letters, try2Letters, try3Letters, try4Letters, try5Letters, try6Letters)
            buttonBack.setOnClickListener {
                handleBackButton()
            }
            infoButton.setOnClickListener {
                InfoRulesDialog.newInstance().showSingle(childFragmentManager, "Info")
            }
        }
    }

    fun handleBackButton() {
        LeaveDialog.newInstance().showSingle(childFragmentManager, "Leave")
    }


    private fun subscribeUi() {
        viewModel.activeTry.observe(viewLifecycleOwner) {
            if (it > LETTERS_COUNT) {
                viewModel.checkLooserTrophy()
                resetGame()
                LoseDialog.newInstance(word = viewModel.wordValue.value?:"").showSingle(childFragmentManager, "Lose")
            }
        }
        viewModel.badLetters.observe(viewLifecycleOwner) {
            binging?.keyboard?.changeKeysState(it)
        }
        viewModel.goodLetters.observe(viewLifecycleOwner) {
            binging?.keyboard?.changeGoodKeysState(it)
        }
    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(false)
        super.onResume()
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        backCallback.isEnabled = false
        super.onDestroyView()
    }

    private fun resetGame() {
        binging?.apply {
            playAgain.isVisible = true
            keyboard.isInvisible = true
            playAgain.setOnClickListener {
                playAgain.isInvisible = true
                keyboard.isVisible = true
                fullWordsList?.forEach { word ->
                    word?.forEach { letter ->
                        letter.setText("")
                        letter.changeLetterState(LetterState.INPUT)
                    }
                }
                keyboard.resetKeysState((viewModel.badLetters.value?: listOf()) + (viewModel.goodLetters.value?: listOf()))
                viewModel.resetGame(LETTERS_COUNT)
            }
        }
    }
}