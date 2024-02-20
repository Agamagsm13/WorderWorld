package com.agamatech.worderworld.feature.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.databinding.FragmentGameBinding
import com.agamatech.worderworld.feature.game.vm.GameViewModel
import com.example.worderworld.feature.game.LetterData
import com.example.worderworld.feature.game.LetterState
import com.example.worderworld.feature.game.adapter.LetterAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameFragment: Fragment() {

    private var binging: FragmentGameBinding? = null
    private val viewModel: GameViewModel by viewModels()
    private var rotation: Animation? = null
    private var adapter: LetterAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentGameBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            adapter = LetterAdapter()
            word.adapter = adapter
            word.layoutManager = LinearLayoutManager(requireContext())
            adapter?.submitList(listOf(LetterData("F", LetterState.INPUT),LetterData("F", LetterState.INPUT),LetterData("G", LetterState.INPUT),LetterData("F", LetterState.INPUT),LetterData("G", LetterState.INPUT)))
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun subscribeUi() {

    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(false)
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}