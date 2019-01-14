package com.example.hjf.myh5test;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class JSActivity extends AppCompatActivity {

    WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        mWebView = findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("file:///android_asset/javascript.html");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(JSActivity.this);
                dialog.setTitle("Alert");
                dialog.setMessage(message);
                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();
                return true;
            }
        });

        //Js调用Android方法
        //方法一，存在严重bug
        //mWebView.addJavascriptInterface(new AndroidToJs(), "test");
        //方法二
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("js")) {
                    if (uri.getAuthority().equals("webView")) {
                        Log.d("JSActivity", "shouldOverrideUrlLoading: js调用了Android的方法");
                        Set<String> parameterNames = uri.getQueryParameterNames();
                        for (String parameterName : parameterNames) {
                            Log.d("JSActivity", parameterName + " = " + uri.getQueryParameter(parameterName));
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //方法三
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("js")) {
                    if (uri.getAuthority().equals("demo")) {
                        Log.d("JSActivity", "onJsAlert: js调用了Android的方法");
                        result.confirm("js调用Android的方法成功了");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_android_js:
                //方法一
                mWebView.loadUrl("javascript:callJS()");
                //方法二
               /* mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d("JSActivity", "onReceiveValue: " + value);
                    }
                });*/
                break;
            case R.id.bt_js_android:
                mWebView.loadUrl("javascript:returnResult(Android回传给js的值)");
                break;
            default:
        }
    }
}
