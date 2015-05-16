package com.moviebomber.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.moviebomber.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebViewActivity extends ActionBarActivity {

	public static final String EXTRA_URL = "URL";

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	@InjectView(R.id.webview)
	WebView mWebView;

	private MaterialDialog mProgressDialog;
	private WebViewClient mClient = new WebViewClient() {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			mProgressDialog = new MaterialDialog.Builder(view.getContext())
					.title(getResources().getString(R.string.app_name_chinese))
					.content(R.string.loading)
					.progress(true, 0)
					.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			if (mProgressDialog != null)
				mProgressDialog.dismiss();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		ButterKnife.inject(this);
		this.mWebView.setWebViewClient(this.mClient);
		WebSettings settings = this.mWebView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setSupportZoom(true);
		this.mWebView.loadUrl(getIntent().getStringExtra(EXTRA_URL));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
