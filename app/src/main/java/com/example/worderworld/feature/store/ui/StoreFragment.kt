package com.agamatech.worderworld.feature.store.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.agamatech.worderworld.MainActivity
import com.agamatech.worderworld.databinding.FragmentStoreBinding
import com.agamatech.worderworld.feature.store.vm.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StoreFragment: Fragment() {

    private var binging: FragmentStoreBinding? = null
    private val viewModel: StoreViewModel by viewModels()
    private var manager: GridLayoutManager? = null

    private val brPurchase = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //viewModel.updateList()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val b = FragmentStoreBinding.inflate(inflater, container, false).also { binging = it }
        initUi()
        subscribeUi()
        return b.root
    }

    private fun initUi() {
        binging?.apply {
            manager = GridLayoutManager(requireContext(), 2)
            balls.layoutManager = manager
        }
    }

    private fun subscribeUi() {
        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner) {
                Log.e("BillingService Error", it.toString())
            }
            itemList.observe(viewLifecycleOwner) { purchases ->

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