package com.dubaidial.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.techpro.dubaidial.R;

public class MoreInfoWebViewActivity extends Activity 
{
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
	    webView = (WebView) findViewById(R.id.moreinfowebview);
	    webView.getSettings().setJavaScriptEnabled(true);
	    Log.e("----->", "-->"+getIntent().getStringExtra("additionalinfo").trim());
	    webView.loadUrl(getIntent().getStringExtra("additionalinfo").trim());
	    webView.setWebViewClient(new WebViewClient() {
	        public boolean shouldOverrideUrlLoading(WebView view, String url){
	            // do your handling codes here, which url is the requested url
	            // probably you need to open that url rather than redirect:
	            view.loadUrl(url);
	            return false; // then it is not handled by default action
	       }
	    });

//	    String customHtml = "<html><body><h1>Hello, WebView</h1></body></html>";
//		   webView.loadData(customHtml, "text/html", "UTF-8");
    }
}
