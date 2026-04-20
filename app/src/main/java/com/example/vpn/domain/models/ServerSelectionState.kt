package com.example.vpn.domain.models

data class ServerSelectionState(
    val servers: List<VpnServer> = VpnServer.Examples,
    val selectedServerId: String = VpnServer.Default.id,
    val searchQuery: String = "",
    val selectedProtocol: VpnProtocol? = null,
    val selectedCountry: String? = null,
    val isLoading: Boolean = false
) {
    val filteredServers: List<VpnServer>
        get() = servers.filter { server ->
            val matchesSearch = searchQuery.isBlank() ||
                    server.name.contains(searchQuery, ignoreCase = true) ||
                    server.country.contains(searchQuery, ignoreCase = true) ||
                    server.city.contains(searchQuery, ignoreCase = true)

            val matchesProtocol = selectedProtocol == null || server.protocol == selectedProtocol

            val matchesCountry = selectedCountry == null || server.country == selectedCountry

            matchesSearch && matchesProtocol && matchesCountry
        }

    val selectedServer: VpnServer?
        get() = servers.find { it.id == selectedServerId }

    val groupedByCountry: Map<String, List<VpnServer>>
        get() = filteredServers.groupBy { it.country }

    val countries: List<String>
        get() = groupedByCountry.keys.sorted()
}