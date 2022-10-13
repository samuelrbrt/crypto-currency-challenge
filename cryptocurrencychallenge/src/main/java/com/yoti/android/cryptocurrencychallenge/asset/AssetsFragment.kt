package com.yoti.android.cryptocurrencychallenge.asset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.config.base.*
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

        val pagingAdapter = PagingAdapter(R.layout.item_asset, ::AssetViewHolder) { i1, i2 -> i1.id == i2.id }
        val adapterWithFooter =
            pagingAdapter.withLoadStateFooter(ShimmerLoadStateAdapter(R.layout.shimmer_asset, ::AssetShimmerVH) {
                pagingAdapter.retry()
            })

        pagingAdapter.addLoadStateListener {
            binding.isLoading = it.append is LoadState.Loading || it.refresh is LoadState.Loading
            binding.isInitLoadError = it.refresh is LoadState.Error
        }

        binding.assetsRV.adapter = adapterWithFooter
        binding.retry = {
            pagingAdapter.refresh()
        }

        vm.assets.observe(viewLifecycleOwner) {
            pagingAdapter.submitData(lifecycle, it)
        }
    }

    inner class AssetViewHolder(itemBinding: AssetItemBinding) :
        PagingViewHolder<Asset, AssetItemBinding>(itemBinding) {
        override fun onItemClick(item: Asset, position: Int) {
            val direction = AssetsFragmentDirections.toMarket(item.id)
            findNavController().navigate(direction)
        }
    }

    private class AssetShimmerVH(itemBinding: ShimmerAssetBinding) : LoadStateViewHolder<ShimmerAssetBinding>(itemBinding)
}