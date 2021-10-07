package com.yoti.android.cryptocurrencychallenge.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentAssetsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssetsFragment : Fragment() {

    private lateinit var binding: FragmentAssetsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  TODO: To be replaced with the real data from CoinCap API as part of the code challenge
        binding.recyclerViewAssets.adapter = AssetsAdapter(dummyAssets)
    }
}