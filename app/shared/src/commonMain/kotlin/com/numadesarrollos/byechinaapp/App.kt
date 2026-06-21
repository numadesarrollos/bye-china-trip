package com.numadesarrollos.byechinaapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.numadesarrollos.byechinaapp.auth.AuthRepository
import com.numadesarrollos.byechinaapp.nav.AppNavGraph
import com.numadesarrollos.byechinaapp.ui.theme.AppTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val authRepository = koinInject<AuthRepository>()
            val startLoggedIn = remember { authRepository.isSignedIn() }
            AppNavGraph(startLoggedIn = startLoggedIn)
        }
    }
}
