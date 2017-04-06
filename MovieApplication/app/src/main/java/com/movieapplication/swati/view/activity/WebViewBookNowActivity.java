package com.movieapplication.swati.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import application.movie.swati.com.movieapplication.R;

/**
 * Created by aggarwal.swati on 4/6/17.
 */

public class WebViewBookNowActivity extends Activity {

	private WebView webView;
	ProgressBar progressBar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webview);
		webView = (WebView) findViewById(R.id.booknow);
		webView.getSettings().setJavaScriptEnabled(true);
		progressBar = (ProgressBar) findViewById(R.id.progress_indicator);
		webView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);

			}

		});
		webView.loadUrl("http://www.cathaycineplexes.com.sg/");
	}

}
