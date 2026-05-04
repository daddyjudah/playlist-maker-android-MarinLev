package com.practicum.playlistmaker.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.view_model.SettingsViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.getViewModelFactory())
) {
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val isLoading by viewModel.getLoadingStateFlow().collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF3772E7))
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                }
                Text(text = stringResource(R.string.settings_title), fontSize = 22.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.settings_dark_theme), fontSize = 16.sp)
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { viewModel.onThemeChanged(it) },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3772E7))
                )
            }

            val shareMessage = stringResource(R.string.share_message)
            SettingsItem(stringResource(R.string.settings_share), Icons.Default.Share) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareMessage)
                }
                context.startActivity(Intent.createChooser(intent, null))
            }

            val email = stringResource(R.string.support_email)
            val subject = stringResource(R.string.support_subject)
            val body = stringResource(R.string.support_body)
            SettingsItem(stringResource(R.string.settings_support), Icons.Default.Email) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                }
                context.startActivity(intent)
            }

            val link = stringResource(R.string.agreement_link)
            SettingsItem(stringResource(R.string.settings_agreement), Icons.AutoMirrored.Filled.KeyboardArrowRight) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            }
        }
    }
}

@Composable
fun SettingsItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text, fontSize = 16.sp)
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFFAEAFB4), modifier = Modifier.size(24.dp))
    }
}
