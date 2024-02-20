package com.agamatech.worderworld.feature.lets_play.ui

import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.FragmentLetsPlayBinding
import com.agamatech.worderworld.feature.lets_play.vm.LetsPlayViewModel
import com.agamatech.worderworld.utils.dpToPx
import com.agamatech.worderworld.utils.getDrawableId
import com.example.worderworld.feature.LoadWordManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
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
                findNavController().navigate(R.id.nav_action_lets_play_to_game, bundleOf("word" to "wheel"))
            }
            val display: Display = requireActivity().windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y
            ball.x = width.toFloat()/2 - 50f.dpToPx()
            ball.y =  height.toFloat()/1.3f - 50f.dpToPx()
            loadWordManager.loadWords(requireContext())
        }
    }

    fun Resources.getRawTextFile(@RawRes id: Int) =
        openRawResource(id).bufferedReader().use { it.readText() }

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

    fun readFileAsLinesUsingReadLines(fileName: String): List<String> {
        if (File(fileName).exists()) return File(fileName).readLines()
        else return listOf()
    }

}