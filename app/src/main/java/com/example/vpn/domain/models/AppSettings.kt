package com.example.vpn.domain.models

data class AppSettings(
    val autoConnect: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val killSwitch: Boolean = false,
    val selectedProtocol: VpnProtocol = VpnProtocol.VLESS_REALITY,
    val theme: AppTheme = AppTheme.SYSTEM,
    val language: AppLanguage = AppLanguage.SYSTEM,
    val dataSaving: Boolean = false
)

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM;

    val displayName: String
        get() = when (this) {
            LIGHT -> "Light"
            DARK -> "Dark"
            SYSTEM -> "System default"
        }
}

enum class AppLanguage {
    ENGLISH,
    RUSSIAN,
    SYSTEM;

    val displayName: String
        get() = when (this) {
            ENGLISH -> "English"
            RUSSIAN -> "Русский"
            SYSTEM -> "System default"
        }
}