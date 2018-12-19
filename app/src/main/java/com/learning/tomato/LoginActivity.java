package com.learning.tomato;

import android.os.Bundle;
import android.util.Log;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG,"onCreate execute");
    }
}
