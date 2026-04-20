package com.example.vpn.domain.models

import java.util.Date

data class VpnStats(
    val todayDownload: Long = 0,      // байты
    val todayUpload: Long = 0,         // байты
    val totalDownload: Long = 0,       // байты
    val totalUpload: Long = 0,         // байты
    val sessionCount: Int = 0,
    val lastSessionDate: Date? = null,
    val totalAllFormatted: String = ""
) {
    val todayTotal: Long
        get() = todayDownload + todayUpload

    val totalAll: Long
        get() = totalDownload + totalUpload

    fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> String.format("%.1f KB", bytes / 1024.0)
            bytes < 1024 * 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024))
            else -> String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024))
        }
    }

    val todayDownloadFormatted: String
        get() = formatBytes(todayDownload)

    val todayUploadFormatted: String
        get() = formatBytes(todayUpload)

    val totalDownloadFormatted: String
        get() = formatBytes(totalDownload)

    val totalUploadFormatted: String
        get() = formatBytes(totalUpload)
}