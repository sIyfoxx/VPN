package com.example.vpn.domain.models

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val protocols: List<VpnProtocol> = VpnProtocol.values().toList(),
    val themes: List<AppTheme> = AppTheme.values().toList(),
    val languages: List<AppLanguage> = AppLanguage.values().toList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val selectedProtocolDisplay: String
        get() = settings.selectedProtocol.displayName

    val themeDisplay: String
        get() = settings.theme.displayName

    val languageDisplay: String
        get() = settings.language.displayName
}