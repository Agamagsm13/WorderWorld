package com.agamatech.worderworld.feature.lets_play.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
        subscribeUi()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            startButton.setOnClickListener {
                val word = loadWordManager.getRandomWord(5)
                findNavController().navigate(R.id.nav_action_lets_play_to_game, bundleOf("word" to word))
            }
            loadWordManager.loadWords(requireContext())
        }
    }

    private fun subscribeUi() {

    }

    override fun onResume() {
        (requireActivity() as MainActivity).changeNavViewVisibility(true)
        super.onResume()
    }

}