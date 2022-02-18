package com.enginebai.moviehunt.ui.list

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.enginebai.moviehunt.R

@EpoxyModelClass(layout = R.layout.view_load_more)
abstract class LoadMoreView : EpoxyModel<View>()