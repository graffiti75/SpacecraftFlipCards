package com.cericatto.spacecraftflipcards.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cericatto.spacecraftflipcards.domain.errors.Result
import com.cericatto.spacecraftflipcards.domain.repository.OpenNotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val repository: OpenNotifyRepository
): ViewModel() {

	private val _state = MutableStateFlow(MainScreenState())
	val state: StateFlow<MainScreenState> = _state.asStateFlow()

	fun onAction(action: MainScreenAction) {
		when (action) {
			is MainScreenAction.OnRetry -> fetchData()
		}
	}

	init {
		fetchData()
	}

	private fun fetchData() {
		viewModelScope.launch {
			when (val result = repository.fetchData()) {
				is Result.Error -> {
					_state.update { state ->
						state.copy(
							loading = false,
							isConnected = false,
							performAnimation = true
						)
					}
				}
				is Result.Success -> {
					_state.update { it.copy(isConnected = true) }
					_state.update { state ->
						state.copy(
							loading = false,
							craftNames = result.data,
							performAnimation = false
						)
					}
				}
			}
		}
	}
}