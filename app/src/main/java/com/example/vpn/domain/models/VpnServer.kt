package com.example.vpn.domain.models

data class VpnServer(
    val id: String,
    val name: String,
    val country: String,
    val flag: String,
    val city: String,
    val address: String,
    val port: Int,
    val protocol: VpnProtocol,
    val latency: Int = 0,
    val load: Int = 0,
    val isRecommended: Boolean = false
) {
    val displayName: String
        get() = "$flag $country - $city"

    val shortName: String
        get() = "$flag $city"

    val locationString: String
        get() = "$country, $city"

    companion object {
        val Default = VpnServer(
            id = "default",
            name = "Amsterdam",
            country = "Netherlands",
            flag = "🇳🇱",
            city = "Amsterdam",
            address = "ams1.vpn.example.com",
            port = 443,
            protocol = VpnProtocol.VLESS_REALITY,
            latency = 23,
            load = 45,
            isRecommended = true
        )

        val Examples = listOf(
            VpnServer(
                id = "1",
                name = "Amsterdam",
                country = "Netherlands",
                flag = "🇳🇱",
                city = "Amsterdam",
                address = "ams1.vpn.example.com",
                port = 443,
                protocol = VpnProtocol.VLESS_REALITY,
                latency = 23,
                load = 45,
                isRecommended = true
            ),
            VpnServer(
                id = "2",
                name = "Frankfurt",
                country = "Germany",
                flag = "🇩🇪",
                city = "Frankfurt",
                address = "fra1.vpn.example.com",
                port = 443,
                protocol = VpnProtocol.VLESS_REALITY,
                latency = 31,
                load = 62
            ),
            VpnServer(
                id = "3",
                name = "Singapore",
                country = "Singapore",
                flag = "🇸🇬",
                city = "Singapore",
                address = "sg1.vpn.example.com",
                port = 443,
                protocol = VpnProtocol.HYSTERIA2,
                latency = 156,
                load = 38
            ),
            VpnServer(
                id = "4",
                name = "New York",
                country = "USA",
                flag = "🇺🇸",
                city = "New York",
                address = "nyc1.vpn.example.com",
                port = 443,
                protocol = VpnProtocol.SHADOWSOCKS,
                latency = 89,
                load = 71
            ),
            VpnServer(
                id = "5",
                name = "Tokyo",
                country = "Japan",
                flag = "🇯🇵",
                city = "Tokyo",
                address = "tyo1.vpn.example.com",
                port = 443,
                protocol = VpnProtocol.WIREGUARD,
                latency = 178,
                load = 24
            ),
            VpnServer(
                id = "6",
                name = "London",
                country = "UK",
                flag = "🇬🇧",
                city = "London",
                address = "lon1.vpn.example.com",
                port = 443,
                protocol = VpnProtocol.VLESS_REALITY,
                latency = 42,
                load = 53
            )
        )
    }
}

enum class VpnProtocol {
    VLESS_REALITY,
    SHADOWSOCKS,
    HYSTERIA2,
    WIREGUARD;

    val displayName: String
        get() = when (this) {
            VLESS_REALITY -> "VLESS + Reality"
            SHADOWSOCKS -> "Shadowsocks"
            HYSTERIA2 -> "Hysteria2"
            WIREGUARD -> "WireGuard"
        }
}