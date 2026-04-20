package com.example.vpn.ui.screens.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpn.domain.models.AppLanguage
import com.example.vpn.domain.models.AppTheme
import com.example.vpn.domain.models.VpnProtocol
import com.example.vpn.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Panda",
                            tint = BambooGreen,
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = "Bamboo Settings",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = BambooGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = BackgroundDark
                )
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BackgroundDark, DarkGradientEnd)
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PandaSettingsSection(title = "🎋 Panda Connection") {
                    PandaSwitchItem(
                        icon = Icons.Default.CheckCircle,
                        title = "Auto-connect",
                        subtitle = "Connect automatically when panda wakes up",
                        checked = uiState.settings.autoConnect,
                        onCheckedChange = { viewModel.setAutoConnect(it) }
                    )
                    PandaSwitchItem(
                        icon = Icons.Default.Notifications,
                        title = "Bamboo Alerts",
                        subtitle = "Receive notifications about bamboo growth",
                        checked = uiState.settings.notificationsEnabled,
                        onCheckedChange = { viewModel.setNotificationsEnabled(it) }
                    )
                    PandaSwitchItem(
                        icon = Icons.Default.FavoriteBorder,
                        title = "Kill Switch",
                        subtitle = "Protect panda if connection drops",
                        checked = uiState.settings.killSwitch,
                        onCheckedChange = { viewModel.setKillSwitch(it) }
                    )
                }

                PandaSettingsSection(title = "🎋 Bamboo Protocol") {
                    PandaDropdownItem(
                        icon = Icons.Default.Lock,
                        title = "Protocol",
                        value = uiState.settings.selectedProtocol.displayName,
                        options = uiState.protocols.map { it.displayName },
                        onValueSelected = { selected ->
                            val protocol = VpnProtocol.values().find { it.displayName == selected }
                            protocol?.let { viewModel.setProtocol(it) }
                        }
                    )
                }

                PandaSettingsSection(title = "🎋 Panda Look") {
                    PandaDropdownItem(
                        icon = Icons.Default.ShoppingCart,
                        title = "Theme",
                        value = uiState.settings.theme.displayName,
                        options = uiState.themes.map { it.displayName },
                        onValueSelected = { selected ->
                            val theme = AppTheme.values().find { it.displayName == selected }
                            theme?.let { viewModel.setTheme(it) }
                        }
                    )
                    PandaDropdownItem(
                        icon = Icons.Default.LocationOn,
                        title = "Language",
                        value = uiState.settings.language.displayName,
                        options = uiState.languages.map { it.displayName },
                        onValueSelected = { selected ->
                            val language = AppLanguage.values().find { it.displayName == selected }
                            language?.let { viewModel.setLanguage(it) }
                        }
                    )
                }

                PandaSettingsSection(title = "🎋 About Panda") {
                    PandaInfoItem(
                        icon = Icons.Default.Info,
                        title = "Version",
                        value = "1.0.0"
                    )
                    PandaInfoItem(
                        icon = Icons.Default.Lock,
                        title = "Privacy Policy",
                        value = ""
                    )
                    PandaInfoItem(
                        icon = Icons.Default.Favorite,
                        title = "Rate Bamboo VPN",
                        value = ""
                    )
                }
            }
        }
    }
}

@Composable
fun PandaSettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundCard
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = BambooGreen
            )
            Divider(color = PandaGray)
            content()
        }
    }
}

@Composable
fun PandaSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = BambooGold,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimaryDark
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryDark
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = BambooGreen,
                checkedTrackColor = BambooGreen.copy(alpha = 0.5f),
                uncheckedThumbColor = PandaGray,
                uncheckedTrackColor = PandaGray.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun PandaDropdownItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = BambooGold,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimaryDark
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryDark
            )
        }
        Icon(
            Icons.Default.KeyboardArrowDown,
            contentDescription = "Select",
            tint = BambooGold
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BackgroundCard, RoundedCornerShape(12.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == value) BambooGreen else TextPrimaryDark
                        )
                    },
                    onClick = {
                        onValueSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PandaInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = BambooGold,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimaryDark,
            modifier = Modifier.weight(1f)
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryDark
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = TextSecondaryDark,
            modifier = Modifier.size(20.dp)
        )
    }
}