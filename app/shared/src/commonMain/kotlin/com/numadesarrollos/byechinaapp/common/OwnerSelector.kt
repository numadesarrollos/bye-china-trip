package com.numadesarrollos.byechinaapp.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.numadesarrollos.byechinaapp.domain.Owner

@Composable
fun OwnerSelector(
    selected: Owner,
    onSelect: (Owner) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth().selectableGroup()) {
        FilterChip(
            selected = selected == Owner.BEAR,
            onClick = { onSelect(Owner.BEAR) },
            label = { Text("🐻 Borja") },
        )
        Spacer(Modifier.width(8.dp))
        FilterChip(
            selected = selected == Owner.BUN,
            onClick = { onSelect(Owner.BUN) },
            label = { Text("🐰 Esther") },
        )
    }
}
