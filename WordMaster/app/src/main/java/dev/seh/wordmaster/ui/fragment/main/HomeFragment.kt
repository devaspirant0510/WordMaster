package dev.seh.wordmaster.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import dev.seh.wordmaster.R
import dev.seh.wordmaster.base.BaseFragment
import dev.seh.wordmaster.databinding.FragmentHomeBinding
import dev.seh.wordmaster.repository.Repository
import dev.seh.wordmaster.ui.recyclerview.adpater.UserRankingAdapter
import dev.seh.wordmaster.viewmodel.HomeFragmentViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var repository: Repository
    private lateinit var homeViewModel: HomeFragmentViewModel

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity())
            .get(HomeFragmentViewModel::class.java)
        mBinding.viewmodel = homeViewModel
        mBinding.rvUserRanking.adapter = UserRankingAdapter()
        observeData()
    }

    private fun observeData() {
        homeViewModel.userData.observe(viewLifecycleOwner, {
            Log.e(TAG, "observeData:${it.userName}")
            mBinding.tvWelcomeMessage.text = it.userName

        })

    }
}