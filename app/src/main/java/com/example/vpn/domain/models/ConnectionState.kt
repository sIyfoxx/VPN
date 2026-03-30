package com.example.vpn.domain.models

import java.util.Date

sealed class ConnectionState {
    object Disconnected : ConnectionState()
    object Connecting : ConnectionState()
    data class Connected(
        val startedAt: Date = Date(),
        val bytesReceived: Long = 0,
        val bytesSent: Long = 0
    ) : ConnectionState()
    data class Error(val message: String) : ConnectionState()

    val isConnected: Boolean
        get() = this is Connected

    val isConnecting: Boolean
        get() = this is Connecting

    val isDisconnected: Boolean
        get() = this is Disconnected

    val hasError: Boolean
        get() = this is Error

    val displayText: String
        get() = when (this) {
            is Disconnected -> "Disconnected"
            is Connecting -> "Connecting..."
            is Connected -> "Connected"
            is Error -> "Error: ${(this as Error).message}"
        }

    fun getElapsedTime(): String? {
        return if (this is Connected) {
            val diff = System.currentTimeMillis() - startedAt.time
            val seconds = diff / 1000
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60
            String.format("%02d:%02d:%02d", hours, minutes, secs)
        } else null
    }
}