package in.co.praveenkumar.tumtumtracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends Activity {
	WebView mBrowser;
	String DEFAULT_URL = "http://tumtum-iitb.org";
	String DEFAULT_TITLE = "TTT browser";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);

		String url = DEFAULT_URL;
		String title = DEFAULT_TITLE;
		try {
			url = getIntent().getStringExtra("url");
			title = getIntent().getStringExtra("title");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle(title);

		mBrowser = (WebView) findViewById(R.id.webview);
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();

		mBrowser.getSettings().setJavaScriptEnabled(true);
		mBrowser.getSettings().setDomStorageEnabled(true);
		mBrowser.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
		});

		mBrowser.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				// Login user if not logged in
				// if (view.getUrl() == "") {

				// }
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// view.loadUrl("javascript:"
				// + "document.getElementById('password').value='lola';");
				System.out.println("Page loaded!");
			}
		});

		mBrowser.loadUrl(url);
	}

}
