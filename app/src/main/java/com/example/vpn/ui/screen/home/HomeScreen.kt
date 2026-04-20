package com.example.vpn.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpn.ui.theme.*
import com.example.vpn.domain.models.VpnServer
import com.example.vpn.domain.models.ConnectionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
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
                            text = "Panda VPN",
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Кнопка подключения
                PandaConnectButton(
                    isConnected = uiState.isConnected,
                    isConnecting = uiState.isConnecting,
                    onClick = { viewModel.toggleConnection() }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Статус подключения
                ConnectionStatusCard(
                    connectionState = uiState.connectionState,
                    currentServer = uiState.currentServer,
                    sessionTime = uiState.sessionTime
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Статистика
                PandaStatsCard(
                    todayDownload = uiState.stats.todayDownloadFormatted,
                    todayUpload = uiState.stats.todayUploadFormatted,
                    totalTraffic = uiState.stats.totalAllFormatted
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Выбор сервера
                ServerSelectionSection(
                    currentServer = uiState.currentServer,
                    servers = uiState.servers,
                    onServerSelected = { viewModel.selectServer(it) }
                )
            }
        }
    }
}

@Composable
fun PandaConnectButton(
    isConnected: Boolean,
    isConnecting: Boolean,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "panda_pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .size(160.dp)
            .shadow(
                elevation = if (isConnected) 20.dp else 12.dp,
                shape = CircleShape,
                clip = false,
                spotColor = if (isConnected) BambooSuccess else BambooGreen,
                ambientColor = if (isConnected) BambooSuccess else BambooGreen
            )
            .scale(if (isConnected || isConnecting) pulse else 1f)
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                if (isConnected) BambooSuccess else BambooGreen,
                                if (isConnected) BambooSuccess.copy(alpha = 0.7f) else BambooGreen.copy(alpha = 0.7f)
                            ),
                            center = Offset(0.3f, 0.3f),
                            radius = 0.8f
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isConnecting) {
                    CircularProgressIndicator(
                        color = TextPrimaryDark,
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 3.dp
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = if (isConnected) Icons.Default.Lock else Icons.Default.Lock,
                            contentDescription = if (isConnected) "Disconnect" else "Connect",
                            tint = TextPrimaryDark,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = if (isConnected) "DISCONNECT" else "CONNECT",
                            color = TextPrimaryDark,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectionStatusCard(
    connectionState: ConnectionState,
    currentServer: VpnServer,
    sessionTime: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundCard
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Status",
                    tint = if (connectionState.isConnected) BambooSuccess else BambooGold,
                    modifier = Modifier.size(32.dp)
                )
                Column {
                    Text(
                        text = connectionState.displayText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (connectionState.isConnected) BambooSuccess else BambooGold
                    )
                    if (connectionState.isConnected && sessionTime != null) {
                        Text(
                            text = "Session: $sessionTime",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondaryDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = PandaGray)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🐼 Current Server",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryDark
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = currentServer.flag,
                        fontSize = 20.sp
                    )
                    Text(
                        text = currentServer.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = BambooGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "🎋 Latency",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark
                )
                Text(
                    text = if (currentServer.latency > 0) "${currentServer.latency} ms" else "—",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎍 Load",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark
                )
                LinearProgressIndicator(
                    progress = currentServer.load / 100f,
                    modifier = Modifier
                        .width(100.dp)
                        .height(6.dp),
                    color = when (currentServer.load) {
                        in 0..30 -> BambooSuccess
                        in 31..70 -> BambooGold
                        else -> BambooError
                    },
                    trackColor = PandaGray
                )
            }
        }
    }
}

@Composable
fun PandaStatsCard(
    todayDownload: String,
    todayUpload: String,
    totalTraffic: String
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
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎋 Today's Bamboo",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = BambooGreen
                )
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Leaf",
                    tint = BambooGold,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PandaStatItem(
                    icon = Icons.Default.Add,
                    value = todayDownload,
                    label = "Download"
                )
                PandaStatItem(
                    icon = Icons.Default.Done,
                    value = todayUpload,
                    label = "Upload"
                )
                PandaStatItem(
                    icon = Icons.Default.Favorite,
                    value = totalTraffic,
                    label = "Total"
                )
            }
        }
    }
}

@Composable
fun PandaStatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BambooGold,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = BambooGreen
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondaryDark
        )
    }
}

@Composable
fun ServerSelectionSection(
    currentServer: VpnServer,
    servers: List<VpnServer>,
    onServerSelected: (VpnServer) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🎍 Bamboo Locations",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = BambooGreen
            )
            TextButton(onClick = { /* TODO */ }) {
                Text("See all", color = BambooGold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(servers.take(4)) { server ->
                ServerPandaCard(
                    server = server,
                    isSelected = server.id == currentServer.id,
                    onClick = { onServerSelected(server) }
                )
            }
        }
    }
}

@Composable
fun ServerPandaCard(
    server: VpnServer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) BambooGreen.copy(alpha = 0.2f) else BackgroundCard
        ),
        border = if (isSelected) BorderStroke(1.dp, BambooGreen) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = server.flag,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = server.city,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) BambooGreen else TextPrimaryDark,
                maxLines = 1
            )
            Text(
                text = "${server.latency} ms",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondaryDark
            )
            if (server.isRecommended) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "🎋",
                    fontSize = 12.sp
                )
            }
        }
    }
}