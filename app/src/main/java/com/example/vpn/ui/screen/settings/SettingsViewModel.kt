package com.example.vpn.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vpn.domain.models.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        // Симуляция загрузки из DataStore
        viewModelScope.launch {
            delay(300)
            _uiState.update {
                it.copy(
                    settings = AppSettings(
                        autoConnect = false,
                        notificationsEnabled = true,
                        killSwitch = false,
                        selectedProtocol = VpnProtocol.VLESS_REALITY,
                        theme = AppTheme.SYSTEM,
                        language = AppLanguage.SYSTEM,
                        dataSaving = false
                    )
                )
            }
        }
    }

    fun setAutoConnect(enabled: Boolean) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(autoConnect = enabled)
            )
        }
        saveSettings()
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(notificationsEnabled = enabled)
            )
        }
        saveSettings()
    }

    fun setKillSwitch(enabled: Boolean) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(killSwitch = enabled)
            )
        }
        saveSettings()
    }

    fun setProtocol(protocol: VpnProtocol) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(selectedProtocol = protocol)
            )
        }
        saveSettings()
    }

    fun setTheme(theme: AppTheme) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(theme = theme)
            )
        }
        saveSettings()
    }

    fun setLanguage(language: AppLanguage) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(language = language)
            )
        }
        saveSettings()
    }

    fun setDataSaving(enabled: Boolean) {
        _uiState.update { state ->
            state.copy(
                settings = state.settings.copy(dataSaving = enabled)
            )
        }
        saveSettings()
    }

    private fun saveSettings() {
        // TODO: сохранить в DataStore
        viewModelScope.launch {
            delay(100)
            // Симуляция сохранения
        }
    }

    fun resetToDefault() {
        _uiState.update {
            it.copy(
                settings = AppSettings()
            )
        }
        saveSettings()
    }
}