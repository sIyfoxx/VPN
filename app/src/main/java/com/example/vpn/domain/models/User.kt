package com.example.vpn.domain.models

import java.util.Date

data class User(
    val id: String,
    val email: String,
    val username: String,
    val avatarUrl: String? = null,
    val subscription: Subscription? = null,
    val createdAt: Date = Date(),
    val isEmailVerified: Boolean = false
) {
    val displayName: String
        get() = username.ifEmpty { email.substringBefore("@") }

    val isPremium: Boolean
        get() = subscription?.isActive == true
}

data class Subscription(
    val plan: SubscriptionPlan,
    val startsAt: Date,
    val expiresAt: Date? = null,
    val autoRenew: Boolean = true,
    val isTrial: Boolean = false
) {
    val isActive: Boolean
        get() = expiresAt == null || expiresAt > Date()

    val daysLeft: Int
        get() = if (expiresAt != null && isActive) {
            val diff = expiresAt.time - System.currentTimeMillis()
            (diff / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(0)
        } else 0

    val formattedExpiry: String
        get() = expiresAt?.let {
            android.text.format.DateFormat.format("dd MMM yyyy", it).toString()
        } ?: "Never"

    val progressPercent: Float
        get() = if (expiresAt != null && startsAt.time < expiresAt.time) {
            val total = expiresAt.time - startsAt.time
            val remaining = expiresAt.time - System.currentTimeMillis()
            (remaining.toFloat() / total.toFloat()).coerceIn(0f, 1f)
        } else 0f
}

enum class SubscriptionPlan(val displayName: String, val pricePerMonth: Double) {
    FREE("Free", 0.0),
    MONTHLY("Monthly", 3.99),
    SEMI_ANNUAL("6 Months", 4.99),
    YEARLY("Yearly", 9.99);

    val isPaid: Boolean
        get() = this != FREE
}