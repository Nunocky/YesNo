package com.example.yesno

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.yesno.api.YesNoRepository
import com.example.yesno.data.YesNo
import com.example.yesno.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: YesNoRepository
) : ViewModel() {
    private val _fetchState = MutableStateFlow<FetchState>(FetchState.Init)
    val fetchState: StateFlow<FetchState> = _fetchState

    val isFetching = fetchState.map { it is FetchState.Fetching }.asLiveData()

    val text = fetchState.map { state ->
        when (state) {
            FetchState.Init -> ""
            FetchState.Fetching -> "Fetching..."
            is FetchState.Success -> state.yesno.answer
            is FetchState.Fail -> "Failed"
        }
    }.asLiveData()

    fun processFetch() = viewModelScope.launch(Dispatchers.IO) {

        _fetchState.value = FetchState.Fetching

        val response = repository.fetch()

        response
            .onSuccess {
                _fetchState.value = FetchState.Success(it)
            }.onFailure {
                _fetchState.value = FetchState.Fail(it)
            }
    }

    sealed class FetchState {
        object Init : FetchState()
        object Fetching : FetchState()
        class Success(val yesno: YesNo) : FetchState()
        class Fail(val th: Throwable) : FetchState()
    }

}

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
                        Toast.makeText(
                            requireActivity(),
                            "Failed(${state.th.message})",
                            Toast.LENGTH_SHORT
                        ).show()
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