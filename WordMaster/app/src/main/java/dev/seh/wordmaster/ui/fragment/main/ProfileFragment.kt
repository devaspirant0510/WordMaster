package dev.seh.wordmaster.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import dev.seh.wordmaster.R
import dev.seh.wordmaster.base.BaseFragment
import dev.seh.wordmaster.databinding.FragmentProfileBinding
import dev.seh.wordmaster.viewmodel.HomeFragmentViewModel


class ProfileFragment :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile){
    private lateinit var viewModel:HomeFragmentViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        mBinding.viewmodel = viewModel


    }
}