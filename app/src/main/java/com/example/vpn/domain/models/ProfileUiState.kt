package com.example.vpn.domain.models

data class ProfileUiState(
    val user: User = User(
        id = "",
        email = "user@example.com",
        username = "User Name"
    ),
    val stats: VpnStats = VpnStats(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val subscriptionStatus: String
        get() = when {
            user.subscription?.isActive == true -> "Inactive"
            user.subscription?.isTrial == true -> "Trial"
            user.isPremium -> "Premium"
            else -> "Free"
        }

    val daysLeftText: String
        get() = user.subscription?.daysLeft?.let {
            if (it > 0) "$it days left" else "Expired"
        } ?: "No subscription"

    val expiryText: String
        get() = user.subscription?.formattedExpiry ?: "Never"

    val planDisplayName: String
        get() = user.subscription?.plan?.displayName ?: "Free"
}