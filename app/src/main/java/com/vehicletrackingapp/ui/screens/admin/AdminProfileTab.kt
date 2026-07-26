package com.vehicletrackingapp.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.FuturisticTextField
import com.vehicletrackingapp.ui.screens.common.PremiumGlassCard
import com.vehicletrackingapp.ui.screens.common.GradientButton
import com.vehicletrackingapp.ui.screens.common.SectionTitle
import androidx.compose.material3.Text

@Composable
fun AdminProfileTab() {
    var username by remember { mutableStateOf(AppRepository.adminUsername) }
    var password by remember { mutableStateOf(AppRepository.adminPassword) }
    var saved by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionTitle(stringResource(R.string.admin_profile))
        PremiumGlassCard {
            FuturisticTextField(value = username, onValueChange = { username = it }, label = stringResource(R.string.username))
            Spacer(modifier = Modifier.height(16.dp))
            FuturisticTextField(value = password, onValueChange = { password = it }, label = stringResource(R.string.password), isPassword = true)
            Spacer(modifier = Modifier.height(32.dp))
            GradientButton(text = stringResource(R.string.save)) {
                AppRepository.adminUsername = username
                AppRepository.adminPassword = password
                saved = true
            }
            if (saved) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(R.string.save) + " ✓", color = com.vehicletrackingapp.ui.theme.SuccessEmerald, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        }
    }
}
