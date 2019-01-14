package com.example.hjf.myh5test;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * @author heJianfeng
 * @date 2019/1/14
 */
public class AndroidToJs extends Object {

    @JavascriptInterface
    public void hello(String msg) {
        Log.d("AndroidToJs", msg);
    }
}
