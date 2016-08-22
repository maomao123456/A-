package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XieYiActivity extends Activity{
	LinearLayout backQuyu;
	WebView wb;
	TextView tishi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fuwutiaokuan);
		backQuyu=(LinearLayout)findViewById(R.id.xieyi_back_quyu);
		wb=(WebView)findViewById(R.id.xieyi_webview);
		tishi=(TextView)findViewById(R.id.xieyi_tishi);
		backQuyu.setOnClickListener(listener);
		wb.setWebViewClient(client);// 设置webview的client相当于监听
		wb.getSettings().setJavaScriptEnabled(true);
		String url = "http://www.8888pet.com/about/copyright.html";
		wb.loadUrl(url);
	}
	WebViewClient client = new WebViewClient() {
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
		};

		// 网页加载的开始 可以加入进度条view
		public void onPageStarted(WebView view, String url,
				android.graphics.Bitmap favicon) {
			tishi.setVisibility(View.VISIBLE);
		};

		// 加载网页结束后 干的事 如关闭进度条等
		public void onPageFinished(WebView view, String url) {
			tishi.setVisibility(View.GONE);
		};

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return true;// 只调用webview加载数据，避免打开自带的浏览器
		};

	};
	OnClickListener listener=new OnClickListener() {
		public void onClick(View v) {
			Intent intent=new Intent(XieYiActivity.this,RegisterActivity.class);
			startActivity(intent);
			XieYiActivity.this.finish();
		}
	};
}
