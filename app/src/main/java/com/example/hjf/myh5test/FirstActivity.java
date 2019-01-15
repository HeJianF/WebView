package com.example.hjf.myh5test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_main1:
                //webView的用法
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.bt_main2:
                //Android自带 js与native交互
                startActivity(new Intent(this, JSActivity.class));
                break;
            case R.id.bt_main3:
                //JSBridge
                startActivity(new Intent(this, JsBridgeActivity.class));
            default:
        }
    }
}
