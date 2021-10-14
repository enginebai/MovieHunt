package com.enginebai.moviehunt.utils

import android.app.Activity
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.enginebai.moviehunt.R

fun Activity.openFragment(
    fragment: Fragment,
    addToBackStack: Boolean
) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction().apply {
        add(R.id.fragmentContainer, fragment)
        if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
        commit()
    }
}

fun ImageView.loadImage(url: String?) {
    load(url) {
        error(R.color.grey)
        placeholder(R.color.colorPrimary)
        crossfade(true)
    }
}