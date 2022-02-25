package com.enginebai.moviehunt.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.enginebai.moviehunt.resources.MHColors

@Composable
fun ListSeparatorWidget(
    modifier: Modifier = Modifier
        .height(8.dp)
        .background(color = MHColors.background)
) {
    Spacer(
        modifier = modifier
    )
}