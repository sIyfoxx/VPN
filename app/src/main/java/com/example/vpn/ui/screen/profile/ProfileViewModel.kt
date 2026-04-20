package com.example.vpn.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vpn.domain.models.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Calendar

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadUserStats()
    }

    fun loadUserData() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(800)

            // Симуляция данных пользователя
            val expiryDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 18)
            }.time

            val user = User(
                id = "user_123",
                email = "user@example.com",
                username = "User Name",
                avatarUrl = null,
                subscription = Subscription(
                    plan = SubscriptionPlan.YEARLY,
                    startsAt = Date(),
                    expiresAt = expiryDate,
                    autoRenew = true,
                    isTrial = false
                ),
                createdAt = Calendar.getInstance().apply {
                    add(Calendar.MONTH, -6)
                }.time,
                isEmailVerified = true
            )

            _uiState.update {
                it.copy(
                    user = user,
                    isLoading = false
                )
            }
        }
    }

    fun loadUserStats() {
        viewModelScope.launch {
            delay(500)

            _uiState.update {
                it.copy(
                    stats = VpnStats(
                        todayDownload = 128 * 1024 * 1024,
                        todayUpload = 45 * 1024 * 1024,
                        totalDownload = 45_200 * 1024 * 1024, // 45.2 GB
                        totalUpload = 12_300 * 1024 * 1024,   // 12.3 GB
                        sessionCount = 127,
                        lastSessionDate = Date()
                    )
                )
            }
        }
    }

    fun upgradePlan() {
        // TODO: открыть экран выбора плана
    }

    fun logout() {
        // TODO: выход из аккаунта
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)
            // Очищаем данные
            _uiState.update {
                it.copy(
                    user = User(
                        id = "",
                        email = "",
                        username = ""
                    ),
                    isLoading = false
                )
            }
        }
    }

    fun refresh() {
        loadUserData()
        loadUserStats()
    }
}