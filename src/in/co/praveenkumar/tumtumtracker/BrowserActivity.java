package in.co.praveenkumar.tumtumtracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

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
		mBrowser.loadUrl(url);
	}

}
