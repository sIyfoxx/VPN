package com.example.vpn.domain.models

data class HomeUiState(
    val connectionState: ConnectionState = ConnectionState.Disconnected,
    val currentServer: VpnServer = VpnServer.Default,
    val servers: List<VpnServer> = VpnServer.Examples,
    val stats: VpnStats = VpnStats(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isConnected: Boolean
        get() = connectionState.isConnected

    val isConnecting: Boolean
        get() = connectionState.isConnecting

    val statusText: String
        get() = connectionState.displayText

    val sessionTime: String?
        get() = connectionState.getElapsedTime()

    val todayTraffic: String
        get() = "${stats.todayDownloadFormatted} / ${stats.todayUploadFormatted}"

    val getLatencyText: String
        get() = if (currentServer.latency > 0) "${currentServer.latency} ms" else "Unknown"

    val getLoadText: String
        get() = "${currentServer.load}%"

    val loadColor: Long
        get() = when (currentServer.load) {
            in 0..30 -> 0xFF4CAF50.toLong()    // зелёный
            in 31..70 -> 0xFFFF9800.toLong()   // оранжевый
            else -> 0xFFF44336.toLong()        // красный
        }
}