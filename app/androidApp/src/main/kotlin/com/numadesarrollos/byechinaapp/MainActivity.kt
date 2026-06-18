package com.numadesarrollos.byechinaapp

import androidx.compose.runtime.Composable
import com.numadesarrollos.base.presentation.activity.NDActivity

class MainActivity : NDActivity() {

    @Composable
    override fun Content() {
        App()
    }
}
