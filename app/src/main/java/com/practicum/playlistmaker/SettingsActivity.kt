package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SettingsScreen(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    tint = Color.Black
                )
            }
            Text(
                text = stringResource(R.string.settings_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.settings_dark_theme), fontSize = 16.sp, color = Color.Black)
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it },
                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3772E7))
            )
        }

        val shareMessage = stringResource(R.string.share_message)
        SettingsItem(
            text = stringResource(R.string.settings_share),
            icon = Icons.Default.Share,
            onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareMessage)
                }
                context.startActivity(Intent.createChooser(intent, null))
            }
        )

        val supportEmail = stringResource(R.string.support_email)
        val supportSubject = stringResource(R.string.support_subject)
        val supportBody = stringResource(R.string.support_body)
        SettingsItem(
            text = stringResource(R.string.settings_support),
            icon = Icons.Default.Email,
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                    putExtra(Intent.EXTRA_SUBJECT, supportSubject)
                    putExtra(Intent.EXTRA_TEXT, supportBody)
                }
                context.startActivity(intent)
            }
        )

        val agreementLink = stringResource(R.string.agreement_link)
        SettingsItem(
            text = stringResource(R.string.settings_agreement),
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(agreementLink))
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun SettingsItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.Black)
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFAEAFB4),
            modifier = Modifier.size(24.dp)
        )
    }
}
