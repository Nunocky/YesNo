package com.example.yesno.scene.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.yesno.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.button.setOnClickListener {
            viewModel.processFetch()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.fetchState.collect { state ->
                when (state) {
                    MainViewModel.FetchState.Init -> {}

                    MainViewModel.FetchState.Fetching -> {
                        binding.imageView.setImageBitmap(null)
                    }

                    is MainViewModel.FetchState.Success -> {
                        Glide.with(requireActivity()).load(state.yesno.image)
                            .into(binding.imageView)
                    }

                    is MainViewModel.FetchState.Fail -> {
                        binding.imageView.setImageBitmap(null)
                        val dialog = AlertDialog.Builder(requireActivity())
                            .setTitle("Error")
                            .setMessage("Fetch failed")
                            .setPositiveButton("Close") { d, v ->
                                d.dismiss()
                            }
                            .create()

                        dialog.show()
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}