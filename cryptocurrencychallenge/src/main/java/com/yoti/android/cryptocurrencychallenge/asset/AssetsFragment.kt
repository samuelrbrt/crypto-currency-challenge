package com.yoti.android.cryptocurrencychallenge.asset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.config.base.BaseDataBindingHolder
import com.yoti.android.cryptocurrencychallenge.config.base.BaseFragment
import com.yoti.android.cryptocurrencychallenge.config.base.ShimmerAdapter
import com.yoti.android.cryptocurrencychallenge.config.state.observe
import com.yoti.android.cryptocurrencychallenge.databinding.AssetFragmentBinding
import com.yoti.android.cryptocurrencychallenge.databinding.AssetItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssetsFragment : BaseFragment() {
    private val vm: AssetViewModel by activityViewModels()
    private lateinit var binding: AssetFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AssetFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShimmerAdapter(R.layout.item_asset, R.layout.shimmer_asset, ::AssetViewHolder)
        binding.recyclerViewAssets.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm

        vm.assets.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }

        vm.assetRefreshState.observe(viewLifecycleOwner)
            .onLoading { isLoading, _ -> adapter.isShimming = isLoading && adapter.items.isEmpty() }
    }

    inner class AssetViewHolder(itemBinding: AssetItemBinding) : BaseDataBindingHolder<Asset, AssetItemBinding>(itemBinding) {
        override fun onItemClick(item: Asset, position: Int) {
            val direction = AssetsFragmentDirections.toMarket(item.id)
            findNavController().navigate(direction)
        }
    }
}