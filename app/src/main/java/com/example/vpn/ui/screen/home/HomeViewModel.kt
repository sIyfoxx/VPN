package com.example.vpn.ui.screens.home

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

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Симуляция VPN-менеджера (позже заменим на реальный)
    private var isRealConnected = false

    init {
        // Загружаем начальные данные
        loadServers()
        loadStats()
    }

    fun connect() {
        viewModelScope.launch {
            _uiState.update { it.copy(connectionState = ConnectionState.Connecting) }

            // Симуляция подключения
            delay(1500)

            isRealConnected = true
            _uiState.update {
                it.copy(
                    connectionState = ConnectionState.Connected(startedAt = Date())
                )
            }

            // Запускаем симуляцию трафика
            startTrafficSimulation()
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            _uiState.update { it.copy(connectionState = ConnectionState.Connecting) }

            // Симуляция отключения
            delay(500)

            isRealConnected = false
            _uiState.update {
                it.copy(connectionState = ConnectionState.Disconnected)
            }
        }
    }

    fun toggleConnection() {
        if (_uiState.value.isConnected) {
            disconnect()
        } else {
            connect()
        }
    }

    fun selectServer(server: VpnServer) {
        _uiState.update {
            it.copy(
                currentServer = server,
                servers = it.servers.map { s ->
                    if (s.id == server.id) s.copy(isRecommended = true)
                    else s.copy(isRecommended = false)
                }
            )
        }

        // Если были подключены, переподключаемся к новому серверу
        if (_uiState.value.isConnected) {
            disconnect()
            connect()
        }
    }

    fun loadServers() {
        _uiState.update { it.copy(isLoading = true) }

        // Симуляция загрузки с сервера
        viewModelScope.launch {
            delay(800)
            _uiState.update {
                it.copy(
                    servers = VpnServer.Examples,
                    isLoading = false
                )
            }
        }
    }

    fun loadStats() {
        // Симуляция загрузки статистики
        viewModelScope.launch {
            delay(500)
            _uiState.update {
                it.copy(
                    stats = VpnStats(
                        todayDownload = 128 * 1024 * 1024, // 128 MB
                        todayUpload = 45 * 1024 * 1024,   // 45 MB
                        totalDownload = 2_345 * 1024 * 1024, // 2.3 GB
                        totalUpload = 890 * 1024 * 1024,     // 890 MB
                        sessionCount = 127
                    )
                )
            }
        }
    }

    private fun startTrafficSimulation() {
        viewModelScope.launch {
            var bytesReceived = 0L
            var bytesSent = 0L

            while (isRealConnected) {
                delay(2000)

                // Симуляция трафика
                bytesReceived += (500 * 1024..2_000 * 1024).random()
                bytesSent += (200 * 1024..1_000 * 1024).random()

                _uiState.update { state ->
                    val newStats = state.stats.copy(
                        todayDownload = state.stats.todayDownload + bytesReceived,
                        todayUpload = state.stats.todayUpload + bytesSent,
                        totalDownload = state.stats.totalDownload + bytesReceived,
                        totalUpload = state.stats.totalUpload + bytesSent
                    )

                    val newConnection = (state.connectionState as? ConnectionState.Connected)?.copy(
                        bytesReceived = (state.connectionState as ConnectionState.Connected).bytesReceived + bytesReceived,
                        bytesSent = (state.connectionState as ConnectionState.Connected).bytesSent + bytesSent
                    ) ?: state.connectionState

                    state.copy(
                        stats = newStats,
                        connectionState = newConnection
                    )
                }

                bytesReceived = 0
                bytesSent = 0
            }
        }
    }
}