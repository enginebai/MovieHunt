package com.enginebai.moviehunt.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.ColorsPalette
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle

@Composable
fun TitleWidget(
    title: String,
    onClickListener: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MHStyle.headline6,
            modifier = Modifier.padding(start = MHDimensions.pagePadding.dp)
        )
        if (onClickListener != null) {
            Button(onClick = { onClickListener.invoke() }) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    style = MHStyle.subtitle2.copy(color = ColorsPalette.colorAccent)
                )
            }
        }
    }
}

@Composable
@Preview
fun TitleWidgetPreview() {
    TitleWidget(title = stringResource(id = R.string.app_name),
        onClickListener = {

        })
}