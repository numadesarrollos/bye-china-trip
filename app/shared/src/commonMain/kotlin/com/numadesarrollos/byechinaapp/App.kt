package com.numadesarrollos.byechinaapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.numadesarrollos.byechinaapp.auth.LoginScreen
import com.numadesarrollos.byechinaapp.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            // Phase 1: Login screen (Firebase Auth hello-world)
            // Phase 3: replace with NavHost and full navigation
            LoginScreen(
                onLoginSuccess = { /* TODO Phase 3: navigate to home */ },
            )
        }
    }
}
