package com.enginebai.moviehunt

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.enginebai.base.view.BaseActivity

class MainActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		NavigationRouter.navigateToHome(this)
	}

	override fun handleErrorMessage(message: String) {
		displayCustomToast(message)
	}

	override fun getLayoutId() = R.layout.activity_main

	private fun displayCustomToast(message: String) {
		if (message.isBlank()) return
		val layout = layoutInflater.inflate(R.layout.toast, null)
		val textView = layout.findViewById<TextView>(R.id.textToast)
		textView.text = message
		with(Toast(this)) {
			setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
			duration = Toast.LENGTH_SHORT
			view = layout
			show()
		}
	}
}