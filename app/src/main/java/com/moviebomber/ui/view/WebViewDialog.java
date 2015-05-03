package com.moviebomber.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.moviebomber.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by engine on 15/5/3.
 */
public class WebViewDialog extends Dialog {

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
					.title(R.string.app_name)
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

	public WebViewDialog(Context context, String url) {
		super(context, android.R.style.Theme_NoTitleBar_Fullscreen);
		this.setContentView(R.layout.dialog_webview);
		ButterKnife.inject(this);
		this.mWebView.setWebViewClient(this.mClient);
		WebSettings settings = this.mWebView.getSettings();
		settings.setLoadWithOverviewMode(false);
		settings.setUseWideViewPort(false);
		this.mWebView.loadUrl(url);
	}
}
