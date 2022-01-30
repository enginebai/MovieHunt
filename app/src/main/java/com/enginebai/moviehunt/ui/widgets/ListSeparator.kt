package com.enginebai.moviehunt.ui.widgets

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHColors

@EpoxyModelClass(layout = R.layout.view_list_separator)
abstract class ListSeparator : EpoxyModel<View>()

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