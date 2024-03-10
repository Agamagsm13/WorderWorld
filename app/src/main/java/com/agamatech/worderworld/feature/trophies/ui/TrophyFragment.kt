package com.agamatech.worderworld.feature.trophies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.agamatech.worderworld.databinding.FragmentTrophyBinding
import com.agamatech.worderworld.feature.store.adapter.TrophyAdapter
import com.agamatech.worderworld.feature.trophies.vm.TrophyViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrophyFragment: Fragment() {

    private var binging: FragmentTrophyBinding? = null
    private val viewModel: TrophyViewModel by viewModels()
    private var manager: GridLayoutManager? = null
    private var adapter: TrophyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentTrophyBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi()
        viewModel.getAllTrophies()
        viewModel.getAchievedTrophies()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            adapter = TrophyAdapter()
            manager = GridLayoutManager(requireContext(), 1)
            trophies.layoutManager = manager
            trophies.adapter = adapter
        }
    }

    private fun subscribeUi() {
        viewModel.apply {
            allTrophies.observe(viewLifecycleOwner) {
                tryRefreshTrophiesListUi()
            }
            achievedTrophies.observe(viewLifecycleOwner) {
                tryRefreshTrophiesListUi()
            }
        }
    }

    private fun tryRefreshTrophiesListUi() {
        if (viewModel.allTrophies.value?.isNotEmpty() == true) {
            val trophies = viewModel.achievedTrophies.value
            if (trophies != null) {
                adapter?.setAchievedTrophies(trophies)
                adapter?.submitList((viewModel.allTrophies.value)?.sortedByDescending { all -> trophies.any { all.id == it?.id } })
                adapter?.notifyDataSetChanged()
            } else {
                viewModel.allTrophies.value?.let {
                    adapter?.submitList(it)
                    adapter?.notifyDataSetChanged()
                }
            }

        }
    }
}