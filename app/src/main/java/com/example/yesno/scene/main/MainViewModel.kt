package com.example.yesno.scene.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.yesno.data.YesNo
import com.example.yesno.repository.YesNoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: YesNoRepository
) : ViewModel() {
    sealed interface FetchState {
        object Init : FetchState
        object Fetching : FetchState
        class Success(val yesno: YesNo) : FetchState
        class Fail(val th: Throwable) : FetchState
    }

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

    fun processFetch() = viewModelScope.launch {

        _fetchState.value = FetchState.Fetching

        withContext(Dispatchers.IO) {
            repository.fetch()
                .onSuccess { yesno ->
                    _fetchState.value = FetchState.Success(yesno)
                }
                .onFailure {
                    _fetchState.value = FetchState.Fail(it)
                }
        }
    }
}